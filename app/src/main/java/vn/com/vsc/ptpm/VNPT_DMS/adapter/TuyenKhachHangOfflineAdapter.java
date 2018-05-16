package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

public class TuyenKhachHangOfflineAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<KhachHang> mArrayListData;
	private ConvertFont convertFont = new ConvertFont();

	public TuyenKhachHangOfflineAdapter(Context context, ArrayList<KhachHang> data) {
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

	@SuppressWarnings("static-access")
	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHoler;

		if (convertView == null) {
			viewHoler = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_list_hh_detail, null);

			// Initialize controls
			viewHoler.mSTT = (TextView) convertView.findViewById(R.id.tv_stt);
			viewHoler.mName =  (TextView) convertView.findViewById(R.id.tv_name);
			viewHoler.mAddress =  (TextView) convertView.findViewById(R.id.tv_gia);
			viewHoler.mStatus =  (TextView) convertView.findViewById(R.id.tv_tonkho);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		KhachHang item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mSTT.setText("" + (position + 1));
			viewHoler.mName.setText(convertFont.getUTF8StringFromNCR(item.getCode()) + " - "
					+ convertFont.getUTF8StringFromNCR(item.getName()) + " - " + item.getAssign_id());
			viewHoler.mAddress.setText("Địa chỉ: " + convertFont.getUTF8StringFromNCR(item.getAddress()));
			viewHoler.mStatus.setText("Trạng thái: " + getValues_TT(item.getStatus()));
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mSTT = null;
		private TextView mName = null;
		private TextView mAddress = null;
		private TextView mStatus = null;
	}

	private String getValues_TT(String status) {
		String result = "";
		if (status != null) {
			if (status.equals("HT")) {
				result = "Đã ghé thăm";
			} else if (status.equals("DH")) {
				result = "Đã đặt hàng";
			} else if (status.equals("")) {
				result = "Chưa ghé thăm";
			}
			Log.i("result", result);
		}
		return result;
	}
}
