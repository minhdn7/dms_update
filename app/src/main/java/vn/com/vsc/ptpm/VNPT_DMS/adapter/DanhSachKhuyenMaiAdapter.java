package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.event.EvenDanhSachIdKhuyenMaiSuaDonHang;
import vn.com.vsc.ptpm.VNPT_DMS.event.EventDanhSachLuaChonKhuyenMai;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.ChuongTrinhKhuyenMaiResponse;

/**
 * Created by MinhDN on 17/3/2017.
 */

public class DanhSachKhuyenMaiAdapter extends ArrayAdapter<ChuongTrinhKhuyenMaiResponse>{
    private Context context;
    private int resource;
    private List<ChuongTrinhKhuyenMaiResponse> objects;
    private ConvertFont conv = new ConvertFont();
    private Boolean luaChonKhuyenMai = false;
    private ArrayList<ChuongTrinhKhuyenMaiResponse> danhSachLuaChonKhuyenMai;
    private EventDanhSachLuaChonKhuyenMai eventDanhSachLưaChonKhuyenMai;


    public DanhSachKhuyenMaiAdapter(Context context, int resource, List<ChuongTrinhKhuyenMaiResponse> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final DanhSachKhuyenMaiAdapter.ViewHolder viewHoler = new ViewHolder();
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        luaChonKhuyenMai = false;
        danhSachLuaChonKhuyenMai = new ArrayList<>();

        viewHoler.txtStt = (TextView) view.findViewById(R.id.txtStt);
        viewHoler.txtIdMa = (TextView) view.findViewById(R.id.txtIdMa);
        viewHoler.txtTenChuongTrinh = (TextView) view.findViewById(R.id.txtTenChuongTrinh);
        viewHoler.txtNgayHieuLuc = (TextView) view.findViewById(R.id.txtNgayHieuLuc);
        viewHoler.imgCheckKhuyenMai = (ImageView) view.findViewById(R.id.imgCheckKhuyenMai);

        if(position == 0) {
            viewHoler.txtStt.setText(R.string.stt);
            viewHoler.txtIdMa.setTypeface(null, Typeface.BOLD);
            viewHoler.txtTenChuongTrinh.setTypeface(null, Typeface.BOLD);
            viewHoler.txtNgayHieuLuc.setTypeface(null, Typeface.BOLD);
            viewHoler.imgCheckKhuyenMai.setVisibility(View.GONE);
        }else{
            // gán giá trị stt
            viewHoler.txtStt.setText(String.valueOf(position));
        }

        viewHoler.txtIdMa.setText(conv.getUTF8StringFromNCR(objects.get(position).getMaid()));
        viewHoler.txtTenChuongTrinh.setText(conv.getUTF8StringFromNCR(objects.get(position).getName()));
        viewHoler.txtNgayHieuLuc.setText(objects.get(position).getNgayHieuLuc());
        ChuongTrinhKhuyenMaiResponse item = getItem(position);
        // kiem tra trang thai da chon khuyen mai truoc do hay chua
        eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
        if(eventDanhSachLưaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai() != null){
            danhSachLuaChonKhuyenMai = eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai();
            for( ChuongTrinhKhuyenMaiResponse itemKhuyenMai : eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai()){
                if(item.getId().equals(itemKhuyenMai.getId())){
                    luaChonKhuyenMai = true;
                    break;
                }
            }

        }

        EvenDanhSachIdKhuyenMaiSuaDonHang eventDanhSachIdKhuyenMai = EventBus.getDefault().getStickyEvent(EvenDanhSachIdKhuyenMaiSuaDonHang.class);
        if(eventDanhSachIdKhuyenMai != null && eventDanhSachIdKhuyenMai.getDanhSachIdKhuyenMai().size() > 0){
            for(String idKhuyenMaiNhanTuServer : eventDanhSachIdKhuyenMai.getDanhSachIdKhuyenMai()){
                if(idKhuyenMaiNhanTuServer.equals(item.getId())){
                    luaChonKhuyenMai = true;
                    // them danh sach khuyen mai tu server vào danh sach khuyen mai duoi client
                     danhSachLuaChonKhuyenMai.add(item);
                     eventDanhSachLưaChonKhuyenMai = new EventDanhSachLuaChonKhuyenMai(danhSachLuaChonKhuyenMai);
                     EventBus.getDefault().postSticky(eventDanhSachLưaChonKhuyenMai);
                    // xoa trong danh sach khuyen mai tra ve tu server
                    eventDanhSachIdKhuyenMai.getDanhSachIdKhuyenMai().remove(idKhuyenMaiNhanTuServer);
                    // end
                    break;
                }
            }

        }

        // remove Event Khuyen Mai Nhan tu server

        if(luaChonKhuyenMai){
            // reset trang thai lua chon khuyen mai
            viewHoler.imgCheckKhuyenMai.setImageDrawable(view.getResources().getDrawable(R.drawable.check));
        }else{
            //luaChonKhuyenMai = true;
            viewHoler.imgCheckKhuyenMai.setImageDrawable(view.getResources().getDrawable(R.drawable.uncheck));
        }



        // add Event item click
        viewHoler.imgCheckKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDanhSachLưaChonKhuyenMai = EventBus.getDefault().getStickyEvent(EventDanhSachLuaChonKhuyenMai.class);
                if(!luaChonKhuyenMai){
                    luaChonKhuyenMai = true;
                    viewHoler.imgCheckKhuyenMai.setImageDrawable(v.getResources().getDrawable(R.drawable.check));
                    if(eventDanhSachLưaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai() != null){
                        boolean checkSanPhamTrungId = false;

                        for( ChuongTrinhKhuyenMaiResponse itemKhuyenMai : eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai()){
                            if(itemKhuyenMai.getId().equals(getItem(position).getId())){
                                checkSanPhamTrungId = true;
                            }
                        }
                        if(!checkSanPhamTrungId){
                            danhSachLuaChonKhuyenMai = eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai();
                        }

                    }
                     danhSachLuaChonKhuyenMai.add(getItem(position));

                }else {
                    luaChonKhuyenMai = false;
                    viewHoler.imgCheckKhuyenMai.setImageDrawable(v.getResources().getDrawable(R.drawable.uncheck));
                    if(danhSachLuaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai != null && eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai() != null){
                        for( ChuongTrinhKhuyenMaiResponse itemKhuyenMai : eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai()){
                            if(itemKhuyenMai.getId().equals(getItem(position).getId())){
                                danhSachLuaChonKhuyenMai.remove(itemKhuyenMai);
                                break;
                            }
//                            if(eventDanhSachLưaChonKhuyenMai.getDanhSachChuongTrinhKhuyenMai() == null || danhSachLuaChonKhuyenMai == null){
//                                break;
//                            }
                        }

                    }
                    if(danhSachLuaChonKhuyenMai != null){

                        for(int i = 0; i < danhSachLuaChonKhuyenMai.size(); i ++){
                            if(danhSachLuaChonKhuyenMai.get(i).getId().equals(getItem(position).getId())){
                                danhSachLuaChonKhuyenMai.remove(i);
                            }
                        }
                    }

                }
                if(danhSachLuaChonKhuyenMai == null){
                    EventBus.getDefault().removeStickyEvent(eventDanhSachLưaChonKhuyenMai);
                }
                eventDanhSachLưaChonKhuyenMai = new EventDanhSachLuaChonKhuyenMai(danhSachLuaChonKhuyenMai);
                EventBus.getDefault().postSticky(eventDanhSachLưaChonKhuyenMai);
            }
        });
        // end
        return view;
    }

    static class ViewHolder {
        TextView txtStt;
        TextView txtIdMa;
        TextView txtTenChuongTrinh;
        TextView txtNgayHieuLuc;
        ImageView imgCheckKhuyenMai;
    }

}
