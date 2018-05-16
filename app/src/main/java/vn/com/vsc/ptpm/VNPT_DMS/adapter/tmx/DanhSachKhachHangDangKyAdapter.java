package vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.ThongTinTuyenFragment;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachKhachHangDangKyAdapter extends ArrayAdapter<DanhSachKhachHangDangKyResponse> {
    private Context context;
    private int resource;
    private List<DanhSachKhachHangDangKyResponse> objects;
    private ThongTinTuyenFragment thongTinTuyenFragment;
    public DanhSachKhachHangDangKyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachKhachHangDangKyResponse> objects, ThongTinTuyenFragment thongTinTuyenFragment) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.thongTinTuyenFragment = thongTinTuyenFragment;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtStt = (TextView) view.findViewById(R.id.txtStt);
        TextView txtTenKhachHang = (TextView) view.findViewById(R.id.txtTenKhachHang);
        TextView txtDiaChi = (TextView) view.findViewById(R.id.txtDiaChi);
        ImageView imgXoa = (ImageView) view.findViewById(R.id.imgXoa);
        final CheckBox ckThu2 = (CheckBox) view.findViewById(R.id.ckThu2);
        final CheckBox ckThu3 = (CheckBox) view.findViewById(R.id.ckThu3);
        final CheckBox ckThu4 = (CheckBox) view.findViewById(R.id.ckThu4);
        final CheckBox ckThu5 = (CheckBox) view.findViewById(R.id.ckThu5);
        final CheckBox ckThu6 = (CheckBox) view.findViewById(R.id.ckThu6);
        final CheckBox ckThu7 = (CheckBox) view.findViewById(R.id.ckThu7);
        final CheckBox ckChuNhat = (CheckBox) view.findViewById(R.id.ckChuNhat);

        txtStt.setText(String.valueOf(position + 1));
        txtTenKhachHang.setText(getItem(position).getOrgName());
        txtDiaChi.setText(getItem(position).getAddress());

        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thongTinTuyenFragment.xoaTuyenDangKy(position);
            }
        });

        // thứ 2
        if(getItem(position).getMon().equals("1")){
            ckThu2.setChecked(true);
        }else {
            ckThu2.setChecked(false);
        }

        // thứ 3
        if(getItem(position).getTue().equals("1")){
            ckThu3.setChecked(true);
        }else {
            ckThu3.setChecked(false);
        }

        // thứ 4
        if(getItem(position).getWed().equals("1")){
            ckThu4.setChecked(true);
        }else {
            ckThu4.setChecked(false);
        }

        // thứ 5
        if(getItem(position).getThu().equals("1")){
            ckThu5.setChecked(true);
        }else {
            ckThu5.setChecked(false);
        }

        // thứ 6
        if(getItem(position).getFri().equals("1")){
            ckThu6.setChecked(true);
        }else {
            ckThu6.setChecked(false);
        }
        // thứ 7
        if(getItem(position).getSat().equals("1")){
            ckThu7.setChecked(true);
        }else {
            ckThu7.setChecked(false);
        }
        // chủ nhật
        if(getItem(position).getSun().equals("1")){
            ckChuNhat.setChecked(true);
        }else {
            ckChuNhat.setChecked(false);
        }

        // bắt sự kiện thay đổi checkbox
        ckThu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu2.isChecked()){
                    getItem(position).setMon("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setMon("1");

                }else {
                    getItem(position).setMon("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setMon("0");
                }
            }
        });

        ckThu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu3.isChecked()){
                    getItem(position).setTue("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setTue("1");
                }else {
                    getItem(position).setTue("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setTue("0");
                }
            }
        });

        ckThu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu4.isChecked()){
                    getItem(position).setWed("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setWed("1");
                }else {
                    getItem(position).setWed("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setWed("0");
                }
            }
        });


        ckThu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu5.isChecked()){
                    getItem(position).setThu("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setThu("1");
                }else {
                    getItem(position).setThu("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setThu("0");
                }
            }
        });

        ckThu6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu6.isChecked()){
                    getItem(position).setFri("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setFri("1");
                }else {
                    getItem(position).setFri("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setFri("0");
                }
            }
        });

        ckThu7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckThu7.isChecked()){
                    getItem(position).setSat("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setSat("1");
                }else {
                    getItem(position).setSat("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setSat("0");
                }
            }
        });

        ckChuNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ckChuNhat.isChecked()){
                    getItem(position).setSun("1");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setSun("1");
                }else {
                    getItem(position).setSun("0");
//                    thongTinTuyenFragment.danhSachKhachHangDangKyResponses.get(position).setSun("0");
                }
            }
        });
        return view;
    }
}
