package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DSDHListAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.OnCustomClickListener;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DatHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDeleteDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.NhaCungCapDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.SanPhamDonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TrangThaiDonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DonHangDeleteEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.SanPhamDonHangEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangParam;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinAdapNCC;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;

@SuppressLint("ValidFragment")
public class LichSuFragment extends ListFragment implements OnItemClickListener {

    // LogCat tag
    private static final String TAG = LichSuFragment.class.getSimpleName();
    private static final String URL = "URL_DSDH";
    private final int SUA_DH = 2;
    private final int THEM_DH = 1;
    private final int DAT_HANG = 3;
    private final int LICH_SU = 4;
    private int FRAGMENT_STATE = 0;

    // item view
    private CheckBox cb_search;
    private KhachHang KH;
    private String ID = "";
    Controller control;
    Button btn_search, btn_close;
    private ImageButton btn_ngayBD, btn_ngayKT, btn_ngayCH;

    private ProgressDialog pDialog;
    private int pageCount = 0;
    private int totalPages = 0;
    private List<DonHang> arrItems = null;
    private DSDHListAdapter adapter;
    private TextView txt_title;
    private LinearLayout layout_adv_search;
    private Boolean add_layout_search = true;

    private boolean isAddSpinner = true;
    private Spinner spin_tt, spin_ncc;
    private List<TenKH> arr_spinNCC;
    private List<TenKH> arr_spinTT;

    private EditText et_MaDH, et_TenKH, et_tuNgay, et_denNgay, et_loaiSP, et_giaTri, et_ngayYCCH, et_nguoiLap, et_search;
    private String valueSpinNCC, valueSpinTT = "1";
    private int sttSpinNCC, sttSpinTT;
    private String total_rec = "";
    private GetListItemsTask getListItemsTask;
    // cuongtm them
    private String idDonhang = ""; // Id đơn hàng, khi kích vào một đơn hàng để xóa, sửa

    private Activity activity;

    private String getDefJsonObj(DonHangParam dh) {
        String jsonResult = "";
        Type itemType = new TypeToken<DonHangParam>() {
        }.getType();
        jsonResult = new Gson().toJson(dh, itemType);
        return ("json=" + jsonResult);
    }

    private String getURL_DSDH(DonHangParam dh) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_ds&pageno=" + pageCount + "&pagerec=10&" + getDefJsonObj(dh);
        Log.i(URL, url);
        return url;
    }

    private String getURL_NopNor(DonHangParam dh) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_ds&pageno=-1&pagerec=10&" + getDefJsonObj(dh);
        Log.i(URL, url);
        return url;
    }

    private String getURL_NCC() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_dsncc";
        return url;
    }

    private String getURL_TT() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_dstt";
        return url;
    }

    public LichSuFragment() {
    }

    public LichSuFragment(KhachHang KH, int Fragment_state) {
        this.KH = KH;
        this.ID = KH.getId();
        this.FRAGMENT_STATE = Fragment_state;
    }

    public void init() {
        pageCount = 0;
        totalPages = 0;
        arrItems = new ArrayList<DonHang>();
        adapter = null;
    }

    private void AddItems(final View view, final LayoutInflater inflater) {
        layout_adv_search = (LinearLayout) view.findViewById(R.id.layout_adv_search);
        spin_ncc = (Spinner) view.findViewById(R.id.spin_ncc);
        spin_tt = (Spinner) view.findViewById(R.id.spin_tt);
        btn_ngayBD = (ImageButton) view.findViewById(R.id.btn_ngayBD);
        btn_ngayKT = (ImageButton) view.findViewById(R.id.btn_ngayKT);
        btn_ngayCH = (ImageButton) view.findViewById(R.id.btn_ngayCH);
        btn_ngayBD.setOnClickListener(new HandleBtnClick());
        btn_ngayKT.setOnClickListener(new HandleBtnClick());
        btn_ngayCH.setOnClickListener(new HandleBtnClick());
        btn_search = (Button) view.findViewById(R.id.btnSearch);
        btn_search.setOnClickListener(new HandleBtnClick());
        btn_close = (Button) view.findViewById(R.id.btnClose);
        et_MaDH = (EditText) view.findViewById(R.id.txtMdh);
        et_giaTri = (EditText) view.findViewById(R.id.txtGT);
        et_TenKH = (EditText) view.findViewById(R.id.txtKH);
        et_ngayYCCH = (EditText) view.findViewById(R.id.txtNgayYCCH);
        et_tuNgay = (EditText) view.findViewById(R.id.txtNgayBD);
        et_denNgay = (EditText) view.findViewById(R.id.txtNgayKT);
        et_loaiSP = (EditText) view.findViewById(R.id.txtLoaiSP);
        et_nguoiLap = (EditText) view.findViewById(R.id.txtNgLap);
        et_search = (EditText) view.findViewById(R.id.txtSearch);
        txt_title = (TextView) view.findViewById(R.id.title);
        txt_title.setText("");
        cb_search = (CheckBox) view.findViewById(R.id.cb_loaiTK);
        cb_search.setChecked(false);

        cb_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cb_search.isChecked() && add_layout_search) {
                    layout_adv_search.setVisibility(View.VISIBLE);
                    add_layout_search = false;
                    et_search.setEnabled(false);
                } else if (!cb_search.isChecked() && !add_layout_search) {
                    layout_adv_search.setVisibility(View.GONE);
                    add_layout_search = true;
                    et_search.setEnabled(true);

                }
            }
        });
        btn_close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Config.isOpenNew = false;
                activity.onBackPressed();
            }
        });

        // cuongtm thêm, không cần nút lệnh Close, sử dụng back của Android
        btn_close.setVisibility(View.GONE);
        //
    }

    private void Add_Spinner() {
        spin_ncc.setAdapter(new SpinAdapNCC(activity, R.layout.spinner_row, arr_spinNCC));
        spin_ncc.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                setValueSpinNCC(arr_spinNCC.get(pos).getId());
                setSttSpinNCC(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spin_ncc.setSelection(getSttSpinNCC());

        spin_tt.setAdapter(new SpinAdapNCC(activity, R.layout.spinner_row, arr_spinTT));
        spin_tt.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                setValueSpinTT(arr_spinTT.get(pos).getId());
                setSttSpinTT(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        spin_tt.setSelection(getSttSpinTT());
    }

    private class HandleBtnClick implements OnClickListener {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnSearch:
                    hideKeyboard();
                    init();
                    if (NetworkType.internetIsAvailable(activity)) {
                        if (getListItemsTask == null || getListItemsTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getListItemsTask = new GetListItemsTask();
                            getListItemsTask.execute();
                        }
                    } else {
                        getDataOffline();
                    }
                    break;
                case R.id.btn_ngayBD:
                    control.showDatePicker(et_tuNgay, activity);
                    break;
                case R.id.btn_ngayKT:
                    control.showDatePicker(et_denNgay, activity);
                    break;
                case R.id.btn_ngayCH:
                    control.showDatePicker(et_ngayYCCH, activity);
                    break;
                default:
                    break;
            }
        }
    }

    private String GetText(EditText et) {
        String result = "";
        if (!et.getText().toString().isEmpty()) {
            result = et.getText().toString().trim();
        }
        return result;
    }

    private DonHangParam GetDonHang(boolean isCbox_active, int fragment_state) {
        DonHangParam dh = new DonHangParam();
        String id, giaTri, khachHang, tuNgay, denNgay, ngayYCCH, loaiSP, nguoiLap, trangTHai, nhaCC;
        if (isCbox_active)
            id = GetText(et_MaDH);
        else
            id = GetText(et_search);

        giaTri = GetText(et_giaTri);
        khachHang = GetText(et_TenKH);
        tuNgay = GetText(et_tuNgay);
        denNgay = GetText(et_denNgay);
        ngayYCCH = GetText(et_ngayYCCH);
        loaiSP = GetText(et_loaiSP);
        nguoiLap = GetText(et_nguoiLap);
        trangTHai = getValueSpinTT();
        nhaCC = getValueSpinNCC();

        dh.setCust_po("1");
        if (fragment_state == LICH_SU)
            dh.setDvdh(getID());
        else if (fragment_state == DAT_HANG)
            dh.setDvdh("");
        dh.setId(id);
        dh.setNcc(nhaCC);
        dh.setNdh(nguoiLap);
        dh.setRd(ngayYCCH);
        dh.setSd1(tuNgay);
        dh.setSd2(denNgay);
        dh.setTensp(loaiSP);
        dh.setTt(trangTHai);
        Log.i("dh", dh.toString());
        return dh;
    }

    class GetListItemsTask extends AsyncTask<Void, List<DonHang>, String> {

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
        protected String doInBackground(Void... params) {
            pageCount++;
            DonHangParam dh = GetDonHang(cb_search.isChecked(), FRAGMENT_STATE);

            if (totalPages == 0) {
                try {
                    NorAndNop n = control.getNorNop(getURL_NopNor(dh));
                    totalPages = Integer.parseInt(n.getNop());
                    total_rec = n.getNor();
                } catch (Exception e) {
                }
                Log.i(TAG, String.valueOf(totalPages));
            }

            // String jsonResult = control.jsonValues(getURL_DSDH(dh), false);
            String jsonResult = control.jsonValues(control.convertURL(getURL_DSDH(dh)), false);

            Log.i("cuong dsdonhang", jsonResult);

            Type listType = new TypeToken<List<DonHang>>() {
            }.getType();
            List<DonHang> listItems = new ArrayList<DonHang>();
            try {
                listItems = (List<DonHang>) new Gson().fromJson(jsonResult, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listItems.size() > 0) {
                Log.i("kh size=", String.valueOf(listItems.size()));
                for (int i = 0; i < listItems.size(); i++) {
                    arrItems.add(listItems.get(i));
                }
            }

            // add spin_ncc
            if (isAddSpinner) {
                String json_dsncc = control.jsonValues(getURL_NCC(), false);
                Type type_NCC = new TypeToken<List<TenKH>>() {
                }.getType();
                List<TenKH> ds_ncc = new ArrayList<TenKH>();
                try {
                    ds_ncc = (List<TenKH>) new Gson().fromJson(json_dsncc, type_NCC);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arr_spinNCC = new ArrayList<TenKH>();
                TenKH kh = new TenKH("", "Không chọn");
                arr_spinNCC.add(kh);
                if (ds_ncc.size() > 0) {
                    for (int i = 0; i < ds_ncc.size(); i++) {
                        arr_spinNCC.add((ds_ncc.get(i)));
                    }
                }

                // Get data Trang thai don hang
                String json_dstt = control.jsonValues(getURL_TT(), false);
                Type type_TT = new TypeToken<List<TenKH>>() {
                }.getType();
                List<TenKH> ds_ttdh = new ArrayList<TenKH>();
                try {
                    ds_ttdh = (List<TenKH>) new Gson().fromJson(json_dstt, type_TT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arr_spinTT = new ArrayList<TenKH>();
                if (ds_ttdh.size() > 0) {
                    for (int i = 0; i < ds_ttdh.size(); i++) {
                        arr_spinTT.add(ds_ttdh.get(i));
                    }
                }

				/*
                 * arr_spinTT = new ArrayList<TenKH>(); arr_spinTT.add(new TenKH("1", "Mới lập")); arr_spinTT.add(new TenKH("3", "Gửi tới NCC")); arr_spinTT.add(new
				 * TenKH("4", "NCC duyệt")); arr_spinTT.add(new TenKH("5", "NCC không duyệt")); arr_spinTT.add(new TenKH("8", "NCC xuất kho")); arr_spinTT.add(new
				 * TenKH("9", "Đã nhận hàng")); arr_spinTT.add(new TenKH("10", "Hủy đơn"));
				 */
            }

            publishProgress(arrItems);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<DonHang>... values) {
            super.onProgressUpdate(values);
            if (adapter == null) {
                adapter = new DSDHListAdapter(activity, values[0], new OnCustomClickListener() {

                    @Override
                    public void OnCustomClick(View aView, int position) {
                        switch (aView.getId()) {
                            case R.id.ls_chitiet:
                                Fragment fragment = new SuaDHFragment(arrItems.get(position), SUA_DH);
                                if (fragment != null) {
                                    ((MainActivity) activity).replaceFragment(fragment);
                                }
                                break;
                            case R.id.ls_xoa:
                                DonHang dh = new DonHang();
                                dh = arrItems.get(position);
                                idDonhang = dh.getDonnhap_id();

                                Log.i("cuong xoa don hang", "Xoa clicked: " + dh.getTrangthai_id());

                                // cuongtm thêm ngày 23/5/2016
                                if (dh.getTrangthai_id().trim().contentEquals("1") | dh.getTrangthai_id().trim().contentEquals("5")) {
                                    deleteDonhang();
                                } else {
                                    // Khác trạng thái mới lập thì không được xóa
                                    control.showAlertDialog(activity, "Thông báo", "Chỉ cho phép xóa đơn hàng mới lập và NCC không duyệt!",
                                            false);
                                }

                                break;
                            case R.id.item_list_dsdh:
                                Log.i("btn", "Item clicked");
                                break;
                            default:
                                break;
                        }
                    }
                });
                setListAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            if (isAddSpinner) {
                Add_Spinner();
            }
            getListView().setOnScrollListener(onScrollListener());

        }

        private void deleteDonhang() {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Xác nhận xóa đơn hàng");
            builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này ?");
            builder.setCancelable(false);
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (NetworkType.internetIsAvailable(activity)) {
                        // new LogoutTask().execute();
                        new deleteDonhangTask().execute();
                    } else {
                        // Config.isLogin = false;
                        // Xoa Offline
                        // Intent service = new Intent(MainActivity.this, UpdateLocationService.class);
                        // stopService(service);
                        // MainActivity.this.finish();
                    }

                }
            });
            builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        class deleteDonhangTask extends AsyncTask<Void, String, Void> {

            private ProgressDialog pDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(activity);
                pDialog.setMessage("Đang xóa đơn hàng ...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                String url = Config.URL_DELETE_DON_HANG;
                url = url + "&po_id=" + idDonhang;
                String jsonString = control.getDataJSON(url, true);
                // String jsonString = "Xóa thành công. " + url;

                Log.d("ket qua xoa don hang", url + "..." + jsonString);
                publishProgress(jsonString);
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {

                if (values[0] != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(values[0].toString());
                        if (jsonObject != null) {
                            String result = jsonObject.getString("result");
                            Log.d("cuong json result", result);
                            if (result.equals("TRUE")) {
                                // cuongtm thêm : Xóa thành công
                                control.showAlertDialog(activity, "Thông báo", "Xóa đơn hàng thành công!", false);

                                Log.d("cuong load lai don hang", "loading...");
                                init();
                                if (NetworkType.internetIsAvailable(activity)) {
                                    GetListItemsTask task = new GetListItemsTask();
                                    task.execute();
                                } else {
                                    getDataOffline();
                                }

                            } else {
                                control.showAlertDialog(activity, "Thông báo", "Xóa không thành công. Vui lòng thử lại!", false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    control.showAlertDialog(activity, "Error", "Không kết nối được tới máy chủ. Vui lòng thử lại!", false);
                }

                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                dismissWithCheck(pDialog);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            txt_title.setText("Số đơn hàng: " + total_rec);
            dismissWithCheck(pDialog);

            // cuongtm them ngay 23/5/2016, sau khi tìm xong thì với màn hình nhỏ thì bỏ check box (Tìm nâng cao)
            // để hiển thị kết quả
            if (mAF.isPhone) {
                layout_adv_search.setVisibility(View.GONE);
                add_layout_search = true;
                et_search.setEnabled(true);
                cb_search.setChecked(false); // Xóa check ở checkbox
            }

        }

    }

    private OnScrollListener onScrollListener() {
        return new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = getListView().getCount();
                Log.i("count", String.valueOf(count));
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (getListView().getLastVisiblePosition() >= count - threshold && pageCount < totalPages) {
                        // Execute LoadMoreDataTask AsyncTask
                        if (getListItemsTask == null || getListItemsTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getListItemsTask = new GetListItemsTask();
                            getListItemsTask.execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

        };
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_lichsudh, container, false);
        AddItems(view, inflater);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(activity);
        init();
        if (NetworkType.internetIsAvailable(activity)) {
            if (getListItemsTask == null || getListItemsTask.getStatus() == AsyncTask.Status.FINISHED) {
                getListItemsTask = new GetListItemsTask();
                getListItemsTask.execute();
            }
        } else {
            // Get data local
            getDataOffline();
        }

        if (mAF.isPhone) {
            mAF.setEditTextSize(et_search, 0, 55);
        }

    }

    private void getDataOffline() {
        try {
            // Lay danh sach don hang offline
            String donnhapId = "";
            if (cb_search.isChecked()) {
                donnhapId = GetText(et_MaDH);
                String tuNgay = GetText(et_tuNgay);
                String denNgay = GetText(et_denNgay);
                String ngayYCCH = GetText(et_ngayYCCH);
                String loaiSP = GetText(et_loaiSP);
                String nguoiLap = GetText(et_nguoiLap);
                String trangThai = getValueSpinTT();
                String nhaCC = getValueSpinNCC();

                arrItems = new DonHangDAL(activity).getListDonHang(KH.getCode(), donnhapId, "", "", loaiSP, nguoiLap, trangThai, nhaCC);
            } else {
                donnhapId = GetText(et_search);
                if (donnhapId.equals("")) {
                    arrItems = new DonHangDAL(activity).getAllByOrderCode(KH.getCode());
                } else {
                    arrItems = new DonHangDAL(activity).getListDonHangByOrderCode(KH.getCode(), donnhapId);
                }
            }

            txt_title.setText("Số đơn hàng: " + arrItems.size());

            if (isAddSpinner) {
                List<TenKH> ds_ncc = new NhaCungCapDAL(activity).getAll();
                arr_spinNCC = new ArrayList<TenKH>();
                TenKH kh = new TenKH("", "Không chọn");
                arr_spinNCC.add(kh);
                arr_spinNCC.addAll(ds_ncc);

                // Lay trang thai don hang
                List<TenKH> ds_ttdh = new TrangThaiDonHangDAL(activity).getAll();
                arr_spinTT = new ArrayList<TenKH>();
                arr_spinTT.addAll(ds_ttdh);

				/*
                 * arr_spinTT = new ArrayList<TenKH>(); arr_spinTT.add(new TenKH("1", "Mới lập")); arr_spinTT.add(new TenKH("3", "Gửi tới NCC")); arr_spinTT.add(new
				 * TenKH("4", "NCC duyệt")); arr_spinTT.add(new TenKH("5", "NCC không duyệt")); arr_spinTT.add(new TenKH("8", "NCC xuất kho")); arr_spinTT.add(new
				 * TenKH("9", "Đã nhận hàng")); arr_spinTT.add(new TenKH("10", "Hủy đơn"));
				 */
            }
            Add_Spinner();

            adapter = new DSDHListAdapter(activity, arrItems, new OnCustomClickListener() {

                @Override
                public void OnCustomClick(View aView, int position) {
                    switch (aView.getId()) {
                        case R.id.ls_chitiet:
                            Fragment fragment = new SuaDHFragment(arrItems.get(position), SUA_DH);
                            if (fragment != null) {
                                ((MainActivity) activity).replaceFragment(fragment);
                            }
                            break;
                        case R.id.ls_xoa:
                            DonHang dh = arrItems.get(position);
                            deleteDonHangOffline(dh);
                            break;
                        default:
                            break;
                    }
                }
            });
            setListAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteDonHangOffline(DonHang dh) {
        try {
            if (dh.getNguoi_lap().equals(Config.username)) { // Don hang tao offline
                String donhangId = dh.getDonnhap_id();
                SanPhamDonHangDAL sanphamdonhangDal = new SanPhamDonHangDAL(activity);
                ArrayList<SanPhamDonHangEntity> glstSPDH = sanphamdonhangDal.getListById(donhangId);

                // delete don hang offline
                int resultDeleteDH = new DatHangDAL(activity).delete(Integer.parseInt(donhangId));
                Log.i("DELETE-DON-HANG", resultDeleteDH > 0 ? "Success" : "Failure");

                for (int j = 0; j < glstSPDH.size(); j++) {
                    int resultDelete = sanphamdonhangDal.delete(glstSPDH.get(j).get_id());
                    Log.i("DELETE-SPDH", resultDelete > 0 ? "Success" : "Failure");
                }

                int deleteResult = new DonHangDAL(activity).delete(donhangId);
                if (resultDeleteDH > 0 && deleteResult > 0) {
                    control.showAlertDialog(activity, "Thông báo", "Xóa đơn hàng số " + dh.getDonnhap_id() + " thành công!", true);
                    arrItems.remove(dh);
                    adapter.notifyDataSetChanged();
                    txt_title.setText("Số đơn hàng: " + arrItems.size());
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Xóa đơn hàng số " + dh.getDonnhap_id() + " không thành công!", false);
                }
            } else {
                DonHangDeleteEntity donhangdelete = new DonHangDeleteEntity(dh.getDonnhap_id());
                long insertResult = new DonHangDeleteDAL(activity).add(donhangdelete);

                // Delete Donhang
                int deleteResult = new DonHangDAL(activity).delete(dh.getDonnhap_id());

                if (insertResult > 0 && deleteResult > 0) {
                    control.showAlertDialog(activity, "Thông báo", "Xóa đơn hàng số " + dh.getDonnhap_id() + " thành công!", true);
                    arrItems.remove(dh);
                    adapter.notifyDataSetChanged();
                    txt_title.setText("Số đơn hàng: " + arrItems.size());
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Xóa đơn hàng số " + dh.getDonnhap_id() + " không thành công!", false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);// them option

    }

    public KhachHang getKH() {
        return KH;
    }

    public void setKH(KhachHang kH) {
        KH = kH;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(activity, "pos=" + position, Toast.LENGTH_SHORT).show();
        Log.i("click", "click item list view");

    }

    public String getValueSpinNCC() {
        return valueSpinNCC;
    }

    public void setValueSpinNCC(String valueSpinNCC) {
        this.valueSpinNCC = valueSpinNCC;
    }

    public int getSttSpinNCC() {
        return sttSpinNCC;
    }

    public void setSttSpinNCC(int sttSpinNCC) {
        this.sttSpinNCC = sttSpinNCC;
    }

    public String getValueSpinTT() {
        return valueSpinTT;
    }

    public void setValueSpinTT(String valueSpinTT) {
        this.valueSpinTT = valueSpinTT;
    }

    public int getSttSpinTT() {
        return sttSpinTT;
    }

    public void setSttSpinTT(int sttSpinTT) {
        this.sttSpinTT = sttSpinTT;
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
