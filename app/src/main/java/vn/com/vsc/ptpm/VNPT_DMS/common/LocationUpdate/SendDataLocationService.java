package vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ReadWriteFile;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.LoginModel;

/**
 * Created by ToiHV on 18-Sep-16.
 */
public class SendDataLocationService extends Service {
    private static final String TAG = SendDataLocationService.class.getSimpleName();

	int MAX_RECORDS_UPDATE_PER_TIMES = 20;

    boolean isFirstSend = true;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendDataToServer();
            }
        }).start();

        return START_STICKY;
    }

    private void sendDataToServer() {
        ReadWriteFile file = new ReadWriteFile();
        try {
            mAF.ghilog = true;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String formattedDate = df.format(c.getTime());

            Log.i(TAG, "*");
            Log.i(TAG, "START_SERVICE: " + formattedDate);

            // Bootload service
            SharedPreferences prefs = getSharedPreferences(
                    Config.PREF_LOGIN_STATUS, MODE_PRIVATE);
            int loginStatus = prefs.getInt(Config.PREF_KEY_LOGIN_STATUS, 0);
            Log.i(TAG, "loginStatus=" + loginStatus + "- " + Config.isLogin);

            String jsonLocation = "";
            ArrayList<String> listJsonLocation = new ArrayList<String>();
            int count = 0, interval = 1;

            SharedPreferences.Editor editor = prefs.edit();
            if (!prefs.contains(Config.PREF_KEY_INTERVAL) || loginStatus == 0) {
                editor.putInt(Config.PREF_KEY_INTERVAL, 0);
                editor.commit();
            }

            interval = prefs.getInt(Config.PREF_KEY_INTERVAL, 0);
//            mAF.writelog("interval=" + interval + " - Div5: "
//                            + (interval % TIME_PERIOD_SEND_DATA)
//                            + ". loginStatus=" + loginStatus, getApplicationContext(),
//                    mAF.filelog);

            if (loginStatus == 1) {
                interval++;
                editor.putInt(Config.PREF_KEY_INTERVAL, interval);
                editor.commit();
            }

            // Gui du lieu ve Server
            // ************************************************************
//            if (loginStatus == 1
//                    && (interval % TIME_PERIOD_SEND_DATA) == 0) {
            if (loginStatus == 1) {
                Log.d(TAG, "SEND_LOCATION_DATA_PROCESS");

                // Xoa du lieu khong hop le, da gui xong
                LocationHelper dal1 = new LocationHelper(this);
                dal1.deleteSended();

                // neu co mang, gui toan bo du lieu location len server
                if (NetworkType.internetIsAvailable(getApplicationContext())) {
                    Log.d(TAG, "Internet_available!");

                    // try {
                    // lay toan bo danh sach du lieu hop le
                    LocationHelper dal = new LocationHelper(this);
                    ArrayList<LocationModel> listInfo = dal.getAccuracyData();
                    int totalRecords = listInfo.size();
                    Log.d(TAG, "TotalRecords=" + totalRecords);
                    // send data to server
                    if (listInfo != null || totalRecords > 0) {

                        // String jsonLocation = new
                        // Gson().toJson(listInfo);
                        // them N ban ghi co dinh vao 1 list, va gui lan
                        // luot
                        for (int i = 0; i < totalRecords; i++) {
                            jsonLocation += listInfo.get(i).toStringData();
                            count++;
                            if (count == MAX_RECORDS_UPDATE_PER_TIMES) {
//                                Toast.makeText(getApplication(), "Hello", Toast.LENGTH_SHORT).show();
                                listJsonLocation.add(jsonLocation);
                                jsonLocation = "";
                                count = 0;
                            }
                        }

                        if (!jsonLocation.equals("")) {
                            listJsonLocation.add(jsonLocation);
                        }
                        // gui toi da 5 ban ghi 1 lan
                        int sizeUpload = listJsonLocation.size();
                        Log.d(TAG, "TotalListJSON=" + sizeUpload);
                        if (sizeUpload > 0) {
                            for (int j = 0; j < sizeUpload; j++) {
                                String jsonLocat = listJsonLocation.get(j);
                                if (NetworkType
                                        .internetIsAvailable(getApplicationContext())) {
                                    Log.e("CONCRETE", LocationService.class.getName() + " sendDataToServer");

                                    Controller control = new Controller(this);
                                    String json = "";
                                    try {
                                        Controller controller = new Controller(this);
                                        json = controller.getDataJSON(
                                                controller.convertURL(Config.URL_UPDATE_DEVICE_INFO + "&json="
                                                        + jsonLocat + ""), true);
//                                        file.writeToFile(json, getApplicationContext());
                                    } catch (Exception e) {
                                        json = "Error SendLocationTask: " + e.toString();
                                    }

                                    isFirstSend = false;
                                    Log.d(TAG, "JSON_RESPONSE=" + json);
                                    JSONObject jsonObj = new JSONObject(json);
                                    String result = "";
                                    // if session lost
                                    if (jsonObj.has("error_id")) {
                                        // reLogin to server and send
                                        // request again
                                        if (doLogin(control)) {
                                            try {
                                                Controller controller = new Controller(this);
                                                json = controller.getDataJSON(
                                                        controller.convertURL(Config.URL_UPDATE_DEVICE_INFO + "&json="
                                                                + jsonLocat + ""), true);

//                                                file.writeToFile(json, getApplicationContext());
                                            } catch (Exception e) {
                                                json = "Error SendLocationTask: " + e.toString();
                                            }
                                            Log.d(TAG,
                                                    "JSON_REUPDATE_RESPONSE="
                                                            + json);
                                            jsonObj = new JSONObject(json);
                                        }
                                    }
                                    if (jsonObj.has("result")) {
                                        result = jsonObj.getString("result");
                                        if (result.equals("1")) {
                                            // update record send_status = 1
                                            int updateResult = dal
                                                    .updateSendStatus(listInfo);
                                            Log.d(TAG, "updateResult = "
                                                    + updateResult);
                                        }
                                    }
                                    Log.d(TAG, "SERVER RESPONSE=" + result);
                                }
                            }
                        }
                    }
                } else {
                    Log.e(TAG, "Internet unavailable!");
                }
                editor.putInt(Config.PREF_KEY_INTERVAL, 0);
                editor.commit();
            }
            // Ket thuc gui du lieu ve server
            // ************************************************************

            Log.i(TAG, "STOP_SERVICE");

        } catch (Exception ex) {
            mAF.writelog("Error onStartCommand service. " + ex.toString(),
                    getApplicationContext(), mAF.filelog);
        }
    }

    public boolean doLogin(Controller control) {
        try {
            SQLiteData sqlite = new SQLiteData(getApplicationContext());
            String[] s = sqlite.getAccount();
            if (sqlite.isOpen()) {
                sqlite.close();
            }

            String resultAcc;

            try {
                Controller c = new Controller(this);
                String url1;
                String postParams = "";
                url1 = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp";
                postParams = "callback=?&api_code=ghetham_tt_nvkd&username="
                        + s[0] + "&password=" + s[1];
                resultAcc = mAF.sendPost(url1, postParams, getApplicationContext());

            } catch (Exception e) {
                resultAcc = "Error LoginTask service" + e.toString();
            }
            mAF.writelog("LoginTask service: " + resultAcc, getApplicationContext(),
                    mAF.filelog);

            Log.d(TAG, "RELOGIN_INFO=" + resultAcc);

            LoginModel account = new Gson().fromJson(resultAcc,
                    LoginModel.class);

            if (account != null) {
                if (!account.getUser_name().equals("")) {
                    mAF.writelog("doLogin service: OK",
                            getApplicationContext(), mAF.filelog);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            mAF.writelog("Error doLogin service: " + e.toString(),
                    getApplicationContext(), mAF.filelog);
            return false;
        }
    }

//    public class LoginTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String[] s) {
//            String json = "";
//            try {
//                Controller c = new Controller();
//                String url1;
//                String postParams = "";
//                url1 = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp";
//                postParams = "callback=?&api_code=ghetham_tt_nvkd&username="
//                        + s[0] + "&password=" + s[1];
//                json = mAF.sendPost(url1, postParams, getApplicationContext());
//
//            } catch (Exception e) {
//                json = "Error LoginTask service" + e.toString();
//            }
//            mAF.writelog("LoginTask service: " + json, getApplicationContext(),
//                    mAF.filelog);
//            return json;
//        }
//
//        @Override
//        protected void onPostExecute(String message) {
//            // process message
//        }
//
//    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
