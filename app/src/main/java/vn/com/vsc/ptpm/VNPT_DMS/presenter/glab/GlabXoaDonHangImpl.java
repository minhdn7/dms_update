package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabThongTinDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabXoaDonHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabXoaDonHangImpl implements IGlabXoaDonHangPresenter, IFinishedListener {
    public IGlabXoaDonHangView view;
    public UserGlabDao dao;

    public GlabXoaDonHangImpl(IGlabXoaDonHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabXoaDonHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabXoaDonHangError(object);
    }

    @Override
    public void getXoaDonHang(int poId) {
        dao.getXoaDonHang(poId, this);
    }
}
