package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class KHParcel implements Parcelable {

	private List<KhachHang> arrList = new ArrayList<KhachHang>();
	private int myInt = 0;
	private String str = null;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public List<KhachHang> getArrList() {
		return arrList;
	}

	public void setArrList(List<KhachHang> arrList) {
		this.arrList = arrList;
	}

	public int getMyInt() {
		return myInt;
	}

	public void setMyInt(int myInt) {
		this.myInt = myInt;
	}

	public KHParcel() {
		// initialization
		arrList = new ArrayList<KhachHang>();
	}

	public KHParcel(Parcel in) {
		myInt = in.readInt();
		str = in.readString();
		in.readTypedList(arrList, KhachHang.CREATOR);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel outParcel, int flags) {
		outParcel.writeInt(myInt);
		outParcel.writeString(str);
		outParcel.writeTypedList(arrList);
	}

	public static final Parcelable.Creator<KHParcel> CREATOR = new Parcelable.Creator<KHParcel>() {

		@Override
		public KHParcel createFromParcel(Parcel in) {
			return new KHParcel(in);
		}

		@Override
		public KHParcel[] newArray(int size) {
			return new KHParcel[size];
		}
	};
}