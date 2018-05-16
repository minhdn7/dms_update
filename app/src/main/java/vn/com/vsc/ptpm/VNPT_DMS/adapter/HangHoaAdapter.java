package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.entity.HangHoaEntity;

public class HangHoaAdapter extends BaseAdapter {
	LayoutInflater mInflater;
	List<HangHoaEntity> mList;
	Context mContext;
	ConvertFont cf = new ConvertFont();
	boolean isTK;
	int check = 0;

	public HangHoaAdapter(Context c, List<HangHoaEntity> list, boolean isTK) {
		mInflater = LayoutInflater.from(c);
		this.mList = list;
		this.mContext = c;
		this.isTK = isTK;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public HangHoaEntity getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("static-access")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final List<String> mListTK = new ArrayList<String>();
		if (convertView == null) {
			convertView = mInflater.inflate(vn.com.vsc.ptpm.VNPT_DMS.R.layout.item_list_hh, parent, false);	
		}
		TextView stt_hh = (TextView) convertView.findViewById(vn.com.vsc.ptpm.VNPT_DMS.R.id.tv_sttHH);
		ImageView thumb_hh = (ImageView) convertView.findViewById(vn.com.vsc.ptpm.VNPT_DMS.R.id.iv_thumbnailHH);
		TextView title_hh = (TextView) convertView.findViewById(vn.com.vsc.ptpm.VNPT_DMS.R.id.tv_titleHH);
		Spinner gia_hh = (Spinner) convertView.findViewById(vn.com.vsc.ptpm.VNPT_DMS.R.id.sp_giaHH);
		final TextView tonkho_hh = (TextView) convertView.findViewById(vn.com.vsc.ptpm.VNPT_DMS.R.id.tv_tonkhoHH);
		gia_hh.setFocusable(false);

		final HangHoaEntity item = mList.get(position);
		if (item != null) {
			if (item.getRow_stt() != null) {
				stt_hh.setText(item.getRow_stt());
			} else {
				stt_hh.setText("" + (position+1));
			}

			Picasso.with(mContext)
			.load(API_code.URL_API_ROOT + item.getUrl())
			.resize(80, 80).into(thumb_hh);
			title_hh.setText(item.getCode() + " - " + cf.getUTF8StringFromNCR(item.getName()));

			List<String> price = getDSDonViHH(item.getDs_donvi(), item.getSoluong(), mListTK);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.simple_spinner_item, price);
			adapter.setDropDownViewResource(R.layout.simple_list_item_single_choice);
			gia_hh.setAdapter(adapter);

			gia_hh.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					if (!isTK) {
						tonkho_hh.setText("Tồn kho : " + item.getSoluong());
					} else {
						tonkho_hh.setText("Tồn kho : " + mListTK.get(arg2));
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}

		Log.i("Position : ", String.valueOf(position));
		return convertView;
	}

	@SuppressWarnings("static-access")
	public List<String> getDSDonViHH(String ds_donvi, String soluong, List<String> mListTK) {
		ds_donvi = cf.getUTF8StringFromNCR(ds_donvi);
		List<String> result = new ArrayList<String>();
		String[] ds_dovi = ds_donvi.split("\\|");
		DecimalFormat formatterTK = new DecimalFormat("###.##");
		DecimalFormat formatterPrice = new DecimalFormat("#,###,###");

		for (int i = 0; i < ds_dovi.length; i++) {
			String[] s_donvi = ds_dovi[i].split(";");
			try {
				String d_tonkho = formatterTK.format(Double.parseDouble(soluong) / Double.parseDouble(s_donvi[1]));
//				String price = formatterPrice.format(Long.parseLong(s_donvi[s_donvi.length - 1]));
				String price = formatterPrice.format(Double.parseDouble(s_donvi[s_donvi.length - 1]));
				String element = price + " đ/" + s_donvi[2];
				result.add(element);
				mListTK.add(d_tonkho);
			} catch (Exception e) {
				Log.e("Format error : ", e.toString());
			}
		}
		return result;
	}
}
