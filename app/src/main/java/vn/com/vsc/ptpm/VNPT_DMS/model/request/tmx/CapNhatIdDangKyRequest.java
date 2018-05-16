package vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 19/12/2017.
 */

public class CapNhatIdDangKyRequest {
    @SerializedName("targetId")
    @Expose
    private String targetId;

    @SerializedName("targetValue")
    @Expose
    private String targetValue;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public CapNhatIdDangKyRequest(String targetId, String targetValue) {
        this.targetId = targetId;
        this.targetValue = targetValue;
    }
}
