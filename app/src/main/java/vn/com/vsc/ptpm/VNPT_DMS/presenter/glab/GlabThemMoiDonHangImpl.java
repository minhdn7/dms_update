package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatDonHangThemMoiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatTrangThaiView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabThemMoiDonHangImpl implements IGlabCapNhatDonHangThemMoiPresenter, IFinishedListener {
    public IGlabCapNhatDonHangThemMoiView view;
    public UserGlabDao dao;

    public GlabThemMoiDonHangImpl(IGlabCapNhatDonHangThemMoiView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabCapNhatDonHangThemMoiSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabCapNhatDonHangThemMoiError(object);
    }

    @Override
    public void getCapNhatDonHangThemMoi(GlabThemMoiDonHangRequest request) {
        dao.getCapNhatDonHangThemMoi(request, this);
    }
}
