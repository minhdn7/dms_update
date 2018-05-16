package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.NhanHieuResponse;

/**
 * Created by MinhDN on 11/10/2017.
 */

public class DanhSachNhanHieu2Adapter extends ArrayAdapter<NhanHieuResponse> {
    private Context context;
    private int resource;
    //    private List<NhanHieuResponse> objects;
    private ConvertFont conv = new ConvertFont();
    List<NhanHieuResponse> items, tempItems, suggestions;

    public DanhSachNhanHieu2Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NhanHieuResponse> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<NhanHieuResponse>(items); // this makes the difference.
        this.suggestions = new ArrayList<NhanHieuResponse>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { // Ordinary


        return initView(position, convertView, parent);
    }

//    @Nullable
//    @Override
//    public GetCountry getItem(int position) {
//        return super.getItem(position);
//    }



    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) { // This view starts when we click the

        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.item_nhan_hieu, parent, false);

        TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
        txtItemNhanHieu.setText(getItem(position).getName());
        return view;

    }

    public View initView(int position, View convertView, ViewGroup parent) {


        View view = convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.item_nhan_hieu, parent, false);

        TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
        txtItemNhanHieu.setText(getItem(position).getName());
        return view;
    }
}
