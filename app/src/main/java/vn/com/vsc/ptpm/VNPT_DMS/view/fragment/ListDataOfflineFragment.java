package vn.com.vsc.ptpm.VNPT_DMS.view.fragment;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.DataDetailOffline;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.common.Utilities;
import vn.com.vsc.ptpm.VNPT_DMS.dao.DonHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HinhThucThanhToanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.NhaCungCapDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TTKHDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenKhachHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TypeCompanyDAL;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangParam;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;

public class ListDataOfflineFragment extends ListFragment {

	private TextView mTextViewStatus;
	private Button mButtonDownload;
	private DownloadDataTask downloadDataTask;

	private ProgressDialog mProgressDialog;


	public ListDataOfflineFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_data_offline, container, false);

		mTextViewStatus = (TextView) view.findViewById(R.id.text_view_status);
		mButtonDownload = (Button) view.findViewById(R.id.button_download);

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// get data
		String[] listItems = getResources().getStringArray(R.array.list_item_menu_offline);

		// initiate the listadapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
		getListView().setAdapter(adapter);

		// set listener button download
		mButtonDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(downloadDataTask==null||downloadDataTask.getStatus()== AsyncTask.Status.FINISHED){
					downloadDataTask=new DownloadDataTask();
					downloadDataTask.execute();
				}
			}
		});

		getStatusDownloadDataOffline();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//String selectedItem = (String) getListView().getItemAtPosition(position);
		//Toast.makeText(getActivity(), "" + selectedItem, Toast.LENGTH_SHORT).show();

		// Position: 
		// 0 - Khach hang, 1 - Nha cung cap, 2 - Hinh thuc thanh toan
		// 3 - Kieu kinh doanh, 4 - Hang hoa, 5 - Tuyen, 6 - Tuyen Khach hang
		// 7 - Thong tin Khach Hang

		Intent intentDetail = new Intent(getActivity(), DataDetailOffline.class);
		intentDetail.putExtra(Config.KEY_EXTRA_TYPE_DATA_OFFLINE, position);
		startActivity(intentDetail);
	}

	private void getStatusDownloadDataOffline() {
		long date = getDateDownloadDataOffline();
		if (date > 0) {
			try {
				mTextViewStatus.setText("Dữ liệu được tải xuống: " + Utilities.getDate(date, "dd/MM/yyyy HH:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			mTextViewStatus.setText("Không có dữ liệu dưới thiết bị");
		}
	}

	private void showProgressDialog() {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setMessage(getResources().getString(R.string.load_data));
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCanceledOnTouchOutside(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				Toast.makeText(getActivity(), R.string.back_again_exit, Toast.LENGTH_SHORT).show();
			}
		});
		mProgressDialog.show();
	}

	private void closeProgressDialog() {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}


	private boolean saveDateDownloadDataOffline() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE);
		editor.putLong(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE, System.currentTimeMillis());

		return editor.commit();
	}

	private long getDateDownloadDataOffline() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
		long date = sp.getLong(Config.KEY_DATE_DOWNLOAD_DATA_OFFLINE, 0);
		return date;
	}

	private void saveData() {
		try {
			// Delete All Tuyen, TuyenKhachHang
			TuyenDAL tuyenDal = new TuyenDAL(getActivity());
			tuyenDal.deleteAll();

			new TuyenKhachHangDAL(getActivity()).deleteAll();
			new TTKHDAL(getActivity()).deleteAll();

			// Get Data Tuyen, TuyenKhachHang
			TuyenDAL.saveToDB(getActivity(), Config.URL_GET_LIST_TUYEN_ACTIVE);
			ArrayList<Tuyen> glstTuyen = tuyenDal.getAll();
			if (glstTuyen.size() > 0) {
				for (int i = 0; i < glstTuyen.size(); i++) {
					String tuyenID = glstTuyen.get(i).getId();
					String url = Config.GetUrlLDsTuyenKhachHang(tuyenID, 1, "", "");
					TuyenKhachHangDAL.saveToDB(getActivity(), url);
				}
			}

			// Get TTKH
			ArrayList<KhachHang> glstTuyenKhachang = new TuyenKhachHangDAL(getActivity()).getAll();
			if (glstTuyenKhachang.size() > 0) {
				for (int i = 0; i < glstTuyenKhachang.size(); i++) {
					KhachHang item = glstTuyenKhachang.get(i);
					String urlTTKH = Config.GetUrlTTKH(item.getId(), item.getCode(), item.getAssign_id());
					TTKHDAL.saveToDB(getActivity(), urlTTKH);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveData2() {
		try {
			// Du lieu chung
			TypeCompanyDAL.saveToDB(getActivity(), Config.URL_GET_TYPE_COMPANY);
			KhachhangDAL.saveToDB(getActivity(), Config.URL_GET_KHACH_HANG);
			NhaCungCapDAL.saveToDB(getActivity(), Config.URL_GET_NHA_CUNG_CAP);
			HinhThucThanhToanDAL.saveToDB(getActivity(), Config.URL_GET_HINH_THUC_THANH_TOAN);
			HangHoaDAL.saveToDB(getActivity(), Config.URL_GET_HANG_HOA_DANH_MUC);

			// Get danh sach Don hang
			new DonHangDAL(getActivity());
			// Moi lap
			DonHangParam donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "1");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// Gui toi NCC
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "3");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// NCC duyet
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "4");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// NCC khong duyet
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "5");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// NCC xuat kho
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "8");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// Da nhan hang
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "9");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// Huy don
			donhang = new DonHangParam("", "", "", "", "", "", "", "", "1", "10");
			DonHangDAL.saveToDB(getActivity(), Config.getUrlDanhsachDonhang(donhang, "50"));

			// Get danh sach Khao sat, Binh luan


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////ASYNTASK /////////////////////////////
	private class DownloadDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog();
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
			Toast.makeText(getActivity(), "Tải liệu dữ thành công", Toast.LENGTH_SHORT).show();

			saveDateDownloadDataOffline();
			getStatusDownloadDataOffline();
		}

	}
}
