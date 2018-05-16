package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.BinhLuan;

public class BinhLuanAdapter extends BaseAdapter {
	private Context context;
	private List<BinhLuan> blItems;
	private LayoutInflater inflater;
	private ConvertFont conv = new ConvertFont();

	public BinhLuanAdapter(Context context, List<BinhLuan> blItems) {
		this.context = context;
		this.blItems = blItems;
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("static-access")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_bl, null);
			holder = new ViewHolder();
			holder.counter = (TextView) convertView.findViewById(R.id.counter);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BinhLuan m = blItems.get(position);
		if (m != null) {
			holder.counter.setText(String.valueOf(position + 1));
			holder.title.setText("[" + m.getCreated_date() + "] - [" + m.getCreated_by() + "]");
			holder.content.setText(conv.getUTF8StringFromNCR(m.getNote()).replaceAll("<br>", "\n"));
		}
		return convertView;
	}

	private class ViewHolder {
		TextView counter, title, content;
	}

	@Override
	public int getCount() {
		return blItems.size();
	}

	@Override
	public Object getItem(int location) {
		return blItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
