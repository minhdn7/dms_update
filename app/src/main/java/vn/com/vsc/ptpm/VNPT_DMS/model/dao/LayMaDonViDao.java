package vn.com.vsc.ptpm.VNPT_DMS.model.dao;

import okhttp3.HttpUrl;
import retrofit2.Call;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx.IUserTMXDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IService;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class LayMaDonViDao extends BaseDao implements ILayMaDonViDao {
    private IService service;
    @Override
    public void layMaDonViDao(IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layMaDonVi(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }
}
