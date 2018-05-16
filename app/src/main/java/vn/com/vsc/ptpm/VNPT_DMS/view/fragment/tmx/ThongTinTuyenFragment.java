package vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.DanhSachKhachHangDangKyAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.DanhSachDangKyLichTrinhAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.DanhSachKhachHangDeDangKyAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.KhuVucAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.config.CodeDef;
import vn.com.vsc.ptpm.VNPT_DMS.config.StringDef;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.MonthValidator;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.CapNhatIdDangKyRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhachHangDeDangKyRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.DanhSachKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.KhachHangKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDeDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachVungResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.ThongTinDangKyChiTietResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.CapNhatDangKyTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.CapNhatLichTrinhChiTietTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DangKyLichTrinhChiTietTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DangKyLichTrinhTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachDangKyKyLichTrinhTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachIdKhachHangDangKyTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachKhachHangDangKyImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachKhachHangDeDangKyImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachKhuVucImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachQuanImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachTinhImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachVungImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.ThongTinDangKyChiTietTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BaseFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ICapNhatDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ICapNhatLichTrinhChiTietView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDangKyLichTrinhChiTietView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDangKyLichTrinhView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachIdDaDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhachHangDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhachHangDeDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhuVucView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachQuanView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachTinhView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachVungView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IThongTinDangKyChiTietView;

public class ThongTinTuyenFragment extends BaseFragment implements IThongTinDangKyChiTietView, ILayDanhSachKhachHangDangKyView, ILayDanhSachVungView, ILayDanhSachKhuVucView,
        ILayDanhSachTinhView, ILayDanhSachQuanView, ILayDanhSachKhachHangDeDangKyView, ICapNhatDangKyView, IDangKyLichTrinhView, ILayDanhSachIdDaDangKyView, IDangKyLichTrinhChiTietView, ICapNhatLichTrinhChiTietView{
    int pageNo = 1;
    int pageRec = 100;
    int pageNoKhachHangDeDangKy = 1;
    int pageRecKhachHangDeDangKy = 100;
    int stepIndex = 100;
    int iWeek = 0;
    boolean isLoadingMore = true;
    boolean loadingMore = false;
    private String registerId = "";
    private DanhSachKhaoSatFragment.OnFragmentInteractionListener mListener;
    private DanhSachDangKyKyLichTrinhTMXImpl danhSachLichTrinhPresenter;
    private Controller control;
    private List<DanhSachKhaoSatResponse> listDanhSachKhaoSat;
    private DanhSachDangKyLichTrinhAdapter danhSachDangKyLichTrinhAdapter;
    private ListView lvDanhSachDonhang;
    private ListView lvDanhSachKhachHangDeDangKy;
    private KProgressHUD hud;
    private ConvertFont conv = new ConvertFont();
    private int mYear, mMonth, mDay, mWeek;
    private String sDay, sWeek;
    private ImageButton btnThangKhaoSat;
    private EditText txtThang;
    private AutoCompleteTextView txtTenKhachHang;
    private Button btnSearch, btnThoat, btnGhiLai;
    private TextView btnThemXetNghiem;
    private ArrayList<CapNhatIdDangKyRequest> danhSachIdDangKy = new ArrayList<>();
    private String idKhachHang = "";
    private String danhSachidKhachHang = "";
    private List<String> listIdKhachHang = new ArrayList<>();
    private MainActivity mainActivity;
    private KhachHangKhaoSatAdapter adapterKhachHang;
    private List<KhachHangKhaoSatResponse> danhSachKhachHang;
    private MonthValidator monthValidator;
    private EditText txtGhiChu;
    private TextView txtTrangThai, txtTuan, txtNgayThucHien, txtNhanVienKinhDoanh;

    private ThongTinDangKyChiTietTMXImpl thongTinDangKyChiTietPresenter;
    private DanhSachKhachHangDangKyImpl danhSachKhachHangDangKyPresenter;
    private DanhSachKhuVucImpl danhSachKhuVucPresenter;
    private DanhSachVungImpl danhSachVungPresenter;
    private DanhSachTinhImpl danhSachTinhPresenter;
    private DanhSachQuanImpl danhSachQuanPresenter;
    private DanhSachKhachHangDeDangKyImpl danhSachKhachHangDeDangKyPresenter;
    private CapNhatDangKyTMXImpl capNhatDangKyPresenter = new CapNhatDangKyTMXImpl(this);
    private DangKyLichTrinhTMXImpl dangKyLichTrinhPresenter = new DangKyLichTrinhTMXImpl(this);
    private CapNhatLichTrinhChiTietTMXImpl capNhatLichTrinhChiTietTMXPresenter = new CapNhatLichTrinhChiTietTMXImpl(this);
    private DangKyLichTrinhChiTietTMXImpl dangKyLichTrinhChiTietTMXPresenter = new DangKyLichTrinhChiTietTMXImpl(this);

    private DanhSachIdKhachHangDangKyTMXImpl danhSachIdKhachHangDangKyPresenter = new DanhSachIdKhachHangDangKyTMXImpl(this);

    public List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponses;
    List<DanhSachKhachHangDeDangKyResponse> danhSachKhachHangDeDangKy = new ArrayList<>();
    private DanhSachKhachHangDangKyAdapter danhSachKhachHangDangKyAdapter;
    private DanhSachKhachHangDeDangKyAdapter danhSachKhachHangDeDangKyAdapter;
    private List<DanhSachVungResponse> danhSachVung;
    private List<DanhSachVungResponse> danhSachKhuVuc;
    private List<DanhSachVungResponse> danhSachTinh;
    private List<DanhSachVungResponse> danhSachQuan;
    private KhuVucAdapter adapterVung;
    private KhuVucAdapter adapterKhuVuc;
    private KhuVucAdapter adapterTinh;
    private KhuVucAdapter adapterQuan;

    private  AutoCompleteTextView txtVung, txtKhuVuc, txtTinh, txtQuanHuyen;
    private String idVung = "";
    private String idKhuVuc = "";
    private String idTinh = "";
    private String idQuan = "";

    @SuppressLint("ValidFragment")
    public ThongTinTuyenFragment(String registerId) {
        this.registerId = registerId;
    }

    public ThongTinTuyenFragment() {
        // Required empty public constructor
        registerId = "";
    }

    // TODO: Rename and change types and number of parameters
    public static ThongTinTuyenFragment newInstance(String param1, String param2) {
        ThongTinTuyenFragment fragment = new ThongTinTuyenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("Đang kết nối...")
                .setCancellable(true)
                .setAnimationSpeed(3)
                .setDimAmount(0.5f);

        listDanhSachKhaoSat = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_tuyen_tmx, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }

    private void addEvents(View view) {
        danhSachVungPresenter.layDanhSachVung();
        if(registerId.equals("")){
            txtNhanVienKinhDoanh.setText(Config.username);
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            txtNgayThucHien.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);
            mWeek = control.getWeekFromDate(mYear, mMonth, mDay);
            String startDayOfWeek = control.getStartOfWeek((mWeek), mYear);
            String endDayOfWeek = control.getEndOfWeek((mWeek), mYear);
            String sWeek = "Tuần " + String.valueOf(mWeek - 1) + "(" + startDayOfWeek + "-" + endDayOfWeek + ")";
            txtTuan.setText(sWeek);
        }else {
            danhSachIdKhachHangDangKyPresenter.layDanhSachIdKhachHang(registerId);
            thongTinDangKyChiTietPresenter.layThongTinDangKyChiTiet(registerId);
            danhSachKhachHangDangKyPresenter.layDanhSachKhachHangDangKy(pageNo, pageRec, registerId);
        }
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });

        btnThemXetNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTimKiemTuyen();
            }
        });

        txtNgayThucHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(txtNgayThucHien, getActivity());
            }
        });


        btnGhiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.showProgressBar();
                if(registerId.equals("")){
                    dangKyLichTrinhPresenter.dangKyLichTrinh(txtNgayThucHien.getText().toString().trim(), String.valueOf(mWeek), txtGhiChu.getText().toString().trim());
                }else {
                    capNhatDangKyPresenter.capNhatDangKy(registerId, txtNgayThucHien.getText().toString().trim(), String.valueOf(mWeek), txtGhiChu.getText().toString().trim());
//                    capNhatLichTrinhChiTietTMXPresenter.capNhatLichTrinhChiTiet(registerId, danhSachKhachHangDangKyResponses);
                }
            }
        });
    }

    private void addControls(View view) {
        pageNo = 1;
        pageRec = 100;
        stepIndex = 100;
        loadingMore = false;
        if(listDanhSachKhaoSat != null){
            listDanhSachKhaoSat.clear();
        }
        getActivity().setTitle("Thông tin tuyến".toUpperCase());
        monthValidator = new MonthValidator();
        mainActivity = (MainActivity) getActivity();
        control = new Controller(getActivity());

        thongTinDangKyChiTietPresenter = new ThongTinDangKyChiTietTMXImpl(this);
        danhSachKhachHangDangKyPresenter = new DanhSachKhachHangDangKyImpl(this);
        danhSachVungPresenter = new DanhSachVungImpl(this);
        danhSachKhuVucPresenter = new DanhSachKhuVucImpl(this);
        danhSachTinhPresenter = new DanhSachTinhImpl(this);
        danhSachQuanPresenter = new DanhSachQuanImpl(this);
        danhSachKhachHangDeDangKyPresenter = new DanhSachKhachHangDeDangKyImpl(this);

        danhSachVung = new ArrayList<>();
        danhSachKhuVuc = new ArrayList<>();
        danhSachTinh = new ArrayList<>();
        danhSachQuan = new ArrayList<>();
        danhSachKhachHangDangKyResponses = new ArrayList<>();

        txtNhanVienKinhDoanh = (TextView) view.findViewById(R.id.txtNhanVienKinhDoanh);
        txtTuan = (TextView) view.findViewById(R.id.txtTuan);
        txtNgayThucHien = (TextView) view.findViewById(R.id.txtNgayThucHien);
        txtGhiChu = (EditText) view.findViewById(R.id.txtGhiChu);
        txtTrangThai = (TextView) view.findViewById(R.id.txtTrangThai);
        lvDanhSachDonhang = (ListView) view.findViewById(R.id.lvDanhSachDonhang);
        btnThoat = (Button) view.findViewById(R.id.btnThoat);
        btnGhiLai = (Button) view.findViewById(R.id.btnGhiLai);
        btnThemXetNghiem = (TextView) view.findViewById(R.id.btnThemXetNghiem);
    }


    @Override
    public void onThongTinDangKyChiTietSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            ThongTinDangKyChiTietResponse[] arrThongTinDangKy = gson.fromJson(jsonElement, ThongTinDangKyChiTietResponse[].class);
            List<ThongTinDangKyChiTietResponse> thongTinKhachHang = new ArrayList<>(Arrays.asList(arrThongTinDangKy));
            if(thongTinKhachHang.size() > 0){
                txtNhanVienKinhDoanh.setText(thongTinKhachHang.get(0).getNvkd());
                txtTuan.setText(thongTinKhachHang.get(0).getTuanthuchien());
                txtNgayThucHien.setText(thongTinKhachHang.get(0).getStartDate());
                txtTrangThai.setText(thongTinKhachHang.get(0).getIsActive());
                txtGhiChu.setText(thongTinKhachHang.get(0).getNote());
                mWeek = Integer.parseInt(thongTinKhachHang.get(0).getWeek());

            }

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onThongTinDangKyChiTietError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được thông tin đăng ký chi tiết!",
                false);
    }

    @Override
    public void onLayDanhSachKhachHangDangKySuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachKhachHangDangKyResponse[] arrKhachHangDangKy = gson.fromJson(jsonElement, DanhSachKhachHangDangKyResponse[].class);
            List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKy = new ArrayList<>(Arrays.asList(arrKhachHangDangKy));
            danhSachKhachHangDangKyResponses.addAll(danhSachKhachHangDangKy);

            if(danhSachKhachHangDangKy.size() > 0){
                danhSachKhachHangDangKyAdapter = new DanhSachKhachHangDangKyAdapter(getActivity(), R.layout.item_thong_tin_tuyen, danhSachKhachHangDangKyResponses, this);
                lvDanhSachDonhang.setAdapter(danhSachKhachHangDangKyAdapter);
                setListViewHeightBasedOnChildren(lvDanhSachDonhang);
                for(int i = 0; i < danhSachKhachHangDangKy.size(); i++){
                    String sValue = "" + danhSachKhachHangDangKy.get(i).getMon() + ","
                                        + danhSachKhachHangDangKy.get(i).getTue() + ","
                                        + danhSachKhachHangDangKy.get(i).getWed() + ","
                                        + danhSachKhachHangDangKy.get(i).getThu() + ","
                                        + danhSachKhachHangDangKy.get(i).getFri() + ","
                                        + danhSachKhachHangDangKy.get(i).getSat() + ","
                                        + danhSachKhachHangDangKy.get(i).getSun();
                    CapNhatIdDangKyRequest item = new CapNhatIdDangKyRequest(danhSachKhachHangDangKy.get(i).getOrgId(), sValue);
                    danhSachIdDangKy.add(item);
                }
            }

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayDanhSachKhachHangDangKyError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách khách hàng đăng ký!",
                false);
    }

    @Override
    public void onLayDanhSachQuanSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachVungResponse[] arrDanhSachKhuVuc = gson.fromJson(jsonElement, DanhSachVungResponse[].class);
            danhSachQuan = new ArrayList<>(Arrays.asList(arrDanhSachKhuVuc));
            DanhSachVungResponse viTriDau = new DanhSachVungResponse("", "Không chọn");
            danhSachQuan.add(0, viTriDau);
            if(danhSachQuan.size() > 0){
                adapterQuan = new KhuVucAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachQuan);
                txtQuanHuyen.setAdapter(adapterQuan);
                adapterQuan.notifyDataSetChanged();
            }else {

            }

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayDanhSachQuanError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách Quận",
                false);
    }

    @Override
    public void onLayDanhSachTinhSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachVungResponse[] arrDanhSachKhuVuc = gson.fromJson(jsonElement, DanhSachVungResponse[].class);
            danhSachTinh = new ArrayList<>(Arrays.asList(arrDanhSachKhuVuc));
            DanhSachVungResponse viTriDau = new DanhSachVungResponse("", "Không chọn");
            danhSachTinh.add(0, viTriDau);
            if(danhSachTinh.size() > 0) {
                adapterTinh = new KhuVucAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachTinh);
                txtTinh.setAdapter(adapterTinh);
                adapterTinh.notifyDataSetChanged();
            }else {

            }
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayDanhSachTinhError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách Tỉnh",
                false);
    }

    @Override
    public void onLayDanhSachVungSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachVungResponse[] arrDanhSachVung = gson.fromJson(jsonElement, DanhSachVungResponse[].class);
            danhSachVung = new ArrayList<>(Arrays.asList(arrDanhSachVung));
            DanhSachVungResponse viTriDau = new DanhSachVungResponse("", "Không chọn");
            danhSachVung.add(0, viTriDau);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayDanhSachVungError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách Vùng",
                false);
    }

    @Override
    public void onLayDanhSachKhuVucSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachVungResponse[] arrDanhSachKhuVuc = gson.fromJson(jsonElement, DanhSachVungResponse[].class);
            danhSachKhuVuc = new ArrayList<>(Arrays.asList(arrDanhSachKhuVuc));
            DanhSachVungResponse viTriDau = new DanhSachVungResponse("", "Không chọn");
            danhSachKhuVuc.add(0, viTriDau);
            if(danhSachKhuVuc.size() > 0){
                adapterKhuVuc = new KhuVucAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachKhuVuc);
                txtKhuVuc.setAdapter(adapterKhuVuc);
                adapterKhuVuc.notifyDataSetChanged();
            }else {

            }

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayDanhSachKhuVucError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách khu vực",
                false);
    }

    private void dialogTimKiemTuyen(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tmx_tim_kiem_khach_hang);
        dialog.setCancelable(false);
        // chỉnh kích cỡ dialog show
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        height = (int)(height * 1.3);
        width = (int)(width * 0.5);
        lp.width = height;
        lp.height = width;

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        final EditText txtTuKhoa = (EditText) dialog.findViewById(R.id.txtTuKhoa);
        txtVung = (AutoCompleteTextView) dialog.findViewById(R.id.txtVung);
        txtKhuVuc = (AutoCompleteTextView) dialog.findViewById(R.id.txtKhuVuc);
        txtTinh = (AutoCompleteTextView) dialog.findViewById(R.id.txtTinh);
        txtQuanHuyen = (AutoCompleteTextView) dialog.findViewById(R.id.txtQuanHuyen);
        Button btnTimKiem = (Button) dialog.findViewById(R.id.btnTimKiem);
        Button btnChon = (Button) dialog.findViewById(R.id.btnChon);
        Button btnDong = (Button) dialog.findViewById(R.id.btnDong);

        lvDanhSachKhachHangDeDangKy = (ListView) dialog.findViewById(R.id.lvDanhSachKhachHangDeDangKy);

        if(danhSachVung != null && danhSachVung.size() > 0){
            adapterVung = new KhuVucAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachVung);
            txtVung.setAdapter(adapterVung);
            adapterVung.notifyDataSetChanged();
        }
        // vùng click event
        txtVung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                txtVung.showDropDown();
                return false;
            }
        });

        txtVung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idVung = adapterVung.getItem(position).getId();
                idKhuVuc = "";
                idTinh = "";
                idQuan = "";
                txtKhuVuc.setText("");
                txtTinh.setText("");
                txtQuanHuyen.setText("");
                danhSachKhuVucPresenter.layDanhSachKhuVuc(idVung);
                txtVung.setError(null);
                if(idVung.equals("")){
                    txtVung.setText("");
                }
            }
        });

        // end

        // khu vực click event
        txtKhuVuc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(idVung.equals("") || txtVung.getText().toString().trim().equals("")){
                    txtVung.setError("Cần chọn vùng");
                    txtVung.requestFocus();
                    return true;
                }else {
                    txtKhuVuc.showDropDown();
                }

                return false;
            }
        });
        txtKhuVuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idKhuVuc = adapterKhuVuc.getItem(position).getId();
                idTinh = "";
                idQuan = "";
                txtTinh.setText("");
                txtQuanHuyen.setText("");
                danhSachTinhPresenter.layDanhSachTinh(idKhuVuc);
                txtKhuVuc.setError(null);
                if(idKhuVuc.equals("")){
                    txtKhuVuc.setText("");
                }
            }
        });
        // end

        // tỉnh click event
        txtTinh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(idVung.equals("") || txtVung.getText().toString().trim().equals("")){
                    txtVung.setError("Cần chọn Vùng");
                    txtVung.requestFocus();
                    return true;
                }
                if(idKhuVuc.equals("") || txtKhuVuc.getText().toString().trim().equals("")){
                    txtKhuVuc.setError("Cần chọn khu vực");
                    txtKhuVuc.requestFocus();
                    return true;
                }else {
                    txtTinh.showDropDown();
                }

                return false;
            }
        });
        txtTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idTinh = adapterTinh.getItem(position).getId();
                idQuan = "";
                txtQuanHuyen.setText("");
                danhSachQuanPresenter.layDanhSachQuan(idTinh);
                txtTinh.setError(null);
                if(idTinh.equals("")){
                    txtTinh.setText("");
                }
            }
        });
        // end

        // quận click event
        txtQuanHuyen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(idVung.equals("") || txtVung.getText().toString().trim().equals("")){
                    txtVung.setError("Cần chọn Vùng");
                    txtVung.requestFocus();
                    return true;
                }
                if(idKhuVuc.equals("") || txtKhuVuc.getText().toString().trim().equals("")){
                    txtKhuVuc.setError("Cần chọn khu vực");
                    txtKhuVuc.requestFocus();
                    return true;
                }
                if(idTinh.equals("") || txtTinh.getText().toString().trim().equals("")){
                    txtTinh.setError("Cần chọn tỉnh");
                    txtTinh.requestFocus();
                    return true;
                }else {
                    txtQuanHuyen.showDropDown();
                }

                return false;
            }
        });
        txtQuanHuyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idQuan = adapterQuan.getItem(position).getId();
                if(idQuan.equals("")){
                    txtQuanHuyen.setText("");
                }
            }
        });
        // end



        // remove textview when click
        txtVung.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtVung.setText("");
                    txtKhuVuc.setText("");
                    txtTinh.setText("");
                    txtQuanHuyen.setText("");
                    idVung = "";
                    idKhuVuc = "";
                    idTinh = "";
                    idQuan = "";

                }
                return false;
            }
        });
        txtKhuVuc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtKhuVuc.setText("");
                    txtTinh.setText("");
                    txtQuanHuyen.setText("");
                    idKhuVuc = "";
                    idTinh = "";
                    idQuan = "";
                }
                return false;
            }
        });
        txtTinh.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtTinh.setText("");
                    txtQuanHuyen.setText("");
                    idTinh = "";
                    idQuan = "";
                }
                return false;
            }
        });
        txtQuanHuyen.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtQuanHuyen.setText("");
                    idQuan = "";
                }
                return false;
            }
        });
        // end


        dialog.show();
        // event
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(danhSachKhachHangDeDangKyAdapter != null && danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.size() > 0){
                    for(int i = 0; i < danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.size(); i++){
                        if(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkTatCa().isChecked()){
                            DanhSachKhachHangDangKyResponse item = new DanhSachKhachHangDangKyResponse();
                            item.setOrgId(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getOrgId());
                            item.setOrgName(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getOrgName());
                            item.setAddress(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getAddress());
                            item.setMon(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư2().isChecked()));
                            item.setTue(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư3().isChecked()));
                            item.setWed(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư4().isChecked()));
                            item.setThu(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư5().isChecked()));
                            item.setFri(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư6().isChecked()));
                            item.setSat(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkThư7().isChecked()));
                            item.setSun(convertBoolToString(danhSachKhachHangDeDangKyAdapter.danhSachKhachHangDangKyModels.get(i).getCkChuNhat().isChecked()));
                            danhSachKhachHangDangKyResponses.add(item);
                            listIdKhachHang.add(item.getOrgId());
//                            danhSachidKhachHang += "," + "";

                        }
                    }
                }
                if(danhSachKhachHangDangKyAdapter != null){
                    danhSachKhachHangDangKyAdapter.notifyDataSetChanged();
                }else {
                    danhSachKhachHangDangKyAdapter = new DanhSachKhachHangDangKyAdapter(getActivity(), R.layout.item_thong_tin_tuyen, danhSachKhachHangDangKyResponses, ThongTinTuyenFragment.this);
                    lvDanhSachDonhang.setAdapter(danhSachKhachHangDangKyAdapter);
                }
                setListViewHeightBasedOnChildren(lvDanhSachDonhang);
                dialog.dismiss();
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.showProgressBar();
                if(txtVung.getText().toString().trim().equals("")){
                    idVung = "";
                }
                if(txtKhuVuc.getText().toString().trim().equals("")){
                    idKhuVuc = "";
                }
                if(txtTinh.getText().toString().trim().equals("")){
                    idTinh = "";
                }
                if(txtQuanHuyen.getText().toString().trim().equals("")){
                    idQuan = "";
                }
                String sIdKhachHangDaDangKy = "";
                try{
                    if(listIdKhachHang.size() > 0){
                        for(int i = 0; i < listIdKhachHang.size(); i++){
                            if(i > 0){
                                sIdKhachHangDaDangKy += ", ";
                            }
                            sIdKhachHangDaDangKy += listIdKhachHang.get(i);
                        }
                    }

                }catch (Exception ex){

                }

                DanhSachKhachHangDeDangKyRequest request = new DanhSachKhachHangDeDangKyRequest(pageNoKhachHangDeDangKy, pageRecKhachHangDeDangKy, txtTuKhoa.getText().toString().trim(), idVung, idKhuVuc, idTinh, idQuan, sIdKhachHangDaDangKy);
                danhSachKhachHangDeDangKyPresenter.layDanhSachKhachHangDeDangKy(request);
            }
        });
    }

    @Override
    public void onLayDanhSachKhachHangDeDangKySuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachKhachHangDeDangKyResponse[] arrDanhSachKhachHangDeDangKy = gson.fromJson(jsonElement, DanhSachKhachHangDeDangKyResponse[].class);
            danhSachKhachHangDeDangKy = new ArrayList<>(Arrays.asList(arrDanhSachKhachHangDeDangKy));
            danhSachKhachHangDeDangKyAdapter = new DanhSachKhachHangDeDangKyAdapter(getActivity(), R.layout.item_tmx_danh_sach_khach_hang_de_dang_ky, danhSachKhachHangDeDangKy);
            lvDanhSachKhachHangDeDangKy.setAdapter(danhSachKhachHangDeDangKyAdapter);
            setListViewHeightBasedOnChildren(lvDanhSachKhachHangDeDangKy);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
        mainActivity.hideProgressBar();
    }

    @Override
    public void onLayDanhSachKhachHangDeDangKyError(Object object) {
        mainActivity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách khách hàng",
                false);
    }

    @Override
    public void onCapNhatDangKySuccess(Object object) {
        mainActivity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")){
                capNhatLichTrinhChiTietTMXPresenter.capNhatLichTrinhChiTiet(registerId, danhSachKhachHangDangKyResponses);
/*                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật thông tin thành công!",
                        false);*/
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_WEEK_1)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Thời gian đăng ký không hợp lệ (Tuần đăng ký phải lớn hơn tuần hiện tại)!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_WEEK_2)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn cần phải chọn ngày để xác định tuần đăng ký tuyến!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_CODE)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn đã đăng ký tuyến cho tuần làm việc này. Vui lòng kiểm tra lại!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_STATUS)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Đăng ký tuyến đã được PHÊ DUYỆT. Không thể cập nhật!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_PERSON)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn không có quyền thao tác CẬP NHẬT!",
                        false);
            } else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        response.get(0).getResult(),
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onCapNhatDangKyError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không cập nhật được đăng ký",
                false);
    }

    @Override
    public void onDangKyLichTrinhSuccess(Object object) {
        mainActivity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            String[] valuesResponse = response.get(0).getResult().split("\\|");
            if(valuesResponse[0].toLowerCase().equals("true")){
                dangKyLichTrinhChiTietTMXPresenter.dangKyLichTrinhChiTiet(valuesResponse[1].trim(), danhSachKhachHangDangKyResponses);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_WEEK_1)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Thời gian đăng ký không hợp lệ (Tuần đăng ký phải lớn hơn tuần hiện tại)!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_WEEK_2)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn cần phải chọn ngày để xác định tuần đăng ký tuyến!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_CODE)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn đã đăng ký tuyến cho tuần làm việc này. Vui lòng kiểm tra lại!",
                        false);
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        response.get(0).getResult(),
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onDangKyLichTrinhError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không đăng ký được lịch trình",
                false);
    }

    public void showDatePicker(final TextView e, Context context) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                e.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                mWeek = control.getWeekFromDate(year, monthOfYear, dayOfMonth);
                String startDayOfWeek = control.getStartOfWeek((mWeek), year);
                String endDayOfWeek = control.getEndOfWeek((mWeek), year);
                String sWeek = "Tuần " + String.valueOf(mWeek - 1) + "(" + startDayOfWeek + "-" + endDayOfWeek + ")";
                if(year != 2017){
                    sWeek = "Tuần " + String.valueOf(mWeek) + "(" + startDayOfWeek + "-" + endDayOfWeek + ")";
                }


                txtTuan.setText(sWeek);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    @Override
    public void onLayDanhSachIdDaDangKySuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            danhSachidKhachHang = response.get(0).getResult();
            String[] temp = danhSachidKhachHang.split(",");
            if(temp.length > 0){
                for(int i = 0; i < temp.length; i++){
                    listIdKhachHang.add(temp[i]);
                }
            }


            String a = "";
        }catch (Exception ex){

        }
    }

    @Override
    public void onLayDanhSachIdDaDangKyError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không đăng ký được danh sách id khách hàng đã đăng ký",
                false);
    }

    public void xoaTuyenDangKy(int position){

        try{
            if(danhSachKhachHangDangKyResponses.size() > 0) {

                if(listIdKhachHang.size() > 0){
                    for (String item : listIdKhachHang){
                        if(item.equals(danhSachKhachHangDangKyResponses.get(position).getOrgId())){
                            listIdKhachHang.remove(item);
                        }
                    }
                }
                danhSachKhachHangDangKyResponses.remove(position);
                danhSachKhachHangDangKyAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lvDanhSachDonhang);
            }
        }catch (Exception ex){

        }

    }

    private String convertBoolToString(boolean b){
        if(b){
            return "1";
        }else {
            return "0";
        }
    }

    @Override
    public void onDangKyLichTrinhChiTietSuccess(Object object) {
        mainActivity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật dữ liệu thành công!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_PERSON)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn không có quyền thao tác CẬP NHẬT!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_STATUS)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Đăng ký tuyến đã được PHÊ DUYỆT. Không thể đăng ký thêm!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_CODE)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn đã đăng ký tuyến cho tuần làm việc này. Vui lòng kiểm tra lại!",
                        false);
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        response.get(0).getResult(),
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onDangKyLichTrinhChiTietError(Object object) {
        mainActivity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không đăng ký được lịch trình chi tiết!",
                false);
    }

    @Override
    public void onCapNhatLichTrinhChiTietSuccess(Object object) {
        mainActivity.hideProgressBar();
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật dữ liệu thành công!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_PERSON)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn không có quyền thao tác CẬP NHẬT!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_STATUS)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Đăng ký tuyến đã được PHÊ DUYỆT. Không thể đăng ký thêm!",
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_CODE)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Bạn đã đăng ký tuyến cho tuần làm việc này. Vui lòng kiểm tra lại!",
                        false);
            }else {
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        response.get(0).getResult(),
                        false);
            }
        }catch (Exception ex){

        }
    }

    @Override
    public void onCapNhatLichTrinhChiTietError(Object object) {
        mainActivity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không cập nhật được lịch trình chi tiết!",
                false);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(expListView);
        MenuItem itemGhetham = menu.findItem(R.id.action_ghetham);
        MenuItem itemBando = menu.findItem(R.id.action_bando);
        MenuItem itemThongtin = menu.findItem(R.id.action_thongtin);
        MenuItem itemDatHang = menu.findItem(R.id.action_dathang);
        MenuItem itemKhaoSat = menu.findItem(R.id.actionKhaoSat);
        String re = "false";
        Log.i("drawerOpen", re);
        itemGhetham.setVisible(false);
        itemBando.setVisible(false);
        itemThongtin.setVisible(false);
        itemDatHang.setVisible(false);
        itemKhaoSat.setVisible(false);
    }

}
