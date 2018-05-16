package vn.com.vsc.ptpm.VNPT_DMS.model.response.glab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 21/11/2017.
 */

public class DanhSachKhachHangResponse {
    @SerializedName("tenkh")
    @Expose
    private String tenkh;

    @SerializedName("orderer_name")
    @Expose
    private String ordererName;

    @SerializedName("supplier_code")
    @Expose
    private String supplierCode;

    @SerializedName("tong_phuphi_vnd")
    @Expose
    private String tongPhuphiVnd;

    @SerializedName("trangthai")
    @Expose
    private String trangthai;

    @SerializedName("supplier_name")
    @Expose
    private String supplierName;

    @SerializedName("ngay_lap")
    @Expose
    private String ngayLap;

    @SerializedName("orderer_code")
    @Expose
    private String ordererCode;

    @SerializedName("tong_khuyenmai")
    @Expose
    private String tongKhuyenmai;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("promotion_note")
    @Expose
    private String promotionNote;

    @SerializedName("ngay_gui")
    @Expose
    private String ngayGui;

    @SerializedName("trangthai_id")
    @Expose
    private String trangthaiId;

    @SerializedName("donnhap_id")
    @Expose
    private String donnhapId;

    @SerializedName("row_stt")
    @Expose
    private String rowStt;

    @SerializedName("nguoi_gui")
    @Expose
    private String nguoiGui;

    @SerializedName("nguoi_lap")
    @Expose
    private String nguoiLap;

    @SerializedName("nguoi_duyet")
    @Expose
    private String nguoiDuyet;

    @SerializedName("tongtien")
    @Expose
    private String tongtien;

    @SerializedName("note")
    @Expose
    private String note;

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getOrdererName() {
        return ordererName;
    }

    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getTongPhuphiVnd() {
        return tongPhuphiVnd;
    }

    public void setTongPhuphiVnd(String tongPhuphiVnd) {
        this.tongPhuphiVnd = tongPhuphiVnd;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getOrdererCode() {
        return ordererCode;
    }

    public void setOrdererCode(String ordererCode) {
        this.ordererCode = ordererCode;
    }

    public String getTongKhuyenmai() {
        return tongKhuyenmai;
    }

    public void setTongKhuyenmai(String tongKhuyenmai) {
        this.tongKhuyenmai = tongKhuyenmai;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPromotionNote() {
        return promotionNote;
    }

    public void setPromotionNote(String promotionNote) {
        this.promotionNote = promotionNote;
    }

    public String getNgayGui() {
        return ngayGui;
    }

    public void setNgayGui(String ngayGui) {
        this.ngayGui = ngayGui;
    }

    public String getTrangthaiId() {
        return trangthaiId;
    }

    public void setTrangthaiId(String trangthaiId) {
        this.trangthaiId = trangthaiId;
    }

    public String getDonnhapId() {
        return donnhapId;
    }

    public void setDonnhapId(String donnhapId) {
        this.donnhapId = donnhapId;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(String nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public String getNguoiLap() {
        return nguoiLap;
    }

    public void setNguoiLap(String nguoiLap) {
        this.nguoiLap = nguoiLap;
    }

    public String getNguoiDuyet() {
        return nguoiDuyet;
    }

    public void setNguoiDuyet(String nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
