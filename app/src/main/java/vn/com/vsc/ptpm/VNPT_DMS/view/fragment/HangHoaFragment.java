package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HangHoaAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.ShowThumbAdaper;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;

public class HangHoaFragment extends ListFragment {
    private static final String TAG = HangHoaFragment.class.getName();
    EditText txtSearchHH;
    Button btnSearchHH, btnClose;
    TextView tv_NoDataHH;
    Switch sw_trongkhoHH;
    Controller control;
    int totalPage = 0, currentPage = 0;
    List<HangHoaEntity> listHH = new ArrayList<HangHoaEntity>();
    HangHoaAdapter adapter;
    String key = null;
    boolean isTrongKho, isSearch = false;
    int widthScreen, heightScreen;
    private ProgressDialog pDialog;
    private getDSHangHoaTask getDSHangHoaTask;
    private getDSHangHoaSearchTask getDSHangHoaSearchTask;

    private Activity activity;

    public HangHoaFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getWidthHeightScreen();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            widthScreen = widthScreen * 3 / 5;
            heightScreen = heightScreen / 2;
        } else {
            heightScreen = heightScreen * 2 / 5;
        }

//        startLoadData();
    }

    private void startLoadData() {
        if (control.isOnline(activity)) {
            isTrongKho = true;
            if (getDSHangHoaTask == null || getDSHangHoaTask.getStatus() == AsyncTask.Status.FINISHED) {
                getDSHangHoaTask = new getDSHangHoaTask();
                getDSHangHoaTask.execute();
            }
        } else {
            sw_trongkhoHH.setChecked(false);
            isTrongKho = false;
            getDataLocal();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hanghoa, container, false);
        control = new Controller(activity);

        txtSearchHH = (EditText) view.findViewById(R.id.txtSearchHH);
        sw_trongkhoHH = (Switch) view.findViewById(R.id.sw_trongkhoHH);
        tv_NoDataHH = (TextView) view.findViewById(R.id.tv_NoDataHH);
        btnSearchHH = (Button) view.findViewById(R.id.btnSearchHH);
        btnClose = (Button) view.findViewById(R.id.btnClose);
        startLoadData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        startLoadData();
//        txtSearchHH = (EditText) view.findViewById(R.id.txtSearchHH);
//        sw_trongkhoHH = (Switch) view.findViewById(R.id.sw_trongkhoHH);
//        tv_NoDataHH = (TextView) view.findViewById(R.id.tv_NoDataHH);
//        btnSearchHH = (Button) view.findViewById(R.id.btnSearchHH);
//        btnClose = (Button) view.findViewById(R.id.btnClose);

        btnSearchHH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (control.isOnline(activity)) {
                    key = txtSearchHH.getText().toString();
                    setListAdapter(null);
                    adapter = null;
                    listHH = new ArrayList<HangHoaEntity>();
                    currentPage = 0;
                    totalPage = 0;
                    isTrongKho = sw_trongkhoHH.isChecked();
                    if (key.equals("")) {
                        isSearch = false;
                        if (getDSHangHoaTask == null || getDSHangHoaTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSHangHoaTask = new getDSHangHoaTask();
                            getDSHangHoaTask.execute();
                        }
                    } else {
                        isSearch = true;
                        if (getDSHangHoaSearchTask == null || getDSHangHoaSearchTask.getStatus() == AsyncTask.Status.FINISHED) {
                            getDSHangHoaSearchTask = new getDSHangHoaSearchTask();
                            getDSHangHoaSearchTask.execute(key);
                        }
                    }
                } else {
                    getDataLocal();
                }
            }
        });

        btnClose.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Config.isOpenNew = true;
                activity.onBackPressed();
            }
        });
    }

    @SuppressWarnings("static-access")
    private void getDataLocal() {
        try {
            key = txtSearchHH.getText().toString();
            key = new ConvertFont().GetNCRDecimalString(key);
            if (key.equals("")) {
                listHH = new HangHoaDAL(activity).getAll();
            } else {
                listHH = new HangHoaDAL(activity).getByFilter(key);
            }
            if (listHH != null){
                if (listHH.size() > 0) {
                    tv_NoDataHH.setVisibility(View.GONE);
                    adapter = new HangHoaAdapter(activity, listHH, isTrongKho);
                    setListAdapter(adapter);

                    getListView().setOnScrollListener(onScrollListener());
                    getListView().setOnItemClickListener(onItemClickListener());
                } else {
                    tv_NoDataHH.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnScrollListener onScrollListener() {

        return new OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (getListView().getLastVisiblePosition() >= getListView().getCount() - 1 && currentPage < totalPage) {
                        if (control.isOnline(activity)) {
                            if (isSearch) {
                                if (getDSHangHoaSearchTask == null || getDSHangHoaSearchTask.getStatus() == AsyncTask.Status.FINISHED) {
                                    getDSHangHoaSearchTask = new getDSHangHoaSearchTask();
                                    getDSHangHoaSearchTask.execute(key);
                                }
                            } else {
                                if (getDSHangHoaTask == null || getDSHangHoaTask.getStatus() == AsyncTask.Status.FINISHED) {
                                    getDSHangHoaTask = new getDSHangHoaTask();
                                    getDSHangHoaTask.execute();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        };
    }

    private OnItemClickListener onItemClickListener() {
        return new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity, "Click position : " + position, Toast.LENGTH_SHORT).show();
                // FragmentManager fm = activity.getFragmentManager();
                // ThumbnailFragment mDilog = new ThumbnailFragment(listHH,
                // position);
                // mDilog.show(fm,"My Dialog");
                showThumb(activity, position);
            }
        };
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWidthHeightScreen();
            widthScreen = widthScreen * 3 / 5;
            heightScreen = heightScreen / 2;
            Toast.makeText(activity, "Width : " + String.valueOf(widthScreen) + "\n " + "Height : " + String.valueOf(heightScreen), Toast.LENGTH_LONG).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWidthHeightScreen();
            heightScreen = heightScreen * 2 / 5;
            Toast.makeText(activity, "Width : " + String.valueOf(widthScreen) + "\n " + "Height : " + String.valueOf(heightScreen), Toast.LENGTH_LONG).show();
        }
    }

    @SuppressWarnings("static-access")
    private void showThumb(Activity context, int pos) {
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.myPopupThumbnail);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_thumb_hh, viewGroup);

        final ConvertFont cf = new ConvertFont();
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.popup_animation);
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.vp_Thumbnail);
        LinearLayout.LayoutParams ll_thumb = new LinearLayout.LayoutParams(widthScreen, heightScreen);
        viewPager.setLayoutParams(ll_thumb);
        final TextView tv = (TextView) layout.findViewById(R.id.tv_posThumbnail);
        final TextView tv_title = (TextView) layout.findViewById(R.id.title_HH);
        ShowThumbAdaper adapter = new ShowThumbAdaper(activity, listHH);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pos);
        tv.setText("Vuốt ngang để xem hình tiếp theo " + (pos + 1) + "/" + listHH.size());
        tv_title.setText(listHH.get(pos).getCode() + " - " + cf.getUTF8StringFromNCR(listHH.get(pos).getName()));
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                int pos = arg0 + 1;
                tv.setText("Vuốt ngang để xem hình tiếp theo " + pos + "/" + listHH.size());
                tv_title.setText(listHH.get(arg0).getCode() + " - " + cf.getUTF8StringFromNCR(listHH.get(arg0).getName()));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void getWidthHeightScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        widthScreen = displaymetrics.widthPixels;
        heightScreen = displaymetrics.heightPixels;
    }

    public void hideKeyboard() {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    private class getDSHangHoaTask extends AsyncTask<Void, List<HangHoaEntity>, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tải dữ liệu. Vui lòng chờ trong giây lát...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Void... params) {
            currentPage++;
            String urlDataJSON = null;
            if (totalPage == 0) {
                try {
                    if (isTrongKho) {
                        NorAndNop n = control.getNorNop(API_code.URL_HANGHOA + "&trongkho=1&pageno=-1");
                        totalPage = Integer.parseInt(n.getNop());
//						totalPage = (int) Long.parseLong(n.getNop());

                    } else {
                        NorAndNop n = control.getNorNop(API_code.URL_HANGHOA + "&trongkho=-1&pageno=-1");
                        totalPage = Integer.parseInt(n.getNop());
//						totalPage = (int) Long.parseLong(n.getNop());
                    }
                } catch (Exception e) {
                    totalPage = 0;
                }
            }

            if (isTrongKho) {
                urlDataJSON = API_code.URL_HANGHOA + "&trongkho=1&pageno=" + currentPage;
            } else {
                urlDataJSON = API_code.URL_HANGHOA + "&trongkho=-1&pageno=" + currentPage;
            }

            String dsHH = control.getDataJSON(urlDataJSON, false);
            Type listType = new TypeToken<List<HangHoaEntity>>() {
            }.getType();
            List<HangHoaEntity> listNewData = new ArrayList<HangHoaEntity>();
            try {
                listNewData = new Gson().fromJson(dsHH, listType);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage() + "");
            }
            if (listNewData != null) {
                if (listNewData.size() > 0) {
                    for (int i = 0; i < listNewData.size(); i++) {
                        listHH.add(listNewData.get(i));
                    }
                }
            }
            publishProgress(listHH);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<HangHoaEntity>... values) {
            super.onProgressUpdate(values);
            try {
                if (values[0].size() > 0) {
                    tv_NoDataHH.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new HangHoaAdapter(activity, values[0], isTrongKho);
                        setListAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    getListView().setOnScrollListener(onScrollListener());
                    getListView().setOnItemClickListener(onItemClickListener());
                } else {
                    tv_NoDataHH.setVisibility(View.VISIBLE);
                }
            }catch (Exception ex){
                Log.d("LỖI HÀNG HÓA UPDATE: ", ex.toString());
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }
    }

    private class getDSHangHoaSearchTask extends AsyncTask<String, List<HangHoaEntity>, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Đang tìm kiếm dữ liệu...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(String... params) {
            ++currentPage;
            String urlDataJSOn = "";
            if (totalPage == 0) {
                try {
                    if (isTrongKho) {
                        NorAndNop n = control.getNorNop(API_code.URL_HANGHOA + "&tenloaisp=" + params[0] + "&trongkho=1");
                        totalPage = Integer.parseInt(n.getNop());
//						totalPage = (int) Long.parseLong(n.getNop());
                    } else {
                        NorAndNop n = control.getNorNop(API_code.URL_HANGHOA + "&tenloaisp=" + params[0] + "&trongkho=-1");
                        totalPage = Integer.parseInt(n.getNop());
//						totalPage = (int) Long.parseLong(n.getNop());
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }
            if (isTrongKho) {
                urlDataJSOn = API_code.URL_HANGHOA + "&tenloaisp=" + params[0] + "&trongkho=-1&pageno=" + currentPage;
            } else {
                urlDataJSOn = API_code.URL_HANGHOA + "&tenloaisp=" + params[0] + "&trongkho=1&pageno=" + currentPage;
            }
            String dsHH = control.getDataJSON(urlDataJSOn, false);
            Type listType = new TypeToken<List<HangHoaEntity>>() {
            }.getType();
            List<HangHoaEntity> listNewData = new ArrayList<HangHoaEntity>();
            try {
                listNewData = new Gson().fromJson(dsHH, listType);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            if (listNewData != null) {
                if (listNewData.size() > 0) {
                    for (int i = 0; i < listNewData.size(); i++) {
                        listHH.add(listNewData.get(i));
                    }
                }
            }

            publishProgress(listHH);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<HangHoaEntity>... values) {
            super.onProgressUpdate(values);
            if (values[0].size() > 0) {
                tv_NoDataHH.setVisibility(View.GONE);
                if (adapter == null) {
                    adapter = new HangHoaAdapter(activity, values[0], isTrongKho);
                    setListAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
                getListView().setOnScrollListener(onScrollListener());
                getListView().setOnItemClickListener(onItemClickListener());
            } else {
                tv_NoDataHH.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
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
        }
    }

    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            // Do nothing.
        }
    }
}
