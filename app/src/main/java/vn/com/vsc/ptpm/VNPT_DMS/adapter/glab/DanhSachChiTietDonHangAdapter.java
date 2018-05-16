package vn.com.vsc.ptpm.VNPT_DMS.adapter.glab;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachXetNghiemResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabChiTietDonHangFragment;

/**
 * Created by MinhDN on 23/11/2017.
 */

public class DanhSachChiTietDonHangAdapter extends ArrayAdapter<DanhSachXetNghiemResponse>{
    private Context context;
    private int resource;
    private List<DanhSachXetNghiemResponse> objects;
    private GlabChiTietDonHangFragment fragment;

    public DanhSachChiTietDonHangAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachXetNghiemResponse> objects, GlabChiTietDonHangFragment fragment) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtItemSTT = (TextView) view.findViewById(R.id.txtItemSTT);
        TextView txtItemCode = (TextView) view.findViewById(R.id.txtItemCode);
        TextView txtItemName = (TextView) view.findViewById(R.id.txtItemName);
        TextView txtItemCost = (TextView) view.findViewById(R.id.txtItemCost);
        ImageView imgRemoveItem = (ImageView) view.findViewById(R.id.imgRemoveItem);

        try{
            txtItemSTT.setText(String.valueOf(position + 1));
//            txtItemCode.setText(getItem(position).getLoaispId());
//            txtItemName.setText(getItem(position).getLoaispTen());
//            txtItemCost.setText(getItem(position).getGianhapVnd());
            txtItemCode.setText(getItem(position).getMaSanPham());
            txtItemName.setText(getItem(position).getName());
            txtItemCost.setText(getItem(position).getGiaBan());
        }catch (Exception ex){

        }

        imgRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoaThongTinXetNghiem(position);
            }
        });

        return view;
    }
}
