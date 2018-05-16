package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhachHangDeDangKyRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhachHangDeDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhuVucView;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class DanhSachKhachHangDeDangKyImpl implements ILayDanhSachKhachHangDeDangKyTMXPresenter, IFinishedListener {

    public ILayDanhSachKhachHangDeDangKyView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachKhachHangDeDangKyImpl(ILayDanhSachKhachHangDeDangKyView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachKhachHangDeDangKySuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachKhachHangDeDangKyError(object);
    }

    @Override
    public void layDanhSachKhachHangDeDangKy(DanhSachKhachHangDeDangKyRequest request) {
        dao.layDanhSachKhachHangDeDangKyTMXDao(
                                                request.getPageno(),
                                                request.getPagerec(),
                                                request.getTuKhoa(),
                                                request.getBranch(),
                                                request.getArea(),
                                                request.getProvince(),
                                                request.getDistrict(),
                                                request.getListOrgId(), this);
    }
}
