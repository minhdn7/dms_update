package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachIdDaDangKyView;

/**
 * Created by MinhDN on 19/12/2017.
 */

public class DanhSachIdKhachHangDangKyTMXImpl implements IDanhSachIdKhachHangDangKyTMXPresenter, IFinishedListener {

    public ILayDanhSachIdDaDangKyView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachIdKhachHangDangKyTMXImpl(ILayDanhSachIdDaDangKyView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachIdDaDangKySuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachIdDaDangKyError(object);
    }


    @Override
    public void layDanhSachIdKhachHang(String registerId) {
        dao.layDanhSachIdKhachHangDao(registerId, this);
    }
}
