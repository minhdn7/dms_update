package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDangKyLichTrinhView;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class DangKyLichTrinhTMXImpl implements IDangKyLichTrinhTMXPresenter, IFinishedListener {
    public IDangKyLichTrinhView view;
    public DanhSachTuyenTMXDao dao;

    public DangKyLichTrinhTMXImpl(IDangKyLichTrinhView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }
    @Override
    public void onSuccess(Object object) {
        view.onDangKyLichTrinhSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onDangKyLichTrinhError(object);
    }

    @Override
    public void dangKyLichTrinh(String sdate, String sweek, String snote) {
        dao.dangKyLichTrinhTMXDao(sdate, sweek, snote, this);
    }
}
