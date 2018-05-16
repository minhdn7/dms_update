package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;

public class Addr2Activity extends FragmentActivity implements OnMapReadyCallback {
	String TAG = Addr2Activity.class.getSimpleName();

	private EditText et_diachiKH;
	private Button btn_UpdateLocaltion, btn_Thoat;

	private GoogleMap gMap;
	private double latitude;
	private double longitude;

	protected LocationManager locationManager;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addr2);

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		checkGPS();

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);

		if (status != ConnectionResult.SUCCESS) {
			Dialog d = GooglePlayServicesUtil.getErrorDialog(status, this, 1);
			d.show();
		} else {
			// init UI
			et_diachiKH = (EditText) findViewById(R.id.et_diachiKH);
			btn_UpdateLocaltion = (Button) findViewById(R.id.btn_UpdateLocaltion);
			btn_Thoat = (Button) findViewById(R.id.btn_Thoat);
			et_diachiKH.setEnabled(false);

			btn_Thoat.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});

			btn_UpdateLocaltion.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					String address = et_diachiKH.getText().toString();
					Intent intentData = new Intent();
					intentData.putExtra("address", address);
					intentData.putExtra("latitude", latitude);
					intentData.putExtra("longitude", longitude);
					setResult(1, intentData);
					finish();
				}
			});

			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAddr);
//			googleMap = fm.getMap();
			fm.getMapAsync(this);
//			googleMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
//
//				@Override
//				public void onMapLoaded() {
//					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//					googleMap.getUiSettings().setZoomControlsEnabled(true);
//					googleMap.setMyLocationEnabled(false);
//					new GPSTracker(Addr2Activity.this);
//				}
//			});
		}
	}
	public void checkGPS(){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

			if (!provider.contains("gps")) { //if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				sendBroadcast(poke);
			}
		} else {
			if (!isGPSEnabled) {
				// Thong bao chua bat GPS
				AlertDialog.Builder builder = new AlertDialog.Builder(Addr2Activity.this);
				builder.setTitle("GPS đang không bật");
				builder.setMessage("Để sử dụng chức năng này xin vui lòng bật GPS!");
				builder.setCancelable(false);
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivityForResult(intent, 0);
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap;
		gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		gMap.getUiSettings().setZoomControlsEnabled(true);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
				requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			}
			return;
		}
		gMap.setMyLocationEnabled(false);
		new GPSTracker(Addr2Activity.this);
	}

	public class GPSTracker extends Service implements LocationListener {

		private final Context mContext;
		boolean isGPSEnabled = false;
		boolean isNetworkEnabled = false;
		boolean canGetLocation = false;
		Location location;
		private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
		private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
		protected LocationManager locationManager;

		public GPSTracker(Context context) {
			this.mContext = context;
			getLocation();
		}

		public void getLocation() {
			try {
				locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
				isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if (!isGPSEnabled && !isNetworkEnabled) {

				} else {
					this.canGetLocation = true;
					if (isNetworkEnabled) {
						if (ActivityCompat.checkSelfPermission(Addr2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Addr2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
							Log.d(TAG, "check error!");
//							//    ActivityCompat#requestPermissions
//							// here to request the missing permissions, and then overriding
//							//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//							//                                          int[] grantResults)
//							// to handle the case where the user grants the permission. See the documentation
//							// for ActivityCompat#requestPermissions for more details.
							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
								requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
								requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
							}
							return;
						}
						locationManager.requestLocationUpdates(
								LocationManager.NETWORK_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("Network", "Network");

						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
								onLocationChanged(location);
							}
						}
					}

					if (isGPSEnabled) {
						if (location == null) {
							locationManager.requestLocationUpdates(
									LocationManager.GPS_PROVIDER,
									MIN_TIME_BW_UPDATES,
									MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
							Log.d("GPS Enabled", "GPS Enabled");

							if (locationManager != null) {
								location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
								if (location != null) {
									latitude = location.getLatitude();
									longitude = location.getLongitude();
									onLocationChanged(location);
								}
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "Error get location!" + e.toString());
			}
		}

		public void getCurrentLocation(Location location) {
			if (location != null) {
				gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(location.getLatitude(), location.getLongitude()), 13));

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(location.getLatitude(), location
								.getLongitude())).zoom(15).bearing(90).tilt(40)
						.build();

				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				Marker m = gMap
						.addMarker(new MarkerOptions()
								.position(latLng)
								.title("Vị trí của bạn")
								.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_2)));
				m.showInfoWindow();
				gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
				gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
				Geocoder geo = new Geocoder(mContext);
				try {
					List<Address> addr = new ArrayList<Address>();
					addr = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
					if (addr != null && addr.size() > 0) {
						//if (et_diachiKH.getText().toString().equals("")) {
						String address = addr.get(0).getAddressLine(0)
								+ ", " + addr.get(0).getAddressLine(1)
								+ ", " + addr.get(0).getAddressLine(2)
								+ ", " + addr.get(0).getAddressLine(3);
						et_diachiKH.setText(address);
						//}
					} else {
						Toast.makeText(mContext, "Bạn cần phải kết nối mạng và bật định vị GPS!", Toast.LENGTH_LONG).show();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(mContext, "Bạn cần phải cho phép bật vị trí của bạn !", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(intent, 0);
			}
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			getCurrentLocation(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public IBinder onBind(Intent intent) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				break;

			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
