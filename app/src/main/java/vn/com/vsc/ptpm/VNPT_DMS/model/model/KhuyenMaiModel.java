package vn.com.vsc.ptpm.VNPT_DMS.model.model;

public class KhuyenMaiModel {

    //cuongtm thêm
    public static final String TABLE_NAME = "khuyenmai";
    public static final String ID = "id";

    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String DESCRIPTION = "description";


    private int id;
    private String code;
    private String name;
    private String start_date;
    private String end_date;
    private String description;

    public KhuyenMaiModel() {
        super();
    }

    public KhuyenMaiModel(String code, String name, String start_date, String end_date, String description, int id) {
        this.code = code;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }


    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String start_date) {
        this.start_date = start_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
