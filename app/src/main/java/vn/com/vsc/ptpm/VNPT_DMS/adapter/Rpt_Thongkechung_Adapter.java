package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.common.mAF;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Rpt_Thongkechung_Model;

public class Rpt_Thongkechung_Adapter extends BaseAdapter  {

	private Context context;
	private List<Rpt_Thongkechung_Model> kmItems;
	private LayoutInflater inflater;
	private ConvertFont conv = new ConvertFont();
	
	public Rpt_Thongkechung_Adapter(Context context, List<Rpt_Thongkechung_Model> kmItems) {
		this.context = context;
		this.kmItems = kmItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return kmItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return kmItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@SuppressWarnings("static-access")
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		String tieude;
		
		if (convertView == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_rpt_kpi_tkchung, null);
			holder = new ViewHolder();
			holder.txtSott = (TextView) convertView.findViewById(R.id.rpt_stt);
			holder.txtNoidung = (TextView) convertView.findViewById(R.id.rpt_noidung);
			holder.txtChitieu = (TextView) convertView.findViewById(R.id.rpt_chitieu);
			holder.txtThuchien = (TextView) convertView.findViewById(R.id.rpt_thuchien);
			holder.txtConlai = (TextView) convertView.findViewById(R.id.rpt_conlai);
			holder.txtTiendo = (TextView) convertView.findViewById(R.id.rpt_tiendo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Rpt_Thongkechung_Model m = kmItems.get(position);
		if (m != null) {
			
			holder.txtSott.setText(String.valueOf(position + 1));
			holder.txtNoidung.setText(conv.getUTF8StringFromNCR(m.getKpi_target_name()));
			holder.txtChitieu.setText(mAF.formatNumber("###,###.##", m.getKpi_target_value()));
			holder.txtThuchien.setText(mAF.formatNumber("###,###.##", m.getValue_current()));
			holder.txtConlai.setText(mAF.formatNumber("###,###.##", m.getConlai()));
			holder.txtTiendo.setText(mAF.formatNumber("###,###.##", m.getTiendo()));
			//holder.khuyenmai_title.setText(tieude);
			//holder.khuyenmai_content.setText(conv.getUTF8StringFromNCR(m.getDescription()).replaceAll("<br>", "\n"));
		}
		return convertView;
	}
	private class ViewHolder {
		TextView txtSott, txtNoidung, txtChitieu, txtThuchien, txtConlai, txtTiendo;
	}

}
