package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatDonHangThemMoiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatDonHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabCapNhatDonHangImpl implements IGlabCapNhatDonHangPresenter, IFinishedListener {
    public IGlabCapNhatDonHangView view;
    public UserGlabDao dao;

    public GlabCapNhatDonHangImpl(IGlabCapNhatDonHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabCapNhatDonHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabCapNhatDonHangError(object);
    }

    @Override
    public void getCapNhatDonHang(GlabCapNhatDonHangRequest request) {
        dao.getCapNhatDonHang(request, this);
    }
}