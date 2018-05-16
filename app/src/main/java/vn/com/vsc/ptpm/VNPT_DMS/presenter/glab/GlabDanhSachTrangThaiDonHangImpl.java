package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachTrangThaiDonHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachTrangThaiDonHangImpl implements IGlabDanhSachTrangThaiDonHangPresenter, IFinishedListener {
    public IGlabDanhSachTrangThaiDonHangView view;
    public UserGlabDao dao;

    public GlabDanhSachTrangThaiDonHangImpl(IGlabDanhSachTrangThaiDonHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabDanhSachTrangThaiDonHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabDanhSachTrangThaiDonHangError(object);
    }

    @Override
    public void getDanhSachTrangThaiDonHang() {
        dao.getDanhSachTrangThaiDonHang(this);
    }
}
