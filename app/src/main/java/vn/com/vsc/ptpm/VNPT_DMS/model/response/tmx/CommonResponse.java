package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhDN on 3/10/2017.
 */

public class CommonResponse {
    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
//    List<ResultResponse> resultResponses;
//
//    public List<ResultResponse> getResultResponses() {
//        return resultResponses;
//    }
//
//    public void setResultResponses(List<ResultResponse> resultResponses) {
//        this.resultResponses = resultResponses;
//    }
}
