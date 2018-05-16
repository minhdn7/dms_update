package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachDonDatHangImpl implements IGlabDanhSachDonDatHangPresenter, IFinishedListener {
    public IGlabDanhSachDonDatHangView view;
    public UserGlabDao dao;

    public GlabDanhSachDonDatHangImpl(IGlabDanhSachDonDatHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onGlabDanhSachDonDatHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabDanhSachDonDatHangError(object);
    }

    @Override
    public void getDanhSachDonDatHang(int pageno, int pagerec, GlabDanhSachDonDatHangRequest request) {
        dao.getDanhSachDonDatHang(pageno, pagerec, request, this);
    }
}
