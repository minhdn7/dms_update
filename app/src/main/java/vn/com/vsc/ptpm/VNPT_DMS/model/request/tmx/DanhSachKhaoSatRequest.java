package vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 4/10/2017.
 */

public class DanhSachKhaoSatRequest {
    @SerializedName("pageno")
    private int pageno;

    @SerializedName("pagerec")
    private int pageRec;

    @SerializedName("customer")
    private String customer;

    @SerializedName("month")
    private String month;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPageRec() {
        return pageRec;
    }

    public void setPageRec(int pageRec) {
        this.pageRec = pageRec;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public DanhSachKhaoSatRequest(int pageno, int pageRec, String customer, String month) {
        this.pageno = pageno;
        this.pageRec = pageRec;
        this.customer = customer;
        this.month = month;
    }
}
