package vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.SoLieuKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;

/**
 * Created by MinhDN on 2/10/2017.
 */

public interface IUserTMXDao {
    void checkUserTMXDao(IFinishedListener iFinishedListener);
    void getDanhSachKhaoSatTMXDao(DanhSachKhaoSatRequest request, IFinishedListener iFinishedListener);
    void xoaKhaoSatTMXDao(String idSurvey, IFinishedListener iFinishedListener);
    void layNhanHieuTMXDao(IFinishedListener iFinishedListener);
    void layChungLoaiTMXDao(IFinishedListener iFinishedListener);
    void layNhaPhanPhoiTMXDao(String key, IFinishedListener iFinishedListener);
    void layThongTinKhaoSatChiTietTMXDao(String id, IFinishedListener iFinishedListener);
    void layKhachHangTMXDao(String key, IFinishedListener iFinishedListener);
    void layThongTinKhachHangTMXDao(String orgId, IFinishedListener iFinishedListener);
    void capNhatKhaoSatTMXDao(String idKhaoSat, String idKhachHang, String thangKhaoSat, String comment, String daiLy, List<SoLieuKhaoSatRequest> soLieuKhaoSatRequests, IFinishedListener iFinishedListener);
}
