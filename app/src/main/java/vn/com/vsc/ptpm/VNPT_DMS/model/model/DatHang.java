package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class DatHang {
	
	public static final String TABLE_NAME = "dathang";
	
	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String DVDH = "dvdh";
	public static final String NCC = "ncc";
	public static final String NGAYDH = "ngaydh";
	public static final String DIENGIAI = "diengiai";
	public static final String DIACHI = "diachi";
	public static final String GHICHU = "ghichu";
	public static final String NGAYYC = "ngayyc";
	public static final String PHIEUGIAO = "phieugiao";
	public static final String HTTT = "httt";
	
	private int _id;
	private String id;
	private String dvdh;
	private String ncc;
	private String ngaydh;
	private String diengiai;
	private String diachi;
	private String ghichu;
	private String ngayyc;
	private String phieugiao;
	private String httt;

	public DatHang(String id, String dvdh, String ncc, String ngaydh,
			String diachi, String ghichu, String ngayyc, String phieugiao,
			String httt) {
		super();
		this.id = id;
		this.dvdh = dvdh;
		this.ncc = ncc;
		this.ngaydh = ngaydh;
		this.diengiai = "";
		this.diachi = diachi;
		this.ghichu = ghichu;
		this.ngayyc = ngayyc;
		this.phieugiao = phieugiao;
		this.httt = httt;

	}

	public DatHang() {
		super();
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDvdh() {
		return dvdh;
	}

	public void setDvdh(String dvdh) {
		this.dvdh = dvdh;
	}

	public String getNcc() {
		return ncc;
	}

	public void setNcc(String ncc) {
		this.ncc = ncc;
	}

	public String getNgaydh() {
		return ngaydh;
	}

	public void setNgaydh(String ngaydh) {
		this.ngaydh = ngaydh;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getNgayyc() {
		return ngayyc;
	}

	public void setNgayyc(String ngayyc) {
		this.ngayyc = ngayyc;
	}

	public String getPhieugiao() {
		return phieugiao;
	}

	public void setPhieugiao(String phieugiao) {
		this.phieugiao = phieugiao;
	}

	public String getHttt() {
		return httt;
	}

	public void setHttt(String httt) {
		this.httt = httt;
	}

	@Override
	public String toString() {
		return "DatHang [id=" + id + ", dvdh=" + dvdh + ", ncc=" + ncc
				+ ", ngaydh=" + ngaydh + ", diengiai=" + diengiai + ", diachi="
				+ diachi + ", ghichu=" + ghichu + ", ngayyc=" + ngayyc
				+ ", phieugiao=" + phieugiao + ", httt=" + httt + "]";
	}

}
