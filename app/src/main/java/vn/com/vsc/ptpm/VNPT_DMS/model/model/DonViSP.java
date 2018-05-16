package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import java.io.Serializable;

public class DonViSP implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1409191536536726956L;
	private String id;
	private String sluong;
	private String ten_dvi;
	private String gia1;
	private String gia2;

	public DonViSP(String id, String ten_dvi, String gia1) {
		super();
		this.id = id;
		this.ten_dvi = ten_dvi;
		this.gia1 = gia1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSluong() {
		return sluong;
	}

	public void setSluong(String sluong) {
		this.sluong = sluong;
	}

	public String getTen_dvi() {
		return ten_dvi;
	}

	public void setTen_dvi(String ten_dvi) {
		this.ten_dvi = ten_dvi;
	}

	public String getGia1() {
		return gia1;
	}

	public void setGia1(String gia1) {
		this.gia1 = gia1;
	}

	public String getGia2() {
		return gia2;
	}

	public void setGia2(String gia2) {
		this.gia2 = gia2;
	}

	public DonViSP(String id, String sluong, String ten_dvi, String gia1,
			String gia2) {
		super();
		this.id = id;
		this.sluong = sluong;
		this.ten_dvi = ten_dvi;
		this.gia1 = gia1;
		this.gia2 = gia2;
	}

	public DonViSP() {
		super();
		this.gia1 = "0";
	}

	@Override
	public String toString() {
		return "DonViSP [id=" + id + ", sluong=" + sluong + ", ten_dvi="
				+ ten_dvi + ", gia1=" + gia1 + ", gia2=" + gia2 + "]";
	}

}
