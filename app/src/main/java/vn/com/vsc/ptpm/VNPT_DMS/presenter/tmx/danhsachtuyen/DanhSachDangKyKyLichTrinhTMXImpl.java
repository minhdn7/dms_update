package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx.danhsachtuyen;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.DanhSachTuyenTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.danhsachtuyen.IDanhSachDangKyLichTrinhView;

/**
 * Created by MinhDN on 12/12/2017.
 */

public class DanhSachDangKyKyLichTrinhTMXImpl implements IDanhSachDangKyLichTrinhTMXPresenter, IFinishedListener {

    public IDanhSachDangKyLichTrinhView view;
    public DanhSachTuyenTMXDao dao;

    public DanhSachDangKyKyLichTrinhTMXImpl(IDanhSachDangKyLichTrinhView view) {
        this.view = view;
        this.dao = new DanhSachTuyenTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onDanhSachDangKyLichTrinhSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onDanhSachDangKyLichTrinhError(object);
    }

    @Override
    public void layDanhSachDangKyLichTrinh(int pageNo, int pageRec, String isActive, String sDate, String sWeek) {
        dao.layDanhSachDangKyLichTrinhTMXDao(pageNo, pageRec, isActive, sDate, sWeek, this);
    }
}
