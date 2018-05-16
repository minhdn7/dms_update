package vn.com.vsc.ptpm.VNPT_DMS.model.request.glab;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class GlabCapNhatDonHangRequest {
    @SerializedName("id")
    private String id;

    @SerializedName("ncc")
    private String ncc;

    @SerializedName("ngaydh")
    private String ngaydh;

    @SerializedName("fullname")
    private String fullname;


    @SerializedName("dob")
    private String dob;

    @SerializedName("gtinh")
    private String gtinh;

    @SerializedName("diachi")
    private String diachi;

    @SerializedName("email")
    private String email;

    @SerializedName("moblie")
    private String moblie;

    @SerializedName("diengiai")
    private String diengiai;

    @SerializedName("ngayyc")
    private String ngayyc;

    @SerializedName("phieugiao")
    private String phieugiao;

    @SerializedName("tien_km")
    private String tien_km;

    @SerializedName("product_cat_id")
    private String product_cat_id;

    @SerializedName("ghichu")
    @Getter @Setter
    private String ghiChu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTien_km() {
        return tien_km;
    }

    public void setTien_km(String tien_km) {
        this.tien_km = tien_km;
    }

    public String getProduct_cat_id() {
        return product_cat_id;
    }

    public void setProduct_cat_id(String product_cat_id) {
        this.product_cat_id = product_cat_id;
    }
}
