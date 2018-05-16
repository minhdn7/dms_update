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
import vn.com.vsc.ptpm.VNPT_DMS.entity.InfoDeviceEntity;

public class HistoryLocationAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<InfoDeviceEntity> mArrayListData;

	public HistoryLocationAdapter(Context context, ArrayList<InfoDeviceEntity> data) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_list_history_location, null);

			// Initialize controls
			viewHoler.mTime = (TextView) convertView.findViewById(R.id.text_view_time);
			viewHoler.mUsername = (TextView) convertView.findViewById(R.id.text_view_username);
			viewHoler.mLocation = (TextView) convertView.findViewById(R.id.text_view_location);
			viewHoler.mLevelBattery = (TextView) convertView.findViewById(R.id.text_view_level_battery);
			viewHoler.mNetworkType = (TextView) convertView.findViewById(R.id.text_view_network_type);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		InfoDeviceEntity item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mTime.setText("Time: " + item.getTime());
			viewHoler.mUsername.setText("Id: " + item.getId());
			viewHoler.mLocation.setText("Location: " + item.getLat() + " : " + item.getLng());
			viewHoler.mLevelBattery.setText("Pin: " + item.getPin() + " %");
			viewHoler.mNetworkType.setText("Network: " + item.getType_network() + " - SignalStrength: " + item.getValue() + " dBm");
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mTime = null;
		private TextView mUsername = null;
		private TextView mLocation = null;
		private TextView mLevelBattery = null;
		private TextView mNetworkType = null;
	}
}
