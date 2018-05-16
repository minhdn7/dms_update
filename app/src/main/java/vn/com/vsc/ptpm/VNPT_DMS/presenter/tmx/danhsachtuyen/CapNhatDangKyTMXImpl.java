package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ICapNhatDangKyView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDanhSachDangKyLichTrinhView;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class CapNhatDangKyTMXImpl implements ICapNhatDangKyTMXPresenter, IFinishedListener {
    public ICapNhatDangKyView view;
    public DanhSachTuyenTMXDao dao;

    public CapNhatDangKyTMXImpl(ICapNhatDangKyView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onCapNhatDangKySuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onCapNhatDangKyError(object);
    }

    @Override
    public void capNhatDangKy(String registerId, String sdate, String sweek, String snote) {
        dao.capNhatDangKyTMXDao(registerId, sdate, sweek, snote, this);
    }
}
