package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 12/12/2017.
 */

public class DanhSachDangKyLichTrinhResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("row_stt")
    @Expose
    private String rowStt;

    @SerializedName("tuanthuchien")
    @Expose
    private String tuanthuchien;

    @SerializedName("is_active")
    @Expose
    private String isActive;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("nvkd")
    @Expose
    private String nvkd;

    @SerializedName("seller")
    @Expose
    private String seller;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getTuanthuchien() {
        return tuanthuchien;
    }

    public void setTuanthuchien(String tuanthuchien) {
        this.tuanthuchien = tuanthuchien;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNvkd() {
        return nvkd;
    }

    public void setNvkd(String nvkd) {
        this.nvkd = nvkd;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
