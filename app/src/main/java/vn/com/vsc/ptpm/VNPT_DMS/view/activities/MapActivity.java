package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KHParcel;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

@SuppressLint("ShowToast")
public class MapActivity extends FragmentActivity implements LocationListener, OnMyLocationChangeListener, OnMapReadyCallback {
    String TAG = MapActivity.class.getName();

    GoogleMap gMap;
    ProgressDialog pDialog;
    SQLiteData sqlite;
    Controller control = new Controller(this);

    private List<KhachHang> arr_KH;
    private ConvertFont conv = new ConvertFont();
    private HashMap<String, Marker> hmap_marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        //getActionBar().setHomeAsUpIndicator(R.drawable.ic_navigation_back);

        getData();
    }

    private void getData() {
        Bundle b = getIntent().getExtras();
        KHParcel object = b.getParcelable("DSKH");
        arr_KH = object.getArrList();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if (status != ConnectionResult.SUCCESS) {
            Dialog d = GooglePlayServicesUtil.getErrorDialog(status, this, 1);
            d.show();
        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            fm.getMapAsync(this);
//			googleMap.setOnMapLoadedCallback(new OnMapLoadedCallback() {
//
//				@Override
//				public void onMapLoaded() {
//					googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//					googleMap.getUiSettings().setZoomControlsEnabled(true);
//					googleMap.setMyLocationEnabled(true);
//					new GPSTracker(getApplicationContext());
//				}
//			});

//            hmap_marker = new HashMap<String, Marker>();
//            for (int i = 0; i < arr_KH.size(); i++) {
//                Location loca_KH = new Location("");
//                String str_la = arr_KH.get(i).getLattitude();
//                String str_lo = arr_KH.get(i).getLongtitude();
//                if (control.isNumber(str_lo) && control.isNumber(str_la)) {
//                    loca_KH.setLatitude(Double.parseDouble(str_la));
//                    loca_KH.setLongitude(Double.parseDouble(str_lo));
//                    DrawListKHLocation(loca_KH, arr_KH.get(i));
//                } else {
//                    break;
//                }
//            }
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
        gMap.setMyLocationEnabled(true);
        new GPSTracker(MapActivity.this);

        hmap_marker = new HashMap<String, Marker>();
        for (int i = 0; i < arr_KH.size(); i++) {
            Location loca_KH = new Location("");
            String str_la = arr_KH.get(i).getLattitude();
            String str_lo = arr_KH.get(i).getLongtitude();
            if (control.isNumber(str_lo) && control.isNumber(str_la)) {
                loca_KH.setLatitude(Double.parseDouble(str_la));
                loca_KH.setLongitude(Double.parseDouble(str_lo));
                DrawListKHLocation(loca_KH, arr_KH.get(i));
            } else {
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//		getData();
    }

	/*private class GetListKHLocation extends AsyncTask<UpdateGPSKH, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MapActivity.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(UpdateGPSKH... params) {
			String str_KH = new Gson().toJson(params[0]);
			String url = API_code.URL_UPDATE_NEW_KH + "&json=" + str_KH;
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
			pDialog.dismiss();
			if (result.equals("0")) {
				control.showAlertDialog(MapActivity.this, "Success", "Cập nhật vị trí thành công", true);
			} else {
				control.showAlertDialog(MapActivity.this, "Error", result, false);
			}
		}
	}*/

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
                    // Thong bao chua bat GPS
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
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
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(12).bearing(0).tilt(40)
                        .build();
                LatLng latLng = new LatLng(API_code.latitude, API_code.longitude);
                Marker m = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Vị trí của bạn !")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_my_location_blue_32)));
                m.showInfoWindow();

                Log.i(TAG, location.getLatitude() + "- " + location.getLongitude());
                gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            } else {
                Toast.makeText(mContext, "Bạn cần phải cho phép bật vị trí của bạn !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 0);
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            getCurrentLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    @SuppressWarnings("static-access")
    public void DrawListKHLocation(Location location, KhachHang kh) {
        if (location != null) {


            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            String sp = "Mã KH: " + conv.getUTF8StringFromNCR(kh.getCode())
                    + "\nTên KH: " + conv.getUTF8StringFromNCR(kh.getName())
                    + "\nĐịa chỉ: " + conv.getUTF8StringFromNCR(kh.getAddress())
                    + "\nCheck In: " + kh.getCheckin_time()
                    + "\nCheck Out: " + kh.getCheckout_time();
            if (API_code.URL_API_ROOT.contains("neo")) {
            } else {
                sp = sp + "\nKhoảng cách: " + mAF.getDistanceStr(kh.getDistance());
            }


            //+ "\nMã số thuế: " + kh.getTax_code();
            Log.i("cuong snippet kh", sp);
            if (gMap != null) {
                Marker m = gMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Phiếu giao: " + conv.getUTF8StringFromNCR(kh.getAssign_id()))
                        .snippet(sp)
                        .icon(BitmapDescriptorFactory.fromResource(getIcon(kh.getStatus().toString().trim()))));
                hmap_marker.put(kh.getCode(), m);
            }
            //googleMap.setInfoWindowAdapter(new InfoWindowAdap(getApplicationContext(), kh));

            try {
                //Adapter class (Có thể đưa vào package : adapter)
                class CustomInfoWindowAdapter implements InfoWindowAdapter {
                    private final View mContents;

                    CustomInfoWindowAdapter() {
                        mContents = getLayoutInflater().inflate(R.layout.info_kh_popup_map, null);
                    }

                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        render(marker, mContents);
                        return mContents;
                    }

                    private void render(Marker marker, View view) {
                        //int badge;
                        // Use the equals() method on a Marker to check for equals.  Do not use ==.
                        String title = marker.getTitle();
                        TextView titleUi = ((TextView) view.findViewById(R.id.title));
                        titleUi.setText(title);

                        String snippet = marker.getSnippet();
                        TextView snippetUi = ((TextView) view.findViewById(R.id.thongtin));
                        snippetUi.setText(snippet);
                    }
                }

                gMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

				/* Kích vào vị trí nào trên bản đồ thì hiển thị cửa sổ ở đấy.
                // Adding and showing marker while touching the GoogleMap
				googleMap.setOnMapClickListener(new OnMapClickListener() {
					
					@Override
					public void onMapClick(LatLng arg0) {
						// Clears any existing markers from the GoogleMap
						googleMap.clear();
						
						// Creating an instance of MarkerOptions to set position
						MarkerOptions markerOptions = new MarkerOptions();
						
						// Setting position on the MarkerOptions
						markerOptions.position(arg0);				
						
						// Animating to the currently touched position
						googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));	
						
						// Adding marker on the GoogleMap
						Marker marker = googleMap.addMarker(markerOptions);
						
						// Showing InfoWindow on the GoogleMap
						marker.showInfoWindow();
						
					}
				});
				*/

            } catch (Exception e) {
                Log.i("cuong error add Window", e.toString());
            }


        } else {
            Toast.makeText(getApplicationContext(), "Bạn cần phải cho phép bật vị trí của bạn !", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0);
        }
    }

    private int getIcon(String key) {
        int i = R.drawable.poi;
        if (key.equals("HT")) {
            i = R.drawable.checked_in;
        } else if (key.equals("DH")) {
            i = R.drawable.ordered;
        } else {
            i = R.drawable.poi;
        }
        return i;
    }

    @Override
    public void onLocationChanged(Location location) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public void onMyLocationChange(Location location) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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