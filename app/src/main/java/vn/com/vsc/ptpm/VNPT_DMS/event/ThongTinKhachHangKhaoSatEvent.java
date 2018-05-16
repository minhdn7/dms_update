package vn.com.vsc.ptpm.VNPT_DMS.event;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 9/10/2017.
 */

public class ThongTinKhachHangKhaoSatEvent {
    @SerializedName("id")
    private String id;

    @SerializedName("created_date")
    private String created_date;

    @SerializedName("row_stt")
    private String row_stt;

    @SerializedName("phanhoi")
    private String phanhoi;

    @SerializedName("customer")
    private String customer;

    @SerializedName("smonth_survey")
    private String smonth_survey;

    @SerializedName("org_id")
    private String org_id;

    @SerializedName("is_daily")
    private String is_daily;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getRow_stt() {
        return row_stt;
    }

    public void setRow_stt(String row_stt) {
        this.row_stt = row_stt;
    }

    public String getPhanhoi() {
        return phanhoi;
    }

    public void setPhanhoi(String phanhoi) {
        this.phanhoi = phanhoi;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSmonth_survey() {
        return smonth_survey;
    }

    public void setSmonth_survey(String smonth_survey) {
        this.smonth_survey = smonth_survey;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getIs_daily() {
        return is_daily;
    }

    public void setIs_daily(String is_daily) {
        this.is_daily = is_daily;
    }

    public ThongTinKhachHangKhaoSatEvent(String id, String created_date, String row_stt, String phanhoi, String customer, String smonth_survey, String org_id, String is_daily ) {
        this.id = id;
        this.created_date = created_date;
        this.row_stt = row_stt;
        this.phanhoi = phanhoi;
        this.customer = customer;
        this.smonth_survey = smonth_survey;
        this.org_id = org_id;
        this.is_daily = is_daily;
    }
}
