package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.AddrActivity;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.GPSTracker;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TTKHDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.ThongTinKhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenKhachHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TTKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;

@SuppressLint("ValidFragment")
public class TTKHFragment extends Fragment {
    private KhachHang kh = new KhachHang();
    private ThemKH khm;
    private ProgressDialog pDialog;
    private Controller control;
    private ConvertFont conv = new ConvertFont();
    private EditText et_maKH, et_tenKH, et_ngQL, et_tel, et_mobile, et_fax, et_email, et_diachi, et_ghichu, et_mst;
    private ImageButton btn_dc;
    private Button btn_dong, btn_cn;
    private TTKH ttkh;
    private String note = null;
    private int _id;
    private Activity activity;

    private GetDSKHTask getDSKHTask;
    private UpdateCusProcess updateCusProcess;
    private OnClickListener OnClickBtn = new OnClickListener() {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_dc:
                    Intent i = new Intent(activity, AddrActivity.class);
                    i.putExtra("info_KH", kh);
                    // startActivityForResult(i, 1);
                    startActivity(i);
                    Log.d("cuong", "Start activity Address");
                    break;
                case R.id.btn_dong:
                    Config.isOpenNew = false;
                    activity.onBackPressed();
                    break;
                case R.id.btn_cn:
                    UpdateInfoKhachHang();
                    break;
            }
        }
    };

    public TTKHFragment() {
    }

    public TTKHFragment(KhachHang kh) {
        this.kh = kh;
    }

    public String getURL_TTKH() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_ttkh&org_id=" + kh.getId() + "&org_code=" + kh.getCode() + "&assign_id="
                + kh.getAssign_id();
        Log.i("url-TTKH", url);
        return url;
    }

    public void AddItems(View view) {
        et_maKH = (EditText) view.findViewById(R.id.et_maKH);
        et_tenKH = (EditText) view.findViewById(R.id.et_tenKH);
        et_ngQL = (EditText) view.findViewById(R.id.et_ngQL);
        et_tel = (EditText) view.findViewById(R.id.et_tel);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);
        et_fax = (EditText) view.findViewById(R.id.et_fax);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_diachi = (EditText) view.findViewById(R.id.et_diachi);
        et_ghichu = (EditText) view.findViewById(R.id.et_ghichu);
        et_mst = (EditText) view.findViewById(R.id.et_mst);
        btn_dc = (ImageButton) view.findViewById(R.id.btn_dc);
        btn_dong = (Button) view.findViewById(R.id.btn_dong);
        btn_cn = (Button) view.findViewById(R.id.btn_cn);
        btn_dc.setOnClickListener(OnClickBtn);
        btn_dong.setOnClickListener(OnClickBtn);
        btn_cn.setOnClickListener(OnClickBtn);
        // et_maKH.setTextColor(Color.CYAN);
    }

    private boolean validateForm() {
        boolean result = false;

        String tenKH = et_tenKH.getText().toString();
        String tenNgQL = et_ngQL.getText().toString();
        // String soDt = et_mobile.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);

        if (tenKH.equals("")) {
            builder.setMessage("Tên Khách hàng không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_tenKH.requestFocus();
                }
            });
            builder.create().show();
        } else if (tenNgQL.equals("")) {
            builder.setMessage("Tên Người quản lý không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_ngQL.requestFocus();
                }
            });
            builder.create().show();
        }
        /*
         * else if (soDt.equals("")) { builder.setMessage("Số điện thoại không được để trống!"); builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) { et_mobile.requestFocus(); } }); builder.create().show(); }
		 */
        else {
            result = true;
        }

        return result;
    }

    private void UpdateInfoKhachHang() {
        try {
            String id = kh.getId();
            String maKH = et_maKH.getText().toString();
            String tel = et_tel.getText().toString();
            String mobile = et_mobile.getText().toString();
            String fax = et_fax.getText().toString();
            String email = et_email.getText().toString();
            String mst = et_mst.getText().toString();
            String assign = kh.getAssign_id();

            // String tenKH = conv.GetNCRDecimalString(et_tenKH.getText().toString());
            // String tenNgQL = conv.GetNCRDecimalString(et_ngQL.getText().toString());
            // String diachi = conv.GetNCRDecimalString(et_diachi.getText().toString());
            // note = conv.GetNCRDecimalString(et_ghichu.getText().toString());

            String tenKH = URLEncoder.encode(et_tenKH.getText().toString(), "UTF-8");
            String tenNgQL = URLEncoder.encode(et_ngQL.getText().toString(), "UTF-8");
            String diachi = URLEncoder.encode(et_diachi.getText().toString(), "UTF-8");
            note = URLEncoder.encode(et_ghichu.getText().toString(), "UTF-8");

            double latitude = 0;
            double longitude = 0;

            GPSTracker gps = new GPSTracker(activity);
            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }

            if (validateForm()) {

                if (NetworkType.internetIsAvailable(activity)) {
                    ThemKH kh = new ThemKH(id, maKH, tenKH, tenNgQL, fax, mobile, tel, email, diachi, mst, assign, note, latitude, longitude);
                    if (updateCusProcess == null || updateCusProcess.getStatus() == AsyncTask.Status.FINISHED) {
                        updateCusProcess = new UpdateCusProcess();
                        updateCusProcess.execute(kh);
                    }
                } else {
                    // Update thong tin offline
                    ThongTinKhachhangDAL dal = new ThongTinKhachhangDAL(activity);
                    boolean updateLocalSuccess = false;

                    if (khm != null && khm.get_id() != 0) { // Da ton tai _id
                        ThemKH kh = null;
                        if (khm.getId() == null) {
                            _id = khm.get_id();
                            kh = new ThemKH(_id, id, maKH, tenKH, tenNgQL, fax, mobile, tel, email, diachi, mst, assign, note, latitude, longitude, 0);
                        } else {
                            _id = khm.get_id();
                            kh = new ThemKH(_id, id, maKH, tenKH, tenNgQL, fax, mobile, tel, email, diachi, mst, assign, note, latitude, longitude, 1);
                        }
                        int resultUpdateOffline = dal.update(kh);
                        if (resultUpdateOffline > 0) {
                            updateLocalSuccess = true;
                            control.showAlertDialog(activity, "Thông báo", "Cập nhật thông tin Offline thành công", true);
                        } else {
                            control.showAlertDialog(activity, "Thông báo", "Cập nhật thông tin Offline thất bại", false);
                        }

                    } else { // Them moi vao bang thongtinkhachhang trong DB Local
                        ThemKH kh = new ThemKH(id, maKH, tenKH, tenNgQL, fax, mobile, tel, email, diachi, mst, assign, note, latitude, longitude, 1);
                        long resultInsertOffline = dal.add(kh);
                        if (resultInsertOffline > 0) {
                            updateLocalSuccess = true;
                            control.showAlertDialog(activity, "Thông báo", "Cập nhật thông tin Offline thành công", true);
                        } else {
                            control.showAlertDialog(activity, "Thông báo", "Cập nhật thông tin Offline thất bại", false);
                        }
                    }

                    // Update thong tin KH trong bang tuyenkhachhang
                    if (updateLocalSuccess) {
                        String name = URLDecoder.decode(tenKH, "UTF-8");
                        String address = URLDecoder.decode(diachi, "UTF-8");
                        long resultUpdate = new TuyenKhachHangDAL(activity).updateInfoTuyenKH(id, name, address);
                        Log.i("INFO-TUYEN-KH-STATUS", resultUpdate > 0 ? "Success" : "Failure");
                    }
                }
            } // #End if validate

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
         * if (resultCode == 1 && data.hasExtra("addressGM")) { et_diachi.setText(data.getStringExtra("addressGM")); }
		 */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_kh, container, false);
        AddItems(view);

        return view;
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            control = new Controller(activity);
            // Neu khach hang khong phai ngoai tuyen -> Ko cho sua truong dia chi
            if (kh != null) {
                if (kh.getAssign_id() != null && !kh.getAssign_id().equals("-1")) {
                    et_diachi.setEnabled(false);
                    et_diachi.setTextColor(getResources().getColor(android.R.color.darker_gray));
                }
            }

            if (NetworkType.internetIsAvailable(activity)) {
                // Lay thong tin tu Server
                if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                    getDSKHTask = new GetDSKHTask();
                    getDSKHTask.execute();
                }
            } else {
                // Lay thong tin tu DB Local
                if (kh != null) {

                    if (kh.getId() != null && !kh.getId().equals("")) {
                        ttkh = new TTKHDAL(activity).getById(kh.getId());
                        if (ttkh != null) {
                            et_maKH.setText(ttkh.getCode());
                            et_tenKH.setText(conv.getUTF8StringFromNCR(ttkh.getName()));
                            et_ngQL.setText(conv.getUTF8StringFromNCR(ttkh.getManager()));
                            et_tel.setText(ttkh.getTel());
                            et_mobile.setText(ttkh.getMobile());
                            et_fax.setText(ttkh.getFax());
                            et_email.setText(ttkh.getEmail());
                            et_mst.setText(ttkh.getTax_code());
                            et_diachi.setText(conv.getUTF8StringFromNCR(ttkh.getAddress()));
                            et_ghichu.setText(conv.getUTF8StringFromNCR(ttkh.getNote()));
                        }
                        khm = new ThongTinKhachhangDAL(activity).getById(kh.getId());
                    } else { // Lay thong tin khach hang moi offline
                        _id = Integer.parseInt(kh.getDistance());
                        khm = new ThongTinKhachhangDAL(activity).getBy_Id(_id);
                    }

                    // Update lai ttkh voi thong tin duoc cap nhat offline
                    if (khm != null && khm.get_id() != 0) {
                        et_tenKH.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(khm.getName(), "UTF-8")));
                        et_ngQL.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(khm.getMgr(), "UTF-8")));
                        et_tel.setText(khm.getTel());
                        et_mobile.setText(khm.getMobile());
                        et_fax.setText(khm.getFax());
                        et_email.setText(khm.getEmail());
                        et_mst.setText(khm.getTax());
                        et_diachi.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(khm.getAddr(), "UTF-8")));
                        et_ghichu.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(khm.getNote(), "UTF-8")));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class GetDSKHTask extends AsyncTask<Void, TTKH, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(activity.getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String json_ttkh = control.jsonValues(getURL_TTKH(), true);
            TTKH tt_kh = null;
            try {
                tt_kh = (TTKH) new Gson().fromJson(json_ttkh, TTKH.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            publishProgress(tt_kh);
            return null;
        }

        @SuppressWarnings("static-access")
        @Override
        protected void onProgressUpdate(TTKH... values) {
            super.onProgressUpdate(values);
            ttkh = values[0];
            if (ttkh != null) {
                et_maKH.setText(ttkh.getCode());
                et_tenKH.setText(conv.getUTF8StringFromNCR(ttkh.getName()));
                et_ngQL.setText(conv.getUTF8StringFromNCR(ttkh.getManager()));
                et_tel.setText(ttkh.getTel());
                et_mobile.setText(ttkh.getMobile());
                et_fax.setText(ttkh.getFax());
                et_email.setText(ttkh.getEmail());
                et_mst.setText(ttkh.getTax_code());
                et_diachi.setText(conv.getUTF8StringFromNCR(ttkh.getAddress()));
                et_ghichu.setText(conv.getUTF8StringFromNCR(ttkh.getNote()));
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                dismissWithCheck(pDialog);
        }
    }

    public class UpdateCusProcess extends AsyncTask<ThemKH, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(ThemKH... params) {
            // Tao doi tuong Json
            // String jsonKH = ThemKH.toJSON(params[0]);
            String jsonKH = new Gson().toJson(params[0]);
            String url = API_code.URL_UPDATE_KH + "&note=" + note + "&json=" + jsonKH + "";
            Log.d("update_kh", url);
            // String urlConvert = control.convertURL(url);
            // Log.d("url convert", urlConvert);

            String s = null;
            try {
                s = new JSONObject(control.getDataJSON(url, true)).getString("result");
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
                control.showAlertDialog(activity, "Thông báo", "Cập nhập thông tin thành công!", true);
            } else {
                control.showAlertDialog(activity, "Thông báo", result, false);
            }
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
