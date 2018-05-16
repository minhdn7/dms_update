package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabChiTietDonHangView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabThongTinDonHangView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabChiTietDonHangImpl implements IGlabChiTietDonHangPresenter, IFinishedListener {
    public IGlabChiTietDonHangView view;
    public UserGlabDao dao;

    public GlabChiTietDonHangImpl(IGlabChiTietDonHangView view) {
        this.view = view;
        this.dao = new UserGlabDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onGlabChiTietDonHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onGlabChiTietDonHangError(object);
    }

    @Override
    public void getChiTietDonHang(int pageno, int pagerec, int poId, String tenLoaiSanPham) {
        dao.getChiTietDonHang(pageno, pagerec, poId, tenLoaiSanPham, this);
    }
}
