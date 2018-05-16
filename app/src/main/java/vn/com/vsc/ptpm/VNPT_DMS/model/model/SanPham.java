package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SanPham implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6809235426460163785L;
	// private String ngay_hh;
	// private String total_vote;
	// private String giaban;
	// private String product_id;
	private String product_cat_id;
	// private String promotion_amount;
	private String ds_donvi;
	// private String code;
	// private String url;
	// private String avg_point;
	// private String row_stt;
	// private String unit;
	// private String thue;
	// private String total_comment;
	// private String gia_truocthue;
	// private String selling_price_vnd;
	private String name;
	// private String gia_ban;
	// private String expired_date;
	private String soluong;

	@SerializedName("unit_display")
	private String unitDisplay;

	@SerializedName("tax_rate")
	String taxRate;

	public SanPham(String product_cat_id, String ds_donvi, String name, String soluong, String unitDisplay) {
		this.product_cat_id = product_cat_id;
		this.ds_donvi = ds_donvi;
		this.name = name;
		this.soluong = soluong;
		this.unitDisplay = unitDisplay;
	}

	public SanPham(String product_cat_id, String ds_donvi, String name,
				   String soluong) {
		super();
		this.product_cat_id = product_cat_id;
		this.ds_donvi = ds_donvi;
		this.name = name;
		this.soluong = soluong;
	}

	// private String tax_rate;

	// public SanPham(String ngay_hh, String total_vote, String giaban,
	// String product_id, String product_cat_id, String promotion_amount,
	// String ds_donvi, String code, String url, String avg_point,
	// String row_stt, String unit, String thue, String total_comment,
	// String gia_truocthue, String selling_price_vnd, String name,
	// String gia_ban, String expired_date, String soluong, String tax_rate) {
	// super();
	// this.ngay_hh = ngay_hh;
	// this.total_vote = total_vote;
	// this.giaban = giaban;
	// this.product_id = product_id;
	// this.product_cat_id = product_cat_id;
	// this.promotion_amount = promotion_amount;
	// this.ds_donvi = ds_donvi;
	// this.code = code;
	// this.url = url;
	// this.avg_point = avg_point;
	// this.row_stt = row_stt;
	// this.unit = unit;
	// this.thue = thue;
	// this.total_comment = total_comment;
	// this.gia_truocthue = gia_truocthue;
	// this.selling_price_vnd = selling_price_vnd;
	// this.name = name;
	// this.gia_ban = gia_ban;
	// this.expired_date = expired_date;
	// this.soluong = soluong;
	// this.tax_rate = tax_rate;
	// }

	public SanPham(String product_cat_id, String name) {
		super();
		this.product_cat_id = product_cat_id;
		this.name = name;
	}

	// public String getNgay_hh() {
	// return ngay_hh;
	// }
	//
	// public void setNgay_hh(String ngay_hh) {
	// this.ngay_hh = ngay_hh;
	// }
	//
	// public String getTotal_vote() {
	// return total_vote;
	// }
	//
	// public void setTotal_vote(String total_vote) {
	// this.total_vote = total_vote;
	// }
	//
	// public String getGiaban() {
	// return giaban;
	// }
	//
	// public void setGiaban(String giaban) {
	// this.giaban = giaban;
	// }
	//
	// public String getProduct_id() {
	// return product_id;
	// }
	//
	// public void setProduct_id(String product_id) {
	// this.product_id = product_id;
	// }



	public String getProduct_cat_id() {
		return product_cat_id;
	}

	public void setProduct_cat_id(String product_cat_id) {
		this.product_cat_id = product_cat_id;
	}

	// public String getPromotion_amount() {
	// return promotion_amount;
	// }
	//
	// public void setPromotion_amount(String promotion_amount) {
	// this.promotion_amount = promotion_amount;
	// }

	public String getDs_donvi() {
		return ds_donvi;
	}

	public void setDs_donvi(String ds_donvi) {
		this.ds_donvi = ds_donvi;
	}

	// public String getCode() {
	// return code;
	// }
	//
	// public void setCode(String code) {
	// this.code = code;
	// }
	//
	// public String getUrl() {
	// return url;
	// }
	//
	// public void setUrl(String url) {
	// this.url = url;
	// }
	//
	// public String getAvg_point() {
	// return avg_point;
	// }
	//
	// public void setAvg_point(String avg_point) {
	// this.avg_point = avg_point;
	// }
	//
	// public String getRow_stt() {
	// return row_stt;
	// }
	//
	// public void setRow_stt(String row_stt) {
	// this.row_stt = row_stt;
	// }
	//
	// public String getUnit() {
	// return unit;
	// }
	//
	// public void setUnit(String unit) {
	// this.unit = unit;
	// }
	//
	// public String getThue() {
	// return thue;
	// }
	//
	// public void setThue(String thue) {
	// this.thue = thue;
	// }
	//
	// public String getTotal_comment() {
	// return total_comment;
	// }
	//
	// public void setTotal_comment(String total_comment) {
	// this.total_comment = total_comment;
	// }
	//
	// public String getGia_truocthue() {
	// return gia_truocthue;
	// }
	//
	// public void setGia_truocthue(String gia_truocthue) {
	// this.gia_truocthue = gia_truocthue;
	// }
	//
	// public String getSelling_price_vnd() {
	// return selling_price_vnd;
	// }
	//
	// public void setSelling_price_vnd(String selling_price_vnd) {
	// this.selling_price_vnd = selling_price_vnd;
	// }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public String getGia_ban() {
	// return gia_ban;
	// }
	//
	// public void setGia_ban(String gia_ban) {
	// this.gia_ban = gia_ban;
	// }
	//
	// public String getExpired_date() {
	// return expired_date;
	// }
	//
	// public void setExpired_date(String expired_date) {
	// this.expired_date = expired_date;
	// }

	public String getSoluong() {
		return soluong;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	@Override
	public String toString() {
		return "SanPham [product_cat_id=" + product_cat_id + ", ds_donvi="
				+ ds_donvi + ", name=" + name + ", soluong=" + soluong + "]";
	}

	public String getUnitDisplay() {
		return unitDisplay;
	}

	public void setUnitDisplay(String unitDisplay) {
		this.unitDisplay = unitDisplay;
	}

	// public String getTax_rate() {
	// return tax_rate;
	// }
	//
	// public void setTax_rate(String tax_rate) {
	// this.tax_rate = tax_rate;
	// }

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
}
