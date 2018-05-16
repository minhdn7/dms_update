package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachKhuVucView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachKhuVucImpl implements ILayDanhSachKhuVucTMXPresenter, IFinishedListener {

    public ILayDanhSachKhuVucView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachKhuVucImpl(ILayDanhSachKhuVucView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachKhuVucSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachKhuVucError(object);
    }


    @Override
    public void layDanhSachKhuVuc(String brandID) {
        dao.layDanhSachKhuVucTMXDao(brandID, this);
    }
}
