package vn.com.vsc.ptpm.VNPT_DMS.control;

public class API_code {
//    public final static String URL_API_ROOT = "https://biz.vnptsoftware.vn/vnpt/"; //for vsc
    public final static String URL_API_ROOT = "http://123.31.10.61:8080/vnpt/"; //new site test 10/2017
//    public final static String URL_API_ROOT = "http://123.31.25.13:8080/vnpt/"; //new site test

//    public final static String URL_API_ROOT = "http://10.145.40.237:8080/vnpt/";//may tinh Nhat
    //public final static String URL_API_ROOT = "https://cloud.neo.vn/neo/"; //for neoSS

//    public final static String URL_API_ROOT = "http://10.145.40.239:8080/vnpt/"; //for test - truongtx api

    //public final static String URL_API_ROOT = "http://10.145.35.116:8081/vnpt/";  //Máy tính của Cuong test

    //public final static String URL_API_ROOT = "http://10.145.35.26:8080/vnpt/";  //Máy tính của Long CTY
    //public final static String URL_API_ROOT = "http://10.145.32.210:8081/vnpt/";  //Máy tính của laptop wifi PTPM5
    //public final static String URL_API_ROOT = "http://192.168.1.6:8080/vnpt/";  //May tinh LONG, o nha

    //public final static String URL_API_ROOT = "http://192.168.1.101:8081/vnpt/";  //May tinh Cuong, o nha

    //public final static String URL_API_ROOT = "http://192.168.5.100:8081/vnpt/";

    public static final String URL = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?";
    public static final int CALLBACK_DSTUYEN_ACTIVE = 1;
    public static final int CALLBACK_TOTAL_KH = 2;

    public final String LOGIN_API = "ghetham_tt_nvkd";
    public final String GET_DSKH = "tuyen_dstuyen";

    public final static String URL_LOGIN = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_tt_nvkd";
    public final static String URL_THEMKH = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_ttkh_themmoi";
    //cap nhat t.tin KH
    public final static String URL_UPDATE_KH = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_ttkh_capnhat";
    //cap nhat gps
    public final static String URL_UPDATE_NEW_KH = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_diachi";
    public final static String URL_TYPE_COMPANY = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_org_type";
    public final static String URL_HANGHOA = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_danhmucsp";
    public final static String URL_KHUYENMAI = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_tinhkm";

    public final static String URL_DMK = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=hethong_doimk";
    public final static String URL_DS_NVKD = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=baocao_dsnvkd";
    public final static String URL_DS_DONDATHANG = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=donhang_ds";

    //public final static String URL_BC_TONGHOP = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=baocao_th";
    public final static String URL_BC_TONGHOP = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=rpt_thongkechung";
    public final static String URL_DSKH_CHUA_DATHANG = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=rpt_dskh_chuadathang";
    public final static String URL_BC_KPI = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=rpt_kpi";

    public final static String URL_GHETHAM_DSKH = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=ghetham_dskh";
    public final static String URL_DOACT = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=doact";

    // them lay danh sach khuyen mai
    public final static String URL_DANH_SACH_KHUYEN_MAI = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=get_promotion_v1";

    // lấy thông tin tuyến
    public final static String URL_TONG_HOP_KET_QUA_TUYEN = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=tonghop_kqtuyen";

    // kiểm tra đặt hàng - màn hình tuyến
    public final static String URL_KIEM_TRA_DAT_HANG = URL_API_ROOT + "mobiapp_api_v1.jsp?callback=?&api_code=checkin_order";

    public static String URL_DOWN_FILE;

    public static double latitude = 0, longitude = 0;



}
