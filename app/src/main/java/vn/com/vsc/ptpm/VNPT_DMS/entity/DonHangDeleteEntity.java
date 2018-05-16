package vn.com.vsc.ptpm.VNPT_DMS.entity;

public class DonHangDeleteEntity {
	
	public static final String TABLE_NAME = "donhangdelete";
	
	public static final String ID = "id";
	public static final String DONHANGID = "donhangId";

	private int id;
	private String donhangId;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDonhangId() {
		return donhangId;
	}
	public void setDonhangId(String donhangId) {
		this.donhangId = donhangId;
	}
	
	public DonHangDeleteEntity() {
	}
	
	public DonHangDeleteEntity(String donhangId) {
		this.donhangId = donhangId;
	}
}
