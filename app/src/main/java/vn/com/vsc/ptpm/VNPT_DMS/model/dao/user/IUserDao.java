package vn.com.vsc.ptpm.VNPT_DMS.model.dao.user;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;

/**
 * Created by MinhDN on 19/3/2018.
 */

public interface IUserDao {
    void changeImageAva(Integer image_id, Integer related_id, IFinishedListener iFinishedListener);
    void deleteImage(Integer image_id, IFinishedListener iFinishedListener);
}
