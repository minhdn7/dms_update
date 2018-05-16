package vn.com.vsc.ptpm.VNPT_DMS.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import vn.com.vsc.ptpm.VNPT_DMS.dao.InfoDeviceDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import vn.com.vsc.ptpm.VNPT_DMS.control.GetSignalStatus;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;

public class GPSTracker extends Service implements LocationListener,
		onGetSignalStatus {

	private final String TAG = "GPSTracker";
	private final Context mContext;

	// flag for GPS status
	public boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location;
	double latitude;
	double longitude;
	double accuracy;
	int hasAccuracy;

	final double PI = 3.141592654;
	final int EARTH_RADIUS = 6371000;

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 10; // 10s

	protected LocationManager locationManager;

	private onGetSignalStatus listener;
	private GetSignalStatus signalStatus;
	public int levelBattery;
	public int signalStrength;
	public String networkType;
	public String updateTime;
	private InfoDeviceEntity info = null;

	public GPSTracker(Context context) {
		this.mContext = context;
		signalStatus = new GetSignalStatus(context, this);
		getLocation();
	}

	public Location getLocation() {
		try {
			Log.i(TAG, "START GPS");
			Log.d(TAG, "levelBattery =" + levelBattery);
			Log.d(TAG, "networkType =" + networkType);
			Log.d(TAG, "signalStrength =" + signalStrength);
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			Log.i("check", "isGPSEnabled" + isGPSEnabled);
			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			Log.i("check", "isNetworkEnabled" + isNetworkEnabled);
			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
				this.canGetLocation = false;
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this,
							Looper.getMainLooper());
					Log.d("Network", "Network");

					if (locationManager != null) {
						if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
							// TODO: Consider calling
							//    ActivityCompat#requestPermissions
							// here to request the missing permissions, and then overriding
							//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
							//                                          int[] grantResults)
							// to handle the case where the user grants the permission. See the documentation
							// for ActivityCompat#requestPermissions for more details.
							return null;
						}
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
							accuracy = location.getAccuracy();
							if (location.hasAccuracy()) {
								hasAccuracy = 1;
							} else {
								hasAccuracy = 0;
							}
							updateTime = convertGPSTime(location.getTime());

							String message = String
									.format("NETWORK PROVIDER:\nLatitude: %1$s \nLongitude: %2$s",
											location.getLatitude(),
											location.getLongitude());
							Log.i(TAG + "-NETWORK", message);
						}
					}
				}

				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this,
								Looper.getMainLooper());
						Log.d("GPS Enabled", "GPS Enabled");

						if (locationManager != null) {
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								accuracy = location.getAccuracy();
								if (location.hasAccuracy()) {
									hasAccuracy = 1;
								} else {
									hasAccuracy = 0;
								}
								updateTime = convertGPSTime(location.getTime());

								String message = String
										.format("GPS PROVIDER:\nLatitude: %1$s \nLongitude: %2$s",
												location.getLatitude(),
												location.getLongitude());
								Log.i(TAG + "-GPS", message);
							}
						}
					}
				}
			}
			Log.i(TAG, "STOP_GPS");
		} catch (Exception e) {
			e.printStackTrace();
			latitude = longitude = 0d;
		}
		return location;
	}

	public void saveDataToDB() {
		try {
			Log.d(TAG, "Start saveDataToDB");

			InfoDeviceDAL dal = new InfoDeviceDAL(mContext);
			// khoi tao khoang cach giua 2 dia diem ban dau bang 0
			double distance = 0;
			// TH app duoc kich hoat, lay thong tin location cuoi cung.
			if (mAF.isDistance != 0) {
				InfoDeviceEntity entity = dal.getLastInfoDevice();
				if (entity != null) {
					Log.d(TAG, "entity = " + entity.toString());
					double lastLat = entity.getLat();
					double lastLong = entity.getLng();
					distance = getDistanceInMeter(lastLat, lastLong,
							location.getLatitude(), location.getLongitude());
					Log.d(TAG, "DISTANCE = " + distance);
				}
			}
			mAF.isDistance = 1;
			Log.d(TAG, "saveDataToDB 1");
			try {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(mContext);
				Config.username = preferences.getString(Config.LAST_USER_LOGIN,
						"x");
			} catch (Exception ex1) {
				Log.d(TAG, "saveDataToDB 2 Error: " + ex1.toString());
				Config.username = "-";
			}

			Log.d(TAG, "saveDataToDB 2 " + Config.username);

			mAF.writelog("SaveLocationToDB 1 Get user XML: " + Config.username,
					mContext, mAF.filelog);

			info = new InfoDeviceEntity(0, "null", Config.username, latitude,
					longitude, updateTime, levelBattery, networkType,
					signalStrength, 0, distance, accuracy, hasAccuracy);
			long result = dal.add(info);
			int idLastRecord = dal.getCount();

			Log.d(TAG, "COUNT = " + idLastRecord);
			InfoDeviceEntity en = dal.getInfoDevice(idLastRecord);

			Log.d(TAG, "InfoDeviceEntity = " + en.toString());
			Log.e(TAG, result > 0 ? "Success" : "Failure");

			mAF.writelog("SaveLocationToDB: " + en.toString()
					+ ". Total records=" + idLastRecord, mContext, mAF.filelog);

		} catch (Exception e) {
			Log.d(TAG, "Error SaveLocationToDB: " + e.toString());
			mAF.writelog("Error SaveLocationToDB: " + e.toString(), mContext,
					mAF.filelog);
		}
	}

	private double toRad(double r) {
		return r * PI / 180;
	}

	// public double getDistanceInMeter(Location lastLocation,
	// Location currentLocation) {
	public double getDistanceInMeter(double lastLatitude, double lastLong,
									 double currentLatitude, double currentLong) {
		// double lastLong = lastLocation.getLongitude();
		// double lastLatitude = lastLocation.getLatitude();
		// double currentLong = lastLocation.getLongitude();
		// double currentLatitude = lastLocation.getLatitude();

		if (lastLong == 0 || lastLatitude == 0 || currentLong == 0
				|| currentLatitude == 0) {
			return 0d;
		}
		double a, c, d;
		double dLat, dLon, rlat1, rlat2;
		try {
			dLat = toRad(currentLatitude - lastLatitude);
			dLon = toRad(currentLong - lastLong);
			rlat1 = toRad(lastLatitude);
			rlat2 = toRad(currentLatitude);

			a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
					* Math.sin(dLon / 2) * Math.cos(rlat1) * Math.cos(rlat2);
			c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			d = EARTH_RADIUS * c;
		} catch (Exception e) {
			d = 0;
		}
		return Math.ceil(d);
	}

	/* convert gps time */
	public String convertGPSTime(long time) {
		Date d = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmmss");
		return sdf.format(d);
	}

	/* get device time */
	public String getCurrentTime() {
		String time = "";
		try {
			time = Utilities.getDate(System.currentTimeMillis(),
					"ddMMyyyy HHmmss");
		} catch (ParseException e1) {
			e1.printStackTrace();
			time = "";
		}
		return time;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			locationManager.removeUpdates(GPSTracker.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null) {
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog On pressing Settings button will
	 * lauch Settings Options
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

		// Setting Dialog Title
		alertDialog.setTitle("GPS is settings");

		// Setting Dialog Message
		alertDialog
				.setMessage("GPS is not enabled. Do you want to go to settings menu?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(intent);
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public String getBatteryLevel() {
		return levelBattery + "";
	}

	public String getNetworkType() {
		return networkType;
	}

	public String getSignalStrength() {
		return signalStrength + "";
	}

	@Override
	public void onLocationChanged(Location currentLocation) {
		// double minDistance = getDistanceInMeter(this.location,
		// currentLocation);
		// if (minDistance >= DISTANCE_LIMIT) {
		// saveLocationInfoToDB(new LocationInfoEntity(location.getLatitude(),
		// location.getLongitude(), 0));
		// String message = String.format(
		// "LOCATION ONCHANGE:\nLatitude: %1$s \nLongitude: %2$s",
		// location.getLatitude(), location.getLongitude());
		// Log.d("UPDATE-CURRENT-LOCATION", message);
		// } else {
		// Log.d("UPDATE-CURRENT-LOCATION", "minDistance < DISTANCE_LIMIT");
		// }
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onGetBatteryLevel(int i) {
		levelBattery = i;
	}

	@Override
	public void onGetSignalStrengValue(int i) {
		signalStrength = i;
	}

	@Override
	public void onGetNetworkType(String type) {
		networkType = type;
	}

}