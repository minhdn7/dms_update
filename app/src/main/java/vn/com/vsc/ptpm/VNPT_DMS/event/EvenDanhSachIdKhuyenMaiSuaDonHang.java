package vn.com.vsc.ptpm.VNPT_DMS.event;

import java.util.ArrayList;

/**
 * Created by MinhDN on 24/3/2017.
 */

public class EvenDanhSachIdKhuyenMaiSuaDonHang {
    private ArrayList<String> danhSachIdKhuyenMai;

    public EvenDanhSachIdKhuyenMaiSuaDonHang(ArrayList<String> danhSachIdKhuyenMai) {
        this.danhSachIdKhuyenMai = danhSachIdKhuyenMai;
    }

    public ArrayList<String> getDanhSachIdKhuyenMai() {
        return danhSachIdKhuyenMai;
    }

    public void setDanhSachIdKhuyenMai(ArrayList<String> danhSachIdKhuyenMai) {
        this.danhSachIdKhuyenMai = danhSachIdKhuyenMai;
    }
}
