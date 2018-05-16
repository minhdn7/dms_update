package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachTinhView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachTinhImpl implements ILayDanhSachTinhTMXPresenter, IFinishedListener {

    public ILayDanhSachTinhView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachTinhImpl(ILayDanhSachTinhView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachTinhSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachTinhError(object);
    }


    @Override
    public void layDanhSachTinh(String areaId) {
        dao.layDanhSachTinhTMXDao(areaId, this);
    }

    @Override
    public void layDanhSachTinh(String brandId, String areaId) {
        dao.layDanhSachTinhTMXDao(brandId, areaId, this);
    }
}
