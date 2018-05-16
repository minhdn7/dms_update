package vn.com.vsc.ptpm.VNPT_DMS.adapter.glab;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachKhachHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.TrangThaiDonHangResponse;

/**
 * Created by MinhDN on 21/11/2017.
 */

public class TrangThaiDonHangAdapter extends ArrayAdapter<TrangThaiDonHangResponse> {
    private Context context;
    private int resource;
    private List<TrangThaiDonHangResponse> objects;

    public TrangThaiDonHangAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TrangThaiDonHangResponse> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtRowSpinner = (TextView) view.findViewById(R.id.txtRowSpinner);
        txtRowSpinner.setText(getItem(position).getName());
        return view;
    }
}
