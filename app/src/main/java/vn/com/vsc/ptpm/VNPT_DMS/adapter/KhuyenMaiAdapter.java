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
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.KhuyenMaiModel;

//cuongtm thêm
public class KhuyenMaiAdapter extends BaseAdapter {
	
	private Context context;
	private List<KhuyenMaiModel> kmItems;
	private LayoutInflater inflater;
	private ConvertFont conv = new ConvertFont();
	
	
	public KhuyenMaiAdapter(Context context, List<KhuyenMaiModel> kmItems) {
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
			convertView = inflater.inflate(R.layout.item_list_khuyenmai, null);
			holder = new ViewHolder();
			holder.khuyenmai_counter = (TextView) convertView.findViewById(R.id.khuyenmai_counter);
			holder.khuyenmai_title = (TextView) convertView.findViewById(R.id.khuyenmai_title);
			holder.khuyenmai_content = (TextView) convertView.findViewById(R.id.khuyenmai_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		KhuyenMaiModel m = kmItems.get(position);
		if (m != null) {
			holder.khuyenmai_counter.setText(String.valueOf(position + 1));
			tieude = m.getCode() + "/" +  conv.getUTF8StringFromNCR(m.getName());
			if (m.getStartDate().length() >= 4)
			{
				tieude = tieude + " [Từ: " + m.getStartDate();
			}
			if (m.getEndDate().length() >= 4)
			{
				tieude = tieude + " đến: " + m.getEndDate() + "]";
			}else
			{
				tieude = tieude + "]";
			}
			
			holder.khuyenmai_title.setText(tieude);
			holder.khuyenmai_content.setText(conv.getUTF8StringFromNCR(m.getDescription()).replaceAll("<br>", "\n"));
		}
		return convertView;
	}
	
	private class ViewHolder {
		TextView khuyenmai_counter, khuyenmai_title, khuyenmai_content;
	}
	
}
