package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IUserTMXView;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class UserTMXImpl implements IUserTMXPresenter, IFinishedListener {

    public IUserTMXView view;
    public UserTMXDao dao;

    public UserTMXImpl(IUserTMXView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onUserTMXSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onUserTMXError(object);
    }


    @Override
    public void getUserTMXPresenter() {
        dao.checkUserTMXDao(this);
    }
}
