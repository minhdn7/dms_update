package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HangHoaOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HinhThucThanhToanOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.NhaCungCapOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.TuyenKhachHangOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.TuyenOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.TypeCompanyOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HangHoaDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.HinhThucThanhToanDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.NhaCungCapDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TuyenKhachHangDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.TypeCompanyDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HTTT;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TypeCompany;

public class DataDetailOffline extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_detail_offline);

		initUI();
		getDataIntent();
	}

	private void initUI() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		mListView = (ListView) findViewById(R.id.list_view_detail);
	}

	private void getDataIntent() {
		Intent intentData = getIntent();
		int position = intentData.getIntExtra(Config.KEY_EXTRA_TYPE_DATA_OFFLINE, 0);

		switch (position) {
		case 0: // Danh sach Ten Khach hang
			getActionBar().setTitle("Danh sách Tên Khách Hàng Offline");
			ArrayList<TenKH> glstKhachHang = new KhachhangDAL(this).getAll();
			if (glstKhachHang.size() > 0) {
				KhachHangOfflineAdapter khachHangAdaper = new KhachHangOfflineAdapter(this, glstKhachHang);
				mListView.setAdapter(khachHangAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;
		case 1: // Danh sach Nha cung cap
			getActionBar().setTitle("Danh sách Nhà cung cấp Offline");
			ArrayList<TenKH> glstNhaCungCap = new NhaCungCapDAL(this).getAll();
			if (glstNhaCungCap.size() > 0) {
				NhaCungCapOfflineAdapter nhaCungCapAdaper = new NhaCungCapOfflineAdapter(this, glstNhaCungCap);
				mListView.setAdapter(nhaCungCapAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;
		case 2: // Hinh thuc thanh toan
			getActionBar().setTitle("Hình thức thanh toán Offline");
			ArrayList<HTTT> glstHTTT = new HinhThucThanhToanDAL(this).getAll();
			if (glstHTTT.size() > 0) {
				HinhThucThanhToanOfflineAdapter htttAdaper = new HinhThucThanhToanOfflineAdapter(this, glstHTTT);
				mListView.setAdapter(htttAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;
		case 3: // Kieu kinh doanh
			getActionBar().setTitle("Danh sách Kiểu kinh doanh Offline");
			ArrayList<TypeCompany> glstTypeCompany = new TypeCompanyDAL(this).getAll();
			if (glstTypeCompany.size() > 0) {
				TypeCompanyOfflineAdapter typeCompanyAdaper = new TypeCompanyOfflineAdapter(this, glstTypeCompany);
				mListView.setAdapter(typeCompanyAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;
		case 4: // Danh sach hang hoa
			getActionBar().setTitle("Danh sách Hàng hóa Offline");
			ArrayList<HangHoaEntity> glstHangHoa = new HangHoaDAL(this).getAll();
			if (glstHangHoa.size() > 0) {
				HangHoaOfflineAdapter hangHoaAdaper = new HangHoaOfflineAdapter(this, glstHangHoa);
				mListView.setAdapter(hangHoaAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;

		case 5: // Danh sach tuyen
			getActionBar().setTitle("Danh sách Tuyến Offline");
			ArrayList<Tuyen> glstTuyen = new TuyenDAL(this).getAll();
			if (glstTuyen.size() > 0) {
				TuyenOfflineAdapter tuyenAdaper = new TuyenOfflineAdapter(this, glstTuyen);
				mListView.setAdapter(tuyenAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;

		case 6: // Danh sach tuyen khach hang
			getActionBar().setTitle("Danh sách Tuyến Khách hàng Offline");
			ArrayList<KhachHang> glstTuyenKH = new TuyenKhachHangDAL(this).getAll();
			if (glstTuyenKH.size() > 0) {
				TuyenKhachHangOfflineAdapter tuyenKHAdaper = new TuyenKhachHangOfflineAdapter(this, glstTuyenKH);
				mListView.setAdapter(tuyenKHAdaper);
				mListView.setVisibility(View.VISIBLE);
			}

			break;


		default:
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
