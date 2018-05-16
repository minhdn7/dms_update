package vn.com.vsc.ptpm.VNPT_DMS.model.model;

/**
 * Created by ThaoPit on 12/22/2016.
 */

public class NameKHDisplay {
    private String name;
    private String code;
    private String id;

    public NameKHDisplay(String id, String name, String code) {
        this.name = name;
        this.code = code;
        this.id = id;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
