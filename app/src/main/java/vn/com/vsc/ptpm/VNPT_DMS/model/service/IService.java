package vn.com.vsc.ptpm.VNPT_DMS.model.service;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MinhDN on 2/10/2017.
 */

public interface IService {
    /***********************************TMX************************
     Service TMX
     *******************************************************************/

    @GET(ServiceUrl.CHECK_TMX_USER)
    public Call<Object> checkUserTMS(@Query("username") String username,
                                     @Query("password") String password);

    @GET(ServiceUrl.DANH_SACH_KHAO_SAT)
    public Call<Object> getDanhSachKhaoSatTMS(
                                    @Query("pageno") int pageno,
                                    @Query("pagerec") int pageRec,
                                    @Query("customer") String customer,
                                    @Query("month") String month,
                                    @Query("username") String username,
                                     @Query("password") String password);

    @GET(ServiceUrl.XOA_PHIEU_KHAO_SAT)
    public Call<Object> xoaKhaoSatTMS(@Query("survey_id") String survey_id,
                                    @Query("username") String username,
                                     @Query("password") String password);

    @GET(ServiceUrl.DANH_SACH_NHAN_HIEU)
    public Call<Object> layNhanHieuTMS(@Query("username") String username,
                                     @Query("password") String password);

    @GET(ServiceUrl.DANH_SACH_CHUNG_LOAI)
    public Call<Object> layChungLoaiTMS(@Query("username") String username,
                                       @Query("password") String password);

    @GET(ServiceUrl.DANH_SACH_NHA_CUNG_CAP)
    public Call<Object> layNhanPhanPhoiTMS(@Query("key") String key,
                                        @Query("username") String username,
                                       @Query("password") String password);

    @GET(ServiceUrl.DANH_SACH_KHACH_HANG)
    public Call<Object> layKhachHangTMS(@Query("key") String key,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.THONG_TIN_KHAO_SAT_CHI_TIET)
    public Call<Object> layThongTinKhaoSatChiTietTMS(@Query("id") String id,
                                            @Query("username") String username,
                                           @Query("password") String password);

    @GET(ServiceUrl.THEM_MOI_KHAO_SAT)
    public Call<Object> capNhatKhaoSatTMS(@Query("id") String idKhaoSat,
                                          @Query("cus") String idKhachHang,
                                          @Query("month") String thangKhaoSat,
                                          @Query("comment") String comment,
                                          @Query("is_daily") String is_daily,
                                          @Query("json") String json,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.GET_USER_INFO_TMX)
    public Call<Object> layThongTinUserTMS(@Query("org_id") String org_id,
                                           @Query("username") String username,
                                           @Query("password") String password);

    /*********Cập nhật danh sách tuyến***********************
     *
     * @param username
     * @param password
     * @return
     */
    @GET(ServiceUrl.TMX_DANG_KY_LICH_TRINH)
    public Call<Object> dangKyLichTrinhTMX(
                                        @Query("sdate") String sdate,
                                        @Query("sweek") String sweek,
                                        @Query("snote") String snote,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_DANG_KY_LICH_TRINH_CHI_TIET)
    public Call<Object> dangKyLichTrinhChiTietTMX(
                                        @Query("register_id") String register_id,
                                        @Query("json") String json,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_CAP_NHAT_LICH_TRINH_CHI_TIET)
    public Call<Object> capNhatLichTrinhChiTietTMX(
                                        @Query("register_id") String register_id,
                                        @Query("json") String json,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_HUY_DS_DA_DANG_KY)
    public Call<Object> huyDanhSachDangKyTMX(
                                        @Query("register_id") String register_id,
                                        @Query("json") String json,
                                        @Query("username") String username,
                                        @Query("password") String password);


    @GET(ServiceUrl.TMX_CAP_NHAT_DANG_KY)
    public Call<Object> capNhatDangKyTMX(
                                        @Query("register_id") String register_id,
                                        @Query("sdate") String sdate,
                                        @Query("sweek") String sweek,
                                        @Query("snote") String snote,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_XOA_DANG_KY)
    public Call<Object> xoaDangKyTMX(
                                        @Query("register_id") String register_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_DANH_SACH_DANG_KY_LICH_TRINH)
    public Call<Object> danhSachDangKyLichTrinhTMX(
                                        @Query("pageno") int pageNo,
                                        @Query("page_rec") int pageRec,
                                        @Query("is_active") String is_active,
                                        @Query("sdate") String sdate,
                                        @Query("sweek") String sweek,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_THONG_TIN_DANG_KY_CHI_TIET)
    public Call<Object> thongTinDangKyChiTietTMX(
                                        @Query("register_id") String register_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_SACH_KHACH_HANG_DE_DANG_KY)
    public Call<Object> layDanhSachKhachHangDeDangKyTMX(
                                        @Query("pageno") int pageno,
                                        @Query("pagerec") int pagerec,
                                        @Query("tukhoa") String tukhoa,
                                        @Query("branch") String branch,
                                        @Query("area") String area,
                                        @Query("province") String province,
                                        @Query("district") String district,
                                        @Query("list_org_id") String list_org_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_SACH_KHACH_HANG_DA_DANG_KY)
    public Call<Object> layDanhSachKhachHangDangKyTMX(
                                        @Query("pageno") int pageno,
                                        @Query("pagerec") int pagerec,
                                        @Query("register_id") String register_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_SACH_VUNG)
    public Call<Object> layDanhSachVungTMX(
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_SACH_KHU_VUC)
    public Call<Object> layDanhSachKhuVucTMX(
                                        @Query("branch_id") String branch_id,
                                        @Query("username") String username,
                                        @Query("password") String password);


    @GET(ServiceUrl.TMX_LAY_DANH_SACH_TINH)
    public Call<Object> layDanhSachTinhTMX(
                                        @Query("area_id") String area_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_SACH_TINH_PHAN_TUYEN)
    public Call<Object> layDanhSachTinhTMX(
                                        @Query("branch_id") String branch_id,
                                        @Query("area_id") String area_id,
                                        @Query("username") String username,
                                        @Query("password") String password);
    @GET(ServiceUrl.TMX_LAY_DANH_SACH_QUAN)
    public Call<Object> layDanhSachQuanTMX(
                                        @Query("province_id") String province_id,
                                        @Query("username") String username,
                                        @Query("password") String password);

    @GET(ServiceUrl.TMX_LAY_DANH_ID_KHACH_HANG_DA_DANG_KY)
    public Call<Object> layDanhSachIdKhachHangDaDangKyTMX(
                                        @Query("register_id") String register_id,
                                        @Query("username") String username,
                                        @Query("password") String password);
    /***********************************GREEN_LAB************************
        Service Glab
     *******************************************************************/
    @GET(ServiceUrl.LAY_MA_DON_VI)
    public Call<Object> layMaDonVi(@Query("username") String username,
                                   @Query("password") String password);

    @GET(ServiceUrl.GLAB_DANH_SACH_DON_DAT_HANG)
    public Call<Object> glabDanhSachDonDatHang(
                                    @Query("pageno") int pageno,
                                    @Query("pagerec") int pagerec,
                                    @Query("json") String json,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_DANH_SACH_NHOM_XET_NGHIEM)
    public Call<Object> glabDanhSachNhomXetNghiem(
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_DANH_SACH_XET_NGHIEM)
    public Call<Object> glabDanhSachXetNghiem(
                                    @Query("pageno") int pageno,
                                    @Query("pagerec") int pagerec,
                                    @Query("shop_id") int shop_id,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_DANH_SACH_TRANG_THAI_DON_HANG)
    public Call<Object> glabDanhSachTrangThaiDonHang(
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_THONG_TIN_DON_HANG)
    public Call<Object> glabThongTinDonHang(
                                    @Query("po_id") int po_id,
                                    @Query("username") String username,
                                    @Query("password") String password);


    @GET(ServiceUrl.GLAB_XOA_DON_HANG)
    public Call<Object> glabXoaDonHang(
                                    @Query("po_id") int po_id,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_CHI_TIET_DON_HANG)
    public Call<Object> glabChiTietDonHang(
                                    @Query("pageno") int pageno,
                                    @Query("pagerec") int pagerec,
                                    @Query("po_id") int po_id,
                                    @Query("tenloaisp") String tenloaisp,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_CAP_NHAT_TRANG_THAI)
    public Call<Object> glabCapNhatTrangThai(
                                    @Query("po_id") int po_id,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_DANH_SACH_NHA_CUNG_CAP)
    public Call<Object> glabDanhSachNhaCungCap(
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.GLAB_CAP_NHAT_DON_HANG_THEM_MOI)
    public Call<Object> glabCapNhatDonHangThemMoi(
                                    @Query("json") String json,
                                    @Query("username") String username,
                                    @Query("password") String password);


    @GET(ServiceUrl.GLAB_CAP_NHAT_DON_HANG)
    public Call<Object> glabCapNhatDonHang(
                                    @Query("json") String json,
                                    @Query("username") String username,
                                    @Query("password") String password);

    // cập nhật ảnh trong tuyến
    @GET(ServiceUrl.THAY_DOI_ANH_DAI_DIEN)
    public Call<Object> changeImageAva(
                                    @Query("image_id") Integer image_id,
                                    @Query("related_id") Integer related_id,
                                    @Query("username") String username,
                                    @Query("password") String password);

    @GET(ServiceUrl.XOA_ANH)
    public Call<Object> deleteImage(
                                    @Query("image_id") Integer image_id,
                                    @Query("username") String username,
                                    @Query("password") String password);
    // end
}
