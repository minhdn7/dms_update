package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.ILayDanhSachVungView;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachVungImpl implements ILayDanhSachVungTMXPresenter, IFinishedListener {

    public ILayDanhSachVungView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachVungImpl(ILayDanhSachVungView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onLayDanhSachVungSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onLayDanhSachVungError(object);
    }


    @Override
    public void layDanhSachVung() {
        dao.layDanhSachVungTMXDao(this);
    }
}

