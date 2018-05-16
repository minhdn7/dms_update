package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayNhaPhanPhoiView;

/**
 * Created by MinhDN on 6/10/2017.
 */

public class NhaPhanPhoiImpl implements INhaPhanPhoiPresenter, IFinishedListener {

    public ILayNhaPhanPhoiView view;
    public UserTMXDao dao;

    public NhaPhanPhoiImpl(ILayNhaPhanPhoiView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }



    @Override
    public void onSuccess(Object object) {
        view.onLayNhaPhanPhoiSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayNhaPhanPhoiError(object);
    }


    @Override
    public void layNhaPhanPhoi(String key) {
        dao.layNhaPhanPhoiTMXDao(key, this);
    }
}
