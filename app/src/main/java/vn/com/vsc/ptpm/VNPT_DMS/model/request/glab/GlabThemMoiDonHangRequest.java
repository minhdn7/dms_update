package vn.com.vsc.ptpm.VNPT_DMS.model.request.glab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 1/12/2017.
 */

public class GlabThemMoiDonHangRequest {
    @SerializedName("ncc")
    @Expose
    private String ncc;

    @SerializedName("ngaydh")
    @Expose
    private String ngaydh;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("gtinh")
    @Expose
    private String gtinh;

    @SerializedName("diachi")
    @Expose
    private String diachi;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("moblie")
    @Expose
    private String moblie;

    @SerializedName("diengiai")
    @Expose
    private String diengiai;

    @SerializedName("ngayyc")
    @Expose
    private String ngayyc;

    @SerializedName("phieugiao")
    @Expose
    private String phieugiao;

    @SerializedName("tien_km")
    @Expose
    private String tienKm;

    @SerializedName("product_cat_id")
    @Expose
    private String productCatId;

    @SerializedName("ghichu")
    @Getter @Setter
    private String ghiChu;

    public String getNcc() {
        return ncc;
    }

    public void setNcc(String ncc) {
        this.ncc = ncc;
    }

    public String getNgaydh() {
        return ngaydh;
    }

    public void setNgaydh(String ngaydh) {
        this.ngaydh = ngaydh;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGtinh() {
        return gtinh;
    }

    public void setGtinh(String gtinh) {
        this.gtinh = gtinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    public String getDiengiai() {
        return diengiai;
    }

    public void setDiengiai(String diengiai) {
        this.diengiai = diengiai;
    }

    public String getNgayyc() {
        return ngayyc;
    }

    public void setNgayyc(String ngayyc) {
        this.ngayyc = ngayyc;
    }

    public String getPhieugiao() {
        return phieugiao;
    }

    public void setPhieugiao(String phieugiao) {
        this.phieugiao = phieugiao;
    }

    public String getTienKm() {
        return tienKm;
    }

    public void setTienKm(String tienKm) {
        this.tienKm = tienKm;
    }

    public String getProductCatId() {
        return productCatId;
    }

    public void setProductCatId(String productCatId) {
        this.productCatId = productCatId;
    }
}
