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
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;

public class HangHoaOfflineAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<HangHoaEntity> mArrayListData;

	public HangHoaOfflineAdapter(Context context, ArrayList<HangHoaEntity> data) {
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
			viewHoler.mGia =  (TextView) convertView.findViewById(R.id.tv_gia);
			viewHoler.mTonKho =  (TextView) convertView.findViewById(R.id.tv_tonkho);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		HangHoaEntity item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mSTT.setText("" + (position + 1));
			viewHoler.mName.setText(ConvertFont.getUTF8StringFromNCR(item.getName()));
			viewHoler.mGia.setText(item.getGia_ban() + " đ/" + ConvertFont.getUTF8StringFromNCR(item.getUnit()));
			viewHoler.mTonKho.setText("Tồn kho: " + item.getSoluong());
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mSTT = null;
		private TextView mName = null;
		private TextView mGia = null;
		private TextView mTonKho = null;
	}
}
