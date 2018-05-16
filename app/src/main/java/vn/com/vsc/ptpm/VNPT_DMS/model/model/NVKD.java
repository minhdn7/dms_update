package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class NVKD{
	public String user_name;
	public String userid;

	public NVKD(String name, String id) {
		// TODO Auto-generated constructor stub
		this.user_name = name;
		this.userid = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
