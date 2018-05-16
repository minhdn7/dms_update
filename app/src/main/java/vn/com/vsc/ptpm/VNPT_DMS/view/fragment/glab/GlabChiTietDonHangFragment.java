package vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.glab.DanhSachChiTietDonHangAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.glab.DanhSachNhaCungCapAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.glab.ExpandableListDanhSachThiNghiemAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.config.StringDef;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachChiTietDonHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachNhaCungCapResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachNhomXetNghiemResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachXetNghiemResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.ThongTinDonHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabCapNhatDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabThemMoiDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabChiTietDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachNhaCungCapImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachNhomXetNghiemImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachXetNghiemImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabThongTinDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.glab.PrintActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BaseFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.DateTimePickerFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatDonHangThemMoiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabChiTietDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachNhaCungCapView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachNhomXetNghiemView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachXetNghiemView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabThongTinDonHangView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlabChiTietDonHangFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class GlabChiTietDonHangFragment extends BaseFragment implements IGlabThongTinDonHangView, IGlabChiTietDonHangView, IGlabDanhSachXetNghiemView, IGlabDanhSachNhaCungCapView, IGlabDanhSachNhomXetNghiemView, IGlabCapNhatDonHangView, IGlabCapNhatDonHangThemMoiView {
    private MainActivity activity;
    private GlabChiTietDonHangFragment glabChiTietDonHangFragment;
    Controller control = new Controller(getActivity());
    private OnFragmentInteractionListener mListener;
    private TextView  btnThemXetNghiem, txtTongTienHang, txtKhuyenMai, txtTongTien;

    @NotEmpty(trim = true, message = "Trường bắt buộc không được để trống")
    private EditText  txtNgayHenTra;

    @NotEmpty(trim = true, message = "Trường bắt buộc không được để trống")
    private TextView  txtNgayGioLayMau;

    @NotEmpty(trim = true, message = "Họ tên không được để trống")
    private EditText  txtHoTen;
    @NotEmpty(trim = true, message = "Năm không được để trống")
    private EditText txtNam;
    @NotEmpty(trim = true, message = "Địa chỉ không được để trống")
    private EditText txtDiaChi;

    private EditText txtEmail;
    private EditText txtGhiChu;

    @NotEmpty(trim = true, message = "Điện thoại không được để trống")
    private EditText txtDienThoai;

    @NotEmpty(trim = true, message = "Chuẩn đoán không được để trống")
    private EditText txtChuanDoan;

    private EditText txtTienKhuyenMai;

    @NotEmpty(trim = true, message = "Phải chọn nhà phân phối")
    private AutoCompleteTextView txtNhaPhanPhoi;

    private RadioButton ckGioiTinhNam, ckGioiTinhNu;
    private Button btnCapNhat, btnInBienlai, btnThoat;
    private ListView lvDanhSachDonhang;
    private static String poId = "";
    private String idGioiTinh = "1";
    private int pageNo = 1;
    private int pageRec = 100;
    private int idNhaCungCap = -1;
    private String tenNhaCungCap = "";
    private String tenLoaiSanPham = "";
    private int year, month, day, hour, min;

    private GlabThongTinDonHangImpl thongTinDonHangPresenter;
    private GlabChiTietDonHangImpl chiTietDonHangPresenter;
    private GlabDanhSachXetNghiemImpl danhSachXetNghiemPresenter;
    private GlabDanhSachNhomXetNghiemImpl danhSachNhomXetNghiemPresenter;
    private GlabDanhSachNhaCungCapImpl danhSachNhaCungCapPresenter;
    private List<DanhSachChiTietDonHangResponse> chiTietDonHangResponses;
    private GlabThemMoiDonHangImpl themMoiDonHangPresenter;
    private GlabCapNhatDonHangImpl capNhatDonHangPresenter;
    private DanhSachChiTietDonHangAdapter chiTietDonHangAdapter;
    private List<DanhSachXetNghiemResponse> danhSachXetNghiem;
    private Button btnScan, btnDisable, btnExitPrinter, btnPrint;

    ExpandableListDanhSachThiNghiemAdapter listExpendableAdapter;
    ExpandableListDanhSachThiNghiemAdapter listExpendableAdapter2;
    ExpandableListView expandListDanhSachThiNghiem;
    List<DanhSachNhomXetNghiemResponse> listDataHeader;
//    HashMap<DanhSachNhomXetNghiemResponse, List<GroupListModel>> listDataChild;
    HashMap<DanhSachNhomXetNghiemResponse, List<DanhSachXetNghiemResponse>> listDataChild;
    HashMap<DanhSachNhomXetNghiemResponse, List<DanhSachXetNghiemResponse>> listDataChild2;
    List<DanhSachNhaCungCapResponse> danhSachNhaCungCap;
    List<DanhSachNhomXetNghiemResponse> danhSachNhomXetNghiemResponses;
    DanhSachNhaCungCapAdapter danhSachNhaCungCapAdapter;
    private double tongTienHang = 0;
    private double tongTien = 0;
    private double tienKhuyenMai = 0;
    // setup printer
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    // end

    @SuppressLint("ValidFragment")
    public GlabChiTietDonHangFragment(String poId) {
        this.poId = poId;
        this.idNhaCungCap = -1;
        this.tenNhaCungCap = "";
    }

    @SuppressLint("ValidFragment")
    public GlabChiTietDonHangFragment(int idNhaCungCap, String tenNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.poId = "";
    }

    public GlabChiTietDonHangFragment() {
        // Required empty public constructor
        this.poId = "";
        this.idNhaCungCap = -1;
        this.tenNhaCungCap = "";
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glab_chi_tiet_don_hang, container, false);
        addControls(view);
        addEvents();
        try{
            danhSachNhaCungCapPresenter.getDanhSachNhaCungCap();
            danhSachNhomXetNghiemPresenter.getDanhSachNhomXetNghiem();
            if(!poId.equals("")){
                activity.showProgressBar();
                thongTinDonHangPresenter.getThongTinDonHang(Integer.parseInt(poId));
                chiTietDonHangPresenter.getChiTietDonHang(pageNo, pageRec, Integer.parseInt(poId), tenLoaiSanPham);
            }else {

            }
        }catch (Exception ex){

        }

        return view;
    }

    private void addEvents() {
        txtNgayGioLayMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment(txtNgayGioLayMau);
                dateTimePickerFragment.show(fragmentManager, "dateTimePickerFragment");
            }
        });

        txtNgayHenTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                DateTimePickerFragment dateTimePickerFragment = new DateTimePickerFragment(txtNgayHenTra);
                dateTimePickerFragment.show(fragmentManager, "dateTimePickerFragment");
            }
        });

        ckGioiTinhNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idGioiTinh = "1";
                ckGioiTinhNu.setChecked(false);
            }
        });

        ckGioiTinhNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idGioiTinh = "2";
                ckGioiTinhNam.setChecked(false);
            }
        });

        btnThemXetNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idNhaCungCap > 0){
                    activity.showProgressBar();
                    danhSachXetNghiemPresenter.getDanhSachXetNghiem(pageNo, pageRec, idNhaCungCap);
                }else {
                    txtNhaPhanPhoi.setError("Cần chọn nhà cung cấp!");
                    txtNhaPhanPhoi.setFocusable(true);
                    txtNhaPhanPhoi.requestFocus();
                }
            }
        });

        txtNhaPhanPhoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idNhaCungCap = Integer.parseInt(danhSachNhaCungCapAdapter.getItem(position).getId());
                if(danhSachXetNghiem.size() > 0){
                    danhSachXetNghiem.clear();
                    if(chiTietDonHangAdapter != null){
                        chiTietDonHangAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
                if(!isPassedValidate){
                    return;
                }else if(!checkValid()){
                    return;
                }
                if(tongTien < 0){
                    control.showAlertDialog(
                            getActivity(),
                            "Thông báo",
                            "Tổng tiền hàng sau khuyến mại đang có giá trị âm. Đề nghị kiểm tra lại!",
                            false);
                    return;
                }
                activity.showProgressBar();
                String arrayProductId = "";
                if(!poId.equals("")){
                    GlabCapNhatDonHangRequest capNhatDonHangRequest = new GlabCapNhatDonHangRequest();
                    capNhatDonHangRequest.setId(poId);
                    capNhatDonHangRequest.setNcc(String.valueOf(idNhaCungCap));
                    capNhatDonHangRequest.setNgaydh(txtNgayGioLayMau.getText().toString().trim());
                    capNhatDonHangRequest.setFullname(txtHoTen.getText().toString().trim());
                    capNhatDonHangRequest.setDob(txtNam.getText().toString().trim());
                    capNhatDonHangRequest.setGtinh(idGioiTinh);
                    capNhatDonHangRequest.setDiachi(txtDiaChi.getText().toString().trim());
                    capNhatDonHangRequest.setEmail(txtEmail.getText().toString().trim());
                    capNhatDonHangRequest.setMoblie(txtDienThoai.getText().toString().trim());
                    capNhatDonHangRequest.setDiengiai(txtChuanDoan.getText().toString().trim());
                    capNhatDonHangRequest.setNgayyc(txtNgayHenTra.getText().toString().trim());
                    capNhatDonHangRequest.setPhieugiao("");
                    capNhatDonHangRequest.setTien_km(txtTienKhuyenMai.getText().toString().trim());
                    capNhatDonHangRequest.setProduct_cat_id("");
                    capNhatDonHangRequest.setGhiChu(txtGhiChu.getText().toString().trim());
                    if(danhSachXetNghiem.size() > 0){

                        for(int i = 0; i < danhSachXetNghiem.size(); i++){
                            if(i > 0){
                                arrayProductId += ", ";
                            }
                            arrayProductId += danhSachXetNghiem.get(i).getProductCatId();
                        }
                        capNhatDonHangRequest.setProduct_cat_id(arrayProductId);

                    }
                    capNhatDonHangPresenter.getCapNhatDonHang(capNhatDonHangRequest);

                }else {
                    GlabThemMoiDonHangRequest themMoiDonHangRequest = new GlabThemMoiDonHangRequest();
                    themMoiDonHangRequest.setNcc(String.valueOf(idNhaCungCap));
                    themMoiDonHangRequest.setNgaydh(txtNgayGioLayMau.getText().toString().trim());
                    themMoiDonHangRequest.setFullname(txtHoTen.getText().toString().trim());
                    themMoiDonHangRequest.setDob(txtNam.getText().toString().trim());
                    themMoiDonHangRequest.setGtinh(idGioiTinh);
                    themMoiDonHangRequest.setDiachi(txtDiaChi.getText().toString().trim());
                    themMoiDonHangRequest.setEmail(txtEmail.getText().toString().trim());
                    themMoiDonHangRequest.setMoblie(txtDienThoai.getText().toString().trim());
                    themMoiDonHangRequest.setDiengiai(txtChuanDoan.getText().toString().trim());
                    themMoiDonHangRequest.setNgayyc(txtNgayHenTra.getText().toString().trim());
                    themMoiDonHangRequest.setPhieugiao("");
                    themMoiDonHangRequest.setTienKm(txtTienKhuyenMai.getText().toString().trim());
                    themMoiDonHangRequest.setProductCatId("");
                    themMoiDonHangRequest.setGhiChu(txtGhiChu.getText().toString().trim());
                    if(danhSachXetNghiem.size() > 0){
                        for(int i = 0; i < danhSachXetNghiem.size(); i++){
                            if(i > 0){
                                arrayProductId += ", ";
                            }
                            arrayProductId += danhSachXetNghiem.get(i).getProductCatId();
                        }
                        themMoiDonHangRequest.setProductCatId(arrayProductId);
                    }
                    themMoiDonHangPresenter.getCapNhatDonHangThemMoi(themMoiDonHangRequest);
                }
            }
        });

        btnInBienlai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                printerDialog();
                Intent intent = new Intent(getActivity(),
                        PrintActivity.class);
                intent.putExtra("PO_ID", poId);
                startActivity(intent);

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
                try{
                    if(!txtTienKhuyenMai.getText().toString().trim().equals("")){
                        try{
                            tienKhuyenMai = Double.parseDouble(txtTienKhuyenMai.getText().toString().trim().replaceAll(",",""));
                        }catch (Exception ex){
                            tienKhuyenMai = Double.parseDouble(txtTienKhuyenMai.getText().toString().trim());
                        }

                        tongTien = tongTienHang - tienKhuyenMai;
                        String formattedPriceTienKhuyenmai  = new DecimalFormat("##,##0.00 VND").format(tienKhuyenMai);
                        String sTienKhuyenMai = StringDef.khuyenMai + formattedPriceTienKhuyenmai;
                        txtKhuyenMai.setText(sTienKhuyenMai);

                        String formattedPriceTongTien  = new DecimalFormat("##,##0.00 VND").format(tongTien);
                        String sTongTien = StringDef.tongTienHang + formattedPriceTongTien;
                        txtTongTien.setText(sTongTien);
                    }else {
                        tienKhuyenMai = 0;
                        tongTien = tongTienHang - tienKhuyenMai;
                        String sTienKhuyenMai = StringDef.khuyenMai + txtTienKhuyenMai.getText().toString();
                        txtKhuyenMai.setText(sTienKhuyenMai);
                        String formattedPriceTongTien  = new DecimalFormat("##,##0.00 VND").format(tongTien);
                        String sTongTien = StringDef.tongTienHang + formattedPriceTongTien;
                        txtTongTien.setText(sTongTien);
                    }
                }catch (Exception ex){

                }


            }
        });
    }

    private void addControls(View view) {
        activity = (MainActivity) getActivity();

        thongTinDonHangPresenter = new GlabThongTinDonHangImpl(this);
        chiTietDonHangPresenter = new GlabChiTietDonHangImpl(this);
        danhSachXetNghiemPresenter = new GlabDanhSachXetNghiemImpl(this);
        danhSachNhomXetNghiemPresenter = new GlabDanhSachNhomXetNghiemImpl(this);
        danhSachNhaCungCapPresenter = new GlabDanhSachNhaCungCapImpl(this);
        themMoiDonHangPresenter = new GlabThemMoiDonHangImpl(this);
        capNhatDonHangPresenter = new GlabCapNhatDonHangImpl(this);
        danhSachXetNghiem = new ArrayList<>();

        chiTietDonHangResponses = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<DanhSachNhomXetNghiemResponse, List<DanhSachXetNghiemResponse>>();
        danhSachNhaCungCap = new ArrayList<>();
        danhSachNhomXetNghiemResponses = new ArrayList<>();
        lvDanhSachDonhang = (ListView) view.findViewById(R.id.lvDanhSachDonhang);
        txtNgayGioLayMau = (TextView) view.findViewById(R.id.txtNgayGioLayMau);
        txtNgayHenTra = (EditText) view.findViewById(R.id.txtNgayHenTra);
        btnThemXetNghiem = (TextView) view.findViewById(R.id.btnThemXetNghiem);

        txtNhaPhanPhoi = (AutoCompleteTextView) view.findViewById(R.id.txtNhaPhanPhoi);
        txtHoTen = (EditText) view.findViewById(R.id.txtHoTen);
        txtNam = (EditText) view.findViewById(R.id.txtNam);
        txtDiaChi = (EditText) view.findViewById(R.id.txtDiaChi);
        txtEmail = (EditText) view.findViewById(R.id.txtEmail);
        txtDienThoai = (EditText) view.findViewById(R.id.txtDienThoai);
        txtChuanDoan = (EditText) view.findViewById(R.id.txtChuanDoan);
        txtTienKhuyenMai = (EditText) view.findViewById(R.id.txtTienKhuyenMai);
        txtGhiChu = (EditText) view.findViewById(R.id.txtGhiChu);

        ckGioiTinhNam = (RadioButton) view.findViewById(R.id.ckGioiTinhNam);
        ckGioiTinhNu = (RadioButton) view.findViewById(R.id.ckGioiTinhNu);

        btnCapNhat = (Button) view.findViewById(R.id.btnCapNhat);
        btnInBienlai = (Button) view.findViewById(R.id.btnInBienlai);
        btnThoat = (Button) view.findViewById(R.id.btnThoat);

        txtTongTienHang = (TextView) view.findViewById(R.id.txtTongTienHang);
        txtKhuyenMai = (TextView) view.findViewById(R.id.txtKhuyenMai);
        txtTongTien = (TextView) view.findViewById(R.id.txtTongTien);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        String time = day + "/" + (month + 1) + "/" + year + " " + hour + ":" + min;
        txtNgayGioLayMau.setText(time);

        // set default chọn giới tính là Nam
        idGioiTinh = "1";
        ckGioiTinhNam.setChecked(true);
        ckGioiTinhNu.setChecked(false);
        if(!tenNhaCungCap.equals("")){
            txtNhaPhanPhoi.setText(tenNhaCungCap);
        }

        // set up tiền khuyến mại

//        prepareListData();
    }

    @Override
    public void onGlabThongTinDonHangSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            ThongTinDonHangResponse[] arrThongTinDonHang = gson.fromJson(jsonElement, ThongTinDonHangResponse[].class);
            List<ThongTinDonHangResponse> response = new ArrayList<>(Arrays.asList(arrThongTinDonHang));
            idNhaCungCap = Integer.parseInt(response.get(0).getDonviCungungId());

            txtHoTen.setText(response.get(0).getTenkh());
            txtNam.setText(response.get(0).getNamsinhkh());
            txtDiaChi.setText(response.get(0).getDichikh());
            txtEmail.setText(response.get(0).getEmailkh());
            txtDienThoai.setText(response.get(0).getMobilekh());
            txtChuanDoan.setText(response.get(0).getDienGiai());
            txtTienKhuyenMai.setText(response.get(0).getTienkm());
            txtNgayGioLayMau.setText(response.get(0).getNgayLap());
            txtNgayHenTra.setText(response.get(0).getNgayGui());
            txtNhaPhanPhoi.setText(response.get(0).getSupplierName());
            txtGhiChu.setText(response.get(0).getChuthich());

            // giới tính 1 - nam,  2 - nữ
            if(response.get(0).getGioitinhkh().trim().equals("1")){
                ckGioiTinhNam.setChecked(true);
                ckGioiTinhNu.setChecked(false);
            }else if(response.get(0).getGioitinhkh().trim().equals("2")){
                ckGioiTinhNam.setChecked(false);
                ckGioiTinhNu.setChecked(true);
            }
            // tiền khuyến mại góc dưới
            String sTongTienHang = StringDef.tongTienHang + response.get(0).getTongtientruockm();
            String sTongTien = StringDef.tongTien + response.get(0).getTongtien();
            txtTongTienHang.setText(sTongTienHang);
            txtKhuyenMai.setText(StringDef.khuyenMai + response.get(0).getTienkm());
            txtTongTien.setText(sTongTien);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
        activity.hideProgressBar();
    }

    @Override
    public void onGlabThongTinDonHangError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình lấy thông tin đơn hàng!",
                false);
    }

    @Override
    public void onGlabChiTietDonHangSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachChiTietDonHangResponse[] arrChiTietThongTinDonHang = gson.fromJson(jsonElement, DanhSachChiTietDonHangResponse[].class);
            chiTietDonHangResponses = new ArrayList<>(Arrays.asList(arrChiTietThongTinDonHang));

            if(chiTietDonHangResponses.size() > 0){
                if(danhSachXetNghiem.size() > 0){
                    danhSachXetNghiem.clear();
                }
                tongTienHang = 0;
                tongTien = 0;
                for(int i = 0; i < chiTietDonHangResponses.size(); i++){
                    DanhSachXetNghiemResponse item = new DanhSachXetNghiemResponse();
                    item.setMaSanPham(chiTietDonHangResponses.get(i).getMaSp());
                    item.setProductCatId(chiTietDonHangResponses.get(i).getLoaispId());
                    item.setName(chiTietDonHangResponses.get(i).getLoaispTen());
                    item.setGiaBan(chiTietDonHangResponses.get(i).getGianhap());
                    item.setGiaban2(chiTietDonHangResponses.get(i).getGianhapVnd());
                    if(!chiTietDonHangResponses.get(i).getDonviTinh().toLowerCase().equals("km")){
                        tongTienHang += Double.parseDouble(item.getGiaban2());
                    }

                    danhSachXetNghiem.add(item);
                }
                chiTietDonHangAdapter = new DanhSachChiTietDonHangAdapter(getActivity(), R.layout.item_glab_danh_sach_xet_nghiem, danhSachXetNghiem, this);
                lvDanhSachDonhang.setAdapter(chiTietDonHangAdapter);
                setListViewHeightBasedOnChildren(lvDanhSachDonhang);
                String formattedPrice  = new DecimalFormat("##,##0.00 VND").format(tongTienHang);
                String sTongTienHang = StringDef.tongTienHang + formattedPrice;
                txtTongTienHang.setText(sTongTienHang);

                String sTienKhuyenMai = StringDef.khuyenMai + txtTienKhuyenMai.getText().toString();
                tienKhuyenMai = Double.parseDouble(txtTienKhuyenMai.getText().toString().trim().replaceAll(",",""));
                txtKhuyenMai.setText(sTienKhuyenMai);

                tongTien = tongTienHang - tienKhuyenMai;
                String formattedPriceTongTien  = new DecimalFormat("##,##0.00 VND").format(tongTien);
                String sTongTien = StringDef.tongTienHang + formattedPriceTongTien;
                txtTongTien.setText(sTongTien);
            }

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }

    }

    @Override
    public void onGlabChiTietDonHangError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách chi tiết đơn hàng",
                false);
    }

    @Override
    public void onGlabDanhSachXetNghiemSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachXetNghiemResponse[] arrDanhSachXetNghiem = gson.fromJson(jsonElement, DanhSachXetNghiemResponse[].class);
            List<DanhSachXetNghiemResponse> response = new ArrayList<>(Arrays.asList(arrDanhSachXetNghiem));

            if(danhSachNhomXetNghiemResponses.size() > 0 && response.size() > 0){
                for(int i = 0; i < danhSachNhomXetNghiemResponses.size(); i++){
//                    List<GroupListModel> listChild = new ArrayList<>();
                    List<DanhSachXetNghiemResponse> listChild = new ArrayList<>();
                    boolean flagCheck = false;
                    for(int j = 0; j < response.size(); j++){

                        if(danhSachNhomXetNghiemResponses.get(i).getId().equals(response.get(j).getGroupId())){
                            flagCheck = true;
//                            listChild.add(new GroupListModel(response.get(j).getName(), response.get(j).getProductCatId()));
                            listChild.add(response.get(j));
                        }
                        else if(flagCheck && !danhSachNhomXetNghiemResponses.get(i).getId().equals(response.get(j).getGroupId())){
                            break;
                        }
                    }
                    listDataChild.put(listDataHeader.get(i), listChild);

                }
                dialogThemXetNghiem();
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Không có danh sách xét nghiệm!",
                        false);
            }

        }catch (Exception ex){
            Log.d("Parse Error: ", ex.toString());
            control.showAlertDialog(
                    getActivity(),
                    "Thông báo",
                    "Lỗi lấy danh sách xét nghiệm!",
                    false);
        }
        activity.hideProgressBar();
    }

    @Override
    public void onGlabDanhSachXetNghiemError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình lấy danh sách xét nghiệm",
                false);
    }

    @Override
    public void onGlabDanhSachNhaCungCapSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachNhaCungCapResponse[] arrDanhSachNhaCungCap = gson.fromJson(jsonElement, DanhSachNhaCungCapResponse[].class);
            danhSachNhaCungCap = new ArrayList<>(Arrays.asList(arrDanhSachNhaCungCap));
            if(danhSachNhaCungCap.size() > 0){
                danhSachNhaCungCapAdapter = new DanhSachNhaCungCapAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachNhaCungCap);
                txtNhaPhanPhoi.setAdapter(danhSachNhaCungCapAdapter);
            }

        }catch (Exception ex){
            Log.d("Parse Error: ", ex.toString());
        }
    }

    @Override
    public void onGlabDanhSachNhaCungCapError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách nhà cung cấp!",
                false);
    }

    @Override
    public void onGlabDanhSachNhomXetNghiemSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachNhomXetNghiemResponse[] arrDanhSachNhaCungCap = gson.fromJson(jsonElement, DanhSachNhomXetNghiemResponse[].class);
            danhSachNhomXetNghiemResponses = new ArrayList<>(Arrays.asList(arrDanhSachNhaCungCap));
            if(danhSachNhomXetNghiemResponses.size() > 0){
              for(int i = 0; i < danhSachNhomXetNghiemResponses.size(); i++){
                  listDataHeader.add(new DanhSachNhomXetNghiemResponse(danhSachNhomXetNghiemResponses.get(i).getId(),danhSachNhomXetNghiemResponses.get(i).getName()));



              }
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Không có danh sách nhóm xét nghiệm!",
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onGlabDanhSachNhomXetNghiemError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình lấy danh sách nhóm xét nghiệm",
                false);
    }

    @Override
    public void onGlabCapNhatDonHangSuccess(Object object) {
        activity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrayResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> listResponse = new ArrayList<>(Arrays.asList(arrayResponse));
            String[] sThongBao = listResponse.get(0).getResult().split("\\|");
            if(sThongBao[0].toLowerCase().equals("true")){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật đơn hàng thành công!",
                        false);
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        sThongBao[1],
                        false);
            }

        }catch (Exception ex){

        }
    }

    @Override
    public void onGlabCapNhatDonHangError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình cập nhật đơn hàng",
                false);
    }

    @Override
    public void onGlabCapNhatDonHangThemMoiSuccess(Object object) {
        activity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrayResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> listResponse = new ArrayList<>(Arrays.asList(arrayResponse));
            String[] sThongBao = listResponse.get(0).getResult().split("\\|");
            if(sThongBao[0].toLowerCase().equals("true")){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật đơn hàng thành công!",
                        false);
                // cập nhật poId mới
                poId = sThongBao[1];
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        sThongBao[1],
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onGlabCapNhatDonHangThemMoiError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình cập nhật đơn hàng",
                false);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    /*
 * Preparing the list data
 */

    public void dialogThemXetNghiem() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_danh_sach_thi_nghiem);
        dialog.setCancelable(false);
        // chỉnh kích cỡ dialog show
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        height = (int) (height * 0.8);
        width = (int) (width * 0.5);
        lp.width = height;
        lp.height = width;

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        // add Controls
        expandListDanhSachThiNghiem = (ExpandableListView) dialog.findViewById(R.id.expandListDanhSachThiNghiem);
        Button btnDong = (Button) dialog.findViewById(R.id.btnDong);
        Button btnChonXetNghiem = (Button) dialog.findViewById(R.id.btnChon);
        // preparing list data

        listExpendableAdapter = new ExpandableListDanhSachThiNghiemAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expandListDanhSachThiNghiem.setAdapter(listExpendableAdapter);
        dialog.show();

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnChonXetNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // list thứ nhất
                if(listExpendableAdapter.danhSachXetNghiemSelect != null && listExpendableAdapter.danhSachXetNghiemSelect.size() > 0) {
                    if (danhSachXetNghiem.size() > 0) {
                        ArrayList<DanhSachXetNghiemResponse> danhSachXetNghiemThemMoi = new ArrayList<DanhSachXetNghiemResponse>();
                        danhSachXetNghiemThemMoi.addAll(danhSachXetNghiem);

                        for (int i = 0; i < listExpendableAdapter.danhSachXetNghiemSelect.size(); i++) {
                            boolean flag = true;
                            for (int j = 0; j < danhSachXetNghiemThemMoi.size(); j++) {
                                if (danhSachXetNghiemThemMoi.get(j).getProductCatId().equals(listExpendableAdapter.danhSachXetNghiemSelect.get(i).getProductCatId())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                danhSachXetNghiem.add(listExpendableAdapter.danhSachXetNghiemSelect.get(i));
                                tongTienHang += Double.parseDouble(listExpendableAdapter.danhSachXetNghiemSelect.get(i).getGiaban2());
                            }

                        }

                    } else {
                        danhSachXetNghiem.addAll(listExpendableAdapter.danhSachXetNghiemSelect);
                        for (DanhSachXetNghiemResponse item : listExpendableAdapter.danhSachXetNghiemSelect) {
                            tongTienHang += Double.parseDouble(item.getGiaban2());
                        }
                    }
                }
                    // end

                    // list thứ 2
//                    if(listExpendableAdapter2.danhSachXetNghiemSelect != null && listExpendableAdapter2.danhSachXetNghiemSelect.size() > 0) {
//                        if (danhSachXetNghiem.size() > 0) {
//                            ArrayList<DanhSachXetNghiemResponse> danhSachXetNghiemThemMoi = new ArrayList<DanhSachXetNghiemResponse>();
//                            danhSachXetNghiemThemMoi.addAll(danhSachXetNghiem);
//
//                            for (int i = 0; i < listExpendableAdapter2.danhSachXetNghiemSelect.size(); i++) {
//                                boolean flag = true;
//                                for (int j = 0; j < danhSachXetNghiemThemMoi.size(); j++) {
//                                    if (danhSachXetNghiemThemMoi.get(j).getProductCatId().equals(listExpendableAdapter2.danhSachXetNghiemSelect.get(i).getProductCatId())) {
//                                        flag = false;
//                                        break;
//                                    }
//                                }
//                                if (flag) {
//                                    danhSachXetNghiem.add(listExpendableAdapter.danhSachXetNghiemSelect.get(i));
//                                    tongTienHang += Double.parseDouble(listExpendableAdapter.danhSachXetNghiemSelect.get(i).getGiaban2());
//                                }
//
//                            }
//
//                        } else {
//                            danhSachXetNghiem.addAll(listExpendableAdapter2.danhSachXetNghiemSelect);
//                            for (DanhSachXetNghiemResponse item : listExpendableAdapter2.danhSachXetNghiemSelect) {
//                                tongTienHang += Double.parseDouble(item.getGiaban2());
//                            }
//                        }
//                    }
                    // end
                    if(chiTietDonHangAdapter != null){
                        chiTietDonHangAdapter.notifyDataSetChanged();
                    }else {
                        chiTietDonHangAdapter = new DanhSachChiTietDonHangAdapter(getActivity(), R.layout.item_glab_danh_sach_xet_nghiem, danhSachXetNghiem, GlabChiTietDonHangFragment.this);
                    }

                    lvDanhSachDonhang.setAdapter(chiTietDonHangAdapter);
                    setListViewHeightBasedOnChildren(lvDanhSachDonhang);
                    try {
                        String formattedPrice  = new DecimalFormat("##,##0.00 VND").format(tongTienHang);
                        String s = StringDef.tongTienHang + formattedPrice;
                        txtTongTienHang.setText(s);
                    }catch (Exception ex){

                    }

                    try {
                        if(!txtTienKhuyenMai.getText().toString().trim().equals("")){
                            String sTienKhuyenMai = StringDef.khuyenMai + txtTienKhuyenMai.getText().toString();
                            tienKhuyenMai = Double.parseDouble(txtTienKhuyenMai.getText().toString().trim().replaceAll(",",""));
                            txtKhuyenMai.setText(sTienKhuyenMai);
                        }

                    }catch (Exception ex){

                    }

                    try {
                        tongTien = tongTienHang - tienKhuyenMai;
                        String formattedPriceTongTien  = new DecimalFormat("##,##0.00 VND").format(tongTien);
                        String sTongTien = StringDef.tongTienHang + formattedPriceTongTien;
                        txtTongTien.setText(sTongTien);
                    }catch (Exception ex){

                    }

                dialog.dismiss();
            }
        });
    }

    public void xoaThongTinXetNghiem(int position){
        danhSachXetNghiem.remove(position);
        tongTienHang = 0;

        if(danhSachXetNghiem.size() > 0){
            for(int i = 0; i < danhSachXetNghiem.size(); i++){
                tongTienHang += Double.parseDouble(danhSachXetNghiem.get(i).getGiaban2());
            }

            String formattedPrice  = new DecimalFormat("##,##0.00 VND").format(tongTienHang);
            String s = StringDef.tongTienHang + formattedPrice;
            txtTongTienHang.setText(s);


            String sTienKhuyenMai = StringDef.khuyenMai + txtTienKhuyenMai.getText().toString();
            tienKhuyenMai = Double.parseDouble(txtTienKhuyenMai.getText().toString().trim().replaceAll(",",""));
            txtKhuyenMai.setText(sTienKhuyenMai);

            tongTien = tongTienHang - tienKhuyenMai;
            String formattedPriceTongTien  = new DecimalFormat("##,##0.00 VND").format(tongTien);
            String sTongTien = StringDef.tongTienHang + formattedPriceTongTien;
            txtTongTien.setText(sTongTien);
        }else {
            txtTongTienHang.setText("");
            txtKhuyenMai.setText("");
            txtTongTien.setText("");
        }

        chiTietDonHangAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(lvDanhSachDonhang);
    }

    private boolean checkValid(){
        if(!txtEmail.getText().toString().equals("")){
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString().trim()).matches()){
                txtEmail.setError("Địa chỉ Email không hợp lệ");
                txtEmail.requestFocus();
                return false;
            }
        }
        if(txtNam.getText().toString().length() != 4 || (txtNam.getText().toString().charAt(0) == '0')){
            txtNam.setError("Năm sinh không hợp lệ");
            txtNam.requestFocus();
            return false;
        }
        if(txtDienThoai.getText().toString().length() < 10 || txtDienThoai.getText().toString().length() > 11){
            txtDienThoai.setError("Số điện thoại không hợp lệ");
            txtDienThoai.requestFocus();
            return false;
        } else if (txtDienThoai.getText().charAt(0) != '0'){
            txtDienThoai.setError("Số điện thoại không hợp lệ");
            txtDienThoai.requestFocus();
            return false;
        }
//        if(txtNgayHenTra.getText().toString().trim().equals("")){
//            txtNgayHenTra.setError("Ngày giờ hẹn trả không được để trống");
//            txtNgayHenTra.requestFocus();
//            return false;
//        }

        return true;
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem itemGhetham = menu.findItem(R.id.action_ghetham);
        MenuItem itemBando = menu.findItem(R.id.action_bando);
        MenuItem itemThongtin = menu.findItem(R.id.action_thongtin);
        MenuItem itemDatHang = menu.findItem(R.id.action_dathang);
        MenuItem itemKhaoSat = menu.findItem(R.id.actionKhaoSat);
        itemGhetham.setVisible(false);
        itemBando.setVisible(false);
        itemThongtin.setVisible(false);
        itemDatHang.setVisible(false);
        itemKhaoSat.setVisible(false);
    }
}
