package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class Rpt_Kpi_Model {
    //cuongtm thêm
    public static final String TABLE_NAME = "rpt_kpi";
    public static final String ID = "id";
    public static final String KPI_TARGET_NAME = "kpi_target_name";
    public static final String KE_HOACH = "ke_hoach";
    public static final String THUC_TE = "thuc_te";
    public static final String CON_LAI = "con_lai";
    public static final String TIEN_DO = "tien_do";

    private int kpi_id;
    private String kpi_target_name;
    private String ke_hoach;
    private String thuc_te;
    private String con_lai;
    private String tien_do;
	private boolean holiday;


    public Rpt_Kpi_Model() {
        super();
    }

    public Rpt_Kpi_Model(String kpi_target_name, String ke_hoach, String thuc_te, String con_lai, String tien_do) {
        this.kpi_target_name = kpi_target_name;
        this.ke_hoach = ke_hoach;
        this.thuc_te = thuc_te;
        this.con_lai = con_lai;
        this.tien_do = tien_do;
    }

    public int getKpi_id() {
        return kpi_id;
    }

    public void setKpi_id(int kpi_id) {
        this.kpi_id = kpi_id;
    }

    public String getKpi_target_name() {
        return kpi_target_name;
    }

    public void setKpi_target_name(String kpi_target_name) {
        this.kpi_target_name = kpi_target_name;
    }

    public String getKe_hoach() {
        return ke_hoach;
    }

    public void setKe_hoach(String ke_hoach) {
        this.ke_hoach = ke_hoach;
    }

    public String getThuc_te() {
        return thuc_te;
    }

    public void setThuc_te(String thuc_te) {
        this.thuc_te = thuc_te;
    }

    public String getCon_lai() {
        return con_lai;
    }

    public void setCon_lai(String con_lai) {
        this.con_lai = con_lai;
    }

    public String getTien_do() {
        return tien_do;
    }

    public void setTien_do(String tien_do) {
        this.tien_do = tien_do;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }
}
