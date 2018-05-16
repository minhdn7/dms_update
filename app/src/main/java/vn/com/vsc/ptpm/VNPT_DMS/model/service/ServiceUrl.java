package vn.com.vsc.ptpm.VNPT_DMS.model.service;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class ServiceUrl {
    //API TMX
    public static final String DANH_SACH_KHAO_SAT = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_list_survey";
    public static final String THONG_TIN_KHAO_SAT = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_list_survey_by_id";
    public static final String THONG_TIN_KHAO_SAT_CHI_TIET = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_detail_by_id";
    public static final String XOA_PHIEU_KHAO_SAT = "mobiapp_api_tmx.jsp?callback=?&api_code=delete_survey_data";
    public static final String THEM_MOI_KHAO_SAT = "mobiapp_api_tmx.jsp?callback=?&api_code=ins_upd_survey_data";
    public static final String DANH_SACH_NHA_CUNG_CAP = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_list_supplier";
    public static final String DANH_SACH_NHAN_HIEU = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_brand";
    public static final String DANH_SACH_CHUNG_LOAI = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_product_cat";
    public static final String DANH_SACH_KHACH_HANG = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_list_customer";
    public static final String CHECK_TMX_USER = "mobiapp_api_tmx.jsp?callback=?&api_code=is_schema_tmx";
    public static final String GET_USER_INFO_TMX = "mobiapp_api_tmx.jsp?callback=?&api_code=tmx_survey_list_customer_by_id";

    // api danh sách tuyến
    public static final String TMX_DANG_KY_LICH_TRINH = "mobiapp_api_tmx.jsp?callback=?&api_code=router_register";
    public static final String TMX_DANG_KY_LICH_TRINH_CHI_TIET = "mobiapp_api_tmx.jsp?callback=?&api_code=register_detail";
    public static final String TMX_CAP_NHAT_LICH_TRINH_CHI_TIET = "mobiapp_api_tmx.jsp?callback=?&api_code=update_detail";
    public static final String TMX_HUY_DS_DA_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=cancel_detail";
    public static final String TMX_CAP_NHAT_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=update_route_register";
    public static final String TMX_XOA_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=delete_route_register";
    public static final String TMX_DANH_SACH_DANG_KY_LICH_TRINH = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_dangky";
    public static final String TMX_THONG_TIN_DANG_KY_CHI_TIET = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_dangky_chitiet";
    public static final String TMX_LAY_DANH_SACH_KHACH_HANG_DE_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_dskh_dangky";
    public static final String TMX_LAY_DANH_SACH_KHACH_HANG_DA_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_dskh_dadangky";
    public static final String TMX_LAY_DANH_SACH_VUNG = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_branch_cbo";
    public static final String TMX_LAY_DANH_SACH_KHU_VUC = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_area_cbo";
    public static final String TMX_LAY_DANH_SACH_TINH = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_tinh_cbo";
    public static final String TMX_LAY_DANH_SACH_TINH_PHAN_TUYEN = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_tinh_cbo_dst";
    public static final String TMX_LAY_DANH_SACH_QUAN = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_quan_cbo";
    public static final String TMX_LAY_DANH_ID_KHACH_HANG_DA_DANG_KY = "mobiapp_api_tmx.jsp?callback=?&api_code=layds_idkh_dadk";
    // end

    //API Glab
    public static final String LAY_MA_DON_VI = "mobiapp_api_glab.jsp?callback=?&api_code=lay_madv";
    public static final String GLAB_DANH_SACH_DON_DAT_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_ds_glab";
    public static final String GLAB_DANH_SACH_NHOM_XET_NGHIEM = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_nhomsp_glab";
    public static final String GLAB_DANH_SACH_XET_NGHIEM = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_dmsp_glab";
    public static final String GLAB_DANH_SACH_TRANG_THAI_DON_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_dstt_glab";
    public static final String GLAB_THONG_TIN_DON_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_ttdh_glab";
    public static final String GLAB_XOA_DON_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_xoa_glab";
    public static final String GLAB_CHI_TIET_DON_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_dssp_glab";
    public static final String GLAB_CAP_NHAT_TRANG_THAI = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_guincc_glab";
    public static final String GLAB_DANH_SACH_NHA_CUNG_CAP = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_dsncc_glab";
    public static final String GLAB_CAP_NHAT_DON_HANG_THEM_MOI = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_themdh_glab";
    public static final String GLAB_CAP_NHAT_DON_HANG = "mobiapp_api_glab.jsp?callback=?&api_code=donhang_suadh_glab";
    // end

    // chỉnh sửa ảnh đại diện, màn hình tuyến
    public static final String THAY_DOI_ANH_DAI_DIEN = "mobiapp_api_tmx.jsp?callback=?&api_code=update_ava";
    public static final String XOA_ANH = "mobiapp_api_tmx.jsp?callback=?&api_code=delete_images";
    // end
}
