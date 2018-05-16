package vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class DanhSachKhachHangDeDangKyRequest {
    @SerializedName("pageno")
    @Expose
    private int pageno;

    @SerializedName("pagerec")
    @Expose
    private int pagerec;

    @SerializedName("tuKhoa")
    @Expose
    private String tuKhoa;

    @SerializedName("branch")
    @Expose
    private String branch;

    @SerializedName("area")
    @Expose
    private String area;

    @SerializedName("province")
    @Expose
    private String province;

    @SerializedName("district")
    @Expose
    private String district;

    @SerializedName("list_org_id")
    @Expose
    private String listOrgId;

    public String getTuKhoa() {
        return tuKhoa;
    }

    public void setTuKhoa(String tuKhoa) {
        this.tuKhoa = tuKhoa;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getListOrgId() {
        return listOrgId;
    }

    public void setListOrgId(String listOrgId) {
        this.listOrgId = listOrgId;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagerec() {
        return pagerec;
    }

    public void setPagerec(int pagerec) {
        this.pagerec = pagerec;
    }

    public DanhSachKhachHangDeDangKyRequest(int pageno, int pagerec, String tuKhoa, String branch, String area, String province, String district, String listOrgId) {
        this.pageno = pageno;
        this.pagerec = pagerec;
        this.tuKhoa = tuKhoa;
        this.branch = branch;
        this.area = area;
        this.province = province;
        this.district = district;
        this.listOrgId = listOrgId;
    }
}
