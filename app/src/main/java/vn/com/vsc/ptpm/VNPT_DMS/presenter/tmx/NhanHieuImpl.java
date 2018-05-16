package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayNhanHieuView;

/**
 * Created by MinhDN on 6/10/2017.
 */

public class NhanHieuImpl implements INhanHieuPresenter, IFinishedListener {

    public ILayNhanHieuView view;
    public UserTMXDao dao;

    public NhanHieuImpl(ILayNhanHieuView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }



    @Override
    public void onSuccess(Object object) {
        view.onLayNhanHieuSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayNhanHieuError(object);
    }


    @Override
    public void layNhanHieu() {
        dao.layNhanHieuTMXDao(this);
    }
}
