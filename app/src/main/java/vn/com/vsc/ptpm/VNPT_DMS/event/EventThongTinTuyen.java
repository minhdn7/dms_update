package vn.com.vsc.ptpm.VNPT_DMS.event;

/**
 * Created by MinhDN on 12/7/2017.
 */

public class EventThongTinTuyen {
    private String dayTuyen;
    private String idTuyen;

    public String getDayTuyen() {
        return dayTuyen;
    }

    public void setDayTuyen(String dayTuyen) {
        this.dayTuyen = dayTuyen;
    }

    public String getIdTuyen() {
        return idTuyen;
    }

    public void setIdTuyen(String idTuyen) {
        this.idTuyen = idTuyen;
    }

    public EventThongTinTuyen(String dayTuyen, String idTuyen) {
        this.dayTuyen = dayTuyen;
        this.idTuyen = idTuyen;
    }
}
