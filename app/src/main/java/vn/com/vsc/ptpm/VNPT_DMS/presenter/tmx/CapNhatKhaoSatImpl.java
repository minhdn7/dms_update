package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.UserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.SoLieuKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.view.viewevent.tmx.ICapNhatKhaoSatView;

/**
 * Created by MinhDN on 13/10/2017.
 */

public class CapNhatKhaoSatImpl implements ICapNhatKhaoSatPresenter, IFinishedListener {

    public ICapNhatKhaoSatView view;
    public UserTMXDao dao;

    public CapNhatKhaoSatImpl(ICapNhatKhaoSatView view) {
        this.view = view;
        this.dao = new UserTMXDao();
    }

    @Override
    public void onSuccess(Object object) {
        view.onCapNhatKhaoSatSuccess(object);
    }

    @Override
    public void onError(Object object) {
        view.onCapNhatKhaoSatError(object);
    }

    @Override
    public void updateKhaoSat(String idKhaoSat, String idKhachHang, String thangKhaoSat, String comment, String daiLy, List<SoLieuKhaoSatRequest> soLieuKhaoSatRequests) {
        dao.capNhatKhaoSatTMXDao(idKhaoSat, idKhachHang, thangKhaoSat, comment, daiLy, soLieuKhaoSatRequests, this);
    }
}
