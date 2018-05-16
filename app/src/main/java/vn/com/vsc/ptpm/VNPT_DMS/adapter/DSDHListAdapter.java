package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.adapter.DSSPListAdapter.SendData;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHang;

public class DSDHListAdapter extends BaseAdapter {
    private Context context;
    private List<DonHang> KHItems;
    private ConvertFont conv = new ConvertFont();
    private SendData dt;
    private OnCustomClickListener callback;

    public DSDHListAdapter(Context context, List<DonHang> KHItems,
                           OnCustomClickListener callback) {
        this.context = context;
        this.KHItems = KHItems;
        this.callback = callback;
    }

    @SuppressWarnings("static-access")
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            // set layout cho convertView
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_lsdh, null);
            // init va setup ViewHolder
            viewHolder = new ViewHolderItem();
            viewHolder.item_list_dsdh = (RelativeLayout) convertView.findViewById(R.id.item_list_dsdh);
            viewHolder.numb = (TextView) convertView.findViewById(R.id.numb);
            viewHolder.txt_item1 = (TextView) convertView.findViewById(R.id.txt_item1);
            viewHolder.txt_item2 = (TextView) convertView.findViewById(R.id.txt_item2);
            viewHolder.txt_item3 = (TextView) convertView.findViewById(R.id.txt_item3);
            viewHolder.txt_item4 = (TextView) convertView.findViewById(R.id.txt_item4);
            viewHolder.txt_item5 = (TextView) convertView.findViewById(R.id.txt_item5);
            viewHolder.ls_chitiet = (ImageButton) convertView.findViewById(R.id.ls_chitiet);
            viewHolder.ls_xoa = (ImageButton) convertView.findViewById(R.id.ls_xoa);
            // viewHolder.btn_xoa = (Button) convertView
            // .findViewById(R.id.btn_xoalsdh);
            // viewHolder.btn_km = (Button) convertView
            // .findViewById(R.id.btn_tinhkmlsdh);
            // viewHolder.btn_sua = (Button) convertView
            // .findViewById(R.id.btn_sualsdh);
            // store the holder with the view.
            convertView.setTag(viewHolder);
        } else {
            // we've just avoided calling findViewById() on resource everytime
            // just use the viewHolder
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        // object item based on the position
        DonHang m = KHItems.get(position);
        // assign values if the object is not null
        if (m != null) {
            // [Số 109] - [08/07/2015] - [Mới lập]
            viewHolder.numb.setText(String.valueOf(position + 1));
            viewHolder.txt_item1.setText("[Số " + m.getDonnhap_id() + "] - ["
                    + m.getNgay_lap() + "] - ["
                    + conv.getUTF8StringFromNCR(m.getTrangthai()) + "]");
            viewHolder.txt_item2.setText("Tên KH: "
                    + conv.getUTF8StringFromNCR(m.getOrderer_name()));
            viewHolder.txt_item3.setText("Nhà PP: "
                    + conv.getUTF8StringFromNCR(m.getSupplier_name()));
//			viewHolder.txt_item4.setText("Tổng tiền: "
//					+ (m.getTongtien() + " VNĐ \t") + "-" + "\t Khuyến mại: "
//					+ m.getTong_khuyenmai() + " VNĐ");
            if (m.getTongtien() != null && m.getTong_khuyenmai() != null){
                viewHolder.txt_item4.setText("Tổng tiền: " + NumbFormatF(Double.parseDouble(m.getTongtien().replace(",", ""))) + " VNĐ \t" + "-" + "\t Khuyến mại: " + NumbFormatF(Double.parseDouble(m.getTong_khuyenmai().replace(",", ""))) + " VNĐ");
            }else {
                Log.e("ERROR", "Tổng tiền hoặc khuyến mãi NULL");
            }
            viewHolder.txt_item5.setText("Ghi chú: "
                    + conv.getUTF8StringFromNCR(m.getNote()));
        }
        viewHolder.item_list_dsdh.setOnClickListener(new CustomOnClickListener(
                callback, position));
        viewHolder.ls_chitiet.setOnClickListener(new CustomOnClickListener(
                callback, position));
        viewHolder.ls_xoa.setOnClickListener(new CustomOnClickListener(
                callback, position));
        // viewHolder.btn_xoa.setOnClickListener(new CustomOnClickListener(
        // callback, position));
        // viewHolder.btn_km.setOnClickListener(new CustomOnClickListener(
        // callback, position));
        // viewHolder.btn_sua.setOnClickListener(new CustomOnClickListener(
        // callback, position));
        return convertView;
    }

    public String NumbFormatF(double numb) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(numb);
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
        public TextView numb, txt_item1, txt_item2, txt_item4, txt_item3,
                txt_item5;
        // public Button btn_xoa, btn_km, btn_sua;
        public RelativeLayout item_list_dsdh;
        public ImageButton ls_chitiet, ls_xoa;

    }

}
