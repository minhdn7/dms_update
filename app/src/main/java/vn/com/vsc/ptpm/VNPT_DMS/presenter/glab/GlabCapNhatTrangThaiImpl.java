package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.IGlabCapNhatTrangThaiPresenter;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.glab.IGlabDanhSachDonDatHangPresenter;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatTrangThaiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabCapNhatTrangThaiImpl implements IGlabCapNhatTrangThaiPresenter, IFinishedListener {
    public IGlabCapNhatTrangThaiView view;
    public UserGlabDao dao;

    public GlabCapNhatTrangThaiImpl(IGlabCapNhatTrangThaiView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabCapNhatTrangThaiSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabCapNhatTrangThaiError(object);
    }

    @Override
    public void getCapNhatTrangThai(int poId) {
        dao.getCapNhatTrangThai(poId, this);
    }
}
