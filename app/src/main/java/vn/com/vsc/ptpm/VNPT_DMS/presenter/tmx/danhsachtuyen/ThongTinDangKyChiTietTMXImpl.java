package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDanhSachDangKyLichTrinhView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IThongTinDangKyChiTietView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class ThongTinDangKyChiTietTMXImpl implements IThongTinDangKyChiTietTMXPresenter, IFinishedListener {

    public IThongTinDangKyChiTietView view;
    public DanhSachTuyenTMXDao dao;

    public ThongTinDangKyChiTietTMXImpl(IThongTinDangKyChiTietView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onThongTinDangKyChiTietSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onThongTinDangKyChiTietError(object);
    }

    @Override
    public void layThongTinDangKyChiTiet(String registerId) {
        dao.thongTinDangKyChiTietTMXDao(registerId, this);
    }
}
