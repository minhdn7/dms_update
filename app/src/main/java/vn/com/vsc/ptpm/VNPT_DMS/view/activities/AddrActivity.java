package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;
import vn.com.vsc.ptpm.VNPT_DMS.dao.UpdateLocationDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.UpdateGPSKH;

public class AddrActivity extends FragmentActivity implements OnMapReadyCallback {
    private String TAG = AddrActivity.class.getSimpleName();
    EditText et_diachiKH;
    Button btn_UpdateLocaltion, btn_Thoat;
    Button btn_MyLocation, btn_LocationKH;
    GoogleMap gMap;
    ProgressDialog pDialog;
    Controller control = new Controller(this);
    SQLiteData sqlite;
    KhachHang kh;

    protected LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addr);

        Log.d("cuong", "Before Google Play Services Available check");

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        checkGPS();

        sqlite = new SQLiteData(AddrActivity.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        Log.d("cuong", "Google Play Services Available: " + status);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);

        if (status != ConnectionResult.SUCCESS) {
            Dialog d = GooglePlayServicesUtil.getErrorDialog(status, this, 1);
            d.show();
        } else {
            // init UI
            et_diachiKH = (EditText) findViewById(R.id.et_diachiKH);
            btn_UpdateLocaltion = (Button) findViewById(R.id.btn_UpdateLocaltion);
            btn_MyLocation = (Button) findViewById(R.id.btn_my_location);
            btn_LocationKH = (Button) findViewById(R.id.btn_location_kh);
            btn_Thoat = (Button) findViewById(R.id.btn_Thoat);
            //et_diachiKH.setText(getIntent().getStringExtra("addressKH"));
            et_diachiKH.setTextColor(getResources().getColor(android.R.color.darker_gray));

            // Get info KH exits
            Bundle b = getIntent().getExtras();
            kh = b.getParcelable("info_KH");
            ConvertFont cf = new ConvertFont();
            String address = cf.getUTF8StringFromNCR(kh.getAddress());
            et_diachiKH.setText(address);

            btn_Thoat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            btn_UpdateLocaltion.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    UpdateGPSKH data = new UpdateGPSKH(kh.getId(), kh.getCode(), API_code.longitude,
                            API_code.latitude, 1, kh.getAssign_id(), (int) (System.currentTimeMillis() / 1000));
                    Log.i(TAG, API_code.longitude + "-" + API_code.latitude);

                    if (NetworkType.internetIsAvailable(AddrActivity.this)) {
                        new UpdateGPSTask(AddrActivity.this).execute(data);

                    } else {
                        // Offline
                        if (kh.getId() == null) { // Khach hang them moi offline nen ko co id
                            control.showAlertDialog(AddrActivity.this, "Thông báo", "Cập nhật vị trí Offline thành công", true);
                        } else {
                            long resultInsert = new UpdateLocationDAL(AddrActivity.this).add(data);
                            if (resultInsert > 0) {
                                control.showAlertDialog(AddrActivity.this, "Thông báo", "Cập nhật vị trí Offline thành công", true);
                            } else {
                                control.showAlertDialog(AddrActivity.this, "Thông báo", "Cập nhật vị trí Offline không thành công", false);
                            }
                        }
                    }

                }
            });

            btn_LocationKH.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getLocationKhachHang();
                }
            });


            btn_MyLocation.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    GPSTracker gpsTracker = new GPSTracker(AddrActivity.this);
                    gpsTracker.getLocation();
                }
            });


            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAddr);
            fm.getMapAsync(this);
//			googleMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
//
//				@Override
//				public void onMapLoaded() {
//					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//					googleMap.getUiSettings().setZoomControlsEnabled(true);
//					googleMap.setMyLocationEnabled(false);
//					new GPSTracker(AddrActivity.this);
//				}
//			});

            getLocationKhachHang();
        }
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
        new GPSTracker(AddrActivity.this);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 0) {
            new GPSTracker(getApplicationContext());
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        if (et_diachiKH != null)
            i.putExtra("addressGM", et_diachiKH.getText().toString());
        setResult(1, i);
        finish();
        super.onBackPressed();
    }

    public void checkGPS() {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddrActivity.this);
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

    @SuppressWarnings("static-access")
    private void getLocationKhachHang() {
        try {
            Location location_KH = new Location("");
            String latitude = kh.getLattitude();
            String longitude = kh.getLongtitude();
            if (control.isNumber(latitude) && control.isNumber(longitude)) {
                location_KH.setLatitude(Double.parseDouble(latitude));
                location_KH.setLongitude(Double.parseDouble(longitude));

                String address = new ConvertFont().getUTF8StringFromNCR(kh.getAddress());
                et_diachiKH.setText(address);
                getCurrentLocationKhachHang(location_KH, address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // cap nhat dia chi gps
    private class UpdateGPSTask extends AsyncTask<UpdateGPSKH, Void, String> {
        Context context;

        public UpdateGPSTask(Context mContext) {
            this.context = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Updating GPS...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(UpdateGPSKH... params) {
            String str_KH = new Gson().toJson(params[0]);
            String url = API_code.URL_UPDATE_NEW_KH + "&json=" + str_KH;
            Log.i("URL", url);
            String s = null;
            try {
                s = new JSONObject(control.getDataJSON(control.convertURL(url), true)).getString("result");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (AddrActivity.this.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                    return;
                }
            } else {
                if (AddrActivity.this.isFinishing()) {
                    return;
                }
            }
            if (pDialog != null) {
                pDialog.dismiss();
            }

            if (result == null) {
                //Có lỗi trong quá trình xử lý dữ liệu, gán bằng tin nhắn lỗi
                result = "Có lỗi trong quá trình cập nhật vị trí";
            }

            if (result.equals("0")) {
                control.showAlertDialog(context, "Success", "Cập nhật vị trí thành công", true);
            } else {
                control.showAlertDialog(context, "Error", result, false);
            }
        }
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
                        if (ActivityCompat.checkSelfPermission(AddrActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddrActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");

                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                API_code.latitude = location.getLatitude();
                                API_code.longitude = location.getLongitude();
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
                                    API_code.latitude = location.getLatitude();
                                    API_code.longitude = location.getLongitude();
                                    onLocationChanged(location);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void getCurrentLocation(Location location) {
            if (location != null) {
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location
                                .getLongitude())).zoom(12).bearing(0).tilt(40)
                        .build();

                LatLng latLng = new LatLng(API_code.latitude, API_code.longitude);
                Marker m = gMap
                        .addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Vị trí của bạn")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_2)));
                m.showInfoWindow();
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                /*Geocoder geo = new Geocoder(mContext);
                try {
					List<Address> addr = new ArrayList<Address>();
					addr = geo.getFromLocation(API_code.latitude, API_code.longitude, 5);
					if (addr != null && addr.size() > 0) {
						if (et_diachiKH.getText().toString().equals("")) {
							String address = addr.get(0).getAddressLine(0)
									+ ", " + addr.get(0).getAddressLine(1)
									+ ", " + addr.get(0).getAddressLine(2)
									+ ", " + addr.get(0).getAddressLine(3);
							et_diachiKH.setText(address);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}*/
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


    public void getCurrentLocationKhachHang(Location location, String address) {
        if (location != null) {
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(), location.getLongitude()), 13));

			/*CameraPosition cameraPosition = new CameraPosition.Builder()
            .target(new LatLng(location.getLatitude(), location
					.getLongitude())).zoom(15).bearing(90).tilt(40)
					.build();*/

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Marker m = gMap
                    .addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Vị trí của Khách hàng")
                            .snippet(address)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_blue_32)));
            m.showInfoWindow();
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            Toast.makeText(this, "Bạn cần phải cho phép bật vị trí của bạn !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
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
