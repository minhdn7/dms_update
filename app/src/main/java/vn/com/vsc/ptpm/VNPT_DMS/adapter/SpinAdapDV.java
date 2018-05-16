package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonViSP;

public class SpinAdapDV extends ArrayAdapter<DonViSP> {

	private Context context;
	private int layout;
	private List<DonViSP> list;
	ConvertFont conv;

	public SpinAdapDV(Context context, int layout, List<DonViSP> list) {
		super(context, layout, list);
		this.context = context;
		this.layout = layout;
		this.list = list;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		DviHolder holder = null;

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(layout, parent, false);
		holder = new DviHolder();

		holder.dvi = list.get(position);
		holder.txt_item1 = (TextView) row.findViewById(R.id.company);
		holder.txt_item1.setText(holder.dvi.getTen_dvi());
		row.setTag(holder);
		return row;
	}

	private class DviHolder {
		public DonViSP dvi;
		public TextView numb, txt_item1;
		// public LinearLayout item_list_dsdh;
	}
}