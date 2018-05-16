package vn.com.vsc.ptpm.VNPT_DMS.model.request.glab;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabDanhSachDonDatHangRequest {
    @SerializedName("id")
    private String id;

    @SerializedName("rd")
    private String rd;

    @SerializedName("sd1")
    private String sd1;

    @SerializedName("sd2")
    private String sd2;

    @SerializedName("ndh")
    private String ndh;

    @SerializedName("tensp")
    private String tensp;

    @SerializedName("ncc")
    private String ncc;

    @SerializedName("tt")
    private String tt;

    @SerializedName("kh")
    private String kh;

    @SerializedName("st")
    private String st;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getSd1() {
        return sd1;
    }

    public void setSd1(String sd1) {
        this.sd1 = sd1;
    }

    public String getSd2() {
        return sd2;
    }

    public void setSd2(String sd2) {
        this.sd2 = sd2;
    }

    public String getNdh() {
        return ndh;
    }

    public void setNdh(String ndh) {
        this.ndh = ndh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getNcc() {
        return ncc;
    }

    public void setNcc(String ncc) {
        this.ncc = ncc;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
