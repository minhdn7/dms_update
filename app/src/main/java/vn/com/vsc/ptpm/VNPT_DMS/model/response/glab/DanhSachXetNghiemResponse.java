package vn.com.vsc.ptpm.VNPT_DMS.model.response.glab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 23/11/2017.
 */

public class DanhSachXetNghiemResponse {
    @SerializedName("ngay_hh")
    @Expose
    private String ngayHh;

    @SerializedName("ma_sp")
    @Expose
    private String maSanPham;

    @SerializedName("group_name")
    @Expose
    private String groupName;

    @SerializedName("total_vote")
    @Expose
    private String totalVote;

    @SerializedName("giaban")
    @Expose
    private String giaban2;

    @SerializedName("product_id")
    @Expose
    private String productId;

    @SerializedName("product_cat_id")
    @Expose
    private String productCatId;

    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("promotion_amount")
    @Expose
    private String promotionAmount;

    @SerializedName("ds_donvi")
    @Expose
    private String dsDonvi;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("avg_point")
    @Expose
    private String avgPoint;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("row_stt")
    @Expose
    private String rowStt;

    @SerializedName("thue")
    @Expose
    private String thue;

    @SerializedName("total_comment")
    @Expose
    private String totalComment;

    @SerializedName("gia_truocthue")
    @Expose
    private String giaTruocthue;

    @SerializedName("selling_price_vnd")
    @Expose
    private String sellingPriceVnd;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("unit_display")
    @Expose
    private String unitDisplay;

    @SerializedName("gia_ban")
    @Expose
    private String gia_ban;

    @SerializedName("expired_date")
    @Expose
    private String expiredDate;

    @SerializedName("tax_rate")
    @Expose
    private String taxRate;

    @SerializedName("soluong")
    @Expose
    private String soluong;

    public String getNgayHh() {
        return ngayHh;
    }

    public void setNgayHh(String ngayHh) {
        this.ngayHh = ngayHh;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(String totalVote) {
        this.totalVote = totalVote;
    }

    public String getGiaban2() {
        return giaban2;
    }

    public void setGiaban2(String giaban) {
        this.giaban2 = giaban;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCatId() {
        return productCatId;
    }

    public void setProductCatId(String productCatId) {
        this.productCatId = productCatId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(String promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getDsDonvi() {
        return dsDonvi;
    }

    public void setDsDonvi(String dsDonvi) {
        this.dsDonvi = dsDonvi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvgPoint() {
        return avgPoint;
    }

    public void setAvgPoint(String avgPoint) {
        this.avgPoint = avgPoint;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getThue() {
        return thue;
    }

    public void setThue(String thue) {
        this.thue = thue;
    }

    public String getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(String totalComment) {
        this.totalComment = totalComment;
    }

    public String getGiaTruocthue() {
        return giaTruocthue;
    }

    public void setGiaTruocthue(String giaTruocthue) {
        this.giaTruocthue = giaTruocthue;
    }

    public String getSellingPriceVnd() {
        return sellingPriceVnd;
    }

    public void setSellingPriceVnd(String sellingPriceVnd) {
        this.sellingPriceVnd = sellingPriceVnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitDisplay() {
        return unitDisplay;
    }

    public void setUnitDisplay(String unitDisplay) {
        this.unitDisplay = unitDisplay;
    }

    public String getGiaBan() {
        return gia_ban;
    }

    public void setGiaBan(String giaBan) {
        this.gia_ban = giaBan;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }
}
