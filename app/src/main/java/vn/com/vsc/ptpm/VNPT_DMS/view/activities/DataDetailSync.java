package vn.com.vsc.ptpm.VNPT_DMS.view.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HistoryCheckinAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.HistoryLocationAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.KhachHangNewOfflineAdapter;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.dao.CheckinDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.InfoDeviceDAL;
import vn.com.vsc.ptpm.VNPT_DMS.dao.KhachhangMoiDAL;
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;
import vn.com.vsc.ptpm.VNPT_DMS.entity.KhachHangMoiEntity;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;

public class DataDetailSync extends Activity {

	private ListView mListView;
	private int position;
	
	private ArrayList<KhachHangMoiEntity> glstKhachHangNew;
	private ArrayList<InfoDeviceEntity> glstInfo;
	private ArrayList<Checkin> glstCheckin;
	
	private KhachHangNewOfflineAdapter khachHangNewOfflineAdaper;
	private HistoryLocationAdapter historyLocationAdaper;
	private HistoryCheckinAdapter historyCheckinAdapter;

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
		position = intentData.getIntExtra(Config.KEY_EXTRA_TYPE_DATA_SYNC, 0);

		switch (position) {
		case 0: // Danh sach Khach hang moi
			getActionBar().setTitle("Danh sách Khách Hàng mới");
			glstKhachHangNew = new KhachhangMoiDAL(this).getAll();
			if (glstKhachHangNew.size() > 0) {
				khachHangNewOfflineAdaper = new KhachHangNewOfflineAdapter(this, glstKhachHangNew);
				mListView.setAdapter(khachHangNewOfflineAdaper);
				mListView.setVisibility(View.VISIBLE);
				registerForContextMenu(mListView);
			}

			break;
		case 1: // Danh sach Don hang moi
			getActionBar().setTitle("Danh sách Đơn hàng mới");
			/*ArrayList<NhaCungCapEntity> glstNhaCungCap = new NhaCungCapDAL(this).getAll();
			if (glstNhaCungCap.size() > 0) {
				NhaCungCapOfflineAdapter nhaCungCapAdaper = new NhaCungCapOfflineAdapter(this, glstNhaCungCap);
				mListView.setAdapter(nhaCungCapAdaper);
				mListView.setVisibility(View.VISIBLE);
			}*/

			break;
		case 2: // Lich su vi tri
			getActionBar().setTitle("Lịch sử vị trí");
			glstInfo = new InfoDeviceDAL(this).getAll();
			if (glstInfo.size() > 0) {
				historyLocationAdaper = new HistoryLocationAdapter(this, glstInfo);
				mListView.setAdapter(historyLocationAdaper);
				mListView.setVisibility(View.VISIBLE);
				registerForContextMenu(mListView);
			}
			break;
		case 3: // Lich su checkin/ checkout
			getActionBar().setTitle("Lịch sử Checkin/Checkout");
			glstCheckin = new CheckinDAL(this).getAll();
			if (glstCheckin.size() > 0) {
				historyCheckinAdapter = new HistoryCheckinAdapter(this, glstCheckin);
				mListView.setAdapter(historyCheckinAdapter);
				mListView.setVisibility(View.VISIBLE);
				registerForContextMenu(mListView);
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.list_view_detail) {
			menu.setHeaderTitle("Tùy chọn");
			String[] menuItems = getResources().getStringArray(R.array.list_item_context_menu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Get Action Menu: 0: Delete, 1: Edit, 2:Send
		int menuItemIndex = item.getItemId();
		//String[] menuItems = getResources().getStringArray(R.array.list_item_context_menu);
		//String menuItemName = menuItems[menuItemIndex];
		
		// Get index List Data
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int index = info.position;
		
		switch (position) {
		case 0: // Khach hang moi
			if (menuItemIndex == 0) { // Delete
				if (glstKhachHangNew.size() > 0) {

					int id = glstKhachHangNew.get(index).getId();
					int resultDelete = new KhachhangMoiDAL(this).delete(String.valueOf(id));
					if (resultDelete > 0) {
						khachHangNewOfflineAdaper.removeItem(index);
						Toast.makeText(this, "Xóa khách hàng thành công!", Toast.LENGTH_SHORT).show(); 
					} else {
						Toast.makeText(this, "Xóa khách hàng không thành công!", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
			
		case 2: // History Location
			if (menuItemIndex == 0) { // Delete
				if (glstInfo.size() > 0) {

					String time = glstInfo.get(index).getTime();
					int resultDelete = new InfoDeviceDAL(this).delete(time);
					if (resultDelete > 0) {
						historyLocationAdaper.removeItem(index);
						Toast.makeText(this, "Xóa vị trí thành công!", Toast.LENGTH_SHORT).show(); 
					} else {
						Toast.makeText(this, "Xóa vị trí không thành công!", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
			
		case 3: // History Checkin/out
			if (menuItemIndex == 0) { // Delete
				if (glstCheckin.size() > 0) {

					String time = glstCheckin.get(index).getTime();
					int resultDelete = new CheckinDAL(this).delete(time);
					if (resultDelete > 0) {
						historyCheckinAdapter.removeItem(index);
						Toast.makeText(this, "Xóa thông tin checkin/out thành công!", Toast.LENGTH_SHORT).show(); 
					} else {
						Toast.makeText(this, "Xóa thông tin checkin/out không thành công!", Toast.LENGTH_SHORT).show();
					}
				}
			}
			break;
		default:
			break;
		}
		
		
		return super.onContextItemSelected(item);
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
