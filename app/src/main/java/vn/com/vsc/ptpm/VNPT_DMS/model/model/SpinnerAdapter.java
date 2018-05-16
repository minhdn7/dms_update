package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;

public class SpinnerAdapter extends ArrayAdapter<Tuyen> {

	private int layout;
	private ArrayList<Tuyen> glstData;
	private LayoutInflater inflater;

	public SpinnerAdapter(Context context, int layout, ArrayList<Tuyen> data) {
		super(context, layout, data);
		this.layout = layout;
		this.glstData = data;
		this.inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
		View row = inflater.inflate(layout, null);
		TextView label = (TextView) row.findViewById(R.id.company);

		Tuyen item = glstData.get(position);
		if (item != null) {
			label.setText(item.getName()); 
		}

		return row;
	}


}
