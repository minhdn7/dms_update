package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.R;

public class SpinnerAdapterCus extends ArrayAdapter<Tuyen> {

    private int layout;
    private ArrayList<Tuyen> glstData;
    private LayoutInflater inflater;

    public SpinnerAdapterCus(Context context, int layout, ArrayList<Tuyen> data) {
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
        if (glstData.size() > 0) {
            Tuyen item = glstData.get(position);
            if (item.getName() != null) {

                if (item.getName().contains("Thu")) {
                    String[] arr = item.getName().toString().split(",");
                    if (arr.length > 0) {
                        String name = arr[0].toString().replace("Thu", "Thứ");
                        label.setText(name);
                    }
                } else if (item.getName().toLowerCase().equals("ngoai tuyen")) {
                    label.setText("Ngoại tuyến");
                } else if (item.getName().equals("Tất cả")) {
                    label.setText("Tất cả");
                } else {
                    label.setText("Chủ nhật");
                }
            }
        }

        return row;
    }
}