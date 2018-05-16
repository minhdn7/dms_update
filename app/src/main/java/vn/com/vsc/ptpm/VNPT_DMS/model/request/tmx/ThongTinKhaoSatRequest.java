package vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 13/10/2017.
 */

public class ThongTinKhaoSatRequest {
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
}
