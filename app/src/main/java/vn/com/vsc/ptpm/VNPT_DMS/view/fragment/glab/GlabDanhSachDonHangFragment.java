package vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.glab.DanhSachDatHangAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.glab.TrangThaiDonHangAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.DateValidator;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachKhachHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachNhaCungCapResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.TrangThaiDonHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachDonDatHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachNhaCungCapImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabDanhSachTrangThaiDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.GlabXoaDonHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BaseFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachNhaCungCapView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachTrangThaiDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabXoaDonHangView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlabDanhSachDonHangFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 * create an instance of this fragment.
 */
public class GlabDanhSachDonHangFragment extends BaseFragment implements IGlabDanhSachDonDatHangView, IGlabDanhSachTrangThaiDonHangView,
                                                                    IGlabDanhSachNhaCungCapView, IGlabXoaDonHangView{
    Controller control = new Controller(getActivity());
    private OnFragmentInteractionListener mListener;
    private GlabDanhSachDonDatHangImpl danhSachDonDatHangPresenter;
    private GlabDanhSachNhaCungCapImpl danhSachNhaCungCapPresenter;
    private GlabDanhSachTrangThaiDonHangImpl trangThaiDonHangPresenter;

    private GlabXoaDonHangImpl xoaDonHangPresenter;
    private ListView lvDanhSachDonHang;
    private EditText txtSearch;
    private CheckBox ckTimNangCao;
    private Button btnClose;
    private Button btnSearch;
    private LinearLayout layoutAdvanceSearch;
    private boolean isLoadmore = true;
    private List<TrangThaiDonHangResponse> trangThaiDonHangResponse;
    private List<DanhSachNhaCungCapResponse> danhSachNhaCungCapResponse;
    private List<DanhSachKhachHangResponse> danhSachKhachHangResponse;
    private GlabDanhSachDonDatHangRequest danhSachDonDatHangRequest;
    private TrangThaiDonHangAdapter trangThaiDonHangAdapter;
    private String idTrangThai = "";
    private String idNhaCungCap = "";
    private DateValidator dateValidator;
    private int pageNo = 1;
    private int pageSize = 10;
    private int pageRec = 100;
    private DanhSachDatHangAdapter danhSachDatHangAdapter;

    // form tìm kiếm nâng cao
    private EditText txtMaDonHang, txtKhachHang, txtNgayBatDau, txtLoaiSanPham, txtGiaTri, txtNgayYCCH, txtNgayKetThuc, txtNguoiLap;
    private Spinner spTrangThaiDonHang, spNhaCungCap;
    private ImageButton btNgayBatDau, btNgayKetThuc, btNgayCapHang;

    private TextView tv_alert;
    // end
    public GlabDanhSachDonHangFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_glab_danh_sach_don_hang, container, false);
        setHasOptionsMenu(true);// them option
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        ckTimNangCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckTimNangCao.isChecked()){
                    layoutAdvanceSearch.setVisibility(View.VISIBLE);
                    txtSearch.setEnabled(false);
                }else {
                    layoutAdvanceSearch.setVisibility(View.GONE);
                    txtSearch.setEnabled(true);
                }
            }
        });

        lvDanhSachDonHang.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if(visibleItemCount != 0 && (lastInScreen == totalItemCount) && isLoadmore){
                    pageNo += 1;
                    showProgressBar();
                    danhSachDonDatHangPresenter.getDanhSachDonDatHang(pageNo, pageSize, danhSachDonDatHangRequest);
                }
            }
        });

        spTrangThaiDonHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "select: " + trangThaiDonHangResponse.get(position).getId(), Toast.LENGTH_SHORT).show();
                idTrangThai = trangThaiDonHangResponse.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNhaCungCap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idNhaCungCap = danhSachNhaCungCapResponse.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validDate()){
                    showProgressBar();
                    if(danhSachKhachHangResponse != null){
                        danhSachKhachHangResponse.clear();
                    }
                    pageNo = 1;
                    danhSachDonDatHangRequest = new GlabDanhSachDonDatHangRequest();
                    if(ckTimNangCao.isChecked()){
                        danhSachDonDatHangRequest.setId(txtMaDonHang.getText().toString());
                    }else {
                        danhSachDonDatHangRequest.setId(txtSearch.getText().toString());
                    }

                    danhSachDonDatHangRequest.setKh(txtKhachHang.getText().toString());
                    danhSachDonDatHangRequest.setNcc(idNhaCungCap);
                    danhSachDonDatHangRequest.setRd(txtNgayYCCH.getText().toString());
                    danhSachDonDatHangRequest.setSd1(txtNgayBatDau.getText().toString());
                    danhSachDonDatHangRequest.setSd2(txtNgayKetThuc.getText().toString());
                    danhSachDonDatHangRequest.setSt(txtGiaTri.getText().toString());
                    danhSachDonDatHangRequest.setTensp(txtLoaiSanPham.getText().toString());
                    danhSachDonDatHangRequest.setTt(idTrangThai);
                    danhSachDonDatHangPresenter.getDanhSachDonDatHang(pageNo, pageSize, danhSachDonDatHangRequest);
                }

            }
        });

        btNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.showDatePicker(txtNgayBatDau, getActivity());
            }
        });

        btNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.showDatePicker(txtNgayKetThuc, getActivity());
            }
        });

        btNgayCapHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.showDatePicker(txtNgayYCCH, getActivity());
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void addControls(View view) {
        dateValidator = new DateValidator();
        danhSachDatHangAdapter = null;
        danhSachDonDatHangPresenter = new GlabDanhSachDonDatHangImpl(this);
        danhSachNhaCungCapPresenter = new GlabDanhSachNhaCungCapImpl(this);
        trangThaiDonHangPresenter = new GlabDanhSachTrangThaiDonHangImpl(this);

        xoaDonHangPresenter = new GlabXoaDonHangImpl(this);

        danhSachDonDatHangRequest = new GlabDanhSachDonDatHangRequest();
        danhSachDonDatHangRequest.setId("");
        danhSachDonDatHangRequest.setKh("");
        danhSachDonDatHangRequest.setNcc("");
        danhSachDonDatHangRequest.setRd("");
        danhSachDonDatHangRequest.setSd1("");
        danhSachDonDatHangRequest.setSd2("");
        danhSachDonDatHangRequest.setSt("");
        danhSachDonDatHangRequest.setTensp("");
        danhSachDonDatHangRequest.setTt("");
        danhSachKhachHangResponse = new ArrayList<>();
        trangThaiDonHangResponse = new ArrayList<>();
        danhSachNhaCungCapResponse = new ArrayList<>();
        tv_alert = (TextView) view.findViewById(R.id.tv_alert);
        lvDanhSachDonHang = (ListView) view.findViewById(R.id.lvDanhSachDonHang);
        txtSearch = (EditText) view.findViewById(R.id.txtSearch);
        ckTimNangCao = (CheckBox) view.findViewById(R.id.ckTimNangCao);
        btnClose = (Button) view.findViewById(R.id.btnClose);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        layoutAdvanceSearch = (LinearLayout) view.findViewById(R.id.layoutAdvanceSearch);

        // tìm kiếm nâng cao
        txtMaDonHang = (EditText) view.findViewById(R.id.txtMaDonHang);
        txtKhachHang = (EditText) view.findViewById(R.id.txtKhachHang);
        txtNgayBatDau = (EditText) view.findViewById(R.id.txtNgayBatDau);
        txtLoaiSanPham = (EditText) view.findViewById(R.id.txtLoaiSanPham);
        txtGiaTri = (EditText) view.findViewById(R.id.txtGiaTri);
        txtNgayYCCH = (EditText) view.findViewById(R.id.txtNgayYCCH);
        txtNgayKetThuc = (EditText) view.findViewById(R.id.txtNgayKetThuc);
        txtNguoiLap = (EditText) view.findViewById(R.id.txtNguoiLap);

        spTrangThaiDonHang = (Spinner) view.findViewById(R.id.spTrangThaiDonHang);
        spNhaCungCap = (Spinner) view.findViewById(R.id.spNhaCungCap);

        btNgayBatDau = (ImageButton) view.findViewById(R.id.btn_ngayBD);
        btNgayCapHang = (ImageButton) view.findViewById(R.id.btn_ngayCH);
        btNgayKetThuc = (ImageButton) view.findViewById(R.id.btn_ngayKT);
        // end
        ckTimNangCao.setChecked(false);
        txtSearch.setText("");
        showProgressBar();
        danhSachDonDatHangPresenter.getDanhSachDonDatHang(pageNo, pageSize, danhSachDonDatHangRequest);
        trangThaiDonHangPresenter.getDanhSachTrangThaiDonHang();
        danhSachNhaCungCapPresenter.getDanhSachNhaCungCap();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onGlabDanhSachDonDatHangSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachKhachHangResponse[] arrDanhSachKhachHang = gson.fromJson(jsonElement, DanhSachKhachHangResponse[].class);
            List<DanhSachKhachHangResponse> response = new ArrayList<>(Arrays.asList(arrDanhSachKhachHang));
            if(response.size() > 0){
                danhSachKhachHangResponse.addAll(response);
                tv_alert.setVisibility(View.GONE);
                if(response.size() < pageSize){
                    isLoadmore = false;
                }
                if(danhSachDatHangAdapter != null){
                    danhSachDatHangAdapter.notifyDataSetChanged();

                }else {
                    danhSachDatHangAdapter = new DanhSachDatHangAdapter(getActivity(), R.layout.item_list_lsdh, danhSachKhachHangResponse, this, (MainActivity) getActivity());
                    lvDanhSachDonHang.setAdapter(danhSachDatHangAdapter);
                }
            }else {
                tv_alert.setVisibility(View.VISIBLE);
            }


        }catch (Exception ex){
            Log.d("Parse Error: ", ex.toString());
        }
        hideProgressBar();
    }

    @Override
    public void onGlabDanhSachDonDatHangError(Object object) {
        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách đơn hàng",
                false);
    }

    @Override
    public void onGlabDanhSachTrangThaiDonHangSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            TrangThaiDonHangResponse[] arrTrangThaiDonHang = gson.fromJson(jsonElement, TrangThaiDonHangResponse[].class);
            List<TrangThaiDonHangResponse> response = new ArrayList<>(Arrays.asList(arrTrangThaiDonHang));
            if(response != null && response.size() > 0){
                trangThaiDonHangResponse.add(new TrangThaiDonHangResponse("", "Tất cả"));
                trangThaiDonHangResponse.addAll(response);
                ArrayList<String> arrayTrangThai = new ArrayList<>();
                for(int i = 0; i < trangThaiDonHangResponse.size(); i++){
                    arrayTrangThai.add(trangThaiDonHangResponse.get(i).getName());
                }
                ArrayAdapter<String> trangThaiDonHangAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayTrangThai);
                trangThaiDonHangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spTrangThaiDonHang.setAdapter(trangThaiDonHangAdapter);
            }

        }catch (Exception ex){
            Log.d("Parse Error: ", ex.toString());
        }
    }

    @Override
    public void onGlabDanhSachTrangThaiDonHangError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được trạng thái đơn hàng!",
                false);
    }

    @Override
    public void onGlabDanhSachNhaCungCapSuccess(Object object) {
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachNhaCungCapResponse[] arrDanhSachNhaCungCap = gson.fromJson(jsonElement, DanhSachNhaCungCapResponse[].class);
            List<DanhSachNhaCungCapResponse> response = new ArrayList<>(Arrays.asList(arrDanhSachNhaCungCap));
            if(response != null && response.size() > 0){
                danhSachNhaCungCapResponse.add(new DanhSachNhaCungCapResponse("", "Tất cả"));
                danhSachNhaCungCapResponse.addAll(response);
                ArrayList<String> arrayNhaCungCap = new ArrayList<>();
                for(int i = 0; i < danhSachNhaCungCapResponse.size(); i++){
                    arrayNhaCungCap.add(danhSachNhaCungCapResponse.get(i).getName());
                }
                ArrayAdapter<String> danhSachNhaCungCapAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayNhaCungCap);
                danhSachNhaCungCapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spNhaCungCap.setAdapter(danhSachNhaCungCapAdapter);
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

    public void xoaDonHang(int idDonHang){
        xoaDonHangPresenter.getXoaDonHang(idDonHang);
    }

    @Override
    public void onGlabXoaDonHangSuccess(Object object) {
        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")){
                if(danhSachKhachHangResponse != null && danhSachKhachHangResponse.size() > 0){
                    danhSachKhachHangResponse.clear();
                }
                pageNo = 1;
                danhSachDonDatHangRequest = new GlabDanhSachDonDatHangRequest();
                danhSachDonDatHangRequest.setId("");
                danhSachDonDatHangRequest.setKh("");
                danhSachDonDatHangRequest.setNcc("");
                danhSachDonDatHangRequest.setRd("");
                danhSachDonDatHangRequest.setSd1("");
                danhSachDonDatHangRequest.setSd2("");
                danhSachDonDatHangRequest.setSt("");
                danhSachDonDatHangRequest.setTensp("");
                danhSachDonDatHangRequest.setTt("");
                danhSachDonDatHangPresenter.getDanhSachDonDatHang(pageNo, pageSize, danhSachDonDatHangRequest);
            }else {
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
    public void onGlabXoaDonHangError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình xóa đơn hàng!",
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

    private boolean validDate(){
        txtNgayYCCH.getText().toString().matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}");
        txtNgayBatDau.getText().toString().matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}");
        txtNgayKetThuc.getText().toString().matches("[0-9]{2}[/][0-9]{2}[/][0-9]{4}");
        if(!dateValidator.validate(txtNgayYCCH.getText().toString().trim())
                && !txtNgayYCCH.getText().toString().trim().equals("")){
            txtNgayYCCH.setError("Nhập sai định dạng, ngày tìm kiếm phải có định dạng ngày/tháng/năm và có tồn tại theo dương lịch");
            return false;
        }
        if(!dateValidator.validate(txtNgayKetThuc.getText().toString().trim())
                && !txtNgayKetThuc.getText().toString().trim().equals("")){
            txtNgayKetThuc.setError("Nhập sai định dạng, ngày tìm kiếm phải có định dạng ngày/tháng/năm và có tồn tại theo dương lịch");
            return false;
        }
        if(!dateValidator.validate(txtNgayBatDau.getText().toString().trim())
                && !txtNgayBatDau.getText().toString().trim().equals("")){
            txtNgayBatDau.setError("Nhập sai định dạng, ngày tìm kiếm phải có định dạng ngày/tháng/năm và có tồn tại theo dương lịch");
            return false;
        }
        return true;
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

        itemGhetham.setVisible(false);
        itemBando.setVisible(false);
        itemThongtin.setVisible(false);
        itemDatHang.setVisible(false);
        itemKhaoSat.setVisible(true);
        itemKhaoSat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // xóa fragment cũ
                GlabChiTietDonHangFragment chiTietDonHangFragment = (GlabChiTietDonHangFragment) getFragmentManager().findFragmentByTag("vn.com.vsc.ptpm.VNPT_DMS.view.fragment.GlabChiTietDonHangFragment");
                if(chiTietDonHangFragment != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .remove(chiTietDonHangFragment)
                            .commit();
                }

                chiTietDonHangFragment = new GlabChiTietDonHangFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, chiTietDonHangFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return false;
            }
        });
    }



}
