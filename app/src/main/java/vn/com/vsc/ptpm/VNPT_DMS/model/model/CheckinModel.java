package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class CheckinModel {
	
	public static final String TABLE_NAME = "checkin";
	
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String ASSIGN = "assign";
	public static final String LNG = "lng";
	public static final String LAT = "lat";
	public static final String CHECKTYPE = "checktype";
	public static final String TIME = "time";
	
	private String id; // ID của khách hàng
	private String code;// Mã của khách hàng
	private String assign;// ID của phiếu giao trong thông tin dskh trả lại (nếu có)
	private String lng;
	private String lat;
	private String checktype; // 0: checkin, 2: checkout
	private String time;
	private String pin;
	private String type_network;
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getChecktype() {
		return checktype;
	}

	public void setChecktype(String checktype) {
		this.checktype = checktype;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public CheckinModel() {
	}

	public CheckinModel(String id, String code, String assign, String lng, String lat, String checkType, String time) {
		super();
		this.id = id;
		this.code = code;
		this.assign = assign;
		this.lng = lng;
		this.lat = lat;
		this.checktype = checkType;
		this.time = time;
	}

	public CheckinModel(String id, String code, String assign, String lng,
			String lat, String checktype, String time, String pin,
			String type_network, String value) {
		super();
		this.id = id;
		this.code = code;
		this.assign = assign;
		this.lng = lng;
		this.lat = lat;
		this.checktype = checktype;
		this.time = time;
		this.pin = pin;
		this.type_network = type_network;
		this.value = value;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getType_network() {
		return type_network;
	}

	public void setType_network(String type_network) {
		this.type_network = type_network;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
