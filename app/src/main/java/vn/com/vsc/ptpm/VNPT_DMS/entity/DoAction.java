package vn.com.vsc.ptpm.VNPT_DMS.entity;

public class DoAction {

	public static final String ID = "id";
	public static final String ACT = "act";
	public static final String TBL = "tbl";
	public static final String SFIELD = "sfield";
	public static final String SVALUE = "svalue";
	public static final String SWHERE = "swhere";

	private String id;
	private String act;
	private String tbl;
	private String sfield;	
	private String svalue;
	private String swhere;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getTbl() {
		return tbl;
	}

	public void setTbl(String tbl) {
		this.tbl = tbl;
	}
	
	public String getSfield() {
		return sfield;
	}

	public void setSfield(String sfield) {
		this.sfield = sfield;
	}
	
	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}
	
	public String getSwhere() {
		return swhere;
	}

	public void setSwhere(String swhere) {
		this.swhere = swhere;
	}
	
	public DoAction() {
	}
	
	public DoAction(String id, String act, String tbl, String sfield, String svalue, String swhere) {
		this.id = id;
		this.act = act;
		this.tbl = tbl;
		this.sfield = sfield;
		this.svalue = svalue;
		this.swhere = swhere;
	}
}
