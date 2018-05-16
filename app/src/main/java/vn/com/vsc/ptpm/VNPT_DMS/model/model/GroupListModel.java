package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 24/11/2017.
 */

public class GroupListModel {
    @SerializedName("group_name")
    @Expose
    private String nameGroup;

    @SerializedName("group_id")
    @Expose
    private String idGroup;

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public GroupListModel(String nameGroup, String idGroup) {
        this.nameGroup = nameGroup;
        this.idGroup = idGroup;
    }
}
