package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 16/10/2017.
 */

public class ThongTinKhachHangKhaoSatResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("is_daily")
    private String is_daily;

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

    public String getIs_daily() {
        return is_daily;
    }

    public void setIs_daily(String is_daily) {
        this.is_daily = is_daily;
    }
}
