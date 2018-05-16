package vn.com.vsc.ptpm.VNPT_DMS.model.model;

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

public class SpinAdapNCC extends ArrayAdapter<TenKH> {

	private Context context;
	private int layout;
	private List<TenKH> list;
	ConvertFont conv;

	public SpinAdapNCC(Context context, int layout, List<TenKH> list) {
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

	@SuppressWarnings("static-access")
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.spinner_row, null);
		TenKH m = list.get(position);
		TextView label = (TextView) row.findViewById(R.id.company);
		label.setText(conv.getUTF8StringFromNCR(m.getName()));
		return row;
	}
}