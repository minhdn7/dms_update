package vn.com.vsc.ptpm.VNPT_DMS.presenter.user;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.user.UserDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.user.IChangeImageView;

/**
 * Created by MinhDN on 19/3/2018.
 */

public class ChangeImageAvaImpl implements ChangeImageAvaPresenter, IFinishedListener {
    public IChangeImageView view;
    public UserDao dao;

    public ChangeImageAvaImpl(IChangeImageView view) {
        this.view = view;
        this.dao = new UserDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onChangeImageSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onChangeImageError(object);
    }

    @Override
    public void changeImage(Integer image_id, Integer related_id) {
        dao.changeImageAva(image_id, related_id, this);
    }
}
