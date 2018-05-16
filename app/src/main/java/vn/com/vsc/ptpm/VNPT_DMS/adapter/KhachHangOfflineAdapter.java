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
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.TenKH;

public class KhachHangOfflineAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<TenKH> mArrayListKhachHang;

	public KhachHangOfflineAdapter(Context context, ArrayList<TenKH> data) {
		this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mArrayListKhachHang = data;
	}

	@Override
	public int getCount() {
		if (mArrayListKhachHang.size() > 0)
			return mArrayListKhachHang.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if (mArrayListKhachHang.size() > 0)
			return mArrayListKhachHang.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHoler;

		if (convertView == null) {
			viewHoler = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.item_list_detail, null);

			// Initialize controls
			viewHoler.mId = (TextView) convertView.findViewById(R.id.id);
			viewHoler.mName =  (TextView) convertView.findViewById(R.id.name);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		TenKH item = mArrayListKhachHang.get(position);
		if (item != null) {
			viewHoler.mId.setText("" + (position + 1));
			viewHoler.mName.setText(ConvertFont.getUTF8StringFromNCR(item.getName() + " - " + item.getId()));
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mId = null;
		private TextView mName = null;
	}
}
