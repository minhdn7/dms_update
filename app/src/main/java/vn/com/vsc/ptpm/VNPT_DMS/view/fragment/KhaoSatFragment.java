package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhaoSatDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;

@SuppressLint("ValidFragment")
public class KhaoSatFragment extends Fragment {

    LinearLayout layoutData;
    String ORG_ID = "", ORG_CODE = "", ASSIGN_ID = "";
    ScrollView scrollViewData;
    TextView tvNoData;
    Button btn_capnhat, btn_refresh, btn_close;

    ProgressDialog pDialog;
    List<KhaoSat> arrItems;
    String[] a;

    Controller control;
    ConvertFont conv = new ConvertFont();

    private GetDSKHTask getDSKHTask;

    private Activity activity;

    public KhaoSatFragment() {
    }

    // value ="1" is checked, value="0" is uncheck
    public String getJSON_KS() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_khaosat&org_id=" + ORG_ID + "&org_code=" + ORG_CODE + "&assign_id="
                + ASSIGN_ID;
        return url;
    }

    public String getJSON_SEND_KS(String a[]) {
        String data = "";
        if (arrItems.size() > 0) {
            for (int i = 0; i < arrItems.size(); i++) {
                KhaoSat item = arrItems.get(i);

                if (item.getComponent_type().equals("checkbox")) {
                    data += "\",\"a_" + item.getId() + "\":\"" + a[i];
                }

                if (item.getComponent_type().equals("select")) {
                    String[] value = a[i].toString().split(";");
                    if (value.length > 0) {
                        data += "\",\"a_" + item.getId() + "\":\"" + value[0];

                        String text = "";
                        try {
                            text = URLEncoder.encode(value[1].toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        data += "\",\"a_" + item.getId() + "_dsp\":\"" + text;
                    }
                }

                if (item.getComponent_type().equals("text")) {
                    String text = "";
                    try {
                        text = URLEncoder.encode(a[i].toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    data += "\",\"a_" + item.getId() + "\":\"" + text;
                }
            }
        }
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_khaosat_capnhat&json=" + "{\"id\":\"" + ORG_ID + "\",\"code\":\"" + ORG_CODE
                + "\",\"assign\":\"" + ASSIGN_ID + data + "\"}";

        Log.i("send-ks-url", url);
        return url;
    }

    public KhaoSatFragment(String ORG_ID, String ORG_CODE, String ASSIGN_ID) {
        this.ORG_ID = ORG_ID;
        this.ORG_CODE = ORG_CODE;
        this.ASSIGN_ID = ASSIGN_ID;
    }

    private void AddItems(View view) {
        scrollViewData = (ScrollView) view.findViewById(R.id.scroll_view_data);
        tvNoData = (TextView) view.findViewById(R.id.tv_nodata);
        layoutData = (LinearLayout) view.findViewById(R.id.layout_bl);
        btn_capnhat = (Button) view.findViewById(R.id.btn_ks_capnhat);
        btn_refresh = (Button) view.findViewById(R.id.btn_ks_refresh);
        btn_close = (Button) view.findViewById(R.id.btn_ks_close);

        scrollViewData.setVisibility(View.GONE);
        tvNoData.setVisibility(View.GONE);
    }

    private void addBtn() {
        btn_capnhat.setOnClickListener(new HandleBtnClick());
        btn_refresh.setOnClickListener(new HandleBtnClick());
        btn_close.setOnClickListener(new HandleBtnClick());

    }

    private class HandleBtnClick implements OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_ks_capnhat:
                    sendKhaoSat();
                    break;
                case R.id.btn_ks_refresh:

                    if (NetworkType.internetIsAvailable(activity)) {
                        if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSKHTask = new GetDSKHTask();
                            getDSKHTask.execute();
                        }
                    } else {
                        getDataOffline();
                    }
                    break;
                case R.id.btn_ks_close:
                    Config.isOpenNew = false;
                    activity.onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

	/*
     * private String[] getCbState() { String a[] = { "0", "0", "0", "0", "0", "0" }; String a[] = { getItemState(cb_bienngang.isChecked()),
	 * getItemState(cb_biendung.isChecked()), getItemState(cb_bienvay.isChecked()), getItemState(cb_bienled.isChecked()),
	 * String.valueOf(spin_poster.getSelectedItemPosition() + 1), getItemState(cb_tutt.isChecked()) };
	 * 
	 * return a;
	 * 
	 * }
	 */

	/*
     * private String getItemState(boolean bool) { if (bool) { return "1"; } else return "0"; }
	 */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khaosat, container, false);
        AddItems(view);
        addBtn();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(activity);
        if (NetworkType.internetIsAvailable(activity)) {
            if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                getDSKHTask = new GetDSKHTask();
                getDSKHTask.execute();
            }
        } else {
            getDataOffline();
        }
    }

    private void getDataOffline() {
        try {
            arrItems = new KhaoSatDAL(activity).getByTuyenKhachHang(ORG_ID, ORG_CODE, ASSIGN_ID);
            displayData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetDSKHTask extends AsyncTask<Void, List<KhaoSat>, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Void... params) {
            String json_KS = control.jsonValues(getJSON_KS(), false);
            Type type_KS = new TypeToken<List<KhaoSat>>() {
            }.getType();
            arrItems = new ArrayList<KhaoSat>();
            if (!json_KS.equals("")) {
                try {
                    arrItems = (List<KhaoSat>) new Gson().fromJson(json_KS, type_KS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("ks_items_size", String.valueOf(arrItems.size()));
                publishProgress(arrItems);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(List<KhaoSat>... values) {
            super.onProgressUpdate(values);
            displayData();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }

    }

    @SuppressWarnings("static-access")
    private void displayData() {
        try {
            if (arrItems.size() > 0) {

                a = new String[arrItems.size()];
                layoutData.removeAllViews();
                for (int j = 0; j < arrItems.size(); j++) {

                    KhaoSat item = arrItems.get(j);
                    a[j] = item.getValue();
                    final int index = j;
                    if (item.getComponent_type().equals("checkbox")) {

                        CheckBox checkbox = new CheckBox(activity);
                        checkbox.setId(j);
                        checkbox.setText(conv.getUTF8StringFromNCR(item.getName()));
                        checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    a[index] = "1";
                                    // Toast.makeText(activity, "Checkbox[" + index + "] = 1", Toast.LENGTH_SHORT).show();
                                } else {
                                    a[index] = "0";
                                    // Toast.makeText(activity, "Checkbox[" + index + "] = 0", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        layoutData.addView(checkbox);

                        if (item.getValue().equals("1")) {
                            checkbox.setChecked(true);
                        } else {
                            checkbox.setChecked(false);
                        }
                    }

                    if (item.getComponent_type().equals("select")) {
                        LinearLayout layoutSelect = new LinearLayout(activity);
                        layoutSelect.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(activity);
                        title.setText(conv.getUTF8StringFromNCR(item.getName()));
                        layoutSelect.addView(title);
                        layoutSelect.setPadding(5, 0, 5, 0);

                        Spinner spinnerValue = new Spinner(activity);

                        ArrayList<String> glstDataSpinner = new ArrayList<String>();
                        String data = conv.getUTF8StringFromNCR(item.getJson_default_value());
                        final String[] arrData = data.split("\\|");
                        if (arrData.length > 0) {
                            for (int k = 0; k < arrData.length; k++) {
                                String[] arr = arrData[k].toString().split(";");
                                if (arr.length > 0) {
                                    glstDataSpinner.add(arr[1].toString().trim());
                                }
                            }

                            ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, glstDataSpinner);
                            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerValue.setAdapter(adapterSpinner);
                            layoutSelect.addView(spinnerValue);

                            if (item.getValue().equals("")) {
                                spinnerValue.setSelection(0);
                            } else {
                                if (Integer.parseInt(item.getValue()) > 0) {
                                    spinnerValue.setSelection(Integer.parseInt(item.getValue()) - 1);
                                } else {
                                    spinnerValue.setSelection(0);
                                }
                            }
                            spinnerValue.setOnItemSelectedListener(new OnItemSelectedListener() {

                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    a[index] = conv.getUTF8StringFromNCR(arrData[position].toString().trim());
                                    // Toast.makeText(activity, "Spinner[" + position + "] = " + arrData[position], Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
                        }
                        layoutData.addView(layoutSelect);
                    }

                    if (item.getComponent_type().equals("text")) {
                        LinearLayout layoutText = new LinearLayout(activity);
                        layoutText.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(activity);
                        title.setText(conv.getUTF8StringFromNCR(item.getName()));
                        layoutText.addView(title);

                        final EditText edittext = new EditText(activity);
                        edittext.setText(conv.getUTF8StringFromNCR(item.getValue()));
                        edittext.addTextChangedListener(new TextWatcher() {

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                a[index] = edittext.getText().toString();
                                // Toast.makeText(activity, "EditText[" + index + "] = " + edittext.getText().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        layoutText.addView(edittext);
                        layoutText.setPadding(5, 0, 5, 0);
                        layoutData.addView(layoutText);
                    }
                }
                scrollViewData.setVisibility(View.VISIBLE);
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                scrollViewData.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/*
     * private boolean SetCheckBox(String value) { if (value.equals("1")) return true; else return false; }
	 */

    private void sendKhaoSat() {
        if (NetworkType.internetIsAvailable(activity)) {
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang cập nhật thông tin khảo sát...");
            pDialog.setCancelable(false);
            pDialog.show();
            new Thread() {
                public void run() {
                    String json_send = control.jsonValues(getJSON_SEND_KS(a), false);
                    Log.i("json_send", json_send);
                    Message msg = Message.obtain();
                    Bundle b = new Bundle();
                    b.putString("json_send", json_send);
                    msg.setData(b);
                    msgHandler.sendMessage(msg);
                }
            }.start();

        } else {
            // Save to DB Local
            if (arrItems.size() > 0) {

                KhaoSatDAL khaosatDal = new KhaoSatDAL(activity);
                int count = 0;
                for (int i = 0; i < arrItems.size(); i++) {
                    KhaoSat item = arrItems.get(i);
                    item.setStatus(1);
                    item.setUpdated_by(Config.username);
                    try {
                        String date = Utilities.getDate(System.currentTimeMillis(), "dd/MM/yyyy HH:mm");
                        item.setUpdated_date(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Set value for item Khao sat
                    if (item.getComponent_type().equals("checkbox")) {
                        item.setValue(a[i]);
                    }

                    if (item.getComponent_type().equals("select")) {
                        String[] value = a[i].toString().split(";");
                        if (value.length > 0) {
                            item.setValue(value[0]);
                        }
                    }

                    if (item.getComponent_type().equals("text")) {
                        item.setValue(a[i]);
                    }

                    // Insert to DB Local
                    int resultUpdate = khaosatDal.update(item);
                    if (resultUpdate > 0) {
                        count++;
                        Log.i("UPDATE-KHAO-SAT-ITEM", "Success");
                    } else {
                        Log.i("UPDATE-KHAO-SAT-ITEM", "Failure");
                    }
                }

                if (arrItems.size() == count) {
                    control.showAlertDialog(activity, "Thông báo", "Cập nhật Khảo sát Offline thành công", true);
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Cập nhật Khảo sát Offline không thành công", false);
                }
            }
        }
    }

    private Handler msgHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String respon = "";
            try {
                respon = (String) msg.getData().getString("json_send");
                // loai bo dau ngoac vuong:
                respon = respon.substring(1, respon.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                respon = "";
            }
            String ok = "Gửi kết quả khảo sát thành công";
            String fail = "Gửi kết quả khảo sát thất bại";
            AnalysisResults(respon, ok, fail);
        }
    };

    public void AnalysisResults(String respon, String ok, String fail) {
        JSONObject obj = null;
        String result = "";
        try {
            obj = new JSONObject(respon);
            result = obj.optString("result");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (result.equals("0")) {
            dismissWithCheck(pDialog);
            Toast.makeText(activity, ok, Toast.LENGTH_SHORT).show();
        } else {
            dismissWithCheck(pDialog);
            Toast.makeText(activity, fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void dismissWithCheck(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) dialog.getContext()).getBaseContext();

                // if the Context used here was an activity AND it hasn't been finished or destroyed
                // then dismiss it
                if (context instanceof Activity) {

                    // Api >=17
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                            dismissWithTryCatch(dialog);
                        }
                    } else {

                        // Api < 17. Unfortunately cannot check for isDestroyed()
                        if (!((Activity) context).isFinishing()) {
                            dismissWithTryCatch(dialog);
                        }
                    }
                } else
                    // if the Context used wasn't an Activity, then dismiss it too
                    dismissWithTryCatch(dialog);
            }
            dialog = null;
        }
    }

    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }
}
