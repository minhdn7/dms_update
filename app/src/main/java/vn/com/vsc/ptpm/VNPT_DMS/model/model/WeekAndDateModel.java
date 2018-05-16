package vn.com.vsc.ptpm.VNPT_DMS.model.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhDN on 18/12/2017.
 */

public class WeekAndDateModel {
    @SerializedName("week")
    @Expose
    private int week;

    @SerializedName("date")
    @Expose
    private String date;

    public WeekAndDateModel(int week, String date) {
        this.week = week;
        this.date = date;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
