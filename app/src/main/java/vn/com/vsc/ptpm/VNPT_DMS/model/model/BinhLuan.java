package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class BinhLuan {

	public static final String TABLE_NAME = "binhluan";
	public static final String ID = "id";
	
	public static final String CREATED_DATE = "created_date";
	public static final String CREATED_BY = "created_by";
	public static final String ROW_STT = "row_stt";
	public static final String IMAGE_URL = "image_url";
	public static final String IMAGE_RELATED_ID = "image_related_id";
	public static final String NOTE = "note";
	public static final String STATUS = "status";
	public static final String ORG_ID = "org_id";
	public static final String ASSIGN_ID = "assign_id";


	private int id;
	private String created_date;
	private String created_by;
	private String row_stt;
	private String image_url;
	private String image_related_id;
	private String note;
	private int status; // 0: online; 1: offline
	private String org_id;
	private String assign_id;

	public BinhLuan() {
		super();
	}

	public BinhLuan(String orgId, String assignId, String note, int status, String createdDate, String createdBy) {
		this.org_id = orgId;
		this.assign_id = assignId;
		this.note = note;
		this.status = status;
		this.created_date = createdDate;
		this.created_by = createdBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getRow_stt() {
		return row_stt;
	}

	public void setRow_stt(String row_stt) {
		this.row_stt = row_stt;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getImage_related_id() {
		return image_related_id;
	}

	public void setImage_related_id(String image_related_id) {
		this.image_related_id = image_related_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getAssign_id() {
		return assign_id;
	}

	public void setAssign_id(String assign_id) {
		this.assign_id = assign_id;
	}

}
