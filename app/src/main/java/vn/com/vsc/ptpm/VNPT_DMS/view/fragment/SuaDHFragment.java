package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACTAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACTAdapter.DataTransfer;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACV;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DS_SPAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DanhSachKhuyenMaiAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.OnCustomClickListener;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DoAction;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;
import vn.com.vsc.ptpm.VNPT_DMS.event.EvenDanhSachIdKhuyenMaiSuaDonHang;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventDanhSachChuongTrinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventDanhSachLuaChonKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventTinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonViSP;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SP_SL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SPham;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SanPham;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinAdapHTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinAdapNCC;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TTDH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TTKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.ChuongTrinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ChuongTrinhKhuyenMaiResponse;

@SuppressLint("ValidFragment")
public class SuaDHFragment extends ListFragment {
    private static final String TAG = SuaDHFragment.class.getSimpleName();
    private final int SUA_DH = 2;
    private final int THEM_DH = 1;
    private int TYPE = 0;
    private ProgressDialog pDialog;
    private TextView txt_title_ldh, txt_title_addsp, txt_tong, txt_km, txt_tongtien, txtNgaydathang, txtNgaygiaohang, txt_ghichu, txtTongTienTruocVAT;
    private Switch sw_kho;
    Controller control;
    private List<HTTT> arr_spinHTTT;
    private List<TenKH> arr_spinNCC;
    private List<SanPham> arr_SP;// lay dssp trong kho hoac danh muc
    public List<SP_SL> arr_SP_slted;
    private DS_SPAdapter spAdap;
    private int pageCount = 0;
    private int totalPages = 0;
    private Button btnKM, btnGuiDH, btnCNhat, btnClose;
    private ImageButton btnSuaSP, btnXoaSP;
    private EditText et_ngayDH, et_ngayYCCH, et_diachiGH, et_ghichu;
    private Spinner spin_HTTT, spin_NCC;
    private ImageButton btn_ngayDH, btn_ngayYCCH;
    private List<TenKH> arrTen;
    private AutoCompleteTextView act_tenCH, act_searchSP;
    // private KhachHang KH;
    //private TTDH TTDH = new TTDH();
    private TTDH thongTinDonHang = new TTDH();
    private DonHang DH;
    private String ID;// ID cua KH
    private List<String> arrName;
    private ConvertFont conv;
    private Boolean isCreateDH = false;
    private String PO_ID = "";
    private String valueSpinNCC;
    private String valueSpinHTTT;
    private String valueTenKH;
    private String valueNgayDH;
    private String valueNgayGH;
    private String valueDc;
    private String valueGhiChu;
    private String valueTimSP;
    private String valueKho;
    private LinearLayout layout_addHH;
    private Boolean addhh_is_visible = true;
    private LinearLayout layout_ldh;
    private Boolean ldh_is_visible = true;
    private CusACV act_sp;
    private CusACTAdapter act_sp_adap;

    private CapNhatDSSP capNhatDSSP;
    private tinhKhuyenmaiTask tinhKhuyenmaiTask;
    private DoActionTask doActionTask;
    private GetDSKHTask getDSKHTask;

    private long lastPressTime;
    private static final long DOUBLE_PRESS_INTERVAL = 1000;
    private AlertDialog alertDialog;

    private Activity activity;

    private Context context;
    private RadioButton rdKhuyenMaiTuDong, rdKhuyenMaiThuCong;
    private EditText txtTienKhuyenMai;
    String tienKhuyenMai = "0";
    EventDanhSachChuongTrinhKhuyenMai danhSachKhuyenMai;
    List<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai;
    List<ChuongTrinhKhuyenMaiResponse> danhSachKhuyenMaiUuTien;
    List<ChuongTrinhKhuyenMaiResponse> danhSachKhuyenMaiKhongUuTien;
    private EventDanhSachLuaChonKhuyenMai eventDanhSachLưaChonKhuyenMai;
    private EvenDanhSachIdKhuyenMaiSuaDonHang eventDanhSachIdKhuyenMai;
    private Boolean bCoKhuyenMai = true;
    private String loaiKhuyenMai = "1"; // Khuyến mại tự động = 1, thủ công = 2;
    private Boolean bChuaTinhLaiKhuyenMai = true; // khi chưa click vào khuyến mại thủ công thì giá trị = true;
    private ArrayList<String> danhSachIdKhuyenMai;
    //sua loi #5 tại fabric
    public SuaDHFragment() {
    }

    public SuaDHFragment(KhachHang KH, int type) {
        this.ID = KH.getId();
        this.TYPE = type;
    }

    public SuaDHFragment(DonHang dh, int type) {
        this.setDH(dh);
        this.setPO_ID(dh.getDonnhap_id());
        this.TYPE = type;

    }

    private String getURL_tenKH() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_themdh_dskh_v1&org_id=" + ID;
        Log.i("tendskh", url);
        return url;
    }

    private String getURL_HTTT() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_themdh_httt";
        return url;
    }

    private String getURL_NCC() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_dsncc";
        return url;
    }

    private String getURL_AddDH() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_themdh&" + getDefJsonObj(THEM_DH);
        return url;
    }

    private String getURL_SuaDH() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_suadh&" + getDefJsonObj(SUA_DH);
        return url;
    }

    private String getJson_listKH(String key) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=name_customer&name=" + key;
        return url;
    }

    private String getJson_listHH(int pageno) {
        // setValueTimSP(et_searchSP.getText().toString().trim());
        setValueTimSP(act_sp.getText().toString().trim());

        if (sw_kho.isChecked()) {
            setValueKho("-1");// lay ds sp trong kho
        } else {
            setValueKho("1");// lay ds toan bo sp

        }
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_danhmucsp"
                // + "&po_id="
                // + getPO_ID()
                + "&tenloaisp=" + getValueTimSP() + "&trongkho=" + getValueKho() + "&pageno=" + pageno + "&pagerec=20"
                + "&po_id=" + getPO_ID();
        Log.i("url-listhh", url);
        return url;
    }

    private String getDefJsonObj(int type) {
        String jsonResult = "";
        setValueNgayDH(et_ngayDH.getText().toString().trim());
        setValueNgayGH(et_ngayYCCH.getText().toString().trim());
        setValueDc(et_diachiGH.getText().toString().trim());
        setValueGhiChu(et_ghichu.getText().toString().trim());
        try {
            String sDiaChi = URLEncoder.encode(et_diachiGH.getText().toString().trim(), "UTF-8");
            String sGhiChu = URLEncoder.encode(et_ghichu.getText().toString().trim(), "UTF-8");
            setValueDc(sDiaChi);
            setValueGhiChu(sGhiChu);

        }catch (Exception ex){
            Log.d("Lỗi encode diachi: ", ex.toString());
        }

        String dvdh = getID();
        String ncc = getValueSpinNCC();
        String ngaydh = getValueNgayDH();
        String diachi = getValueDc();
        String ghichu = getValueGhiChu();
        String ngayyc = getValueNgayGH();
        String phieugiao = "";
        String httt = getValueSpinHTTT();
        DatHang dh = new DatHang();
        if (type == THEM_DH)
            dh = new DatHang("", dvdh, ncc, ngaydh, diachi, ghichu, ngayyc, phieugiao, httt);
        else if (type == SUA_DH)
            dh = new DatHang(getPO_ID(), dvdh, ncc, ngaydh, diachi, ghichu, ngayyc, phieugiao, httt);
        Type itemType = new TypeToken<DatHang>() {
        }.getType();
        jsonResult = new Gson().toJson(dh, itemType);
        return ("json=" + jsonResult);
    }

    private String getURL_SuaSP(String po_id, SP_SL spsl) {
        String loaisp_id = spsl.getDvi().getId();
        String soluong = spsl.getSl_dat();
        String gia = spsl.getDvi().getGia1();
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_suasp&po_id=" + po_id + "&loaisp_id=" + loaisp_id + "&soluong=" + soluong
                + "&gia=" + gia;
        return url;
    }

    private String getURL_XoaSP(String po_id, String loaisp_id) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_xoasp&po_id=" + po_id + "&loaisp_id=" + loaisp_id;
        return url;
    }

    private String getURL_GuiNCC(String po_id) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_guincc&po_id=" + po_id;
        return url;
    }

    private String getURL_TTDH(String po_id) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_ttdh&po_id=" + po_id;
        return url;
    }

    private String getDonHang_DSSP(String pageno, String pagerec, String po_id, String tenloaisp) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_dssp&pageno=" + "&pagerec=" + pagerec + "&po_id=" + po_id + "&tenloaisp="
                + tenloaisp;
        return url;
    }

    public String NumbFormatF(double numb) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(numb);
    }

    private void addControl(View view) {
        rdKhuyenMaiTuDong = (RadioButton) view.findViewById(R.id.rdSuaKhuyenMaiTuDong);
        rdKhuyenMaiThuCong = (RadioButton) view.findViewById(R.id.rdSuaKhuyenMaiThuCong);
        txtTienKhuyenMai = (EditText) view.findViewById(R.id.txtSuaTienKhuyenMai);

        layout_addHH = (LinearLayout) view.findViewById(R.id.layout_addHH);
        layout_ldh = (LinearLayout) view.findViewById(R.id.layout_ldh);
        txt_title_ldh = (TextView) view.findViewById(R.id.txt_title_ldh);
        txt_title_addsp = (TextView) view.findViewById(R.id.txt_title_addsp);

        txtTongTienTruocVAT = (TextView) view.findViewById(R.id.txtTongTienTruocVAT);
        txt_tong = (TextView) view.findViewById(R.id.txt_tong);
        txt_km = (TextView) view.findViewById(R.id.txt_km);
        txt_tongtien = (TextView) view.findViewById(R.id.txt_tongtien);

        txtNgaydathang = (TextView) view.findViewById(R.id.txtNgaydathang);
        txtNgaygiaohang = (TextView) view.findViewById(R.id.txtNgaygiaohang);
        txt_ghichu = (TextView) view.findViewById(R.id.txt_ghichu);

        et_ngayDH = (EditText) view.findViewById(R.id.tv_ngayDH);
        et_ngayYCCH = (EditText) view.findViewById(R.id.et_ngayYCCH);
        et_diachiGH = (EditText) view.findViewById(R.id.et_diachiGH);
        et_ghichu = (EditText) view.findViewById(R.id.et_ghichu);
        spin_HTTT = (Spinner) view.findViewById(R.id.spin_HTTT);
        spin_NCC = (Spinner) view.findViewById(R.id.spin_NCC);
        act_tenCH = (AutoCompleteTextView) view.findViewById(R.id.act_tenCH);

        btn_ngayDH = (ImageButton) view.findViewById(R.id.btn_ngayDH);
        btn_ngayYCCH = (ImageButton) view.findViewById(R.id.btn_ngayYCCH);
        btnKM = (Button) view.findViewById(R.id.btnKM);
        btnGuiDH = (Button) view.findViewById(R.id.btnGuiDH);
        btnCNhat = (Button) view.findViewById(R.id.btnCNhat);
        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnSuaSP = (ImageButton) view.findViewById(R.id.btn_suaSP);
        btnXoaSP = (ImageButton) view.findViewById(R.id.btn_xoaSP);

        btn_ngayDH.setOnClickListener(handleClick);
        btn_ngayYCCH.setOnClickListener(handleClick);
        btnKM.setOnClickListener(handleClick);
        btnGuiDH.setOnClickListener(handleClick);
        btnCNhat.setOnClickListener(handleClick);
        btnClose.setOnClickListener(handleClick);

        txt_title_ldh.setOnClickListener(handleClick);
        txt_title_addsp.setOnClickListener(handleClick);

        act_sp = (CusACV) view.findViewById(R.id.act_sp);
        act_searchSP = (AutoCompleteTextView) view.findViewById(R.id.et_searchSP);
        sw_kho = (Switch) view.findViewById(R.id.switch1);
        sw_kho.setChecked(false);

        // cuongtm thêm để chỉnh giao diện cho tương thích với smartphone 4,5 inch
        if (mAF.isPhone) {
            txtNgaydathang.setText("Ngày đặt");
            txtNgaygiaohang.setText("Ngày giao");
            et_ghichu.setHint(txt_ghichu.getText() + "");
            txt_ghichu.setText("");
        }

//        Log.i("trang thai don hang", DH.getTrangthai_id() + "");

        if (DH != null && DH.getTrangthai_id() != null)
            if (DH.getTrangthai_id().trim().contentEquals("1") | DH.getTrangthai_id().trim().contentEquals("5")) {
                btnKM.setVisibility(View.VISIBLE);
                btnGuiDH.setVisibility(View.VISIBLE);
                btnCNhat.setVisibility(View.VISIBLE);
            } else {
                btnKM.setVisibility(View.GONE);
                btnGuiDH.setVisibility(View.GONE);
                btnCNhat.setVisibility(View.GONE);
            }

        mAF.setButtonSize(btnKM, 0, 130);
        mAF.setButtonSize(btnCNhat, 0, 130);
        mAF.setButtonSize(btnGuiDH, 0, 100);
        mAF.setButtonSize(btnClose, 0, 100);
        //
    }

    private void Add_Lv_SP() {
        spAdap = new DS_SPAdapter(activity, R.layout.item_list_addsp2, arr_SP_slted, new OnCustomClickListener() {

            @Override
            public void OnCustomClick(View aView, int position) {
                switch (aView.getId()) {
                    case R.id.btn_suaSP:
                        // ThemSanPham(arr_SP_slted.get(position));

                        // cuongtm thêm để cấm thêm khi trạng thái đơn hàng <> 1
                        // 1 là mới lập, 3 là gửi nhà CC, 4 là Nhà CC duyệt ...
                        // 5 là nhà CC ko duuyet dc phep sua
                        long pressTime = System.currentTimeMillis();
                        if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                            if (alertDialog == null) {
                                alertDialog = new AlertDialog.Builder(activity)
                                        .setTitle("Nhấn quá nhanh!")
                                        .setCancelable(false)
                                        .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).setIcon(R.drawable.alert_dialog_warning)
                                        .show();
                            }
                        } else {
                            if (DH.getTrangthai_id().contentEquals("1") || DH.getTrangthai_id().contentEquals("5")) {
                                if (Double.parseDouble(arr_SP_slted.get(position).getDvi().getGia1()) < 0) {
                                    control.showAlertDialog(activity, "Thông báo", "Không được sửa, xóa hàng khuyến mại", false);
                                } else {
                                    ThemSanPham(arr_SP_slted.get(position));
                                }

                            } else if (DH.getTrangthai_id().contentEquals("3")) {
                                control.showAlertDialog(activity, "Thông báo", "Đơn hàng đã được gửi tới NCC", false);
                            } else {
                                control.showAlertDialog(activity, "Thông báo", "Không được phép sửa đơn hàng đã duyệt", false);
                            }

                            lastPressTime = pressTime;
                        }
                        //
                        break;
                    case R.id.btn_xoaSP:
                        // Old code
                        // XoaSanPham(arr_SP_slted.get(position).getSp().getProduct_cat_id());

                        // cuongtm thêm để cấm thêm khi trạng thái đơn hàng <> 1
                        // 1 là mới lập, 3 là gửi nhà CC, 4 là Nhà CC duyệt ...
                        if (DH.getTrangthai_id().contentEquals("1") || DH.getTrangthai_id().trim().contentEquals("5")) {
                            if (Double.parseDouble(arr_SP_slted.get(position).getDvi().getGia1()) < 0) {
                                control.showAlertDialog(activity, "Thông báo", "Không được sửa, xóa hàng khuyến mại", false);
                            } else {
                                XoaSanPham(arr_SP_slted.get(position).getSp().getProduct_cat_id());
                            }

                        } else {
                            control.showAlertDialog(activity, "Thông báo", "Chỉ cho phép xóa đơn hàng mới lập và NCC không duyệt!", false);
                        }
                        //
                        Log.i("btn", "Item clicked");

                        break;

                    default:
                        break;
                }
            }
        });
        setListAdapter(spAdap);
    }

    public String getNameKH() {
        String Name = "";
        String Id_kh = getID();
        if (arrTen.size() > 0) {
            for (int i = 0; i < arrTen.size(); i++) {
                if (arrTen.get(i).getId() != null && arrTen.get(i).getId().trim().equals(Id_kh)) {
                    Name = arrTen.get(i).getName();
                    break;
                }
            }
        }
        Log.i("arrTen", String.valueOf(arrTen.size()));
        if (Name.length() <= 1) {
//			Log.d("cuong", "Set getNameKH to mAF.sTmp: " + mAF.sTmp);
//			mAF.writelog("Set getNameKH to mAF.sTmp: " + mAF.sTmp, activity, mAF.filelog);
            try{
                Name = conv.getUTF8StringFromNCR(DH.getOrderer_name());
            }catch (Exception e){
                Log.e("get OrderName: ", e.toString());
            }

        }
        return Name;
    }

    public String getIdKH(String input) {
        String Name = "";
        String Id_kh = input;
        if (arrTen.size() > 0) {
            for (int i = 0; i < arrTen.size(); i++) {
                if (arrTen.get(i).getName().trim().equals(Id_kh)) {
                    Name = arrTen.get(i).getId();
                    break;
                }
            }
        }
        Log.i("arrTen", String.valueOf(arrTen.size()));
        return Name;
    }

    private void processGetDSKH() {

        if (NetworkType.internetIsAvailable(activity)) {
            if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                getDSKHTask = new GetDSKHTask();
                getDSKHTask.execute();
            }
            if (capNhatDSSP == null || capNhatDSSP.getStatus() == AsyncTask.Status.FINISHED) {
                capNhatDSSP = new CapNhatDSSP();
                capNhatDSSP.execute();
            }
        } else {
            control.showAlertDialog(activity, "Thông báo", "Chức năng chỉ hoạt động khi có internet", false);
        }
    }

    public void init() {
        pageCount = 0;
        totalPages = 0;
        arr_SP = new ArrayList<SanPham>();
        spAdap = null;
    }

    class GetDSKHTask extends AsyncTask<Void, List<HTTT>, String> {

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
            if (TYPE == SUA_DH) {
                String json_ttdh = control.jsonValues(getURL_TTDH(getPO_ID()), true);
                try {
//                    TTDH = (TTDH) new Gson().fromJson(json_ttdh, TTDH.class);
                    thongTinDonHang = (TTDH) new Gson().fromJson(json_ttdh, TTDH.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i(TAG, thongTinDonHang.toString());
            }

            // add id and name KH
            try {
                String json_dsten = control.jsonValues(getURL_tenKH(), false);

                Log.d("cuong json_dsten data", json_dsten);
                if (json_dsten.contains("api_error") || (json_dsten.contains("[]"))) {
                    Log.d("error json_dsten", json_dsten);
                    arrTen = new ArrayList<TenKH>();
                    arrName = new ArrayList<String>();
                } else {
                    try {
                        arrTen = new ArrayList<TenKH>();
                        arrName = new ArrayList<String>();
                        JSONArray arrResultDanhSachKhachHang = new JSONArray(json_dsten);
                        for (int j = 0; j < arrResultDanhSachKhachHang.length(); j++) {
                            JSONObject joKhachHang = arrResultDanhSachKhachHang.getJSONObject(j);
                            TenKH tenKH = new TenKH(joKhachHang.getString("id"), joKhachHang.getString("name"));

                            arrTen.add(tenKH);
                            arrName.add(conv.getUTF8StringFromNCR(tenKH.getName()));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception ex) {
                Log.d("error json data", ex.toString());
            }

            // add spinHTTT
            try {
                String json_dst;

                //Kiểm tra Danh sách khách hàng đã được lấy trước đó chưa
                String strHinhThucThanhToan = ((MainActivity) activity).hmHinhThucThanhToan.get("value");
                String strTimePutValueHTTT = ((MainActivity) activity).hmHinhThucThanhToan.get("time");
                long currentTime = System.currentTimeMillis();

                //Kiểm tra xem đã lấy dữ liệu khách hàng trước đó chưa và thời gian dữ liệu đã cũ hơn 1 ngày chưa
                if (strHinhThucThanhToan != null && strTimePutValueHTTT != null && Long.valueOf(strTimePutValueHTTT) - currentTime < 86400000) {
                    json_dst = strHinhThucThanhToan;
                    Log.e("CONCRETE", "Lấy lại Hình thức thanh toán từ cache");
                } else {
                    json_dst = control.jsonValues(getURL_HTTT(), false);
                    if (!json_dst.contains("api_error")) {
                        //Cache lại danh sách khách hàng cho lần sau
                        ((MainActivity) activity).hmHinhThucThanhToan.put("value", json_dst);
                        ((MainActivity) activity).hmHinhThucThanhToan.put("time", String.valueOf(System.currentTimeMillis()));
                        Log.e("CONCRETE", "Lưu Hình thức thanh toán mới xuống cache");
                    }
                }

                Log.d("cuong json_dst data", json_dst);
                if (json_dst.contains("api_error") || (json_dst.contains("[]"))) {
                    Log.d("error json_dst", json_dst);
                } else {
                    Type type_HTTT = new TypeToken<List<HTTT>>() {
                    }.getType();

                    List<HTTT> ds_HTTT = new ArrayList<HTTT>();
                    try {
                        ds_HTTT = (List<HTTT>) new Gson().fromJson(json_dst, type_HTTT);
                        Log.i("arr size", String.valueOf(ds_HTTT.size()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (ds_HTTT.size() > 0) {
                        arr_spinHTTT = new ArrayList<HTTT>();
                        for (int i = 0; i < ds_HTTT.size(); i++) {
                            arr_spinHTTT.add(ds_HTTT.get(i));
                        }
                    }
                }
            } catch (Exception ex) {
                Log.d("error json_dst", ex.toString());
            }

            // add spinNCC
            try {
                String json_dsncc;

                //Kiểm tra Danh sách khách hàng đã được lấy trước đó chưa
                String strDanhSachNhaCungCap = ((MainActivity) activity).hmDanhSachNhaCungCap.get("value");
                String strTimePutValueNCC = ((MainActivity) activity).hmDanhSachNhaCungCap.get("time");
                long currentTime = System.currentTimeMillis();

                //Kiểm tra xem đã lấy dữ liệu khách hàng trước đó chưa và thời gian dữ liệu đã cũ hơn 1 ngày chưa
                if (strDanhSachNhaCungCap != null && strTimePutValueNCC != null && Long.valueOf(strTimePutValueNCC) - currentTime < 86400000) {
                    json_dsncc = strDanhSachNhaCungCap;
                    Log.e("CONCRETE", "Lấy lại Danh sách NCC từ cache");
                } else {
                    json_dsncc = control.jsonValues(getURL_NCC(), false);
                    if (!json_dsncc.contains("api_error")) {
                        //Cache lại danh sách khách hàng cho lần sau
                        ((MainActivity) activity).hmDanhSachNhaCungCap.put("value", json_dsncc);
                        ((MainActivity) activity).hmDanhSachNhaCungCap.put("time", String.valueOf(System.currentTimeMillis()));
                        Log.e("CONCRETE", "Lưu Danh sách NCC mới xuống cache");
                    }
                }

                Log.d("cuong json_dsncc data", json_dsncc);

                if (json_dsncc.contains("api_error") || (json_dsncc.contains("[]"))) {
                    Log.d("error json_dsncc", json_dsncc);
                } else {
                    Type type_NCC = new TypeToken<List<TenKH>>() {
                    }.getType();
                    List<TenKH> ds_ncc = new ArrayList<TenKH>();
                    try {
                        ds_ncc = (List<TenKH>) new Gson().fromJson(json_dsncc, type_NCC);
                        Log.i("arr size", String.valueOf(ds_ncc.size()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    arr_spinNCC = new ArrayList<TenKH>();
                    if (ds_ncc.size() > 0) {
                        for (int i = 0; i < ds_ncc.size(); i++) {
                            arr_spinNCC.add((ds_ncc.get(i)));
                        }
                    }
                }
            } catch (Exception ex) {
                Log.d("error json_dsncc", ex.toString());
            }

//            // lay toan bo danh sach san pham (ko co trong kho)
//            Type type_sp = new TypeToken<List<SanPham>>() {
//            }.getType();
//            // value =0: lay toan bo ds
//            String json_sp = control.jsonValues(getJson_listHH(0), false);
//            Log.i("listhh", json_sp);
//            List<SanPham> ds_sp = new ArrayList<SanPham>();
//            try {
//                ds_sp = (List<SanPham>) new Gson().fromJson(json_sp, type_sp);
//                Log.i("arr size", String.valueOf(ds_sp.size()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (ds_sp != null && ds_sp.size() > 0) {
//                for (int i = 0; i < ds_ncc.size(); i++) {
//                    arr_SP.add((ds_sp.get(i)));
//                }
//            }
            publishProgress(arr_spinHTTT);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<HTTT>... values) {
            super.onProgressUpdate(values);
            addSpinner();
            if (TYPE == SUA_DH) {
                setID(thongTinDonHang.getDonvi_dathang_id() + "");
                txt_title_ldh.setText("Thông tin đơn hàng, số: " + getDH().getDonnhap_id());
                if (getTTDH().getDia_diem_giao_hang() != null) {
                    et_diachiGH.setText(conv.getUTF8StringFromNCR(getTTDH().getDia_diem_giao_hang().trim()));
                }
                if (getTTDH().getChuthich() != null) {
                    et_ghichu.setText(conv.getUTF8StringFromNCR(getTTDH().getChuthich().trim()));
                }
                if (thongTinDonHang != null && thongTinDonHang.getTrangthai_id() != null) {
                    // Gửi NCC, NCC duyệt, NCC không duyệt
                    if (thongTinDonHang.getTrangthai_id().equals("3") || thongTinDonHang.getTrangthai_id().equals("4")) {
                        btnKM.setVisibility(View.INVISIBLE);
                        btnGuiDH.setVisibility(View.INVISIBLE);
                    }

                    // Đã nhận hàng, Hủy đơn
                    if (thongTinDonHang.getTrangthai_id().equals("9") || thongTinDonHang.getTrangthai_id().equals("10")) {
                        btnKM.setVisibility(View.INVISIBLE);
                        btnGuiDH.setVisibility(View.INVISIBLE);
                        btnCNhat.setVisibility(View.INVISIBLE);
                    }
                }

                // lay thong tin tien mat khuyen mai
                if(thongTinDonHang.getTienKhuyenMai() != null){
                    txtTienKhuyenMai.setText(thongTinDonHang.getTienKhuyenMai().toString());
                }
                // end

                // lay loai khuyen mai
                if(thongTinDonHang.getPromotionId() != null) {
                    if (thongTinDonHang.getPromotionId().toString().equals("0")) {
                        rdKhuyenMaiTuDong.setChecked(true);
                        rdKhuyenMaiThuCong.setChecked(false);
                    }else{
                        // check các đơn hàng với thông tin khuyến mại
                        rdKhuyenMaiTuDong.setChecked(false);
                        rdKhuyenMaiThuCong.setChecked(true);
                        String[] danhSachId = thongTinDonHang.getPromotionId().toString().split(",");
                        danhSachIdKhuyenMai = new ArrayList<>();
                        if(danhSachId != null && danhSachId.length > 0){
                            for(int i = 0; i < danhSachId.length; i++){
                                danhSachIdKhuyenMai.add(danhSachId[i]);
                            }
                        }
                        loaiKhuyenMai = "2";
                        eventDanhSachIdKhuyenMai = new EvenDanhSachIdKhuyenMaiSuaDonHang(danhSachIdKhuyenMai);
                        EventBus.getDefault().postSticky(eventDanhSachIdKhuyenMai);
                        // end
                    }
                }
                // end
            }
            addAutoUpdateText();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }
    }

    private class DanhSachKH extends AsyncTask<Void, List<TTKH>, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onProgressUpdate(List<TTKH>... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class CapNhatDSSP extends AsyncTask<Void, List<SPham>, Void> {
        double tong_tien = 0;
        double tongtienkm = 0;
        double tongTienVAT = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Void... params) {
            // cap nhat giao dien: lay toan bo dssp trong don hang
            String json_dssp = control.jsonValues(getDonHang_DSSP("1", "100", getPO_ID(), ""), false);// list hien thi 100sp
            Log.i("dssp", json_dssp);
            arr_SP_slted = new ArrayList<SP_SL>();
            Type type_dssp = new TypeToken<List<SPham>>() {
            }.getType();
            List<SPham> add_dssp = new ArrayList<SPham>();
            try {
                add_dssp = (List<SPham>) new Gson().fromJson(json_dssp, type_dssp);
                Log.i("add_dssp", String.valueOf(add_dssp.size()));

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (add_dssp.size() > 0) {
                for (int i = 0; i < add_dssp.size(); i++) {
                    SPham spham = add_dssp.get(i);
                    // add Dvi
                    String id_dvi = spham.getLoaisp_id();
                    String sluong = "1";// so luong sp tren 1 don vi tinh
                    String ten_dvi = spham.getLoaisp_ten();
                    String gia1 = spham.getGianhap_vnd();
                    String gia2 = spham.getSelling_price_vnd();
                    String sl_dat = spham.getSoluong();
                    String tonKho = spham.getHangton();
                    // add SanPham
                    String product_cat_id = spham.getLoaisp_id();
                    String ds_donvi = spham.getLoaisp_id() + ";" + sluong + ";" + spham.getDonvi_tinh() + ";" + spham.getGianhap_vnd() + ";" + spham.getGianhap_vnd()
                            + ";";
                    String name = spham.getLoaisp_ten();
                    String soluong = "0";

                    SP_SL add_sp = new SP_SL(new SanPham(product_cat_id, ds_donvi, name, soluong), new DonViSP(id_dvi, sluong, ten_dvi, gia1, gia2), sl_dat, tonKho);
                    Log.i("sp_sl", add_sp.toString());
                    arr_SP_slted.add(add_sp);

                }
            }
            publishProgress(add_dssp);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<SPham>... values) {
            super.onProgressUpdate(values);
            try {
                Add_Lv_SP();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            double tong_tien = 0;
//            double tongtienkm = 0;
            double dongia = 0;

            // txt_km.setText("Khuyến mại: 0 VNĐ");
            try {
                if (values[0].size() > 0) {
                    for (int i = 0; i < values[0].size(); i++) {
                        // cuongtm them
                        dongia = Double.parseDouble(values[0].get(i).getGianhap_vnd());
                        if (dongia >= 0) {
                            tong_tien += Double.parseDouble(values[0].get(i).getSoluong()) * Double.parseDouble(values[0].get(i).getGianhap_vnd());
                            if (values[0].get(i).getTaxRate() != null) {
                                tongTienVAT += (Double.parseDouble(values[0].get(i).getSoluong()) * Double.parseDouble(values[0].get(i).getGianhap_vnd())) / (1 + Double.parseDouble(values[0].get(i).getTaxRate()));
                            }
                        } else {
                            // Hang khuyen mai
                            tongtienkm += Double.parseDouble(values[0].get(i).getSoluong()) * Double.parseDouble(values[0].get(i).getGianhap_vnd());
                        }
                        //

                    }
                }
                txt_tong.setText("Tổng tiền hàng: " + NumbFormatF(tong_tien) + " VNĐ");
                txt_tongtien.setText("Tổng tiền: " + NumbFormatF(tong_tien + tongtienkm) + " VNĐ");
                txt_km.setText("Khuyến mại: " + NumbFormatF(Math.abs(tongtienkm)) + " VNĐ");
                txt_km.setTypeface(null, Typeface.BOLD);
                txtTongTienTruocVAT.setText("Tổng tiền hàng (trước VAT): " + NumbFormatF(tongTienVAT) + " VNĐ");
                txtTongTienTruocVAT.setTypeface(null, Typeface.BOLD);
            }catch (Exception ex){
                Log.d("Lỗi sửa đơn hàng: ", ex.toString());
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // thông báo khi tổng tiền âm
            if((tong_tien + tongtienkm) < 0){
                if((tong_tien + tongtienkm) < 0){
                    EventTinhKhuyenMai eventTinhKhuyenMai = new EventTinhKhuyenMai(false);
                    EventBus.getDefault().postSticky(eventTinhKhuyenMai);
                    control.showAlertDialog(activity, getString(R.string.thongbao).toUpperCase(),
                            getString(R.string.thongBaoTienKhuyenMaiAm), false);
                }
                else {
                    EventTinhKhuyenMai eventTinhKhuyenMai = new EventTinhKhuyenMai(true);
                    EventBus.getDefault().postSticky(eventTinhKhuyenMai);
                }
            }
        }
    }

    private void SearchSP() {

        new Thread() {
            public void run() {
                String json_result = "";
                /**
                 * Code cũ gây lỗi list view show trên điện thoại
                 **/
//                Log.i("addDh", getJson_listHH(1));
//                json_result = control.jsonValues(control.convertURL(getJson_listHH(1)), false);
//                Log.i("dsdat", json_result);
                /**
                 * Update code mới ngày 31/3/2017 sửa lỗi hiển thị spinner list item trên điện thoại
                 **/
                if (NetworkType.internetIsAvailable(activity)) {

                    try {
                        json_result = control.jsonValues(control.convertURL(getJson_listHH(1)), false);
                        if (json_result.contains("api_error")) {
                            Log.i("error json_result ", json_result);
                        }
                    } catch (Exception ex) {
                        Log.d("error json", ex.toString());
                    }
                } else {
                    String name = conv.GetNCRDecimalString(act_sp.getText().toString());
                    ArrayList<HangHoaEntity> glstHangHoa = new HangHoaDAL(activity).getByFilter(name);
                    Type itemType = new TypeToken<ArrayList<HangHoaEntity>>() {
                    }.getType();
                    json_result = new Gson().toJson(glstHangHoa, itemType);
                }
                Log.i("dsdat", json_result);

                // end
                Message msg = Message.obtain();
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString("searchsp", json_result);
                Log.i("searchsp", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
            }
        }.start();
    }

    private void CreateDonHang() {
        // pDialog = new ProgressDialog(activity);
        // pDialog.setMessage("Đang lập đơn hàng...");
        // pDialog.setCancelable(false);
        // pDialog.show();
        new Thread() {
            public void run() {
                String json_result = "";
                Log.i("addDh", getURL_AddDH());
                // code cũ gây lỗi mất tiếng viết
//                json_result = control.jsonValues(control.convertURL(getURL_AddDH()), false);
                // end
                // code mới
                json_result = control.jsonValues(getURL_AddDH(), false);
                // end
                Log.i("dsdat", json_result);
                Message msg = Message.obtain();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("createDH_result", json_result);
                Log.i("createDH_result", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
                // xu ly them 1 cai handler
            }
        }.start();
    }

    private void GuiNCC() {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Đang gửi đơn hàng...");
        pDialog.setCancelable(false);
        pDialog.show();
        new Thread() {
            public void run() {
                String json_result = "";
                Log.i("addDh", getURL_GuiNCC(getPO_ID()));
                json_result = control.jsonValues(control.convertURL(getURL_GuiNCC(getPO_ID())), false);
                Log.i("dsdat", json_result);
                Message msg = Message.obtain();
                msg.what = 4;
                Bundle b = new Bundle();
                b.putString("guincc_result", json_result);
                Log.i("guincc_result", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
                // xu ly them 1 cai handler
            }
        }.start();
    }

    private void XoaSanPham(final String id_del) {
        new Thread() {
            public void run() {
                String json_result = "";
                json_result = control.jsonValues(control.convertURL(getURL_XoaSP(getPO_ID(), id_del)), false);
                Log.i("del_sp", json_result);
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message msg = Message.obtain();
                msg.what = 5;
                Bundle b = new Bundle();
                b.putString("del_sp", json_result);
                Log.i("del_sp", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
            }
        }.start();
    }

    private void ThemSanPham(final SP_SL spsl) {
        if (Config.isEmptyEdittext) {
            Toast.makeText(activity, "Số lượng sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
        } else {
            new Thread() {
                public void run() {
                    String json_result = "";
                    Log.i("themsp_json", getURL_SuaSP(getPO_ID(), spsl));
                    json_result = control.jsonValues(control.convertURL(getURL_SuaSP(getPO_ID(), spsl)), false);
                    Log.i("themsp", json_result);
                    JSONObject emp;
                    String empname = "";
                    try {
                        emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                        empname = emp.optString("result");
                        Log.i("json_tbl", empname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.what = 6;
                    Bundle b = new Bundle();
                    b.putString("edit_sp", json_result);
                    Log.i("edit_sp", json_result);
                    msg.setData(b);
                    messageHandler.sendMessage(msg);
                }
            }.start();
        }
    }

    // lay danh sach khuyen mai
    private String urlDanhSachKhuyenMai(String urlAPI, ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        String defaultPageNo = "1";
        String defaultPageRec = "1000";
        String url = urlAPI + "&id=" + chuongTrinhKhuyenMai.getId().trim() + "&code=" + chuongTrinhKhuyenMai.getCode().trim()
                + "&name=" + chuongTrinhKhuyenMai.getName() + "&type=" + chuongTrinhKhuyenMai.getType()
                + "&start_date=" + chuongTrinhKhuyenMai.getStarDate() + "&end_date=" + chuongTrinhKhuyenMai.getEndDate()
                +"&po_id=" + getPO_ID()
                + "&pageno=" + defaultPageNo + "&pagerec=" + defaultPageRec;
        return url;
    }
    // end

    private void layDanhSachSanPhamKhuyenMai(final String urlApi, final ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(getString(R.string.dangLayDanhSachKhuyenMai));
        pDialog.setCancelable(false);
        pDialog.show();


        new Thread() {
            public void run() {
                String json_result = "";
                String url = urlDanhSachKhuyenMai(urlApi, chuongTrinhKhuyenMai);
                Log.i("URL KHUYEN MAI", url);
                try {
                    json_result = control.jsonValues(control.convertURL(url), false);
                } catch (Exception ex) {
                    Log.d("error json", ex.toString());
                }
                pDialog.dismiss();
                Log.i("KHUYEN MAI TRA VE", json_result);
                Type listType = new TypeToken<List<ChuongTrinhKhuyenMaiResponse>>() {
                }.getType();
                danhSachChuongTrinhKhuyenMai = (List<ChuongTrinhKhuyenMaiResponse>) new Gson().fromJson(json_result, listType);
                danhSachKhuyenMaiUuTien = new ArrayList<ChuongTrinhKhuyenMaiResponse>();
                danhSachKhuyenMaiKhongUuTien = new ArrayList<ChuongTrinhKhuyenMaiResponse>();
                Message msg = Message.obtain();
                msg.what = 9;
                messageHandler.sendMessage(msg);


            }
        }.start();
    }


    private void SuaDonHang() {
        new Thread() {
            public void run() {
                String json_result = "";
                Log.i("suaDH", getURL_SuaDH());
                // code cũ gây lỗi mất tiếng viết
//              json_result = control.jsonValues(control.convertURL(getURL_SuaDH()), false);
                // end

                // code mới
//                json_result = control.jsonValues(getURL_AddDH(), false);
                json_result = control.jsonValues(getURL_SuaDH(), false);
                // end

                Log.i("suaDH_re", json_result);
                Message msg = Message.obtain();
                msg.what = 7;
                Bundle b = new Bundle();
                b.putString("suaDH", json_result);
                Log.i("suaDH", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
                // xu ly them 1 cai handler
            }
        }.start();
    }

    private Handler messageHandler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String aResponse = (String) (msg.getData().getString("createDH_result").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    // CheckLapDH = false;
                    Toast.makeText(activity, "Chưa lập được đơn hàng", Toast.LENGTH_SHORT).show();
                } else {
                    setPO_ID(empname.substring(4, empname.length()));
                    if (arr_SP_slted.size() > 0) {
                        // new CapNhatDSSP().execute(arr_SP_slted);
                    }
                    // else {
                    // control.showAlertDialog(activity, "THÔNG BÁO",
                    // "Bạn chưa cập nhật danh mục hàng hóa", true);
                    // }
                    Toast.makeText(activity, "Lập đơn hàng thành công, POID= " + getPO_ID(), Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 2) {
                String aResponse = (String) (msg.getData().getString("searchsp").trim());
                Type type_sp = new TypeToken<List<SanPham>>() {
                }.getType();
                List<SanPham> ds_sp = new ArrayList<SanPham>();
                try {
                    ds_sp = (List<SanPham>) new Gson().fromJson(aResponse, type_sp);
                    Log.i("sp_size", String.valueOf(ds_sp.size()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arr_SP = new ArrayList<SanPham>();
                arr_SP.clear();
                if (ds_sp != null && ds_sp.size() > 0) {
                    for (int i = 0; i < ds_sp.size(); i++) {
                        //ds_sp.get(i).setProduct_cat_id(conv.getUTF8StringFromNCR(ds_sp.get(i).getProduct_cat_id()));
                        arr_SP.add(ds_sp.get(i));
                    }
                    // update the adapter
                    if (arr_SP != null) {
                        act_sp_adap = new CusACTAdapter(activity, R.layout.item_list_acv, arr_SP, new DataTransfer() {

                            @Override
                            public void setValues(String pos) {
                                // TODO Auto-generated method stub

                            }
                        });
                        act_sp.setAdapter(act_sp_adap);
                        act_sp_adap.notifyDataSetChanged();
                    }

                } else if (ds_sp.size() == 0) {
                    //Toast.makeText(activity, "Không tìm thấy sản phẩm", Toast.LENGTH_LONG).show();
                }
            } else if (msg.what == 3) {
                String aResponse = (String) (msg.getData().getString("senddh").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    Toast.makeText(activity, "Chưa thêm được sp", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Thêm SP thành công", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 4) {
                String aResponse = (String) (msg.getData().getString("guincc_result").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (empname.equals("TRUE")) {
                    dismissWithCheck(pDialog);
                    new AlertDialog.Builder(activity)
                            .setTitle("Gửi đơn hàng thành công")
                            .setMessage("Đơn hàng đã được gửi đến nhà cung cấp")
                            .setCancelable(false)
                            .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btnGuiDH.setEnabled(false);
                                }
                            })
                            .setIcon(R.drawable.ic_success)
                            .show();
                } else {
                    Toast.makeText(activity, "Chưa gửi được đến nhà cung cấp", Toast.LENGTH_SHORT).show();
                    dismissWithCheck(pDialog);
                }
            } else if (msg.what == 5) {
                String aResponse = (String) (msg.getData().getString("del_sp").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    Toast.makeText(activity, "Chưa xóa được sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                    if (capNhatDSSP == null || capNhatDSSP.getStatus() == AsyncTask.Status.FINISHED) {
                        capNhatDSSP = new CapNhatDSSP();
                        capNhatDSSP.execute();
                    }
                }
            } else if (msg.what == 6) {
                String aResponse = (String) (msg.getData().getString("edit_sp").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (empname.equals("")) {
//                    if(Config.isEmptyEdittext){
//                        Toast.makeText(activity, "Số lượng sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
//                    }else {
                    Toast.makeText(activity, "Chưa cập nhật được sản phẩm", Toast.LENGTH_SHORT).show();
//                    new CapNhatDSSP().execute();
//                    }
                } else {
                    Toast.makeText(activity, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                }
                if (capNhatDSSP == null || capNhatDSSP.getStatus() == AsyncTask.Status.FINISHED) {
                    capNhatDSSP = new CapNhatDSSP();
                    capNhatDSSP.execute();
                }
            } else if (msg.what == 7) {
                String aResponse = (String) (msg.getData().getString("suaDH").trim());
                JSONObject emp;
                String empname = "";
                try {
                    emp = new JSONObject(aResponse.substring(1, aResponse.length() - 1));
                    empname = emp.optString("result");
                    Log.i("json_tbl", empname);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    Toast.makeText(activity, "Sửa thông tin đơn hàng ko thành công", Toast.LENGTH_SHORT).show();
                }
//                else {
//                    Toast.makeText(activity, "Đã sửa thông tin đơn hàng", Toast.LENGTH_SHORT).show();
//                }
            } else if(msg.what == 9){
                if(danhSachChuongTrinhKhuyenMai != null && danhSachChuongTrinhKhuyenMai.size() > 0) {
                    bCoKhuyenMai = true;
                    final Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_danh_sach_khuyen_mai);

                    // chỉnh kích cỡ dialog show
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int height = displayMetrics.heightPixels;
                    int width = displayMetrics.widthPixels;
                    height = (int)(height * 1.3);
                    width = (int)(width * 0.5);
                    lp.width = height;
                    lp.height = width;

                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(lp);
                    // end

                    // đặt label
                    ChuongTrinhKhuyenMaiResponse nhanKhuyenMai = new ChuongTrinhKhuyenMaiResponse("", "", "", "", getString(R.string.ngayHieuLuc) ,"", getString(R.string.tenChuongTrinh),"", getString(R.string.idMa), "","");
                    danhSachKhuyenMaiUuTien.add(nhanKhuyenMai);
                    danhSachKhuyenMaiKhongUuTien.add(nhanKhuyenMai);
                    // end

                    // phan biet danh sach khuyen mai uu tien
                    for(ChuongTrinhKhuyenMaiResponse item : danhSachChuongTrinhKhuyenMai){
                        if(item.getPromotion() != null) {
                            if (item.getPromotion().trim().equals("2")) {
                                danhSachKhuyenMaiUuTien.add(item);
                            }
                            if (item.getPromotion().trim().equals("3")) {
                                danhSachKhuyenMaiKhongUuTien.add(item);
                            }
                        }
                    }

                    // set listview khuyen mai uu tien
                    if(danhSachKhuyenMaiUuTien.size() > 1) {
                        // set data list view
                        ListView lvDanhSachKhuyenMaiUuTien = (ListView) dialog.findViewById(R.id.lvDanhSachKhuyenMaiUuTien);
                        DanhSachKhuyenMaiAdapter danhSachKhuyenMaiAdapter = new DanhSachKhuyenMaiAdapter(activity, R.layout.item_khuyen_mai, danhSachKhuyenMaiUuTien);
                        lvDanhSachKhuyenMaiUuTien.setAdapter(danhSachKhuyenMaiAdapter);
                    }
                    // set listview khuyen mai khong uu tien
                    if(danhSachKhuyenMaiKhongUuTien.size() > 1) {
                        // set data list view
                        ListView lvDanhSachKhuyenMaiKhongUuTien = (ListView) dialog.findViewById(R.id.lvDanhSachKhuyenMaiKhongUuTien);
                        DanhSachKhuyenMaiAdapter danhSachKhuyenMaiAdapter = new DanhSachKhuyenMaiAdapter(activity, R.layout.item_khuyen_mai, danhSachKhuyenMaiKhongUuTien);
                        lvDanhSachKhuyenMaiKhongUuTien.setAdapter(danhSachKhuyenMaiAdapter);
                    }
                    // end

                    dialog.show();

                    // bat su kien button Xac Nhan khuyen mai click
                    Button btnXacNhanKhuyenMai = (Button) dialog.findViewById(R.id.btnXacNhanKhuyenMai);
                    btnXacNhanKhuyenMai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }else{
                    bCoKhuyenMai = false;
                    control.showAlertDialog(activity, "THÔNG BÁO",
                            "Không có chương trình khuyến mại!", false);
                }
            }

        }
    };

    private void addSpinner() {

        spin_HTTT.setAdapter(new SpinAdapHTTT(activity, R.layout.spinner_row, arr_spinHTTT));
        spin_HTTT.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                // Toast.makeText(
                // activity,
                // arr_spinHTTT.get(pos).getHttt_id() + " - "
                // + arr_spinHTTT.get(pos).getHinhthuc(),
                // Toast.LENGTH_SHORT).show();
                setValueSpinHTTT(arr_spinHTTT.get(pos).getHttt_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spin_NCC.setAdapter(new SpinAdapNCC(activity, R.layout.spinner_row, arr_spinNCC));
        spin_NCC.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                // Toast.makeText(
                // activity,
                // arr_spinNCC.get(pos).getId() + " - "
                // + arr_spinNCC.get(pos).getName(),
                // Toast.LENGTH_SHORT).show();
                setValueSpinNCC(arr_spinNCC.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        if (thongTinDonHang != null) {
            if (thongTinDonHang.getDonvi_cungung_id() != null) {
                String item_ncc = thongTinDonHang.getDonvi_cungung_id().trim();
                for (int i = 0; i < arr_spinNCC.size(); i++) {
                    if (item_ncc.equals(arr_spinNCC.get(i).getId().toString().trim())) {
                        spin_NCC.setSelection(i);
                    }
                }
            }
            if (thongTinDonHang.getPayment_method_id() != null) {
                String item_httt = thongTinDonHang.getPayment_method_id().trim();
                for (int i = 0; i < arr_spinHTTT.size(); i++) {
                    if (item_httt.equals(arr_spinHTTT.get(i).getHttt_id().toString().trim())) {
                        spin_HTTT.setSelection(i);
                    }
                }
            }
        }
    }
    private OnClickListener handleClick = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                //sửa ngày đặt hàng
                case R.id.btn_ngayDH:
                    if (TYPE == THEM_DH)
                        control.showDatePicker(et_ngayDH, activity);
                    else if (TYPE == SUA_DH) {
                        btn_ngayDH.setEnabled(false);
                    }
                    break;
                //sửa ngày giao hàng
                case R.id.btn_ngayYCCH:
                    control.showDatePicker(et_ngayYCCH, activity);
                    break;
                //cập nhật
                case R.id.btnCNhat:
                    long pressTime = System.currentTimeMillis();
                    if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                        if (alertDialog == null) {
                            alertDialog = new AlertDialog.Builder(activity)
                                    .setTitle("Nhấn quá nhanh!")
                                    .setCancelable(false)
                                    .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).setIcon(R.drawable.alert_dialog_warning)
                                    .show();
                        }
                    } else {
                        if (arr_SP_slted.size() >= 0) {
                            for (int i = 0; i < arr_SP_slted.size(); i++) {
                                ThemSanPham(arr_SP_slted.get(i));
                            }
                        }
                        if (!getPO_ID().equals("")) {
                            SuaDonHang();
                        }
                        hideKeyboard();

                        lastPressTime = pressTime;
                    }
                    break;
                //thoát
                case R.id.btnClose:
                    activity.onBackPressed();
                    hideKeyboard();
                    break;
                //tính khuyến mại
                case R.id.btnKM:
                    // cuongtm them
                    // po_id, httt_id, khuyenmai
                    if (tinhKhuyenmaiTask == null || tinhKhuyenmaiTask.getStatus() == AsyncTask.Status.FINISHED) {

                        // kiem tra voi truong hop khuyen mai tu dong da lua chon trong danh sach khuyen mai hay chua
                        eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
                        eventDanhSachIdKhuyenMai = EventBus.getDefault().getStickyEvent(EvenDanhSachIdKhuyenMaiSuaDonHang.class);
                        if(loaiKhuyenMai.equals("2") && bCoKhuyenMai && !bChuaTinhLaiKhuyenMai &&
                                (eventDanhSachLưaChonKhuyenMai == null || eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai().size() == 0)){
                            control.showAlertDialog(activity, "THÔNG BÁO",
                                    "Bạn chưa lựa chọn chương trình khuyến mại!", false);
                        }else {
                            // po_id, httt_id, khuyenmai
                            tinhKhuyenmaiTask = new tinhKhuyenmaiTask();
                            tinhKhuyenmaiTask.execute();
                        }

                    }
                    hideKeyboard();
                    break;
                //gửi đơn hàng
                case R.id.btnGuiDH:
                    long pressTime2 = System.currentTimeMillis();
                    if (pressTime2 - lastPressTime <= DOUBLE_PRESS_INTERVAL) {
                        if (alertDialog == null) {
                            alertDialog = new AlertDialog.Builder(activity)
                                    .setTitle("Nhấn quá nhanh!")
                                    .setCancelable(false)
                                    .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).setIcon(R.drawable.alert_dialog_warning)
                                    .show();
                        }
                    } else {
                        // hiển thị cảnh báo khi tổng tiền có giá trị âm
                        EventTinhKhuyenMai eventTinhKhuyenMai = EventBus.getDefault().getStickyEvent(EventTinhKhuyenMai.class);
                        if(eventTinhKhuyenMai != null && !eventTinhKhuyenMai.isbTongTienKhuyenMaiDuong()) {
                            control.showAlertDialog(activity, getString(R.string.thongbao).toUpperCase(),
                                    getString(R.string.thongBaoTienKhuyenMaiAm), false);
                        }else {
                            if (arr_SP_slted.size() >= 0 && !getPO_ID().equals("")) {
                                GuiNCC();
                            }
                            hideKeyboard();
                            lastPressTime = pressTime2;
                        }
                    }
                    break;
                //ẩn hiện thông tin khách hàng
                case R.id.txt_title_ldh:
                    if (ldh_is_visible) {
                        layout_ldh.setVisibility(View.GONE);
                        ldh_is_visible = false;
                    } else {
                        layout_ldh.setVisibility(View.VISIBLE);
                        ldh_is_visible = true;
                    }
                    break;
                //ẩn hiện danh mục hàng hóa
                case R.id.txt_title_addsp:
                    // if (CheckLapDH) {
                    if (addhh_is_visible) {
                        layout_addHH.setVisibility(View.GONE);
                        addhh_is_visible = false;
                    } else {
                        layout_addHH.setVisibility(View.VISIBLE);
                        addhh_is_visible = true;
                    }
                    // }
                    break;
            }
        }
    };

    // cuongtm thêm : Start Tính khuyến mại
    private class tinhKhuyenmaiTask extends AsyncTask<Void, String, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tính khuyến mại ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = API_code.URL_KHUYENMAI + "&po_id=" + getPO_ID();
            eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
            url = url + "&httt_id=" + getValueSpinHTTT() + "&tien_km=" + tienKhuyenMai + "&loai_km=" + loaiKhuyenMai;
            if (loaiKhuyenMai.equals("2")){
                String idKhuyenMai = "";
                if(eventDanhSachLưaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai().size() > 0) {
                    for (ChuongTrinhKhuyenMaiResponse itemKhuyenMai : eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai()) {
                        idKhuyenMai += itemKhuyenMai.getId() + ",";
                    }
                } else if(bChuaTinhLaiKhuyenMai){
                    for (String item : danhSachIdKhuyenMai) {
                        idKhuyenMai += item + ",";
                    }
                }
                // remove "," in last index of string
                if (idKhuyenMai != null && idKhuyenMai.length() > 0 && idKhuyenMai.charAt(idKhuyenMai.length()-1)==',') {
                    idKhuyenMai = idKhuyenMai.substring(0, idKhuyenMai.length()-1);
                }
                Log.d("Id khuyen mai:", idKhuyenMai);
                url = url + "&id_km=" + idKhuyenMai;
            }

            String json_result = "";
            try {
                // json_result = control.getDataJSON(control.convertURL(url), true);
                // json_result = control.jsonValues(url,false);
                json_result = control.getDataJSON(url, true);
            } catch (Exception ex) {
                Log.d("error url khuyenmai", url + ". Kq=" + ex.toString());
            }

            Log.d("cuong url khuyenmai", url + ". Kq=" + json_result);
            publishProgress(json_result);
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
                        String[] a = result.split("\\|"); // Do | là ký tự đặc biệt, cần đưa thêm \\ vào
                        if (a[0].trim().toUpperCase().contains("OK")) {
                            control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại hoàn tất!", true);
                            if (NetworkType.internetIsAvailable(activity)) {
                                // Load lại dữ liệu
                                if (doActionTask == null || doActionTask.getStatus() == Status.FINISHED) {
                                    doActionTask = new DoActionTask();
                                    doActionTask.execute();
                                }
                            } else {
                            }
                        } else {
                            control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại không thành công. Vui lòng thử lại!", false);
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

    // End tinh khuyen mai

    // cuongtm thêm : Start lấy tiền khuyến mại //Thử nghiệm
    private class DoActionTask extends AsyncTask<Void, String, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang thực hiện ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = API_code.URL_DOACT;
            String json1 = "";

            DoAction dact = new DoAction("", "getkm", "pos_po", "promotion_amount", "", "id=" + getPO_ID());
            json1 = "&json=" + new Gson().toJson(dact);

            url = url + json1;
            String json_result = "";
            try {
                Log.d("doaction getkm url ", url);
                json_result = control.getDataJSON(control.convertURL(url), true);
            } catch (Exception ex) {
                Log.d("error tien khuyenmai", url + ". Kq=" + ex.toString());
            }

            Log.d("error tien khuyenmai", url + ". Kq=" + json_result);
            publishProgress(json_result);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if (values[0] != null) {
                try {
                    JSONObject jsonObject = new JSONObject(values[0].toString());
                    if (jsonObject != null) {
                        String result = jsonObject.getString("promotion_amount");
                        Log.d("cuong giatri=", result);
                        // txt_km.setText(result);
                        if (mAF.isNumeric(result)) {
                            txt_km.setText("Khuyến mại: " + NumbFormatF(Double.parseDouble(result)) + " VNĐ");
                        } else {
                            txt_km.setText("Khuyến mại: 0");
                        }

                        // cuongtm : load lại danh sách sản phẩm để ra sp khuyến mại
                        if (capNhatDSSP == null || capNhatDSSP.getStatus() == Status.FINISHED) {
                            capNhatDSSP = new CapNhatDSSP();
                            capNhatDSSP.execute();
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

    // End lay tien khuyen mai

    private void addAutoUpdateText() {
        act_tenCH.addTextChangedListener(new GenericTextWatcher(act_tenCH));
        act_tenCH.setAdapter(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrName));
        act_tenCH.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // Toast.makeText(
                // activity,
                // "ID="
                // + getIdKH(conv.GetNCRDecimalString(act_tenCH
                // .getText().toString())),
                // Toast.LENGTH_SHORT).show();
                Log.i("posi", String.valueOf(getIdKH(conv.GetNCRDecimalString(act_tenCH.getText().toString()))));
                setID(getIdKH(conv.GetNCRDecimalString(act_tenCH.getText().toString())));
            }
        });

        String nameKH = conv.getUTF8StringFromNCR(getNameKH());
        act_tenCH.setText(nameKH);
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        if (TYPE == THEM_DH) {
            et_ngayDH.setText(timeStamp);
            et_ngayYCCH.setText(timeStamp);
        } else if (TYPE == SUA_DH) {
            et_ngayDH.setText(getTTDH().getNgay_lap().trim());
            et_ngayDH.setEnabled(false);
            et_ngayYCCH.setText(getTTDH().getNgaymuon_nhanhang());
        }
        act_sp.addTextChangedListener(new SearchSPTextWatcher());
        // // ObjectItemData has no value at first
        List<SanPham> list = new ArrayList<SanPham>();
        // set the custom ArrayAdapter
        act_sp_adap = new CusACTAdapter(activity, R.layout.item_list_acv, list);
        act_sp.setAdapter(act_sp_adap);
    }

    Handler handler = new Handler();

    private void Autocompletes_Timer(final String newText) {
        // new text will be here. so if you type fast within 1 sec.
        // handler will be remover each time so that handler post delay also be
        // remove.
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        // new text will be in runnable with 1 sec delay.
        handler.postDelayed(runnable(newText), 1000);
    }

    private Runnable runnable(final String newText) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Log.d("Autocompleted", newText);
                // call AysncTask here
            }

        };
        return runnable;
    }

    private class SearchSPTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence userInput, int start, int before, int count) {
            Log.e(TAG, "User input: " + userInput);
            if (getPO_ID().equals("") && isCreateDH == false) {
                CreateDonHang();
                isCreateDH = true;
            }
            String newText = userInput.toString();
            if (!newText.trim().equals("")) {
                Autocompletes_Timer(newText);
                SearchSP();
//                act_sp.setOnItemClickListener(new OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (arr_SP != null && arr_SP.size() > 0) {
//                            DonViSP dvi_0 = getArr_dvi(arr_SP.get(position).getDs_donvi()).get(0);
//                            String id_dvi = dvi_0.getId();
//                            String sluong = dvi_0.getSluong();
//                            String ten_dvi = dvi_0.getTen_dvi();
//                            String gia1 = dvi_0.getGia1();
//                            String gia2 = dvi_0.getGia2();
//                            String sl_dat = "1";
//                            SP_SL add_sp = new SP_SL(arr_SP.get(position), new DonViSP(id_dvi, sluong, ten_dvi, gia1, gia2), sl_dat);
//                            // Log.i("sp_sl", add_sp.toString());
//                            // spAdap.add(add_sp);
//                            arr_SP_slted.add(add_sp);
//                            spAdap.notifyDataSetChanged();
//                            act_sp.setText("");
//                        }
//                    }
//                });
                if (arr_SP != null && arr_SP.size() > 0) {

                    act_sp.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (arr_SP.size() > position) {
                                dsDonVi = arr_SP.get(position).getDs_donvi();
                                if (dsDonVi != null && getArr_dvi(dsDonVi).size() > 0) {
//                                    DonViSP dvi_0 = getArr_dvi(arr_SP.get(position).getDs_donvi()).get(0);
                                    DonViSP dvi_0 = getArr_dvi(dsDonVi).get(0);
                                    String id_dvi = dvi_0.getId();
                                    String sluong = dvi_0.getSluong();
                                    String ten_dvi = dvi_0.getTen_dvi();
                                    String gia1 = dvi_0.getGia1();
                                    String gia2 = dvi_0.getGia2();
                                    String sl_dat = "1";
                                    SP_SL add_sp = new SP_SL(arr_SP.get(position), new DonViSP(id_dvi, sluong, ten_dvi, gia1, gia2), sl_dat);
                                    // Log.i("sp_sl", add_sp.toString());
                                    // spAdap.add(add_sp);
                                    arr_SP_slted.add(add_sp);
                                    //spAdap.notifyDataSetChanged();
                                    act_sp.setText("");
                                    setListAdapter(spAdap);
                                }
                            } else {
                                act_sp.setText("");
                            }
                        }
                    });
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
    }

    String dsDonVi = "";

    private List<DonViSP> getArr_dvi(String input) {
        List<DonViSP> arr_dvi = new ArrayList<DonViSP>();
        String s = conv.getUTF8StringFromNCR(input);
        String[] result = s.split("[;|]");
        int numbRlts = (result.length) / 5;
        for (int i = 0; i < numbRlts; i++) {
            DonViSP dvi = new DonViSP();
            for (int j = 1; j <= 5; j++) {
                dvi = new DonViSP(result[i * j], result[i * j + 1], result[i * j + 2], result[i * j + 3], result[i * j + 4]);
            }
            arr_dvi.add(dvi);
        }
        return arr_dvi;
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // Toast.makeText(activity, "ID=" + "longpd",
            // Toast.LENGTH_SHORT)
            // .show();
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_suadh, container, false);
        removeEvent();
        addControl(view);
        addEvents(view);
        disableControls();
        return view;
    }

    private void disableControls() {
        act_tenCH.setEnabled(false);
        spin_NCC.setEnabled(false);
        et_ngayDH.setEnabled(false);
        et_ngayYCCH.setEnabled(false);
        et_diachiGH.setEnabled(false);
        spin_HTTT.setEnabled(false);
        et_ghichu.setEnabled(false);
        btn_ngayYCCH.setEnabled(false);
        btn_ngayDH.setEnabled(false);
    }

    private void removeEvent() {
        eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
        if(eventDanhSachLưaChonKhuyenMai != null) {
            EventBus.getDefault().removeStickyEvent(eventDanhSachLưaChonKhuyenMai);
        }
        EventTinhKhuyenMai eventTinhKhuyenMai = EventBus.getDefault().getStickyEvent(EventTinhKhuyenMai.class);
        if(eventTinhKhuyenMai != null) {
            EventBus.getDefault().removeStickyEvent(eventTinhKhuyenMai);
        }
    }

    private void addEvents(View view) {
        rdKhuyenMaiTuDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdKhuyenMaiThuCong.setChecked(false);
                loaiKhuyenMai = "1";

            }
        });

        rdKhuyenMaiThuCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiKhuyenMai = "2";
                bChuaTinhLaiKhuyenMai = false;
                rdKhuyenMaiTuDong.setChecked(false);
                ChuongTrinhKhuyenMai chuongTrinhKhuyenmai = new ChuongTrinhKhuyenMai();
                chuongTrinhKhuyenmai.setId("");
                chuongTrinhKhuyenmai.setCode("");
                chuongTrinhKhuyenmai.setName("");
                chuongTrinhKhuyenmai.setType("");
                chuongTrinhKhuyenmai.setStarDate("");
                chuongTrinhKhuyenmai.setEndDate("");
                layDanhSachSanPhamKhuyenMai(API_code.URL_DANH_SACH_KHUYEN_MAI, chuongTrinhKhuyenmai);
            }
        });

        txtTienKhuyenMai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    tienKhuyenMai = "0";
                }else{
                    tienKhuyenMai = s.toString();
                }
            }
        });


        eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);

        if(eventDanhSachLưaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai().size() > 0){
            rdKhuyenMaiTuDong.setChecked(false);
            rdKhuyenMaiThuCong.setChecked(true);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(activity);
        init();
        processGetDSKH();
    }

    public TTDH getTTDH() {
        return thongTinDonHang;
    }

    public void setTTDH(TTDH tTDH) {
        thongTinDonHang = tTDH;
    }

    public DonHang getDH() {
        return DH;
    }

    public void setDH(DonHang dH) {
        DH = dH;
    }

    public String getValueKho() {
        return valueKho;
    }

    public void setValueKho(String valueKho) {
        this.valueKho = valueKho;
    }

    public String getValueTimSP() {
        return valueTimSP;
    }

    public void setValueTimSP(String valueTimSP) {
        this.valueTimSP = valueTimSP;
    }

    public String getPO_ID() {
        return PO_ID;
    }

    public void setPO_ID(String pO_ID) {
        PO_ID = pO_ID;
    }

    public String getValueNgayGH() {
        return valueNgayGH;
    }

    public void setValueNgayGH(String valueNgayGH) {
        this.valueNgayGH = valueNgayGH;
    }

    public String getValueSpinNCC() {
        return valueSpinNCC;
    }

    public void setValueSpinNCC(String valueSpinNCC) {
        this.valueSpinNCC = valueSpinNCC;
    }

    public String getValueSpinHTTT() {
        return valueSpinHTTT;
    }

    public void setValueSpinHTTT(String valueSpinHTTT) {
        this.valueSpinHTTT = valueSpinHTTT;
    }

    public String getValueTenKH() {
        return valueTenKH;
    }

    public void setValueTenKH(String valueTenKH) {
        this.valueTenKH = valueTenKH;
    }

    public String getValueNgayDH() {
        return valueNgayDH;
    }

    public void setValueNgayDH(String valueNgayDH) {
        this.valueNgayDH = valueNgayDH;
    }

    public String getValueDc() {
        return valueDc;
    }

    public void setValueDc(String valueDc) {
        this.valueDc = valueDc;
    }

    public String getValueGhiChu() {
        return valueGhiChu;
    }

    public void setValueGhiChu(String valueGhiChu) {
        this.valueGhiChu = valueGhiChu;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = activity;
    }
}
