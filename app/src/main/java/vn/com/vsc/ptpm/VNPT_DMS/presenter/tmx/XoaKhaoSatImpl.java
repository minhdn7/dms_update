package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IUserTMXView;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IXoaPhieuKhaoSatView;

/**
 * Created by MinhDN on 4/10/2017.
 */

public class XoaKhaoSatImpl implements IXoaKhaoSatPresenter, IFinishedListener {

    public IXoaPhieuKhaoSatView view;
    public UserTMXDao dao;

    public XoaKhaoSatImpl(IXoaPhieuKhaoSatView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }



    @Override
    public void onSuccess(Object object) {
        view.onXoaKhaoSatSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onXoaKhaoSatError(object);
    }


    @Override
    public void xoaKhaoSat(String surveyId) {
        dao.xoaKhaoSatTMXDao(surveyId, this);
    }
}
