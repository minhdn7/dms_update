package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ThongTinTuyenResponse;

public class ThongTinDialog extends BaseDialog {
    TextView tvTitle, btn_close;
    TextView txtTongKhachHang, txtGheThamConlai, txtKhachHangNgoaiTuyen, txtSoKhachDatHang, txtSoDonHang, txtTongTienHang;
    Controller con = new Controller(getContext());
    ThongTinTuyenResponse thongTinTuyenResponse;

    public ThongTinDialog(Context context, String title, ThongTinTuyenResponse thongTinTuyenResponse) {
        super(context);
        setContentView(R.layout.custom_dialog_thongtin);
        addItems();
        this.thongTinTuyenResponse = thongTinTuyenResponse;
        addControl(title);
        this.con = new Controller(context);
        addEvents();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public void addItems() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        txtTongKhachHang = (TextView) findViewById(R.id.txtTongKhachHang);
        txtGheThamConlai = (TextView) findViewById(R.id.txtGheThamConlai);
        txtKhachHangNgoaiTuyen = (TextView) findViewById(R.id.txtKhachHangNgoaiTuyen);
        txtSoKhachDatHang = (TextView) findViewById(R.id.txtSoKhachDatHang);
        txtSoDonHang = (TextView) findViewById(R.id.txtSoDonHang);
        txtTongTienHang = (TextView) findViewById(R.id.txtTongTienHang);
    }

    public void addControl(String title) {
        tvTitle.setText(title);
        btn_close = (TextView) findViewById(R.id.btnClose);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThongTinDialog.this.dismiss();
            }
        });
    }

    private void addEvents() {
        txtTongKhachHang.setText(thongTinTuyenResponse.getNoOfCus());
        txtGheThamConlai.setText(thongTinTuyenResponse.getNoOfDone() + " / " + thongTinTuyenResponse.getNoOfNone());
        txtKhachHangNgoaiTuyen.setText(thongTinTuyenResponse.getNoOfDoneNt());
        txtSoKhachDatHang.setText(thongTinTuyenResponse.getNoOfOrderedCustomer());
        txtSoDonHang.setText(thongTinTuyenResponse.getNoOfOrder());
        txtTongTienHang.setText(thongTinTuyenResponse.getTotalOfOrder());
    }

}
