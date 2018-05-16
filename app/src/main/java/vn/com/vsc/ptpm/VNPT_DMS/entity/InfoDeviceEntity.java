package vn.com.vsc.ptpm.VNPT_DMS.entity;

public class InfoDeviceEntity {

	public static final String TABLE_NAME = "infodevice";

	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String ASSIGN = "assign";// ma NVKD
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "lng";
	public static final String TIME = "time";
	public static final String LEVEL_BATTERY = "pin";
	public static final String NETWORK_TYPE = "type_network";
	public static final String SIGNAL_STRENGTH = "value";
	public static final String SEND_STATUS = "send_status";
	public static final String DISTANCE = "distance";
	public static final String HAS_ACCURACY = "has_accuracy";
	public static final String ACCURACY = "accuracy";

	private int id;
	private String code;
	private String assign;
	private double lat;
	private double lng;
	private String time;
	private int pin;
	private String type_network;
	private int value;
	private int send_status;
	private double distance;
	private double accuracy;
	private int hasAccuracy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType_network() {
		return type_network;
	}

	public void setType_network(String type_network) {
		this.type_network = type_network;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSend_status() {
		return send_status;
	}

	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}

	public InfoDeviceEntity() {
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
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

	public InfoDeviceEntity(int id, String code, String assign, double lat, double lng, String time, int pin, String type_network, int value,
			int send_status, double distance, double accuracy, int hasAccuracy) {
		super();
		this.id = id;
		this.code = code;
		this.assign = assign;
		this.lat = lat;
		this.lng = lng;
		this.time = time;
		this.pin = pin;
		this.type_network = type_network;
		this.value = value;
		this.send_status = send_status;
		this.distance = distance;
		this.accuracy = accuracy;
		this.hasAccuracy = hasAccuracy;
	}

	@Override
	public String toString() {
		return "InfoDeviceEntity [id=" + id + ", code=" + code + ", assign=" + assign + ", lat=" + lat + ", lng=" + lng + ", time=" + time + ", pin="
				+ pin + ", type_network=" + type_network + ", value=" + value + ", send_status=" + send_status + ", distance=" + distance
				+ ", accuracy=" + accuracy
				+ ", has_accuracy =" + hasAccuracy
				+ "]";
	}

	public String toStringData() {
		return id + "," + code + "," + assign + "," + lat + "," + lng + "," + time + "," + pin + "," + type_network + "," + value + "," + send_status
				+ "," + distance + "|";
	}
}
