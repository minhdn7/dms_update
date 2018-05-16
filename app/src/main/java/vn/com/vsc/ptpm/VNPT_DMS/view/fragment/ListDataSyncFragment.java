package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.DataDetailSync;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.dao.CheckinDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.InfoDeviceDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangMoiDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.KhachHangMoiEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;

public class ListDataSyncFragment extends ListFragment {

    private TextView mTextViewStatus;
    private Button mButtonSyncAll;

    private ProgressDialog mProgressDialog;
    private Controller control;
    private SyncAllDataTask syncAllDataTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_sync, container, false);

        mTextViewStatus = (TextView) view.findViewById(R.id.text_view_status);
        mButtonSyncAll = (Button) view.findViewById(R.id.button_sync);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(getActivity());
        // get data
        String[] listItems = getResources().getStringArray(R.array.list_item_menu_sync);

        // initiate the listadapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        getListView().setAdapter(adapter);

        // set listener button download
        mButtonSyncAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (syncAllDataTask == null || syncAllDataTask.getStatus() == AsyncTask.Status.FINISHED) {
                    syncAllDataTask = new SyncAllDataTask();
                    syncAllDataTask.execute();
                }
            }
        });

        getStatusUploadDataSync();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Position: 0 - Ds Khach hang moi, 1 - DS Don hang moi, 2 - Lich su vi tri, 3 - Checkin/out

        Intent intentDetail = new Intent(getActivity(), DataDetailSync.class);
        intentDetail.putExtra(Config.KEY_EXTRA_TYPE_DATA_SYNC, position);
        startActivity(intentDetail);
    }

    private void getStatusUploadDataSync() {
        long date = getDateUploadDataSync();
        if (date > 0) {
            try {
                mTextViewStatus.setText("Dữ liệu đã được đồng bộ: " + Utilities.getDate(date, "dd/MM/yyyy HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            mTextViewStatus.setText("Dữ liệu chưa đồng bộ");
        }
    }

    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getResources().getString(R.string.load_data));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                Toast.makeText(getActivity(), R.string.back_again_exit, Toast.LENGTH_SHORT).show();
            }
        });
        mProgressDialog.show();
    }

    private void closeProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }


    private boolean saveDateUploadDataSync() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Config.KEY_DATE_UPLOAD_DATA_SYNC);
        editor.putLong(Config.KEY_DATE_UPLOAD_DATA_SYNC, System.currentTimeMillis());

        return editor.commit();
    }

    private long getDateUploadDataSync() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        long date = sp.getLong(Config.KEY_DATE_UPLOAD_DATA_SYNC, 0);
        return date;
    }

    ////////////////////////ASYNTASK /////////////////////////////
    private class SyncAllDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            syncKhachHangNew();
            syncHistoryLocation();
            syncInfoCheckin();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            closeProgressDialog();

            saveDateUploadDataSync();
            getStatusUploadDataSync();
        }
    }

    private void syncKhachHangNew() {
        try {
            ArrayList<KhachHangMoiEntity> glstKhachHangMoi = new KhachhangMoiDAL(getActivity()).getAll();
            if (glstKhachHangMoi.size() > 0) {
                for (int i = 0; i < glstKhachHangMoi.size(); i++) {
                    KhachHangMoiEntity item = glstKhachHangMoi.get(i);

                    String jsonKH = new Gson().toJson(item);
                    String result = null;
                    try {
                        String json = control.getDataJSON(control.convertURL(API_code.URL_THEMKH + "&note="
                                + item.getNote() + "&json=" + jsonKH + ""), true);

                        Log.i("url getThemKH ", API_code.URL_THEMKH + "&note=" + item.getNote() + "&json=" + jsonKH + "");
                        result = new JSONObject(json).getString("result");

                        String[] res = result.split("\\|");
                        if (result != null && res[0].equals("OK")) {
                            Log.i("SYNC-KHACH-HANG-NEW", "OK");

                            int delete = new KhachhangMoiDAL(getActivity()).delete(String.valueOf(item.getId()));
                            Log.i("DELETE-KHACH-HANG-NEW", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("SYNC-KHACH-HANG-NEW", "ERROR");
                        }

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncHistoryLocation() {
        try {
            ArrayList<InfoDeviceEntity> glstInfo = new InfoDeviceDAL(getActivity()).getAll();
            if (glstInfo.size() > 0) {
                for (int i = 0; i < glstInfo.size(); i++) {
                    InfoDeviceEntity item = glstInfo.get(i);

                    // Send data location to Server
                    String jsonLocation = new Gson().toJson(item);
                    String json = control.getDataJSON(control.convertURL(Config.URL_UPDATE_LOCATION + "&json=" + jsonLocation + ""), true);
                    String result = new JSONObject(json).getString("result");

                    if (result.equals("0")) {
                        Log.i("STATUS_UPADTE", "Update Location Success");
                        int delete = new InfoDeviceDAL(getActivity()).delete(item.getTime());
                        Log.i("DELETE-HISTORY-LOCATION", delete > 0 ? "Success" : "Failure");
                    } else {
                        Log.i("STATUS_UPADTE", "Update Location Failure");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncInfoCheckin() {
        try {
            ArrayList<Checkin> glstCheckin = new CheckinDAL(getActivity()).getAll();
            if (glstCheckin.size() > 0) {
                for (int i = 0; i < glstCheckin.size(); i++) {
                    Checkin item = glstCheckin.get(i);

                    String jsonCheckin = new Gson().toJson(item);
                    String result = new JSONObject(control.jsonValues(Config.GetUrlCheckin(jsonCheckin), true)).getString("result");
                    if (result.equals("0")) {
                        Log.i("STATUS-CHECKIN", "Update info checkin success");
                        int delete = new CheckinDAL(getActivity()).delete(item.getTime());
                        Log.i("DELETE-INFO-CHECKIN", delete > 0 ? "Success" : "Failure");
                    } else {
                        Log.i("STATUS-CHECKIN", "Update info checkin failure");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
