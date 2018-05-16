package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhachHangDangKyView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachKhachHangDangKyImpl implements ILayDanhSachKhachHangDangKyTMXPresenter, IFinishedListener {

    public ILayDanhSachKhachHangDangKyView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachKhachHangDangKyImpl(ILayDanhSachKhachHangDangKyView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachKhachHangDangKySuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachKhachHangDangKyError(object);
    }


    @Override
    public void layDanhSachKhachHangDangKy(int pageNo, int pageRec, String registerId) {
        dao.layDanhSachKhachHangDangKyTMXDao(pageNo, pageRec, registerId, this);
    }
}

