package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class HinhAnh {
	private String id;
	private String created_date;
	private String created_by;
	private String related_id;
	private String applied_table;
	private String file_size;
	private String name;
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getRelated_id() {
		return related_id;
	}

	public void setRelated_id(String related_id) {
		this.related_id = related_id;
	}

	public String getApplied_table() {
		return applied_table;
	}

	public void setApplied_table(String applied_table) {
		this.applied_table = applied_table;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
