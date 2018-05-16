package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.widget.CheckBox;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 19/12/2017.
 */

public class DanhSachKhachHangDangKyModel {
    private String orgId;
    private String orgName;
    private String address;
    private CheckBox ckThư2;
    private CheckBox ckThư3;
    private CheckBox ckThư4;
    private CheckBox ckThư5;
    private CheckBox ckThư6;
    private CheckBox ckThư7;
    private CheckBox ckChuNhat;
    private CheckBox ckTatCa;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CheckBox getCkThư2() {
        return ckThư2;
    }

    public void setCkThư2(CheckBox ckThư2) {
        this.ckThư2 = ckThư2;
    }

    public CheckBox getCkThư3() {
        return ckThư3;
    }

    public void setCkThư3(CheckBox ckThư3) {
        this.ckThư3 = ckThư3;
    }

    public CheckBox getCkThư4() {
        return ckThư4;
    }

    public void setCkThư4(CheckBox ckThư4) {
        this.ckThư4 = ckThư4;
    }

    public CheckBox getCkThư5() {
        return ckThư5;
    }

    public void setCkThư5(CheckBox ckThư5) {
        this.ckThư5 = ckThư5;
    }

    public CheckBox getCkThư6() {
        return ckThư6;
    }

    public void setCkThư6(CheckBox ckThư6) {
        this.ckThư6 = ckThư6;
    }

    public CheckBox getCkThư7() {
        return ckThư7;
    }

    public void setCkThư7(CheckBox ckThư7) {
        this.ckThư7 = ckThư7;
    }

    public CheckBox getCkChuNhat() {
        return ckChuNhat;
    }

    public void setCkChuNhat(CheckBox ckChuNhat) {
        this.ckChuNhat = ckChuNhat;
    }

    public CheckBox getCkTatCa() {
        return ckTatCa;
    }

    public void setCkTatCa(CheckBox ckTatCa) {
        this.ckTatCa = ckTatCa;
    }
}
