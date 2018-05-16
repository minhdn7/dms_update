package vn.com.vsc.ptpm.VNPT_DMS.common;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;

import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DatHang;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DonHangParam;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhaoSat;

public class Config {
    public static boolean isEmptyEdittext;
    public static boolean isEmptyEdittext2;
    public static boolean isCreateNewDHFromQLDH;
    public static boolean isNewCustomer;
    public static String NAME_KH;
    public static boolean isLogin;
    public static boolean isOnlineMode;
    public static String username;
    public static String password;
    public static String orgId;
    public static String orgCode;
    public static String assignId;
    public static boolean isOpenNew;
    public static String MaDonVi = "";
    public static String currentLat;
    public static String currentLng;
    public static String maGlab = "glab";
    public static String maTMX = "tmx";

    public static final int MAX_RECORDS_UPDATE_PER_TIMES = 15;  //15

    //Thời gian gửi dữ liệu lên server. Là bội số của chu kỳ lấy vị trí GPS
    public static final int TIME_PERIOD_SEND_DATA = 20;

    //Khoảng cách tối thiểu để lấy giá trị này. Để khoảng 5, hoặc 10m. Test thử thì để 0
    public static final int MAX_DISTANCE_ACCURACY = 10;


    // Key Shared Preferences
    public static final String KEY_DATE_DOWNLOAD_DATA_OFFLINE = "date_download_data_offline";
    public static final String KEY_DATE_UPLOAD_DATA_SYNC = "date_upload_data_sync";
    public static final String KEY_TIME_GET_LOCATION = "time_get_location";

    // Key Extra
    public static final String KEY_EXTRA_TYPE_DATA_OFFLINE = "key_data_offline";
    public static final String KEY_EXTRA_TYPE_DATA_SYNC = "key_data_sync";

    // Key
    public static final String KEY_BUNDLE_TUYEN = "bundle_tuyen";
    public static final String KEY_KHACH_HANG_CODE = "khach_hang_code";
    public static final String KEY_DATA_KHACH_HANG = "data_khach_hang";
    public static final String KEY_DATA_TUYEN = "data_tuyen";
    public static final String KEY_POSITION_TUYEN = "position_tuyen";
    public static final String KEY_DATA_TRANG_THAI = "data_trang_thai";
    public static final String KEY_POSITION_TRANG_THAI = "position_trang_thai";
    public static final String KEY_TOTAL_KHACH_HANG = "total_khach_hang";
    public static final String PREF_LOGIN_STATUS = "PREF_LOGIN_STATUS";
    public static final String PREF_KEY_LOGIN_STATUS = "login_status";
    public static final String PREF_INTERVAL = "PREF_INTERVAL";
    public static final String PREF_KEY_INTERVAL = "PREF_KEY_INTERVAL";
    public static final String LAST_USER_LOGIN = "LAST_USER_LOGIN";
    public static final String USER_HAS_JUST_LOGIN = "USER_HAS_JUST_LOGIN";

    // Link Api
    public static final String URL_BASE = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=";
    public static final String URL_GET_KHACH_HANG = URL_BASE + "donhang_themdh_dskh";
    public static final String URL_GET_TYPE_COMPANY = URL_BASE + "ghetham_org_type";
    public static final String URL_GET_NHA_CUNG_CAP = URL_BASE + "donhang_dsncc";
    public static final String URL_GET_TRANG_THAI_DON_HANG = URL_BASE + "donhang_dstt";
    public static final String URL_GET_HINH_THUC_THANH_TOAN = URL_BASE + "donhang_themdh_httt";
    public static final String URL_GET_HANG_HOA_TRONG_KHO = URL_BASE + "donhang_danhmucsp&tenloaisp=&trongkho=1&pageno=0&pagerec=0";
    public static final String URL_GET_HANG_HOA_DANH_MUC = URL_BASE + "donhang_danhmucsp&tenloaisp=&trongkho=0&pageno=0&pagerec=0";
    public static final String URL_GET_LIST_TUYEN_ACTIVE = URL_BASE + "tuyen_dstuyen&pageno=0&json={%22act%22:%221%22}";
    public static final String URL_GET_LIST_TUYEN_KHACH_HANG = URL_BASE + "tuyen_dstuyen&pageno=0&json={%22act%22:%221%22}";
    public static final String URL_GET_TOTAL_DON_HANG = URL_BASE + "donhang_ds&pageno=-1&pagerec=10&";
    public static final String URL_GET_LIST_ROAD = URL_BASE + "tuyen_dsduong";
    public static final String URL_GET_TTKH = URL_BASE + "ghetham_ttkh";
    public static final String URL_GET_THEM_DON_HANG = URL_BASE + "donhang_themdh&";
    public static final String URL_GET_SUA_DON_HANG = URL_BASE + "donhang_suasp";
    public static final String URL_GET_GUI_DON_HANG = URL_BASE + "donhang_guincc";
    public static final String URL_GET_LIST_BINH_LUAN = URL_BASE + "ghetham_binhluan";
    public static final String URL_GET_LIST_KHAO_SAT = URL_BASE + "ghetham_khaosat";

    public static final String URL_UPDATE_LOCATION = URL_BASE + "device_status_upd";
    public static final String URL_UPDATE_DEVICE_INFO = URL_BASE + "capnhat_thongtin_thietbi";
    public static final String URL_TUYEN_KHACH_HANG = URL_BASE + "tuyen_dskh";
    public static final String URL_CHECKIN = URL_BASE + "ghetham_checkin";
    public static final String URL_LOGOUT = URL_BASE + "hethong_logout";
    public static final String URL_DANH_SACH_DON_HANG = URL_BASE + "donhang_ds";
    public static final String URL_THEM_MOI_BINH_LUAN = URL_BASE + "ghetham_binhluan_themmoi";
    public static final String URL_CAP_NHAT_KHAO_SAT = URL_BASE + "ghetham_khaosat_capnhat";
    public static final String URL_DELETE_DON_HANG = URL_BASE + "donhang_xoa";

    public static String UpdateLocation(Double latitude, Double longitude, String time, int pin, String type, int value) {
        return URL_UPDATE_LOCATION
                + "&latitude=" + latitude
                + "&longitude=" + longitude
                + "&time=" + time
                + "&pin=" + pin
                + "&type_network=" + type
                + "&value=" + value;
    }

    public static String GetUrlLDsTuyenKhachHang(String ID, int pageno, String done, String MA_KH) {
        String url = URL_TUYEN_KHACH_HANG + "&pageno="
                + pageno
                + "&pagerec=50&json={%22trxid%22:%22"
                + ID
                + "%22,%22done%22:%22"
                + done
                + "%22,%22makh%22:%22"
                + MA_KH
                + "%22}";
        return url;
    }

    public static String GetRoad(String tuyenId) {
        return URL_GET_LIST_ROAD + "&assign_id==" + tuyenId;
    }

    public static String GetUrlCheckin(String ckin) {
        String url = URL_CHECKIN + "&json=" + ckin.toString();
        Log.i("url-checkin", url);
        return url;
    }


    public static String GetUrlTTKH(String khachhangId, String code, String assignId) {
        String url = URL_GET_TTKH
                + "&org_id=" + khachhangId
                + "&org_code=" + code
                + "&assign_id=" + assignId;
        return url;
    }

    public static String GetUrlTotalDonHang(DonHangParam dh) {
        return URL_GET_TOTAL_DON_HANG + getDefJsonObj(dh);
    }

    public static String getUrlDanhsachDonhang(DonHangParam dh, String pagerec) {
        String url = API_code.URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_ds&pageno=1&pagerec=" +
                pagerec + "&" + getDefJsonObj(dh);

        return url;
    }

    private static String getDefJsonObj(DonHangParam dh) {
        String jsonResult = "";
        Type itemType = new TypeToken<DonHangParam>() {
        }.getType();
        jsonResult = new Gson().toJson(dh, itemType);
        return ("json=" + jsonResult);
    }

    public static String getUrlAddDonHang(DatHang dh) {
        String url = URL_GET_THEM_DON_HANG + getDefJsonObj(dh);
        return url;
    }

    private static String getDefJsonObj(DatHang dh) {
        String jsonResult = "";
        Type itemType = new TypeToken<DatHang>() {
        }.getType();
        jsonResult = new Gson().toJson(dh, itemType);
        return ("json=" + jsonResult);
    }

    public static String getUrlCNDH(String po_id, String loaisp_id, String soluong, String gia) {
        String url = URL_GET_SUA_DON_HANG
                + "&po_id=" + po_id
                + "&loaisp_id=" + loaisp_id
                + "&soluong=" + soluong
                + "&gia=" + gia;
        return url;
    }

    public static String getUrlGuiNCC(String po_id) {
        return URL_GET_GUI_DON_HANG + "&po_id=" + po_id;
    }


    public static String getUrlBinhLuan(String khachhangId, String assignID) {
        String url = URL_GET_LIST_BINH_LUAN
                + "&pageno=1&pagerec=10"
                + "&org_id=" + khachhangId
                + "&assign_id=" + assignID;
        return url;
    }

    public static String GuiBinhLuan(String khachhangId, String assignID, String note) {
        String url = URL_THEM_MOI_BINH_LUAN
                + "&org_id=" + khachhangId
                + "&assign_id=" + assignID
                + "&comment=" + note;
        return url;
    }

    public static String getUrlKhaosat(String orgId, String orgCode, String assignId) {
        String url = URL_GET_LIST_KHAO_SAT
                + "&org_id=" + orgId
                + "&org_code=" + orgCode
                + "&assign_id=" + assignId;
        return url;
    }

    public static String CapNhatKhaoSat(String orgId, String orgCode, String assignId, ArrayList<KhaoSat> arrItems, String a[]) {
        String url = "";
        try {
            String data = "";
            if (arrItems.size() > 0) {
                for (int i = 0; i < arrItems.size(); i++) {
                    KhaoSat item = arrItems.get(i);

                    if (item.getComponent_type().equals("checkbox")) {
                        data += "\",\"a_" + item.getId() + "\":\"" + a[i];
                    }

                    if (item.getComponent_type().equals("select")) {
                        String[] value = a[i].toString().split(";");
                        if (value.length > 0) {
                            data += "\",\"a_" + item.getId() + "\":\"" + value[0];

                            String text = "";
                            try {
                                text = URLEncoder.encode(value[1].toString(), "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            data += "\",\"a_" + item.getId() + "_dsp\":\"" + text;
                        }
                    }

                    if (item.getComponent_type().equals("text")) {
                        String text = "";
                        try {
                            text = URLEncoder.encode(a[i].toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        data += "\",\"a_" + item.getId() + "\":\"" + text;
                    }
                }
            }

            url = URL_CAP_NHAT_KHAO_SAT + "&json=" + "{\"id\":\"" + orgId + "\",\"code\":\"" + orgCode + "\",\"assign\":\"" + assignId
                    + data + "\"}";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String DeleteDonHang(String donhangId) {
        String url = URL_DELETE_DON_HANG + "&po_id=" + donhangId;
        return url;
    }
}