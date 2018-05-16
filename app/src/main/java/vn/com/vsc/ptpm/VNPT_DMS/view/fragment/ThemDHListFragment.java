package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.ACTSearchKHAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACTAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACTAdapter.DataTransfer;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.CusACV;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DSSPListAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DS_SPAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DanhSachKhuyenMaiAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.NameKHAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.OnCustomClickListener;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.DateValidator;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DatHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HinhThucThanhToanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.NhaCungCapDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.SanPhamDonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DoAction;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.SanPhamDonHangEntity;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventDanhSachChuongTrinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventDanhSachLuaChonKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventTinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DSKHModel;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonViSP;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.NameKHDisplay;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SP_SL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SPham;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SanPham;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinAdapHTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinAdapNCC;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.ChuongTrinhKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ChuongTrinhKhuyenMaiResponse;

@SuppressLint("ValidFragment")
public class ThemDHListFragment extends BaseListFragment{
    private static final String TAG = ThemDHListFragment.class.getSimpleName();
    private DateValidator dateValidator;
    private final int SUA_DH = 2;
    private final int THEM_DH = 1;
    private ProgressDialog pDialog;
    private TextView txt_title_ldh, txt_title_addsp, txt_tong, txt_km, txt_tongtien, txtNgaydathang, txtNgaygiaohang, txt_ghichu, txtTongTienTruocVAT;
    private Switch sw_kho;
    Controller control;
    private List<HTTT> arr_spinHTTT;
    private List<TenKH> arr_spinNCC;
    private List<SanPham> arr_SP;
    private List<DSKHModel> arr_search_dskh;
    public List<SP_SL> arr_SP_slted;
    private DSSPListAdapter spAdap;
    private String khongChonNhaCungCapId = "-3";
    // cuongtm them
    private DS_SPAdapter spAdap1;
    private DonHang DH;
    //

    // tạo đơn hàng
    private Button btnTaoDonHang;
    private LinearLayout viewDanhMucHangHoa;

    private int pageCount = 0;
    private int totalPages = 0;
    private double tongtienhang = 0;
    private double tongTienTruocVAT = 0;
    private Button btnKM, btnGuiDH, btnCNhat, btnClose;

    @NotEmpty(message = "Trường bắt buộc không được để trống")
    private EditText et_ngayDH, et_ngayYCCH;
    private EditText et_diachiGH, et_ghichu;
    private Spinner spin_HTTT, spin_NCC;
    private ImageButton btn_ngayDH, btn_ngayGH;

    @NotEmpty(message = "Phải nhập tên khách hàng")
    private AutoCompleteTextView act_tenCH;

    private KhachHang KH;
    private String pID;
    private List<TenKH> arrTen;
    private List<String> arrName;
    private List<NameKHDisplay> arrNameClone;
    private int mYear, mMonth, mDay;
    private ConvertFont conv;
    String arr_test[];
    ArrayAdapter<String> adap_lvSP;
    List<String> arrTest2;
    ArrayAdapter<String> adap_lvSP2;
    ACTSearchKHAdapter act_search_kh_adapter;

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

    private int pos_del;
    private String nhacungcap;

    private tinhKhuyenmaiTask tinhkmTask = new tinhKhuyenmaiTask();
    private Activity activity;

    private Context context;
    private long lastPressTime;
    private static final long DOUBLE_PRESS_INTERVAL = 1000;
    private AlertDialog alertDialog;
    private List<String> arr_remove_items = new ArrayList<String>();
    private Drawable iconSuccess, iconWarning, iconError;

    private RadioButton rdKhuyenMaiTuDong, rdKhuyenMaiThuCong;
    private EditText txtTienKhuyenMai;
    Button btnXacNhanKhuyenMai;
    String tienKhuyenMai = "0";
    EventDanhSachChuongTrinhKhuyenMai danhSachKhuyenMai;
    List<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai;
    List<ChuongTrinhKhuyenMaiResponse> danhSachKhuyenMaiUuTien;
    List<ChuongTrinhKhuyenMaiResponse> danhSachKhuyenMaiKhongUuTien;
    private EventDanhSachLuaChonKhuyenMai eventDanhSachLưaChonKhuyenMai;
    private Boolean bCoKhuyenMai = true;
    private String loaiKhuyenMai = "1"; // Khuyến mại tự động = 1, thủ công = 2;

    public ThemDHListFragment() {
    }

    public DonHang getDH() {
        return DH;
    }

    public void setDH(DonHang dH) {
        DH = dH;
    }

    public int getPos_del() {
        return pos_del;
    }

    public void setPos_del(int pos_del) {
        this.pos_del = pos_del;
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

    public ThemDHListFragment(KhachHang KH) {
        this.KH = KH;
        this.pID = KH.getId();
    }

    public KhachHang getKH() {
        return KH;
    }

    public void setKH(KhachHang kH) {
        KH = kH;
    }

    public String getID() {
        return pID;
    }

    public void setID(String iD) {
        pID = iD;
    }

    private String getURLGetKhachHangByID() {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_themdh_dskh_v1&org_id="
//                + ID
                ;
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

    // lay danh sach khuyen mai
    private String urlDanhSachKhuyenMai(String urlAPI, ChuongTrinhKhuyenMai chuongTrinhKhuyenMai) {
        String defaultPageNo = "1";
        String defaultPageRec = "1000";
        String url = urlAPI + "&id=" + chuongTrinhKhuyenMai.getId().trim() + "&code=" + chuongTrinhKhuyenMai.getCode().trim()
                + "&name=" + chuongTrinhKhuyenMai.getName() + "&type=" + chuongTrinhKhuyenMai.getType()
                + "&start_date=" + chuongTrinhKhuyenMai.getStarDate()  + "&end_date=" + chuongTrinhKhuyenMai.getEndDate() +"&po_id="
                + "&pageno=" + defaultPageNo + "&pagerec=" + defaultPageRec;
        return url;
    }
    // end

    private String getJson_listHH(int pageno) {
        // setValueTimSP(et_searchSP.getText().toString().trim());
        setValueTimSP(act_sp.getText().toString().trim());

        if (sw_kho.isChecked()) {
            setValueKho("-1");// lay ds sp trong kho
        } else {
            setValueKho("1");// lay ds toan bo sp
        }
//        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_danhmucsp"
        String url = API_code.URL_HANGHOA
                // + "&po_id="
                // + getPO_ID()
                + "&tenloaisp=" + getValueTimSP() + "&trongkho=" + getValueKho() + "&pageno=" + pageno + "&pagerec=20"
                + "&po_id=" + getPO_ID();
        Log.i("url-listhh", url);
        return url;
    }

    private String getJson_listKH() {
        String keyword = act_tenCH.getText().toString();
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=name_customer&name=" + keyword;
        Log.e(TAG + " url-listkh", url);
        return url;
    }

    private String getDefJsonObj(int ptype) {
        String jsonResult = "";

        try {
            setValueNgayDH(et_ngayDH.getText().toString().trim());
            setValueNgayGH(et_ngayYCCH.getText().toString().trim());
            String sDiaChi = URLEncoder.encode(et_diachiGH.getText().toString().trim(), "UTF-8");
            setValueDc(sDiaChi);

            setValueNgayGH(et_ngayYCCH.getText().toString().trim());
            String sGhiChu = URLEncoder.encode(et_ghichu.getText().toString().trim(), "UTF-8");
            setValueGhiChu(sGhiChu);
            String dvdh = getID();
            Log.e("dvdh", getID());
            String ncc = getValueSpinNCC();
            String ngaydh = getValueNgayDH();
            String diachi = getValueDc();
            String ghichu = getValueGhiChu();
            String ngayyc = getValueNgayGH();
            String phieugiao = "";
            String httt = getValueSpinHTTT();
            DatHang dh = new DatHang();
            if (ptype == THEM_DH) {
//                phieugiao = mAF.Route_assign_id;
                phieugiao = KH.getAssign_id();
                dh = new DatHang("", dvdh, ncc, ngaydh, diachi, ghichu, ngayyc, phieugiao, httt);
            } else if (ptype == SUA_DH) {
                dh = new DatHang(getPO_ID(), dvdh, ncc, ngaydh, diachi, ghichu, ngayyc, phieugiao, httt);
            }
//            jsonResult = new Gson().toJson(dh);
//            jsonResult = control.subJSON(jsonResult);
            Type itemType = new TypeToken<DatHang>() {
            }.getType();
            jsonResult = new Gson().toJson(dh, itemType);



        } catch (Exception ex) {
            Log.d("error getDefJsonObj: ", ex.toString());
        }
        return ("json=" + jsonResult + "");
    }

    private String getURL_CNDH(String po_id, SP_SL spsl) {
        String loaisp_id = spsl.getDvi().getId();
        String soluong = spsl.getSl_dat();
        String gia = spsl.getDvi().getGia1();
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_suasp&po_id=" + po_id + "&loaisp_id=" + loaisp_id + "&soluong=" + soluong
                + "&gia=" + gia;
        return url;
    }

    private String getURL_XoaDH(String po_id, String loaisp_id) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_xoasp&po_id=" + po_id + "&loaisp_id=" + loaisp_id;
        return url;
    }

    private String getURL_GuiNCC(String po_id) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_guincc&po_id=" + po_id;
        return url;
    }

    private String getDonHang_DSSP(String pageno, String pagerec, String po_id, String tenloaisp) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_dssp&pageno=" + "&pagerec=" + pagerec + "&po_id=" + po_id + "&tenloaisp="
                + tenloaisp;
        return url;
    }

    public String NumbFormatF(String numb) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(Double.parseDouble(numb.toString().trim()));
    }

    private void addControl(View view) {
        // controls tạo đơn hàng
        btnTaoDonHang = (Button) view.findViewById(R.id.btnTaoDonHang);
        viewDanhMucHangHoa = (LinearLayout) view.findViewById(R.id.viewDanhMucHangHoa);

        rdKhuyenMaiTuDong = (RadioButton) view.findViewById(R.id.rdKhuyenMaiTuDong);
        rdKhuyenMaiThuCong = (RadioButton) view.findViewById(R.id.rdKhuyenMaiThuCong);
        txtTienKhuyenMai = (EditText) view.findViewById(R.id.txtTienKhuyenMai);

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
        btn_ngayGH = (ImageButton) view.findViewById(R.id.btn_ngayYCCH);
        // btn_LapDH = (Button) view.findViewById(R.id.btnLapDH);
        // btn_clear = (Button) view.findViewById(R.id.btnClear);
        // btnSearchSP = (Button) view.findViewById(R.id.btnSearchSP);
        btnKM = (Button) view.findViewById(R.id.btnKM);
        btnGuiDH = (Button) view.findViewById(R.id.btnGuiDH);
        btnCNhat = (Button) view.findViewById(R.id.btnCNhat);
        btnClose = (Button) view.findViewById(R.id.btnClose);

        iconSuccess = view.getResources().getDrawable(R.drawable.icon_success);
        iconWarning = view.getResources().getDrawable(R.drawable.icon_warning);
        iconError = view.getResources().getDrawable(R.drawable.icon_error);

        btn_ngayDH.setOnClickListener(handleClick);
        btn_ngayGH.setOnClickListener(handleClick);
        // btn_clear.setOnClickListener(handleClick);
        // btn_LapDH.setOnClickListener(handleClick);
        btnKM.setOnClickListener(handleClick);
        btnGuiDH.setOnClickListener(handleClick);
        btnCNhat.setOnClickListener(handleClick);
        btnClose.setOnClickListener(handleClick);

        txt_title_ldh.setOnClickListener(handleClick);
        txt_title_addsp.setOnClickListener(handleClick);

        act_sp = (CusACV) view.findViewById(R.id.act_sp);
//        act_searchSP = (AutoCompleteTextView) view.findViewById(R.id.et_searchSP);
        sw_kho = (Switch) view.findViewById(R.id.switch1);
        sw_kho.setChecked(false);
        btnCNhat.setEnabled(true);

        // cuongtm thêm để chỉnh giao diện cho tương thích với smartphone 4,5 inch
        if (mAF.isPhone) {
            txtNgaydathang.setText("Ngày đặt");
            txtNgaygiaohang.setText("Ngày giao");
            et_ghichu.setHint(txt_ghichu.getText());
            txt_ghichu.setText("");
        }
        mAF.setButtonSize(btnKM, 0, 130);
        mAF.setButtonSize(btnCNhat, 0, 130);

        mAF.setButtonSize(btnGuiDH, 0, 100);
        mAF.setButtonSize(btnClose, 0, 100);
        //


    }

    public void removeSP_SLOnClickHandler(View v) {
        SP_SL itemToRemove = (SP_SL) v.getTag();
        spAdap.remove(itemToRemove);
    }

    private void Add_Lv_SP() {
        arr_SP_slted = new ArrayList<SP_SL>();
        spAdap = new DSSPListAdapter(activity, R.layout.item_list_addsp, arr_SP_slted, new DSSPListAdapter.SendData() {

            @Override
            public void setValues(String t) {
                // txt_tong.setText("Tổng tiền hàng: " +
                // String.valueOf(t));
            }

            @Override
            public void setDonViSP(SP_SL dvi) {
                // txt_tong.setText(dvi.toString()
                // + dvi.getDvi().toString());
            }

            @Override
            public void setList(List<SP_SL> list) {
                List<SP_SL> arr_slsp = (List<SP_SL>) list;
                tongtienhang = 0;
                tongTienTruocVAT = 0;

                txt_km.setText("Khuyến mại: 0 VNĐ");
                if (arr_slsp.size() > 0) {
                    for (int i = 0; i < arr_slsp.size(); i++) {
                        tongtienhang += Double.parseDouble(arr_slsp.get(i).getTong_t_hang().toString().trim());
                        if(arr_slsp.get(i).getTongTienTruocVAT() != "0"){
                            tongTienTruocVAT += Double.parseDouble(arr_slsp.get(i).getTongTienTruocVAT().toString().trim());
                        }
                    }
                    txt_tong.setText("Tổng tiền hàng: " + NumbFormatF(String.valueOf(tongtienhang)) + " VNĐ");
                    txt_tongtien.setText("Tổng tiền: " + NumbFormatF(String.valueOf(tongtienhang)) + " VNĐ");
                    txtTongTienTruocVAT.setText("Tổng tiền hàng (trước VAT): " + NumbFormatF(String.valueOf(tongTienTruocVAT)) + " VNĐ");

                } else {
                    txt_tong.setText("Tổng tiền hàng: 0 VNĐ");
                    txtTongTienTruocVAT.setText("Tổng tiền hàng (trước VAT): 0 VNĐ");
                }
                arr_SP_slted = (List<SP_SL>) list;
            }

            @Override
            public void setRemoveItems(String s) {
                arr_remove_items.add(s);
                // sau khi bam vao cap nhat thi xoa het item trong list
            }
        });
        setListAdapter(spAdap);
        // txt_tong.setText(String.valueOf(tong_tien_hang));
    }

    public String getNameKH() {
        String Name = "";
        try {
            String Id_kh = getKH().getId().trim();
            if (arrTen.size() > 0) {
                for (int i = 0; i < arrTen.size(); i++) {
                    if (arrTen.get(i).getId().trim().equals(Id_kh)) {
                        Name = arrTen.get(i).getName();
                        break;
                    }
                }
            }
            Log.i("arrTen", String.valueOf(arrTen.size()));
        } catch (Exception e) {
            mAF.writelog("Error getNameKH: " + e.toString(), activity, mAF.filelog);
            Log.d("cuong error getNameKH", e.toString());
            e.printStackTrace();
        }
        if (Name.length() <= 1) {
            Log.d("cuong", "Set getNameKH to mAF.sTmp: " + mAF.sTmp);
            mAF.writelog("Set getNameKH to mAF.sTmp: " + mAF.sTmp, activity, mAF.filelog);
            Name = conv.getUTF8StringFromNCR(mAF.sTmp);
        }

        //Thu luon reset ve maf.sglobal
        Log.d("cuong", "Set getNameKH to mAF.sTmp: " + mAF.sTmp + ". " + conv.getUTF8StringFromNCR(mAF.sTmp));
        //Name = conv.getUTF8StringFromNCR(mAF.sTmp);

        return Name;
    }

    public String getIdKH(String input) {
        String Name = "";

        try {
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
        } catch (Exception e) {
            Log.d("cuong error getIdKH : ", e.toString());
        }
        return Name;
    }

    private void processGetDSKH() {
        try {
            if (control.isOnline(activity)) {
                GetDSKHTask task = new GetDSKHTask();
                task.execute();
            } else {
                getDataOffline();
            }
        } catch (Exception e) {
            Log.e(TAG + "error processGetDSKH: ", e.toString());
        }

    }

    @SuppressWarnings("static-access")
    private void getDataOffline() {
        try {
            // TODO: Lay du lieu trong csdl offline
            sw_kho.setChecked(true);

            // add id and name KH
            List<TenKH> ds_ten = new KhachhangDAL(activity).getAll();
            Log.i("LIST_TEN_KH", String.valueOf(ds_ten.size()));

            arrTen = new ArrayList<TenKH>();
            arrName = new ArrayList<String>();
            //ds ten KH ko co dau "_" de tim kiem trong autocompletetextview
            arrNameClone = new ArrayList<>();
            if (ds_ten.size() > 0) {
                for (int i = 0; i < ds_ten.size(); i++) {
                    arrTen.add((ds_ten.get(i)));
                    arrName.add(conv.getUTF8StringFromNCR(ds_ten.get(i).getName()));
//                    arrNameClone.add(conv.getUTF8StringFromNCR(ds_ten.get(i).getName().replace("_", " ")));
                    String codeKH = conv.getUTF8StringFromNCR(ds_ten.get(i).getName()).split("_")[0];
                    String nameKH = conv.getUTF8StringFromNCR(ds_ten.get(i).getName()).split("_")[1];
                    //loi khi chay offline
                    NameKHDisplay nameKHDisplay = new NameKHDisplay("", nameKH, codeKH);
                    arrNameClone.add(nameKHDisplay);
                }
            }

            // add spinHTTT
            List<HTTT> ds_HTTT = new HinhThucThanhToanDAL(activity).getAll();
            Log.i("LIST_HTTT", String.valueOf(ds_HTTT.size()));
            if (ds_HTTT.size() > 0) {
                arr_spinHTTT = new ArrayList<HTTT>();
                for (int i = 0; i < ds_HTTT.size(); i++) {
                    arr_spinHTTT.add(ds_HTTT.get(i));
                }
            }

            // add spinNCC
            List<TenKH> ds_ncc = new NhaCungCapDAL(activity).getAll();
            arr_spinNCC = new ArrayList<TenKH>();
            if (ds_ncc.size() > 0) {
                for (int i = 0; i < ds_ncc.size(); i++) {
                    arr_spinNCC.add((ds_ncc.get(i)));
                }
            }

            // lay toan bo danh sach san pham theo danh mucj
            List<HangHoaEntity> ds_sp = new HangHoaDAL(activity).getAll();
            Log.i("LIST_HANGHOA", String.valueOf(ds_sp.size()));
            if (ds_sp.size() > 0) {
                for (int i = 0; i < ds_sp.size(); i++) {
                    SanPham item = new SanPham(ds_sp.get(i).getProduct_cat_id(), ds_sp.get(i).getDs_donvi(), ds_sp.get(i).getName(), ds_sp.get(i).getSoluong(), ds_sp.get(i).getUnit_display());
                    arr_SP.add(item);
                }
            }

            addSpinner();
            addAutoUpdateText();
            Add_Lv_SP();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        control = new Controller(activity);
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

        @SuppressWarnings({"unchecked", "static-access"})
        @Override
        protected String doInBackground(Void... params) {
            // TODO: Get Data
            // add id and name KH
            try {
                String json_dsten = control.jsonValues(getURLGetKhachHangByID(), false);
                Log.d("cuong json_dsten data", json_dsten);
                arrTen = new ArrayList<TenKH>();
                arrName = new ArrayList<String>();
                //array ten KH khong co dau "_"
                arrNameClone = new ArrayList<>();
                if (json_dsten.contains("api_error") || (json_dsten.contains("[]"))) {
                    Log.d("error json_dsten", json_dsten);
                } else {
                    try {
                        JSONArray arrResultDanhSachKhachHang = new JSONArray(json_dsten);
                        for (int j = 0; j < arrResultDanhSachKhachHang.length(); j++) {
                            JSONObject joKhachHang = arrResultDanhSachKhachHang.getJSONObject(j);
                            TenKH tenKH = new TenKH(joKhachHang.getString("id"), joKhachHang.getString("name"));
                            arrTen.add(tenKH);
                            arrName.add(conv.getUTF8StringFromNCR(tenKH.getName()));
//                            arrNameClone.add(conv.getUTF8StringFromNCR(tenKH.getName().replace("_", " ")));
                            String codeKH = conv.getUTF8StringFromNCR(tenKH.getName()).split("_")[0];
                            String nameKH = conv.getUTF8StringFromNCR(tenKH.getName()).split("_")[1];
                            NameKHDisplay nameKHDisplay = new NameKHDisplay(joKhachHang.getString("id"), nameKH, codeKH);
                            arrNameClone.add(nameKHDisplay);
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
                    arr_spinNCC.add(new TenKH(khongChonNhaCungCapId, "Chọn nhà cung cấp"));
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
//            try {
//                String json_sp = control.jsonValues(getJson_listHH(0), false);
//                Log.d("cuong json_sp data", json_sp);
//
//                if (json_sp.contains("api_error") || (json_sp.contains("[]"))) {
//                    Log.d("error json_sp", json_sp);
//                } else {
//                    Type type_sp = new TypeToken<List<SanPham>>() {
//                    }.getType();
//                    // value =0: lay toan bo ds
//                    // Log.i("listhh", json_sp);
//                    List<SanPham> ds_sp = new ArrayList<SanPham>();
//                    try {
//                        ds_sp = (List<SanPham>) new Gson().fromJson(json_sp, type_sp);
//                        Log.i("LIST_SP", String.valueOf(ds_sp.size()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (ds_sp.size() > 0) {
//                        for (int i = 0; i < ds_sp.size(); i++) {
//                            arr_SP.add((ds_sp.get(i)));
//                        }
//                    }
//                }
//            } catch (Exception ex) {
//                Log.d("error json_sp", ex.toString());
//            }

            publishProgress(arr_spinHTTT);

            return null;
        }

        @Override
        protected void onProgressUpdate(List<HTTT>... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            addSpinner();
            addAutoUpdateText();
            Add_Lv_SP();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (pDialog != null)
                dismissWithCheck(pDialog);
        }
    }

    private class GetDHTask extends AsyncTask<Void, List<SanPham>, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tải dữ liệu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            pageCount++;

            try {
                if (totalPages == 0) {
                    try {
                        NorAndNop n = control.getNorNop(getJson_listHH(-1));
                        // check ham xu ly so, truong hop du lieu tra ve la ky tu ko
                        // phai so
                        totalPages = Integer.parseInt(n.getNop());
                    } catch (Exception e) {
                        totalPages = 0;
                    }
                    Log.i(TAG, String.valueOf(totalPages));
                }

                Type type_sp = new TypeToken<List<SanPham>>() {
                }.getType();

                String json_sp = control.jsonValues(getJson_listHH(pageCount), false);
                Log.d("cuong json_sp data", json_sp);

                if (json_sp.contains("api_error")) {
                    Log.d("error json_sp", json_sp);
                } else {
                    Log.i("listhh", json_sp);
                    List<SanPham> ds_sp = new ArrayList<SanPham>();
                    try {
                        ds_sp = (List<SanPham>) new Gson().fromJson(json_sp, type_sp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("arr size", String.valueOf(ds_sp.size()));
                    if (ds_sp.size() > 0) {
                        for (int i = 0; i < ds_sp.size(); i++) {
                            arr_SP.add(ds_sp.get(i));
                        }
                    } else {
                        // xu ly neu size bang 0
                    }
                }
            } catch (Exception ex) {
                Log.d("error doInBackground", ex.toString());
            }

            // publishProgress(arr_SP);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<SanPham>... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            // if (spAdap == null) {
            // DSSPListAdapter.DataTransfer dt = new DataTransfer() {
            //
            // @Override
            // public void setValues(String al) {
            // // TODO Auto-generated method stub
            // et_searchSP.setText(al);
            // }
            // };
            // spAdap = new DSSPListAdapter(activity, values[0], dt);
            // setListAdapter(spAdap);
            // } else {
            // spAdap.notifyDataSetChanged();
            // }
            // getListView().setOnScrollListener(onScrollListener());
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();
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
                        GetDHTask task = new GetDHTask();
                        task.execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

        };
    }

    private class CapNhatDSSP extends AsyncTask<List<SP_SL>, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang cập nhật...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(List<SP_SL>... params) {

            for (int i = 0; i < params[0].size(); i++) {
                String json_result = "";
                String url = getURL_CNDH(getPO_ID(), params[0].get(i));
                Log.i("cuong cndh", url);
                json_result = control.jsonValues(control.convertURL(url), false);
                Log.i("cuong datsp", json_result);
                JSONObject emp;
                String empname = "";

                try {
                    emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                    empname = emp.optString("result");
                    Log.i("cuong json_tbl", empname);
                    publishProgress(empname);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (arr_remove_items.size() > 0) {
                for (int i = 0; i < arr_remove_items.size(); i++) {
                    String json_result = "";
                    Log.i("id_removed", getURL_XoaDH(getPO_ID(), arr_remove_items.get(i)) + " size=" + arr_remove_items.size());
                    json_result = control.jsonValues(control.convertURL(getURL_XoaDH(getPO_ID(), arr_remove_items.get(i))), false);
                    Log.i("removed_result", json_result);
                    JSONObject emp;
                    String empname = "";
                    try {
                        emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                        empname = emp.optString("result");
                        Log.i("cuong json_tbl", empname);
                        // publishProgress(empname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals("")) {
                    Log.i("them_fail", String.valueOf(i));
                } else {
                    Log.i("them_ok", String.valueOf(i));
                }
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();
        }

    }

    // cuongtm thêm
    private void Add_Lv_SP_V2() {
        spAdap1 = new DS_SPAdapter(activity, R.layout.item_list_addsp2, arr_SP_slted, new OnCustomClickListener() {

            @Override
            public void OnCustomClick(View aView, int position) {
                switch (aView.getId()) {
                    case R.id.btn_suaSP:
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
                            if (arr_SP_slted != null && arr_SP_slted.size() >= position) {
                                // cuongtm thêm để cấm thêm khi trạng thái đơn hàng <> 1
                                if (Double.parseDouble(arr_SP_slted.get(position).getDvi().getGia1()) < 0) {
                                    control.showAlertDialog(activity, "Thông báo", "Không được sửa, xóa hàng khuyến mại", false);
                                } else {
                                    ThemSanPham(arr_SP_slted.get(position));
                                }

                                lastPressTime = pressTime;
                            }
                        }
                        break;
                    case R.id.btn_xoaSP:
                        if (arr_SP_slted != null && arr_SP_slted.size() >= position) {
                            // cuongtm thêm để cấm thêm khi trạng thái đơn hàng <> 1
                            if (Double.parseDouble(arr_SP_slted.get(position).getDvi().getGia1()) < 0) {
                                control.showAlertDialog(activity, "Thông báo", "Không được sửa, xóa hàng khuyến mại", false);
                            } else {
                                XoaSanPham(arr_SP_slted.get(position).getSp().getProduct_cat_id());
                            }

                            Log.i("btn", "Item clicked");
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        setListAdapter(spAdap1);
    }

    //

    // cuongtm them
    private void ThemSanPham(final SP_SL spsl) {
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

    private void XoaSanPham(final String id_del) {
        new Thread() {
            public void run() {
                String json_result = "";
                json_result = control.jsonValues(control.convertURL(getURL_XoaSP(getPO_ID(), id_del)), false);
                //if(s)
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

    //
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

    //

    // cuongtm thêm
    private class CapNhatDSSP_V2 extends AsyncTask<Void, List<SPham>, Void> {
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
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            Log.i("add_dssp", String.valueOf(add_dssp.size()));

            if (add_dssp.size() > 0) {
                // add
                for (int i = 0; i < add_dssp.size(); i++) {
                    SPham spham = add_dssp.get(i);
                    // add Dvi
                    String id_dvi = spham.getLoaisp_id();
                    String sluong = "1";// so luong sp tren 1 don vi tinh
                    String ten_dvi = spham.getLoaisp_ten();
                    String gia1 = spham.getGianhap_vnd();
                    String gia2 = spham.getSelling_price_vnd();
                    String sl_dat = spham.getSoluong();
                    // add SanPham
                    String product_cat_id = spham.getLoaisp_id();
                    // add danh sach don vi
                    String ds_donvi = spham.getLoaisp_id() + ";" + sluong + ";" + spham.getDonvi_tinh() + ";" + spham.getGianhap_vnd() + ";" + spham.getGianhap_vnd()
                            + ";";
                    String name = spham.getLoaisp_ten();
                    String soluong = "0";

                    SP_SL add_sp = new SP_SL(new SanPham(product_cat_id, ds_donvi, name, soluong), new DonViSP(id_dvi, sluong, ten_dvi, gia1, gia2), sl_dat);
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
            // Add_Lv_SP();

            // cuongtm theem
            Add_Lv_SP_V2();

//            double tong_tien = 0;
//            double tongtienkm = 0;
            double dongia = 0;
            if (values != null) {
                if (values.length > 0){
                    // txt_km.setText("Khuyến mại: 0 VNĐ");
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
                    }
                }
            }

            txt_tong.setText("Tổng tiền hàng: " + NumbFormatF(tong_tien) + " VNĐ");
            txt_tongtien.setText("Tổng tiền: " + NumbFormatF(tong_tien + tongtienkm) + " VNĐ");
            txt_km.setText("Khuyến mại: " + NumbFormatF(Math.abs(tongtienkm)) + " VNĐ");
            txt_km.setTypeface(null, Typeface.BOLD);
            txtTongTienTruocVAT.setText("Tổng tiền hàng (trước VAT): " + NumbFormatF(tongTienVAT) + " VNĐ");
            txtTongTienTruocVAT.setTypeface(null, Typeface.BOLD);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // thông báo khi tổng tiền âm
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

    //

    private void CapNhatDH(final SP_SL spsl) {

        new Thread() {
            public void run() {
                String json_result = "";
                Log.i("cndh", getURL_CNDH("217", spsl));
                json_result = control.jsonValues(control.convertURL(getURL_CNDH("217", spsl)), false);
                Log.i("datsp", json_result);
                Message msg = Message.obtain();
                msg.what = 3;
                Bundle b = new Bundle();
                b.putString("senddh", json_result);
                Log.i("senddh", json_result);
                msg.setData(b);
                messageHandler.sendMessage(msg);
            }
        }.start();
    }

    private void SearchSP() {

        new Thread() {
            @SuppressWarnings("static-access")
            public void run() {
                String json_result = "";

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

                Message msg = Message.obtain();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("createDH_result", json_result);
                Log.i("Create-DH-result", json_result);
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
                String url = getURL_GuiNCC(getPO_ID());
                Log.i("guincc url", url);
                Log.d("guincc url", url);
                try {
                    json_result = control.jsonValues(control.convertURL(url), false);
                } catch (Exception ex) {
                    Log.d("error json", ex.toString());
                }

                Log.i("guincc dsdat", json_result);

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

                // code cũ
//                json_result = control.jsonValues(control.convertURL(getURL_SuaDH()), false);

                // code sửa để hiển thị tiếng việt có dấu
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
                    // pDialog.dismiss();
                    // CheckLapDH = false;
                    Toast.makeText(activity, "Chưa lập được đơn hàng", Toast.LENGTH_SHORT).show();
                } else {
                    // pDialog.dismiss();
                    setPO_ID(empname.substring(4, empname.length()));
                    if (arr_SP_slted.size() > 0) {
                        new CapNhatDSSP().execute(arr_SP_slted);
                    }
                     else {
//                        control.showAlertDialog(activity, "THÔNG BÁO",
//                        "Bạn chưa cập nhật danh mục hàng hóa", true);
                     }
                    Toast.makeText(activity, "Lập đơn hàng thành công, POID= " + getPO_ID(), Toast.LENGTH_SHORT).show();
                    btnTaoDonHang.setVisibility(View.GONE);
                    viewDanhMucHangHoa.setVisibility(View.VISIBLE);
                    layout_addHH.setVisibility(View.VISIBLE);
                }
                pDialog.dismiss();
            } else if (msg.what == 2) {
                String aResponse = (String) (msg.getData().getString("searchsp").trim());
                Type type_sp = new TypeToken<List<SanPham>>() {
                }.getType();
                List<SanPham> ds_sp = new ArrayList<SanPham>();
                try {
                    ds_sp = (List<SanPham>) new Gson().fromJson(aResponse, type_sp);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                Log.i("sp_size", String.valueOf(ds_sp.size()));

                arr_SP = new ArrayList<SanPham>();
                arr_SP.clear();

                if (ds_sp != null && ds_sp.size() > 0) {
                    for (int i = 0; i < ds_sp.size(); i++) {
                        arr_SP.add(ds_sp.get(i));
                    }
                    if (arr_SP != null && arr_SP.size() > 0) {
                        // update the adapter
                        act_sp_adap = new CusACTAdapter(activity, R.layout.item_list_acv, arr_SP, new DataTransfer() {

                            @Override
                            public void setValues(String pos) {
                                // TODO Auto-generated method stub
                            }
                        });
                        act_sp.setAdapter(act_sp_adap);
                        act_sp_adap.notifyDataSetChanged();
                    }

                } else {
//                    Toast.makeText(activity, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
                /*
                 * else if (ds_sp.size() == 0) { Toast.makeText(activity, "Ko tim thay san pham", Toast.LENGTH_LONG).show(); }
				 */
            } else if (msg.what == 3)

            {
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
                    Toast.makeText(activity, "Chưa Thêm được sp", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Thêm SP thành công", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 4)

            {
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
                    pDialog.dismiss();
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
//                    control.showAlertDialog(activity, "Gửi đơn hàng thành công", "Đơn hàng đã được gửi đến nhà cung cấp", true);
                } else {
                    pDialog.dismiss();
                    Toast.makeText(activity, "Chưa gửi được đến nhà cung cấp", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 5)

            {
                String aResponse = (String) (msg.getData().getString("del_sp").trim());
                JSONObject emp;
                String empname = "";
                try {
                    // emp = new JSONObject(aResponse.substring(1,
                    // aResponse.length() - 1));
                    // empname = emp.optString("result");

                    // cuongtm them
                    empname = aResponse;
                    Log.i("json_tbl", empname);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    Toast.makeText(activity, "Chưa xóa được sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();

                    // new CapNhatDSSP().execute();
                    // cuongtm them
                    new CapNhatDSSP_V2().execute();
                }
            } else if (msg.what == 6)

            {
                String aResponse = (String) (msg.getData().getString("edit_sp").trim());
                JSONObject emp;
                String empname = "";
                try {
                    // emp = new JSONObject(aResponse.substring(1,
                    // aResponse.length() - 1));
                    //
                    // empname = emp.optString("result");
                    empname = aResponse; // TRUE|44,000|4
                    Log.i("json_tbl", empname);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (empname.equals("")) {
                    Toast.makeText(activity, "Chưa cập nhật được sản phẩm", Toast.LENGTH_SHORT).show();
                    new CapNhatDSSP().execute();
                } else {
                    Toast.makeText(activity, "Đã cập nhật sản phẩm", Toast.LENGTH_SHORT).show();

                    // new CapNhatDSSP().execute();

                    // cuongtm them
                    new CapNhatDSSP_V2().execute();

                }
            } else if (msg.what == 7)

            {
                String aResponse = (String) (msg.getData().getString("suaDH").trim());
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
                    Toast.makeText(activity, "Sửa thông tin đơn hàng ko thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Đã sửa thông tin đơn hàng", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 8) {
                String aResponse = (String) (msg.getData().getString("searchkh").trim());
                Type type_kh = new TypeToken<List<DSKHModel>>() {
                }.getType();
                List<DSKHModel> ds_kh = new ArrayList<>();
                try {
                    ds_kh = new Gson().fromJson(aResponse, type_kh);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arr_search_dskh = new ArrayList<DSKHModel>();
                if (ds_kh.size() > 0) {
                    for (int i = 0; i < ds_kh.size(); i++) {
                        arr_search_dskh.add(ds_kh.get(i));
                    }
                    if (arr_search_dskh != null) {
                        act_search_kh_adapter = new ACTSearchKHAdapter(activity, R.layout.item_list_acv, arr_search_dskh);
                        act_tenCH.setAdapter(act_search_kh_adapter);
                    }
                }
            }else if(msg.what == 9){

                // fake du lieu de test
//                ChuongTrinhKhuyenMaiResponse khuyenMai_1 = new ChuongTrinhKhuyenMaiResponse("22", "Khuy&#7871;n m&#7841;i", "1", "K&#237;ch ho&#7841;t", "07/03/2017-31/03/2017","description", "&#205;ch nh&#7845;t","2", "22/x1", "baoht-15/03/2017","0");
//                ChuongTrinhKhuyenMaiResponse khuyenMai_2 = new ChuongTrinhKhuyenMaiResponse("23", "Khuy&#7871;n m&#7841;i", "1", "K&#237;ch ho&#7841;t", "07/03/2017-31/03/2017","description", "&#205;ch nh&#7845;t","2", "22/x1", "baoht-15/03/2017","0");
//                ChuongTrinhKhuyenMaiResponse khuyenMai_3 = new ChuongTrinhKhuyenMaiResponse("24", "Khuy&#7871;n m&#7841;i", "1", "K&#237;ch ho&#7841;t", "07/03/2017-31/03/2017","description", "&#205;ch nh&#7845;t","2", "22/x1", "baoht-15/03/2017","0");
//                danhSachChuongTrinhKhuyenMai.add(khuyenMai_1);
//                danhSachChuongTrinhKhuyenMai.add(khuyenMai_2);
//                danhSachChuongTrinhKhuyenMai.add(khuyenMai_3);
                // end

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
                    height = (int)(height * 1.1);
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
        if (arr_spinHTTT != null && arr_spinHTTT.size() > 0) {
            spin_HTTT.setAdapter(new SpinAdapHTTT(activity, R.layout.spinner_row, arr_spinHTTT));
            spin_HTTT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                    // Toast.makeText(activity, arr_spinHTTT.get(pos).getHttt_id() + " - " + arr_spinHTTT.get(pos).getHinhthuc(),
                    // Toast.LENGTH_SHORT).show();
                    setValueSpinHTTT(arr_spinHTTT.get(pos).getHttt_id());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }
        if (arr_spinNCC != null && arr_spinNCC.size() > 0) {
            spin_NCC.setAdapter(new SpinAdapNCC(activity, R.layout.spinner_row, arr_spinNCC));
            spin_NCC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long arg3) {
                    // Toast.makeText(activity, arr_spinNCC.get(pos).getId() + " - " + arr_spinNCC.get(pos).getName(),
                    // Toast.LENGTH_SHORT).show();
                    setValueSpinNCC(arr_spinNCC.get(pos).getId());
                    nhacungcap = arr_spinNCC.get(pos).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
        }
    }

    private View.OnClickListener handleClick = new View.OnClickListener() {
        @SuppressWarnings("unchecked")
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ngayDH:
                    showDatePicker(et_ngayDH);
                    break;
                case R.id.btn_ngayYCCH:
                    showDatePicker(et_ngayYCCH);
                    break;
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
                        if (Config.isEmptyEdittext2) {
                            Toast.makeText(activity, "Số lượng sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
                        } else {
                            hideKeyboard();
                            if (NetworkType.internetIsAvailable(activity)) {
                                //if (!getPO_ID().equals("")) {
                                    boolean bSanPhamTrung = false;

                                    ArrayList<String> danhSachIdSanPhanTrung = new ArrayList<>();

                                    if(arr_SP_slted.size() >= 2) {
                                        for (int i = 0; i < arr_SP_slted.size() - 1; i++) {
                                            for (int j = i + 1; j < arr_SP_slted.size(); j++) {
                                                if  ((arr_SP_slted.get(i).getDvi().getId().equals(arr_SP_slted.get(j).getDvi().getId()))) {
                                                    bSanPhamTrung = true;
                                                    if(!danhSachIdSanPhanTrung.contains(arr_SP_slted.get(i).getDvi().getId())) {
                                                        danhSachIdSanPhanTrung.add(arr_SP_slted.get(i).getDvi().getId());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if(!bSanPhamTrung) {
                                        danhSachIdSanPhanTrung.clear();
                                        if (arr_SP_slted.size() > 0) {
                                            new CapNhatDSSP().execute(arr_SP_slted);
                                        }
                                        if (!getPO_ID().equals("")) {
                                            SuaDonHang();
                                        }
                                        btnGuiDH.setVisibility(View.VISIBLE);
                                        btnKM.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        String stringHienThiIdSanPhamTrung = "";
                                        if(danhSachIdSanPhanTrung.size() > 0){
                                            for(int i = 0; i < danhSachIdSanPhanTrung.size(); i++){
                                                stringHienThiIdSanPhamTrung += "  " + danhSachIdSanPhanTrung.get(i);
                                            }
                                        }
                                        control.showAlertDialog(activity, "THÔNG BÁO",
                                                "Không cập nhật được, có các sản phẩm trùng mã hàng:" + stringHienThiIdSanPhamTrung, false);

                                    }

                            } else {
                                // Get data don hang
                                setValueNgayDH(et_ngayDH.getText().toString().trim());
                                setValueNgayGH(et_ngayYCCH.getText().toString().trim());
                                try {
                                    String sDiaChi = URLEncoder.encode(et_diachiGH.getText().toString().trim(), "UTF-8");
                                    String sGhiChu = URLEncoder.encode(et_ghichu.getText().toString().trim(), "UTF-8");
                                    setValueDc(sDiaChi);
                                    setValueGhiChu(sGhiChu);
                                    // setValueGhiChu();
                                }catch(Exception ex){
                                    Log.d("Encode fail:", ex.toString());
                                    setValueDc(et_diachiGH.getText().toString().trim());
                                    setValueGhiChu(et_ghichu.getText().toString().trim());
                                }
                                String dvdh = getID();
                                String ncc = getValueSpinNCC();
                                String ngaydh = getValueNgayDH();
                                String diachi = getValueDc();
                                String ghichu = getValueGhiChu();
                                String ngayyc = getValueNgayGH();
                                String phieugiao = "";
                                String httt = getValueSpinHTTT();
                                DatHang dh = new DatHang("", dvdh, ncc, ngaydh, diachi, ghichu, ngayyc, phieugiao, httt);
                                long donhangId = new DatHangDAL(activity).add(dh);
                                Log.i("DON-HANG-ID", "" + donhangId);

                                if (donhangId > 0) {
                                    // Get danh sach san pham don hang
                                    int flag = 0;
                                    if (arr_SP_slted.size() >= 0) {

                                        SanPhamDonHangDAL dal = new SanPhamDonHangDAL(activity);
                                        for (int i = 0; i < arr_SP_slted.size(); i++) {
                                            String loaisp_id = arr_SP_slted.get(i).getDvi().getId();
                                            String soluong = arr_SP_slted.get(i).getSl_dat();
                                            String gia = arr_SP_slted.get(i).getDvi().getGia1();

                                            SanPhamDonHangEntity sanpham = new SanPhamDonHangEntity(String.valueOf(donhangId), loaisp_id, soluong, gia);
                                            long resultInsert = dal.add(sanpham);
                                            if (resultInsert > 0) {
                                                flag++;
                                                Log.i("INSERT-SPDH", "Success " + resultInsert);
                                            } else {
                                                Log.i("INSERT-SPDH", "Failure");
                                            }
                                        }
                                    }
                                    if (arr_SP_slted.size() == flag) {
                                        control.showAlertDialog(activity, "Thông báo", "Lập đơn hàng thành công", true);
                                        btnCNhat.setEnabled(false);

                                        // Them vao bang Donhang
                                        DonHang donhang = new DonHang();
                                        donhang.setDonnhap_id("" + donhangId);
                                        try {
                                            String ngaylap = Utilities.getDate(System.currentTimeMillis(), "dd/MM/yyyy");
                                            donhang.setNgay_lap(ngaylap);
                                            donhang.setNguoi_lap(Config.username);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        donhang.setTrangthai("M&#7899;i l&#7853;p");
                                        donhang.setTrangthai_id("1");
                                        String tenKH = getNameKH();

                                        String[] arrTenKh = tenKH.split("_");
                                        // sửa lỗi array index out of bounds exception arrTenKh
                                        if(arrTenKh.length > 1) {
                                            donhang.setOrderer_code(arrTenKh[0].toString());
                                            donhang.setOrderer_name(arrTenKh[1].toString());
                                        } else{
                                            Log.d("LỖI ĐỊNH DẠNG TÊN KH: ", arrTenKh[0]);
                                        }
                                        // end
                                        donhang.setSupplier_name(nhacungcap);
                                        donhang.setTongtien(NumbFormatF(String.valueOf(tongtienhang)));
                                        donhang.setTong_khuyenmai("0");
                                        donhang.setNote(ghichu);

                                        long insertResult = new DonHangDAL(activity).add(donhang);
                                        if (insertResult > 0) {
                                            Log.i("DON-HANG-OFFLINE-INSERT", "Success");
                                        } else {
                                            Log.i("DON-HANG-OFFLINE-INSERT", "Failure");
                                        }
                                    }
                                } else {
                                    control.showAlertDialog(activity, "Thông báo", "Lập đơn hàng thất bại", false);
                                }
                            }
                        }
                        lastPressTime = pressTime;
                    }
                    break;
                case R.id.btnKM:
                    // kiem tra voi truong hop khuyen mai tu dong da lua chon trong danh sach khuyen mai hay chua
                    eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
                    if(loaiKhuyenMai.equals("2") && bCoKhuyenMai
                            && (eventDanhSachLưaChonKhuyenMai == null || eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai().size() == 0)){
                        control.showAlertDialog(activity, "THÔNG BÁO",
                                "Bạn chưa lựa chọn chương trình khuyến mại!", false);
                    }else {
                        // po_id, httt_id, khuyenmai
                        tinhKhuyenmaiTask task = new tinhKhuyenmaiTask();
                        task.execute();
                    }
                    break;
                case R.id.btnClose:
                    Config.isOpenNew = false;
                    activity.onBackPressed();
                    break;
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
                            GuiNCC();
                            lastPressTime = pressTime2;
                        }
                    }
                    break;
                case R.id.txt_title_ldh:
                    if (ldh_is_visible) {
                        layout_ldh.setVisibility(View.GONE);
                        ldh_is_visible = false;
                    } else {
                        layout_ldh.setVisibility(View.VISIBLE);
                        ldh_is_visible = true;
                    }
                    break;
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
                if(eventDanhSachLưaChonKhuyenMai != null ) {
                    for (ChuongTrinhKhuyenMaiResponse itemKhuyenMai : eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai()) {
                        idKhuyenMai += itemKhuyenMai.getId() + ",";
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
                    JSONObject jsonObject = new JSONObject(values[0]);
                        String result = jsonObject.getString("result");
                        Log.d("cuong json result", result);
                        String[] a = result.split("\\|"); // Do | là ký tự đặc biệt, cần đưa thêm \\ vào
                        if (a[0].trim().toUpperCase().contains("OK")) {
                            control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại hoàn tất!", true);
                            if (NetworkType.internetIsAvailable(activity)) {
                                // Load lại dữ liệu
                                DoActionTask tsk1 = new DoActionTask();
                                tsk1.execute();
                            }
                        } else {
                            control.showAlertDialog(activity, "Thông báo", "Tính khuyến mại không thành công. Vui lòng thử lại!", false);
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
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }


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

            DoAction dact = new DoAction("", "getkm", "pos_po", "promotion_amount", "", " id=" + getPO_ID());

            json1 = "&json=" + new Gson().toJson(dact);

            url = url + json1;
            String json_result = "";
            try {
                Log.d("doaction getkm url ", url);
                json_result = control.getDataJSON(control.convertURL(url), true);
            } catch (Exception ex) {
                Log.d("get tien khuyenmai", url + ". Kq=" + ex.toString());
            }

            Log.d("get tien khuyenmai", url + ". Kq=" + json_result);
            publishProgress(json_result);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            if (values[0] != null) {
                try {
                    JSONObject jsonObject = new JSONObject(values[0]);
                        String result = jsonObject.getString("promotion_amount");
                        Log.d("cuong giatri=", result);
                        // txt_km.setText(result);
                        if (mAF.isNumeric(result)) {
                            txt_km.setText("Khuyến mại: " + NumbFormatF(Double.parseDouble(result)) + " VNĐ");
                        } else {
                            txt_km.setText("Khuyến mại: 0");
                        }

                        // new CapNhatDSSP().execute();
                        // cuongtm theem
                        new CapNhatDSSP_V2().execute();
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
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }

        }
    }

    // End lay tien khuyen mai

    public String NumbFormatF(double numb) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(numb);
    }

    private void showDatePicker(final EditText e) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                e.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, monthOfYear, year);
                long dtDob = chosenDate.toMillis(true);
                CharSequence strDate = DateFormat.format("dd/MM/yyyy", dtDob);
                // Toast.makeText(activity,
                // "Date picked: " + strDate,
                // Toast.LENGTH_SHORT).show();
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    @SuppressWarnings("static-access")
    private void addAutoUpdateText() {
        try {
            final NameKHAdapter tempArr = new NameKHAdapter(activity, android.R.layout.simple_list_item_1, arrNameClone);
//            final ArrayAdapter<NameKHDisplay> tempArr = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, arrNameClone);
            // act_tenCH.setText(KH.getName());
            if (arrNameClone != null && arrNameClone.size() > 0)
                act_tenCH.setAdapter(tempArr);
            act_tenCH.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*
                     * Toast.makeText(activity, "ID=" + getIdKH(conv.GetNCRDecimalString(act_tenCH.getText().toString())), Toast.LENGTH_SHORT).show();
					 */
                    //00032 Hoàng Quốc Phương, H568 Bdasdasks sdhasdjh asdhasdj asdhasudhasd
                    //00032_Hoàng Quốc Phương
                    String name = arrNameClone.get(position).getCode() + "_" + arrNameClone.get(position).getName();
//                    Log.i("posi", String.valueOf(position));
//                    String[] partName = act_tenCH.getText().toString().split(" ");
//                    String partID = partName[0];
//                    String tmp = act_tenCH.getText().toString().replace(partID + " ", "");
//                    String name = partID + "_" + tmp;
//                    setID(getIdKH(conv.GetNCRDecimalString(name)));
                    act_tenCH.setText(name);
                    pID = arrNameClone.get(position).getId();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!Config.isNewCustomer) {
            String nameKH = conv.getUTF8StringFromNCR(getNameKH());
            act_tenCH.setText(nameKH);
        } else {
            String nameKH = Config.NAME_KH;
//			String nameKH = conv.getUTF8StringFromNCR(Config.NAME_KH+"");;
            act_tenCH.setText(nameKH);
        }
        if (Config.isCreateNewDHFromQLDH) {
            act_tenCH.setText("");
        }
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        et_ngayDH.setText(timeStamp);
        et_ngayYCCH.setText(timeStamp);

        act_sp.addTextChangedListener(new SearchSPTextWatcher());
        // // ObjectItemData has no value at first
        List<SanPham> list = new ArrayList<SanPham>();
        // set the custom ArrayAdapter
        act_sp_adap = new CusACTAdapter(context, R.layout.item_list_acv, list);
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
//            if (NetworkType.internetIsAvailable(activity)) {
//                if (getPO_ID().equals("") && isCreateDH == false) {
//                    CreateDonHang();
//                    isCreateDH = true;
//                }
//            }

            String newText = userInput.toString();
            if (!newText.trim().equals("")) {
                Autocompletes_Timer(newText);
                SearchSP();
                if (arr_SP != null && arr_SP.size() > 0) {

                    act_sp.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (arr_SP.size() > position) {
                                dsDonVi = arr_SP.get(position).getDs_donvi();
                                if (dsDonVi != null && getArr_dvi(dsDonVi).size() > 0) {
                                    DonViSP dvi_0 = getArr_dvi(dsDonVi).get(0);
                                    String id_dvi = dvi_0.getId();
                                    String sluong = dvi_0.getSluong();
                                    String ten_dvi = dvi_0.getTen_dvi();
                                    String gia1 = dvi_0.getGia1();
                                    String gia2 = dvi_0.getGia2();
                                    String sl_dat = "1";
                                    SP_SL add_sp = new SP_SL(arr_SP.get(position), new DonViSP(id_dvi, sluong, ten_dvi, gia1, gia2), sl_dat);
                                    // sửa mã lỗi #28789, khi thêm mới bị lưu lại mã hãng đã xóa sau khi tính khuyến mại
                                    // spAdap.add(add_sp);
                                    arr_SP_slted.add(add_sp);
                                    // end

                                    arr_remove_items = new ArrayList<String>();
                                    act_sp.setText("");
                                    setListAdapter(spAdap);
                                    spAdap.notifyDataSetChanged();

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

    @SuppressWarnings("static-access")
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
            // Toast.makeText(activity, "ID=" + "longpd", Toast.LENGTH_SHORT).show();
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_themdh2, container, false);
        dateValidator = new DateValidator();
        removeEventKhuyenMai();
        addControl(view);
        addEvents();
        return view;
    }

    // remove event luu lua chon khuyen mai trong Event Bus
    private void removeEventKhuyenMai() {
        EventDanhSachLuaChonKhuyenMai eventDanhSachLuaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
        if(eventDanhSachLuaChonKhuyenMai != null) {
            EventBus.getDefault().removeStickyEvent(eventDanhSachLuaChonKhuyenMai);
        }

        EventTinhKhuyenMai eventTinhKhuyenMai = EventBus.getDefault().getStickyEvent(EventTinhKhuyenMai.class);
        if(eventTinhKhuyenMai != null) {
            EventBus.getDefault().removeStickyEvent(eventTinhKhuyenMai);
        }

    }

    private void addEvents() {
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

        btnTaoDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkType.internetIsAvailable(activity)) {
                    if(!validNullObject())
                        return;
                    if(!checkDateFormateValid()){
                        return;
                    }
                    if(getValueSpinNCC().equals(khongChonNhaCungCapId)){
                        control.showAlertDialog(activity, "Error", "Cần chọn nhà cung cấp trước khi khởi tạo đơn hàng!", false);
                    }
                    else {
                        pDialog = new ProgressDialog(activity);
                        pDialog.setMessage("Đang tạo đơn hàng...");
                        pDialog.setCancelable(false);
                        pDialog.show();
                        CreateDonHang();
                        isCreateDH = true;

                    }
//                    else if (getPO_ID().equals("") && isCreateDH == false) {
//                        pDialog = new ProgressDialog(activity);
//                        pDialog.setMessage("Đang tạo đơn hàng...");
//                        pDialog.setCancelable(false);
//                        pDialog.show();
//                        CreateDonHang();
//                        isCreateDH = true;
//
//                    }
                }
            }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        processGetDSKH();
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

    //giai phap chong crash dialog
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

    public boolean checkDateFormateValid(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);
        if(!dateValidator.validate(et_ngayDH.getText().toString().trim())){
            et_ngayDH.setError("Nhập sai định dạng, ngày đặt hàng phải có định dạng ngày/tháng/năm và có tồn tại theo dương lịch");
            return false;
        }
        if(!dateValidator.validate(et_ngayYCCH.getText().toString().trim())){
            et_ngayYCCH.setError("Nhập sai định dạng, ngày giao hàng phải có định dạng ngày/tháng/năm và có tồn tại theo dương lịch");
            return false;
        }
        // compare date
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dateDatHang = formatter.parse(et_ngayDH.getText().toString().trim());
            Date dateGiaoHang = formatter.parse(et_ngayYCCH.getText().toString().trim());
            if(dateGiaoHang.compareTo(dateDatHang) < 0){
                et_ngayYCCH.setError("Ngày giao hàng phải sau ngày đặt hàng!");
                builder.setMessage("Ngày giao hàng phải sau ngày đặt hàng!");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        et_ngayYCCH.requestFocus();
                    }
                });
                builder.create().show();
                return false;
            }
        }catch (Exception ex){
            return false;
        }

        return true;
    }

    private boolean validNullObject(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Thông báo");
        builder.setCancelable(false);
        if(act_tenCH.getText().toString().trim().equals("")){
            act_tenCH.setError("Phải nhập tên khách hàng");
            builder.setMessage("Phải nhập tên khách hàng!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    act_tenCH.requestFocus();
                }
            });
            builder.create().show();
            return false;
        } else if(et_ngayDH.getText().toString().trim().equals("")){
            et_ngayDH.setError("Trường bắt buộc không được để trống");
            builder.setMessage("Ngày đặt hàng không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_ngayDH.requestFocus();
                }
            });
            builder.create().show();
            return false;
        }
        else if(et_ngayYCCH.getText().toString().trim().equals("")){
            et_ngayYCCH.setError("Trường bắt buộc không được để trống");
            builder.setMessage("Ngày giao hàng không được để trống!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    et_ngayYCCH.requestFocus();
                }
            });
            builder.create().show();
            return false;
        }
        return true;
    }
}
