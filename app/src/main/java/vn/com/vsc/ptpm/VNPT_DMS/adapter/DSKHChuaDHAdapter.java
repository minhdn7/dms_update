package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.ResultDSKHChuaDH;

public class DSKHChuaDHAdapter extends BaseAdapter {

	List<ResultDSKHChuaDH> mList;
	LayoutInflater mInflater;
	ConvertFont cf = new ConvertFont();

	public DSKHChuaDHAdapter(Context c, List<ResultDSKHChuaDH> list) {
		// TODO Auto-generated constructor stub
		this.mInflater = LayoutInflater.from(c);
		this.mList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_list_dskh_chuadh,
					parent, false);
		}
		TextView stt = (TextView) convertView.findViewById(R.id.tv_dskh_stt);
		TextView makh = (TextView) convertView.findViewById(R.id.tv_dskh_makh);
		TextView tenkh = (TextView) convertView
				.findViewById(R.id.tv_dskh_tenkh);
		TextView diachi = (TextView) convertView
				.findViewById(R.id.tv_dskh_diachi);
		TextView tuyen = (TextView) convertView
				.findViewById(R.id.tv_dskh_tuyen);
		TextView tansuat = (TextView) convertView
				.findViewById(R.id.tv_dskh_tansuat);
		TextView dsthang_gannhat = (TextView) convertView
				.findViewById(R.id.tv_dskh_dsthang_gannhat);
		TextView ngaydh_gannhat = (TextView) convertView
				.findViewById(R.id.tv_dskh_ngaydh_gannhat);

		stt.setText("" + (position + 1));
		makh.setText(mList.get(position).makh);
		tenkh.setText(cf.getUTF8StringFromNCR(mList.get(position).tenkh));
		diachi.setText(cf.getUTF8StringFromNCR(mList.get(position).diachi));
		tuyen.setText(mList.get(position).tentuyen);
		//cuongtm sua
		tansuat.setText(mList.get(position).tansuat);
		dsthang_gannhat.setText(mList.get(position).doanhso);
		ngaydh_gannhat.setText(mList.get(position).lastorder);
		//
		return convertView;
	}

}
