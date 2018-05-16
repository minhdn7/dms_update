package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;

/**
 * Created by MinhDN on 20/11/2017.
 */

public interface IGlabDanhSachDonDatHangPresenter {
    void getDanhSachDonDatHang(int pageno, int pagerec, GlabDanhSachDonDatHangRequest request);
}
