package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import com.google.gson.annotations.SerializedName;

public class SPham {
	String phuphi_gianhap_vnd;
	String loaisp_id;
	String loaisp_ten;
	String quycach_donggoi;
	String ma_sp;
	String donvi_tinh;
	String hangton;
	String loaitien_id;
	String row_stt;
	String gianhap;
	String tygia;
	String quydoi_vnd;
	String image_url;
	String is_ordered;
	String stt;
	String selling_price_vnd;
	String gianhap_vnd;
	String gianhap_vnd_cu;
	String thanhtien;
	String soluong;

	@SerializedName("tax_rate")
	String taxRate;

	public SPham() {
		super();
	}

	public String getPhuphi_gianhap_vnd() {
		return phuphi_gianhap_vnd;
	}

	public void setPhuphi_gianhap_vnd(String phuphi_gianhap_vnd) {
		this.phuphi_gianhap_vnd = phuphi_gianhap_vnd;
	}

	public String getLoaisp_id() {
		return loaisp_id;
	}

	public void setLoaisp_id(String loaisp_id) {
		this.loaisp_id = loaisp_id;
	}

	public String getLoaisp_ten() {
		return loaisp_ten;
	}

	public void setLoaisp_ten(String loaisp_ten) {
		this.loaisp_ten = loaisp_ten;
	}

	public String getQuycach_donggoi() {
		return quycach_donggoi;
	}

	public void setQuycach_donggoi(String quycach_donggoi) {
		this.quycach_donggoi = quycach_donggoi;
	}

	public String getMa_sp() {
		return ma_sp;
	}

	public void setMa_sp(String ma_sp) {
		this.ma_sp = ma_sp;
	}

	public String getDonvi_tinh() {
		return donvi_tinh;
	}

	public void setDonvi_tinh(String donvi_tinh) {
		this.donvi_tinh = donvi_tinh;
	}

	public String getHangton() {
		return hangton;
	}

	public void setHangton(String hangton) {
		this.hangton = hangton;
	}

	public String getLoaitien_id() {
		return loaitien_id;
	}

	public void setLoaitien_id(String loaitien_id) {
		this.loaitien_id = loaitien_id;
	}

	public String getRow_stt() {
		return row_stt;
	}

	public void setRow_stt(String row_stt) {
		this.row_stt = row_stt;
	}

	public String getGianhap() {
		return gianhap;
	}

	public void setGianhap(String gianhap) {
		this.gianhap = gianhap;
	}

	public String getTygia() {
		return tygia;
	}

	public void setTygia(String tygia) {
		this.tygia = tygia;
	}

	public String getQuydoi_vnd() {
		return quydoi_vnd;
	}

	public void setQuydoi_vnd(String quydoi_vnd) {
		this.quydoi_vnd = quydoi_vnd;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getIs_ordered() {
		return is_ordered;
	}

	public void setIs_ordered(String is_ordered) {
		this.is_ordered = is_ordered;
	}

	public String getStt() {
		return stt;
	}

	public void setStt(String stt) {
		this.stt = stt;
	}

	public String getSelling_price_vnd() {
		return selling_price_vnd;
	}

	public void setSelling_price_vnd(String selling_price_vnd) {
		this.selling_price_vnd = selling_price_vnd;
	}

	public String getGianhap_vnd() {
		return gianhap_vnd;
	}

	public void setGianhap_vnd(String gianhap_vnd) {
		this.gianhap_vnd = gianhap_vnd;
	}

	public String getGianhap_vnd_cu() {
		return gianhap_vnd_cu;
	}

	public void setGianhap_vnd_cu(String gianhap_vnd_cu) {
		this.gianhap_vnd_cu = gianhap_vnd_cu;
	}

	public String getThanhtien() {
		return thanhtien;
	}

	public void setThanhtien(String thanhtien) {
		this.thanhtien = thanhtien;
	}

	public String getSoluong() {
		return soluong;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}
}
