package vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DanhSachKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.DanhSachDangKyLichTrinhAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.config.CodeDef;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.MonthValidator;
import vn.com.vsc.ptpm.VNPT_DMS.event.ThongTinKhachHangKhaoSatEvent;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.DanhSachKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.KhachHangKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachDangKyLichTrinhResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.DanhSachKhachHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachDangKyKyLichTrinhTMXImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.XoaDangKyImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinKhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDanhSachDangKyLichTrinhView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IXoaDangKyView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DanhSachTuyenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DanhSachTuyenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachTuyenFragment extends Fragment implements IDanhSachDangKyLichTrinhView, IXoaDangKyView {
    int pageNo = 1;
    int pageRec = 100;
    int stepIndex = 100;
    boolean isLoadingMore = true;
    boolean loadingMore = false;

    private DanhSachKhaoSatFragment.OnFragmentInteractionListener mListener;
    private DanhSachDangKyKyLichTrinhTMXImpl danhSachLichTrinhPresenter;
    private Controller control;
    private List<DanhSachDangKyLichTrinhResponse> listDanhSachDangKyLichTrinh;
    private DanhSachDangKyLichTrinhAdapter danhSachDangKyLichTrinhAdapter;
    private ListView lvDanhSachTuyen;
    private KProgressHUD hud;
    private ConvertFont conv = new ConvertFont();
    private int mYear, mMonth, mDay, mWeek;
    private ImageButton btnThangKhaoSat;
    private EditText txtThang;
    private AutoCompleteTextView txtTenKhachHang;
    private Button btnSearch;
    private DanhSachKhachHangImpl danhSachKhachHangPresenter;
    private XoaDangKyImpl xoaDangKyPresenter = new XoaDangKyImpl(this);
    private String idKhachHang = "";
    private MainActivity mainActivity;
    private KhachHangKhaoSatAdapter adapterKhachHang;
    private List<KhachHangKhaoSatResponse> danhSachKhachHang;
    private String sDate;
    public DanhSachTuyenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachTuyenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachTuyenFragment newInstance(String param1, String param2) {
        DanhSachTuyenFragment fragment = new DanhSachTuyenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_tuyen_tmx, container, false);
        getActivity().setTitle(getString(R.string.danhSachTuyen).toUpperCase());
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        mainActivity.showProgressBar();
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        sDate = String.valueOf(mDay) + "/" + String.valueOf(mMonth + 1) + "/" + String.valueOf(mYear);

        danhSachLichTrinhPresenter.layDanhSachDangKyLichTrinh(pageNo, pageRec, "", sDate, "");

        lvDanhSachTuyen.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void addControls(View view) {
        pageNo = 1;
        pageRec = 100;
        stepIndex = 100;
        loadingMore = false;
        lvDanhSachTuyen = (ListView) view.findViewById(R.id.lvDanhSachTuyen);
        listDanhSachDangKyLichTrinh = new ArrayList<>();
        getActivity().setTitle("Danh sách tuyến".toUpperCase());
        danhSachLichTrinhPresenter = new DanhSachDangKyKyLichTrinhTMXImpl(this);
        mainActivity = (MainActivity) getActivity();
        danhSachDangKyLichTrinhAdapter = null;
        control = new Controller(getActivity());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDanhSachDangKyLichTrinhSuccess(Object object) {
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachDangKyLichTrinhResponse[] arrDanhSachKhaoSat = gson.fromJson(jsonElement, DanhSachDangKyLichTrinhResponse[].class);
            List<DanhSachDangKyLichTrinhResponse> danhSachKhaoSatResponses = new ArrayList<>(Arrays.asList(arrDanhSachKhaoSat));
            if(danhSachKhaoSatResponses.size() < stepIndex){
                loadingMore = true;
            }
            for(DanhSachDangKyLichTrinhResponse item: danhSachKhaoSatResponses){
                listDanhSachDangKyLichTrinh.add(item);
            }
            if(danhSachDangKyLichTrinhAdapter != null){
                danhSachDangKyLichTrinhAdapter.notifyDataSetChanged();
            }else {
                danhSachDangKyLichTrinhAdapter = new DanhSachDangKyLichTrinhAdapter(getActivity(), R.layout.item_danh_sach_lich_trinh, listDanhSachDangKyLichTrinh, this, (MainActivity) getActivity());
            }
            lvDanhSachTuyen.setAdapter(danhSachDangKyLichTrinhAdapter);

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
        mainActivity.hideProgressBar();
    }

    @Override
    public void onDanhSachDangKyLichTrinhError(Object object) {
        mainActivity.hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được danh sách đăng ký lịch trình. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onXoaDangKySuccess(Object object) {

        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            CommonResponse[] arrResponse = gson.fromJson(jsonElement, CommonResponse[].class);
            List<CommonResponse> response = new ArrayList<>(Arrays.asList(arrResponse));
            if(response.get(0).getResult().toLowerCase().equals("true")) {
                if (listDanhSachDangKyLichTrinh != null && listDanhSachDangKyLichTrinh.size() > 0) {
                    listDanhSachDangKyLichTrinh.clear();
                }
                pageNo = 1;
                pageRec = 100;
                danhSachLichTrinhPresenter.layDanhSachDangKyLichTrinh(pageNo, pageRec, "", sDate, "");
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_STATUS)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        getString(R.string.codeFalseStatus),
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_NO_MANAGER)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        getString(R.string.codeFalseNoManager),
                        false);
            }else if(response.get(0).getResult().equals(CodeDef.CODE_ERROR_FALSE_NO_PERMISSION)){
                control.showAlertDialog(
                        getActivity(),
                        "Thông báo",
                        getString(R.string.codeFalseNoPermission),
                        false);
            }
            else {
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
        mainActivity.hideProgressBar();
    }

    @Override
    public void onXoaDangKyError(Object object) {
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Có lỗi trong quá trình xóa tuyến!",
                false);
    }

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
        itemKhaoSat.setVisible(true);
        itemKhaoSat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // xóa fragment cũ
                ThongTinTuyenFragment olderFragment = (ThongTinTuyenFragment) getFragmentManager().findFragmentByTag("vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinTuyenFragment");
                if(olderFragment != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .remove(olderFragment)
                            .commit();
                }

                ThongTinTuyenFragment thongTinTuyenFragment = new ThongTinTuyenFragment("");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, thongTinTuyenFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return false;
            }
        });
    }

    public void xoaDonHang(String idTuyen){
        xoaDangKyPresenter.xoaDangKy(idTuyen);
    }
}
