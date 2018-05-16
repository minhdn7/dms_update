package vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;

/**
 * Created by MinhDN on 20/11/2017.
 */

public interface IUserGlabDao {
    void getDanhSachDonDatHang(int pageno, int pagerec, GlabDanhSachDonDatHangRequest request, IFinishedListener iFinishedListener);
    void getDanhSachNhomXetNghiem(IFinishedListener iFinishedListener);
    void getDanhSachXetNghiem(int pageno, int pagerec, int shopId, IFinishedListener iFinishedListener);
    void getDanhSachTrangThaiDonHang(IFinishedListener iFinishedListener);

    void getThongTinDonHang(int poId, IFinishedListener iFinishedListener);
    void getXoaDonHang(int poId, IFinishedListener iFinishedListener);
    void getChiTietDonHang(int pageno, int pagerec, int poId, String tenLoaiSanPham,IFinishedListener iFinishedListener);
    void getCapNhatTrangThai(int poId, IFinishedListener iFinishedListener);
    void getDanhSachNhaCungCap(IFinishedListener iFinishedListener);
    void getCapNhatDonHangThemMoi(GlabThemMoiDonHangRequest request, IFinishedListener iFinishedListener);
    void getCapNhatDonHang(GlabCapNhatDonHangRequest request, IFinishedListener iFinishedListener);
}
