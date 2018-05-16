package vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachDangKyLichTrinhResponse;
import vn.com.vsc.ptpm.VNPT_DMS.view.activities.MainActivity;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.glab.GlabChiTietDonHangFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachKhaoSatFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.DanhSachTuyenFragment;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.tmx.ThongTinTuyenFragment;

/**
 * Created by MinhDN on 12/12/2017.
 */

public class DanhSachDangKyLichTrinhAdapter extends ArrayAdapter<DanhSachDangKyLichTrinhResponse> {
    private Context context;
    private int resource;
    private List<DanhSachDangKyLichTrinhResponse> objects;
    private DanhSachTuyenFragment danhSachTuyenFragment;
    private MainActivity activity;

    public DanhSachDangKyLichTrinhAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachDangKyLichTrinhResponse> objects, DanhSachTuyenFragment danhSachTuyenFragment, MainActivity activity) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.danhSachTuyenFragment = danhSachTuyenFragment;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        TextView txtTuanKhaoSat = (TextView) view.findViewById(R.id.txtTuanKhaoSat);
        TextView txtNhanVienKinhDoanh = (TextView) view.findViewById(R.id.txtNhanVienKinhDoanh);
        TextView txtTrangThai = (TextView) view.findViewById(R.id.txtTrangThai);
        RelativeLayout viewDanhSachKhaoSat = (RelativeLayout) view.findViewById(R.id.viewDanhSachKhaoSat);
        ImageButton btnXemTuyen = (ImageButton) view.findViewById(R.id.btnXemTuyen);
        ImageButton btnXoaTuyen = (ImageButton) view.findViewById(R.id.btnXoaTuyen);

        TextView txtSTT = (TextView) view.findViewById(R.id.txtSTT);
        try{
            txtTuanKhaoSat.setText(getItem(position).getTuanthuchien());
            txtNhanVienKinhDoanh.setText(getItem(position).getNvkd());
            txtTrangThai.setText(getItem(position).getIsActive());
            txtSTT.setText(String.valueOf(position + 1));
        }catch (Exception e){

        }

        viewDanhSachKhaoSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinTuyenFragment thongTinTuyenFragment = new ThongTinTuyenFragment(getItem(position).getId());
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, thongTinTuyenFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnXemTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinTuyenFragment thongTinTuyenFragment = new ThongTinTuyenFragment(getItem(position).getId());
                FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, thongTinTuyenFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnXoaTuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn xóa tuyến này ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        danhSachTuyenFragment.xoaDonHang(getItem(position).getId());
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
