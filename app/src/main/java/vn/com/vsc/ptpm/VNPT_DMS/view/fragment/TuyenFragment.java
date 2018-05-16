package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx.KhuVucAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.StringDef;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.event.ThongTinKhachHangKhaoSatEvent;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachVungResponse;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachKhuVucImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachQuanImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachTinhImpl;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen.DanhSachVungImpl;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DSKHListAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.OnCustomClickListener;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.GPSTracker;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.dao.CheckinDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.ThongTinKhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenKhachHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventThongTinTuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BaseDialog;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinnerAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SpinnerAdapterCus;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.KiemTraDatHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabChiTietDonHangFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhuVucView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachQuanView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachTinhView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachVungView;

import static android.R.id.list;

public class TuyenFragment extends ListFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        ILayDanhSachVungView, ILayDanhSachKhuVucView, ILayDanhSachTinhView, ILayDanhSachQuanView {
    private static final String TAG = TuyenFragment.class.getName();
    private KiemTraDatHang kiemTraDatHang;
    // private final int DAT_HANG = 3;
    private Spinner spinnerTuyen;
    private Spinner spinnerTrangthai;
    private EditText txtSearchKH;
    private Button btnSearch;
    private ListView listViewKhachHang;
    private TextView OnCustomClickListener;
    private TextView tvAlert;
    private CheckBox ckSearchAdvence;
    private LinearLayout viewSearchAdvance;
    private AutoCompleteTextView txtVung, txtKhuVuc, txtTinh, txtQuanHuyen;

    // vùng, khu vực, tỉnh, quận
    private DanhSachVungImpl danhSachVungPresenter = new DanhSachVungImpl(this);
    private DanhSachKhuVucImpl danhSachKhuVucPresenter = new DanhSachKhuVucImpl(this);
    private DanhSachTinhImpl danhSachTinhPresenter = new DanhSachTinhImpl(this);
    private DanhSachQuanImpl danhSachQuanPresenter = new DanhSachQuanImpl(this);

    private String idVung = "";
    private String idKhuVuc = "";
    private String idTinh = "";
    private String idQuan = "";
    private List<DanhSachVungResponse> danhSachVung;
    private List<DanhSachVungResponse> danhSachKhuVuc;
    private List<DanhSachVungResponse> danhSachTinh;
    private List<DanhSachVungResponse> danhSachQuan;
    private KhuVucAdapter adapterVung;
    private KhuVucAdapter adapterKhuVuc;
    private KhuVucAdapter adapterTinh;
    private KhuVucAdapter adapterQuan;
    // end
    private GetDSKHTask getDSKHTask;
    private GetDataTuyenTask getDataTuyenTask;
    private GetDSKHGpsTask getDSKHGpsTask;
    private CheckinTask checkinTask;

    // private ArrayAdapter<String> adapterSpinnerTrangthai;
    private SpinnerAdapterCus adapterSpinnerTuyen;
    private SpinnerAdapter adapterSpinnerTrangthai;
    private DSKHListAdapter adapterListKhachHang;
    private ProgressDialog pDialog;
    private Point p;
    private Controller control;

    private final int LICH_SU = 4;
    private ArrayList<KhachHang> glstKhachhang;
    private ArrayList<KhachHang> tempArr;
    private ArrayList<Tuyen> glstTuyen = new ArrayList<>();

    private ArrayList<Tuyen> glstTrangthai;
    private int positionSpinnerTuyen = 0;
    private int positionSpinnerTrangthai = 0;
    private int totalKhachhang = 0;
    private String currentTuyenId = "";
    // mã trạng thái: DONE:
    // "": lấy tất cả, 3: đã đặt, 2: đã đến, 1: chưa đến, 4: chưa cập nhật vị
    // trí, 5: chưa upload ảnh.
    private String currentTrangthaiId = "";
    private String MA_KH = "";
    // private String FILE_DST = "tuyen_dst.txt";
    private String json_dst = "";
    // private static int save = -1;

    // private Bundle savedState = null;
    private boolean isRestoreData = false;
    // private static boolean isScrolling = false;
    // private static boolean isLoadingMore = false;
//	private boolean isFirsttimeCreateFragment = true;
    private Boolean flgErr;
    private boolean isActivityRunning = false;
    private int pageCount = 1;
    private int totalPages = 0;
    public static boolean isTatCa;
    private Activity activity;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private ConvertFont conv = new ConvertFont();
    public static Location currentLocation;
    private Boolean bCheckIn = false;

    public TuyenFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Fragment 1", "onCreateView");
//		isFirsttimeCreateFragment = true;
        View view = inflater.inflate(R.layout.fragment_tuyen, container, false);
        addController(view);
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        flgErr = false;

        if (Config.isOpenNew) {
            Log.i("Fragment 1", "open new");

            MA_KH = "";
            positionSpinnerTrangthai = 0;
            positionSpinnerTuyen = 0;
            txtSearchKH.setText(MA_KH);
        } else {
            Log.i("Fragment 1", "open resume");
            isRestoreData = true;

            // Get data
            SharedPreferences sp = PreferenceManager
                    .getDefaultSharedPreferences(activity);
            positionSpinnerTuyen = sp.getInt(Config.KEY_POSITION_TUYEN, 0);
            Log.d("tuyenid", positionSpinnerTuyen + "");

            positionSpinnerTrangthai = sp.getInt(
                    Config.KEY_POSITION_TRANG_THAI, 0);
            MA_KH = sp.getString(Config.KEY_KHACH_HANG_CODE, "");
            String jsonTuyen = sp.getString(Config.KEY_DATA_TUYEN, "");

            Type type = new TypeToken<ArrayList<Tuyen>>() {
            }.getType();
            try {
                glstTuyen = new Gson().fromJson(jsonTuyen, type);
            } catch (Exception e) {
                e.printStackTrace();
            }

			/*
             * if(savedInstanceState != null && savedState == null) { savedState
			 * = savedInstanceState.getBundle(Config.KEY_BUNDLE_TUYEN); } if
			 * (savedState != null) { isRestoreData = true; if
			 * (savedState.containsKey(Config.KEY_KHACH_HANG_CODE)) { MA_KH =
			 * savedState.getString(Config.KEY_KHACH_HANG_CODE, "");
			 * txtSearchKH.setText(MA_KH); } if
			 * (savedState.containsKey(Config.KEY_POSITION_TUYEN))
			 * positionSpinnerTuyen =
			 * savedState.getInt(Config.KEY_POSITION_TUYEN, 0); if
			 * (savedState.containsKey(Config.KEY_POSITION_TRANG_THAI))
			 * positionSpinnerTrangthai =
			 * savedState.getInt(Config.KEY_POSITION_TRANG_THAI, 0); if
			 * (savedState.containsKey(Config.KEY_TOTAL_KHACH_HANG))
			 * totalKhachhang = savedState.getInt(Config.KEY_TOTAL_KHACH_HANG,
			 * 0);
			 * 
			 * if(savedState.containsKey(Config.KEY_DATA_TUYEN)) glstTuyen =
			 * savedState.getParcelableArrayList(Config.KEY_DATA_TUYEN);
			 * if(savedState.containsKey(Config.KEY_DATA_TRANG_THAI))
			 * glstTrangthai =
			 * savedState.getParcelableArrayList(Config.KEY_DATA_TRANG_THAI);
			 * if(savedState.containsKey(Config.KEY_DATA_KHACH_HANG))
			 * glstKhachhang =
			 * savedState.getParcelableArrayList(Config.KEY_DATA_KHACH_HANG); }
			 * savedState = null;
			 */
        }
        addEvents();
        return view;
    }

    private void addEvents() {
        danhSachVungPresenter.layDanhSachVung();
        danhSachTinhPresenter.layDanhSachTinh("0", "0");
        // vùng click event
        txtVung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                 txtVung.showDropDown();
                return false;
            }
        });

        // remove textview when click
        txtVung.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtVung.setText("");
                    txtKhuVuc.setText("");
                    idVung = "";
                    idKhuVuc = "";
                    danhSachTinhPresenter.layDanhSachTinh("0", "0");

                }
                return false;
            }
        });
        txtKhuVuc.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    txtKhuVuc.setText("");
                    idKhuVuc = "";
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
                    danhSachTinhPresenter.layDanhSachTinh("0", "0");
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
                txtTinh.showDropDown();

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
    }

    private void addController(View view) {
        txtSearchKH = (EditText) view.findViewById(R.id.txtSearchKH);
        txtSearchKH.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearchKH.setText("");
            }
        });
        spinnerTuyen = (Spinner) view.findViewById(R.id.spin_tuyen);
        spinnerTrangthai = (Spinner) view.findViewById(R.id.spin_trangthai);
        btnSearch = (Button) view.findViewById(R.id.btnSearch);
        listViewKhachHang = (ListView) view.findViewById(list);
        tvAlert = (TextView) view.findViewById(R.id.tv_alert);
        ckSearchAdvence = (CheckBox) view.findViewById(R.id.ckSearchAdvence);
        viewSearchAdvance = (LinearLayout) view.findViewById(R.id.viewSearchAdvance);
        txtVung = (AutoCompleteTextView) view.findViewById(R.id.txtVung);
        txtKhuVuc = (AutoCompleteTextView) view.findViewById(R.id.txtKhuVuc);
        txtTinh = (AutoCompleteTextView) view.findViewById(R.id.txtTinh);
        txtQuanHuyen = (AutoCompleteTextView) view.findViewById(R.id.txtQuanHuyen);

        txtVung = (AutoCompleteTextView) view.findViewById(R.id.txtVung);
        txtKhuVuc = (AutoCompleteTextView) view.findViewById(R.id.txtKhuVuc);
        txtTinh = (AutoCompleteTextView) view.findViewById(R.id.txtTinh);
        txtQuanHuyen = (AutoCompleteTextView) view.findViewById(R.id.txtQuanHuyen);
        ckSearchAdvence.setChecked(false);
        viewSearchAdvance.setVisibility(View.GONE);

        ckSearchAdvence.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckSearchAdvence.isChecked()){
                    viewSearchAdvance.setVisibility(View.VISIBLE);
                }else {
                    viewSearchAdvance.setVisibility(View.GONE);
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
                pageCount = 1;
                totalKhachhang = 0;
                flgErr = false;
                mAF.getLatLongCurrent(activity);
                if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                    getDSKHTask = new GetDSKHTask();
                    getDSKHTask.execute();
                }
                if (glstTuyen != null && glstTuyen.size() > 0 && spinnerTuyen != null && spinnerTuyen.getSelectedItem() != null) {
                    if (spinnerTuyen.getSelectedItem().equals(glstTuyen.get(0))) {
                        isTatCa = true;
                    } else {
                        isTatCa = false;
                    }
                }
            }
        });

        // listViewKhachHang.setOnScrollListener(onScrollListener());
    }

    protected synchronized void buildGoogleApiClient() {
        Log.e(TAG + "buildGoogleApiClient", "buildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initValues();
        startLoadData();
        buildGoogleApiClient();
        mGoogleApiClient.connect();
    }

    public void initValues() {
        control = new Controller(activity);
        pageCount = 1;
        totalKhachhang = 0;
        txtSearchKH.setText("");
    }

    private void getDataTrangThai() {
        try {
            glstTrangthai = new ArrayList<Tuyen>();
            Tuyen trangthai1 = new Tuyen("", "Tất cả");
            Tuyen trangthai2 = new Tuyen("1", "Chưa ghé thăm");
            Tuyen trangthai3 = new Tuyen("2", "Đã ghé thăm");
            Tuyen trangthai4 = new Tuyen("3", "Đã đặt và gửi đơn");
            Tuyen trangthai5 = new Tuyen("4", "Chưa cập nhật vị trí");
            Tuyen trangthai6 = new Tuyen("5", "Chưa upload ảnh");

            glstTrangthai.add(trangthai1);
            glstTrangthai.add(trangthai2);
            glstTrangthai.add(trangthai3);
            glstTrangthai.add(trangthai4);
            glstTrangthai.add(trangthai5);
            glstTrangthai.add(trangthai6);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataToSpinnerTrangthai() {
        if (glstTrangthai != null && glstTrangthai.size() > 0) {
            adapterSpinnerTrangthai = new SpinnerAdapter(activity,
                    R.layout.spinner_row, glstTrangthai);
            spinnerTrangthai.setAdapter(adapterSpinnerTrangthai);
            spinnerTrangthai
                    .setOnItemSelectedListener(new OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                   int position, long arg3) {

                            currentTrangthaiId = glstTrangthai.get(position)
                                    .getId();
                            positionSpinnerTrangthai = position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }
                    });
            spinnerTrangthai.setSelection(positionSpinnerTrangthai);
        }
    }

    @SuppressWarnings("unchecked")
    private void getDataTuyen() {
        try {
            glstTuyen = new ArrayList<Tuyen>();
            if (NetworkType.internetIsAvailable(activity)) {
                Type type_Tuyen = new TypeToken<ArrayList<Tuyen>>() {
                }.getType();
                json_dst = control.jsonValues(getURL_DSTuyen_active(), false);
                // control.writeData(activity, FILE_DST, json_dst);
                try {
                    glstTuyen = (ArrayList<Tuyen>) new Gson().fromJson(
                            json_dst, type_Tuyen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                glstTuyen = new TuyenDAL(activity).getAll();
            }

            if (glstTuyen.size() > 0) {
                boolean flag = false;
                if (glstTuyen != null) {
                    boolean checkDayTuyen = false;
                    for (int i = 0; i < glstTuyen.size(); i++) {
                        String[] arr = glstTuyen.get(i).getName().split(",");
                        if (arr.length > 0) {
                            String day = arr[0].toString().trim();
                            if (day.toLowerCase().equals("ngoai tuyen")) {
                                positionSpinnerTuyen = i + 1;
                            }
                            if (getCurDay(day)) {
                                currentTuyenId = glstTuyen.get(i).getId();
                                positionSpinnerTuyen = i + 1;
                                flag = true;
                                checkDayTuyen = true;
                                break;
                            }
                        }
                    }
                    if(!checkDayTuyen){
                        positionSpinnerTuyen = glstTuyen.size();
                    }
                }
                // Neu khong co ngay nao dung -> hiển thị tuyến của ngày gần nhất
                if (!flag) {
                    currentTuyenId = glstTuyen.get(positionSpinnerTuyen - 1)
                            .getId();
                }

                Log.d("tuyenid", currentTuyenId + "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillDataToSpinnerTuyen() {
        if (glstTuyen != null && glstTuyen.size() > 0 && glstTuyen.get(0) != null && glstTuyen.get(0).getName() != null) {
            if (!glstTuyen.get(0).getName().equals("Tất cả")) {
                glstTuyen.add(0, new Tuyen("-2", "Tất cả"));
            }
            adapterSpinnerTuyen = new SpinnerAdapterCus(activity,
                    R.layout.spinner_row, glstTuyen);
//            adapterSpinnerTuyen.add(new Tuyen("-2", "Tất cả"));
            spinnerTuyen.setAdapter(adapterSpinnerTuyen);
            spinnerTuyen.setOnItemSelectedListener(new OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                    currentTuyenId = glstTuyen.get(position).getId();
                    positionSpinnerTuyen = position + 1;
                    Config.assignId = currentTuyenId;
//                    Toast.makeText(getActivity(), "tuyen id=  " + currentTuyenId, Toast.LENGTH_SHORT).show();
                    String[] arrCurrentTuyen = glstTuyen.get(position).getName().split(",");
                    String dayName = "";
                    if(currentTuyenId.equals("-2") || currentTuyenId.equals("-1")){
                        int iDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                        switch(iDay){
                            case 1:
                                dayName = "Chủ Nhật";
                                break;
                            case 2:
                                dayName = "Thứ 2";
                                break;
                            case 3:
                                dayName = "Thứ 3";
                                break;
                            case 4:
                                dayName = "Thứ 4";
                                break;
                            case 5:
                                dayName = "Thứ 5";
                                break;
                            case 6:
                                dayName = "Thứ 6";
                                break;
                            case 7:
                                dayName = "Thứ 7";
                                break;
                        }
                    } else if(arrCurrentTuyen != null && arrCurrentTuyen.length > 0){
                        switch (arrCurrentTuyen[0].toLowerCase()) {
                            case "chu nhat":
                                dayName = "Chủ Nhật";
                                break;
                            case "thu 2":
                                dayName = "Thứ 2";
                                break;
                            case "thu 3":
                                dayName = "Thứ 3";
                                break;
                            case "thu 4":
                                dayName = "Thứ 4";
                                break;
                            case "thu 5":
                                dayName = "Thứ 5";
                                break;
                            case "thu 6":
                                dayName = "Thứ 6";
                                break;
                            case "thu 7":
                                dayName = "Thứ 7";
                                break;
                        }
                    }
                    EventThongTinTuyen eventThongTinTuyen = new EventThongTinTuyen(dayName, currentTuyenId);
                    EventBus.getDefault().postSticky(eventThongTinTuyen);
//                    Toast.makeText(getActivity(), "tuyến: " + dayName, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
            spinnerTuyen.setSelection(positionSpinnerTuyen);

        }
    }

    private void getTotalKhachhangByTuyen(String tuyenId) {
        try {
            if (NetworkType.internetIsAvailable(activity)) {
                NorAndNop n = control.getNorNop(getURL_NopNor(tuyenId));
                totalKhachhang = Integer.parseInt(n.getNor());
            }
        } catch (Exception e) {
            Log.d("cuong error", e.toString());
            totalKhachhang = 0;
            mAF.writelog("Error TuyenFrag getTotalKhachhangByTuyen: ",
                    activity, mAF.filelog);
        }
    }

    @SuppressWarnings("unchecked")
    private void getDataKhachhang(String tuyenID) {
        try {
            // Get data KH
            MA_KH = txtSearchKH.getText().toString();
            String khachhangId = URLEncoder.encode(MA_KH, "UTF-8");
            glstKhachhang = new ArrayList<KhachHang>();
            tempArr = new ArrayList<KhachHang>();
            if (NetworkType.internetIsAvailable(activity)) {
                if (!tuyenID.equals(null)) {
                    // sửa theo nghiệp vụ mới khi chọn tất cả(-2) hoặc ngoại tuyến (-1) thì chỉ load 100 bản ghi
                    if (tuyenID.equals("-1") || tuyenID.equals("-2")) {
                        totalKhachhang = 100;// tpq
                    }
                }

                Log.d("tuyenid", tuyenID);
                mAF.Route_assign_id = tuyenID;
                String link = "";
                //load 50 khach hang
                if(ckSearchAdvence.isChecked()){
                    link = getURL_DSKH_Tuyen(tuyenID,
                            currentTrangthaiId, khachhangId, totalKhachhang, mAF.latCurrent + "", mAF.longCurrent + "",
                            idVung, idKhuVuc,  idTinh, idQuan);
                }else {
                    link = getURL_DSKH_Tuyen(tuyenID,
                            currentTrangthaiId, khachhangId, totalKhachhang, mAF.latCurrent + "", mAF.longCurrent + "",
                            "", "", "", "");
                }

                //nếu id tuyến là -2 nhập tọa độ là 0
//                if (tuyenID == "-2") {
//                    link = getURL_DSKH_Tuyen(tuyenID, currentTrangthaiId, khachhangId, totalKhachhang, "0.0", "0.0");
                String json_dskh = control.jsonValues(link, false);
                Log.i(this.getClass().getSimpleName(), "Danh sach khach hang: " + json_dskh);
                // Khong lay duoc du lieu thi khong khoi tao glstKhachhang, neu
                // khong chuong trinh se bi out
                if (json_dskh.contains("distance")) {
                    try {
                        Type type_DSKH = new TypeToken<ArrayList<KhachHang>>() {
                        }.getType();
                        glstKhachhang = (ArrayList<KhachHang>) new Gson()
                                .fromJson(json_dskh, type_DSKH);
                    } catch (Exception e) {
                        Log.d(TAG, "glstKhachhang.size = 0");
                    }
                } else {
                    flgErr = true;
                    mAF.writelog("Error TuyenFragment getDataKhachhang: "
                            + json_dskh, activity, mAF.filelog);
                }

            } else {
                glstKhachhang = new ArrayList<KhachHang>();

                // Lay danh sach khach hang moi offline them vao danh sach ngoai
                // tuyen
                if (tuyenID.equals("-1")) {
                    // Neu trang thai la Da dat va ghe tham -> ko lay du lieu
                    if (!currentTrangthaiId.equals("2")
                            && !currentTrangthaiId.equals("3")) {
                        ArrayList<ThemKH> glstKhachHangNewOffline = new ThongTinKhachhangDAL(
                                activity).getByType(0);
                        if (glstKhachHangNewOffline.size() > 0) {
                            for (int i = 0; i < glstKhachHangNewOffline.size(); i++) {

                                KhachHang item = new KhachHang();
                                item.setDistance(String
                                        .valueOf(glstKhachHangNewOffline.get(i)
                                                .get_id())); // gan _id cho
                                // distance
                                item.setId(glstKhachHangNewOffline.get(i)
                                        .getId());
                                item.setCode(glstKhachHangNewOffline.get(i)
                                        .getNew_code());
                                item.setName(URLDecoder.decode(
                                        glstKhachHangNewOffline.get(i)
                                                .getName(), "UTF-8"));
                                item.setAddress(URLDecoder.decode(
                                        glstKhachHangNewOffline.get(i)
                                                .getAddr(), "UTF-8"));
                                item.setLattitude(String
                                        .valueOf(glstKhachHangNewOffline.get(i)
                                                .getLattitude()));
                                item.setLongtitude(String
                                        .valueOf(glstKhachHangNewOffline.get(i)
                                                .getLongtitude()));
                                item.setStatus("");
                                item.setImage_url("");

                                glstKhachhang.add(item);
                            }
                        }
                    }
                }

                // Lay danh sach KH trong DB
                ArrayList<KhachHang> glstKhachHangDB = new TuyenKhachHangDAL(
                        activity).getByAssignId(tuyenID,
                        currentTrangthaiId, khachhangId);
                if (glstKhachHangDB.size() > 0) {
                    glstKhachhang.addAll(glstKhachHangDB);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayDataKhachhang() {
        try {
            if (glstTuyen != null && glstTuyen.size() > 0)
                adapterListKhachHang = new DSKHListAdapter(activity,
                        glstKhachhang, new OnCustomClickListener() {

                    @Override
                    public void OnCustomClick(View aView, int position) {

                        switch (aView.getId()) {
                            case R.id.dskh_item:
                                mAF.rowClick = position;
                                positionListViewSelected = position;
                                int[] location = new int[2];
                                listViewKhachHang.getLocationOnScreen(location);
                                KhachHang kh;
                                if (glstKhachhang.size() >= position) {
                                    kh = glstKhachhang.get(position);
                                    Config.orgId = kh.getId();
                                    Config.orgCode = kh.getCode();
                                    Config.assignId = kh.getAssign_id();

                                    MA_KH = kh.getCode().trim();
                                    txtSearchKH.setText(MA_KH);
                                    mAF.sTmp = kh.getName();  //Luu ten KH va set vao form dat hang
                                }
                                p = new Point();
                                p.x = location[0];
                                p.y = location[1];
                                if (p != null) {
                                    showPopup(activity, p);
                                }

                                adapterListKhachHang.notifyDataSetChanged();
                                if (glstKhachhang != null) {
                                    tvAlert.setVisibility(glstKhachhang.size() == 0 ? View.VISIBLE : View.GONE);
                                }

                                break;
                            case R.id.kh_chitiet:
                                Fragment fragment = new TTKHFragment(
                                        glstKhachhang.get(position));
                                if (fragment != null) {
                                    ((MainActivity) activity)
                                            .replaceFragment(fragment);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                });
            Log.d("CONCRETE", "displayDataKhachhang | Set Adapter | Size of list customer = " + glstKhachhang.size());
//            listViewKhachHang.setAdapter(adapterListKhachHang);
//            getListView().setAdapter(adapterListKhachHang);
            setListAdapter(adapterListKhachHang);

            tvAlert.setVisibility(glstKhachhang.size() == 0 ? View.VISIBLE : View.GONE);

            // listViewKhachHang.smoothScrollToPosition(mAF.rowClick);
            // listViewKhachHang.getChildAt(mAF.rowClick);

            // gui danh sach tuyen sang MainActivity
            if (glstTuyen.size() > 0) {
                passData(glstTuyen.get(positionSpinnerTuyen - 1));
            }
            if (glstKhachhang.size() > 0) {
                passDSKH(glstKhachhang);
            }
        } catch (Exception e) {
            Log.d("displayDataKhachhang=", e.toString());
            mAF.writelog(
                    "Error TuyenFragment displayDataKhachhang: " + e.toString(),
                    activity, mAF.filelog);
            e.printStackTrace();
        }
    }

	/*
     * private OnScrollListener onScrollListener() { return new
	 * OnScrollListener() {
	 * 
	 * @Override public void onScrollStateChanged(AbsListView view, int
	 * scrollState) { int threshold = 1; int count = getListView().getCount();
	 * 
	 * if (scrollState == SCROLL_STATE_IDLE) { if
	 * (getListView().getLastVisiblePosition() >= count - threshold && count <
	 * totalKhachhang) { Log.i("loaddata", "loading more data"); new
	 * LoadMoreDataTask().execute(); } } }
	 * 
	 * @Override public void onScroll(AbsListView view, int firstVisibleItem,
	 * int visibleItemCount, int totalItemCount) {
	 * 
	 * pageCount = (totalItemCount/MAX_ITEM_PER_PAGE); boolean loadMore =
	 * firstVisibleItem + visibleItemCount == totalItemCount; if
	 * (NetworkType.internetIsAvailable(activity) && !isLoadingMore &&
	 * loadMore) { if (pageCount >= 1 && totalItemCount < totalKhachhang) {
	 * pageCount++; new LoadMoreDataTask().execute(); } } } }; }
	 */

    public boolean getCurDay(String input_day) {
        String day_name = "";
        boolean result = false;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        Log.i("cday", String.valueOf(day));

        switch (day) {
            case 1:
                day_name = "Chu Nhat";
                break;
            case 2:
                day_name = "Thu 2";
                break;
            case 3:
                day_name = "Thu 3";
                break;
            case 4:
                day_name = "Thu 4";
                break;
            case 5:
                day_name = "Thu 5";
                break;
            case 6:
                day_name = "Thu 6";
                break;
            case 7:
                day_name = "Thu 7";
                break;
            default:
                break;
        }

        if (input_day.toLowerCase().equals("chu nhat")
                || input_day.toLowerCase().equals("cn")) {
            if (day_name.equals("Chu Nhat")) {
                result = true;
            }
        } else {
            if (input_day.equalsIgnoreCase(day_name)) {
                result = true;
            }
        }

        return result;
    }

    private void showProgressDialog() {
        if (isActivityRunning) {
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(false);
            try {
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing() && isActivityRunning) {
            dismissWithCheck(pDialog);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG + "onConnected", "onConnected");
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            return;
        }
        currentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //10 seconds
        mLocationRequest.setFastestInterval(10000); //10 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG + "onConnectionSuspended", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG + "onConnectionFailed", "onConnectionFailed");
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
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
            if(danhSachVung != null && danhSachVung.size() > 0){
                adapterVung = new KhuVucAdapter(getActivity(), R.layout.item_nhan_hieu, danhSachVung);
                txtVung.setAdapter(adapterVung);
                adapterVung.notifyDataSetChanged();
            }
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

	/*
     * @Override public void onSaveInstanceState(Bundle outState) {
	 * super.onSaveInstanceState(outState);
	 * outState.putBundle(Config.KEY_BUNDLE_TUYEN, (savedState != null) ?
	 * savedState : saveState()); }
	 */

    // /////////////////////// AYSNTASK //////////////////////////////
    private class GetDataTuyenTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getDataTrangThai();
            getDataTuyen();
            // getTotalKhachhangByTuyen();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            fillDataToSpinnerTrangthai();
            fillDataToSpinnerTuyen();
            txtSearchKH.setText("");
            dismissWithCheck(pDialog);
            if (getDSKHTask == null || getDSKHTask.getStatus() == Status.FINISHED) {
                try{
                    getDSKHTask = new GetDSKHTask();
                    getDSKHTask.execute();
                }catch (Exception ex){

                }

            }

        }
    }

    private class GetDSKHTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getTotalKhachhangByTuyen(currentTuyenId);
            getDataKhachhang(currentTuyenId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            displayDataKhachhang();
            dismissWithCheck(pDialog);
            if (flgErr) {
                control.showAlertDialog(
                        activity,
                        "Error",
                        "Không có dữ liệu hoặc lấy dữ liệu không thành công. Vui lòng thử lại!",
                        false);
            }

        }
    }

    private class CheckinTask extends AsyncTask<Checkin, Void, String> {

        private int checktype = 0;

        public CheckinTask(int type) {
            this.checktype = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(Checkin... params) {
            String str_KH = new Gson().toJson(params[0]);
            String s = null;
            try {
                String url = control.convertURL(getURL_Checkin(str_KH));
                s = control.jsonValues(url, true);
                Log.d("cuong kq checkin", s);
                s = new JSONObject(s).getString("result");
            } catch (JSONException e1) {
                Log.d("cuong kq checkin error", s + e1.toString());
                mAF.writelog("Error TuyenFragment CheckinTask doInBackground: "
                        + e1.toString(), activity, mAF.filelog);
                e1.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
            if (result.equals("0")) {
                String time = "";
                try {
                    time = Utilities.getDate(System.currentTimeMillis(),
                            "dd/MM HH:mm");
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (checktype == 0) {
                    glstKhachhang.get(positionListViewSelected).setStatus("HT");
                    glstKhachhang.get(positionListViewSelected)
                            .setCheckin_time(time);
                    control.showAlertDialog(activity, "Thông báo",
                            "Checkin vị trí thành công", true);
                } else {
                    glstKhachhang.get(positionListViewSelected)
                            .setCheckout_time(time);
                    control.showAlertDialog(activity, "Thông báo",
                            "Checkout vị trí thành công", true);
                }
                Log.d("CONCRETE", "onPostExecute| Size of list customer = " + glstKhachhang.size());
                adapterListKhachHang.notifyDataSetChanged();
                tvAlert.setVisibility(glstKhachhang.size() == 0 ? View.VISIBLE : View.GONE);

            } else if(result.equals("-1")){
                if (checktype == 0) {   // trường hợp check in lỗi
                    control.showAlertDialog(activity,
                            "Thông báo",
                            "Khoảng cách quá xa, không thể Checkin. Khoảng cách tối đa cho phép là: "
                                    + mAF.maxDistance + " m", false);
                } else { // trường hợp check out lỗi
                    control.showAlertDialog(activity,
                            "Thông báo",
                            "Khoảng cách quá xa, không thể Checkout. Khoảng cách tối đa cho phép là: "
                                    + mAF.maxDistance + " m", false);
                }
            } else {
                control.showAlertDialog(activity, "Thông báo", result,
                        false);
            }

        }
    }

    private int positionListViewSelected;

    // The method that displays the popup.
    @SuppressWarnings("deprecation")
    public void showPopup(Activity context, Point p) {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context
                .findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater
                .inflate(R.layout.popup_chucnang, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);

        // load animation
        popup.setAnimationStyle(R.style.popup_animation);
        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.BOTTOM, 0, 0);

        // initUI
        LinearLayout btn_cn_checkin, btn_cn_khaosat, btn_cn_binhluan, btn_cn_hinhanh, btn_cn_lichsu, btn_cn_dathang, btn_cn_checkout;

        btn_cn_checkin = (LinearLayout) layout
                .findViewById(R.id.btn_cn_checkin);
        btn_cn_khaosat = (LinearLayout) layout
                .findViewById(R.id.btn_cn_khaosat);
        btn_cn_binhluan = (LinearLayout) layout
                .findViewById(R.id.btn_cn_binhluan);

        btn_cn_hinhanh = (LinearLayout) layout
                .findViewById(R.id.btn_cn_hinhanh);
        btn_cn_lichsu = (LinearLayout) layout.findViewById(R.id.btn_cn_lichsu);
        btn_cn_dathang = (LinearLayout) layout
                .findViewById(R.id.btn_cn_dathang);

        btn_cn_checkout = (LinearLayout) layout
                .findViewById(R.id.btn_cn_checkout);

        OnClickListener ChucNangListener = new OnClickListener() {
            Fragment fragment;

            public void onClick(View view) {
                if (popup.isShowing())
                    popup.dismiss();
                switch (view.getId()) {
                    case R.id.btn_cn_checkin:
                        // cuongtm them
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
//                        if (isTatCa) {
//                            checkinout(0);
//                        } else {
                            if (mAF.maxDistance == 0) {
                                control.showAlertDialog(
                                        activity,
                                        "Thông báo",
                                        "Không có tham số hệ thống. Cần Thoát ra và Login lại",
                                        false);
                            } else {
                                if (mAF.isNumeric(glstKhachhang.get(
                                        positionListViewSelected).getDistance())) {
                                    checkinout(0);
/*                                    if (Long.parseLong(glstKhachhang.get(
                                            positionListViewSelected).getDistance()) > mAF.maxDistance) {
                                        control.showAlertDialog(activity,
                                                "Thông báo",
                                                "Khoảng cách quá xa, không thể Checkin. Khoảng cách tối đa cho phép là: "
                                                        + mAF.maxDistance + " m", false);
                                    } else {
                                        checkinout(0);
                                    }*/
                                } else {
                                    control.showAlertDialog(
                                            activity,
                                            "Thông báo",
                                            "Không thể checkin do không tính được khoảng cách đến Khách hàng",
                                            false);
                                }
                            }
                        }
                        break;
                    case R.id.btn_cn_khaosat:
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                            String ORG_ID = glstKhachhang.get(positionListViewSelected)
                                    .getId();
                            String ORG_CODE = glstKhachhang.get(
                                    positionListViewSelected).getCode();
                            String ASSIGN_ID = glstKhachhang.get(
                                    positionListViewSelected).getAssign_id();
                            if(Config.MaDonVi.equals(Config.maTMX)){
//                                EventBus.getDefault().removeStickyEvent(ThongTinKhachHangKhaoSatEvent.class);
                                MainActivity activity = (MainActivity) getActivity();
                                activity.showProgressBar();
                                ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent = new ThongTinKhachHangKhaoSatEvent("",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ORG_ID,
                                        ""
                                );
                                EventBus.getDefault().postSticky(khachHangKhaoSatEvent);
                                fragment = new ThongTinKhaoSatFragment(ORG_ID);
                            }else {
                                fragment = new KhaoSatFragment(ORG_ID, ORG_CODE, ASSIGN_ID);
                            }
                        }
                        break;
                    case R.id.btn_cn_binhluan:
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                            fragment = new BinhLuanFragment(
                                    glstKhachhang.get(positionListViewSelected));
                        }
                        break;
                    case R.id.btn_cn_hinhanh:
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                            fragment = new HinhAnhFragment(
                                    glstKhachhang.get(positionListViewSelected));
                        }
                        break;
                    case R.id.btn_cn_lichsu:
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                            // Cuong them
                            mAF.sGlobal = glstKhachhang.get(positionListViewSelected)
                                    .getId();
                            fragment = new LichSuFragment(
                                    glstKhachhang.get(positionListViewSelected),
                                    LICH_SU);
                        }
                        break;
                    case R.id.btn_cn_dathang:
                        if (Config.MaDonVi.equals(Config.maGlab)) {
                            int idNhaCungCap = Integer.parseInt(glstKhachhang.get(positionListViewSelected).getId());
                            String nameNhaCungCap = "";
                            try {
                                nameNhaCungCap = conv.getUTF8StringFromNCR(URLDecoder.decode(
                                        glstKhachhang.get(positionListViewSelected).getName(), "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            fragment = new GlabChiTietDonHangFragment(idNhaCungCap, nameNhaCungCap);
                        }else {
                            if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                                requestDatHang();
    //                            Config.isNewCustomer = false;
    //                            Config.isCreateNewDHFromQLDH = false;
    //                            // Cuong them
    //                            mAF.sGlobal = glstKhachhang.get(positionListViewSelected)
    //                                    .getId();
    //                            KhachHang kh = glstKhachhang.get(positionListViewSelected);
    //                            kh.setId(mAF.sGlobal);
    //                            Toast.makeText(getActivity(), "ID= " + mAF.sGlobal, Toast.LENGTH_SHORT).show();
    //                            fragment = new ThemDHListFragment(kh);
                            }
                        }
                        break;
                    case R.id.btn_cn_checkout:
                        if (glstKhachhang != null && glstKhachhang.size() > positionListViewSelected) {
                            checkinout(2);
                        }
                        break;
                    default:
                        break;
                }

                if (fragment != null) {
                    ((MainActivity) activity).replaceFragment(fragment);
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        };

        btn_cn_checkin.setOnClickListener(ChucNangListener);
        btn_cn_khaosat.setOnClickListener(ChucNangListener);
        btn_cn_binhluan.setOnClickListener(ChucNangListener);
        btn_cn_hinhanh.setOnClickListener(ChucNangListener);
        btn_cn_lichsu.setOnClickListener(ChucNangListener);
        btn_cn_dathang.setOnClickListener(ChucNangListener);
        btn_cn_checkout.setOnClickListener(ChucNangListener);
    }

    private void checkinout(int type) {
        if(type == 0){
            bCheckIn = true;
        }else if(type == 2){
            bCheckIn = false;
        }
        try {
            // Kiem tra toa do vi tri cua KH truoc khi checkin
            KhachHang kh = glstKhachhang.get(positionListViewSelected);
            if (kh != null && !kh.getLattitude().equals("0")
                    && !kh.getLongtitude().equals("0")) {

                GPSTracker gps = new GPSTracker(activity);
                if (gps.canGetLocation()) {
                    double latitude = currentLocation.getLatitude();
                    double longitude = currentLocation.getLongitude();
                    double accuracy = currentLocation.getAccuracy();
                    int hasAccuracy = 0;
                    if(currentLocation.hasAccuracy()){
                        hasAccuracy = 1;
                    }else {
                        hasAccuracy = 0;
                    }

                    if ((int) latitude == 0 || (int) longitude == 0) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }
                    Log.e("checkin location", latitude + " - " + longitude);



                    String checkType = "0";
                    boolean flag = false;
                    if (type == 2) {
                        checkType = "2";

                        // code cũ, lỗi check out do bắt trường hợp này, ko rõ nguyên nhân tại sao bắt, BA ko giải thích được
                        if (kh.getCheckin_time().equals("")) {
                            flag = true;
                            control.showAlertDialog(activity, "Thông báo",
                                    "Không thể checkout khi chưa checkin!",
                                    false);
                        }

//                        if (!kh.getCheckin_time().equals("")
//                                && !kh.getCheckout_time().equals("")) {
//                            flag = true;
//                            control.showAlertDialog(activity, "Thông báo",
//                                    "Bạn đã checkout với khách hàng này rồi!",
//                                    false);
//                        }
                        // end

                    }

                    if (!flag) {
                        String time = Utilities.getDate(
                                System.currentTimeMillis(), "ddMMyyyy HHmmss");
                        String pin = gps.getBatteryLevel();
                        String networkType = gps.getNetworkType();
                        String signalStrength = gps.getSignalStrength();
                        Checkin ckin = new Checkin(glstKhachhang.get(
                                positionListViewSelected).getId(),
                                glstKhachhang.get(positionListViewSelected)
                                        .getCode(), glstKhachhang.get(
                                positionListViewSelected)
                                .getAssign_id(),
                                String.valueOf(longitude),

                                String.valueOf(latitude), checkType, time, pin,
                                networkType, signalStrength, accuracy, hasAccuracy);

                        String str_KH = new Gson().toJson(ckin);
                        Log.i("INFO-CHECKIN-OUT", str_KH);
                        if (NetworkType.internetIsAvailable(activity)) {
                            if (checkinTask == null || checkinTask.getStatus() == AsyncTask.Status.FINISHED) {
                                checkinTask = new CheckinTask(Integer.parseInt(checkType));
                                checkinTask.execute(ckin);

                            }
                        } else {
                            SaveInfoCheckinOffline(ckin);
                        }
                    }
                } else {
                    gps.showSettingsAlert();
                }
            } else {
                if (type == 0) {
                    control.showAlertDialog(
                            activity,
                            "Thông báo",
                            "Không thể checkin khi không có tọa độ Khách hàng!",
                            false);
                } else {
                    control.showAlertDialog(
                            activity,
                            "Thông báo",
                            "Không thể checkout khi không có tọa độ Khách hàng!",
                            false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveInfoCheckinOffline(Checkin data) {
        try {
            if (data != null) {
                long result = new CheckinDAL(activity).add(data);
                if (result > 0) {
                    String time = "";
                    try {
                        time = Utilities.getDate(System.currentTimeMillis(),
                                "dd/MM HH:mm");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    KhachHang kh = glstKhachhang.get(positionListViewSelected);
                    if (data.getChecktype().equals("0")) {
                        kh.setStatus("HT");
                        kh.setCheckin_time(time);
                        glstKhachhang.get(positionListViewSelected).setStatus(
                                "HT");
                        glstKhachhang.get(positionListViewSelected)
                                .setCheckin_time(time);
                        control.showAlertDialog(activity, "Thông báo",
                                "Checkin vị trí thành công", true);
                    } else {
                        kh.setCheckout_time(time);
                        glstKhachhang.get(positionListViewSelected)
                                .setCheckout_time(time);
                        control.showAlertDialog(activity, "Thông báo",
                                "Checkout vị trí thành công", true);
                    }
                    Log.d("CONCRETE", "SaveInfoCheckinOffline| Size of list customer = " + glstKhachhang.size());

                    adapterListKhachHang.notifyDataSetChanged();
                    tvAlert.setVisibility(glstKhachhang.size() == 0 ? View.VISIBLE : View.GONE);
                    long updateResult = new TuyenKhachHangDAL(activity)
                            .update(kh);
                    Log.i("UPDATE-INFO-CHECKIN-OUT",
                            updateResult > 0 ? "Success" : "Failure");
                } else {
                    control.showAlertDialog(activity, "Cảnh báo",
                            "Lưu thông tin thất bại!", false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ghetham:
                GheThamDialog gheThamDialog = new GheThamDialog(activity,
                        getResources().getString(R.string.d_gps_title), "");
                gheThamDialog.show();

                return true;
        }
        return false;
    }

    public class GheThamDialog extends BaseDialog {

        public GheThamDialog(Context context, String title, String message) {
            super(context);

            setContentView(R.layout.custom_dialog_ghetham);
            final EditText txtBankinh = (EditText) findViewById(R.id.d_tongKH);
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            // Spinner spin_gps = (Spinner) findViewById(R.id.spin_gps);
            TextView btn_close = (TextView) findViewById(R.id.btnSearchGPS);
            tvTitle.setText(title);

            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (NetworkType.internetIsAvailable(activity)) {
                        String bk = txtBankinh.getText().toString();
                        GheThamDialog.this.dismiss();
                        if (getDSKHGpsTask == null || getDSKHGpsTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSKHGpsTask = new GetDSKHGpsTask();
                            getDSKHGpsTask.execute(bk);
                        }
                    } else {
                        control.showAlertDialog(activity, "Thông báo",
                                "Chức năng chỉ hoạt động khi có internet",
                                false);
                    }
                }
            });

        }
    }

    private class GetDSKHGpsTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                getTotalKhachhangByTuyen(currentTuyenId);
                getDataKhachhangGps(params[0]);
            }catch (Exception ex){
                Log.e("LỖI TÌM TUYẾN", "lỗi: " + ex);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            displayDataKhachhang();
            dismissWithCheck(pDialog);
        }
    }

    @SuppressWarnings("unchecked")
    private void getDataKhachhangGps(String radius) {
        try {
            // Get data KH
            glstKhachhang = new ArrayList<KhachHang>();
            if (NetworkType.internetIsAvailable(activity)) {
                if (currentTuyenId.equals("-1")) {
                    totalKhachhang = 100;
                }

                GPSTracker gps = new GPSTracker(activity);
                if (gps.canGetLocation()) {
                    double latitude = currentLocation.getLatitude();
                    double longitude = currentLocation.getLongitude();
                    if ((int) latitude == 0 || (int) longitude == 0) {
                        latitude = gps.getLatitude();
                        longitude = gps.getLongitude();
                    }

                    String link = getURL_DSKH_GPS(radius, latitude, longitude,
                            currentTuyenId, 1, totalKhachhang);

                    String json_dskh_gps = "";

                    // json_dskh_gps =
                    // control.jsonValues(control.convertURL(link), false);

                    // cuongtm thêm vào, sử dụng 1 hàm search khoảng cách cho
                    // đồng nhất khi tìm
                    json_dskh_gps = control.jsonValues(link, false);

                    Log.i("cuong dskh theo gps", json_dskh_gps);

                    Type type_DSKH = new TypeToken<ArrayList<KhachHang>>() {
                    }.getType();
                    try {
                        glstKhachhang = (ArrayList<KhachHang>) new Gson()
                                .fromJson(json_dskh_gps, type_DSKH);
                    } catch (Exception e) {
                    }
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getURL_Info() {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh";
        return url;
    }

    public String getURL_Default(int pageCount) {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh&pageno="
                + String.valueOf(pageCount) + "&pagerec=10";
        Log.i("url-return", url);
        return url;
    }

    public String getURL_Json1(int page, String jsonText) {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh&pageno="
                + String.valueOf(page) + "&pagerec=10" + jsonText;
        Log.i("url-json1", url);
        return url;
    }

    public String getURL_Json2(int page, String tinh_trang, String tuyen) {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh&pageno="
                + String.valueOf(page) + "&pagerec=10&json={" + tinh_trang
                + "," + tuyen + "}";
        Log.i("url-return", url);
        return url;
    }

    // lay danh sach tuyen, bao gom ca ID cua DSKH trong tuyen do.
    public String getURL_DSTuyen_active() {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=tuyen_dstuyen&pageno=0&json={%22act%22:%221%22}";
        Log.i("url-getdst", url);
        return url;
    }

    // lay danh sach tuyen, bao gom ca ID cua DSKH trong tuyen do.
    public String getURL_DSKH_Tuyen(String ID, String done, String khachhangId,
                                    int itemPerpage, String latitude, String longitude,
                                    String idVung, String idKhuVuc, String idTinh, String idQuan) {

        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=tuyen_dskh&pageno="
                + pageCount + "&pagerec=" + itemPerpage
                + "&json={%22trxid%22:%22" + ID + "%22,%22done%22:%22" + done
                + "%22,%22makh%22:%22" + khachhangId + "%22"

                + ",%22lat%22:%22" + latitude + "%22"

                + ",%22long%22:%22" + longitude + "%22"

                + ",%22bid%22:%22" + idVung + "%22"
                + ",%22aid%22:%22" + idKhuVuc + "%22"
                + ",%22pid%22:%22" + idTinh + "%22"
                + ",%22did%22:%22" + idQuan + "%22"

                + "}";
        //mAF.latCurrent
        Log.i("dskh-tuyen", url);
        return url;
    }

    private String getURL_NopNor(String tuyenId) {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=tuyen_dskh&json={%22trxid%22:%22"
                + tuyenId + "%22}";
        Log.i("url-bl-nop", url);
        return url;
    }

    private String getURL_Checkin(String ckin) {
        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_checkin&json="
                + ckin.toString();
        Log.i("url-checkin", url);
        return url;
    }

    private String getURL_DSKH_GPS(String radius, Double lat, Double lng,
                                   String assignId, int type, int itemPerpage) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("radius", radius);
            jsonObject.put("lat", lat);
            jsonObject.put("lon", lng);
            jsonObject.put("assign", type); // type = 1 -> chỉ lấy các KH trong
            // tuyến đang xem xét
            jsonObject.put("aid", assignId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

		/*
         * String url = API_code.URL_API_ROOT+
		 * "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh" + "&pageno="+
		 * pageCount + "&pagerec=" + itemPerpage + "&json="+
		 * jsonObject.toString();
		 */

        String url = API_code.URL_API_ROOT
                + "mobiapp_api_v1.jsp?callback=?&api_code=tuyen_dskh&pageno="
                + pageCount + "&pagerec=" + itemPerpage
                + "&json={%22trxid%22:%22" + currentTuyenId
                + "%22,%22done%22:%22" + "" + "%22,%22makh%22:%22" + "" + "%22"

                + ",%22lat%22:%22" + mAF.latCurrent + "%22"

                + ",%22long%22:%22" + mAF.longCurrent + "%22"

                + ",%22radius%22:%22" + radius + "%22"

                + "}";
        Log.i("url-dskh-gps", url);
        return url;
    }

    public class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String[] s) {
            String json = "";
            try {
                Controller c = new Controller(activity);

                String url1;
                String postParams = "";
                url1 = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp";
                postParams = "callback=?&api_code=ghetham_tt_nvkd&username="
                        + s[0] + "&password=" + s[1];
                json = mAF.sendPost(url1, postParams, activity);

            } catch (Exception e) {
                json = "Error LoginTask service" + e.toString();
            }
            mAF.writelog("LoginTask service: " + json, activity,
                    mAF.filelog);
            return json;
        }

        @Override
        protected void onPostExecute(String message) {
            // process message
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fragment 1", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        isActivityRunning = true;
    }

    //	@Override
//	public void onResume() {
//		super.onResume();
////		Log.i(this.getClass().getSimpleName(), "onResume | isFirsttimeCreateFragment = " + isFirsttimeCreateFragment);
//
////		SharedPreferences sharedPreferences = PreferenceManager
////				.getDefaultSharedPreferences(activity.getBaseContext());
////		boolean isUserHasJustLogined = sharedPreferences.getBoolean(Config.USER_HAS_JUST_LOGIN, false);
//
//		if (isUserHasJustLogined){
////			SharedPreferences.Editor editor = sharedPreferences.edit();
////			editor.putBoolean(Config.USER_HAS_JUST_LOGIN, false);
////			editor.commit();
//
//			Fragment currentVisibleFragment = getFragmentManager()
//					.findFragmentById(R.id.frame_container);
//			if (currentVisibleFragment != null && currentVisibleFragment.isVisible() && currentVisibleFragment instanceof TuyenFragment) {
//
//				startLoadData();
//
////				try {
////				SQLiteData sqlite = new SQLiteData(activity);
////				String[] s = sqlite.getAccount();
////				if (sqlite.isOpen()) {
////					sqlite.close();
////				}
////				String resultAcc = new LoginTask().execute(new String[]{s[0], s[1]}).get();
////
////				} catch (Exception e) {
////					mAF.writelog("Error doLogin service: " + e.toString(), activity, mAF.filelog);
////				}
//			}
//		}
////		else {
////			isFirsttimeCreateFragment = false;
////		}
//	}

    private void startLoadData() {
        Log.d(this.getClass().getSimpleName(), "startLoadData");
        if (isRestoreData && !Config.isOpenNew) {
            getDataTrangThai();
            fillDataToSpinnerTrangthai();
            fillDataToSpinnerTuyen();
            // displayDataKhachhang();
            if (getDSKHTask == null || getDSKHTask.getStatus() == AsyncTask.Status.FINISHED) {
                getDSKHTask = new GetDSKHTask();
                getDSKHTask.execute();
            }
        } else {
            MA_KH = "";
            txtSearchKH.clearComposingText();
            txtSearchKH.setText(MA_KH);
            if (getDataTuyenTask == null || getDataTuyenTask.getStatus() == AsyncTask.Status.FINISHED) {
                getDataTuyenTask = new GetDataTuyenTask();
                getDataTuyenTask.execute();
            }

        }

        // Cuong them ngày 09/05/2016
        if (mAF.isPhone) {
            mAF.setEditTextSize(this.txtSearchKH, 0, 90);
            mAF.setButtonSize(this.btnSearch, 0, 50);
            // mAF.setSpinnerSize(this.spinnerTuyen,
            // mAF.pxToDp(this.btnSearch.getHeight()) , 50);
            mAF.setSpinnerSize(this.spinnerTuyen, 0, 40);
            mAF.setSpinnerSize(this.spinnerTrangthai, 0, 60);
        } else {
            mAF.setSpinnerSize(this.spinnerTrangthai, 0, 100);
            mAF.setSpinnerSize(this.spinnerTuyen, 0, 60);
        }
        mAF.isDistance = 0; // Mỗi lần mở lại thì reset về 0 để tìm 1 KH đầu
        // tiên
    }

    @Override
    public void onPause() {
        super.onPause();

        isActivityRunning = false;

        Log.i("Fragment 1", "onPause");

        // Save data before stop
        Gson gson = new Gson();
        String jsonArrayListTuyen = gson.toJson(glstTuyen);

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Config.KEY_POSITION_TUYEN);
        editor.remove(Config.KEY_POSITION_TRANG_THAI);
        editor.remove(Config.KEY_KHACH_HANG_CODE);
        editor.remove(Config.KEY_DATA_TUYEN);

        editor.putInt(Config.KEY_POSITION_TUYEN, positionSpinnerTuyen);
        editor.putInt(Config.KEY_POSITION_TRANG_THAI, positionSpinnerTrangthai);
        editor.putString(Config.KEY_KHACH_HANG_CODE, MA_KH);
        editor.putString(Config.KEY_DATA_TUYEN, jsonArrayListTuyen);
        editor.commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fragment 1", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Fragment 1", "onDestroyView");
        // savedState = saveState();
    }

	/*
     * private Bundle saveState() { Bundle outState = new Bundle();
	 * outState.putString(Config.KEY_KHACH_HANG_CODE, MA_KH);
	 * outState.putInt(Config.KEY_POSITION_TUYEN, positionSpinnerTuyen);
	 * outState.putInt(Config.KEY_POSITION_TRANG_THAI,
	 * positionSpinnerTrangthai); //outState.putInt(Config.KEY_TOTAL_KHACH_HANG,
	 * totalKhachhang);
	 * 
	 * outState.putParcelableArrayList(Config.KEY_DATA_TUYEN, glstTuyen);
	 * outState.putParcelableArrayList(Config.KEY_DATA_TRANG_THAI,
	 * glstTrangthai);
	 * //outState.putParcelableArrayList(Config.KEY_DATA_KHACH_HANG,
	 * glstKhachhang);
	 * 
	 * return outState; }
	 */

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Fragment 1", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment 1", "onDetach");
    }

    // passing data from Fragment to main_Activity`
    public interface OnDataPass {
        public void onDataPass(Tuyen t);

        public void transferDSKH(List<KhachHang> list);

    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        dataPasser = (OnDataPass) activity;
    }

    public void passData(Tuyen t) {
        dataPasser.onDataPass(t);
    }

    public void passDSKH(List<KhachHang> list) {
        dataPasser.transferDSKH(list);
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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


    /*
        Hàm check được phép đặt hàng hay không, gọi api checkin_order
        return true - cho phép đặt hàng
        return false - Không cho phép đặt hàng hiển thị thông báo
    */

    private void requestDatHang(){
        if (NetworkType.internetIsAvailable(getActivity())){
            if (kiemTraDatHang == null || kiemTraDatHang.getStatus() == AsyncTask.Status.FINISHED) {
                kiemTraDatHang = new KiemTraDatHang(getActivity());
                if(kiemTraDatHang != null) {
                    kiemTraDatHang.execute(glstKhachhang.get(positionListViewSelected).getId());
                }
            }
        }
//     Toast.makeText(getActivity(), "ID= " + mAF.sGlobal, Toast.LENGTH_SHORT).show();

    }


    private class KiemTraDatHang extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;
        Context context;
        public KiemTraDatHang(Context mContext) {
            this.context = mContext;
        }

        @Override
        protected void onPreExecute() {
            try {
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Đang tải dữ liệu. Vui lòng chờ trong giây lát...");
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String sValue = "";
            try {
                String url = API_code.URL_KIEM_TRA_DAT_HANG + "&org_id=" + params[0];
                sValue = control.jsonValues(url, true);
            } catch (Exception ex) {
                Log.e("send Error: ", ex.toString());
            }
            return sValue;
        }


        @Override
        protected void onPostExecute(String result) {
            try{
                dismissWithCheck(pDialog);
                Gson gson = new Gson();
                KiemTraDatHangResponse kiemTraDatHangResponse = gson.fromJson(result, KiemTraDatHangResponse.class);

                if(kiemTraDatHangResponse.getName().toLowerCase().equals("false")){
                    control.showAlertDialog(activity,
                            "Thông báo",
                            "Cần Check in trước khi đặt hàng!", false);
                }else {
                    Fragment fragment;
                    Config.isNewCustomer = false;
                    Config.isCreateNewDHFromQLDH = false;
                    mAF.sGlobal = glstKhachhang.get(positionListViewSelected)
                            .getId();
                    KhachHang khachHang = glstKhachhang.get(positionListViewSelected);
                    khachHang.setId(mAF.sGlobal);
                    fragment = new ThemDHListFragment(khachHang);
                    ((MainActivity) activity).replaceFragment(fragment);
                }
            }catch (Exception ex){

            }

        }
    }
}