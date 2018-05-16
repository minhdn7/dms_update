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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Tuyen;

public class TuyenOfflineAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater;
	private ArrayList<Tuyen> mArrayListData;

	public TuyenOfflineAdapter(Context context, ArrayList<Tuyen> data) {
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
			convertView = mLayoutInflater.inflate(R.layout.item_list_detail, null);

			// Initialize controls
			viewHoler.mId = (TextView) convertView.findViewById(R.id.id);
			viewHoler.mName =  (TextView) convertView.findViewById(R.id.name);

			convertView.setTag(viewHoler);
		} else {
			viewHoler = (ViewHolder) convertView.getTag();
		}

		// Fill data
		Tuyen item = mArrayListData.get(position);
		if (item != null) {
			viewHoler.mId.setText("" + item.getId());
			viewHoler.mName.setText(item.getName() + " - Từ " + item.getStart_date() + " đến " + item.getEnd_date());
		}

		return convertView;
	}

	public static class ViewHolder {
		private TextView mId = null;
		private TextView mName = null;
	}
}
