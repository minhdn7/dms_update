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

public class SpinAdapKH extends ArrayAdapter<TenKH> {

	private Context context;
	private int layout;
	private List<TenKH> KHItems;

	public SpinAdapKH(Context context, int layout, List<TenKH> KHItems) {
		super(context, layout, KHItems);
		this.context = context;
		this.layout = layout;
		this.KHItems = KHItems;
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
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.spinner_row, null);
		TenKH m = KHItems.get(position);
		// LayoutInflater inflater = (LayoutInflater)
		// context.getLayoutInflater();
		// View row = inflater.inflate(R.layout.row, parent, false);
		TextView label = (TextView) row.findViewById(R.id.company);
		label.setText(m.getName().substring(0, 5));

		// TextView sub = (TextView) row.findViewById(R.id.sub);
		// sub.setText(subs[position]);
		//
		// ImageView icon = (ImageView) row.findViewById(R.id.image);
		// icon.setImageResource(arr_images[position]);
		return row;
	}
}