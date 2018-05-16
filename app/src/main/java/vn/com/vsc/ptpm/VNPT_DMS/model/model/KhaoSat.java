package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class KhaoSat {
	
	public static final String TABLE_NAME = "khaosat";
	
	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String UPDATED_DATE = "updated_date";
	public static final String JSON_DEFAULT_VALUE = "json_default_value";
	public static final String COMPONENT_TYPE = "component_type";
	public static final String VALUE = "value";
	public static final String UPDATED_BY = "updated_by";
	public static final String CODE = "code";
	public static final String UPDATED_IP = "updated_ip";
	public static final String STATUS = "status";
	public static final String ORG_ID = "org_id";
	public static final String ORG_CODE = "org_code";
	public static final String ASSIGN_ID = "assign_id";
	public static final String NAME = "name";

	private int _id;
	private String id;
	private String updated_date;
	private String json_default_value;
	private String component_type;
	private String value;
	private String updated_by;
	private String code;
	private String updated_ip;
	private int status; // 0: online; 1: offline
	private String org_id;
	private String org_code;
	private String assign_id;
	private String name;

	public KhaoSat() {
		super();
	}
	
	public KhaoSat(String id, String updated_date, String value, int status, String org_id, String org_code, String assign_id) {
		this.id = id;
		this.updated_date = updated_date;
		this.value = value;
		this.status = status;
		this.org_id = org_id;
		this.org_code = org_code;
		this.assign_id = assign_id;
	}

	public KhaoSat(String id, String updated_date, String json_default_value,
			String component_type, String value, String updated_by,
			String code, String updated_ip) {
		super();
		this.id = id;
		this.updated_date = updated_date;
		this.json_default_value = json_default_value;
		this.component_type = component_type;
		this.value = value;
		this.updated_by = updated_by;
		this.code = code;
		this.updated_ip = updated_ip;
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

	public String getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public String getJson_default_value() {
		return json_default_value;
	}

	public void setJson_default_value(String json_default_value) {
		this.json_default_value = json_default_value;
	}

	public String getComponent_type() {
		return component_type;
	}

	public void setComponent_type(String component_type) {
		this.component_type = component_type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUpdated_ip() {
		return updated_ip;
	}

	public void setUpdated_ip(String updated_ip) {
		this.updated_ip = updated_ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	public String getAssign_id() {
		return assign_id;
	}

	public void setAssign_id(String assign_id) {
		this.assign_id = assign_id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
