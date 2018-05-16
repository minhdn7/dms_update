package vn.com.vsc.ptpm.VNPT_DMS.model.dao.tmx;

import com.google.gson.Gson;

import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.BaseDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.DanhSachKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.tmx.SoLieuKhaoSatRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IService;

/**
 * Created by MinhDN on 2/10/2017.
 */

public class UserTMXDao extends BaseDao implements IUserTMXDao{
    private IService service;
    @Override
    public void checkUserTMXDao(IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.checkUserTMS(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getDanhSachKhaoSatTMXDao(DanhSachKhaoSatRequest request, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.getDanhSachKhaoSatTMS(request.getPageno(),
                request.getPageRec(),
                request.getCustomer(),
                request.getMonth(),
                Config.username,
                Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }


    @Override
    public void xoaKhaoSatTMXDao(String idSurvey, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.xoaKhaoSatTMS(idSurvey, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layNhanHieuTMXDao(IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layNhanHieuTMS(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layChungLoaiTMXDao(IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layChungLoaiTMS(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layNhaPhanPhoiTMXDao(String key, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layNhanPhanPhoiTMS(key, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layKhachHangTMXDao(String key, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layKhachHangTMS(key, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void layThongTinKhachHangTMXDao(String orgId, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layThongTinUserTMS(orgId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }


    @Override
    public void layThongTinKhaoSatChiTietTMXDao(String orgId, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Call<Object> call = service.layThongTinKhaoSatChiTietTMS(orgId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void capNhatKhaoSatTMXDao(String idKhaoSat, String idKhachHang, String thangKhaoSat, String comment, String daiLy, List<SoLieuKhaoSatRequest> soLieuKhaoSatRequests, IFinishedListener iFinishedListener) {
        service = BaseService.createServiceDMS(IService.class);
        Gson gson = new Gson();
        String[] arrSoLieu = new String[soLieuKhaoSatRequests.size()];
        for(int i = 0; i < soLieuKhaoSatRequests.size(); i++){
            arrSoLieu[i] = gson.toJson(soLieuKhaoSatRequests.get(i));
            String brand = "brand" + (i + 1);
            String product_cat = "product_cat" + (i + 1);
            String supplier = "supplier" + (i + 1);
            String tran_cost = "tran_cost" + (i + 1);
            String load_cost = "load_cost" + (i + 1);
            String buy_price = "buy_price" + (i + 1);
            String sell_price = "sell_price" + (i + 1);
            String quan_expected = "quan_expected" + (i + 1);
            arrSoLieu[i] = arrSoLieu[i].replace("brand", brand);
            arrSoLieu[i] = arrSoLieu[i].replace("product_cat", product_cat);
            arrSoLieu[i] = arrSoLieu[i].replace("supplier", supplier);
            arrSoLieu[i] = arrSoLieu[i].replace("tran_cost", tran_cost);
            arrSoLieu[i] = arrSoLieu[i].replace("load_cost", load_cost);
            arrSoLieu[i] = arrSoLieu[i].replace("buy_price", buy_price);
            arrSoLieu[i] = arrSoLieu[i].replace("sell_price", sell_price);
            arrSoLieu[i] = arrSoLieu[i].replace("quan_expected", quan_expected);
//            arrSoLieu[i] = arrSoLieu[i].replaceAll("\"", "%22");
            arrSoLieu[i] = arrSoLieu[i].substring(1, arrSoLieu[i].length() - 1);

        }
        String json = "{" + "\"target\"" + ":" + "{";
        for(int i = 0; i < arrSoLieu.length; i++){
            json += arrSoLieu[i];
        }
        json += "},";
        json += "\"target_num\"" + ":" + arrSoLieu.length + "}";


        Call<Object> call = service.capNhatKhaoSatTMS(idKhaoSat, idKhachHang, thangKhaoSat, comment, daiLy, json, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }



}
