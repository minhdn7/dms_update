package vn.com.vsc.ptpm.VNPT_DMS.event;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ChuongTrinhKhuyenMaiResponse;

/**
 * Created by MinhDN on 17/3/2017.
 */

public class EventDanhSachChuongTrinhKhuyenMai {
    private List<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai;

    public EventDanhSachChuongTrinhKhuyenMai(List<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai) {
        this.danhSachChuongTrinhKhuyenMai = danhSachChuongTrinhKhuyenMai;
    }

    public List<ChuongTrinhKhuyenMaiResponse> getDanhSachChuongTrinhKhuyenMai() {
        return danhSachChuongTrinhKhuyenMai;
    }

    public void setDanhSachChuongTrinhKhuyenMai(List<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai) {
        this.danhSachChuongTrinhKhuyenMai = danhSachChuongTrinhKhuyenMai;
    }
}
