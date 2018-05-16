package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

public class KHListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<KhachHang> navDrawerItems;

	public KHListAdapter(Context context,
			ArrayList<KhachHang> navDrawerItems) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.item_list_kh, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.kh_title);
		TextView txtAddress = (TextView) convertView.findViewById(R.id.kh_address);

//		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		txtTitle.setText(navDrawerItems.get(position).getAddress());

		// displaying count
		// check whether it set visible or not
//		if (navDrawerItems.get(position).getCounterVisibility()) {
//			txtCount.setText(navDrawerItems.get(position).getCount());
//		} else {
//			// hide the counter view
//			txtCount.setVisibility(View.GONE);
//		}

		return convertView;
	}

}
