package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DanhSachNhanHieuAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.ThongTinKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.MonthValidator;
import vn.com.vsc.ptpm.VNPT_DMS.event.ThongTinKhachHangKhaoSatEvent;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.SoLieuKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.KhachHangKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.NhanHieuResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ThongTinKhaoSatChiTietResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.CapNhatKhaoSatImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.ChungLoaiImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.DanhSachKhachHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.NhaPhanPhoiImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.NhanHieuImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.ThongTinKhachHangKhaoSatImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.ThongTinKhaoSatImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ICapNhatKhaoSatView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IDanhSachKhachHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayChungLoaiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayNhaPhanPhoiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayNhanHieuView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayThongTinKhachHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IThongTinKhaoSatView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThongTinKhaoSatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ThongTinKhaoSatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongTinKhaoSatFragment extends android.app.Fragment implements IThongTinKhaoSatView, ILayNhanHieuView, ILayChungLoaiView, ILayNhaPhanPhoiView, IDanhSachKhachHangView, ICapNhatKhaoSatView, ILayThongTinKhachHangView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity activity;
    private String comment;
    private String daily = "";
    private OnFragmentInteractionListener mListener;
    private KProgressHUD hud;
    private ConvertFont conv = new ConvertFont();
    private ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent;
    private Controller control;
    private EditText txtPhanHoi;
    private CheckBox ckSanPhamHoangThach;
    private ThongTinKhaoSatImpl thongTinKhaoSatPresenter;
    private List<ThongTinKhaoSatChiTietResponse> thongTinKhaoSatResponses;
    private ThongTinKhaoSatAdapter thongTinKhaoSatAdapter;
    private ListView lvDanhMucKhaoSat;
    private ImageButton btnThangKhaoSat;
    private EditText txtThangKhaoSat;
    private int mYear, mMonth, mDay;

    private SearchableSpinner txtNhanHieu, txtChungLoai, txtNhaPhanPhoi;
    public AutoCompleteTextView txtTenKhachHang;
    private EditText txtCPVanTai, txtCPBocXep, txtGiaDenCuaHang, txtGiaBan, txtSanLuongDuKien;
    private Button btnCapNhat, btnDong;
    private CapNhatKhaoSatImpl capNhatKhaoSatPresenter;

    private ChungLoaiImpl chungLoaiPresenter;
    private NhanHieuImpl nhanHieuPresenter;
    private NhaPhanPhoiImpl nhaPhanPhoiPresenter;
    private DanhSachKhachHangImpl danhSachKhachHangPresenter;

    private List<NhanHieuResponse> danhSachNhanHieu;
    private List<NhanHieuResponse> danhSachChungLoai;
    private List<NhanHieuResponse> danhSachNhaPhanPhoi;
    private List<KhachHangKhaoSatResponse> danhSachKhachHang;
    private ArrayList<String> arrNhanHieu = new ArrayList<>();
    private ArrayList<String> arrChungLoai = new ArrayList<>();
    private ArrayList<String> arrNhaPhanPhoi = new ArrayList<>();
    private DanhSachNhanHieuAdapter adapterNhaPhanPHoi;
    private DanhSachNhanHieuAdapter adapterChungLoai;
    private DanhSachNhanHieuAdapter adapterNhanHieu;
    private KhachHangKhaoSatAdapter adapterKhachHang;
    private TextView btnThemKhaoSat;
    private ThongTinKhaoSatFragment thongTinKhaoSatFragment;
    private ThongTinKhachHangKhaoSatImpl thongTinKhachHangKhaoSatPresenter;
    private MonthValidator monthValidator;
    String idNhanHieu = "0";
    String idChungLoai = "0";
    String idNhaPhanPhoi = "0";
    String idKhachHang = "0";
    String idKhaoSat = "";
    private String ORG_ID = "";
    String nameNhanHieu, nameChungLoai, nameNhaPhanPhoi;

    @SuppressLint("ValidFragment")
    public ThongTinKhaoSatFragment(String ORG_ID) {
        this.ORG_ID = ORG_ID;
    }

    public ThongTinKhaoSatFragment() {
        // Required empty public constructor
        this.ORG_ID = "";

    }


    @SuppressLint("ValidFragment")
    public ThongTinKhaoSatFragment(ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent) {
        this.khachHangKhaoSatEvent = khachHangKhaoSatEvent;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongTinKhaoSatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongTinKhaoSatFragment newInstance(String param1, String param2) {
        ThongTinKhaoSatFragment fragment = new ThongTinKhaoSatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        setHasOptionsMenu(true);
        hud = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("Đang kết nối...")
                .setCancellable(true)
                .setAnimationSpeed(3)
                .setDimAmount(0.5f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_thong_tin_khao_sat, container, false);
        getActivity().setTitle(getString(R.string.thongTinKhaoSat).toUpperCase());
        addControls(view);
        addEvents(view);
        return view;
    }

    private void addEvents(View view) {
//        showProgressBar();
        nhanHieuPresenter.layNhanHieu();
        chungLoaiPresenter.layChungLoai();
        nhaPhanPhoiPresenter.layNhaPhanPhoi("");
//        danhSachKhachHangPresenter.getDanhSachKhachHang("");

        btnThemKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSoLieuKhaoSat();
            }
        });

        btnThangKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(txtThangKhaoSat);
            }
        });

        txtTenKhachHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(count > 1){
//                    danhSachKhachHangPresenter.getDanhSachKhachHang(s.toString());
//                }
                danhSachKhachHangPresenter.getDanhSachKhachHang(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = "";
            }
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idKhachHang.equals("0")){
                    txtTenKhachHang.setError("Phải chọn khách hàng");
                    return;
                }else if(txtThangKhaoSat.getText().toString().trim().equals("")){
                    txtThangKhaoSat.setError("Cần chọn tháng khảo sát");
                    return;
                } else if(!monthValidator.validate(txtThangKhaoSat.getText().toString().trim())
                        && !txtThangKhaoSat.getText().toString().trim().equals("")){
                    txtThangKhaoSat.setError("Nhập sai định dạng, tháng tìm kiếm phải có định dạng tháng/năm");
                    txtThangKhaoSat.requestFocus();
                    return;
                }
                final List<SoLieuKhaoSatRequest> danhSachSoLieuKhaoSat = new ArrayList<SoLieuKhaoSatRequest>();

                if(ckSanPhamHoangThach.isChecked()){
                    daily = "1";
                }else {
                    daily = "0";
                }
                comment = txtPhanHoi.getText().toString().trim();
                try {
                    comment = URLEncoder.encode(txtPhanHoi.getText().toString().trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if(thongTinKhaoSatResponses != null && thongTinKhaoSatResponses.size() > 0){
                    for(int i = 0; i < thongTinKhaoSatResponses.size(); i++){
                        SoLieuKhaoSatRequest soLieuKhaoSatRequest = new SoLieuKhaoSatRequest();
                        soLieuKhaoSatRequest.setBrand(thongTinKhaoSatResponses.get(i).getBrand_id());
                        soLieuKhaoSatRequest.setProduct_cat(thongTinKhaoSatResponses.get(i).getProduct_cat_id());
                        soLieuKhaoSatRequest.setSupplier(thongTinKhaoSatResponses.get(i).getDistributor_id());
                        soLieuKhaoSatRequest.setTran_cost(thongTinKhaoSatResponses.get(i).getTransport_cost());
                        soLieuKhaoSatRequest.setLoad_cost(thongTinKhaoSatResponses.get(i).getLoading_cost());
                        soLieuKhaoSatRequest.setBuy_price(thongTinKhaoSatResponses.get(i).getBuying_price());
                        soLieuKhaoSatRequest.setSell_price(thongTinKhaoSatResponses.get(i).getSelling_price());
                        soLieuKhaoSatRequest.setQuan_expected(thongTinKhaoSatResponses.get(i).getQuantity_expected());
                        danhSachSoLieuKhaoSat.add(soLieuKhaoSatRequest);
                    }


                    activity.showProgressBar();
                    capNhatKhaoSatPresenter.updateKhaoSat(idKhaoSat, idKhachHang,
                            txtThangKhaoSat.getText().toString().trim(),
                            comment,
                            daily,
                            danhSachSoLieuKhaoSat);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Cảnh báo");
                    builder.setMessage("Bạn chưa nhập số liệu khảo sát. Bạn có muốn tiếp Cập nhật không?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.showProgressBar();
                            capNhatKhaoSatPresenter.updateKhaoSat(idKhaoSat, idKhachHang,
                                    txtThangKhaoSat.getText().toString().trim(),
                                    comment,
                                    daily,
                                    danhSachSoLieuKhaoSat);
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

            }
        });
    }

    private void addControls(View view) {
        activity = (MainActivity) getActivity();
        thongTinKhaoSatFragment = this;
        nhanHieuPresenter = new NhanHieuImpl(this);
        chungLoaiPresenter = new ChungLoaiImpl(this);
        nhaPhanPhoiPresenter = new NhaPhanPhoiImpl(this);
        danhSachKhachHangPresenter = new DanhSachKhachHangImpl(this);
        capNhatKhaoSatPresenter = new CapNhatKhaoSatImpl(this);
        monthValidator = new MonthValidator();
        txtTenKhachHang = (AutoCompleteTextView) view.findViewById(R.id.txtTenKhachHang);
        txtThangKhaoSat = (EditText) view.findViewById(R.id.txtThangKhaoSat);
        txtPhanHoi = (EditText) view.findViewById(R.id.txtPhanHoi);
        btnThangKhaoSat = (ImageButton) view.findViewById(R.id.btnThangKhaoSat);
        txtThangKhaoSat = (EditText) view.findViewById(R.id.txtThangKhaoSat);
        btnThemKhaoSat = (TextView) view.findViewById(R.id.btnThemKhaoSat);
        ckSanPhamHoangThach = (CheckBox) view.findViewById(R.id.ckSanPhamHoangThach);
        lvDanhMucKhaoSat = (ListView) view.findViewById(R.id.lvDanhMucKhaoSat);
        btnCapNhat = (Button) view.findViewById(R.id.btnCapNhat);
        btnDong = (Button) view.findViewById(R.id.btnDong);
        thongTinKhaoSatPresenter = new ThongTinKhaoSatImpl(this);
        thongTinKhachHangKhaoSatPresenter = new ThongTinKhachHangKhaoSatImpl(this);
        control = new Controller(getActivity());
//        khachHangKhaoSatEvent = EventBus.getDefault().getStickyEvent(ThongTinKhachHangKhaoSatEvent.class);
        txtTenKhachHang.setText("");
        ckSanPhamHoangThach.setChecked(false);

        //set tháng mặc định
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        String thangKhaoSat = (mMonth + 1) + "/" + mYear;
        txtThangKhaoSat.setText(thangKhaoSat);
        // end

        if(khachHangKhaoSatEvent != null){

            try {
                if(!khachHangKhaoSatEvent.getId().equals("")){
                    activity.showProgressBar();
                    txtTenKhachHang.setText(khachHangKhaoSatEvent.getCustomer());
                    txtThangKhaoSat.setText(khachHangKhaoSatEvent.getSmonth_survey());
//                    txtPhanHoi.setText(khachHangKhaoSatEvent.getPhanhoi());
                    txtPhanHoi.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(khachHangKhaoSatEvent.getPhanhoi(), "UTF-8")));

                    idKhaoSat = khachHangKhaoSatEvent.getId();
                    idKhachHang = khachHangKhaoSatEvent.getOrg_id();
                    if(khachHangKhaoSatEvent.getIs_daily().equals("1")){
                        ckSanPhamHoangThach.setChecked(true);
                    }
                    thongTinKhaoSatPresenter.layThongTinKhaoSat(khachHangKhaoSatEvent.getId());
                }else {
                    txtTenKhachHang.setText("");
                    //set tháng mặc định
                    Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get(Calendar.MONTH);
                    thangKhaoSat = (mMonth + 1) + "/" + mYear;
                    txtThangKhaoSat.setText(thangKhaoSat);
                    // end
                }

            }catch (Exception ex){

            }
        }

        // gọi hàm lấy thông tin khách hàng nếu luồng khảo sát đi từ màn hình tuyến & khách hàng
        if(!ORG_ID.equals("")){
                activity.showProgressBar();
                thongTinKhachHangKhaoSatPresenter.layThongTinKhachHangKhaosat(ORG_ID);
        }
//        if(khachHangKhaoSatEvent != null){
//            if(!khachHangKhaoSatEvent.getOrg_id().equals("") && khachHangKhaoSatEvent.getId().equals("")){
//                activity.showProgressBar();
//                thongTinKhachHangKhaoSatPresenter.layThongTinKhachHangKhaosat(khachHangKhaoSatEvent.getOrg_id());
//            }else {
//
//            }
//        }
//        if(!ORG_ID.equals("")){
//            showProgressBar();
//            thongTinKhachHangKhaoSatPresenter.layThongTinKhachHangKhaosat(ORG_ID);
//        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onLayThongTinKhaoSatSuccess(Object object) {

        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            ThongTinKhaoSatChiTietResponse[] arrThongTinKhaoSat = gson.fromJson(jsonElement, ThongTinKhaoSatChiTietResponse[].class);
            thongTinKhaoSatResponses = new ArrayList<>(Arrays.asList(arrThongTinKhaoSat));
            thongTinKhaoSatAdapter = new ThongTinKhaoSatAdapter(getActivity(), R.layout.item_thong_tin_khao_sat, thongTinKhaoSatResponses, this);
            lvDanhMucKhaoSat.setAdapter(thongTinKhaoSatAdapter);
            setListViewHeightBasedOnChildren(lvDanhMucKhaoSat);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }

        activity.hideProgressBar();
    }

    @Override
    public void onLayThongTinKhaoSatError(Object object) {
        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được thông tin khảo sát. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onLayKhachHangSuccess(Object object) {

        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            KhachHangKhaoSatResponse[] arrDanhSachKhachHang = gson.fromJson(jsonElement, KhachHangKhaoSatResponse[].class);
            if(danhSachKhachHang != null && danhSachKhachHang.size() > 0){
                danhSachKhachHang.clear();
            }
            danhSachKhachHang = new ArrayList<>(Arrays.asList(arrDanhSachKhachHang));
//            ArrayList<String> arrDanhSach = new ArrayList<>();
//            for (int i = 0; i < danhSachKhachHang.size(); i++){
//                try{
////                    danhSachKhachHang.get(i).setName(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachKhachHang.get(i).getName(), "UTF-8")));
////                    arrDanhSach.add(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachKhachHang.get(i).getName(), "UTF-8")));
//                }catch (Exception ex){
//                    Log.d("Convert Error", ex.toString());
//                }
//
//            }
            if(danhSachKhachHang.size() > 0){
                adapterKhachHang = new KhachHangKhaoSatAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachKhachHang);
                txtTenKhachHang.setAdapter(adapterKhachHang);
                adapterKhachHang.notifyDataSetChanged();
            }
            txtTenKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    KhachHangKhaoSatResponse khachHangResponse = adapterKhachHang.getItem(position);
                    Toast.makeText(getActivity(), khachHangResponse.getId(), Toast.LENGTH_SHORT).show();
                    idKhachHang = khachHangResponse.getId();
                    if(khachHangResponse.getDaiLy().equals("1")){
                        ckSanPhamHoangThach.setChecked(true);
                    }
                }
            });


        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
//        hideProgressBar();
    }

    @Override
    public void onLayKhachHangError(Object object) {

        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách khách hàng. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onCapNhatKhaoSatSuccess(Object object) {

        activity.hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        "Cập nhật thông tin khảo sát thành công",
                        false);
            }else {
//                String noiDungThongBao =  conv.getUTF8StringFromNCR(URLDecoder.decode(response.get(0).getResult(), "UTF-8"));
                String noiDungThongBao =  response.get(0).getResult();

                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        noiDungThongBao,
                        false);
            }
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onCapNhatKhaoSatError(Object object) {

        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không cập nhật thông tin khảo sát. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onLayThongTinKhachHangSuccess(Object object) {
        activity.hideProgressBar();
        if(ORG_ID.equals("")){
            return;
        }
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            KhachHangKhaoSatResponse[] arrKhachHang = gson.fromJson(jsonElement, KhachHangKhaoSatResponse[].class);

            List<KhachHangKhaoSatResponse> thongTinKhachHang = new ArrayList<>(Arrays.asList(arrKhachHang));
//            if(response.get(0) != null){

//                txtTenKhachHang.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(thongTinKhachHang.get(0).getName(), "UTF-8")));
                txtTenKhachHang.setText(thongTinKhachHang.get(0).getName());
                idKhachHang = thongTinKhachHang.get(0).getId();
                if(thongTinKhachHang.get(0).getDaiLy().equals("1")){
                    ckSanPhamHoangThach.setChecked(true);
                }else {
                    ckSanPhamHoangThach.setChecked(false);
                }
//            }

            activity.hideProgressBar();
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayThongTinKhachHangError(Object object) {

        activity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được thông tin khách hàng. Vui lòng thử lại!",
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



//    public void showProgressBar() {
//        try{
//            if (hud != null && !hud.isShowing())
//                hud.show();
//            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }catch (Exception ex){
//
//        }
//
//    }
//
//    public void hideProgressBar() {
//        try{
//            if (hud != null && hud.isShowing())
//                hud.dismiss();
//            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }catch (Exception ex){
//
//        }
//
//    }


    public void dialogSoLieuKhaoSat(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_so_lieu_khao_sat);
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
        // end
        txtNhanHieu = (SearchableSpinner) dialog.findViewById(R.id.txtNhanHieu);
        txtChungLoai = (SearchableSpinner) dialog.findViewById(R.id.txtChungLoai);
        txtNhaPhanPhoi = (SearchableSpinner) dialog.findViewById(R.id.txtNhaPhanPhoi);

        txtCPVanTai = (EditText) dialog.findViewById(R.id.txtCPVanTai);
        txtCPBocXep = (EditText) dialog.findViewById(R.id.txtCPBocXep);
        txtGiaDenCuaHang = (EditText) dialog.findViewById(R.id.txtGiaDenCuaHang);
        txtGiaBan = (EditText) dialog.findViewById(R.id.txtGiaBan);
        txtSanLuongDuKien = (EditText) dialog.findViewById(R.id.txtSanLuongDuKien);
        // set default
        if(danhSachNhanHieu.size() > 0){
            idNhanHieu = danhSachNhanHieu.get(0).getId();
            nameNhanHieu = danhSachNhanHieu.get(0).getName();
        }
        if(danhSachChungLoai.size() > 0){
            idChungLoai = danhSachChungLoai.get(0).getId();
            nameChungLoai = danhSachChungLoai.get(0).getName();
        }

        if(danhSachNhaPhanPhoi.size() > 0){
            idNhaPhanPhoi = danhSachNhaPhanPhoi.get(0).getId();
            nameNhaPhanPhoi = danhSachNhaPhanPhoi.get(0).getName();
        }


        // end

        Button btnThemTiep = (Button) dialog.findViewById(R.id.btnThemTiep);
        Button btnThemDong = (Button) dialog.findViewById(R.id.btnThemDong);
        Button btnDong = (Button) dialog.findViewById(R.id.btnDong);

        if(danhSachNhanHieu != null && danhSachNhanHieu.size() > 0){

            final ArrayAdapter<String> nhanHieuAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrNhanHieu);
            nhanHieuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtNhanHieu.setAdapter(nhanHieuAdapter);

            txtNhanHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(getActivity(), danhSachNhanHieu.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idNhanHieu = danhSachNhanHieu.get(position).getId();
                    nameNhanHieu = danhSachNhanHieu.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        if(danhSachChungLoai != null && danhSachChungLoai.size() > 0){
            final ArrayAdapter<String> chungLoaiAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrChungLoai);
            chungLoaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtChungLoai.setAdapter(chungLoaiAdapter);

            txtChungLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(getActivity(), danhSachChungLoai.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idChungLoai = danhSachChungLoai.get(position).getId();
                    nameChungLoai = danhSachChungLoai.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



        }

        if(danhSachNhaPhanPhoi != null && danhSachNhaPhanPhoi.size() > 0){
            final ArrayAdapter<String> nhaPhanPhoiAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrNhaPhanPhoi);
            nhaPhanPhoiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtNhaPhanPhoi.setAdapter(nhaPhanPhoiAdapter);

            txtNhaPhanPhoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), danhSachNhaPhanPhoi.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idNhaPhanPhoi = danhSachNhaPhanPhoi.get(position).getId();
                    nameNhaPhanPhoi = danhSachNhaPhanPhoi.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        dialog.show();

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnThemDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thêm mới thông tin thay đổi
                if(checkValid(txtCPVanTai, txtCPBocXep, txtGiaDenCuaHang, txtGiaBan, txtSanLuongDuKien)){
                    ThongTinKhaoSatChiTietResponse itemKhaoSat = new ThongTinKhaoSatChiTietResponse();
                    itemKhaoSat.setBrand(nameNhanHieu);
                    itemKhaoSat.setBrand_id(idNhanHieu);
                    itemKhaoSat.setProduct_cat(nameChungLoai);
                    itemKhaoSat.setProduct_cat_id(idChungLoai);
                    itemKhaoSat.setNhaphanphoi(nameNhaPhanPhoi);
                    itemKhaoSat.setDistributor_id(idNhaPhanPhoi);

                    itemKhaoSat.setTransport_cost(txtCPVanTai.getText().toString().trim());
                    itemKhaoSat.setLoading_cost(txtCPBocXep.getText().toString().trim());
                    itemKhaoSat.setBuying_price(txtGiaDenCuaHang.getText().toString().trim());
                    itemKhaoSat.setSelling_price(txtGiaBan.getText().toString().trim());
                    itemKhaoSat.setQuantity_expected(txtSanLuongDuKien.getText().toString().trim());
                    if(thongTinKhaoSatResponses != null){
                        thongTinKhaoSatResponses.add(itemKhaoSat);
                    }else {
                        thongTinKhaoSatResponses = new ArrayList<ThongTinKhaoSatChiTietResponse>();
                        thongTinKhaoSatResponses.add(itemKhaoSat);
                    }
                    if(thongTinKhaoSatAdapter != null){

                    }else {
                        thongTinKhaoSatAdapter = new ThongTinKhaoSatAdapter(getActivity(), R.layout.item_thong_tin_khao_sat, thongTinKhaoSatResponses, thongTinKhaoSatFragment);
                        lvDanhMucKhaoSat.setAdapter(thongTinKhaoSatAdapter);
                    }

                    thongTinKhaoSatAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(lvDanhMucKhaoSat);
                    dialog.dismiss();
                }

            }
        });

        btnThemTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thêm mới thông tin thay đổi
                if(checkValid(txtCPVanTai, txtCPBocXep, txtGiaDenCuaHang, txtGiaBan, txtSanLuongDuKien)){
                    ThongTinKhaoSatChiTietResponse itemKhaoSat = new ThongTinKhaoSatChiTietResponse();
                    itemKhaoSat.setBrand(nameNhanHieu);
                    itemKhaoSat.setBrand_id(idNhanHieu);
                    itemKhaoSat.setProduct_cat(nameChungLoai);
                    itemKhaoSat.setProduct_cat_id(idChungLoai);
                    itemKhaoSat.setNhaphanphoi(nameNhaPhanPhoi);
                    itemKhaoSat.setDistributor_id(idNhaPhanPhoi);

                    itemKhaoSat.setTransport_cost(txtCPVanTai.getText().toString().trim());
                    itemKhaoSat.setLoading_cost(txtCPBocXep.getText().toString().trim());
                    itemKhaoSat.setBuying_price(txtGiaDenCuaHang.getText().toString().trim());
                    itemKhaoSat.setSelling_price(txtGiaBan.getText().toString().trim());
                    itemKhaoSat.setQuantity_expected(txtSanLuongDuKien.getText().toString().trim());

                    if(thongTinKhaoSatResponses != null){
                        thongTinKhaoSatResponses.add(itemKhaoSat);
                    }else {
                        thongTinKhaoSatResponses = new ArrayList<ThongTinKhaoSatChiTietResponse>();
                        thongTinKhaoSatResponses.add(itemKhaoSat);
                    }

                    // reset value set to default
                    idNhanHieu = danhSachNhanHieu.get(0).getId();
                    nameNhanHieu = danhSachNhanHieu.get(0).getName();
                    idChungLoai = danhSachChungLoai.get(0).getId();
                    nameChungLoai = danhSachChungLoai.get(0).getName();
                    idNhaPhanPhoi = danhSachNhaPhanPhoi.get(0).getId();
                    nameNhaPhanPhoi = danhSachNhaPhanPhoi.get(0).getName();
                    txtCPVanTai.setText("");
                    txtCPBocXep.setText("");
                    txtGiaDenCuaHang.setText("");
                    txtGiaBan.setText("");
                    txtSanLuongDuKien.setText("");
                    if(thongTinKhaoSatAdapter != null){

                    }else {
                        thongTinKhaoSatAdapter = new ThongTinKhaoSatAdapter(getActivity(), R.layout.item_thong_tin_khao_sat, thongTinKhaoSatResponses, thongTinKhaoSatFragment);
                        lvDanhMucKhaoSat.setAdapter(thongTinKhaoSatAdapter);
                    }

                    thongTinKhaoSatAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(lvDanhMucKhaoSat);
                }

            }
        });
    }


    public void dialogSoLieuKhaoSat(final ThongTinKhaoSatChiTietResponse thongTinKhaoSatChiTietResponse, final int position){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_so_lieu_khao_sat);
        dialog.setCancelable(false);


        // chỉnh kích cỡ dialog show
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        height = (int)(height * 1);
        width = (int)(width * 0.5);
        lp.width = height;
        lp.height = width;

        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        // end
        txtNhanHieu = (SearchableSpinner) dialog.findViewById(R.id.txtNhanHieu);
        txtChungLoai = (SearchableSpinner) dialog.findViewById(R.id.txtChungLoai);
        txtNhaPhanPhoi = (SearchableSpinner) dialog.findViewById(R.id.txtNhaPhanPhoi);

        txtCPVanTai = (EditText) dialog.findViewById(R.id.txtCPVanTai);
        txtCPBocXep = (EditText) dialog.findViewById(R.id.txtCPBocXep);
        txtGiaDenCuaHang = (EditText) dialog.findViewById(R.id.txtGiaDenCuaHang);
        txtGiaBan = (EditText) dialog.findViewById(R.id.txtGiaBan);
        txtSanLuongDuKien = (EditText) dialog.findViewById(R.id.txtSanLuongDuKien);

        nameNhanHieu = thongTinKhaoSatChiTietResponse.getBrand();
        idChungLoai = thongTinKhaoSatChiTietResponse.getBrand_id();
        nameChungLoai = thongTinKhaoSatChiTietResponse.getProduct_cat();
        idChungLoai = thongTinKhaoSatChiTietResponse.getProduct_cat_id();
        nameNhaPhanPhoi = thongTinKhaoSatChiTietResponse.getNhaphanphoi();
        idNhaPhanPhoi = thongTinKhaoSatChiTietResponse.getDistributor_id();

        txtCPVanTai.setText(thongTinKhaoSatChiTietResponse.getTransport_cost());
        txtCPBocXep.setText(thongTinKhaoSatChiTietResponse.getLoading_cost());
        txtGiaDenCuaHang.setText(thongTinKhaoSatChiTietResponse.getBuying_price());
        txtGiaBan.setText(thongTinKhaoSatChiTietResponse.getSelling_price());
        txtSanLuongDuKien.setText(thongTinKhaoSatChiTietResponse.getQuantity_expected());


        Button btnThemTiep = (Button) dialog.findViewById(R.id.btnThemTiep);
        Button btnThemDong = (Button) dialog.findViewById(R.id.btnThemDong);
        Button btnDong = (Button) dialog.findViewById(R.id.btnDong);
        btnThemTiep.setVisibility(View.GONE);
        btnThemDong.setText(getString(R.string.sua));
        if(danhSachNhanHieu != null && danhSachNhanHieu.size() > 0){

            final ArrayAdapter<String> nhanHieuAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrNhanHieu);
            nhanHieuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtNhanHieu.setAdapter(nhanHieuAdapter);
            for(int i = 0; i < danhSachNhanHieu.size(); i++){
                if(danhSachNhanHieu.get(i).getId().equals(thongTinKhaoSatChiTietResponse.getBrand_id())){
                txtNhanHieu.setSelection(i);
                    break;
                }
            }

            txtNhanHieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), danhSachNhanHieu.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idNhanHieu = danhSachNhanHieu.get(position).getId();
                    nameNhanHieu = danhSachNhanHieu.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        if(danhSachChungLoai != null && danhSachChungLoai.size() > 0){
            final ArrayAdapter<String> chungLoaiAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrChungLoai);
            chungLoaiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtChungLoai.setAdapter(chungLoaiAdapter);
            for(int i = 0; i < danhSachChungLoai.size(); i++){
                if(danhSachChungLoai.get(i).getId().equals(thongTinKhaoSatChiTietResponse.getProduct_cat_id())){
                    txtChungLoai.setSelection(i);
                    break;
                }
            }
            txtChungLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), danhSachChungLoai.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idChungLoai = danhSachChungLoai.get(position).getId();
                    nameChungLoai = danhSachChungLoai.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        if(danhSachNhaPhanPhoi != null && danhSachNhaPhanPhoi.size() > 0){
            final ArrayAdapter<String> nhaPhanPhoiAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item, arrNhaPhanPhoi);
            nhaPhanPhoiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            txtNhaPhanPhoi.setAdapter(nhaPhanPhoiAdapter);
            for(int i = 0; i < danhSachNhaPhanPhoi.size(); i++){
                if(danhSachNhaPhanPhoi.get(i).getId().equals(thongTinKhaoSatChiTietResponse.getDistributor_id())){
                    txtNhaPhanPhoi.setSelection(i);
                    break;
                }
            }
            txtNhaPhanPhoi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), danhSachNhaPhanPhoi.get(position).getId(), Toast.LENGTH_SHORT).show();
                    idNhaPhanPhoi = danhSachNhaPhanPhoi.get(position).getId();
                    nameNhaPhanPhoi = danhSachNhaPhanPhoi.get(position).getName();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        dialog.show();

        btnThemDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update lại thông tin thay đổi
                if(checkValid(txtCPVanTai, txtCPBocXep, txtGiaDenCuaHang, txtGiaBan, txtSanLuongDuKien)) {
                    thongTinKhaoSatResponses.get(position).setBrand(nameNhanHieu);
                    thongTinKhaoSatResponses.get(position).setBrand_id(idNhanHieu);
                    thongTinKhaoSatResponses.get(position).setProduct_cat(nameChungLoai);
                    thongTinKhaoSatResponses.get(position).setProduct_cat_id(idChungLoai);
                    thongTinKhaoSatResponses.get(position).setNhaphanphoi(nameNhaPhanPhoi);
                    thongTinKhaoSatResponses.get(position).setDistributor_id(idNhaPhanPhoi);

                    thongTinKhaoSatResponses.get(position).setTransport_cost(txtCPVanTai.getText().toString().trim());
                    thongTinKhaoSatResponses.get(position).setLoading_cost(txtCPBocXep.getText().toString().trim());
                    thongTinKhaoSatResponses.get(position).setBuying_price(txtGiaDenCuaHang.getText().toString().trim());
                    thongTinKhaoSatResponses.get(position).setSelling_price(txtGiaBan.getText().toString().trim());
                    thongTinKhaoSatResponses.get(position).setQuantity_expected(txtSanLuongDuKien.getText().toString().trim());

                    thongTinKhaoSatAdapter.notifyDataSetChanged();
                }
                dialog.dismiss();

            }
        });


        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    @Override
    public void onLayNhanHieuSuccess(Object object) {
//        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            NhanHieuResponse[] arrDanhSachNhanHieu = gson.fromJson(jsonElement, NhanHieuResponse[].class);
            danhSachNhanHieu = new ArrayList<>(Arrays.asList(arrDanhSachNhanHieu));
            if(arrNhanHieu != null && arrNhanHieu.size() > 0){
                arrNhanHieu.clear();
            }

            for (int i = 0; i < danhSachNhanHieu.size(); i++){
//                danhSachNhanHieu.get(i).setName(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachNhanHieu.get(i).getName(), "UTF-8")));
//                arrNhanHieu.add(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachNhanHieu.get(i).getName(), "UTF-8")));
                arrNhanHieu.add(danhSachNhanHieu.get(i).getName());
            }
            adapterNhanHieu = new DanhSachNhanHieuAdapter (getActivity(), R.layout.item_nhan_hieu, danhSachNhanHieu);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayNhanHieuError(Object object) {
//        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách nhãn hiệu. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onLayChungLoaiSuccess(Object object) {
//        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            NhanHieuResponse[] arrDanhSachChungLoai = gson.fromJson(jsonElement, NhanHieuResponse[].class);
            danhSachChungLoai = new ArrayList<>(Arrays.asList(arrDanhSachChungLoai));
            if(arrChungLoai != null || arrChungLoai.size() > 0){
                arrChungLoai.clear();
            }
            for (int i = 0; i < danhSachChungLoai.size(); i++){
                try{
//                    danhSachChungLoai.get(i).setName(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachChungLoai.get(i).getName(), "UTF-8")));
//                    arrChungLoai.add(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachChungLoai.get(i).getName(), "UTF-8")));
                    arrChungLoai.add(danhSachChungLoai.get(i).getName());
                }catch (Exception ex){

                }

            }

            adapterChungLoai = new DanhSachNhanHieuAdapter (getActivity(), R.layout.item_nhan_hieu, danhSachChungLoai);
        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayChungLoaiError(Object object) {
//        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách chủng loại mặt hàng. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onLayNhaPhanPhoiSuccess(Object object) {
//        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            NhanHieuResponse[] arrDanhSachNhaPhanPhoi = gson.fromJson(jsonElement, NhanHieuResponse[].class);
            if(arrNhaPhanPhoi != null && arrNhaPhanPhoi.size() > 0){
                arrNhaPhanPhoi.clear();
            }
            danhSachNhaPhanPhoi = new ArrayList<>(Arrays.asList(arrDanhSachNhaPhanPhoi));

            for (int i = 0; i < danhSachNhaPhanPhoi.size(); i++){
//                danhSachNhaPhanPhoi.get(i).setName(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachNhaPhanPhoi.get(i).getName(), "UTF-8")));
//                arrNhaPhanPhoi.add(conv.getUTF8StringFromNCR(URLDecoder.decode(danhSachNhaPhanPhoi.get(i).getName(), "UTF-8")));
                arrNhaPhanPhoi.add(danhSachNhaPhanPhoi.get(i).getName());
            }

            adapterNhaPhanPHoi = new DanhSachNhanHieuAdapter (getActivity(), R.layout.item_nhan_hieu, danhSachNhaPhanPhoi);

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onLayNhaPhanPhoiError(Object object) {
//        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được nhà phân phối. Vui lòng thử lại!",
                false);
    }

    private void showDatePicker(final EditText e) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                e.setText((monthOfYear + 1) + "/" + year);
                Time chosenDate = new Time();
                chosenDate.set(dayOfMonth, monthOfYear, year);
                long dtDob = chosenDate.toMillis(true);
                CharSequence strDate = DateFormat.format("dd/MM/yyyy", dtDob);
            }
        }, mYear, mMonth, mDay);
        dpd.show();
    }

    public void XoaThongTinKhaoSat(int position){
        thongTinKhaoSatResponses.remove(position);
        thongTinKhaoSatAdapter.notifyDataSetChanged();
    }

    private boolean checkValid(EditText...editTexts){
        boolean flagCheck = true;
        for (int i = 0; i < editTexts.length; i++){
            if(editTexts[i].getText().toString().trim().equals("")){
                editTexts[i].setError("Trường bắt buộc không được để trống");
                editTexts[i].requestFocus();
                flagCheck = false;
            }
        }
        return flagCheck;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
