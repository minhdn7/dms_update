package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class TypeCompany {
	
	public static final String TABLE_NAME = "typecompany";
	
	public static final String ID = "id";
	public static final String NAME = "name";
	
	private String id;
	private String name;
	
	public TypeCompany() {
	}
	
	public TypeCompany(String _id, String _name) {
		this.id = _id;
		this.name = _name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
