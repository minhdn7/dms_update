package vn.com.vsc.ptpm.VNPT_DMS.event;

/**
 * Created by MinhDN on 13/4/2017.
 */

public class EventTinhKhuyenMai {
    boolean bTongTienKhuyenMaiDuong;

    public boolean isbTongTienKhuyenMaiDuong() {
        return bTongTienKhuyenMaiDuong;
    }

    public void setbTongTienKhuyenMaiDuong(boolean bTongTienKhuyenMaiDuong) {
        this.bTongTienKhuyenMaiDuong = bTongTienKhuyenMaiDuong;
    }

    public EventTinhKhuyenMai(boolean bTongTienKhuyenMaiDuong) {
        this.bTongTienKhuyenMaiDuong = bTongTienKhuyenMaiDuong;
    }
}
