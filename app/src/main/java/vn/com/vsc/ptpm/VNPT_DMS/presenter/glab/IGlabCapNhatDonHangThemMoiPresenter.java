package vn.com.vsc.ptpm.VNPT_DMS.presenter.glab;

import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabThemMoiDonHangRequest;

/**
 * Created by MinhDN on 20/11/2017.
 */

public interface IGlabCapNhatDonHangThemMoiPresenter {
    void getCapNhatDonHangThemMoi(GlabThemMoiDonHangRequest request);
}
