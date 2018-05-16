package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IXoaDangKyView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class XoaDangKyImpl implements IXoaDangKyTMXPresenter, IFinishedListener {

    public IXoaDangKyView view;
    public DanhSachTuyenTMXDao dao;

    public XoaDangKyImpl(IXoaDangKyView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onXoaDangKySuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onXoaDangKyError(object);
    }


    @Override
    public void xoaDangKy(String registerId) {
        dao.xoaDangKyTMXDao(registerId, this);
    }
}