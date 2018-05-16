package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabThongTinDonHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabThongTinDonHangImpl implements IGlabThongTinDonHangPresenter, IFinishedListener {
    public IGlabThongTinDonHangView view;
    public UserGlabDao dao;

    public GlabThongTinDonHangImpl(IGlabThongTinDonHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onGlabThongTinDonHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabThongTinDonHangError(object);
    }

    @Override
    public void getThongTinDonHang(int poId) {
        dao.getThongTinDonHang(poId, this);
    }
}
