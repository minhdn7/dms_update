package vn.com.vsc.ptpm.VNPT_DMS.model.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class HeaderAPI {
    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public HeaderAPI(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
