package vn.com.vsc.ptpm.VNPT_DMS.presenter.user;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.user.UserDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.user.IDeleteImageView;

/**
 * Created by MinhDN on 19/3/2018.
 */

public class DeleteImageImpl implements DeleteImagePresenter, IFinishedListener {
    public IDeleteImageView view;
    public UserDao dao;

    public DeleteImageImpl(IDeleteImageView view) {
        this.view = view;
        this.dao = new UserDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onDeleteImageSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onDeleteImageError(object);
    }


    @Override
    public void deleteImage(Integer image_id) {
        dao.deleteImage(image_id, this);
    }
}
