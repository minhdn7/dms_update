package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import io.fabric.sdk.android.Fabric;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.Calendar;
import java.util.List;

//import io.fabric.sdk.android.Fabric;
import vn.com.vsc.ptpm.VNPT_DMS.BuildConfig;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.LocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.SendDataLocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DoAction;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.LoginModel;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.LayMaDonViImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.UserTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.ILayMaDonViView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IUserTMXView;
import vn.com.vsc.ptpm.VNPT_DMS.vn.BootReceiver;

public class Login extends Activity implements ILayMaDonViView {
    private static final String TAG = "LOGIN";
    private static String VERSION = "30"; //huhu_v2
    private static boolean VERSION_OK;
//    private UserTMXImpl checkUserTMXPresenter;
    Controller control = new Controller(this);
    SQLiteData sqlite;
    boolean isRememberAccount;
    View.OnClickListener buttonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.d(TAG, v.getId() + "");
            switch (v.getId()) {
                // case R.id.btnRetrofit:
                // mBus.post(new LoginRequest("baoht", "test"));
                // break;
            }
        }
    };
    private Button btn_Login;
    private TextView txt_username, txt_password;
    private ProgressDialog pDialog;
    private CheckBox cb_saveAcc;
    private ImageView imglogo;
    private SharedPreferences prefs;
    private Editor editor;
    private boolean isFirstTimeActitivyCreated = true;
    private static boolean isActivityRunning;
    private CheckVersionAsynctask checkVersionAsynctask;
    private getParameterTask getParameterTask;
    private LoginTask loginTask;
    private Context context;
    private LayMaDonViImpl layMaDonViPresenter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
//        Fabric.with(this, new Crashlytics());
      setContentView(R.layout.activity_login);
        context = this;
        VERSION = BuildConfig.VERSION_CODE + "";
        if (checkVersionAsynctask == null || checkVersionAsynctask.getStatus() == AsyncTask.Status.FINISHED) {
            checkVersionAsynctask = new CheckVersionAsynctask();
            checkVersionAsynctask.execute();
        }

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        layMaDonViPresenter = new LayMaDonViImpl(this);
        btn_Login = (Button) findViewById(R.id.btnLogin);
        txt_username = (TextView) findViewById(R.id.txtUsername);
        txt_password = (TextView) findViewById(R.id.txtPassword);
        cb_saveAcc = (CheckBox) findViewById(R.id.cb_login);
        imglogo = (ImageView) findViewById(R.id.imgLogo);
        btn_Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
     //Crashlytics.getInstance();
    sqlite = new SQLiteData(Login.this);
        sqlite = new SQLiteData(Login.this);
      sqlite.createTable();

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        getAcount(sqlite);

        // txt_username.setText("his_vnpt_unt");
        // txt_password.setText("123456");
        cb_saveAcc.setChecked(true);

        // Cường thêm ngày 09/05/2016
        getScreenInDP();
        if (mAF.dpHeight <= mAF.maxHeight) {
            imglogo.setVisibility(View.GONE);
            mAF.isPhone = true;
        }
        mAF.getLatLongCurrent(Login.this);
        Log.i("cuong latlong", mAF.latCurrent + " " + mAF.longCurrent);
        mAF.isDistance = 0;
        mAF.ghilog = true;
        mAF.init4Log(Login.this, mAF.filelog);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sqlite.isOpen()) {
            sqlite.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void doLogin() {
//		hideKeyboard();
        if (checkVersionAsynctask == null || checkVersionAsynctask.getStatus() == AsyncTask.Status.FINISHED) {
            checkVersionAsynctask = new CheckVersionAsynctask();
            checkVersionAsynctask.execute();
        }
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.USER_HAS_JUST_LOGIN, true);
        editor.commit();


        isRememberAccount = cb_saveAcc.isChecked();
        final String username = txt_username.getText().toString();
        final String password = txt_password.getText().toString();

        if (control.isOnline(Login.this) && VERSION_OK) {
            Config.isOnlineMode = true;
            // Ham Login cu
            if (loginTask == null || loginTask.getStatus() == AsyncTask.Status.FINISHED) {
                loginTask = new LoginTask(this);
                loginTask.execute(new String[]{username, password});
            }
//			LocationHelper locationHelper = new LocationHelper(Login.this);
//			locationHelper.removeAll();
            /** LOGIN - MOI **/
            // pDialog = new ProgressDialog(Login.this);
            // pDialog.setMessage("Đang kiểm tra thông tin đăng nhập...");
            // pDialog.setCancelable(false);
            // pDialog.show();
            // mBus.post(new LoginRequest(username, password));
        } else if (!control.isOnline(Login.this)) {
//        } else {
            Config.isOnlineMode = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("Không có dữ liệu internet");
            builder.setMessage("Bạn có muốn làm việc ở chế độ Offline ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Đồng ý",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (validateForm()) {
                                String[] s = sqlite.getAccount();
                                if (s != null && s.length > 0) {
                                    if (s[0].equals(username)
                                            && s[1].equals(password)) {
                                        if (!isRememberAccount) {
                                            sqlite.queryTable("Update TaiKhoan set isSave ="
                                                    + isRememberAccount
                                                    + " where username="
                                                    + username);

                                            // cuongtm sửa, chỉ lưu 1
                                            // tài khoản login cuối cùng
                                            // trên máy
                                            // sqlite.queryTable("Update TaiKhoan set isSave ="
                                            // + isRememberAccount +
                                            // " where username=" +
                                            // username);

                                        }
                                        Config.isLogin = true;
                                        Config.username = username;
                                        Intent intent = new Intent(Login.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        control.showAlertDialog(
                                                Login.this,
                                                "Error",
                                                "Tài khoản hoặc mật khẩu không đúng!",
                                                false);
                                    }
                                } else {
                                    control.showAlertDialog(
                                            Login.this,
                                            "Error",
                                            "Không tồn tại tài khoản này dưới thiết bị!",
                                            false);
                                }
                            }

                        }
                    });
            builder.setNegativeButton("Không",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private boolean validateForm() {
        boolean result = false;

        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);

        if (username.equals("")) {
            builder.setMessage("Tên đăng nhập không được để trống!");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txt_username.requestFocus();
                        }
                    });
            builder.create().show();
        } else if (password.equals("")) {
            builder.setMessage("Mật khẩu không được để trống!");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txt_password.requestFocus();
                        }
                    });
            builder.create().show();
        } else {
            result = true;
        }

        return result;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
        isActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
        isActivityRunning = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CONCRETE", "onResume");
        if (isFirstTimeActitivyCreated) {
            isFirstTimeActitivyCreated = false;
            Log.d("CONCRETE", "Activity is created first time");
        } else {
            Log.d("CONCRETE", "Start auto login");
            init();
        }
        isActivityRunning = true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void doStartService() {
        try {
            Log.d(TAG, "Main Start Service");
            Calendar cal = Calendar.getInstance();
            AlarmManager am = (AlarmManager) this
                    .getSystemService(Activity.ALARM_SERVICE);

            long interval = 1000 * 10; // milliseconds
            Intent intent = new Intent(Login.this, LocationService.class);
//			bindService(intent, conn, Context.BIND_AUTO_CREATE);
            PendingIntent pendingIntent = PendingIntent.getService(Login.this,
                    0, intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    interval, pendingIntent);

            Log.d(TAG, "alarm repeat service");
            mAF.writelog("doStartService: set alarm repeate ok ",
                    getApplicationContext(), mAF.filelog);
        } catch (Exception ex) {
            mAF.writelog("Error doStartService: " + ex.toString(),
                    getApplicationContext(), mAF.filelog);
        }
    }

    public void doStartServiceSendData() {
        try {
            Log.d(TAG, "Main Start Service Send Data");
            Calendar cal = Calendar.getInstance();
            AlarmManager am = (AlarmManager) this
                    .getSystemService(Activity.ALARM_SERVICE);

            long interval = BootReceiver.INTERVAL; // milliseconds
            Intent intent = new Intent(Login.this, SendDataLocationService.class);
            PendingIntent pendingIntent = PendingIntent.getService(Login.this,
                    0, intent, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    interval, pendingIntent);

            Log.d(TAG, "alarm repeat service send data");
            mAF.writelog("doStartService: set alarm repeate send data ok ",
                    getApplicationContext(), mAF.filelog);
        } catch (Exception ex) {
            mAF.writelog("Error doStartService: " + ex.toString(),
                    getApplicationContext(), mAF.filelog);
        }
    }

    // Cuong bổ sung, lấy ra độ cao, rộng của màn hình (đo bằng dp)
    private void getScreenInDP() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        mAF.density = getResources().getDisplayMetrics().density;
        mAF.dpHeight = outMetrics.heightPixels / mAF.density;
        mAF.dpWidth = outMetrics.widthPixels / mAF.density;
    }

    public String getURL_CheckVersion() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=version_new&version=" + VERSION + "&type=" + "1";
        Log.e(TAG + " url-checkversion", url);
        return url;
    }

    private void doSaveLoginInfo(LoginModel loginModel) {
        if (loginModel != null) {
            if (loginModel.getUser_name().equals("")) {
                control.showAlertDialog(Login.this, "Error",
                        loginModel.getApi_error(), false);
            } else {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                Editor editor1 = sharedPreferences.edit();
                editor1.putString(Config.LAST_USER_LOGIN, txt_username
                        .getText().toString());
                editor1.commit();

                if (getParameterTask == null || getParameterTask.getStatus() == AsyncTask.Status.FINISHED) {
                    getParameterTask = new getParameterTask();
                    getParameterTask.execute();
                }

                if (isRememberAccount) {

                    // 19-05-2016 cuongtm thêm để xóa các tài khoản khác,
                    // chỉ lưu lại 1 tài khoản
                    String q = "";
                    q = "delete from TaiKhoan where username <> '"
                            + loginModel.getUser_name() + "'";
                    sqlite.queryTable(q);

					/*
                     * //Insert 1 ban ghi vao bang checkin de test dong bo len
					 * //q =
					 * "insert into checkin(id, code ,assign, lng , lat , checktype , time ) values"
					 * ; //q = q +
					 * "('6285','KH27', '144','105.8590347','21.0020898','0','19052016 162855')"
					 * ; //sqlite.queryTable(q); //DatabaseHandler db1;
					 * //db1.onOpen("quanlynhanvienkinhdoanh.db"); Checkin chk =
					 * new Checkin(); chk.setId("36"); chk.setCode("00022");
					 * chk.setAssign("372"); chk.setLng("105.85562896728516");
					 * chk.setLat("21.002832412719727"); chk.setChecktype("0");
					 * chk.setTime("22052016 120000"); CheckinDAL chkdal = new
					 * CheckinDAL(getBaseContext()); chkdal.add(chk); //
					 */

                    String query = "Insert or ignore into TaiKhoan(username,password,isSave,"
                            + "user_name,language_id,active_shop_code,active_shop_id,mobile_theme_id) values "
                            + "('"
                            + txt_username.getText().toString()
                            + "','"
                            + txt_password.getText().toString()
                            + "','"
                            + isRememberAccount
                            + "','"
                            + loginModel.getUser_name()
                            + "','"
                            + loginModel.getLanguage_id()
                            + "','"
                            + loginModel.getActive_shop_code()
                            + "','"
                            + loginModel.getActive_shop_id()
                            + "','"
                            + loginModel.getMobile_theme_id() + "');";
                    sqlite.queryTable(query);
                } else {
                    sqlite.queryTable("Delete from TaiKhoan");
                }
                Config.isLogin = true;
                Config.username = txt_username.getText().toString();
                Config.password = txt_password.getText().toString();

                // Start service nhan cong
                if (!isMyServiceRunning(LocationService.class)) {
                    doStartService();
                    Log.d(TAG, "first run service get location");
                }
//				doStartService();
                if (!isMyServiceRunning(SendDataLocationService.class)) {
                    doStartServiceSendData();
                    Log.d(TAG, "first run service send data");
                }
                layMaDonViPresenter.getMaDonVi();

//                Intent intent = new Intent(Login.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
            }
        } else {
            control.showAlertDialog(Login.this, "Error",
                    "Không kết nối được tới máy chủ. Vui lòng thử lại!", false);
        }
    }

    public void init() {
        prefs = getSharedPreferences(Config.PREF_LOGIN_STATUS, MODE_PRIVATE);
        if (prefs.contains(Config.PREF_KEY_LOGIN_STATUS)) {
            int loginStatus = prefs.getInt(Config.PREF_KEY_LOGIN_STATUS, 0);
            if (loginStatus == 1) {
                // Toast.makeText(getApplicationContext(), "loginStatus == 1",
                // Toast.LENGTH_LONG).show();
                doLogin();
            } else {
                editor = prefs.edit();
                editor.putInt(Config.PREF_KEY_LOGIN_STATUS, 0);
                editor.commit();
            }
        }
    }

    private void getAcount(SQLiteData sqlite) {
        String[] s = sqlite.getAccount();
        if (s != null) {
            if (s[2].equals("true")) {
                cb_saveAcc.setChecked(true);
                txt_username.setText(s[0]);
                txt_password.setText(s[1]);
            }
        }
    }

//    @Override
//    public void onUserTMXSuccess(Object object) {

//
//        Intent intent = new Intent(Login.this, MainActivity.class);
//        try{
//            Gson gson = new Gson();
//            JsonElement jsonElement = gson.toJsonTree(object);
//            Type listType = new TypeToken<List<CommonResponse>>(){}.getType();
//            List<CommonResponse> response = (List<CommonResponse>) gson.fromJson(String.valueOf(object), listType);
////            List<CommonResponse> response = gson.fromJson(jsonElement, CommonResponse.class);
//            String s = response.get(0).getResult().toLowerCase();
//            intent.putExtra("IS_USER_TMX", s);
//        }catch (Exception ex){
//            Log.d("Parse error:", ex.toString());
//        }
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

//    @Override
//    public void onUserTMXError(Object object) {
//        control.showAlertDialog(
//                Login.this,
//                "Thông báo",
//                "Không lấy được tham số hệ thống. Vui lòng Login lại!",
//                false);
//    }

    @Override
    public void onLayMaDonViSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            Type listType = new TypeToken<List<CommonResponse>>(){}.getType();
            List<CommonResponse> response = (List<CommonResponse>) gson.fromJson(String.valueOf(object), listType);
            if(response.get(0).getResult().toLowerCase().equals(Config.maGlab)){
                Config.MaDonVi = Config.maGlab;
            }else if(response.get(0).getResult().toLowerCase().equals(Config.maTMX)){
                Config.MaDonVi = Config.maTMX;
            }
            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayMaDonViError(Object object) {
        control.showAlertDialog(
                Login.this,
                "Thông báo",
                "Không lấy được mã đơn vị. Vui lòng Login lại!",
                false);
    }

    // cuongtm thêm : lay tham so he thong
    private class getParameterTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            String url = API_code.URL_DOACT;
            String json1 = "";

            DoAction dact = new DoAction(
                    "",
                    "getpara",
                    "app_parameter",
                    "value ",
                    "",
                    " code in ('MONTH_STOP_CUSTOMER','CHECKIN_DISTANCE_LIMIT') order by to_number(value) asc");
            try {
                json1 = "&json=" + new Gson().toJson(dact);
            } catch (Exception e) {
                e.printStackTrace();
            }
            url = url + json1;
            String json_result = "";
            try {
                json_result = control
                        .getDataJSON(control.convertURL(url), true);
                // json_result = control.getDataJSON(url, true);
            } catch (Exception ex) {
                Log.d("error cuong doaction ", url + ". Kq=" + ex.toString());
            }
            Log.d("cuong get doaction ", url + ". Kq=" + json_result);
            publishProgress(json_result);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if (!values[0].equals("")) {
                try {
                    String kq = values[0].toString();
                    if (kq.toLowerCase().contains("value")) {
                        // String result =
                        // jsonObject.getString("promotion_amount");
                        // Do | là ký tự đặc biệt,cần đưa thêm \\ vào
                        String[] a = values[0].toString().split(",");
                        if (a[0].trim().toLowerCase().contains("value")) {
                            JSONObject jsonObject1 = new JSONObject(a[0]);
                            String kq1 = jsonObject1.getString("value");
                            if (mAF.isNumeric(kq1)) {
                                mAF.nStopCust = Float.parseFloat(kq1);
                            } else {
                                mAF.nStopCust = 0;
                            }
                            Log.d("cuong mAF.nStopCust=", mAF.nStopCust + "");

                            if (a.length >= 1) {
                                if (a[1].trim().toLowerCase().contains("value")) {
                                    jsonObject1 = new JSONObject(a[1]);
                                    kq1 = jsonObject1.getString("value");
                                    if (mAF.isNumeric(kq1)) {
                                        mAF.maxDistance = Float.parseFloat(kq1);
                                        // mAF.maxDistance = 200000;
                                    } else {
                                        mAF.maxDistance = 0;
                                    }
                                    Log.d("cuong mAF.maxDistance=",
                                            mAF.maxDistance + "");
                                }
                            }

                        } else {
                            control.showAlertDialog(
                                    Login.this,
                                    "Thông báo",
                                    "Không lấy được tham số hệ thống. Vui lòng Login lại!",
                                    false);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                control.showAlertDialog(Login.this, "Error",
                        "Không kết nối được tới máy chủ. Vui lòng thử lại!",
                        false);
            }

            super.onProgressUpdate(values);
        }
    }

    private class LoginTask extends AsyncTask<String, LoginModel, LoginModel> {
        Context context;

        public LoginTask(Context mContext) {
            this.context = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Đang kiểm tra thông tin đăng nhập...");
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected LoginModel doInBackground(String... params) {
            String url = API_code.URL_LOGIN + "&username=" + params[0]
                    + "&password=" + params[1];
            Log.d("cuong login", "url_login=" + url);
            String result = control.jsonValues(url, true);
            Log.d("cuong login", url + ". kq=" + result + "");
            LoginModel account = new LoginModel();
            try {
                account = new Gson().fromJson(result, LoginModel.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return account;
        }

        @Override
        protected void onPostExecute(LoginModel result) {
            super.onPostExecute(result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (Login.this.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                    return;
                }
            } else {
                if (Login.this.isFinishing()) {
                    return;
                }
            }
            if (pDialog != null) {
                pDialog.dismiss();
            }
            if (result != null) {
                doSaveLoginInfo(result);
            } else {
                Log.e("ERROR", "There is something wrong!");
            }
        }
    }

    private class CheckVersionAsynctask extends AsyncTask<Void, String, String> {

        @Override
        protected String doInBackground(Void... params) {
            String s = "";
            try {
                String json = control.jsonValues(getURL_CheckVersion(), false);
                Log.e(TAG + " json-checkversion", json);
                if (json.contains("TRUE")) {
                    s = "TRUE";
                } else if (json.contains("FALSE")) {
                    s = "FALSE";
                } else {
                    s = "UNKNOWN";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (!result.equals("")) {
                if (result == "FALSE") {
                    VERSION_OK = false;
                    if (control.isOnline(Login.this)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setIcon(R.drawable.alert_dialog_warning);
                        alert.setTitle("Hệ thống đã có phiên bản mới");
                        alert.setMessage("Vui lòng cập nhật phiên bản mới để sử dụng ổn định nhất!");
                        alert.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                            }
                        });
                        alert.setCancelable(false);
                        alert.create();
                        //kiểm tra trước khi hiển thị dialog
                        if (!((Activity) context).isFinishing()) {
                            alert.show();
                        }
                    } else {
                        //khong co mang cho dang nhap che do offline
                    }
                } else {
                    VERSION_OK = true;
                }
            }
        }
    }
}
