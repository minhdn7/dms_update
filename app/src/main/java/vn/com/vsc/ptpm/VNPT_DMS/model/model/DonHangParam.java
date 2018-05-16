package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;


public class DonHangParam {
	// Đơn hàng ID:
	private String id;
	private String rd;
	private String sd1;
	private String sd2;
	private String ndh;
	private String tensp;
	private String ncc;
	private String dvdh;
	private String tt;
	@SerializedName("kh")
	private String khachHang;

	@SerializedName("st")
	private String giaTri;

	// luon gan cung gia tri la 1
	private String cust_po;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getSd1() {
		return sd1;
	}

	public void setSd1(String sd1) {
		this.sd1 = sd1;
	}

	public String getSd2() {
		return sd2;
	}

	public void setSd2(String sd2) {
		this.sd2 = sd2;
	}

	public String getNdh() {
		return ndh;
	}

	public void setNdh(String ndh) {
		this.ndh = ndh;
	}

	public String getTensp() {
		return tensp;
	}

	public void setTensp(String tensp) {
		this.tensp = tensp;
	}

	public String getNcc() {
		return ncc;
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public String getDvdh() {
		return dvdh;
	}

	public void setDvdh(String dvdh) {
		this.dvdh = dvdh;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getCust_po() {
		return cust_po;
	}

	public void setCust_po(String cust_po) {
		this.cust_po = cust_po;
	}

	public String getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(String khachHang) {
		this.khachHang = khachHang;
	}

	public String getGiaTri() {
		return giaTri;
	}

	public void setGiaTri(String giaTri) {
		this.giaTri = giaTri;
	}

	public DonHangParam(String id, String rd, String sd1, String sd2,
						String ndh, String tensp, String ncc, String dvdh, String tt,
						String cust_po) {
		super();
		this.id = id;
		this.rd = rd;
		this.sd1 = sd1;
		this.sd2 = sd2;
		this.ndh = ndh;
		this.tensp = tensp;
		this.ncc = ncc;
		this.dvdh = dvdh;
		this.tt = tt;
		this.cust_po = cust_po;
	}

	public DonHangParam() {
		super();
	}

	@Override
	public String toString() {
		return "DonHangParam [id=" + id + ", rd=" + rd + ", sd1=" + sd1
				+ ", sd2=" + sd2 + ", ndh=" + ndh + ", tensp=" + tensp
				+ ", ncc=" + ncc + ", dvdh=" + dvdh + ", tt=" + tt
				+ ", cust_po=" + cust_po + "]";
	}

}
