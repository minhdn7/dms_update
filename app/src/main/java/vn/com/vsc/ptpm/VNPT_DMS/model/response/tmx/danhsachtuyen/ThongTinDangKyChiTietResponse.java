package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class ThongTinDangKyChiTietResponse {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("tuanthuchien")
    @Expose
    private String tuanthuchien;

    @SerializedName("is_active")
    @Expose
    private String isActive;

    @SerializedName("spermission")
    @Expose
    private String spermission;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("nvkd")
    @Expose
    private String nvkd;

    @SerializedName("seller")
    @Expose
    private String seller;

    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("week")
    @Expose
    private String week;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSpermission() {
        return spermission;
    }

    public void setSpermission(String spermission) {
        this.spermission = spermission;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
