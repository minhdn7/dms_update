package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DSKHChuaDHAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.Rpt_Kpi_Adapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.Rpt_Thongkechung_Adapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangTKC;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.NVKD;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ResultDSKHChuaDH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Rpt_Kpi_Model;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Rpt_Thongkechung_Model;

@SuppressLint("ValidFragment")
public class DSKHChuaDHFragment extends ListFragment {

    Button btn_searchDSKH;
    Spinner sp_NVKD, spThang, spNam;
    EditText et_startDateDSKH, et_endDateDSKH;
    ImageView iv_startDateDSKH, iv_endDateDSKH;
    LinearLayout linearResult_DSKHChuadathang;

    int day, month, year, totalPage = 0, currentPage = 0, totalPageKH = 0, pageCount = 0, currentPageKH = 0;
    Controller control;
    ConvertFont cf = new ConvertFont();
    private ProgressDialog pDialog;
    List<String> listNVKD;
    List<DonHang> listDonHang;
    List<NVKD> mList;
    List<KhachHang> listKhachHang;
    TextView tv_NVKD;
    String idNVKD;

    // cuongtm them
    List<ResultDSKHChuaDH> mListResultSearch;
    DSKHChuaDHAdapter adapter = null;

    List<Rpt_Kpi_Model> mListRptKPI;
    Rpt_Kpi_Adapter adapterKpi = null;

    List<Rpt_Thongkechung_Model> mListRptThongkechung;
    Rpt_Thongkechung_Adapter adapterThongkechung = null;

    String startDate;
    String endDate;

    private GetDS_KH_ChuaDathang_Task getDS_kh_chuaDathang_task;
    private Get_RPT_KPI_Task get_rpt_kpi_task;
    private Get_RPT_Thongkechung_Task get_rpt_thongkechung_task;

    private Activity activity;

    public DSKHChuaDHFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dskh_chuadathang, container, false);
        btn_searchDSKH = (Button) view.findViewById(R.id.btn_searchDSKH);

        sp_NVKD = (Spinner) view.findViewById(R.id.sp_NVKD);
        spThang = (Spinner) view.findViewById(R.id.sp_Thang);
        spNam = (Spinner) view.findViewById(R.id.sp_Nam);

        et_startDateDSKH = (EditText) view.findViewById(R.id.et_startDateDSKH);
        et_endDateDSKH = (EditText) view.findViewById(R.id.et_endDateDSKH);
        iv_startDateDSKH = (ImageView) view.findViewById(R.id.iv_startDateDSKH);
        iv_endDateDSKH = (ImageView) view.findViewById(R.id.iv_endDateDSKH);

        // cuongtm them
        tv_NVKD = (TextView) view.findViewById(R.id.tv_NVKD);
        linearResult_DSKHChuadathang = (LinearLayout) view.findViewById(R.id.linearLayoutKQ_DSKHChuaDathang);

        et_startDateDSKH.setVisibility(View.GONE);
        et_endDateDSKH.setVisibility(View.GONE);
        iv_startDateDSKH.setVisibility(View.GONE);
        iv_endDateDSKH.setVisibility(View.GONE);

        enDisControl(false);

        if (mAF.act.contentEquals("rpt_dskh_chuadathang")) {
            linearResult_DSKHChuadathang.setVisibility(View.VISIBLE);
        }
        if (mAF.act.contentEquals("rpt_kpi") || mAF.act.contentEquals("rpt_chung")) {
            //linearResult_RPT_KPI.setVisibility(View.VISIBLE);
        }
        getDate();

        // cuongtm tam bo, khong dung
        addSpThang();
        addSpNam();

        listKhachHang = new ArrayList<KhachHang>();

        iv_startDateDSKH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_startDateDSKH.getText().toString().length() > 0) {
                    String[] item = et_startDateDSKH.getText().toString().split("/");
                    day = Integer.parseInt(item[0]);
                    month = Integer.parseInt(item[1]) - 1;
                    year = Integer.parseInt(item[2]);
                }
                DialogFragment dialog = new DatePickerFragment(0);
                dialog.show(getFragmentManager(), "DatePicker");
            }
        });

        iv_endDateDSKH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_endDateDSKH.getText().toString().length() > 0) {
                    String[] item = et_endDateDSKH.getText().toString().split("/");
                    day = Integer.parseInt(item[0]);
                    month = Integer.parseInt(item[1]) - 1;
                    year = Integer.parseInt(item[2]);
                }
                DialogFragment dialog = new DatePickerFragment(1);
                dialog.show(getFragmentManager(), "DatePicker");
            }
        });

        btn_searchDSKH.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("cuong click", mAF.act);
                enDisControl(false);

                if (mAF.act.contentEquals("rpt_dskh_chuadathang")) {
                    linearResult_DSKHChuadathang.setVisibility(View.VISIBLE);
                }
                if (mAF.act.contentEquals("rpt_kpi") || mAF.act.contentEquals("rpt_chung")) {
                    //linearResult_RPT_KPI.setVisibility(View.VISIBLE);
                }

                listDonHang = new ArrayList<DonHang>();
                totalPage = currentPage = 0;
                if (control.isOnline(getActivity())) {
                    // startDate = et_startDateDSKH.getText().toString();
                    startDate = "01/" + spThang.getSelectedItem().toString() + "/" + spNam.getSelectedItem().toString();
                    Log.d("startdate", startDate);

                    endDate = getLastDayOfMonth(Integer.parseInt(spThang.getSelectedItem().toString()) - 1, Integer.parseInt(spNam.getSelectedItem().toString())) + "";
//					endDate = a + "/" + spThang.getSelectedItem().toString() + "/" + spNam.getSelectedItem().toString();

                    idNVKD = "";

                    // cuongtm tam bo, tinh theo cach moi
                    /**
                     * if (sp_NVKD.getSelectedItemPosition() != 0) { idNVKD = mList.get(sp_NVKD.getSelectedItemPosition() - 1).userid; } getListView().setAdapter(null);
                     * new DSDonHang_ThangTask().execute(new DonHangTKC(startDate, endDate, idNVKD)); if (listKhachHang.size() <= 0) { new DSKhachHangTask().execute(); }
                     **/

                    // Rpt danh sach chua dat hang
                    if (mAF.act.contentEquals("rpt_dskh_chuadathang")) {
                        // cuongtm them
                        mListResultSearch = new ArrayList<ResultDSKHChuaDH>();
                        getListView().setAdapter(null);
                        if (getDS_kh_chuaDathang_task == null || getDS_kh_chuaDathang_task.getStatus() == AsyncTask.Status.FINISHED) {
                            getDS_kh_chuaDathang_task = new GetDS_KH_ChuaDathang_Task();
                            getDS_kh_chuaDathang_task.execute();
                        }
                    }
                    // end Rpt danh sach chua dat hang if (mAF.act.contentEquals("rpt_dskh_chuadathang") )

                    // Rpt rpt_kpi
                    if (mAF.act.contentEquals("rpt_kpi")) {
                        // cuongtm them
                        mListRptKPI = new ArrayList<Rpt_Kpi_Model>();
                        getListView().setAdapter(null);
                        if (get_rpt_kpi_task == null || get_rpt_kpi_task.getStatus() == AsyncTask.Status.FINISHED) {
                            get_rpt_kpi_task = new Get_RPT_KPI_Task();
                            get_rpt_kpi_task.execute();
                        }
                    }
                    // end rpt_kpi danh sach chua dat hang if (mAF.act.contentEquals("rpt_kpi") )

                    // Rpt rpt_thongkechung
                    if (mAF.act.contentEquals("rpt_chung")) {
                        // cuongtm them
                        mListRptThongkechung = new ArrayList<Rpt_Thongkechung_Model>();
                        getListView().setAdapter(null);
                        if (get_rpt_thongkechung_task == null || get_rpt_thongkechung_task.getStatus() == AsyncTask.Status.FINISHED) {
                            get_rpt_thongkechung_task = new Get_RPT_Thongkechung_Task();
                            get_rpt_thongkechung_task.execute();
                        }
                    }
                    // end rpt_kpi danh sach chua dat hang if (mAF.act.contentEquals("rpt_kpi") )

                } else {

                }
            }
        });

        // cuongtm them: chỉ báo cáo cho 1 nhân viên kd, nên không cần hiển thị.
        // khi lấy danh sách NVKD xuống thì bổ sung where để chỉ lấy 1 NVKD ra
        tv_NVKD.setVisibility(View.GONE);
        sp_NVKD.setVisibility(View.GONE);
        // linearResult.setBackgroundColor(Color.parseColor("#F0F8FF"));
        //
        if (savedInstanceState != null) {
            Log.d("on create form", "Not NULL");
        }
        Log.d("on create form", "OK");
        return view;
    }

    private static String getLastDayOfMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        return DATE_FORMAT.format(date);
    }

    private void addSpThang() {
        String[] items = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spThang.setAdapter(adapter);
        Calendar now = Calendar.getInstance();
        int m = now.get(Calendar.MONTH);
        spThang.setSelection(m);
    }

    private void addSpNam() {
        String kq = "";
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        for (int i = (year - 5); i <= year; i++) {
            if (i == (year - 5)) {
                kq = i + "";
            } else {
                kq = i + "," + kq;
            }
        }
        Log.d("kq", kq);
        String[] items = kq.split(",");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spNam.setAdapter(adapter);
    }

    public void enDisControl(Boolean flg) {
        if (!flg) {
            linearResult_DSKHChuadathang.setVisibility(View.GONE);
            //linearResult_RPT_KPI.setVisibility(View.GONE);
        } else {
            linearResult_DSKHChuadathang.setVisibility(View.VISIBLE);
            //linearResult_RPT_KPI.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        control = new Controller(getActivity());
    }

    // cuongtm them - tinh rpt theo cach moi
    private String getURL_KH_ChuaDH() {
        String url = API_code.URL_DSKH_CHUA_DATHANG + "&pageno=" + pageCount + "&pagerec=100&org_id=0" + "&assign_id=0" + "&json={\"date1\":\"" + startDate + "\""
                + ",\"date2\":\"" + endDate + "\"" + "}";
        Log.i("url-dskhchuadathang", url);
        return url;
    }

    private String getURL_RPT_KPI() {
        String url = API_code.URL_BC_KPI + "&pageno=" + pageCount + "&pagerec=100&org_id=0" + "&assign_id=0" + "&json={\"date1\":\"" + startDate + "\"" + ",\"date2\":\""
                + endDate + "\"" + "}";
        Log.i("cuong URL_BC_KPI", url);
        // result= control.getDataJSON(control.convertURL(url), true);
        return url;
    }

    private String getURL_RPT_THONGKECHUNG() {
        String url = API_code.URL_BC_TONGHOP + "&pageno=" + pageCount + "&pagerec=100&org_id=0" + "&assign_id=0" + "&json={\"date1\":\"" + startDate + "\""
                + ",\"date2\":\"" + endDate + "\"" + "}";
        Log.i("cuong URL_BC_TONGHOP", url);
        // result= control.getDataJSON(control.convertURL(url), true);
        return url;
    }

    class GetDS_KH_ChuaDathang_Task extends AsyncTask<Void, List<ResultDSKHChuaDH>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
            // MA_KH = txtSearchKH.getText().toString().trim();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            pageCount++;
            if (totalPage == 0) {
                totalPage = 100;
            }
            String url, json_dsbl = "";
            url = getURL_KH_ChuaDH();
            try {
                json_dsbl = control.jsonValues(url, false);
                // json_dsbl = control.getDataJSON(url, false); //Khi null hay bi loi
                Log.d("cuong result", json_dsbl);

                if (json_dsbl.contains("api_error")) {
                    mAF.writelog("Error KHKDH Rpt: " + json_dsbl, getActivity(), mAF.filelog);
                    Log.d("lay dskhchuadathang", "Lỗi lấy số liệu khuyến mại");
                } else {
                    Type listType = new TypeToken<List<ResultDSKHChuaDH>>() {
                    }.getType();
                    List<ResultDSKHChuaDH> ds_bl = new ArrayList<ResultDSKHChuaDH>();
                    try {
                        ds_bl = (List<ResultDSKHChuaDH>) new Gson().fromJson(json_dsbl, listType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ds_bl != null && ds_bl.size() > 0) {
                        for (int i = 0; i < ds_bl.size(); i++) {
                            mListResultSearch.add(ds_bl.get(i));
                        }
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Không có dữ liệu!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            } catch (Exception ex) {
                mAF.writelog("Error KHKDH Rpt: " + ex.toString(), getActivity(), mAF.filelog);
                Log.d("json dskhchuadathang", ex.toString());
            }

            publishProgress(mListResultSearch);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<ResultDSKHChuaDH>... values) {
            super.onProgressUpdate(values);
            if (adapter == null) {
                adapter = new DSKHChuaDHAdapter(getActivity(), values[0]);
                setListAdapter(adapter);
            } else {
                // adapter.notifyDataSetChanged();
                // cuongtm them
                adapter = new DSKHChuaDHAdapter(getActivity(), values[0]);
                setListAdapter(adapter);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }

    }

    class Get_RPT_KPI_Task extends AsyncTask<Void, List<Rpt_Kpi_Model>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
            // MA_KH = txtSearchKH.getText().toString().trim();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            pageCount++;
            if (totalPage == 0) {
                totalPage = 100;
                // tam bo
                // NorAndNop n = control.getNorNop(getURL_NopNor());
                // totalPages = Integer.parseInt(n.getNop());
            }
            String url, json_dsbl = "";
            url = getURL_RPT_KPI();
            try {
                json_dsbl = control.jsonValues(url, false);
                // json_dsbl = control.getDataJSON(url, false); //Khi null hay bi loi
                Log.d("cuong result", json_dsbl);

                if (json_dsbl.contains("api_error")) {
                    Log.d("lay dskhchuadathang", "Lỗi lấy số liệu khuyến mại");
                } else {
                    Type listType = new TypeToken<List<Rpt_Kpi_Model>>() {
                    }.getType();
                    List<Rpt_Kpi_Model> ds_bl = new ArrayList<Rpt_Kpi_Model>();
                    ;
                    try {
                        ds_bl = (List<Rpt_Kpi_Model>) new Gson().fromJson(json_dsbl, listType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ds_bl.size() > 0) {
                        for (int i = 0; i < ds_bl.size(); i++) {
                            mListRptKPI.add(ds_bl.get(i));
                        }
                    }
                }
            } catch (Exception ex) {
                Log.d("json dskhchuadathang", ex.toString());
            }

            publishProgress(mListRptKPI);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<Rpt_Kpi_Model>... values) {
            super.onProgressUpdate(values);
            if (adapterKpi == null) {
                adapterKpi = new Rpt_Kpi_Adapter(getActivity(), values[0]);
                setListAdapter(adapterKpi);
            } else {
                // adapter.notifyDataSetChanged();
                // cuongtm them
                adapterKpi = new Rpt_Kpi_Adapter(getActivity(), values[0]);
                setListAdapter(adapterKpi);
            }

            // getListView().setOnScrollListener(onScrollListener());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }

    }

    class Get_RPT_Thongkechung_Task extends AsyncTask<Void, List<Rpt_Thongkechung_Model>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
            // MA_KH = txtSearchKH.getText().toString().trim();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            pageCount++;
            if (totalPage == 0) {
                totalPage = 100;
                // tam bo
                // NorAndNop n = control.getNorNop(getURL_NopNor());
                // totalPages = Integer.parseInt(n.getNop());
            }
            String url, json_dsbl = "";
            url = getURL_RPT_THONGKECHUNG();
            try {
                json_dsbl = control.jsonValues(url, false);
                // json_dsbl = control.getDataJSON(url, false); //Khi null hay bi loi
                Log.d("cuong result", json_dsbl);

                if (json_dsbl.contains("api_error")) {
                    Log.d("lay dskhchuadathang", "Lỗi lấy số liệu khuyến mại");
                } else {
                    Type listType = new TypeToken<List<Rpt_Thongkechung_Model>>() {
                    }.getType();
                    List<Rpt_Thongkechung_Model> ds_bl = new ArrayList<Rpt_Thongkechung_Model>();
                    ;
                    try {
                        ds_bl = (List<Rpt_Thongkechung_Model>) new Gson().fromJson(json_dsbl, listType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (ds_bl.size() > 0) {
                        for (int i = 0; i < ds_bl.size(); i++) {
                            mListRptThongkechung.add(ds_bl.get(i));
                        }
                    }

                }
            } catch (Exception ex) {
                Log.d("json dskhchuadathang", ex.toString());
            }

            publishProgress(mListRptThongkechung);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<Rpt_Thongkechung_Model>... values) {
            super.onProgressUpdate(values);
            if (adapterThongkechung == null) {
                adapterThongkechung = new Rpt_Thongkechung_Adapter(getActivity(), values[0]);
                setListAdapter(adapterKpi);
            } else {
                // adapter.notifyDataSetChanged();
                // cuongtm them
                adapterThongkechung = new Rpt_Thongkechung_Adapter(getActivity(), values[0]);
                setListAdapter(adapterThongkechung);
            }

            // getListView().setOnScrollListener(onScrollListener());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
        }

    }

    // end cuongtm them - tinh rpt theo cach moi

    private class DSDonHang_ThangTask extends AsyncTask<DonHangTKC, Void, Void> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Đang tìm kiếm ...");
            pDialog.show();
        }

        @Override
        protected Void doInBackground(DonHangTKC... params) {
            // TODO Auto-generated method stub
            currentPage++;
            String json = new Gson().toJson(params);
            if (totalPage == 0) {
                String url = API_code.URL_DS_DONDATHANG + "&pageno=-1&json=" + control.subJSON(json);
                NorAndNop n = control.getNorNop(url);
                totalPage = Integer.parseInt(n.getNop());

                if (totalPage == 0) {
                    return null;
                }
            }

            String url = API_code.URL_DS_DONDATHANG + "&pageno=" + currentPage + "&json=" + control.subJSON(json);
            String jsonData = control.getDataJSON(url, false);

            Type listType = new TypeToken<List<DonHang>>() {
            }.getType();
            List<DonHang> listData = new ArrayList<DonHang>();
            ;
            try {
                listData = new Gson().fromJson(jsonData, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listData.size() > 0) {
                for (int i = 0; i < listData.size(); i++) {
                    listDonHang.add(listData.get(i));
                }
            }

            if (currentPage < totalPage) {
                doInBackground(params);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (listKhachHang.size() > 0) {
                getResultSearch();
            }
        }
    }

    private class DSKhachHangTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            currentPageKH++;
            if (totalPageKH == 0) {
                String url = API_code.URL_GHETHAM_DSKH + "&pageno=-1";
                try {
                    NorAndNop n = control.getNorNop(url);
                    totalPageKH = Integer.parseInt(n.getNop());
                } catch (Exception e) {
                }

                if (totalPageKH == 0) {
                    return null;
                }
            }

            String urlData = API_code.URL_GHETHAM_DSKH + "&pageno=" + currentPageKH;
            String jsonData = control.getDataJSON(urlData, false);
            Type listType = new TypeToken<List<KhachHang>>() {
            }.getType();
            List<KhachHang> listData = new ArrayList<KhachHang>();
            try {
                listData = new Gson().fromJson(jsonData, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (listData.size() > 0) {
                for (int i = 0; i < listData.size(); i++) {
                    listKhachHang.add(listData.get(i));
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (currentPageKH < totalPageKH) {
                new DSKhachHangTask().execute();
            }
            Log.i("List Đơn Hàng : ", listDonHang.size() + "");
            Log.i("List Khách Hàng : ", listKhachHang.size() + "");
            getResultSearch();
        }
    }

    private class DSNhaCungCapTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String json = control.getDataJSON(API_code.URL_DS_NVKD, false);
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            listNVKD = new ArrayList<String>();
            Type listType = new TypeToken<List<NVKD>>() {
            }.getType();
            try {
                mList = new Gson().fromJson(result, listType);
            } catch (Exception e) {
                e.printStackTrace();
            }
            listNVKD.add("Tất cả");
            if (mList.size() > 0) {
                for (int i = 0; i < mList.size(); i++) {
                    listNVKD.add(cf.getUTF8StringFromNCR(mList.get(i).user_name));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, listNVKD);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            sp_NVKD.setAdapter(adapter);
        }
    }

    private void getResultSearch() {
        // Get codeKH in DH to list
        List<String> order_codeDH = new ArrayList<String>();
        for (int i = 0; i < listDonHang.size(); i++) {
            if (!order_codeDH.contains(listDonHang.get(i).getOrderer_code())) {
                order_codeDH.add(listDonHang.get(i).getOrderer_code());
            }
        }

        // Check codeKH exitst in codeKH of DH
        for (int j = 0; j < listKhachHang.size(); j++) {
            if (!order_codeDH.contains(listKhachHang.get(j).getCode())) {
                KhachHang kh = listKhachHang.get(j);
                ResultDSKHChuaDH result = new ResultDSKHChuaDH(kh.getCode(), kh.getName(), kh.getAddress(), kh.getAssign_name().substring(0,
                        kh.getAssign_name().indexOf("-")), "", "", "");
                mListResultSearch.add(result);
            }
        }

        adapter = new DSKHChuaDHAdapter(getActivity(), mListResultSearch);
        setListAdapter(adapter);
        //tv_total_result.setText("Kết quả tìm kiếm :" + mListResultSearch.size());
        dismissWithCheck(pDialog);
    }

    private void getDate() {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        et_startDateDSKH.setText(new StringBuilder().append(1).append("/").append(month + 1).append("/").append(year));
        et_endDateDSKH.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year));
    }

    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        int id;

        public DatePickerFragment(int id) {
            // TODO Auto-generated constructor stub
            this.id = id;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            return dialog;
        }

        @Override
        public void onDateSet(DatePicker view, int selectyear, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            year = selectyear;
            month = monthOfYear;
            day = dayOfMonth;
            switch (id) {
                case 0:
                    et_startDateDSKH.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year));
                    break;
                case 1:
                    et_endDateDSKH.setText(new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
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
}
