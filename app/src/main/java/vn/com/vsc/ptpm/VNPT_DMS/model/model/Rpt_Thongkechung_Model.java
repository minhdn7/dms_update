package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class Rpt_Thongkechung_Model {
    //cuongtm thêm
    public static final String TABLE_NAME = "rpt_thongkechung";
    public static final String ID = "id";

    public static final String KPI_TARGET_NAME = "";
    public static final String KPI_TARGET_VALUE = "";
    public static final String VALUE_CURRENT = "";
    public static final String CONLAI = "";
    public static final String TIENDO = "";

    private int kpi_id;
    private String kpi_target_name;
    private String kpi_target_value;
    private String value_current;
    private String conlai;
    private String tiendo;
    private boolean holiday;

    public Rpt_Thongkechung_Model() {
        super();
    }

    public Rpt_Thongkechung_Model(String kpi_target_name, String kpi_target_value, String value_current, String conlai, String tiendo) {
        this.kpi_target_name = kpi_target_name;
        this.kpi_target_value = kpi_target_value;
        this.value_current = value_current;
        this.conlai = conlai;
        this.tiendo = tiendo;
    }

    public String getKpi_target_name() {
        return kpi_target_name;
    }

    public void setKpi_target_name(String kpi_target_name) {
        this.kpi_target_name = kpi_target_name;
    }

    public String getKpi_target_value() {
        return kpi_target_value;
    }

    public void setKpi_target_value(String kpi_target_value) {
        this.kpi_target_value = kpi_target_value;
    }


    public String getValue_current() {
        return value_current;
    }

    public void setValue_current(String value_current) {
        this.value_current = value_current;
    }

    public String getConlai() {
        return conlai;
    }

    public void setConlai(String conlai) {
        this.conlai = conlai;
    }

    public String getTiendo() {
        return tiendo;
    }

    public void setTiendo(String tiendo) {
        this.tiendo = tiendo;
    }

    public int getId() {
        return kpi_id;
    }

    public void setId(int kpi_id) {
        this.kpi_id = kpi_id;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }
}
