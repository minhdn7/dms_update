package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ThemKH {
	
	public static final String TABLE_NAME = "thongtinkhachhang";
	
	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String NEW_CODE = "new_code";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String MANAGER = "mgr";
	public static final String MOBILE = "mobile";
	public static final String TEL = "tel";
	public static final String FAX = "fax";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "addr";
	public static final String TAX = "tax";
	public static final String ENT_ID = "ent_id";
	public static final String NOTE = "note";
	public static final String ASSIGN = "assign";
	public static final String TYPE = "type";
	public static final String LATTITUDE = "lattitude";
	public static final String LONGTITUDE = "longtitude";
	
	private int _id;
	private String id;
	private String new_code;
	private String code;
	private String name;
	private String mgr;
	private String mobile;
	private String tel;
	private String fax;
	private String email;
	private String addr;
	private String tax;
	private String ent_id;
	private String note;
	private String assign;
	private int type; // 0: Khach moi - 1: Cap nhat KH
	private double lattitude;
	private double longtitude;

	
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

	public String getNew_code() {
		return new_code;
	}

	public void setNew_code(String new_code) {
		this.new_code = new_code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public ThemKH() {
	}
	
	// Them moi KH
	public ThemKH(String maKH, String tenKH, String tenNgQL, String soDT,
			String fax, String email, String diaChi, String maSoThue,
			String typeCompany, String note, double lattitude, double longtitude, int type) {
		this.new_code = maKH;
		this.name = tenKH;
		this.mgr = tenNgQL;
		this.mobile = soDT;
		this.fax = fax;
		this.email = email;
		this.addr = diaChi;
		this.tax = maSoThue;
		this.ent_id = typeCompany;
		this.note = note;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
		this.type = type;
	}

	public ThemKH(String id, String maKH, String tenKH, String tenNgQL, String soDT, String tel,
			String fax, String email, String diaChi, String maSoThue,
			String assign, String note, double lattitude, double longtitude) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.code = maKH;
		this.name = tenKH;
		this.mgr = tenNgQL;
		this.mobile = soDT;
		this.tel = tel;
		this.fax = fax;
		this.email = email;
		this.addr = diaChi;
		this.tax = maSoThue;
		this.assign = assign;
		this.note = note;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
	}

	public ThemKH(String id, String code, String name, String mgr, String fax,
			String mobile, String tel, String email, String addr, String tax,
			String assign, String note, double lattitude, double longtitude, int type) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.mgr = mgr;
		this.fax = fax;
		this.mobile = mobile;
		this.tel = tel;
		this.email = email;
		this.addr = addr;
		this.tax = tax;
		this.assign = assign;
		this.note = note;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
		this.type = type;
	}
	
	public ThemKH(int _id, String id, String code, String name, String mgr, String fax,
			String mobile, String tel, String email, String addr, String tax,
			String assign, String note, double lattitude, double longtitude, int type) {
		this._id = _id;
		this.id = id;
		this.code = code;
		this.name = name;
		this.mgr = mgr;
		this.fax = fax;
		this.mobile = mobile;
		this.tel = tel;
		this.email = email;
		this.addr = addr;
		this.tax = tax;
		this.assign = assign;
		this.note = note;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
		this.type = type;
	}

	public static String toJSON(ThemKH data){
		JSONObject jsonObject= new JSONObject();
		try {
			jsonObject.put(ThemKH.ID, data.getId());
			jsonObject.put(ThemKH.CODE, data.getCode());
			jsonObject.put(ThemKH.NAME, data.getName());
			jsonObject.put(ThemKH.MANAGER, data.getMgr());
			jsonObject.put(ThemKH.MOBILE, data.getMobile());
			jsonObject.put(ThemKH.TEL, data.getTel());
			jsonObject.put(ThemKH.FAX, data.getFax());
			jsonObject.put(ThemKH.EMAIL, data.getEmail());
			jsonObject.put(ThemKH.ADDRESS, data.getAddr());
			jsonObject.put(ThemKH.TAX, data.getTax());
			jsonObject.put(ThemKH.ENT_ID, data.getEnt_id());
			jsonObject.put(ThemKH.NOTE, data.getNote());
			jsonObject.put(ThemKH.ASSIGN, data.getAssign());
			jsonObject.put(ThemKH.TYPE, data.getType());
			jsonObject.put(ThemKH.LATTITUDE, data.getLattitude());
			jsonObject.put(ThemKH.LONGTITUDE, data.getLongtitude());

			return jsonObject.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}
}
