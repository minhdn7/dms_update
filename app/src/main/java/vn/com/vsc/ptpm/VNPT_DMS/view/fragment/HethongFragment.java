package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.NetworkType;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.dao.BinhLuanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.CheckinDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DatHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDeleteDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HinhThucThanhToanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.InfoDeviceDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhaoSatDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.NhaCungCapDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.SanPhamDonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TTKHDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.ThongTinKhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TrangThaiDonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenKhachHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TypeCompanyDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.UpdateLocationDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.DonHangDeleteEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.SanPhamDonHangEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BinhLuan;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangParam;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ThemKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.UpdateGPSKH;

public class HethongFragment extends Fragment {

    private Controller control;
    private EditText et_mk_cu, et_mk_moi, et_mk_xacnhan;
    private ProgressDialog pDialog;

    private TextView tvStatusSync, tvStatusGetDataOffline;
    //	private EditText et_time_get_location;
    private Button btn_Update, btnSyncAll, btnGetDataOffline;

    private DoiMatKhauTask doiMatKhauTask;
    private SyncAllDataTask syncAllDataTask;
    private GetDataOfflineTask getDataOfflineTask;
    private Activity activity;

    public HethongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hethong, container, false);
        // init UI
        et_mk_cu = (EditText) view.findViewById(R.id.et_MatKhauCu);
        et_mk_moi = (EditText) view.findViewById(R.id.et_MatKhauMoi);
        et_mk_xacnhan = (EditText) view.findViewById(R.id.et_XacNhan);
//		et_time_get_location = (EditText) view.findViewById(R.id.edit_text_time_get_location);
        tvStatusSync = (TextView) view.findViewById(R.id.text_view_status);
        tvStatusGetDataOffline = (TextView) view.findViewById(R.id.text_view_status1);

        btn_Update = (Button) view.findViewById(R.id.btn_XNDoiMauKau);
//		btnUpdateTimeLocation = (Button) view.findViewById(R.id.btn_update_time_get_location);
        btnSyncAll = (Button) view.findViewById(R.id.btn_sync_all);
        btnGetDataOffline = (Button) view.findViewById(R.id.btn_get_data_offline);

        // init Listener
        btn_Update.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (control.isOnline(activity)) {
                    if (et_mk_moi.getText().toString().equals(et_mk_xacnhan.getText().toString())) {
                        if (doiMatKhauTask == null || doiMatKhauTask.getStatus() == AsyncTask.Status.FINISHED) {
                            doiMatKhauTask = new DoiMatKhauTask();
                            doiMatKhauTask.execute();
                        }
                    } else {
                        control.showAlertDialog(
                                activity,
                                "Error",
                                "Mật khẩu mới không hợp lệ ! \nHãy xác nhận lại mật khẩu mới ",
                                false);
                        et_mk_xacnhan.setText("");
                    }
                } else {
                    control.showAlertDialog(activity, "Error", "Không có kết nối internet ! ", false);
                }
            }
        });

//		btnUpdateTimeLocation.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (et_time_get_location.getText().toString().equals("")) {
//					control.showAlertDialog(
//							activity,
//							"Error",
//							"Thời gian gửi thông tin vị trí về Server không được để trống!",
//							false);
//					et_time_get_location.requestFocus();
//				} else {
//					int data = Integer.parseInt(et_time_get_location.getText().toString());
//					boolean result = saveTimeGetLocation(data);
//					if (result) {
//						Toast.makeText(activity, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//						//et_time_get_location.setSelected(false);
//						et_time_get_location.clearFocus();
//
//						// Start Service update location
//						Intent service = new Intent(activity, LocationService.class);
//						activity.stopService(service);
//
//						activity.startService(service);
//					} else {
//						Toast.makeText(activity, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//		});

        btnSyncAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NetworkType.internetIsAvailable(activity)) {
                    if (syncAllDataTask == null || syncAllDataTask.getStatus() == AsyncTask.Status.FINISHED) {
                        syncAllDataTask = new SyncAllDataTask();
                        syncAllDataTask.execute();
                    }
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Không thể đồng bộ khi không có internet", false);
                }
            }
        });

        btnGetDataOffline.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NetworkType.internetIsAvailable(activity)) {
                    if (getDataOfflineTask == null || getDataOfflineTask.getStatus() == AsyncTask.Status.FINISHED) {
                        getDataOfflineTask = new GetDataOfflineTask();
                        getDataOfflineTask.execute();
                    }
                } else {
                    control.showAlertDialog(activity, "Thông báo", "Không thể lấy dữ liệu Offline khi không có internet", false);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Get time location
//		et_time_get_location.setText("" + getTimeGetLocation());
        control = new Controller(activity);
        getStatusUploadDataSync();
        getStatusDownloadDataOffline();
    }

    private class DoiMatKhauTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Updating ...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        String txtMKcu = et_mk_cu.getText().toString();
        String txtMKmoi = et_mk_moi.getText().toString();

        @Override
        protected String doInBackground(Void... params) {
            String url = API_code.URL_DMK + "&matkhau_cu="
                    + txtMKcu + "&matkhau_moi="
                    + txtMKmoi;
            String result = control.getDataJSON(url, true);
            try {
                result = new JSONObject(result).getString("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dismissWithCheck(pDialog);
            if (result.equals("0")) {
                control.showAlertDialog(activity, "Success", "Cập nhật mật khẩu thành công ! ", true);
            } else {
                control.showAlertDialog(activity, "Error", result, false);
            }
        }
    }

    private boolean saveTimeGetLocation(int data) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Config.KEY_TIME_GET_LOCATION);
        editor.putInt(Config.KEY_TIME_GET_LOCATION, data);

        return editor.commit();
    }

    private int getTimeGetLocation() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        int date = sp.getInt(Config.KEY_TIME_GET_LOCATION, 5);
        return date;
    }

    private void showProgressDialog(String message) {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(message);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                Toast.makeText(activity, R.string.back_again_exit, Toast.LENGTH_SHORT).show();
            }
        });
        pDialog.show();
    }

    private void closeProgressDialog() {
        dismissWithCheck(pDialog);
    }

    ////////////////////////ASYNTASK /////////////////////////////
    private class SyncAllDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Đang đồng bộ dữ liệu. Vui lòng chờ trong giây lát..");
        }

        @Override
        protected Void doInBackground(Void... params) {
            syncKhachHangNew();
            syncKhachHangUpdate();
            syncHistoryLocation();
            syncInfoCheckin();
            syncUpdateLocation();
            syncDonHang();
            syncKhaoSat();
            syncBinhLuan();
            syncDonHangDelete();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            closeProgressDialog();

            saveDateUploadDataSync();
            getStatusUploadDataSync();
        }
    }

    private void syncKhachHangNew() {
        try {
            ArrayList<ThemKH> glstKhachHangMoi = new ThongTinKhachhangDAL(activity).getByType(0);
            if (glstKhachHangMoi.size() > 0) {
                for (int i = 0; i < glstKhachHangMoi.size(); i++) {
                    ThemKH item = glstKhachHangMoi.get(i);
                    String result = null;

                    try {
                        String jsonKH = new Gson().toJson(item);
                        String url = API_code.URL_THEMKH + "&note=" + item.getNote() + "&json=" + jsonKH + "";
                        Log.i("URL-THEM-KH-NEW", url);

                        //Tam bo
                        //String json = control.jsonValues(url, true);

                        //cuongtm sửa ngày 20/5/2016, phải convert url thì mới GET được ???
                        String json = control.getDataJSON(control.convertURL(url), true);


                        result = new JSONObject(json).getString("result");

                        String[] res = result.split("\\|");
                        if (result != null && res[0].equals("OK")) {
                            Log.i("SYNC-KHACH-HANG-NEW", "OK");
                            String id = res[1];
                            Log.i("ID-KHACH-HANG-NEW", "ID = " + id);

                            // Update khachhangId trong don hang offline
                            String idOffline = "offline" + item.get_id();
                            DatHang dathangOffline = new DatHangDAL(activity).getByDVDH(idOffline);
                            if (dathangOffline != null) {
                                dathangOffline.setDvdh(id);

                                long resultUpdate = new DatHangDAL(activity).update(dathangOffline);

                                // Delete tenKH
                                int deleteTenKH = new KhachhangDAL(activity).delete(idOffline);
                                Log.i("DELETE-TEN-KHACH-HANG", deleteTenKH > 0 ? "Success" : "Failure");
                            }

                            // Delete khachhang moi
                            int delete = new ThongTinKhachhangDAL(activity).delete(String.valueOf(item.get_id()));
                            Log.i("DELETE-KHACH-HANG-NEW", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("SYNC-KHACH-HANG-NEW", "ERROR: " + result);
                        }

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncKhachHangUpdate() {
        try {
            ArrayList<ThemKH> glstKhachHangUpdate = new ThongTinKhachhangDAL(activity).getByType(1);
            if (glstKhachHangUpdate.size() > 0) {
                for (int i = 0; i < glstKhachHangUpdate.size(); i++) {
                    ThemKH item = glstKhachHangUpdate.get(i);
                    String result = null;

                    try {
                        String jsonKH = new Gson().toJson(item);
                        String url = API_code.URL_UPDATE_KH + "&note=" + item.getNote() + "&json=" + jsonKH + "";
                        Log.i("URL-UPDATE-KH", url);

                        //Tạm bỏ
                        //String json = control.getDataJSON(url, true);

                        //cuongtm sửa ngày 20/5/2016, phải convert url thì mới GET được ???
                        String json = control.getDataJSON(control.convertURL(url), true);

                        result = new JSONObject(json).getString("result");

                        if (result.equals("0")) {
                            Log.i("SYNC-KHACH-HANG-UPDATE", "OK");

                            int delete = new ThongTinKhachhangDAL(activity).delete(String.valueOf(item.get_id()));
//							Log.i("DELETE-KHACH-HANG-UPDATE", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("SYNC-KHACH-HANG-UPDATE", "ERROR");
                        }

                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncHistoryLocation() {
        try {
            ArrayList<InfoDeviceEntity> glstInfo = new InfoDeviceDAL(activity).getAll();
            if (glstInfo.size() > 0) {
                for (int i = 0; i < glstInfo.size(); i++) {
                    InfoDeviceEntity item = glstInfo.get(i);
                    String result = null;

                    // Send data location to Server
                    try {
                        String jsonLocation = new Gson().toJson(item);
                        String url = Config.URL_UPDATE_LOCATION + "&json=" + jsonLocation + "";
                        Log.i("URL-HISTORY-LOCATION", url);

                        String json = control.getDataJSON(control.convertURL(url), true);
                        result = new JSONObject(json).getString("result");
                        mAF.writelog("Result SynLocationHistory: " + result, activity, mAF.filelog);

                        if (result.equals("0")) {
                            Log.i("STATUS_UPADTE", "Update Location Success");

                            int delete = new InfoDeviceDAL(activity).delete(item.getTime());
                            Log.i("DELETE-HISTORY-LOCATION", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("STATUS_UPADTE", "Update Location Failure");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncInfoCheckin() {
        try {
            ArrayList<Checkin> glstCheckin = new CheckinDAL(activity).getAll();

            Log.i("cuong syn checkin", "" + glstCheckin.size());

            if (glstCheckin.size() > 0) {
                for (int i = 0; i < glstCheckin.size(); i++) {
                    Checkin item = glstCheckin.get(i);
                    String result = null;

                    try {
                        //cuongtm test ok 20/5/2016
                        //Lệnh này trả về dữ liệu kiểu json
                        String jsonCheckin = new Gson().toJson(item);

                        //cuongtm sửa ngày 20/05/2016, chuyển kiểu dữ liệu json cho phù hợp
                        /*
                        String jsonCheckin = "%7B"
						+ "%22id%22:%22"
						+ item.getId()
						+ "%22" 								
						
						+ ",%22code%22:%22"
						+ item.getCode()
						+ "%22" 
						
						+ ",%22assign%22:%22"
						+ item.getAssign()
						+ "%22"
						
						+ ",%22lng%22:%22"
						+ item.getLng()
						+ "%22"
						
						+ ",%22lat%22:%22"
						+ item.getLat()
						+ "%22"
						
						+ ",%22checktype%22:%22"
						+ item.getChecktype()
						+ "%22"

						+ ",%22time%22:%22"
						+ item.getTime()
						+ "%22"						
						+ "%7D";
						*/


                        String url = Config.GetUrlCheckin(jsonCheckin);
                        Log.i("cuong url syn checkin", url);

                        //cuongtm tạm bỏ sử dụng dòng bên dưới
                        //result = new JSONObject(control.jsonValues(url, true)).getString("result");

                        result = control.getDataJSON(control.convertURL(url), true);
//						Log.i("cuong kq syn result value", result);

                        result = new JSONObject(result).getString("result");
                        Log.i("cuong kq syn location", result);

                        mAF.writelog("Result Syn Checkin: " + result, activity, mAF.filelog);
                        if (result.equals("0")) {
                            Log.i("STATUS-CHECKIN", "Update info checkin success");

                            int delete = new CheckinDAL(activity).delete(item.getTime());
                            Log.i("DELETE-INFO-CHECKIN", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("STATUS-CHECKIN", "Update info checkin failure");

                            //cuongtm đưa vào để xóa luôn dữ liệu test
                            //int delete = new CheckinDAL(activity).delete(item.getTime());
                            //Log.i("DELETE-INFO-CHECKIN", delete>0?"Success":"Failure");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncUpdateLocation() {
        try {
            ArrayList<UpdateGPSKH> glstUpdateLocation = new UpdateLocationDAL(activity).getAll();
            if (glstUpdateLocation.size() > 0) {
                for (int i = 0; i < glstUpdateLocation.size(); i++) {
                    UpdateGPSKH item = glstUpdateLocation.get(i);
                    String result = null;

                    try {
                        String jsonUpdate = new Gson().toJson(item);
                        String url = API_code.URL_UPDATE_NEW_KH + "&json=" + jsonUpdate + "";
                        Log.i("URL-UPDATE-LOCATION", url);

                        result = new JSONObject(control.getDataJSON(control.convertURL(url), true)).getString("result");
                        if (result.equals("0")) {
                            Log.i("STATUS-UPDATE-LOCATION", "Update location success");

                            int delete = new UpdateLocationDAL(activity).delete(item.getTime());
                            Log.i("DELETE-UPDATE-LOCATION", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("STATUS-UPDATE-LOCATION", "Update location failure");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncDonHang() {
        try {
            ArrayList<DatHang> glstDonHang = new DatHangDAL(activity).getAll();
            if (glstDonHang.size() > 0) {
                for (int i = 0; i < glstDonHang.size(); i++) {
                    DatHang item = glstDonHang.get(i);
                    // Gui don hang truoc
                    String json_result = "";
                    json_result = control.jsonValues(control.convertURL(Config.getUrlAddDonHang(item)), false);

                    JSONObject emp;
                    String empname = "";
                    try {
                        emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                        empname = emp.optString("result");
                        Log.i("json_tbl", empname);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (empname.equals("")) {
                        break;
                    } else {
                        String po_id = empname.substring(4, empname.length());
                        String id = String.valueOf(item.get_id());

                        SanPhamDonHangDAL sanphamdonhangDal = new SanPhamDonHangDAL(activity);
                        ArrayList<SanPhamDonHangEntity> glstSPDH = sanphamdonhangDal.getListById(id);

                        if (glstSPDH.size() > 0) {
                            Log.i("LIST-SAN-PHAM-ITEM", "" + glstSPDH.size());

                            int sosanpham = 0;
                            for (int j = 0; j < glstSPDH.size(); j++) {
                                SanPhamDonHangEntity sp = glstSPDH.get(j);

                                String url = Config.getUrlCNDH(po_id, sp.getLoaisp_id(), sp.getSoluong(), sp.getGia());
                                json_result = control.jsonValues(control.convertURL(url), false);
                                JSONObject emp2;
                                String empname2 = "";

                                try {
                                    emp2 = new JSONObject(json_result.substring(1, json_result.length() - 1));
                                    empname2 = emp2.optString("result");
                                    Log.i("json_tbl", empname2);

                                    if (empname2.equals("")) {
                                        Log.i("CAP-NHAT-DON-HANG-ITEM", "Failure");
                                    } else {
                                        Log.i("CAP-NHAT-DON-HANG-ITEM", "Success");
                                        sosanpham++;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (sosanpham == glstSPDH.size()) {
                                // Them don hang thanh cong
                                Log.e("CAP-NHAT-DON-HANG", "Success");

                                // Gui don hang
                                String url = Config.getUrlGuiNCC(po_id);
                                Log.i("URL-GUI-DON-HANG", url);
                                json_result = control.jsonValues(control.convertURL(url), false);

                                try {
                                    emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                                    empname = emp.optString("result");
                                    Log.i("json_tbl", empname);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (empname.equals("TRUE")) {
                                    Log.i("STATUS-GUI-DON-HANG", "Gửi đơn hàng thành công đến nhà cung cấp");
                                } else {
                                    Log.i("STATUS-GUI-DON-HANG", "Chưa gửi được đến nhà cung cấp");
                                }

                                // delete don hang offline
                                int resultDeleteDH = new DatHangDAL(activity).delete(item.get_id());
                                Log.i("DELETE-DAT-HANG", resultDeleteDH > 0 ? "Success" : "Failure");

                                for (int j = 0; j < glstSPDH.size(); j++) {
                                    int resultDelete = sanphamdonhangDal.delete(glstSPDH.get(j).get_id());
                                    Log.i("DELETE-SPDH", resultDelete > 0 ? "Success" : "Failure");
                                }

                                int deleteResult = new DonHangDAL(activity).delete("" + item.get_id());
                                Log.i("DELETE-DON-HANG", deleteResult > 0 ? "Success" : "Failure");

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void syncBinhLuan() {
        try {
            ArrayList<BinhLuan> glstCheckin = new BinhLuanDAL(activity).getByStatus(1);
            if (glstCheckin.size() > 0) {
                for (int i = 0; i < glstCheckin.size(); i++) {
                    BinhLuan item = glstCheckin.get(i);

                    try {
                        String url = Config.GuiBinhLuan(item.getOrg_id(), item.getAssign_id(), item.getNote());
                        String json_result = control.jsonValues(control.convertURL(url), false);
                        JSONObject emp;
                        String empname = "";
                        try {
                            emp = new JSONObject(json_result.substring(1, json_result.length() - 1));
                            empname = emp.optString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (empname.equals("0")) {
                            Log.i("STATUS-GUI-BINH-LUAN", "Send comment success");

                            int delete = new BinhLuanDAL(activity).delete(item.getId());
                            Log.i("DELETE-BINH-LUAN", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("STATUS-GUI-BINH-LUAN", "Send comment failure");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("static-access")
    private void syncKhaoSat() {
        try {
            ArrayList<KhachHang> glstTuyenKH = new TuyenKhachHangDAL(activity).getAll();
            if (glstTuyenKH.size() > 0) {

                KhaoSatDAL khaosatDal = new KhaoSatDAL(activity);
                for (KhachHang item : glstTuyenKH) {
                    ArrayList<KhaoSat> glstKhaoSat = khaosatDal.getByStatus(item.getId(), item.getCode(), item.getAssign_id(), 1);

                    if (glstKhaoSat.size() > 0) {

                        ConvertFont conv = new ConvertFont();
                        String a[] = new String[glstKhaoSat.size()];

                        for (int i = 0; i < glstKhaoSat.size(); i++) {
                            KhaoSat ks = glstKhaoSat.get(i);

                            if (ks.getComponent_type().equals("select")) {
                                String data = conv.getUTF8StringFromNCR(ks.getJson_default_value());
                                final String[] arrData = data.split("\\|");
                                if (arrData.length > 0) {
                                    for (int j = 0; j < arrData.length; j++) {
                                        String[] arr = arrData[j].toString().split(";");
                                        if (arr.length > 0) {
                                            if (ks.getValue().equals(arr[0].toString().trim())) {
                                                a[i] = arrData[j].toString().trim();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            if (ks.getComponent_type().equals("checkbox")) {
                                a[i] = ks.getValue();
                            }

                            if (ks.getComponent_type().equals("text")) {
                                a[i] = ks.getValue();
                            }
                        }

                        String url = Config.CapNhatKhaoSat(item.getId(), item.getCode(), item.getAssign_id(), glstKhaoSat, a);
                        String json_send = control.jsonValues(url, false);
                        // loai bo dau ngoac vuong:
                        String respon = json_send.substring(1, json_send.length() - 1);

                        JSONObject obj = null;
                        String result = "";
                        try {
                            obj = new JSONObject(respon);
                            result = obj.optString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result.equals("0")) {
//							Log.i("STATUS-CAP-NHAT-KHAO-SAT", "Update Khao sat success");

                            // Update trang thai da cap nhat
                            for (KhaoSat ks : glstKhaoSat) {
                                ks.setStatus(0);
                                int resultUpdate = khaosatDal.update(ks);
                                Log.i("UPDATE-KHAO-SAT-ITEM", resultUpdate > 0 ? "Success" : "Failure");
                            }
                        } else {
//							Log.i("STATUS-CAP-NHAT-KHAO-SAT", "Update Khao sat failure");
                        }
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void syncDonHangDelete() {
        try {
            ArrayList<DonHangDeleteEntity> glstDonHangDelete = new DonHangDeleteDAL(activity).getAll();
            if (glstDonHangDelete.size() > 0) {
                for (int i = 0; i < glstDonHangDelete.size(); i++) {
                    DonHangDeleteEntity item = glstDonHangDelete.get(i);

                    try {
                        String result = "";
                        String url = Config.DeleteDonHang(item.getDonhangId());
                        String json_result = control.jsonValues(control.convertURL(url), true);
                        if (!json_result.equals("")) {
                            result = new JSONObject(json_result).getString("result");
                        }

                        if (result.equals("TRUE")) {
                            Log.i("STATUS-DELETE-DON-HANG", "Delete Don hang success");

                            int delete = new DonHangDeleteDAL(activity).delete(item.getId());
                            Log.i("DELETE-DON-HANG", delete > 0 ? "Success" : "Failure");
                        } else {
                            Log.i("STATUS-DELETE-DON-HANG", "Delete Don hang failure");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getStatusUploadDataSync() {
        long date = getDateUploadDataSync();
        if (date > 0) {
            try {
                tvStatusSync.setText("Dữ liệu đã được đồng bộ: " + Utilities.getDate(date, "dd/MM/yyyy HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tvStatusSync.setText("Dữ liệu chưa đồng bộ");
        }
    }

    private boolean saveDateUploadDataSync() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Config.KEY_DATE_UPLOAD_DATA_SYNC);
        editor.putLong(Config.KEY_DATE_UPLOAD_DATA_SYNC, System.currentTimeMillis());

        return editor.commit();
    }

    private long getDateUploadDataSync() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        long date = sp.getLong(Config.KEY_DATE_UPLOAD_DATA_SYNC, 0);
        return date;
    }


    /////////////////////////// ASYNTASK 2 //////////////////////////////////////
    private class GetDataOfflineTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("Đang tải dữ liệu. Vui lòng chờ trong giây lát..");
        }

        @Override
        protected Void doInBackground(Void... params) {
            saveData();
            saveData2();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            closeProgressDialog();
            Toast.makeText(activity, "Tải liệu dữ thành công", Toast.LENGTH_SHORT).show();

            saveDateDownloadDataOffline();
            getStatusDownloadDataOffline();
        }
    }


    private void saveData() {
        try {
            // Delete All Tuyen, TuyenKhachHang
            TuyenDAL tuyenDal = new TuyenDAL(activity);
            tuyenDal.deleteAll();

            new TuyenKhachHangDAL(activity).deleteAll();
            new TTKHDAL(activity).deleteAll();

            // Get Data Tuyen, TuyenKhachHang
            TuyenDAL.saveToDB(activity, Config.URL_GET_LIST_TUYEN_ACTIVE);
            ArrayList<Tuyen> glstTuyen = tuyenDal.getAll();
            if (glstTuyen.size() > 0) {
                for (int i = 0; i < glstTuyen.size(); i++) {
                    String tuyenID = glstTuyen.get(i).getId();
                    String url = Config.GetUrlLDsTuyenKhachHang(tuyenID, 1, "", "");
                    TuyenKhachHangDAL.saveToDB(activity, url);
                }
            }

            // Get TTKH
            new BinhLuanDAL(activity).deleteAll();
            ArrayList<KhachHang> glstTuyenKhachang = new TuyenKhachHangDAL(activity).getAll();
            if (glstTuyenKhachang.size() > 0) {
                for (int i = 0; i < glstTuyenKhachang.size(); i++) {
                    KhachHang item = glstTuyenKhachang.get(i);

                    // Get thong tin khach hang
                    String urlTTKH = Config.GetUrlTTKH(item.getId(), item.getCode(), item.getAssign_id());
                    TTKHDAL.saveToDB(activity, urlTTKH);

                    // Get danh sach Khao sat, Binh luan
                    String urlBinhluan = Config.getUrlBinhLuan(item.getId(), item.getAssign_id());
                    BinhLuanDAL.saveToDB(activity, urlBinhluan, item.getId(), item.getAssign_id());

                    String urlKhaosat = Config.getUrlKhaosat(item.getId(), item.getCode(), item.getAssign_id());
                    KhaoSatDAL.saveToDB(activity, urlKhaosat, item.getId(), item.getCode(), item.getAssign_id());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveData2() {
        try {
            // Du lieu chung
            TypeCompanyDAL.saveToDB(activity, Config.URL_GET_TYPE_COMPANY);
            KhachhangDAL.saveToDB(activity, Config.URL_GET_KHACH_HANG);
            NhaCungCapDAL.saveToDB(activity, Config.URL_GET_NHA_CUNG_CAP);
            TrangThaiDonHangDAL.saveToDB(activity, Config.URL_GET_TRANG_THAI_DON_HANG);
            HinhThucThanhToanDAL.saveToDB(activity, Config.URL_GET_HINH_THUC_THANH_TOAN);
            HangHoaDAL.saveToDB(activity, Config.URL_GET_HANG_HOA_DANH_MUC);

            // Get danh sach Don hang
            new DonHangDAL(activity).deleteAll();
            // Moi lap
            DonHangParam donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // Gui toi NCC
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "3", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // NCC duyet
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "4", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // NCC khong duyet
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "5", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // NCC xuat kho
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "8", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // Da nhan hang
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "9", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

            // Huy don
            donhang = new DonHangParam("", "", "", "", "", "", "", "", "10", "1");
            DonHangDAL.saveToDB(activity, Config.getUrlDanhsachDonhang(donhang, "50"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean saveDateDownloadDataOffline() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE);
        editor.putLong(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE, System.currentTimeMillis());

        return editor.commit();
    }

    private long getDateDownloadDataOffline() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        long date = sp.getLong(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE, 0);
        return date;
    }

    private void getStatusDownloadDataOffline() {
        long date = getDateDownloadDataOffline();
        if (date > 0) {
            try {
                tvStatusGetDataOffline.setText("Dữ liệu được tải xuống: " + Utilities.getDate(date, "dd/MM/yyyy HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            tvStatusGetDataOffline.setText("Không có dữ liệu dưới thiết bị");
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
