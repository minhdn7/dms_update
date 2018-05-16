package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class Checkin {
	
	public static final String TABLE_NAME = "checkin";
	
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String ASSIGN = "assign";
	public static final String LNG = "lng";
	public static final String LAT = "lat";
	public static final String CHECKTYPE = "checktype";
	public static final String TIME = "time";
	public static final String PIN = "pin";
	public static final String NETWORK_TYPE = "type_network";
	public static final String SIGNAL_STRENGTH = "value";
	public static final String HAS_ACCURACY = "has_accuracy";
	public static final String ACCURACY = "accuracy";

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
	private double accuracy;
	private int hasAccuracy;

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
	
	public Checkin() {
	}

	public Checkin(String id, String code, String assign, String lng, String lat, String checkType, String time) {
		super();
		this.id = id;
		this.code = code;
		this.assign = assign;
		this.lng = lng;
		this.lat = lat;
		this.checktype = checkType;
		this.time = time;
	}

	public Checkin(String id, String code, String assign, String lng,
			String lat, String checktype, String time, String pin,
			String type_network, String value, double accuracy, int hasAccuracy) {
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
		this.accuracy = accuracy;
		this.hasAccuracy = hasAccuracy;
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

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public int getHasAccuracy() {
		return hasAccuracy;
	}

	public void setHasAccuracy(int hasAccuracy) {
		this.hasAccuracy = hasAccuracy;
	}
}
