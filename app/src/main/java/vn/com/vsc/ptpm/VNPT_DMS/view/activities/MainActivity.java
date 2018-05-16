package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.Utils.NetworkStateChanged;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.ExpandableListAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.LocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.LocationUpdate.SendDataLocationService;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.WakeLocker;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.dao.service.CheckIntentService;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventThongTinTuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KHParcel;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.NavDrawerItem;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThongTinDialog;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ThongTinTuyenResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BanDoFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BaoCaoKPIFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.BinhLuanFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ChiTietKhuyenMaiFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.DSKHChuaDHFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabDanhSachDonHangFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachKhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.DatHangFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.HangHoaFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.HethongFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.HinhAnhFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.KhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.KhuyenMaiFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.LichSuFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.SuaDHFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.TTKHFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThemDHListFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThemKHFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongKeChungFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.TuyenFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.TuyenFragment.OnDataPass;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachTuyenFragment;

public class MainActivity extends Activity implements OnDataPass {
    private LayThongTinTuyen layThongTinTuyen;
    private final String TAG = "MainActivity";
    private final int DAT_HANG = 3;
    private KProgressHUD hud;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    public String titleDialogThongTin = "";
    TuyenFragment Tuyen_frag;
    private ProgressDialog pDialog;
    //
    AlertDialog dialog;

    // nav drawer title
    private CharSequence mDrawerTitle;
    private Tuyen tuyen;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    ExpandableListView expListView;
    HashMap<NavDrawerItem, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    FragmentManager fManager;
    FragmentTransaction fTrans;
    List<String> bctk;
    Controller control = new Controller(this);
    API_code api;
    HttpURLConnection conn = null;
    List<KhachHang> dskh;
    public static final int MO_ACTIVITY_MAP = 1;
    SharedPreferences.Editor editor;
    private int currentGroupPosition = 0;
    private int currentChildPosition = 0;

    protected LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean isActivityExist;

    //Dữ liệu lưu 1 lần trong ngày
    public HashMap<String, String> hmHinhThucThanhToan = new HashMap<>();
    public HashMap<String, String> hmDanhSachNhaCungCap = new HashMap<>();

    private AlertDialog.Builder builder;
    private static String BROADCAST_NETWORK = "vn.com.vsc.ptpm.VNPT_DMS.Utils.NetworkStateReceiver";
    private static boolean isNetwork;

    @SuppressWarnings("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(MainActivity.this);

        // start service
        getApplication().getApplicationContext().startService(new Intent(getApplicationContext(), LocationService.class));
        getApplication().getApplicationContext().startService(new Intent(getApplicationContext(), SendDataLocationService.class));
        getApplication().getApplicationContext().startService(new Intent(getApplicationContext(), CheckIntentService.class));

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        checkGPS();

        Log.d(TAG, "IS_MY_SERVICE_RUNNING=" + isMyServiceRunning(LocationService.class));
        Log.d(TAG, "IS_MY_SERVICE_RUNNING=" + isMyServiceRunning(SendDataLocationService.class));


        String q = Config.username;
        editor = getSharedPreferences(Config.PREF_LOGIN_STATUS, MODE_PRIVATE).edit();
        editor.putInt(Config.PREF_KEY_LOGIN_STATUS, 1);
        // editor.putString(Config.LAST_USER_LOGIN, "");
        editor.commit();

        SharedPreferences prefs = getSharedPreferences(Config.PREF_LOGIN_STATUS, MODE_PRIVATE);
        int loginStatus = prefs.getInt(Config.PREF_KEY_LOGIN_STATUS, 0);
        Log.d(TAG, "loginStatus=" + loginStatus);

        mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expListView = (ExpandableListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        if(Config.MaDonVi.equals(Config.maTMX)){
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
        }else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
        }


//         navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));

        bctk = new ArrayList<String>();
        bctk.add("Thống kê chung");
        bctk.add("Báo cáo KPI");
        bctk.add("DS KH chưa đặt hàng");

        listDataChild = new HashMap<NavDrawerItem, List<String>>();


        if(Config.MaDonVi.equals(Config.maTMX)){
            listDataChild.put(navDrawerItems.get(0), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(1), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(2), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(3), bctk);
            listDataChild.put(navDrawerItems.get(4), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(5), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(6), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(7), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(8), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(9), new ArrayList<String>());
        }else {
            listDataChild.put(navDrawerItems.get(0), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(1), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(2), bctk);
            listDataChild.put(navDrawerItems.get(3), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(4), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(5), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(6), new ArrayList<String>());
            listDataChild.put(navDrawerItems.get(7), new ArrayList<String>());
        }


        // listDataChild.put(navDrawerItems.get(8), new ArrayList<String>());
        // listDataChild.put(navDrawerItems.get(9), new ArrayList<String>());
        navMenuIcons.recycle();

        listAdapter = new ExpandableListAdapter(getApplicationContext(), navDrawerItems, listDataChild);
        expListView.setAdapter(listAdapter);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int groupPosition, long arg3) {
                expListView.collapseGroup(2);
                Fragment fragment = getFragmentByGroupPosition(groupPosition);

                if (fragment != null) {
                    replaceFragment(fragment);
                    expListView.setItemChecked(groupPosition, true);
                    expListView.setSelection(groupPosition);
                    setTitle(navMenuTitles[groupPosition]);
                    mDrawerLayout.closeDrawer(expListView);
                } else {
                    Log.e("MainActivity", "Error in creating fragment");
                }
                return false;
            }
        });

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Fragment fragment = getChildFragmentByChildPosition(childPosition);

                if (fragment != null) {
                    replaceFragment(fragment);
                    expListView.setItemChecked(groupPosition, true);
                    expListView.setSelection(groupPosition);
                    setTitle(bctk.get(childPosition));
                    mDrawerLayout.closeDrawer(expListView);
                } else {
                    Log.e("MainActivity", "Error in creating fragment");

                }
                return false;
            }
        });

        check = 0;
        invalidateOptionsMenu();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // start khi load mainform
        if (savedInstanceState == null) {
            Config.isOpenNew = true;

            // Tam bo

            TuyenFragment fragment = new TuyenFragment();
            // KhuyenMaiFragment fragment = new KhuyenMaiFragment();

            replaceFragment(fragment);
            expListView.setItemChecked(0, true);
            expListView.setSelection(0);
            setTitle(navMenuTitles[0]);

            mDrawerLayout.closeDrawer(expListView);
        }

        getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getFragmentManager().findFragmentById(R.id.frame_container);
                if (f != null) {
                    Log.e("CONCRETE", "addOnBackStackChangedListener");
                    updateTitleAndDrawer(f);
                }
            }
        });

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDetailsLabel("Đang kết nối...")
                .setCancellable(true)
                .setAnimationSpeed(3)
                .setDimAmount(0.5f);
    }

    private Fragment getFragmentByGroupPosition(int groupPosition) {
        Fragment fragment = null;
        currentGroupPosition = groupPosition;
        if(Config.MaDonVi.equals(Config.maTMX)) {
            switch (groupPosition) {
                case 0:
                    // tuyến
                    Config.isOpenNew = true;
                    fragment = new TuyenFragment();
                    break;
                case 1:
                    fragment = new DanhSachTuyenFragment();
                    break;
                case 2:
                    fragment = new DatHangFragment(new KhachHang(), DAT_HANG);
                    break;
                case 3:
                    break;
                case 4:
                    fragment = new ThemKHFragment();
                    break;
                case 5:
                    fragment = new HangHoaFragment();
                    break;
                case 6:
                    try {
                        fragment = new KhuyenMaiFragment();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 7:
                    fragment = new HethongFragment();
                    break;
                case 8:
                    fragment = new DanhSachKhaoSatFragment();
                    break;
                case 9:
                    logout();
                    break;
                case 10:
                    fragment = new ChiTietKhuyenMaiFragment();
                    break;
                default:
                    break;
            }

        }else if(Config.MaDonVi.equals(Config.maGlab)){
            switch (groupPosition) {
                case 0:
                    Config.isOpenNew = true;
                    fragment = new TuyenFragment();
                    break;
                case 1:
                    fragment = new GlabDanhSachDonHangFragment();
                    break;
                case 2:
                    break;
                case 3:
                    fragment = new ThemKHFragment();
                    break;
                case 4:
                    fragment = new HangHoaFragment();
                    break;
                case 5:
                    try {
                        fragment = new KhuyenMaiFragment();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 6:
                    fragment = new HethongFragment();
                    break;
                case 7:
                    logout();
                    break;
                case 8:
                    fragment = new ChiTietKhuyenMaiFragment();
                    break;
                default:
                    break;
            }
        }else {
            switch (groupPosition) {
                case 0:
                    Config.isOpenNew = true;
                    fragment = new TuyenFragment();
                    break;
                case 1:
                    fragment = new DatHangFragment(new KhachHang(), DAT_HANG);
                    break;
                case 2:
                    break;
                case 3:
                    fragment = new ThemKHFragment();
                    break;
                case 4:
                    fragment = new HangHoaFragment();
                    break;
                case 5:
                    try {
                        fragment = new KhuyenMaiFragment();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                case 6:
                    fragment = new HethongFragment();
                    break;
                case 7:
                    logout();
                    break;
                case 8:
                    fragment = new ChiTietKhuyenMaiFragment();
                    break;

				/*
                 * case 8: fragment = new ListDataOfflineFragment(); break;
				 */
                /*
                 * case 9: fragment = new ListDataSyncFragment(); break;
				 */
                default:
                    break;
            }
        }

        return fragment;
    }

    private Fragment getChildFragmentByChildPosition(int childPosition) {
        Fragment fragment = null;
        currentChildPosition = childPosition;
        switch (childPosition) {
            case 0:
                mAF.act = "rpt_chung";
                fragment = new ThongKeChungFragment();
                expListView.collapseGroup(2);
                break;
            case 1:
                mAF.act = "rpt_kpi";
                fragment = new BaoCaoKPIFragment();
                // fragment = new DSKHChuaDHFragment();
                expListView.collapseGroup(2);
                break;
            case 2:
                mAF.act = "rpt_dskh_chuadathang";
                fragment = new DSKHChuaDHFragment();
                expListView.collapseGroup(2);
                break;
            default:
                break;
        }
        return fragment;
    }

    private void updateTitleAndDrawer(Fragment fragment) {
        String fragClassName = fragment.getClass().getName();
         if(Config.MaDonVi.equals(Config.maTMX)){
            if (fragClassName.equals(TuyenFragment.class.getName())) {
                setTitle(navMenuTitles[0]);
                check = 0;
                expListView.setItemChecked(0, true);
                expListView.setSelection(0);
                // set selected item position, etc
            } else if (fragClassName.equals(DatHangFragment.class.getName())) {
                setTitle(navMenuTitles[1]);
                check = 2;
                expListView.setItemChecked(2, true);
                expListView.setSelection(2);
                // set selected item position, etc
            } else if (fragClassName.equals(ThemKHFragment.class.getName())) {
                setTitle(navMenuTitles[3]);
                check = 4;
                expListView.setItemChecked(4, true);
                expListView.setSelection(4);
                // set selected item position, etc
            } else if (fragClassName.equals(HangHoaFragment.class.getName())) {
                setTitle(navMenuTitles[4]);
                check = 5;
                expListView.setItemChecked(5, true);
                expListView.setSelection(5);
                // set selected item position, etc
            } else if (fragClassName.equals(KhuyenMaiFragment.class.getName())) {
                setTitle(navMenuTitles[5]);
                check = 6;
                expListView.setItemChecked(6, true);
                expListView.setSelection(6);
                // set selected item position, etc
            } else if (fragClassName.equals(HethongFragment.class.getName())) {
                setTitle(navMenuTitles[6]);
                check = 7;
                expListView.setItemChecked(7, true);
                expListView.setSelection(7);
                // set selected item position, etc
            } else if (fragClassName.equals(KhaoSatFragment.class.getName())) {
                setTitle("Khảo sát");
                check = 8;
                // set selected item position, etc
            } else if (fragClassName.equals(BinhLuanFragment.class.getName())) {
                setTitle("Bình luận");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(LichSuFragment.class.getName())) {
                setTitle("Lịch sử đặt hàng");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(ThemDHListFragment.class.getName())) {
                setTitle("Thêm đơn hàng mới");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(HinhAnhFragment.class.getName())) {
                setTitle("Hình ảnh");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(TTKHFragment.class.getName())) {
                setTitle("Thông tin khách hàng");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(SuaDHFragment.class.getName())) {
                setTitle("Sửa đơn hàng");
                check = 3;
                // set selected item position, etc
            } else if (fragClassName.equals(ThongKeChungFragment.class.getName())) {
                setTitle("Thống kê chung");
                check = 3;
            } else if (fragClassName.equals(BaoCaoKPIFragment.class.getName())) {
                setTitle("Báo cáo KPI");
                check = 3;
            } else if (fragClassName.equals(DSKHChuaDHFragment.class.getName())) {
                setTitle("DS KH chưa đặt hàng");
                check = 3;
            } else if (fragClassName.equals(ChiTietKhuyenMaiFragment.class.getName())) {
                setTitle("Chi tiết khuyến mại");
                check = 3;
            }
        }else {
            if (fragClassName.equals(TuyenFragment.class.getName())) {
                setTitle(navMenuTitles[0]);
                check = 0;
                expListView.setItemChecked(0, true);
                expListView.setSelection(0);
                // set selected item position, etc
            } else if (fragClassName.equals(DatHangFragment.class.getName())) {
                setTitle(navMenuTitles[1]);
                check = 1;
                expListView.setItemChecked(1, true);
                expListView.setSelection(1);
                // set selected item position, etc
            } else if (fragClassName.equals(ThemKHFragment.class.getName())) {
                setTitle(navMenuTitles[3]);
                check = 2;
                expListView.setItemChecked(3, true);
                expListView.setSelection(3);
                // set selected item position, etc
            } else if (fragClassName.equals(HangHoaFragment.class.getName())) {
                setTitle(navMenuTitles[4]);
                check = 2;
                expListView.setItemChecked(4, true);
                expListView.setSelection(4);
                // set selected item position, etc
            } else if (fragClassName.equals(KhuyenMaiFragment.class.getName())) {
                setTitle(navMenuTitles[5]);
                check = 2;
                expListView.setItemChecked(5, true);
                expListView.setSelection(5);
                // set selected item position, etc
            } else if (fragClassName.equals(HethongFragment.class.getName())) {
                setTitle(navMenuTitles[6]);
                check = 2;
                expListView.setItemChecked(6, true);
                expListView.setSelection(6);
                // set selected item position, etc
            } else if (fragClassName.equals(KhaoSatFragment.class.getName())) {
                setTitle("Khảo sát");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(BinhLuanFragment.class.getName())) {
                setTitle("Bình luận");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(LichSuFragment.class.getName())) {
                setTitle("Lịch sử đặt hàng");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(ThemDHListFragment.class.getName())) {
                setTitle("Thêm đơn hàng mới");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(HinhAnhFragment.class.getName())) {
                setTitle("Hình ảnh");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(TTKHFragment.class.getName())) {
                setTitle("Thông tin khách hàng");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(SuaDHFragment.class.getName())) {
                setTitle("Sửa đơn hàng");
                check = 2;
                // set selected item position, etc
            } else if (fragClassName.equals(ThongKeChungFragment.class.getName())) {
                setTitle("Thống kê chung");
                check = 2;
            } else if (fragClassName.equals(BaoCaoKPIFragment.class.getName())) {
                setTitle("Báo cáo KPI");
                check = 2;
            } else if (fragClassName.equals(DSKHChuaDHFragment.class.getName())) {
                setTitle("DS KH chưa đặt hàng");
                check = 2;
            } else if (fragClassName.equals(ChiTietKhuyenMaiFragment.class.getName())) {
                setTitle("Chi tiết khuyến mại");
                check = 2;
            }
        }

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_ghetham:
            /*
             * GheThamDialog gheThamDialog = new GheThamDialog(MainActivity.this, getResources().getString(R.string.d_gps_title), ""); gheThamDialog.show();
			 */
                return false;
            case R.id.action_bando:
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                KHParcel object = new KHParcel();
                object.setArrList(dskh);
                i.putExtra("DSKH", object);
                startActivity(i);
                return true;
            case R.id.action_thongtin:
                if (NetworkType.internetIsAvailable(this)){
                    if (layThongTinTuyen == null || layThongTinTuyen.getStatus() == AsyncTask.Status.FINISHED) {
                        layThongTinTuyen = new LayThongTinTuyen(this);
                        EventThongTinTuyen eventThongTinTuyen = EventBus.getDefault().getStickyEvent(EventThongTinTuyen.class);
                        if(layThongTinTuyen != null) {
                            layThongTinTuyen.execute(eventThongTinTuyen);
                        }
                    }
                } else {

                }

                return true;

            case R.id.action_dathang:
                Config.isCreateNewDHFromQLDH = true;
                KhachHang kh = new KhachHang();
                // kh.setId(""); //Code cũ, rem đi

                // Cuong them : để lúc vào lịch sử đơn hàng, kích đặt hàng (Dấu +) thì ra cửa sổ
                // đặt hàng, có ngay thông tin KH đã chọn ở lịch sử đơn hàng
                kh.setId("");
                Config.NAME_KH = "";
                mAF.sTmp = "";
//			Log.d("cuong click them don hang", mAF.sGlobal);
                //

                Fragment fragment = new ThemDHListFragment(kh);
                if (fragment != null) {
                    replaceFragment(fragment);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDataPass(Tuyen t) {
        // Log.d("LOG", "hello " + t.getName());
        this.tuyen = t;
    }

    public int check = 0;

    public void setCheck(int check) {
        this.check = check;
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(expListView);
        MenuItem item_ghetham = menu.findItem(R.id.action_ghetham);
        MenuItem item_bando = menu.findItem(R.id.action_bando);
        MenuItem item_thongtin = menu.findItem(R.id.action_thongtin);
        MenuItem item_dathang = menu.findItem(R.id.action_dathang);

        String re = "false";
        if (drawerOpen) {
            re = "true";
        }
        Log.i("drawerOpen", re);

        switch (check) {
            case 0:
                if (!drawerOpen) {
                    item_ghetham.setVisible(true);
                    item_bando.setVisible(true);
                    item_thongtin.setVisible(true);
                    item_dathang.setVisible(false);
                } else {
                    item_ghetham.setVisible(false);
                    item_bando.setVisible(false);
                    item_thongtin.setVisible(false);
                    item_dathang.setVisible(false);
                }
                break;
            case 1:
                if (!drawerOpen) {
                    item_ghetham.setVisible(false);
                    item_bando.setVisible(false);
                    item_thongtin.setVisible(false);
                    item_dathang.setVisible(true);
                } else {
                    item_ghetham.setVisible(false);
                    item_bando.setVisible(false);
                    item_thongtin.setVisible(false);
                    item_dathang.setVisible(false);
                }

                break;
            case 2:
                item_ghetham.setVisible(false);
                item_bando.setVisible(false);
                item_thongtin.setVisible(false);
                item_dathang.setVisible(false);
                break;

            default:
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void replaceFragment(Fragment fragment) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isUserHasJustLogined = sharedPreferences.getBoolean(Config.USER_HAS_JUST_LOGIN, false);

        String backStateName = fragment.getClass().getName();
        String fragmentTag = backStateName;
        Log.i("frgmntName-created", fragmentTag);
        fManager = getFragmentManager();
        boolean fragmentPopped = fManager.popBackStackImmediate(backStateName, 0);

        if (isUserHasJustLogined) {
            Log.e("replaceFragment", "Repeace with new Fragmet");
        } else {
            Log.e("replaceFragment", "Not replace fragmet");
        }

        // fragment not in back stack, create it.
        if ((!fragmentPopped && fManager.findFragmentByTag(fragmentTag) == null) || isUserHasJustLogined) {
            fTrans = fManager.beginTransaction();
            fTrans.replace(R.id.frame_container, fragment, fragmentTag);
            fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fTrans.addToBackStack(backStateName);
            Log.i("frgmntName-create", fragmentTag);
            fTrans.commit();

            //Disable resume function
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Config.USER_HAS_JUST_LOGIN, false);
            editor.commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        PackageManager manager = getApplicationContext().getPackageManager();
        PackageInfo info;
        String version = "";
        try {
            info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
//			version = " [Phiên bản " + info.versionName + "]";
        } catch (NameNotFoundException e) {
        }

        mTitle = title + version;
        Log.e("CONCRETE", "setTitle| title = " + title);

        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
    }

    // back fragment or activity
    @Override
    public void onBackPressed() {

        Log.e("CONCRETE", "MainActivity: onBackPressed");
        // ==1 de bo qua blank window.
        Fragment fragment = getFragmentManager().findFragmentByTag("bando_fragment");
        // If its an instance of Fragment1 I don't want to finish my activity,
        // so I launch a Toast instead.
        Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag(BinhLuanFragment.class.getName());
        if (myFragment != null && myFragment.isVisible()) {
            // add your code here
        }
        if (fragment instanceof BanDoFragment) {
            Toast.makeText(getApplicationContext(), "YOU ARE AT THE TOP FRAGMENT", Toast.LENGTH_SHORT).show();
        }
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            logout();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        WakeLocker.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityExist = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityExist = true;
        Log.e("UPDATE VIEW", "onResume");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean isUserHasJustLogined = sharedPreferences.getBoolean(Config.USER_HAS_JUST_LOGIN, false);

        if (isUserHasJustLogined && currentGroupPosition != 7) {
            if (currentGroupPosition != 2) {
                Log.e("UPDATE VIEW", "Go to Group");
                replaceFragment(getFragmentByGroupPosition(currentGroupPosition));
                setTitle(navMenuTitles[currentGroupPosition]);
            } else {
                Log.e("UPDATE VIEW", "Go to Child");
                replaceFragment(getChildFragmentByChildPosition(currentChildPosition));
                setTitle(bctk.get(currentChildPosition));
//				expListView.setSelectedChild(2, currentChildPosition, true);
            }
            expListView.setSelectedGroup(currentGroupPosition);
            expListView.setItemChecked(currentGroupPosition, true);
            mDrawerLayout.closeDrawer(expListView);
        }
    }

    @Override
    public void transferDSKH(List<KhachHang> list) {
        // TODO Auto-generated method stub
        dskh = new ArrayList<KhachHang>();
        for (KhachHang khachHang : list) {
            dskh.add(khachHang);
        }
        Log.i("map", String.valueOf(dskh.size()));
    }

    private void turnGPSOff() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (provider.contains("gps")) { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn thoát khỏi chương trình ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // stop background service
                getApplication().getApplicationContext().stopService(new Intent(getApplicationContext(), LocationService.class));
                getApplication().getApplicationContext().stopService(new Intent(getApplicationContext(), SendDataLocationService.class));
                getApplication().getApplicationContext().stopService(new Intent(getApplicationContext(), CheckIntentService.class));

//				LocationHelper locationHelper = new LocationHelper(MainActivity.this);
//				locationHelper.removeAll();
                try {
                    turnGPSOff();

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent i1 = new Intent(MainActivity.this, LocationService.class);
                    PendingIntent pendingIntent1 = PendingIntent.getService(MainActivity.this,
                            0, i1, 0);
                    alarmManager.cancel(pendingIntent1);
                    MainActivity.this.stopService(i1);
//					if (!sService(i1)) {
//						MainActivity.this.stopService(i1);
//					}
//					boolean x = sService(i1);

                    Intent i2 = new Intent(MainActivity.this, SendDataLocationService.class);
//				PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 1253, i2, 0);
                    PendingIntent pendingIntent2 = PendingIntent.getService(MainActivity.this,
                            0, i2, 0);
                    alarmManager.cancel(pendingIntent2);
//					boolean y = sService(i2);
                    MainActivity.this.stopService(i2);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ERROR", "error logout");
                }

                if (NetworkType.internetIsAvailable(getApplicationContext()) && Config.isOnlineMode) {
                    new LogoutTask().execute();
                    if (Config.isLogin) {
                        Config.isLogin = false;
                        // Intent service = new Intent(MainActivity.this, UpdateLocationService.class);
                        // stopService(service);
                        /** Bootload service **/
                        editor.putInt(Config.PREF_KEY_LOGIN_STATUS, 0);
                        editor.commit();
//						MainActivity.this.finish();
//						Intent i = new Intent(MainActivity.this, Login.class);
//						startActivity(i);
                    }
                } else {
                    Config.isLogin = false;
                    // Intent service = new Intent(MainActivity.this, UpdateLocationService.class);
                    // stopService(service);
                    /** Bootload service **/
                    editor.putInt(Config.PREF_KEY_LOGIN_STATUS, 0);
                    editor.commit();
//					MainActivity.this.finish();
                    Intent i = new Intent(MainActivity.this, Login.class);
                    startActivity(i);
                    if (isActivityExist)
                        MainActivity.this.finish();

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

    private class LogoutTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Đang đăng xuất khỏi hệ thống...");
            pDialog.setCancelable(false);
            if (!((Activity) MainActivity.this).isFinishing()) {
                pDialog.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            String jsonString = control.getDataJSON(Config.URL_LOGOUT, true);
            return jsonString;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (MainActivity.this.isDestroyed()) { // or call isFinishing() if min sdk version < 17
                    return;
                }
            } else {
                if (MainActivity.this.isFinishing()) {
                    return;
                }
            }
            if (pDialog != null) {
                pDialog.dismiss();
            }

            if (response != null) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject != null) {
                        String result = jsonObject.getString("result");
                        if (result.equals("OK")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Đăng xuất thành công!");
                            builder.setCancelable(false);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Config.isLogin = false;
                                    // Intent service = new Intent(MainActivity.this, UpdateLocationService.class);
                                    // stopService(service);
                                    /* Bootload */
                                    editor.putInt(Config.PREF_KEY_LOGIN_STATUS, 0);
                                    editor.commit();
//									MainActivity.this.finish();
                                    Intent i = new Intent(MainActivity.this, Login.class);
                                    startActivity(i);
                                    if (isActivityExist)
                                        MainActivity.this.finish();
                                }
                            });
                            builder.create().show();
                        } else {
                            control.showAlertDialog(MainActivity.this, "Thông báo", "Quá trình đăng xuất thất bại. Vui lòng thử lại!", false);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                control.showAlertDialog(MainActivity.this, "Error", "Không kết nối được tới máy chủ. Vui lòng thử lại!", false);
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void checkGPS() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

            if (!provider.contains("gps")) { //if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
        } else {
            if (!isGPSEnabled) {
                // Thong bao chua bat GPS
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Không xác định được vị trí");
                builder.setMessage("Xin vui lòng bật GPS!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(final NetworkStateChanged networkStateChanged) {

        if (networkStateChanged.isInternetConnected()) {
            Log.e(TAG, "isConnected");
            if(dialog.isShowing()){
                dialog.dismiss();
            }

        } else {
            if (Config.isOnlineMode) {
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Mất kết nối mạng");
                    builder.setMessage("Vui lòng kiểm tra lại kết nối");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
//                    final AlertDialog dialog = builder.create();
                    dialog = builder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (isNetworkConnected()) {
                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());
                            } else {
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                startActivityForResult(intent, 0);
                            }
                        }
                    });
            }
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
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

    private class LayThongTinTuyen extends AsyncTask<EventThongTinTuyen, Void, String> {
        private ProgressDialog pDialog;
        Context context;
        public LayThongTinTuyen(Context mContext) {
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
        protected String doInBackground(EventThongTinTuyen... params) {
            String sValue = "";
            try {
                String url = API_code.URL_TONG_HOP_KET_QUA_TUYEN + "&trxid=" + params[0].getIdTuyen();
                sValue = control.jsonValues(url, true);
            } catch (Exception ex) {
                Log.e("send Error: ", ex.toString());
            }
            return sValue;
        }


        @Override
        protected void onPostExecute(String result) {
            String jsonResult = "";
            dismissWithCheck(pDialog);
            Gson g = new Gson();
            ThongTinTuyenResponse  thongTinTuyenResponse = g.fromJson(result, ThongTinTuyenResponse.class);

            EventThongTinTuyen eventThongTinTuyen = EventBus.getDefault().getStickyEvent(EventThongTinTuyen.class);
            if(eventThongTinTuyen != null){
                ThongTinDialog thongTinDialog = new ThongTinDialog(MainActivity.this, getResources().getString(R.string.d_title) + " - " + eventThongTinTuyen.getDayTuyen(), thongTinTuyenResponse);
                thongTinDialog.show();
            }
        }
    }


    public void showProgressBar() {
        try{
            if (hud != null && !hud.isShowing())
                hud.show();
//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }catch (Exception ex){

        }

    }

    public void hideProgressBar() {
        try {
            if (hud != null && hud.isShowing())
                hud.dismiss();
//            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } catch (Exception ex) {

        }
    }


}


