package vn.com.vsc.ptpm.VNPT_DMS.model.response.glab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 24/11/2017.
 */

public class DanhSachNhaCungCapResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
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

    public DanhSachNhaCungCapResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
