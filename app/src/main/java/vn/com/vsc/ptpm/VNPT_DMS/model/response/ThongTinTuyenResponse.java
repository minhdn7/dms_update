package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 13/7/2017.
 */

public class ThongTinTuyenResponse {
    @SerializedName("total_of_order")
    private String totalOfOrder;

    @SerializedName("no_of_none")
    private String noOfNone;

    @SerializedName("no_of_done")
    private String noOfDone;

    @SerializedName("no_of_order")
    private String noOfOrder;

    @SerializedName("no_of_cus")
    private String noOfCus;

    @SerializedName("no_of_ordered_customer")
    private String noOfOrderedCustomer;

    @SerializedName("no_of_done_nt")
    private String noOfDoneNt;

    public String getTotalOfOrder() {
        return totalOfOrder;
    }

    public void setTotalOfOrder(String totalOfOrder) {
        this.totalOfOrder = totalOfOrder;
    }

    public String getNoOfNone() {
        return noOfNone;
    }

    public void setNoOfNone(String noOfNone) {
        this.noOfNone = noOfNone;
    }

    public String getNoOfDone() {
        return noOfDone;
    }

    public void setNoOfDone(String noOfDone) {
        this.noOfDone = noOfDone;
    }

    public String getNoOfCus() {
        return noOfCus;
    }

    public void setNoOfCus(String noOfCus) {
        this.noOfCus = noOfCus;
    }

    public String getNoOfOrderedCustomer() {
        return noOfOrderedCustomer;
    }

    public void setNoOfOrderedCustomer(String noOfOrderedCustomer) {
        this.noOfOrderedCustomer = noOfOrderedCustomer;
    }

    public String getNoOfDoneNt() {
        return noOfDoneNt;
    }

    public void setNoOfDoneNt(String noOfDoneNt) {
        this.noOfDoneNt = noOfDoneNt;
    }

    public String getNoOfOrder() {
        return noOfOrder;
    }

    public void setNoOfOrder(String noOfOrder) {
        this.noOfOrder = noOfOrder;
    }
}
