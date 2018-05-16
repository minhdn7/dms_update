package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.event.ThongTinKhachHangKhaoSatEvent;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.DanhSachKhaoSatResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachKhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.ThongTinKhaoSatFragment;

/**
 * Created by MinhDN on 4/10/2017.
 */

public class DanhSachKhaoSatAdapter extends ArrayAdapter<DanhSachKhaoSatResponse>{
    private Context context;
    private int resource;
    private List<DanhSachKhaoSatResponse> objects;
    private ConvertFont conv = new ConvertFont();
    private DanhSachKhaoSatFragment danhSachKhaoSatFragment;
    private MainActivity activity;
    public DanhSachKhaoSatAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachKhaoSatResponse> objects, DanhSachKhaoSatFragment danhSachKhaoSatFragment, MainActivity activity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.danhSachKhaoSatFragment = danhSachKhaoSatFragment;
        this.activity = activity;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtSTT = (TextView) view.findViewById(R.id.txtSTT);
        TextView txtTenKhachHang = (TextView) view.findViewById(R.id.txtTenKhachHang);
        TextView txtThangKhaoSat = (TextView) view.findViewById(R.id.txtThangKhaoSat);
        TextView txtNgayThucHien = (TextView) view.findViewById(R.id.txtNgayThucHien);
        ImageView btnXoaKhaoSat = (ImageView) view.findViewById(R.id.btnXoaKhaoSat);
        ImageView btnXemKhaoSat = (ImageView) view.findViewById(R.id.btnXemKhaoSat);
        LinearLayout  viewChiTietDanhSachKhaoSat = (LinearLayout) view.findViewById(R.id.viewChiTietDanhSachKhaoSat);;
        try {
            txtSTT.setText( getItem(position).getRow_stt());
            txtTenKhachHang.setText(conv.getUTF8StringFromNCR(URLDecoder.decode(getItem(position).getCustomer(), "UTF-8")));
            txtThangKhaoSat.setText(getItem(position).getSmonth_survey());
            txtNgayThucHien.setText(getItem(position).getCreated_date());


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        viewChiTietDanhSachKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showProgressBar();
                Toast.makeText(getContext(), getItem(position).getId(), Toast.LENGTH_SHORT).show();
                ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent = new ThongTinKhachHangKhaoSatEvent(getItem(position).getId(),
                        getItem(position).getCreated_date(),
                        getItem(position).getRow_stt(),
                        getItem(position).getPhanhoi(),
                        getItem(position).getCustomer(),
                        getItem(position).getSmonth_survey(),
                        getItem(position).getOrg_id(),
                        getItem(position).getIs_daily()
                );
                EventBus.getDefault().postSticky(khachHangKhaoSatEvent);
                ThongTinKhaoSatFragment thongTinKhaoSatFragment = new ThongTinKhaoSatFragment(khachHangKhaoSatEvent);
                activity.replaceFragment(thongTinKhaoSatFragment);
            }
        });

        btnXemKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showProgressBar();
                Toast.makeText(getContext(), getItem(position).getId(), Toast.LENGTH_SHORT).show();
                ThongTinKhachHangKhaoSatEvent khachHangKhaoSatEvent = new ThongTinKhachHangKhaoSatEvent(getItem(position).getId(),
                        getItem(position).getCreated_date(),
                        getItem(position).getRow_stt(),
                        getItem(position).getPhanhoi(),
                        getItem(position).getCustomer(),
                        getItem(position).getSmonth_survey(),
                        getItem(position).getOrg_id(),
                        getItem(position).getIs_daily()
                        );
                EventBus.getDefault().postSticky(khachHangKhaoSatEvent);
                ThongTinKhaoSatFragment thongTinKhaoSatFragment = new ThongTinKhaoSatFragment(khachHangKhaoSatEvent);
                activity.replaceFragment(thongTinKhaoSatFragment);
            }
        });

        btnXoaKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn xóa khảo sát này ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        danhSachKhaoSatFragment.xoaKhaoSat(getItem(position).getId());
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
        return view;
    }


}
