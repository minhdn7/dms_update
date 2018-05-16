package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 16/3/2017.
 */

public class ChuongTrinhKhuyenMaiResponse {
    private String id;

    @SerializedName("promotion_type")
    private String promotionType;

    @SerializedName("row_stt")
    private String rowStt;

    @SerializedName("is_active")
    private String isSctive;

    @SerializedName("ngayhieuluc")
    private String ngayHieuLuc;

    private String description;

    private String name;

    private String promotion;

    private String maid;

    @SerializedName("option_promotion_type")
    private String optionPromotionType;

    @SerializedName("updated_by")
    private String updatedBy;

    private Boolean luaChonKhuyenMai = false;
//    public ChuongTrinhKhuyenMaiResponse() {
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ChuongTrinhKhuyenMaiResponse(String id, String promotionType, String rowStt, String isSctive, String ngayHieuLuc, String description, String name, String promotion, String maid, String optionPromotionType, String updatedBy) {
        this.id = id;
        this.promotionType = promotionType;
        this.rowStt = rowStt;
        this.isSctive = isSctive;
        this.ngayHieuLuc = ngayHieuLuc;
        this.description = description;
        this.name = name;
        this.promotion = promotion;
        this.maid = maid;
        this.optionPromotionType = optionPromotionType;
        this.updatedBy = updatedBy;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getIsSctive() {
        return isSctive;
    }

    public void setIsSctive(String isSctive) {
        this.isSctive = isSctive;
    }

    public String getNgayHieuLuc() {
        return ngayHieuLuc;
    }

    public void setNgayHieuLuc(String ngayHieuLuc) {
        this.ngayHieuLuc = ngayHieuLuc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getOptionPromotionType() {
        return optionPromotionType;
    }

    public void setOptionPromotionType(String optionPromotionType) {
        this.optionPromotionType = optionPromotionType;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean getLuaChonKhuyenMai() {
        return luaChonKhuyenMai;
    }

    public void setLuaChonKhuyenMai(Boolean luaChonKhuyenMai) {
        this.luaChonKhuyenMai = luaChonKhuyenMai;
    }
}
