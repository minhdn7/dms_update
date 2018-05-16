package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 12/10/2017.
 */

public class KhachHangKhaoSatResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("is_daily")
    private String daiLy;

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

    public String getDaiLy() {
        return daiLy;
    }

    public void setDaiLy(String daiLy) {
        this.daiLy = daiLy;
    }
}
