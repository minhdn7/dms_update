package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class LoginModel {
	private String user_name="";
	private String language_id;
	private String active_shop_code;
	private String active_shop_id;
	private String mobile_theme_id;
	private String api_error;
	private String error_id;

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}
	public String getActive_shop_code() {
		return active_shop_code;
	}
	public void setActive_shop_code(String active_shop_code) {
		this.active_shop_code = active_shop_code;
	}
	public String getActive_shop_id() {
		return active_shop_id;
	}
	public void setActive_shop_id(String active_shop_id) {
		this.active_shop_id = active_shop_id;
	}
	public String getMobile_theme_id() {
		return mobile_theme_id;
	}
	public void setMobile_theme_id(String mobile_theme_id) {
		this.mobile_theme_id = mobile_theme_id;
	}
	public String getApi_error() {
		return api_error;
	}
	public void setApi_error(String api_error) {
		this.api_error = api_error;
	}
	public String getError_id() {
		return error_id;
	}
	public void setError_id(String error_id) {
		this.error_id = error_id;
	}
	public LoginModel() {
		super();
	}

}
