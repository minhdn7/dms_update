package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDangKyLichTrinhChiTietView;

/**
 * Created by MinhDN on 20/12/2017.
 */

public class CapNhatLichTrinhChiTietTMXImpl implements ICapNhatLichTrinhChiTietTMXPresenter, IFinishedListener {
    public IDangKyLichTrinhChiTietView view;
    public DanhSachTuyenTMXDao dao;

    public CapNhatLichTrinhChiTietTMXImpl(IDangKyLichTrinhChiTietView view) {
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
    public void capNhatLichTrinhChiTiet(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse) {
        dao.capNhatLichTrinhChiTietTMXDao(registerId, danhSachKhachHangDangKyResponse, this);
    }
}