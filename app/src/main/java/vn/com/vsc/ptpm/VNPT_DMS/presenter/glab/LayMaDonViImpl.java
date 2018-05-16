package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.LayMaDonViDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab.UserGlabDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.presenter.ILayMaDonViPresenter;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.ILayMaDonViView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.glab.IGlabCapNhatTrangThaiView;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class LayMaDonViImpl implements ILayMaDonViPresenter, IFinishedListener {
    public ILayMaDonViView view;
    public LayMaDonViDao dao;

    public LayMaDonViImpl(ILayMaDonViView view) {
        this.view = view;
        this.dao = new LayMaDonViDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayMaDonViSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayMaDonViError(object);
    }

    @Override
    public void getMaDonVi() {
        dao.layMaDonViDao(this);
    }
}
