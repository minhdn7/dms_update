package vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;

/**
 * Created by MinhDN on 12/12/2017.
 */

public interface IDanhSachTuyenTMXDao {
    void dangKyLichTrinhTMXDao(String sdate, String sweek, String snote, IFinishedListener iFinishedListener);
    void dangKyLichTrinhChiTietTMXDao(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse, IFinishedListener iFinishedListener);
    void capNhatLichTrinhChiTietTMXDao(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse, IFinishedListener iFinishedListener);
    void huyDanhSachDangKyTMXDao(String registerId, IFinishedListener iFinishedListener);
    void capNhatDangKyTMXDao(String registerId, String sdate, String sweek, String snote, IFinishedListener iFinishedListener);
    void xoaDangKyTMXDao(String registerId, IFinishedListener iFinishedListener);
    void thongTinDangKyChiTietTMXDao(String registerId, IFinishedListener iFinishedListener);
    void layDanhSachKhachHangDeDangKyTMXDao(int pageNo, int pageRec, String tuKhoa, String branch, String area, String province, String district, String listOrgId, IFinishedListener iFinishedListener);
    void layDanhSachKhachHangDangKyTMXDao(int pageNo, int pageRec, String registerId, IFinishedListener iFinishedListener);
    void layDanhSachVungTMXDao(IFinishedListener iFinishedListener);
    void layDanhSachKhuVucTMXDao(String branchId, IFinishedListener iFinishedListener);
    void layDanhSachTinhTMXDao(String areaId, IFinishedListener iFinishedListener);
    void layDanhSachTinhTMXDao(String branchId, String areaId, IFinishedListener iFinishedListener);
    void layDanhSachQuanTMXDao(String provinceId, IFinishedListener iFinishedListener);
    void layDanhSachDangKyLichTrinhTMXDao(int pageNo, int pageRec, String isActive, String sDate, String sWeek, IFinishedListener iFinishedListener);
    void layDanhSachIdKhachHangDao(String registerId, IFinishedListener iFinishedListener);
}

