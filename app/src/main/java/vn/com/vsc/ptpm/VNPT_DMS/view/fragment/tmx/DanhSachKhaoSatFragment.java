package vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DanhSachKhaoSatAdapter;

import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangKhaoSatAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.valid.MonthValidator;
import vn.com.vsc.ptpm.VNPT_DMS.event.ThongTinKhachHangKhaoSatEvent;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.CommonResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.DanhSachKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.KhachHangKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.DanhSachKhachHangImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.DanhSachKhaoSatImpl;

import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.XoaKhaoSatImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinKhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IDanhSachKhaoSatView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IXoaPhieuKhaoSatView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DanhSachKhaoSatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DanhSachKhaoSatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachKhaoSatFragment extends Fragment implements IDanhSachKhaoSatView, IXoaPhieuKhaoSatView {

    int startIndex = 1;
    int limitIndex = 100;
    int stepIndex = 100;
    boolean isLoadingMore = true;
    boolean loadingMore = false;

    private OnFragmentInteractionListener mListener;
    private DanhSachKhaoSatImpl danhSachKhaoSatPresenter;
    private XoaKhaoSatImpl xoaKhaoSatPresenter;
    private Controller control;
    private List<DanhSachKhaoSatResponse> listDanhSachKhaoSat;
    private DanhSachKhaoSatAdapter danhSachKhaoSatAdapter;
    private ListView lvDanhSachKhaoSat;
    private KProgressHUD hud;
    private ConvertFont conv = new ConvertFont();
    private int mYear, mMonth, mDay;
    private ImageButton btnThangKhaoSat;
    private EditText txtThang;
    private AutoCompleteTextView txtTenKhachHang;
    private Button btnSearch;
    private DanhSachKhachHangImpl danhSachKhachHangPresenter;
    private String idKhachHang = "";
    private MainActivity mainActivity;
    private KhachHangKhaoSatAdapter adapterKhachHang;
    private List<KhachHangKhaoSatResponse> danhSachKhachHang;
    private MonthValidator monthValidator;

    public DanhSachKhaoSatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachKhaoSatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachKhaoSatFragment newInstance(String param1, String param2) {
        DanhSachKhaoSatFragment fragment = new DanhSachKhaoSatFragment();
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
        final View view = inflater.inflate(R.layout.fragment_danh_sach_khao_sat2, container, false);
        addControls(view);
        addEvents(view);
        return view;
    }



    private void addControls(View view) {
        startIndex = 1;
        limitIndex = 100;
        stepIndex = 100;
        loadingMore = false;
        if(listDanhSachKhaoSat != null){
            listDanhSachKhaoSat.clear();
        }
        getActivity().setTitle("Danh sách khảo sát".toUpperCase());
        monthValidator = new MonthValidator();
        danhSachKhaoSatPresenter = new DanhSachKhaoSatImpl(this);
        xoaKhaoSatPresenter = new XoaKhaoSatImpl(this);
        mainActivity = (MainActivity) getActivity();
        control = new Controller(getActivity());
//        listDanhSachKhaoSat = new ArrayList<>();
        lvDanhSachKhaoSat = (ListView) view.findViewById(R.id.lvDanhSachKhaoSat);
        btnThangKhaoSat = (ImageButton) view.findViewById(R.id.btnThangKhaoSat);
//        danhSachKhachHangPresenter = new DanhSachKhachHangImpl(this);

        txtThang = (EditText) view.findViewById(R.id.txtThang);
        txtTenKhachHang = (AutoCompleteTextView) view.findViewById(R.id.txtKhachHang);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
    }

    private void addEvents(View view) {
        showProgressBar();
        DanhSachKhaoSatRequest request = new DanhSachKhaoSatRequest(startIndex, limitIndex, "", "");
        danhSachKhaoSatPresenter.getDanhSachKhaoSatresenter(request);
        btnThangKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(txtThang);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!monthValidator.validate(txtThang.getText().toString().trim())
                        && !txtThang.getText().toString().trim().equals("")){
                    txtThang.setError("Nhập sai định dạng, tháng tìm kiếm phải có định dạng tháng/năm");
                    txtThang.requestFocus();
                    return;
                }
                showProgressBar();
                if (listDanhSachKhaoSat != null && listDanhSachKhaoSat.size() > 0){
                    listDanhSachKhaoSat.clear();
                }
                try {
                    String tenKhachHang = URLDecoder.decode(txtTenKhachHang.getText().toString().trim(), "UTF-8");
                    DanhSachKhaoSatRequest request = new DanhSachKhaoSatRequest(1, 100, tenKhachHang, txtThang.getText().toString().trim());
                    danhSachKhaoSatPresenter.getDanhSachKhaoSatresenter(request);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


            }
        });


        lvDanhSachKhaoSat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if(visibleItemCount != 0 && (lastInScreen == totalItemCount) && !(loadingMore)){
                    startIndex += 1;
                    limitIndex += stepIndex;
                    DanhSachKhaoSatRequest request = new DanhSachKhaoSatRequest(startIndex, limitIndex, "", "");
                    danhSachKhaoSatPresenter.getDanhSachKhaoSatresenter(request);
//                    Toast.makeText(getActivity(), "Loadmore", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDanhSachKhaoSatSuccess(Object object) {
        hideProgressBar();
        try{

            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            DanhSachKhaoSatResponse[] arrDanhSachKhaoSat = gson.fromJson(jsonElement, DanhSachKhaoSatResponse[].class);
            List<DanhSachKhaoSatResponse> danhSachKhaoSatResponses = new ArrayList<>(Arrays.asList(arrDanhSachKhaoSat));
            if(danhSachKhaoSatResponses.size() < stepIndex){
                loadingMore = true;
            }
//            if(listDanhSachKhaoSat != null && listDanhSachKhaoSat.size() > 0){
//                listDanhSachKhaoSat.clear();
//            }
            for(DanhSachKhaoSatResponse item: danhSachKhaoSatResponses){
                listDanhSachKhaoSat.add(item);
            }
//            listDanhSachKhaoSat.addAll(danhSachKhaoSatResponses);
            danhSachKhaoSatAdapter = new DanhSachKhaoSatAdapter(getActivity(), R.layout.item_danh_sach_khao_sat, listDanhSachKhaoSat, this, (MainActivity) getActivity());
            lvDanhSachKhaoSat.setAdapter(danhSachKhaoSatAdapter);

        }catch (Exception ex){
            Log.d("Parse error:", ex.toString());
        }
    }

    @Override
    public void onDanhSachKhaoSatError(Object object) {
        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được tham số hệ thống. Vui lòng thử lại!",
                false);
    }

    @Override
    public void onXoaKhaoSatSuccess(Object object) {
        hideProgressBar();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(object);
            Type listType = new TypeToken<List<CommonResponse>>(){}.getType();
            List<CommonResponse> response = (List<CommonResponse>) gson.fromJson(String.valueOf(object), listType);
            if(response.get(0).getResult().toLowerCase().equals("true")){
                if(listDanhSachKhaoSat != null && listDanhSachKhaoSat.size() > 0){
                    listDanhSachKhaoSat.clear();
                }
                DanhSachKhaoSatRequest request = new DanhSachKhaoSatRequest(1, 100, "", "");
                danhSachKhaoSatPresenter.getDanhSachKhaoSatresenter(request);
            }else {

                String noiDungThongBao =  conv.getUTF8StringFromNCR(URLDecoder.decode(response.get(0).getResult(), "UTF-8"));

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
    public void onXoaKhaoSatError(Object object) {
        hideProgressBar();
        control.showAlertDialog(
                getActivity(),
                "Thông báo",
                "Không lấy được tham số hệ thống. Vui lòng thử lại!",
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
        itemKhaoSat.setVisible(true);
        itemKhaoSat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                EventBus.getDefault().removeStickyEvent(ThongTinKhachHangKhaoSatEvent.class);
                ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent = new ThongTinKhachHangKhaoSatEvent("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                );
                EventBus.getDefault().postSticky(khachHangKhaoSatEvent);

                // xóa fragment cũ
                ThongTinKhaoSatFragment olderFragment = (ThongTinKhaoSatFragment) getFragmentManager().findFragmentByTag("vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinKhaoSatFragment");
                if(olderFragment != null){
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .remove(olderFragment)
                            .commit();
                }

                ThongTinKhaoSatFragment thongTinKhaoSatFragment = new ThongTinKhaoSatFragment("");
//                mainActivity.replaceFragment(thongTinKhaoSatFragment);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.frame_container, thongTinKhaoSatFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                return false;
            }
        });
    }

    public void xoaKhaoSat(String idKhaoSat){
        showProgressBar();
        xoaKhaoSatPresenter.xoaKhaoSat(idKhaoSat);
    }

    public void showProgressBar() {
        try{
            if (hud != null && !hud.isShowing())
                hud.show();
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }catch (Exception ex){

        }

    }

    public void hideProgressBar() {
        try{
            if (hud != null && hud.isShowing())
                hud.dismiss();
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }catch (Exception ex){

        }

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
}
