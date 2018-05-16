package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class DonHangTKC {
	public String id;
	public String rd;
	public String sd1;
	public String sd2;
	public String ndh;
	public String tensp;
	public String ncc;
	public String dvdh;
	public String tt;
	public String cust_po = "1";

	public DonHangTKC(String beginDate, String endDate, String idNVKD) {
		// TODO Auto-generated constructor stub
		this.sd1 = beginDate;
		this.sd2 = endDate;
		this.ndh = idNVKD;
	}

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

}
