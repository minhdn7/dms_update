package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tuyen implements Parcelable {

	public static final String TABLE_NAME = "tuyen";

	public static final String NO_OF_NONE = "no_of_none";
	public static final String IS_FINISH = "is_finish";
	public static final String IS_ACTIVE = "is_active";
	public static final String CODE = "code";
	public static final String SELLER = "seller";
	public static final String TOTAL_OF_ORDER = "total_of_order";
	public static final String ID = "id";
	public static final String ROW_STT = "row_stt";
	public static final String END_DATE = "end_date";
	public static final String NO_OF_DONE = "no_of_done";
	public static final String NO_OF_ORDERED_CUSTOMER = "no_of_ordered_customer";
	public static final String NO_OF_ORDER = "no_of_order";
	public static final String NAME = "name";
	public static final String START_DATE = "start_date";

	private String no_of_none;
	private String is_finish;
	private String is_active;
	private String code;
	private String seller;
	private String total_of_order;
	private String id;
	private String row_stt;
	private String end_date;
	private String no_of_done;
	private String no_of_ordered_customer;
	private String no_of_order;
	private String name;
	private String start_date;

	public String getNo_of_none() {
		return no_of_none;
	}

	public void setNo_of_none(String no_of_none) {
		this.no_of_none = no_of_none;
	}

	public String getIs_finish() {
		return is_finish;
	}

	public void setIs_finish(String is_finish) {
		this.is_finish = is_finish;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getTotal_of_order() {
		return total_of_order;
	}

	public void setTotal_of_order(String total_of_order) {
		this.total_of_order = total_of_order;
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

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getNo_of_done() {
		return no_of_done;
	}

	public void setNo_of_done(String no_of_done) {
		this.no_of_done = no_of_done;
	}

	public String getNo_of_ordered_customer() {
		return no_of_ordered_customer;
	}

	public void setNo_of_ordered_customer(String no_of_ordered_customer) {
		this.no_of_ordered_customer = no_of_ordered_customer;
	}

	public String getNo_of_order() {
		return no_of_order;
	}

	public void setNo_of_order(String no_of_order) {
		this.no_of_order = no_of_order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public Tuyen(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Tuyen() {
	}

	public Tuyen(Parcel source) {
		this.no_of_none = source.readString();
		this.is_finish = source.readString();
		this.is_active = source.readString();
		this.code = source.readString();
		this.seller = source.readString();
		this.total_of_order = source.readString();
		this.id = source.readString();
		this.row_stt = source.readString();
		this.end_date = source.readString();
		this.no_of_done = source.readString();
		this.no_of_ordered_customer = source.readString();
		this.no_of_order = source.readString();
		this.name = source.readString();
		this.start_date = source.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(no_of_none);
		dest.writeString(is_finish);
		dest.writeString(is_active);
		dest.writeString(code);
		dest.writeString(seller);
		dest.writeString(total_of_order);
		dest.writeString(id);
		dest.writeString(row_stt);
		dest.writeString(end_date);
		dest.writeString(no_of_done);
		dest.writeString(no_of_ordered_customer);
		dest.writeString(no_of_order);
		dest.writeString(name);
		dest.writeString(start_date);
	}

	public static final Parcelable.Creator<Tuyen> CREATOR = new Parcelable.Creator<Tuyen>() {

		@Override
		public Tuyen createFromParcel(Parcel source) {
			return new Tuyen(source);
		}

		@Override
		public Tuyen[] newArray(int size) {
			return new Tuyen[size];
		}
	};

}
