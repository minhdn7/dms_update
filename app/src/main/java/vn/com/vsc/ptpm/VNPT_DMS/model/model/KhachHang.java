package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KhachHang implements Parcelable {
	
	public static final String TABLE_NAME = "tuyenkhachhang";
	
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String ASSIGN_ID = "assign_id";
	public static final String ADDRESS = "address";
	public static final String ROW_STT = "row_stt";
	public static final String IMAGE_URL = "image_url";
	public static final String LATTITUDE = "lattitude";
	public static final String LONGTITUDE = "longtitude";
	public static final String STATUS = "status";
	public static final String ROAD_ID = "road_id";
	public static final String ROAD_NAME = "road_name";
	public static final String CHECKIN_TIME = "checkin_time";
	public static final String CHECKOUT_TIME = "checkout_time";
	
	
	private String assign_name;
	private String distanceinmeter;
	private String code;
	private String crm_org_role_type;
	private String id;
	private String row_stt;
	private String distance;
	private String assign_id;
	private String address;
	private String image_url;
	private String tax_code;
	private String name;
	private String lattitude;
	private String longtitude;
	private String note;
	private String status;
	private String road_id;
	private String road_name;
	private String checkin_time;
	private String checkout_time;

	//cuongtm thêm 
	private String checkin_distance;
	public String getCheckin_distance() {
		return checkin_distance;
	}
	public void setCheckin_distance(String checkin_distance) {
		this.checkin_distance = checkin_distance;
	}
	//
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssign_name() {
		return assign_name;
	}

	public void setAssign_name(String assign_name) {
		this.assign_name = assign_name;
	}

	public String getDistanceinmeter() {
		return distanceinmeter;
	}

	public void setDistanceinmeter(String distanceinmeter) {
		this.distanceinmeter = distanceinmeter;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCrm_org_role_type() {
		return crm_org_role_type;
	}

	public void setCrm_org_role_type(String crm_org_role_type) {
		this.crm_org_role_type = crm_org_role_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRow_stt() {
		return row_stt;
	}

	public void setRow_stt(String row_stt) {
		this.row_stt = row_stt;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getAssign_id() {
		return assign_id;
	}

	public void setAssign_id(String assign_id) {
		this.assign_id = assign_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getTax_code() {
		return tax_code;
	}

	public void setTax_code(String tax_code) {
		this.tax_code = tax_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getRoad_id() {
		return road_id;
	}

	public void setRoad_id(String road_id) {
		this.road_id = road_id;
	}

	public String getRoad_name() {
		return road_name;
	}

	public void setRoad_name(String road_name) {
		this.road_name = road_name;
	}
	public String getCheckin_time() {
		return checkin_time;
	}

	public void setCheckin_time(String checkin_time) {
		this.checkin_time = checkin_time;
	}

	public String getCheckout_time() {
		return checkout_time;
	}

	public void setCheckout_time(String checkout_time) {
		this.checkout_time = checkout_time;
	}

	public KhachHang() {

	}

	public KhachHang(Parcel source) {
		this.assign_name = source.readString();
		this.distanceinmeter = source.readString();
		this.code = source.readString();
		this.crm_org_role_type = source.readString();
		this.id = source.readString();
		this.row_stt = source.readString();
		this.distance = source.readString();
		this.assign_id = source.readString();
		this.address = source.readString();
		this.image_url = source.readString();
		this.tax_code = source.readString();
		this.name = source.readString();
		this.lattitude = source.readString();
		this.longtitude = source.readString();
		this.note = source.readString();
		this.status = source.readString();
		this.road_id = source.readString();
		this.road_name = source.readString();
		this.checkin_time = source.readString();
		this.checkout_time = source.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(assign_name);
		dest.writeString(distanceinmeter);
		dest.writeString(code);
		dest.writeString(crm_org_role_type);
		dest.writeString(id);
		dest.writeString(row_stt);
		dest.writeString(distance);
		dest.writeString(assign_id);
		dest.writeString(address);
		dest.writeString(image_url);
		dest.writeString(tax_code);
		dest.writeString(name);
		dest.writeString(lattitude);
		dest.writeString(longtitude);
		dest.writeString(note);
		dest.writeString(status);
		dest.writeString(road_id);
		dest.writeString(road_name);
		dest.writeString(checkin_time);
		dest.writeString(checkout_time);
	}

	public static final Parcelable.Creator<KhachHang> CREATOR = new Parcelable.Creator<KhachHang>() {

		@Override
		public KhachHang createFromParcel(Parcel source) {
			return new KhachHang(source);
		}

		@Override
		public KhachHang[] newArray(int size) {
			return new KhachHang[size];
		}
	};
}
