package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IThongTinKhaoSatView;

/**
 * Created by MinhDN on 10/10/2017.
 */

public class ThongTinKhaoSatImpl implements IThongTinKhaoSatPresenter, IFinishedListener {
    public IThongTinKhaoSatView view;
    public UserTMXDao dao;

    public ThongTinKhaoSatImpl(IThongTinKhaoSatView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayThongTinKhaoSatSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayThongTinKhaoSatError(object);
    }

    @Override
    public void layThongTinKhaoSat(String id) {
        dao.layThongTinKhaoSatChiTietTMXDao(id, this);
    }
}
