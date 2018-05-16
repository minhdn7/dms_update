package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import java.io.Serializable;

public class SP_SL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3760635213709615823L;
	private SanPham sp;
	private DonViSP dvi;
	// private String id_dat;
	// private String sl_dat;
	// private String don_vi;
	//private String don_gia;
	
	private String ton_kho;
	private String selected = "0";
	private String sl_dat;
	private String tong_t_hang = "0";
	private String tongTienTruocVAT = "0";

	public String getTong_t_hang() {
		return tong_t_hang;
	}


	
	public void setTong_t_hang(String tong_t_hang) {
		this.tong_t_hang = tong_t_hang;
	}

	public String getSl_dat() {
		return sl_dat;
	}

	public void setSl_dat(String sl_dat) {
		this.sl_dat = sl_dat;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public SP_SL(SanPham sp, DonViSP dvi, String sl_dat, String ton_kho) {
		super();
		this.sp = sp;
		this.dvi = dvi;
		this.sl_dat = sl_dat;
		this.ton_kho = ton_kho;
	}

	public SP_SL(SanPham sp, DonViSP dvi, String sl_dat) {
		super();
		this.sp = sp;
		this.dvi = dvi;
		this.sl_dat = sl_dat;
	}

	public DonViSP getDvi() {
		return dvi;
	}

	public void setDvi(DonViSP dvi) {
		this.dvi = dvi;
	}

	public String getTon_kho() {
		return ton_kho;
	}

	public void setTon_kho(String ton_kho) {
		this.ton_kho = ton_kho;
	}

	public SanPham getSp() {
		return sp;
	}

	public void setSp(SanPham sp) {
		this.sp = sp;
	}

	public String getTongTienTruocVAT() {
		return tongTienTruocVAT;
	}

	public void setTongTienTruocVAT(String tongTienTruocVAT) {
		this.tongTienTruocVAT = tongTienTruocVAT;
	}

	@Override
	public String toString() {
		return "SP_SL [sp=" + sp.toString() + ", dvi=" + dvi.toString() + ", ton_kho=" + ton_kho
				+ ", selected=" + selected + ", sl_dat=" + sl_dat
				+ ", tong_t_hang=" + tong_t_hang + "]";
	}


}
