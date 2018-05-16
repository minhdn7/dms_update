package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 6/10/2017.
 */

public class ChungLoaiResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
