package vn.com.vsc.ptpm.VNPT_DMS.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 10/10/2017.
 */

public class ThongTinKhaoSatChiTietResponse {
    @SerializedName("buying_price_char")
    private String buying_price_char;

    @SerializedName("selling_price_char")
    private String selling_price_char;

    @SerializedName("loading_cost_char")
    private String loading_cost_char;


    @SerializedName("buying_price_")
    private String buying_price;


    @SerializedName("product_cat")
    private String product_cat;


    @SerializedName("row_stt")
    private String row_stt;

    @SerializedName("quantity_expected_char")
    private String quantity_expected_char;

    @SerializedName("transport_cost_char")
    private String transport_cost_char;

    @SerializedName("nhaphanphoi")
    private String nhaphanphoi;

    @SerializedName("quantity_expected")
    private String quantity_expected;

    @SerializedName("brand")
    private String brand;

    @SerializedName("selling_price_")
    private String selling_price;

    @SerializedName("transport_cost")
    private String transport_cost;

    @SerializedName("loading_cost")
    private String loading_cost;

    @SerializedName("product_cat_id")
    private String product_cat_id;

    @SerializedName("brand_id")
    private String brand_id;

    @SerializedName("distributor_id")
    private String distributor_id;

    public String getBuying_price_char() {
        return buying_price_char;
    }

    public void setBuying_price_char(String buying_price_char) {
        this.buying_price_char = buying_price_char;
    }

    public String getSelling_price_char() {
        return selling_price_char;
    }

    public void setSelling_price_char(String selling_price_char) {
        this.selling_price_char = selling_price_char;
    }

    public String getLoading_cost_char() {
        return loading_cost_char;
    }

    public void setLoading_cost_char(String loading_cost_char) {
        this.loading_cost_char = loading_cost_char;
    }

    public String getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(String buying_price) {
        this.buying_price = buying_price;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getRow_stt() {
        return row_stt;
    }

    public void setRow_stt(String row_stt) {
        this.row_stt = row_stt;
    }

    public String getQuantity_expected_char() {
        return quantity_expected_char;
    }

    public void setQuantity_expected_char(String quantity_expected_char) {
        this.quantity_expected_char = quantity_expected_char;
    }

    public String getTransport_cost_char() {
        return transport_cost_char;
    }

    public void setTransport_cost_char(String transport_cost_char) {
        this.transport_cost_char = transport_cost_char;
    }

    public String getNhaphanphoi() {
        return nhaphanphoi;
    }

    public void setNhaphanphoi(String nhaphanphoi) {
        this.nhaphanphoi = nhaphanphoi;
    }

    public String getQuantity_expected() {
        return quantity_expected;
    }

    public void setQuantity_expected(String quantity_expected) {
        this.quantity_expected = quantity_expected;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public String getTransport_cost() {
        return transport_cost;
    }

    public void setTransport_cost(String transport_cost) {
        this.transport_cost = transport_cost;
    }

    public String getLoading_cost() {
        return loading_cost;
    }

    public void setLoading_cost(String loading_cost) {
        this.loading_cost = loading_cost;
    }

    public String getProduct_cat_id() {
        return product_cat_id;
    }

    public void setProduct_cat_id(String product_cat_id) {
        this.product_cat_id = product_cat_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getDistributor_id() {
        return distributor_id;
    }

    public void setDistributor_id(String distributor_id) {
        this.distributor_id = distributor_id;
    }

    public ThongTinKhaoSatChiTietResponse() {
    }

    public ThongTinKhaoSatChiTietResponse(String buying_price, String product_cat, String row_stt, String nhaphanphoi, String brand, String selling_price, String transport_cost, String loading_cost, String product_cat_id, String brand_id, String distributor_id) {
        this.buying_price = buying_price;
        this.product_cat = product_cat;
        this.row_stt = row_stt;
        this.nhaphanphoi = nhaphanphoi;
        this.brand = brand;
        this.selling_price = selling_price;
        this.transport_cost = transport_cost;
        this.loading_cost = loading_cost;
        this.product_cat_id = product_cat_id;
        this.brand_id = brand_id;
        this.distributor_id = distributor_id;
    }
}
