package vn.com.vsc.ptpm.VNPT_DMS.presenter.tmx;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.SoLieuKhaoSatRequest;

/**
 * Created by MinhDN on 13/10/2017.
 */

public interface ICapNhatKhaoSatPresenter {
    void updateKhaoSat(String idKhaoSat, String idKhachHang, String thangKhaoSat, String comment, String daiLy, List<SoLieuKhaoSatRequest> soLieuKhaoSatRequests);
}
