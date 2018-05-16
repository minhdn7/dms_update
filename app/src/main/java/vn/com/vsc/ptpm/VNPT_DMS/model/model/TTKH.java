package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TTKH implements Parcelable {
	
	public static final String TABLE_NAME = "ttkh";
	
	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String MANAGER = "manager";
	public static final String MOBILE = "mobile";
	public static final String TEL = "tel";
	public static final String FAX = "fax";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String ADDRESS_ID = "address_id";
	public static final String TAX_CODE = "tax_code";
	public static final String NOTE = "note";
	public static final String LATTITUDE = "lattitude";
	public static final String LONGTITUDE = "longtitude";
	
	
	private String id;
	private String code;
	private String name;
	private String manager;
	private String mobile;
	private String tel;
	private String fax;	
	private String email;
	private String address;
	private String address_id;
	private String tax_code;
	private String note;
	private String lattitude;
	private String longtitude;


	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public TTKH() {
		super();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public TTKH(Parcel source) {
		this.fax = source.readString();
		this.manager = source.readString();
		this.tel = source.readString();
		this.code = source.readString();
		this.id = source.readString();
		this.email = source.readString();
		this.address = source.readString();
		this.address_id = source.readString();
		this.tax_code = source.readString();
		this.name = source.readString();
		this.lattitude = source.readString();
		this.longtitude = source.readString();
		this.note = source.readString();
		this.mobile = source.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(fax);
		dest.writeString(manager);
		dest.writeString(tel);
		dest.writeString(code);
		dest.writeString(id);
		dest.writeString(email);
		dest.writeString(address);
		dest.writeString(address_id);
		dest.writeString(tax_code);
		dest.writeString(name);
		dest.writeString(lattitude);
		dest.writeString(longtitude);
		dest.writeString(note);
		dest.writeString(mobile);

	}

	public static final Parcelable.Creator<TTKH> CREATOR = new Parcelable.Creator<TTKH>() {

		@Override
		public TTKH createFromParcel(Parcel source) {
			return new TTKH(source);
		}

		@Override
		public TTKH[] newArray(int size) {
			return new TTKH[size];
		}
	};
}
