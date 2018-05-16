package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachNhomXetNghiemView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachNhomXetNghiemImpl implements IGlabDanhSachNhomXetNghiemPresenter, IFinishedListener {
    public IGlabDanhSachNhomXetNghiemView view;
    public UserGlabDao dao;

    public GlabDanhSachNhomXetNghiemImpl(IGlabDanhSachNhomXetNghiemView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onGlabDanhSachNhomXetNghiemSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabDanhSachNhomXetNghiemError(object);
    }

    @Override
    public void getDanhSachNhomXetNghiem() {
        dao.getDanhSachNhomXetNghiem(this);
    }
}
