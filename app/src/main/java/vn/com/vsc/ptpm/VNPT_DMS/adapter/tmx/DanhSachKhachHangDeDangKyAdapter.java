package vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DanhSachKhachHangDangKyModel;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachDangKyLichTrinhResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDeDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachTuyenFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.ThongTinTuyenFragment;

/**
 * Created by MinhDN on 19/12/2017.
 */

public class DanhSachKhachHangDeDangKyAdapter extends ArrayAdapter<DanhSachKhachHangDeDangKyResponse> {
    public List<DanhSachKhachHangDangKyModel> danhSachKhachHangDangKyModels = new ArrayList<>();
    private Context context;
    private int resource;
    private List<DanhSachKhachHangDeDangKyResponse> objects;

    public DanhSachKhachHangDeDangKyAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachKhachHangDeDangKyResponse> objects) {
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
        TextView txtStt = (TextView) view.findViewById(R.id.txtStt);
        TextView txtTenKhachHang = (TextView) view.findViewById(R.id.txtTenKhachHang);
        TextView txtDiaChi = (TextView) view.findViewById(R.id.txtDiaChi);


        final CheckBox ckThu2 = (CheckBox) view.findViewById(R.id.ckThu2);
        final CheckBox ckThu3 = (CheckBox) view.findViewById(R.id.ckThu3);
        final CheckBox ckThu4 = (CheckBox) view.findViewById(R.id.ckThu4);
        final CheckBox ckThu5 = (CheckBox) view.findViewById(R.id.ckThu5);
        final CheckBox ckThu6 = (CheckBox) view.findViewById(R.id.ckThu6);
        final CheckBox ckThu7 = (CheckBox) view.findViewById(R.id.ckThu7);
        final CheckBox ckChuNhat = (CheckBox) view.findViewById(R.id.ckChuNhat);
        final CheckBox ckTatCa = (CheckBox) view.findViewById(R.id.ckTatCa);
        DanhSachKhachHangDangKyModel item = new DanhSachKhachHangDangKyModel();
        item.setCkThư2(ckThu2);
        item.setCkThư3(ckThu3);
        item.setCkThư4(ckThu4);
        item.setCkThư5(ckThu5);
        item.setCkThư6(ckThu6);
        item.setCkThư7(ckThu7);
        item.setCkChuNhat(ckChuNhat);
        item.setCkTatCa(ckTatCa);
        item.setOrgId(getItem(position).getOrgId());
        item.setOrgName(getItem(position).getOrgName());
        item.setAddress(getItem(position).getAddress());
        danhSachKhachHangDangKyModels.add(item);

        ckThu2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckThu3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckThu4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ckThu4.isChecked()){

                }
                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckThu5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckThu6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckThu7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        ckChuNhat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(ckThu2.isChecked() || ckThu3.isChecked() || ckThu4.isChecked()
                        || ckThu5.isChecked() || ckThu6.isChecked() || ckThu7.isChecked() || ckChuNhat.isChecked()){
                    ckTatCa.setChecked(true);

                }else {
                    ckTatCa.setChecked(false);
                }
            }
        });

        try{
            txtStt.setText(String.valueOf(position + 1));
            txtTenKhachHang.setText(getItem(position).getOrgName());
            txtDiaChi.setText(getItem(position).getAddress());
        }catch (Exception ex){

        }



        return view;
    }
}
