package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ILayThongTinKhachHangView;

/**
 * Created by MinhDN on 16/10/2017.
 */

public class ThongTinKhachHangKhaoSatImpl implements IThongTinKhachHangKhaoSatPresenter, IFinishedListener {
    public ILayThongTinKhachHangView view;
    public UserTMXDao dao;

    public ThongTinKhachHangKhaoSatImpl(ILayThongTinKhachHangView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }



    @Override
    public void onSuccess(Object object) {
        view.onLayThongTinKhachHangSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayThongTinKhachHangError(object);
    }


    @Override
    public void layThongTinKhachHangKhaosat(String orgId) {
        dao.layThongTinKhachHangTMXDao(orgId, this);
    }
}
