package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.Controller;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont3;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhachHang;

public class DSKHListAdapter extends BaseAdapter {
    private Context context;
    private List<KhachHang> KHItems;
    private LayoutInflater inflater;
    private ConvertFont conv = new ConvertFont();
    private ConvertFont3 conv3 = new ConvertFont3();
    private String url_head = API_code.URL_API_ROOT;
    private OnCustomClickListener callback;
    private Controller control;

    public DSKHListAdapter(Context context, List<KhachHang> KHItems, OnCustomClickListener callback) {
        this.context = context;
        this.KHItems = KHItems;
        this.callback = callback;
        this.inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.control = new Controller(context);
    }

    public void updateData(ArrayList<KhachHang> arrayListEntity) {
        KHItems.addAll(arrayListEntity);
        notifyDataSetChanged();
    }

    @SuppressWarnings("static-access")
    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final KhachHang m = KHItems.get(position);

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_list_kh, null);

            holder.dskh_item = (RelativeLayout) convertView.findViewById(R.id.dskh_item);
            holder.kh_chitiet = (ImageButton) convertView.findViewById(R.id.kh_chitiet);
            holder.numb = (TextView) convertView.findViewById(R.id.kh_dem);
            holder.title = (TextView) convertView.findViewById(R.id.kh_title);
            holder.address = (TextView) convertView.findViewById(R.id.kh_address);
            holder.status = (TextView) convertView.findViewById(R.id.kh_trangthai);
            holder.avatar = (ImageView) convertView.findViewById(R.id.kh_thumbnail);
            holder.khoangcach = (TextView) convertView.findViewById(R.id.khoangcach);
            holder.donhang = (TextView) convertView.findViewById(R.id.donhang);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Fill data

        if (m != null) {
            holder.numb.setText(String.valueOf(position + 1));
            if (m.getStatus() != null) {
                if (m.getStatus().equals("HT")) {
                    holder.numb.setBackgroundResource(R.color.Checked);
                } else {
                    holder.numb.setBackgroundResource(R.color.neo_xanhlam);
                }
            }

            String code = conv.getUTF8StringFromNCR(m.getCode());
            String name = conv.getUTF8StringFromNCR(m.getName());
            holder.title.setText(code + " - " + name);
            holder.address.setText(conv.getUTF8StringFromNCR(m.getAddress()));

            // Name
            //holder.title.setText(conv.getUTF8StringFromNCR(m.getCode()) + " - "
            //		+ conv.getUTF8StringFromNCR(m.getName()));
            // address
            //holder.address.setText(conv.getUTF8StringFromNCR(m.getAddress()));

            String khoangcach, tmp, tmp1;
            khoangcach = "";
            tmp = "";
            tmp1 = "";
            /*
             //Tạm bỏ không dùng, khi cuộn thì sẽ lấy lại, khoảng cách gây lỗi. Chuyển lấy trên server
			Log.i("cuong isDistance=", mAF.isDistance + "");
			if ((m.getLongtitude() != "") && (m.getLongtitude().trim() != "0") && (mAF.isDistance <= 1))
			{
				
				
				 
				tmp = mAF.getURLDistance(mAF.latCurrent + "," + mAF.longCurrent ,  m.getLattitude() + "," + m.getLongtitude());
				Log.d("cuong url distance", tmp);	
				tmp = "";
				if (tmp != "")
				{
					try {
						String kq = new DownloadURLData().execute(tmp).get();
						//Log.d("cuong distance json", kq);						
						JSONObject jsonRespRouteDistance = new JSONObject(kq)
	                    .getJSONArray("rows")
	                    .getJSONObject(0)
	                    .getJSONArray ("elements")
	                    .getJSONObject(0)
	                    .getJSONObject("distance");

						khoangcach = jsonRespRouteDistance.get("text").toString();
						Log.d("cuong khoangcach", khoangcach);
						
						//mAF.isDistance = mAF.isDistance + 1;
						//holder.khoangcach.setText(khoangcach);
					       
					} 
					catch (Exception e) {
						Log.i("errcuong", e.toString());
					}
				}
				//if (tmp != "")					
				tmp = "";
				
			}
			//if ((m.getLongtitude() != "") && (m.getLongtitude().trim() != "0") && (mAF.isDistance == 0))
			*/


            //Log.i("img-url", m.getImage_url());
            Picasso.with(context).load(url_head + m.getImage_url()).resize(70, 70).into(holder.avatar);

            //m.getDistanceinmeter()
            //m.setDistance(distance)

            if (control.isOnline(context) && mAF.chkDieukien()) {
                tmp = m.getCheckin_time().trim() + "";
                if (tmp.contains("null")) {
                    tmp = "";
                } else {
                    if (tmp.length() >= 4) {
                        tmp = " [" + tmp;
                    }
                }
                // sửa lỗi m.getCheckin_distance().trim() null
                try {
                    tmp1 = m.getCheckin_distance().trim() + "";
                }catch(Exception ex){
                    Log.d("m fail: ", ex.toString());
                }
                // fail
                if (tmp1.contains("null")) {
                    tmp1 = "";
                } else {
                    if (tmp1.length() >= 2) {
                        tmp1 = ", " + mAF.getDistanceStr(tmp1) + "]";
                    }
                }

                if ((tmp.length() > 4) && (tmp1.length() == 0)) {
                    tmp = tmp + "]";
                }
                holder.khoangcach.setText(mAF.getDistanceStr(m.getDistance()));
            }
            // nghiệp vụ cũ check trường tất cả, nghiệp vụ thay đổi 10/7/2017 ko check trường này
//            if (TuyenFragment.isTatCa) {
//                holder.status.setText("");
//            } else {
//                holder.status.setText(getValues_TT(m.getStatus()) + tmp + tmp1);
//            }
            holder.status.setText(getValues_TT(m.getStatus()) + tmp + tmp1);
            holder.dskh_item.setOnClickListener(new CustomOnClickListener(callback, position));
            holder.kh_chitiet.setOnClickListener(new CustomOnClickListener(callback, position));
            holder.donhang.setText("");

            //cuongtm them : change color

            try {
//				if ((mAF.rowClick == position) && (mAF.rowClick != 0))

                if (mAF.rowClick == position) {
                    convertView.setBackgroundResource(R.color.YellowGreen);
                } else {
                    convertView.setBackgroundResource(R.color.transparent);
                }
            } catch (Exception e) {

            }


        }

        return convertView;
    }

    static class ViewHolder {
        public TextView numb, title, address, status, khoangcach, donhangimg, donhang;
        public ImageView avatar;
        public RelativeLayout dskh_item;
        public ImageButton kh_chitiet;
    }

    @Override
    public int getCount() {
        if (KHItems.size() > 0)
            return KHItems.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int location) {
        if (KHItems.size() > 0)
            return KHItems.get(location);
        else
            return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private String getValues_TT(String status) {
        String result = "";
        if (status.equals("HT")) {
            result = "Đã ghé thăm";
        } else if (status.equals("DH")) {
            result = "Đã đặt hàng";
        } else if (status.equals("")) {
            result = "Chưa ghé thăm";
        }
        //Log.i("result", result);
        return result;
    }


}
