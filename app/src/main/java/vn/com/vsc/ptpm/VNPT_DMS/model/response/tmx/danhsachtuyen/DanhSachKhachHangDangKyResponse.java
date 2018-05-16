package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 13/12/2017.
 */

public class DanhSachKhachHangDangKyResponse {
    @SerializedName("org_id")
    @Expose
    private String orgId;

    @SerializedName("org_name")
    @Expose
    private String orgName;

    @SerializedName("row_stt")
    @Expose
    private String rowStt;

    @SerializedName("thu")
    @Expose
    private String thu;

    @SerializedName("sat")
    @Expose
    private String sat;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("wed")
    @Expose
    private String wed;

    @SerializedName("mon")
    @Expose
    private String mon;

    @SerializedName("org_code")
    @Expose
    private String orgCode;

    @SerializedName("tue")
    @Expose
    private String tue;

    @SerializedName("sun")
    @Expose
    private String sun;

    @SerializedName("fri")
    @Expose
    private String fri;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getThu() {
        return thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getSat() {
        return sat;
    }

    public void setSat(String sat) {
        this.sat = sat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWed() {
        return wed;
    }

    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTue() {
        return tue;
    }

    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getSun() {
        return sun;
    }

    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getFri() {
        return fri;
    }

    public void setFri(String fri) {
        this.fri = fri;
    }
}
