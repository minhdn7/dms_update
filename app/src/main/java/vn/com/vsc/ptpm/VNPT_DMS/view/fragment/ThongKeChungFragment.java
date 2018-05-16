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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.Rpt_Thongkechung_Adapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.NorAndNop;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BCTongHop;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangTKC;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.NVKD;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Rpt_Thongkechung_Model;

public class ThongKeChungFragment extends ListFragment {
    private Get_RPT_Thongkechung_Task get_rpt_thongkechung_task;
    Button btn_searchTKC;
    Spinner sp_NVKD;
    EditText et_startDateTKC, et_endDateTKC;
    ImageView iv_startDateTKC, iv_endDateTKC;
    TextView tv_DoanhSo_KetQua, tv_NangSuat_KetQua, tv_SoKHghetham_KetQua,
            tv_soKHmoi_KetQua, tv_NVKD;
    int day, month, year;
    private ProgressDialog pDialog;
    Controller control;
    ConvertFont cf = new ConvertFont();
    int totalPage = 0, currentPage = 0, totalMoiTao = 0, totalPageBC = 0,
            currentPageBC = 0, pageCount = 0;
    List<DonHang> listDonHang;
    List<String> listNVKD;
    List<BCTongHop> listBCTongHop;
    List<NVKD> mList;
    String idNVKD;

    List<Rpt_Thongkechung_Model> mListRptThongkechung;
    Rpt_Thongkechung_Adapter adapterThongkechung = null;

    String startDate;
    String endDate;
    RadioButton rad, rad1;
    boolean ckrad;
    Boolean showErr;
    LinearLayout linearLayout1;

    public ThongKeChungFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rpt_thongkechung, container,
                false);
        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
        btn_searchTKC = (Button) view.findViewById(R.id.btn_searchRPT1);
        sp_NVKD = (Spinner) view.findViewById(R.id.sp_NVKD1);
        et_startDateTKC = (EditText) view.findViewById(R.id.et_startDate1);
        et_endDateTKC = (EditText) view.findViewById(R.id.et_endDate1);
        iv_startDateTKC = (ImageView) view.findViewById(R.id.iv_startDate1);
        iv_endDateTKC = (ImageView) view.findViewById(R.id.iv_endDate1);
        tv_NVKD = (TextView) view.findViewById(R.id.tv_NVKD1);
        rad = (RadioButton) view.findViewById(R.id.radio_Ngay_TKC);
        rad1 = (RadioButton) view.findViewById(R.id.radio_Thang_TKC);
        getDate();

        control = new Controller(getActivity());

        //new DSNhaCungCapTask().execute();
        iv_endDateTKC.setVisibility(View.GONE);
        et_endDateTKC.setVisibility(View.GONE);
        showErr = false;

        //Dat lai lable cho Opt de hien thi cho man hinh dien thoai
        if (mAF.isPhone) {
            rad.setText("Ngày");
            rad1.setText("LKT");
        }

        iv_startDateTKC.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_startDateTKC.getText().toString().length() > 0) {
                    String[] item = et_startDateTKC.getText().toString()
                            .split("/");
                    day = Integer.parseInt(item[0]);
                    month = Integer.parseInt(item[1]) - 1;
                    year = Integer.parseInt(item[2]);
                }
                DialogFragment dialog = new DatePickerFragment(0);
                dialog.show(getFragmentManager(), "DatePicker");
            }
        });

        iv_endDateTKC.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (et_endDateTKC.getText().toString().length() > 0) {
                    String[] item = et_endDateTKC.getText().toString()
                            .split("/");
                    day = Integer.parseInt(item[0]);
                    month = Integer.parseInt(item[1]) - 1;
                    year = Integer.parseInt(item[2]);
                }
                DialogFragment dialog = new DatePickerFragment(1);
                dialog.show(getFragmentManager(), "DatePicker");
            }
        });

        btn_searchTKC.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (linearLayout1.getVisibility() == View.INVISIBLE)
                    linearLayout1.setVisibility(View.VISIBLE);
                idNVKD = "";
                totalPage = currentPage = totalMoiTao = totalPageBC = currentPageBC = 0;
                showErr = false;
                if (control.isOnline(getActivity())) {
                    startDate = et_startDateTKC.getText().toString();
                    endDate = et_endDateTKC.getText().toString();
                    String[] item1;
                    item1 = startDate.split("/");
                    if (item1.length < 3) {
                        control.showAlertDialog(getActivity(), "Thông báo", "Ngày nhập vào không đúng", false);
                        return;
                    } else {
                        if (item1[0].length() < 1 || item1[1].length() < 1 || item1[2].length() < 4) {
                            control.showAlertDialog(getActivity(), "Thông báo", "Ngày nhập vào không đúng", false);
                            return;
                        }
                    }
                    if (rad.isChecked()) {
                        ckrad = true;
                        endDate = startDate;
                    }
                    if (rad1.isChecked()) {
                        ckrad = false;
                        endDate = startDate;
                        startDate = "01/" + item1[1] + "/" + item1[2];
                    }
                    mListRptThongkechung = new ArrayList<Rpt_Thongkechung_Model>();
                    getListView().setAdapter(null);
                    if (get_rpt_thongkechung_task == null || get_rpt_thongkechung_task.getStatus() == AsyncTask.Status.FINISHED) {
                        get_rpt_thongkechung_task = new Get_RPT_Thongkechung_Task();
                        get_rpt_thongkechung_task.execute();
                    }
                }

            }
        });
        tv_NVKD.setVisibility(View.GONE);
        sp_NVKD.setVisibility(View.GONE);
        return view;
    }


    private String getURL_RPT_THONGKECHUNG() {
        pageCount = 4;
        String url = API_code.URL_BC_TONGHOP + "&pageno="
                + pageCount
                + "&pagerec=100&org_id=0"
                + "&assign_id=0"
                + "&json={\"date1\":\"" + startDate + "\""
                + ",\"date2\":\"" + endDate + "\""
                + "}";
        Log.i("cuong URL_BC_TONGHOP", url);
        return url;
    }


    class Get_RPT_Thongkechung_Task extends AsyncTask<Void, List<Rpt_Thongkechung_Model>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getResources().getText(R.string.load_data));
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Void... params) {
            pageCount++;
            if (totalPage == 0) {
                totalPage = 100;
            }
            String url, json_dsbl = "";
            url = getURL_RPT_THONGKECHUNG();
            try {
                json_dsbl = control.jsonValues(url, false);
                //json_dsbl = control.getDataJSON(url, false);  //Khi null hay bi loi
                Log.d("cuong result", json_dsbl);

                if (json_dsbl.contains("api_error")) {
                    showErr = true;
                    mAF.writelog("Error TKC rpt: " + json_dsbl, getActivity(), mAF.filelog);
//					Log.d("cuong error lay dskhchuadathang", "Lỗi lấy số liệu khuyến mại");
                } else {
                    Type listType = new TypeToken<List<Rpt_Thongkechung_Model>>() {
                    }.getType();
                    List<Rpt_Thongkechung_Model> ds_bl = (List<Rpt_Thongkechung_Model>) new Gson().fromJson(json_dsbl, listType);
                    if (ckrad && ds_bl.get(0).isHoliday() == false) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                linearLayout1.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "Ngày được lựa chọn là ngày nghỉ!", Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                    for (int i = 0; i < ds_bl.size(); i++) {
//						mListRptThongkechung.add(ds_bl.get(i));
                        if (ckrad && i == 3) {
                            mListRptThongkechung.add(new Rpt_Thongkechung_Model(ds_bl.get(i).getKpi_target_name(), null, ds_bl.get(i).getValue_current(), null, null));
                        } else {
                            mListRptThongkechung.add(ds_bl.get(i));
                        }
                    }
                }
            } catch (Exception ex) {
                showErr = false;
                mAF.writelog("Error TKC rpt: " + ex.toString(), getActivity(), mAF.filelog);
//				Log.d("cuong error get json data dskhchuadathang", ex.toString());
            }

            publishProgress(mListRptThongkechung);
            return null;
        }

        @Override
        protected void onProgressUpdate(List<Rpt_Thongkechung_Model>... values) {
            super.onProgressUpdate(values);
            if (adapterThongkechung == null) {
                adapterThongkechung = new Rpt_Thongkechung_Adapter(getActivity(), values[0]);
                setListAdapter(adapterThongkechung);
            } else {
                adapterThongkechung = new Rpt_Thongkechung_Adapter(getActivity(), values[0]);
                setListAdapter(adapterThongkechung);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (pDialog != null && pDialog.isShowing())
                dismissWithCheck(pDialog);

            if (showErr) {
                control.showAlertDialog(getActivity(), "Thông báo", "Không có số liệu", false);
            }
            Log.d("cuong", "showErr=" + showErr);

        }

    }

    private class ResultSearchTask extends AsyncTask<DonHangTKC, Void, Void> {

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
                String url = API_code.URL_DS_DONDATHANG + "&pageno=-1&json="
                        + control.subJSON(json);
                NorAndNop n = control.getNorNop(url);
                totalPage = Integer.parseInt(n.getNop());

                if (totalPage == 0) {
                    return null;
                }
            }

            String url = API_code.URL_DS_DONDATHANG + "&pageno=" + currentPage
                    + "&json=" + control.subJSON(json);
            String jsonData = control.getDataJSON(url, false);
            Type listType = new TypeToken<List<DonHang>>() {
            }.getType();
            List<DonHang> listData = new Gson().fromJson(jsonData, listType);
            for (int i = 0; i < listData.size(); i++) {
                if (!listData.get(i).getTrangthai().equals("1")
                        && !listData.get(i).getTrangthai().equals("5")
                        && !listData.get(i).getTrangthai().equals("9")) {
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
            if (listDonHang.size() > 0) {
                int idoanhso = 0;
                for (int i = 0; i < listDonHang.size(); i++) {
                    String tien = listDonHang.get(i).getTongtien().replace(",", "");
                    int tien_donhang = Integer.parseInt(tien);
                    idoanhso += tien_donhang;
                }
                DecimalFormat formatterPrice = new DecimalFormat("#,###,###");
                String doanhso = formatterPrice.format(Long.parseLong(String
                        .valueOf(idoanhso)));
                tv_DoanhSo_KetQua.setText(doanhso);
                tv_NangSuat_KetQua.setText("" + listDonHang.size());
            }
        }
    }

    private class BCTongHopTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            currentPageBC++;
            if (totalPageBC == 0) {
                String url = API_code.URL_BC_TONGHOP + "&pageno=-1";
                NorAndNop n = control.getNorNop(url);
                totalPageBC = Integer.parseInt(n.getNop());
                if (totalPageBC == 0) {
                    return null;
                }
            }

            String url = API_code.URL_BC_TONGHOP + "&pageno=" + currentPageBC
                    + "&json={\"nvkd\":\"" + params[0] + "\",\"bd\":"
                    + params[1] + ",\"kt\":" + params[2] + "}";
            String jsonData = control.getDataJSON(url, false);
            Type listType = new TypeToken<List<BCTongHop>>() {
            }.getType();
            List<BCTongHop> listData = new Gson().fromJson(jsonData, listType);
            for (int i = 0; i < listData.size(); i++) {
                listBCTongHop.add(listData.get(i));
            }
            if (currentPageBC < totalPageBC) {
                doInBackground(params);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            pDialog.dismiss();
            if (listBCTongHop.size() > 0) {
                if (sp_NVKD.getSelectedItemPosition() == 0) {
                    int khmoi = 0;
                    int checkin = 0;
                    for (int i = 0; i < listBCTongHop.size(); i++) {
                        if (!listBCTongHop.get(i).no_new.equals("")) {
                            khmoi += Integer
                                    .parseInt(listBCTongHop.get(i).no_new);
                        }
                        if (!listBCTongHop.get(i).no_checkin.equals("")) {
                            checkin += Integer
                                    .parseInt(listBCTongHop.get(i).no_checkin);

                        }
                    }
                    tv_SoKHghetham_KetQua.setText("" + checkin);
                    tv_soKHmoi_KetQua.setText("" + khmoi);
                } else {
                    for (int i = 0; i < listBCTongHop.size(); i++) {
                        String s = idNVKD;
                        String s1 = listBCTongHop.get(i).seller;
                        if (s.equals(s1)) {
                            tv_SoKHghetham_KetQua
                                    .setText(listBCTongHop.get(i).no_checkin);
                            tv_soKHmoi_KetQua
                                    .setText(listBCTongHop.get(i).no_new);
                        }
                    }
                }
            }
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
            mList = new Gson().fromJson(result, listType);
            listNVKD.add("Tất cả");
            for (int i = 0; i < mList.size(); i++) {
                listNVKD.add(cf.getUTF8StringFromNCR(mList.get(i).user_name));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(), android.R.layout.simple_spinner_item,
                    listNVKD);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            sp_NVKD.setAdapter(adapter);
        }
    }

    private void getDate() {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);
        et_startDateTKC.setText(new StringBuilder().append(day).append("/")
                .append(month + 1).append("/").append(year));
        et_endDateTKC.setText(new StringBuilder().append(day).append("/")
                .append(month + 1).append("/").append(year));
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
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
        public void onDateSet(DatePicker view, int selectyear, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            year = selectyear;
            month = monthOfYear;
            day = dayOfMonth;
            switch (id) {
                case 0:
                    et_startDateTKC
                            .setText(new StringBuilder().append(day).append("/")
                                    .append(month + 1).append("/").append(year));
                    break;
                case 1:
                    et_endDateTKC
                            .setText(new StringBuilder().append(day).append("/")
                                    .append(month + 1).append("/").append(year));
                    break;
                default:
                    break;
            }
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
