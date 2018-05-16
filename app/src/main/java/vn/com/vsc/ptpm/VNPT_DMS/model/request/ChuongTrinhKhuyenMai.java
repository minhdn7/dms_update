package vn.com.vsc.ptpm.VNPT_DMS.model.request;

/**
 * Created by MinhDN on 16/3/2017.
 */

public class ChuongTrinhKhuyenMai {
    private String id;
    private String code;
    private String name;
    private String type;
    private String starDate;
    private String endDate;

    public ChuongTrinhKhuyenMai() {
    }

    public ChuongTrinhKhuyenMai(String id, String code, String name, String type, String starDate, String endDate) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.starDate = starDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
