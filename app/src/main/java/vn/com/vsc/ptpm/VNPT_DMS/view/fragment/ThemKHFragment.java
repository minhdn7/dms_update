package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.annotation.Email;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.Addr2Activity;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.GPSTracker;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utils;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.SQLiteData;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.ThongTinKhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TypeCompanyDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TypeCompany;

public class ThemKHFragment extends BaseFragment {

    Controller control;
    String note = null, name = null, id = null;
    List<TypeCompany> listTC = new ArrayList<>();
    List<String> listTypeCompany = new ArrayList<String>();
    ConvertFont cf = new ConvertFont();
    boolean isThemKH;
    SQLiteData sqlite;
    String noteOffline, idOffline;
    double longitude, latitude;
    int orderNow = 0;
    private EditText edit_maKH, edit_tenKH, edit_QL, edit_SDT, edit_fax,
            edit_dc, edit_mst, edit_note;

    @Email(message = "email nhập không đúng định dạng")
    private EditText edit_email;

    private ImageView iv_Location;
    private Spinner sp_LVKinhDoanh;
    private Button btn_HuyBoThemKH, btn_XacNhanThemKH;
    private CheckBox cb_dathangngay;
    private ProgressDialog pDialog;
    private ArrayList<TypeCompany> glstData;
    private getTypeCompanyTask getTypeCompanyTask;
    private AddCusProcess addCusProcess;
    private Activity activity;
    private int maxLengthSDT = 11;
    public ThemKHFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_themkh, container, false);

        // init UI
        edit_maKH = (EditText) view.findViewById(R.id.et_maKH);
        edit_tenKH = (EditText) view.findViewById(R.id.et_tenKH);
        edit_QL = (EditText) view.findViewById(R.id.et_tenQL);
        edit_SDT = (EditText) view.findViewById(R.id.et_sdt);
        edit_fax = (EditText) view.findViewById(R.id.et_fax);
        edit_email = (EditText) view.findViewById(R.id.et_email);
        edit_dc = (EditText) view.findViewById(R.id.et_diachi);
        edit_mst = (EditText) view.findViewById(R.id.et_mst);
        edit_note = (EditText) view.findViewById(R.id.et_ghichu);
        cb_dathangngay = (CheckBox) view.findViewById(R.id.cb_dat_hang_ngay);
        sp_LVKinhDoanh = (Spinner) view.findViewById(R.id.sp_LVKinhDoanh);
        btn_HuyBoThemKH = (Button) view.findViewById(R.id.btn_HuyBoThemKH);
        btn_XacNhanThemKH = (Button) view.findViewById(R.id.btn_XacNhanThemKH);
        iv_Location = (ImageView) view.findViewById(R.id.iv_Location);

        // filter limit lenght truong sdt



        edit_SDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 2) {
                    if(s.charAt(0) == '0' && s.charAt(1) == '9'){
                        maxLengthSDT = 10;
                    }else{
                        maxLengthSDT = 11;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                InputFilter[] filterArray = new InputFilter[1];
                filterArray[0] = new InputFilter.LengthFilter(maxLengthSDT);
                edit_SDT.setFilters(filterArray);
            }
        });


        // end

        //edit_maKH.setEnabled(false);
        if (NetworkType.internetIsAvailable(activity)) {
            iv_Location.setVisibility(View.VISIBLE);
        } else {
            iv_Location.setVisibility(View.GONE);
        }


        // init listener
        btn_HuyBoThemKH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Config.isOpenNew = true;
                activity.onBackPressed();
                //activity.getFragmentManager().popBackStack();
            }
        });

        btn_XacNhanThemKH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                createKhachHang();
            }
        });

        iv_Location.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NetworkType.internetIsAvailable(activity)) {
                    Intent i = new Intent(activity, Addr2Activity.class);
                    startActivityForResult(i, 1);
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Chức năng này chỉ hoạt động khi có internet", false);
                }
            }
        });
//		edit_tenKH.setText("Trường tx");
//		edit_QL.setText("Thúy ĐT");
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1) {
            if (data.hasExtra("address") && data.hasExtra("longitude") && data.hasExtra("longitude")) {
                edit_dc.setText(data.getStringExtra("address").toString());
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                Log.i("cuong", latitude + " va " + longitude);
            } else {
                control.showAlertDialog(activity, "Thông báo", "Không lấy được vị trí Long, Lat. Hãy thử lại", false);
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        control = new Controller(activity);
        isThemKH = false;

        try {
            if (listTypeCompany.isEmpty()) {
                listTypeCompany.add("Không chọn");

                // Get data Type Company
                if (control.isOnline(activity)) {
                    if (getTypeCompanyTask == null || getTypeCompanyTask.getStatus() == AsyncTask.Status.FINISHED) {
                        getTypeCompanyTask = new getTypeCompanyTask();
                        getTypeCompanyTask.execute(API_code.URL_TYPE_COMPANY);
                    }
                } else {
                    // Get data in DB Local
                    glstData = new TypeCompanyDAL(activity).getAll();
                    if (glstData.size() > 0) {
                        for (int i = 0; i < glstData.size(); i++) {
                            listTypeCompany.add(cf.getUTF8StringFromNCR(glstData.get(i).getName()));
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            activity, android.R.layout.simple_spinner_item, listTypeCompany);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sp_LVKinhDoanh.setAdapter(adapter);
                }
            } else {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        activity, android.R.layout.simple_spinner_item, listTypeCompany);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                sp_LVKinhDoanh.setAdapter(adapter);
            }

            // Get current location
            GPSTracker gpsTracker = new GPSTracker(activity);
            if (gpsTracker.canGetLocation()) {
                latitude = gpsTracker.getLatitude();
                longitude = gpsTracker.getLongitude();
            }
        } catch (Exception e) {
            mAF.writelog("Error ThemKHFragment onActivityCreated: " + e.toString() + "", activity, mAF.filelog);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void createKhachHang() {
        hideKeyboard();
        isPassedValidate = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);

        if(!edit_maKH.getText().toString().trim().equals("")){
            if(! edit_maKH.getText().toString().matches("[a-zA-Z0-9]*")){
                edit_maKH.setError("Mã khách hàng phải có định dạng tiếng việt không dấu");
                builder.setMessage("Mã khách hàng phải có định dạng tiếng việt không dấu");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_maKH.requestFocus();
                    }
                });
                builder.create().show();
                return;
            }
        }
        if(!edit_email.getText().toString().trim().equals("")){
            validator.validate();
            if(!isPassedValidate) {

                builder.setMessage("Email nhập sai định dạng");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_email.requestFocus();
                    }
                });
                builder.create().show();
                return;
            }
        }

        try {
            final ConvertFont conv = new ConvertFont();
            // Get data
            String maKH = edit_maKH.getText().toString().trim();
            //String tenKH = edit_tenKH.getText().toString();
            //String tenNgQL = edit_QL.getText().toString();
            String soDt = edit_SDT.getText().toString().trim();
            final String fax = edit_fax.getText().toString().trim();
            String email = edit_email.getText().toString().trim();
            //String dc = edit_dc.getText().toString();
            String maSoThue = edit_mst.getText().toString().trim();
            //note = edit_note.getText().toString();

            String tenKH = URLEncoder.encode(edit_tenKH.getText().toString().trim(), "UTF-8");
            String tenNgQL = URLEncoder.encode(edit_QL.getText().toString().trim(), "UTF-8");
            String dc = URLEncoder.encode(edit_dc.getText().toString().trim(), "UTF-8");
            note = URLEncoder.encode(edit_note.getText().toString().trim(), "UTF-8");
            name = tenKH;

            // Get Assign
            String end_id = "";
            if (sp_LVKinhDoanh.getSelectedItemPosition() != 0) {
                if (NetworkType.internetIsAvailable(activity)) {
                    end_id = listTC.get(sp_LVKinhDoanh.getSelectedItemPosition() - 1).getId();
                } else {
                    end_id = String.valueOf(glstData.get(sp_LVKinhDoanh.getSelectedItemPosition() - 1).getId());
                }
            }

            if (validateForm()) {
                ThemKH khachhang = new ThemKH(maKH, tenKH, tenNgQL, soDt, fax, email, dc, maSoThue, end_id,
                        note, latitude, longitude, 0);

                if (control.isOnline(activity)) {
                    if (addCusProcess == null || addCusProcess.getStatus() == AsyncTask.Status.FINISHED) {
                        addCusProcess = new AddCusProcess();
                        addCusProcess.execute(khachhang);
                    }
                } else {
                    // Add KhachHangNew into DB Local
                    final long resultInsert = new ThongTinKhachhangDAL(activity).add(khachhang);

                    if (resultInsert > 0) {
                        // Hien thi thong bao them thanh cong
                        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                        alert.setIcon(R.drawable.ic_success);
                        alert.setTitle("Thông báo");
                        alert.setMessage("Thêm Khách hàng Offline thành công!");
                        alert.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (cb_dathangngay.isChecked()) {
                                    Config.isCreateNewDHFromQLDH = false;
                                    Config.isNewCustomer = true;
                                    KhachHang kh = new KhachHang();
                                    Config.NAME_KH = name;
                                    String ten = conv.toDecimalNCR(edit_maKH.getText().toString()) + "_" + conv.toDecimalNCR(edit_tenKH.getText().toString());
                                    String id = "offline" + String.valueOf(resultInsert);
                                    kh.setName(ten);
                                    kh.setId(id);

                                    // Them ten khach hang moi offline
                                    TenKH tenKH = new TenKH(id, ten);
                                    long insertResult = new KhachhangDAL(activity).add(tenKH);
                                    Log.i("INSERT-TEN-KH-OFFLINE", insertResult > 0 ? "ID = " + insertResult : "Failure");

                                    Fragment fragment = new ThemDHListFragment(kh);
                                    if (fragment != null) {
                                        ((MainActivity) activity).replaceFragment(fragment);
                                    }
                                }
                                clearText();
                            }
                        });
                        alert.create().show();
                    } else {
                        control.showAlertDialog(activity, "Thông báo", "Thêm Khách hàng Offline không thành công", false);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearText() {
        edit_maKH.setText("");
        edit_tenKH.setText("");
        edit_QL.setText("");
        edit_SDT.setText("");
        edit_fax.setText("");
        edit_email.setText("");
        edit_dc.setText("");
        edit_mst.setText("");
        edit_note.setText("");
        cb_dathangngay.setChecked(false);
        edit_tenKH.requestFocus();
    }

    private boolean validateForm() {
        boolean result = false;

        String tenKH = edit_tenKH.getText().toString().trim();
        String tenNgQL = edit_QL.getText().toString().trim();
        String soDt = edit_SDT.getText().toString().trim();
        String diaChi = edit_dc.getText().toString().trim();
        String loaiKH = sp_LVKinhDoanh.getSelectedItem().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);

        if (tenKH.equals("")) {
            builder.setMessage("Tên Khách hàng không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit_tenKH.requestFocus();
                }
            });
            builder.create().show();
        } else if (tenNgQL.equals("")) {
            builder.setMessage("Tên Người quản lý không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit_QL.requestFocus();
                }
            });
            builder.create().show();
        } else if (soDt.equals("")) {
            builder.setMessage("Số điện thoại không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit_SDT.requestFocus();
                }
            });
            builder.create().show();
        } else if (diaChi.equals("")) {
            builder.setMessage("Địa chỉ không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edit_dc.requestFocus();
                }
            });
            builder.create().show();
        } else if (loaiKH.equals("Không chọn")) {
            builder.setMessage("Loại khách hàng phải được chọn!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        } else {
            result = true;
        }

        // 20170301: VietNH check thông tin số điện thoại
        if (soDt != null && soDt.trim().length() > 0) {
            if (soDt.trim().length() < 10 || soDt.trim().length() > 11) {
                result = false;
                builder.setMessage("Độ dài số điện thoại không hợp lệ!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_SDT.requestFocus();
                    }
                });
                builder.create().show();
            } else {
                if (!Utils.checkDauSoDT(soDt.trim())) {
                    result = false;
                    builder.setMessage("Đầu số điện thoại không chính xác!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            edit_SDT.requestFocus();
                        }
                    });
                    builder.create().show();
                }
            }
        }
        // End: VietNH

        return result;
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private class getTypeCompanyTask extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tải danh mục loại công ty, vui lòng chờ giây lát ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String s = "";
            try {
                s = control.getDataJSON(params[0], false);
            } catch (Exception e) {
                mAF.writelog("Error ThemKHFragment getTypeCompanyTask: " + e.toString() + ". kq=" + s, activity, mAF.filelog);
            }
            Log.e("TYPE-COMPANY", s);
            return s;
        }

        @SuppressWarnings("static-access")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //cuongtm them, co du lieu json thi moi khoi tao combo
            try {
                if (result.contains("name")) {

                    Type type = new TypeToken<List<TypeCompany>>() {
                    }.getType();
                    listTC = new Gson().fromJson(result, type);
                    // sqlite.queryTable("Delete from TypeCompany");

                    if (listTC.size() > 0) {
                        for (int i = 0; i < listTC.size(); i++) {
                            try {
                                String name = cf.getUTF8StringFromNCR(listTC.get(i).getName());
                                listTypeCompany.add(name);
                            } catch (Exception E) {
                                Toast.makeText(activity, "Error get type company" + E.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                activity, android.R.layout.simple_spinner_item, listTypeCompany);
                        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                        sp_LVKinhDoanh.setAdapter(adapter);
                    }
                }
            } catch (Exception e) {
                mAF.writelog("Error ThemKHFragment onPostExecute: " + e.toString(), activity, mAF.filelog);
            }

            if (pDialog != null) {
                dismissWithCheck(pDialog);
            }

        }
    }

    private class AddCusProcess extends AsyncTask<ThemKH, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang xử lý dữ liệu..");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(ThemKH... params) {
            String jsonKH = new Gson().toJson(params);
            jsonKH = control.subJSON(jsonKH);
            String s = "";
            try {
                String url = API_code.URL_THEMKH + "&note=" + note + "&json=" + jsonKH + "";
                Log.d("url getThemKH ", url);

                //String json = control.getDataJSON(control.convertURL(url), true);
                String json = control.jsonValues(url, true);
//				String json = "";
//				String url1;
//				String postParams = "";
//				url1 = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp";
//				postParams = "callback=?&api_code=ghetham_ttkh_themmoi"+ "&note=" + note + "&json=" + jsonKH + "";
//				json = mAF.sendPost(url1, postParams, activity);
                if (!json.equals("")) {
                    s = new JSONObject(json).getString("result");
                }
            } catch (JSONException e1) {
                mAF.writelog("Error ThemKHFragment doInBackground ThemKH: " + e1.toString(), activity, mAF.filelog);
                e1.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
            /*************************************************************************************
             * array res trả về có cấu trúc khi thành công: {"OK", Id khách hàng, Mã khách hàng}
             * ***********************************************************************************/
             if (!result.equals("")) {
                String[] res = result.split("\\|");
                if (res.length > 0 && res[0].equals("OK")) {
                    edit_maKH.setText(res[2]);
                    id = res[1];

					/*id = res[1];
                    if (API_code.latitude == 0 && API_code.longitude == 0) {
						showAlertDialog(
								activity,
								"Success",
								"Thêm khách hàng mới thành công ! \nHãy cập nhật vị trí ngay trên google map !",
								true);
						clearText();
					} else {
						new UpdateGPSTask().execute(new UpdateGPSKH(res[1], "", API_code.longitude, API_code.latitude, 1, ""));
					}*/

                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setIcon(R.drawable.ic_success);
                    alert.setTitle("Thông báo");
                    alert.setMessage("Thêm khách hàng mới thành công!");
                    alert.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (cb_dathangngay.isChecked()) {
                                mAF.Route_assign_id = "-2";
                                Config.isCreateNewDHFromQLDH = false;
                                Config.isNewCustomer = true;
                                KhachHang kh = new KhachHang();
//								Config.NAME_KH = name;
                                Config.NAME_KH = edit_maKH.getText().toString() + "_" + edit_tenKH.getText().toString();
                                kh.setName(name);
                                kh.setId(id);
                                Fragment fragment = new ThemDHListFragment(kh);
                                if (fragment != null) {
                                    ((MainActivity) activity).replaceFragment(fragment);
                                }
                            }
                            clearText();
                        }
                    });
                    alert.create().show();
                } else {
                    try {
                        ConvertFont conv = new ConvertFont();
                        String decodeResult = conv.getUTF8StringFromNCR(URLDecoder.decode(result, "UTF-8"));
                        control.showAlertDialog(activity, "Error", decodeResult, false);
                    } catch (Exception ex){
                        Log.e("Decode fail: ", ex.toString());
                    }
                }
            } else {
                control.showAlertDialog(activity, "Error", "Có lỗi trong quá trình kết nối. Vui lòng thử lại!", false);
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
