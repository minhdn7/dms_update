package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;

/**
 * Created by MinhDN on 12/12/2017.
 */

public interface ICapNhatLichTrinhChiTietTMXPresenter {
    void capNhatLichTrinhChiTiet(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse);
}
