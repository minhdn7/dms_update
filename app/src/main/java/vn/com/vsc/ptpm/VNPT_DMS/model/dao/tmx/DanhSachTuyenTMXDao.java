package vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.BaseDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachKhachHangDangKyResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IService;

/**
 * Created by MinhDN on 12/12/2017.
 */

public class DanhSachTuyenTMXDao extends BaseDao implements IDanhSachTuyenTMXDao{
    private IService service = BaseService.createServiceDMS(IService.class);

    @Override
    public void dangKyLichTrinhTMXDao(String sdate, String sweek, String snote, IFinishedListener iFinishedListener) {

        Call<Object> call = service.dangKyLichTrinhTMX(sdate, sweek, snote, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void dangKyLichTrinhChiTietTMXDao(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse, IFinishedListener iFinishedListener) {
        String json = "{" + "\"target\"" + ":" + "{";
        for(int i = 0; i < danhSachKhachHangDangKyResponse.size(); i++){
            json += "\"" + "target_id" + (i + 1) + "\"";
            json += ":";
            json += "\"" + danhSachKhachHangDangKyResponse.get(i).getOrgId() + "\"";
            json += ",";
            json += "\"" + "target_value" + (i + 1) + "\"";
            json += ":";
            String arrayThu = danhSachKhachHangDangKyResponse.get(i).getMon() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getTue() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getWed() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getThu() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getFri() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getSat() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getSun();
            json += "\"" + arrayThu + "\"";
            if(i != (danhSachKhachHangDangKyResponse.size() - 1)){
                json += ",";
            }
        }
        json += "},";
        json += "\"target_num\"" + ":" + danhSachKhachHangDangKyResponse.size() + "}";

        Call<Object> call = service.dangKyLichTrinhChiTietTMX(registerId, json,  Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void capNhatLichTrinhChiTietTMXDao(String registerId, List<DanhSachKhachHangDangKyResponse> danhSachKhachHangDangKyResponse, IFinishedListener iFinishedListener) {

        Gson gson = new Gson();
        String json = "{" + "\"target\"" + ":" + "{";
        for(int i = 0; i < danhSachKhachHangDangKyResponse.size(); i++){
            json += "\"" + "target_id" + (i + 1) + "\"";
            json += ":";
            json += "\"" + danhSachKhachHangDangKyResponse.get(i).getOrgId() + "\"";
            json += ",";
            json += "\"" + "target_value" + (i + 1) + "\"";
            json += ":";
            String arrayThu = danhSachKhachHangDangKyResponse.get(i).getMon() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getTue() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getWed() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getThu() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getFri() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getSat() + ","
                    + danhSachKhachHangDangKyResponse.get(i).getSun();
            json += "\"" + arrayThu + "\"";
            if(i != (danhSachKhachHangDangKyResponse.size() - 1)){
                json += ",";
            }
        }
        json += "},";
        json += "\"target_num\"" + ":" + danhSachKhachHangDangKyResponse.size() + "}";
        Call<Object> call = service.capNhatLichTrinhChiTietTMX(registerId, json,  Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void huyDanhSachDangKyTMXDao(String registerId, IFinishedListener iFinishedListener) {

        String json = "";
        Call<Object> call = service.huyDanhSachDangKyTMX(registerId, json,  Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void capNhatDangKyTMXDao(String registerId, String sdate, String sweek, String snote, IFinishedListener iFinishedListener) {

        Call<Object> call = service.capNhatDangKyTMX(registerId, sdate, sweek, snote, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void xoaDangKyTMXDao(String registerId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.xoaDangKyTMX(registerId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void thongTinDangKyChiTietTMXDao(String registerId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.thongTinDangKyChiTietTMX(registerId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachKhachHangDeDangKyTMXDao(int pageNo, int pageRec, String tuKhoa, String branch, String area, String province, String district, String listOrgId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachKhachHangDeDangKyTMX(pageNo, pageRec, tuKhoa, branch, area, province, district, listOrgId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachKhachHangDangKyTMXDao(int pageNo, int pageRec, String registerId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachKhachHangDangKyTMX(pageNo, pageRec, registerId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachVungTMXDao(IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachVungTMX(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachKhuVucTMXDao(String branchId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachKhuVucTMX(branchId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachTinhTMXDao(String areaId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachTinhTMX(areaId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachTinhTMXDao(String branchId, String areaId, IFinishedListener iFinishedListener) {
        Call<Object> call = service.layDanhSachTinhTMX(branchId, areaId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachQuanTMXDao(String provinceId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachQuanTMX(provinceId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachDangKyLichTrinhTMXDao(int pageNo, int pageRec, String isActive, String sDate, String sWeek, IFinishedListener iFinishedListener) {

        Call<Object> call = service.danhSachDangKyLichTrinhTMX(pageNo, pageRec, isActive, sDate, sWeek, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layDanhSachIdKhachHangDao(String registerId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.layDanhSachIdKhachHangDaDangKyTMX(registerId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }
}
