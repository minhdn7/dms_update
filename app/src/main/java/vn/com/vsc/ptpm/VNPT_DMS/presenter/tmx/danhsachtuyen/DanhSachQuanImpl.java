package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachQuanView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachQuanImpl implements ILayDanhSachQuanTMXPresenter, IFinishedListener {

    public ILayDanhSachQuanView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachQuanImpl(ILayDanhSachQuanView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachQuanSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachQuanError(object);
    }


    @Override
    public void layDanhSachQuan(String provinceId) {
        dao.layDanhSachQuanTMXDao(provinceId, this);
    }
}

