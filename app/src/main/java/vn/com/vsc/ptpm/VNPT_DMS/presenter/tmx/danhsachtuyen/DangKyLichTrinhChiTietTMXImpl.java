package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDangKyLichTrinhChiTietView;

/**
 * Created by MinhDN on 20/12/2017.
 */

public class DangKyLichTrinhChiTietTMXImpl implements IDangKyLichTrinhChiTietTMXPresenter, IFinishedListener {
    public IDangKyLichTrinhChiTietView view;
    public DanhSachTuyenTMXDao dao;

    public DangKyLichTrinhChiTietTMXImpl(IDangKyLichTrinhChiTietView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onDangKyLichTrinhChiTietSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onDangKyLichTrinhChiTietError(object);
    }


    @Override
    public void dangKyLichTrinhChiTiet(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse) {
        dao.dangKyLichTrinhChiTietTMXDao(registerId, danhSachKhachHangDangKyResponse, this);
    }
}