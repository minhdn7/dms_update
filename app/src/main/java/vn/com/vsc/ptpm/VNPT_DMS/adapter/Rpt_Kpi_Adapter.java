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
import vn.com.vsc.ptpm.VNPT_DMS.model.model.Rpt_Kpi_Model;

public class Rpt_Kpi_Adapter extends BaseAdapter {
	private Context context;
	private List<Rpt_Kpi_Model> kmItems;
	private LayoutInflater inflater;
	private ConvertFont conv = new ConvertFont();

	public Rpt_Kpi_Adapter(Context context, List<Rpt_Kpi_Model> kmItems) {
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
			holder.txtTT = (TextView) convertView.findViewById(R.id.rpt_stt);
			holder.txtNoiDung = (TextView) convertView.findViewById(R.id.rpt_noidung);
			holder.txtKeHoach = (TextView) convertView.findViewById(R.id.rpt_chitieu);
			holder.txtThucTe = (TextView) convertView.findViewById(R.id.rpt_thuchien);
			holder.txtConLai = (TextView) convertView.findViewById(R.id.rpt_conlai);
			holder.txtTienDo = (TextView) convertView.findViewById(R.id.rpt_tiendo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Rpt_Kpi_Model model = kmItems.get(position);
		if (model != null) {

			holder.txtTT.setText(String.valueOf(position + 1));
			holder.txtNoiDung.setText(conv.getUTF8StringFromNCR(model.getKpi_target_name()));

			holder.txtThucTe.setText(mAF.formatNumber("###,###.##", model.getThuc_te()));

			holder.txtKeHoach.setText(mAF.formatNumber("###,###.##", model.getKe_hoach()));

			holder.txtConLai.setText(mAF.formatNumber("###,###.##", model.getCon_lai()));

			holder.txtTienDo.setText(mAF.formatNumber("###,###.##", model.getTien_do()));
		}
		return convertView;
	}

	private class ViewHolder {
		TextView txtTT, txtNoiDung, txtKeHoach, txtThucTe, txtConLai, txtTienDo;
	}

}
