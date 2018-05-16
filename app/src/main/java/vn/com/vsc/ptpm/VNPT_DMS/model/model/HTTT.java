package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class HTTT {
	public static final String TABLE_NAME = "hinhthucthanhtoan";
	
	public static final String HTTT_ID = "httt_id";
	public static final String HINHTHUC = "hinhthuc";
	
	private String hinhthuc;
	private String httt_id;
	
	public String getHinhthuc() {
		return hinhthuc;
	}
	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
	}
	public String getHttt_id() {
		return httt_id;
	}
	public void setHttt_id(String httt_id) {
		this.httt_id = httt_id;
	}
	
	public HTTT() {
		super();
	}
	
}
