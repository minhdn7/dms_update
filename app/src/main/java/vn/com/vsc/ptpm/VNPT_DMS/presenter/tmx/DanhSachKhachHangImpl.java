package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IDanhSachKhachHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IDanhSachKhaoSatView;

/**
 * Created by MinhDN on 11/10/2017.
 */

public class DanhSachKhachHangImpl implements IDanhSachKhachHangPresenter, IFinishedListener {

    public IDanhSachKhachHangView view;
    public UserTMXDao dao;

    public DanhSachKhachHangImpl(IDanhSachKhachHangView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayKhachHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayKhachHangError(object);
    }

    @Override
    public void getDanhSachKhachHang(String key) {
        dao.layKhachHangTMXDao(key, this);
    }
}
