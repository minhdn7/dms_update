package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class UpdateGPSKH {

	public static final String TABLE_NAME = "updatelocation";

	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String LNG = "lng";
	public static final String LAT = "lat";
	public static final String UPD = "upd"; // 1: cap nhat dia chi theo google map
	public static final String ASSIGN = "assign";
	public static final String TIME = "time";

	private String id;
	private String code;
	private double lng;
	private double lat;
	private int upd;
	private String assign;
	private int time;

	public UpdateGPSKH() {
	}

	public UpdateGPSKH(String Id, String Code, Double Lng, Double Lat, int Udp, String Assign, int time) {
		this.id = Id;
		this.code = Code;
		this.lng = Lng;
		this.lat = Lat;
		this.upd = Udp;
		this.assign = Assign;
		this.time = time;
	}

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

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public int getUpd() {
		return upd;
	}

	public void setUpd(int upd) {
		this.upd = upd;
	}

	public String getAssign() {
		return assign;
	}

	public void setAssign(String assign) {
		this.assign = assign;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
