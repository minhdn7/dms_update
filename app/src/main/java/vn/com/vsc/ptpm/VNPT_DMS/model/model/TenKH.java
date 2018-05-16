package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class TenKH {

	public static final String TABLE_NAME = "tenkh";
	public static final String TABLE_NAME_NCC = "ncc";
	public static final String TABLE_NAME_TTDH = "ttdh";
	
	public static final String ID = "id";
	public static final String NAME = "name";
	
	private String id;
	private String name;

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

	public TenKH() {
		super();
	}

	public TenKH(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
