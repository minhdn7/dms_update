package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayChungLoaiView;

/**
 * Created by MinhDN on 6/10/2017.
 */

public class ChungLoaiImpl implements IChungLoaiPresenter, IFinishedListener {

    public ILayChungLoaiView view;
    public UserTMXDao dao;

    public ChungLoaiImpl(ILayChungLoaiView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }



    @Override
    public void onSuccess(Object object) {
        view.onLayChungLoaiSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayChungLoaiError(object);
    }


    @Override
    public void layChungLoai() {
        dao.layChungLoaiTMXDao(this);
    }
}

