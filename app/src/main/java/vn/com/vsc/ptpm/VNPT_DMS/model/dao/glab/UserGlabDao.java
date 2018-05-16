package vn.com.vsc.ptpm.VNPT_DMS.model.dao.glab;

import com.google.gson.Gson;

import okhttp3.HttpUrl;
import retrofit2.Call;
import vn.com.vsc.ptpm.VNPT_DMS.common.Config;
import vn.com.vsc.ptpm.VNPT_DMS.model.dao.BaseDao;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabCapNhatThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabDanhSachDonDatHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.request.glab.GlabThemMoiDonHangRequest;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.BaseService;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IFinishedListener;
import vn.com.vsc.ptpm.VNPT_DMS.model.service.IService;

/**
 * Created by MinhDN on 20/11/2017.
 */

public class UserGlabDao extends BaseDao implements IUserGlabDao {
    private IService service = BaseService.createServiceDMS(IService.class);

    @Override
    public void getDanhSachDonDatHang(int pageno, int pagerec, GlabDanhSachDonDatHangRequest request, IFinishedListener iFinishedListener) {

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Call<Object> call = service.glabDanhSachDonDatHang(pageno, pagerec, json, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getDanhSachNhomXetNghiem(IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabDanhSachNhomXetNghiem(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getDanhSachXetNghiem(int pageno, int pagerec, int shopId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabDanhSachXetNghiem(pageno, pagerec, shopId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getDanhSachTrangThaiDonHang(IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabDanhSachTrangThaiDonHang(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getThongTinDonHang(int poId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabThongTinDonHang(poId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getXoaDonHang(int poId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabXoaDonHang(poId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getChiTietDonHang(int pageno, int pagerec, int poId, String tenLoaiSanPham, IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabChiTietDonHang(pageno, pagerec, poId, tenLoaiSanPham, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getCapNhatTrangThai(int poId, IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabCapNhatTrangThai(poId, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getDanhSachNhaCungCap(IFinishedListener iFinishedListener) {

        Call<Object> call = service.glabDanhSachNhaCungCap(Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getCapNhatDonHangThemMoi(GlabThemMoiDonHangRequest request, IFinishedListener iFinishedListener) {

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Call<Object> call = service.glabCapNhatDonHangThemMoi(json, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }

    @Override
    public void getCapNhatDonHang(GlabCapNhatDonHangRequest request, IFinishedListener iFinishedListener) {

        Gson gson = new Gson();
        String json = gson.toJson(request);
        Call<Object> call = service.glabCapNhatDonHang(json, Config.username, Config.password);
        HttpUrl url = call.request().url();
        call(call, iFinishedListener);
    }


}
