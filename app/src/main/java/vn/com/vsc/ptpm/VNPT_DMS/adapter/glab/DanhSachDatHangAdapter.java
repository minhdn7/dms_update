package vn.com.vsc.ptpm.VNPT_DMS.adapter.glab;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachKhachHangResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabChiTietDonHangFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabDanhSachDonHangFragment;

/**
 * Created by MinhDN on 21/11/2017.
 */

public class DanhSachDatHangAdapter extends ArrayAdapter<DanhSachKhachHangResponse> {
    private Context context;
    private int resource;
    private List<DanhSachKhachHangResponse> objects;
    private GlabDanhSachDonHangFragment danhSachDonHangFragment;
    private MainActivity mainActivity;

    public DanhSachDatHangAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachKhachHangResponse> objects, GlabDanhSachDonHangFragment danhSachDonHangFragment, MainActivity mainActivity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.danhSachDonHangFragment = danhSachDonHangFragment;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView numb = (TextView) view.findViewById(R.id.numb);
        TextView txtHeader = (TextView) view.findViewById(R.id.txt_item1);
        TextView txtKhachHang = (TextView) view.findViewById(R.id.txt_item2);
        TextView txtNhaPhanPhoi = (TextView) view.findViewById(R.id.txt_item3);
        TextView txtTongTien = (TextView) view.findViewById(R.id.txt_item4);
        TextView txtGhiChu = (TextView) view.findViewById(R.id.txt_item5);
        ImageView imgChiTiet = (ImageView) view.findViewById(R.id.ls_chitiet);
        ImageView imgXoa = (ImageView) view.findViewById(R.id.ls_xoa);
        RelativeLayout viewDanhSachKhaoSat = (RelativeLayout) view.findViewById(R.id.item_list_dsdh);

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn xóa đơn hàng này ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        danhSachDonHangFragment.xoaDonHang(Integer.parseInt(getItem(position).getDonnhapId()));
                    }
                });
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        imgChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlabChiTietDonHangFragment chiTietDonHangFragment = new GlabChiTietDonHangFragment(getItem(position).getDonnhapId());
                FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, chiTietDonHangFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        viewDanhSachKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlabChiTietDonHangFragment chiTietDonHangFragment = new GlabChiTietDonHangFragment(getItem(position).getDonnhapId());
                FragmentTransaction transaction = mainActivity.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, chiTietDonHangFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        try{
            // đổ dữ liệu
            numb.setText(getItem(position).getRowStt());
            txtHeader.setText("[Số " + getItem(position).getDonnhapId()
                    + "] - ["
                    + getItem(position).getNgayLap() + "] - ["
                    + getItem(position).getTrangthai() + "]");

            txtKhachHang.setText("Tên KH: "
                    + getItem(position).getOrdererName());


            txtNhaPhanPhoi.setText("Nhà PP: "
                    + getItem(position).getSupplierName());

            txtTongTien.setText("Tổng tiền: " + NumbFormatF(Double.parseDouble(getItem(position).getTongtien().replace(",", ""))) + " VNĐ \t" + "-" + "\t Khuyến mại: " + NumbFormatF(Double.parseDouble(getItem(position).getTongKhuyenmai().replace(",", ""))) + " VNĐ");

            txtGhiChu.setText("Ghi chú: "
                    + getItem(position).getNote());

        }catch (Exception ex){

        }



        return view;
    }


    public String NumbFormatF(double numb) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(numb);
    }

}
