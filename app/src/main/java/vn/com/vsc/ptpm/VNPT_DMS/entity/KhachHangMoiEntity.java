package vn.com.vsc.ptpm.VNPT_DMS.entity;

public class KhachHangMoiEntity {
	
	public static final String TABLE_NAME = "khachhangmoi";
	
	public static final String ID = "id";
	public static final String NEW_CODE = "new_code";
	public static final String NAME = "name";
	public static final String MANAGER = "mgr";
	public static final String MOBILE = "mobile";
	public static final String FAX = "fax";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "addr";
	public static final String TAX = "tax";
	public static final String ENT_ID = "ent_id";
	public static final String NOTE = "note";
	public static final String ORDER_NOW = "order_now";
	public static final String LATTITUDE = "lattitude";
	public static final String LONGTITUDE = "longtitude";

	private int id;
	private String new_code;
	private String name;
	private String mgr;
	private String mobile;
	private String fax;
	private String email;
	private String addr;
	private String tax;
	private String ent_id;
	private String note;
	private int order_now;
	private double lattitude;
	private double longtitude;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNew_code() {
		return new_code;
	}
	public void setNew_code(String new_code) {
		this.new_code = new_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMgr() {
		return mgr;
	}
	public void setMgr(String mgr) {
		this.mgr = mgr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getEnt_id() {
		return ent_id;
	}
	public void setEnt_id(String ent_id) {
		this.ent_id = ent_id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getOrder_now() {
		return order_now;
	}
	public void setOrder_now(int order_now) {
		this.order_now = order_now;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	
	public KhachHangMoiEntity() {
	}
	
	public KhachHangMoiEntity(String new_code, String name, String mgr, String mobile, String fax, String email,
			String addr, String tax, String ent_id, String note, int order_now, double lattitude, double longtitude) {
		
		this.new_code = new_code;
		this.name = name;
		this.mgr = mgr;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.addr = addr;
		this.tax = tax;
		this.ent_id = ent_id;
		this.note = note;
		this.order_now = order_now;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
	}
	
	public KhachHangMoiEntity(int id, String new_code, String name, String mgr, String mobile, String fax, String email,
			String addr, String tax, String ent_id, String note, int order_now, double lattitude, double longtitude) {
		
		this.id = id;
		this.new_code = new_code;
		this.name = name;
		this.mgr = mgr;
		this.mobile = mobile;
		this.fax = fax;
		this.email = email;
		this.addr = addr;
		this.tax = tax;
		this.ent_id = ent_id;
		this.note = note;
		this.order_now = order_now;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
	}
}
