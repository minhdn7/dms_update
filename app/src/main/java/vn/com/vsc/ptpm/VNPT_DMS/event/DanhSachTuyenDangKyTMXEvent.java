package vn.com.vsc.ptpm.VNPT_DMS.event;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.model.DanhSachKhachHangDangKyModel;

/**
 * Created by MinhDN on 19/12/2017.
 */

public class DanhSachTuyenDangKyTMXEvent {
    private List<DanhSachKhachHangDangKyModel> danhSachKhachHangDangKy;

    public List<DanhSachKhachHangDangKyModel> getDanhSachKhachHangDangKy() {
        return danhSachKhachHangDangKy;
    }

    public void setDanhSachKhachHangDangKy(List<DanhSachKhachHangDangKyModel> danhSachKhachHangDangKy) {
        this.danhSachKhachHangDangKy = danhSachKhachHangDangKy;
    }

    public DanhSachTuyenDangKyTMXEvent(List<DanhSachKhachHangDangKyModel> danhSachKhachHangDangKy) {
        this.danhSachKhachHangDangKy = danhSachKhachHangDangKy;
    }
}
