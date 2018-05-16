package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SanPham;

public class ActSearchSPAdap extends BaseAdapter {
	private Context context;
	private List<SanPham> KHItems;
	private ConvertFont conv = new ConvertFont();
	private ArrayList<SanPham> arraylist;

	public ActSearchSPAdap(Context context, List<SanPham> KHItems) {
		this.context = context;
		this.KHItems = KHItems;
		this.arraylist = new ArrayList<SanPham>();
		this.arraylist.addAll(KHItems);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderItem viewHolder;
		if (convertView == null) {
			// set layout cho convertView
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_acv, null);
			// init va setup ViewHolder
			viewHolder = new ViewHolderItem();
			viewHolder.txt_item1 = (TextView) convertView.findViewById(R.id.textViewItem);
			// store the holder with the view.
			convertView.setTag(viewHolder);
		} else {
			// we've just avoided calling findViewById() on resource everytime
			// just use the viewHolder
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		// object item based on the position
		final SanPham m = KHItems.get(position);
		// assign values if the object is not null
		if (m != null) {
			viewHolder.txt_item1.setText(m.getProduct_cat_id() + " - " + m.getName());
		}

		return convertView;
	}

	@Override
	public int getCount() {
		return KHItems.size();
	}

	@Override
	public Object getItem(int location) {
		return KHItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolderItem {
		public TextView txt_item1;
		// public LinearLayout item_list_dsdh;
	}

	//
	// private class HandlerBtnClick implements OnClickListener {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// switch (v.getId()) {
	// case R.id.btn_addSP:
	// Log.i("btn", "themsp");
	// // Intent intent = new Intent(context, ThemDHListFragment.class);
	// // intent.putExtra("data", "Longpd");
	// // context.startActivity(intent);
	// Toast.makeText(context, "ID=", Toast.LENGTH_SHORT).show();
	// break;
	//
	// default:
	// break;
	// }
	// }
	// }

	// Filter Class
	public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		KHItems.clear();
		if (charText.length() == 0) {
			KHItems.addAll(arraylist);
		} else {
			for (SanPham wp : arraylist) {
				if (wp.getProduct_cat_id().toLowerCase(Locale.getDefault()).contains(charText)
						|| wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
					KHItems.add(wp);
				}
			}
		}
		notifyDataSetChanged();
	}
}
