package vn.com.vsc.ptpm.VNPT_DMS.entity;

public class SanPhamDonHangEntity {
	
	public static final String TABLE_NAME = "sanphamdonhang";
	
	public static final String _ID = "_id";
	public static final String PO_ID = "po_id";
	public static final String LOAISP_ID = "loaisp_id";
	public static final String SOLUONG = "soluong";
	public static final String GIA = "gia";

	private int _id;
	private String po_id;
	private String loaisp_id;
	private String soluong;
	private String gia;
	
	public SanPhamDonHangEntity() {
	}
	
	public SanPhamDonHangEntity(String poId, String loaiSPId, String soLuong, String gia) {
		this.po_id = poId;
		this.loaisp_id = loaiSPId;
		this.soluong = soLuong;
		this.gia = gia;
	}

	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getPo_id() {
		return po_id;
	}
	public void setPo_id(String po_id) {
		this.po_id = po_id;
	}
	public String getLoaisp_id() {
		return loaisp_id;
	}
	public void setLoaisp_id(String loaisp_id) {
		this.loaisp_id = loaisp_id;
	}
	public String getSoluong() {
		return soluong;
	}
	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}
	public String getGia() {
		return gia;
	}
	public void setGia(String gia) {
		this.gia = gia;
	}

}
