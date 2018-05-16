package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.ReadWriteFile;

import static vn.com.vsc.ptpm.VNPT_DMS.control.API_code.latitude;
import static vn.com.vsc.ptpm.VNPT_DMS.control.API_code.longitude;

/**
 * Created by ToiHV on 25-Aug-16.
 */
public class LocationService extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,
        ILocationConstants, IPreferenceConstants {

    private static final String TAG = LocationService.class.getSimpleName();
    private Context mContext;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    private AppPreferences appPreferences;
    private ReadWriteFile file;

    //cac tham so toa do, khoang cach
    protected Location mCurrentLocation;
    private LocationHelper locationHelper;
    protected String mLastUpdateTime;
    private Location oldLocation;
    private Location newLocation;
    private float distance;
    private LocationModel model;

    //tham so pin, loai tin hieu, cuong do tin hieu
    public int levelBattery;
    public int signalStrength;
    private WifiManager wifiManager;
    private MyPhoneStateListener myListener;
    private int singalStrength3G;
    private TelephonyManager telephonyManager;
    public String networkType;
    private long previousUpdateTime = 0;

    int count = 0;
    boolean isFirstSaveDB = false;

    private MainActivity activity = new MainActivity();

    ArrayList<LatLng> directionPositionList = new ArrayList<LatLng>();
    private String serverKey = "AIzaSyAZQ2jucDKfh19Z28RsqFLIBY65qq0emD8";

    public LocationService(Context context) {
        this.mContext = context;
//        getLocation();
    }

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        file = new ReadWriteFile();
        appPreferences = new AppPreferences(this);
        oldLocation = new Location("Point A");
        newLocation = new Location("Point B");
        locationHelper = new LocationHelper(this);
        mLastUpdateTime = "";
        distance = appPreferences.getFloat(PREF_DISTANCE, 0);
        getSignalStrengthAndNetworkType();
        Log.d(TAG, "onCreate Distance: " + distance);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buildGoogleApiClient();
        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
        return START_STICKY;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        /*tin hieu co the tra ve som hon so voi thoi gian update location thiet dat, thoi gian tra ve dia diem se nam trong khoang UPDATE_INTERVAL_IN_MILLISECONDS va FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS*/
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//        /*PRIORITY_HIGH_ACCURACY: chinh xac nhat, dung de dinh vi khoang cach trong thoi gian thuc
//        * PRIORITY_BALANCED_POWER_ACCURACY: che do dinh vi tiet kiem nang luong
//		* PRIORITY_LOW_POWER: do chinh xac ~10km, dung de xac dinh dia diem nhu mot thanh pho
//		* */
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(10000); //10 seconds
//        mLocationRequest.setFastestInterval(10000); //5 seconds
        //chon che do tiet kiem nang luong, su dung PRIORITY_HIGH_ACCURACY khi can do chinh xac
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        try {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d("CONCRETE", "Start Location Update...");
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            } else {
                Log.d("CONCRETE", "Start Location Update failed!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("CONCRETE", "There something wrong!");
        }
    }

    //xu li location
    private void updateLocation() {
        if (Config.isLogin) {
            if (null != mCurrentLocation) {
                mAF.latCurrent = mCurrentLocation.getLatitude();
                mAF.longCurrent = mCurrentLocation.getLongitude();
                appPreferences.putFloat(PREF_DISTANCE, distance);
                getOldNewLocation(mCurrentLocation);
                saveDB(mCurrentLocation);
                logLocation();
            }
        }
    }

    public void logLocation() {
        Log.d(TAG + "count", count + "");
        Log.d(TAG, getUser() + "\n" + mCurrentLocation.getLatitude() + "\n"
                + mCurrentLocation.getLongitude() + "\n" + onGetTime() + "\n"
                + levelBattery + "\n" + networkType + "\n" + signalStrength
                + "\n" + 0 + "\n" + 0 + "");
    }

    public void saveDB(Location location) {
        isFirstSaveDB = true;
//        getSignalStrengthAndNetworkType();
        int hasAccurancy = 0;
        if(location.hasAccuracy()){
            hasAccurancy = 1;
        }else {
            hasAccurancy = 0;
        }
        model = new LocationModel(0, "null", getUser(), location.getLatitude(),
                location.getLongitude(), onGetTime(), getLevelBattery(), networkType,
                signalStrength, 0, 0, location.getAccuracy(), hasAccurancy);
        locationHelper.add(model);
        Log.d(TAG, "save db ok" + "; Login: " + Config.isLogin);
        previousUpdateTime = System.currentTimeMillis();

    }

    // toa do moi va cu cap nhat theo dia diem duoc chon trong updateLocation
    public void getOldNewLocation(Location currentLocation) {
        if (oldLocation.getLatitude() == 0 && oldLocation.getLongitude() == 0) {
            oldLocation.setLatitude(currentLocation.getLatitude());
            oldLocation.setLongitude(currentLocation.getLongitude());
        } else {
            oldLocation.setLatitude(newLocation.getLatitude());
            oldLocation.setLongitude(newLocation.getLongitude());
        }
        newLocation.setLatitude(currentLocation.getLatitude());
        newLocation.setLongitude(currentLocation.getLongitude());
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, LocationService.this);
    }

    @Override
    public void onDestroy() {
//        appPreferences.putFloat(PREF_DISTANCE, distance);
//        if (mGoogleApiClient.isConnected()) {
//            stopLocationUpdates();
//            Log.d(TAG, "stop location update");
//        }
//        mGoogleApiClient.disconnect();
//        Log.d(TAG, "onDestroy Distance " + distance);
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle connectionHint) throws SecurityException {
        Log.i(TAG, "Connected to GoogleApiClient");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mCurrentLocation == null) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mCurrentLocation = LocationServices.FusedLocationApi
                            .getLastLocation(mGoogleApiClient);
                    if (mCurrentLocation != null)
                        mLastUpdateTime = convertGPSTime(mCurrentLocation.getTime());
                    updateLocation();
                }
            }
        }).start();
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(final Location location) {
        getSignalStrengthAndNetworkType();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        if (networkType == "Wifi") {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    checkValidAfterLocationChange(location);

                }
            }).start();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mCurrentLocation = location;
                    mLastUpdateTime = convertGPSTime(mCurrentLocation.getTime());
                    updateLocation();
//					Log.d(TAG, "connected 3G!");
                }
            }).start();
        }
    }

    private synchronized void checkValidAfterLocationChange(Location location) {
        // previous code version (System.currentTimeMillis() - previousUpdateTime >= 60000)
        if (System.currentTimeMillis() - previousUpdateTime >= 50000) {
            Log.e(TAG, "Time is VALID!;Login: " + Config.isLogin);
            mCurrentLocation = location;
            mLastUpdateTime = convertGPSTime(mCurrentLocation.getTime());
            getSignalStrengthAndNetworkType();
            updateLocation();
        } else {
//			Log.e("onLocationChanged", "Time is INvalid. Get too much location!");
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    //khoang cach di duoc theo thoi gian thuc
    private float getUpdatedDistance() {
        distance = newLocation.distanceTo(oldLocation);
        return distance;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String convertGPSTime(long time) {
        Date d = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy HHmmss");
        return sdf.format(d);
    }

    public String getUser() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String user = Config.username;
        user = preferences.getString(Config.LAST_USER_LOGIN, "x");
        return user;
    }

    public String onGetTime() {
        String time = convertGPSTime(mCurrentLocation.getTime());
        return time;
    }

    public int getLevelBattery() {
        int level = 0;
        try {
            IntentFilter ifilter = new IntentFilter(
                    Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = this.registerReceiver(null, ifilter);
            level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            Log.i("PIN", "Pin: " + level + "%");
        } catch (Exception e) {
            e.printStackTrace();
            level = -1;
        }
        return level;
    }

    private void getSignalStrengthAndNetworkType() {
//        try {
        ConnectivityManager cm = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean is3g = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        boolean isWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();

        NetworkInfo Info = cm.getActiveNetworkInfo();
        if (Info == null || !Info.isConnectedOrConnecting()) {
            networkType = "Offline";
        } else {
            int netType = Info.getType();

            if (netType == ConnectivityManager.TYPE_WIFI) {
//				Log.i(TAG, "Wifi connection");
                networkType = "Wifi";
                wifiManager = (WifiManager) this
                        .getSystemService(Context.WIFI_SERVICE);
                signalStrength = wifiManager.getConnectionInfo().getRssi();
                // Need to get wifi strength
            } else if (netType == ConnectivityManager.TYPE_MOBILE) {
//                else {
                networkType = "3G";
//                signalStrength = -31;
                // Need to get differentiate between 3G/GPRS
                myListener = new MyPhoneStateListener();
                telephonyManager = (TelephonyManager) this
                        .getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(myListener,
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                stopPhoneStateListener();
            } else {
                networkType = "Unknow";
                // networkType = "-1";
                signalStrength = 0;
            }
        }
//        } catch (Exception e) {
//            networkType = "Unknow";
//            signalStrength = 0;
//            e.printStackTrace();
//        }
    }

    public double getLatitude() {
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public double getLongitude() {
        if (mCurrentLocation != null) {
            longitude = mCurrentLocation.getLongitude();
        }

        // return longitude
        return longitude;
    }

    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            if (signalStrength.isGsm()) {
                if (signalStrength.getGsmSignalStrength() != 99) {
                    singalStrength3G = (2 * signalStrength
                            .getGsmSignalStrength()) - 113;
                } else {
                    singalStrength3G = -120; // add tpq no signal
                }
            } else {
                singalStrength3G = signalStrength.getCdmaDbm();
            }
        }
    }

    public void stopPhoneStateListener() {
        if (telephonyManager != null) {
            telephonyManager.listen(myListener, PhoneStateListener.LISTEN_NONE);
            telephonyManager = null;
        }
    }


}
