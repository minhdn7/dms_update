package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.entity.KhachHangMoiEntity;

public class KhachHangNewOfflineAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<KhachHangMoiEntity> mArrayListData;
	
	public KhachHangNewOfflineAdapter(Context context, ArrayList<KhachHangMoiEntity> data) {
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mArrayListData = data;
	}
	
	@Override
	public int getCount() {
		if (mArrayListData.size() > 0)
			return mArrayListData.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mArrayListData.size() > 0)
			return mArrayListData.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void removeItem(int index) {
		mArrayListData.remove(index);
		notifyDataSetChanged();
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHoler;
		
		if (convertView == null) {
			viewHoler = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_list_khachhang_new, null);
			
			// Initialize controls
			viewHoler.mNewCode = (TextView) convertView.findViewById(R.id.text_view_new_code);
			viewHoler.mName = (TextView) convertView.findViewById(R.id.text_view_name);
			viewHoler.mManager = (TextView) convertView.findViewById(R.id.text_view_manager);
			viewHoler.mMobile = (TextView) convertView.findViewById(R.id.text_view_mobile);
			viewHoler.mFax = (TextView) convertView.findViewById(R.id.text_view_fax);
			viewHoler.mEmail = (TextView) convertView.findViewById(R.id.text_view_email);
			viewHoler.mAddress = (TextView) convertView.findViewById(R.id.text_view_address);
			viewHoler.mLocation = (TextView) convertView.findViewById(R.id.text_view_location);
			viewHoler.mTaxCode = (TextView) convertView.findViewById(R.id.text_view_tax_code);
			viewHoler.mType = (TextView) convertView.findViewById(R.id.text_view_type);
			viewHoler.mNote = (TextView) convertView.findViewById(R.id.text_view_note);
			viewHoler.mOrderNow = (TextView) convertView.findViewById(R.id.text_view_order_now);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		KhachHangMoiEntity item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mNewCode.setText("Mã khách hàng: " + item.getNew_code());
			viewHoler.mName.setText("Tên khách hàng: " + item.getName());
			viewHoler.mManager.setText("Người quản lý: " + item.getMgr());
			viewHoler.mMobile.setText("Số điện thoại: " + item.getMobile());
			viewHoler.mFax.setText("Fax: " + item.getFax());
			viewHoler.mEmail.setText("Email: " + item.getEmail());
			viewHoler.mAddress.setText("Địa chỉ: " + item.getAddr());
			viewHoler.mLocation.setText("Tọa độ: " + item.getLattitude() + " : " + item.getLongtitude());
			viewHoler.mTaxCode.setText("Mã số thuế: " + item.getTax());
			viewHoler.mType.setText("Loại kinh doanh: " + item.getEnt_id());
			viewHoler.mNote.setText("Ghi chú: " + item.getNote());
			viewHoler.mOrderNow.setText(item.getOrder_now()==1?"Đặt hàng ngay":"Không đặt hàng ngay");
		}
		
		return convertView;
	}
	
	public static class ViewHolder {
		private TextView mNewCode = null;
		private TextView mName = null;
		private TextView mManager = null;
		private TextView mMobile = null;
		private TextView mFax = null;
		private TextView mEmail = null;
		private TextView mAddress = null;
		private TextView mLocation = null;
		private TextView mTaxCode = null;
		private TextView mType = null;
		private TextView mNote = null;
		private TextView mOrderNow = null;
	}
}
