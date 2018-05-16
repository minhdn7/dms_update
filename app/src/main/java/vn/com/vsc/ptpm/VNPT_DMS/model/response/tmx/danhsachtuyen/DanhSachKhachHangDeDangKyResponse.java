package vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class DanhSachKhachHangDeDangKyResponse {
    @SerializedName("org_name")
    @Expose
    private String orgName;

    @SerializedName("org_id")
    @Expose
    private String orgId;

    @SerializedName("row_stt")
    @Expose
    private String rowStt;

    @SerializedName("area_id")
    @Expose
    private String areaId;

    @SerializedName("province_id")
    @Expose
    private String provinceId;

    @SerializedName("address_id")
    @Expose
    private String addressId;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("district_id")
    @Expose
    private String districtId;

    @SerializedName("org_code")
    @Expose
    private String orgCode;

    @SerializedName("seller")
    @Expose
    private String seller;

    @SerializedName("branch_id")
    @Expose
    private String branchId;

    public String[] getArrayThu() {
        return arrayThu;
    }

    public void setArrayThu(String[] arrayThu) {
        this.arrayThu = arrayThu;
    }

    private String[] arrayThu;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRowStt() {
        return rowStt;
    }

    public void setRowStt(String rowStt) {
        this.rowStt = rowStt;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
