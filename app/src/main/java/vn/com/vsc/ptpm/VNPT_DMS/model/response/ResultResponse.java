package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhDN on 3/10/2017.
 */

public class ResultResponse {
    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
