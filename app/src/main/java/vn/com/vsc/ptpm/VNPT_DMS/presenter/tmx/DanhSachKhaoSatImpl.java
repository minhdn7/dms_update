package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.IDanhSachKhaoSatView;

/**
 * Created by MinhDN on 4/10/2017.
 */

public class DanhSachKhaoSatImpl implements IDanhSachKhaoSatPresenter, IFinishedListener {
    public IDanhSachKhaoSatView view;
    public UserTMXDao dao;

    public DanhSachKhaoSatImpl(IDanhSachKhaoSatView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onDanhSachKhaoSatSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onDanhSachKhaoSatSuccess(object);
    }


    @Override
    public void getDanhSachKhaoSatresenter(DanhSachKhaoSatRequest request) {
        dao.getDanhSachKhaoSatTMXDao(request, this);
    }
}
