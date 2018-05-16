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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Checkin;

public class HistoryCheckinAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<Checkin> mArrayListData;

	public HistoryCheckinAdapter(Context context, ArrayList<Checkin> data) {
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
			viewHoler.mCode = (TextView) convertView.findViewById(R.id.text_view_username);
			viewHoler.mAssign = (TextView) convertView.findViewById(R.id.text_view_location);
			viewHoler.mLocation = (TextView) convertView.findViewById(R.id.text_view_level_battery);
			viewHoler.mNetworkType = (TextView) convertView.findViewById(R.id.text_view_network_type);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		Checkin item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mTime.setText("Time: " + item.getTime());
			viewHoler.mCode.setText("ID: " + item.getId() + " - Code" + item.getCode());
			viewHoler.mAssign.setText("Tuyen ID: " + item.getAssign());
			viewHoler.mLocation.setText("Location: " + item.getLat() + " : " + item.getLng());
			if (item.getChecktype().equals("0"))
				viewHoler.mNetworkType.setText("Kiểu: Checkin");
			else
				viewHoler.mNetworkType.setText("Kiểu: Checkout");
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mTime = null;
		private TextView mCode = null;
		private TextView mLocation = null;
		private TextView mAssign = null;
		private TextView mNetworkType = null;
	}
}
