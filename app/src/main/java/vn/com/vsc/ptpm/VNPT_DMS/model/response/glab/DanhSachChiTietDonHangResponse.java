package vn.com.vsc.ptpm.VNPT_DMS.model.response.glab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 23/11/2017.
 */

public class DanhSachChiTietDonHangResponse {
    @SerializedName("phuphi_gianhap_vnd")
    @Expose
    private String phuphiGianhapVnd;

    @SerializedName("loaisp_id")
    @Expose
    private String loaispId;

    @SerializedName("loaisp_ten")
    @Expose
    private String loaispTen;

    @SerializedName("quycach_donggoi")
    @Expose
    private String quycachDonggoi;

    @SerializedName("ma_sp")
    @Expose
    private String maSp;

    @SerializedName("donvi_tinh")
    @Expose
    private String donviTinh;

    @SerializedName("hangton")
    @Expose
    private String hangton;

    @SerializedName("loaitien_id")
    @Expose
    private String loaitienId;

    @SerializedName("gianhap")
    @Expose
    private String gianhap;

    @SerializedName("tygia")
    @Expose
    private String tygia;

    @SerializedName("quydoi_vnd")
    @Expose
    private String quydoiVnd;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("is_ordered")
    @Expose
    private String isOrdered;

    @SerializedName("stt")
    @Expose
    private String stt;

    @SerializedName("selling_price_vnd")
    @Expose
    private String sellingPriceVnd;

    @SerializedName("gianhap_vnd")
    @Expose
    private String gianhapVnd;

    @SerializedName("gianhap_vnd_cu")
    @Expose
    private String gianhapVndCu;

    @SerializedName("thanhtien")
    @Expose
    private String thanhtien;

    @SerializedName("soluong")
    @Expose
    private String soluong;

    @SerializedName("tax_rate")
    @Expose
    private String taxRate;

    public String getPhuphiGianhapVnd() {
        return phuphiGianhapVnd;
    }

    public void setPhuphiGianhapVnd(String phuphiGianhapVnd) {
        this.phuphiGianhapVnd = phuphiGianhapVnd;
    }

    public String getLoaispId() {
        return loaispId;
    }

    public void setLoaispId(String loaispId) {
        this.loaispId = loaispId;
    }

    public String getLoaispTen() {
        return loaispTen;
    }

    public void setLoaispTen(String loaispTen) {
        this.loaispTen = loaispTen;
    }

    public String getQuycachDonggoi() {
        return quycachDonggoi;
    }

    public void setQuycachDonggoi(String quycachDonggoi) {
        this.quycachDonggoi = quycachDonggoi;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getDonviTinh() {
        return donviTinh;
    }

    public void setDonviTinh(String donviTinh) {
        this.donviTinh = donviTinh;
    }

    public String getHangton() {
        return hangton;
    }

    public void setHangton(String hangton) {
        this.hangton = hangton;
    }

    public String getLoaitienId() {
        return loaitienId;
    }

    public void setLoaitienId(String loaitienId) {
        this.loaitienId = loaitienId;
    }

    public String getGianhap() {
        return gianhap;
    }

    public void setGianhap(String gianhap) {
        this.gianhap = gianhap;
    }

    public String getTygia() {
        return tygia;
    }

    public void setTygia(String tygia) {
        this.tygia = tygia;
    }

    public String getQuydoiVnd() {
        return quydoiVnd;
    }

    public void setQuydoiVnd(String quydoiVnd) {
        this.quydoiVnd = quydoiVnd;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIsOrdered() {
        return isOrdered;
    }

    public void setIsOrdered(String isOrdered) {
        this.isOrdered = isOrdered;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getSellingPriceVnd() {
        return sellingPriceVnd;
    }

    public void setSellingPriceVnd(String sellingPriceVnd) {
        this.sellingPriceVnd = sellingPriceVnd;
    }

    public String getGianhapVnd() {
        return gianhapVnd;
    }

    public void setGianhapVnd(String gianhapVnd) {
        this.gianhapVnd = gianhapVnd;
    }

    public String getGianhapVndCu() {
        return gianhapVndCu;
    }

    public void setGianhapVndCu(String gianhapVndCu) {
        this.gianhapVndCu = gianhapVndCu;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

}
