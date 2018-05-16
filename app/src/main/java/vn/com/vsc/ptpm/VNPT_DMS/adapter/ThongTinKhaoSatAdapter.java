package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ThongTinKhaoSatChiTietResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinKhaoSatFragment;

/**
 * Created by MinhDN on 10/10/2017.
 */

public class ThongTinKhaoSatAdapter extends ArrayAdapter<ThongTinKhaoSatChiTietResponse>{
    private Context context;
    private int resource;
    private List<ThongTinKhaoSatChiTietResponse> objects;
    private ConvertFont conv = new ConvertFont();
    private ThongTinKhaoSatFragment thongTinKhaoSatFragment;

    public ThongTinKhaoSatAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ThongTinKhaoSatChiTietResponse> objects, ThongTinKhaoSatFragment thongTinKhaoSatFragment) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.thongTinKhaoSatFragment = thongTinKhaoSatFragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final DanhSachKhuyenMaiAdapter.ViewHolder viewHoler = new DanhSachKhuyenMaiAdapter.ViewHolder();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtItemSTT = (TextView) view.findViewById(R.id.txtItemSTT);
        TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
        TextView txtItemChungLoai = (TextView) view.findViewById(R.id.txtItemChungLoai);
        TextView txtItemNhaPhanPhoi = (TextView) view.findViewById(R.id.txtItemNhaPhanPhoi);
        TextView txtItemChiPhiVanTai = (TextView) view.findViewById(R.id.txtItemChiPhiVanTai);
        TextView txtItemChiPhiBocXep = (TextView) view.findViewById(R.id.txtItemChiPhiBocXep);
        TextView txtItemGiaDenCuaHang = (TextView) view.findViewById(R.id.txtItemGiaDenCuaHang);
        TextView txtItemGiaBan = (TextView) view.findViewById(R.id.txtItemGiaBan);
        TextView txtItemSanLuongDuKien = (TextView) view.findViewById(R.id.txtItemSanLuongDuKien);
        ImageView imgRemoveItem = (ImageView) view.findViewById(R.id.imgRemoveItem);
        ImageView imgEditItem = (ImageView) view.findViewById(R.id.imgEditItem);

        try {
//            txtItemSTT.setText(getItem(position).getRow_stt());
            String stt = String.valueOf((position + 1));
            txtItemSTT.setText(stt);
            txtItemNhanHieu.setText(conv.getUTF8StringFromNCR(getItem(position).getBrand()));
            txtItemChungLoai.setText(conv.getUTF8StringFromNCR(getItem(position).getProduct_cat()));
            txtItemNhaPhanPhoi.setText(conv.getUTF8StringFromNCR(getItem(position).getNhaphanphoi()));
            txtItemChiPhiVanTai.setText(conv.getUTF8StringFromNCR(getItem(position).getTransport_cost()));
            txtItemChiPhiBocXep.setText(conv.getUTF8StringFromNCR(getItem(position).getLoading_cost()));
            txtItemGiaDenCuaHang.setText(conv.getUTF8StringFromNCR(getItem(position).getBuying_price()));
            txtItemGiaBan.setText(conv.getUTF8StringFromNCR(getItem(position).getSelling_price()));
            txtItemSanLuongDuKien.setText(conv.getUTF8StringFromNCR(getItem(position).getQuantity_expected()));

        }catch (Exception ex){
            Log.d("Error convert", ex.toString());
        }

    imgEditItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            thongTinKhaoSatFragment.dialogSoLieuKhaoSat(getItem(position), position);
        }
    });

    imgRemoveItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            thongTinKhaoSatFragment.XoaThongTinKhaoSat(position);
        }
    });
        return view;
    }
}
