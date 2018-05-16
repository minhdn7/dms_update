package vn.com.vsc.ptpm.VNPT_DMS.event;

import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ChuongTrinhKhuyenMaiResponse;

/**
 * Created by MinhDN on 20/3/2017.
 */

public class EventDanhSachLuaChonKhuyenMai {
    private ArrayList<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai;

    public EventDanhSachLuaChonKhuyenMai(ArrayList<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai) {
        this.danhSachChuongTrinhKhuyenMai = danhSachChuongTrinhKhuyenMai;
    }

    public ArrayList<ChuongTrinhKhuyenMaiResponse> getDanhSachChuongTrinhKhuyenMai() {
        return danhSachChuongTrinhKhuyenMai;
    }

    public void setDanhSachChuongTrinhKhuyenMai(ArrayList<ChuongTrinhKhuyenMaiResponse> danhSachChuongTrinhKhuyenMai) {
        this.danhSachChuongTrinhKhuyenMai = danhSachChuongTrinhKhuyenMai;
    }
}
