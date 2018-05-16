package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachDonDatHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabDanhSachXetNghiemView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachXetNghiemImpl implements IGlabDanhSachXetNghiemPresenter, IFinishedListener {
    public IGlabDanhSachXetNghiemView view;
    public UserGlabDao dao;

    public GlabDanhSachXetNghiemImpl(IGlabDanhSachXetNghiemView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onGlabDanhSachXetNghiemSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabDanhSachXetNghiemError(object);
    }

    @Override
    public void getDanhSachXetNghiem(int pageno, int pagerec, int shopId) {
        dao.getDanhSachXetNghiem(pageno, pagerec, shopId, this);
    }
}
