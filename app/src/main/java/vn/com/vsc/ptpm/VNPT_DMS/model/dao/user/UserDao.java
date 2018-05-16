package vn.com.vsc.ptpm.VNPT_DMS.model.dao.user;

import okhttp3.HttpUrl;
import retrofit2.Call;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.BaseDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IService;

/**
 * Created by MinhDN on 19/3/2018.
 */

public class UserDao extends BaseDao implements IUserDao{
    private IService service = BaseService.createServiceDMS(IService.class);
    @Override
    public void changeImageAva(Integer image_id, Integer related_id, IFinishedListener iFinishedListener) {
        Call<Object> call = service.changeImageAva(image_id, related_id,
                                                    Config.username,
                                                    Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void deleteImage(Integer image_id, IFinishedListener iFinishedListener) {
        Call<Object> call = service.deleteImage(image_id,
                Config.username,
                Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }
}
