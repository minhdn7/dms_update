package vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 13/10/2017.
 */

public class SoLieuKhaoSatRequest {
    @SerializedName("brand")
    private String brand;

    @SerializedName("product_cat")
    private String product_cat;

    @SerializedName("supplier")
    private String supplier;

    @SerializedName("tran_cost")
    private String tran_cost;

    @SerializedName("load_cost")
    private String load_cost;

    @SerializedName("buy_price")
    String buy_price;

    @SerializedName("sell_price")
    String sell_price;

    @SerializedName("quan_expected")
    String quan_expected;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTran_cost() {
        return tran_cost;
    }

    public void setTran_cost(String tran_cost) {
        this.tran_cost = tran_cost;
    }

    public String getLoad_cost() {
        return load_cost;
    }

    public void setLoad_cost(String load_cost) {
        this.load_cost = load_cost;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getQuan_expected() {
        return quan_expected;
    }

    public void setQuan_expected(String quan_expected) {
        this.quan_expected = quan_expected;
    }
}
