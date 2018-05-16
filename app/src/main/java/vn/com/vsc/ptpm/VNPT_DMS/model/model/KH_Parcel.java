package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KH_Parcel implements Parcelable {
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

	public KH_Parcel() {
	}

	public KH_Parcel(Parcel source) {
		// TODO Auto-generated constructor stub
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

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
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
