package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatTrangThaiView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachNhaCungCapView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachNhaCungCapImpl implements IGlabDanhSachNhaCungCapPresenter, IFinishedListener {
    public IGlabDanhSachNhaCungCapView view;
    public UserGlabDao dao;

    public GlabDanhSachNhaCungCapImpl(IGlabDanhSachNhaCungCapView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabDanhSachNhaCungCapSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabDanhSachNhaCungCapError(object);
    }

    @Override
    public void getDanhSachNhaCungCap() {
        dao.getDanhSachNhaCungCap(this);
    }
}
