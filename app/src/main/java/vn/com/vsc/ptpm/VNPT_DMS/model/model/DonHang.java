package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class DonHang {

	
	public static final String TABLE_NAME = "donhang";
	
	public static final String ORDERER_NAME = "orderer_name";
	public static final String TRANGTHAI = "trangthai";
	public static final String TRANGTHAI_ID = "trangthai_id";
	public static final String SUPPLIER_NAME = "supplier_name";
	public static final String NGAY_LAP = "ngay_lap";
	public static final String NGUOI_LAP = "nguoi_lap";
	public static final String ORDERER_CODE = "orderer_code";
	public static final String TONG_KHUYENMAI = "tong_khuyenmai";
	public static final String DONNHAP_ID = "donnhap_id";
	public static final String TONGTIEN = "tongtien";
	public static final String NOTE = "note";
	
	private String orderer_name;
	private String trangthai;
	private String trangthai_id;
	private String supplier_name;
	private String ngay_lap;
	private String nguoi_lap;
	private String orderer_code;
	private String tong_khuyenmai;
	private String donnhap_id;
	private String tongtien;
	private String note;

	public String getNguoi_lap() {
		return nguoi_lap;
	}

	public void setNguoi_lap(String nguoi_lap) {
		this.nguoi_lap = nguoi_lap;
	}
	public String getTrangthai_id() {
		return trangthai_id;
	}

	public void setTrangthai_id(String trangthai_id) {
		this.trangthai_id = trangthai_id;
	}

	public String getOrderer_name() {
		return orderer_name;
	}

	public void setOrderer_name(String orderer_name) {
		this.orderer_name = orderer_name;
	}
	
	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public String getNgay_lap() {
		return ngay_lap;
	}

	public void setNgay_lap(String ngay_lap) {
		this.ngay_lap = ngay_lap;
	}

	public String getOrderer_code() {
		return orderer_code;
	}
	public void setOrderer_code(String orderer_code) {
		this.orderer_code = orderer_code;
	}
	
	public String getTong_khuyenmai() {
		return tong_khuyenmai;
	}

	public void setTong_khuyenmai(String tong_khuyenmai) {
		this.tong_khuyenmai = tong_khuyenmai;
	}

	public String getDonnhap_id() {
		return donnhap_id;
	}

	public void setDonnhap_id(String donnhap_id) {
		this.donnhap_id = donnhap_id;
	}

	
	public String getTongtien() {
		return tongtien;
	}

	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	// public String tong_phuphi_vnd;
	// public String supplier_code;
	// public String code;
	// public String promotion_note;
	// public String ngay_gui;
	// public String trangthai_id;

	// public String nguoi_gui;
	// public String nguoi_lap;
	// public String nguoi_duyet;
	// public String getTong_phuphi_vnd() {
	// return tong_phuphi_vnd;
	// }
	// public void setTong_phuphi_vnd(String tong_phuphi_vnd) {
	// this.tong_phuphi_vnd = tong_phuphi_vnd;
	// }
	// public String getSupplier_code() {
	// return supplier_code;
	// }
	// public void setSupplier_code(String supplier_code) {
	// this.supplier_code = supplier_code;
	// }
	// public String getNguoi_gui() {
	// return nguoi_gui;
	// }
	// public void setNguoi_gui(String nguoi_gui) {
	// this.nguoi_gui = nguoi_gui;
	// }
	// public String getNguoi_lap() {
	// return nguoi_lap;
	// }
	// public void setNguoi_lap(String nguoi_lap) {
	// this.nguoi_lap = nguoi_lap;
	// }
	// public String getNguoi_duyet() {
	// return nguoi_duyet;
	// }
	// public void setNguoi_duyet(String nguoi_duyet) {
	// this.nguoi_duyet = nguoi_duyet;
	// }
	// public String getCode() {
	// return code;
	// }
	// public void setCode(String code) {
	// this.code = code;
	// }

	// public String getPromotion_note() {
	// return promotion_note;
	// }
	// public void setPromotion_note(String promotion_note) {
	// this.promotion_note = promotion_note;
	// }
	// public String getNgay_gui() {
	// return ngay_gui;
	// }
	// public void setNgay_gui(String ngay_gui) {
	// this.ngay_gui = ngay_gui;
	// }
	// public String getTrangthai_id() {
	// return trangthai_id;
	// }
	// public void setTrangthai_id(String trangthai_id) {
	// this.trangthai_id = trangthai_id;
	// }
}
