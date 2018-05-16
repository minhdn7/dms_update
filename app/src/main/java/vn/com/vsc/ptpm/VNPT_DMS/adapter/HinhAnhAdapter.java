package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.API_code;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.HinhAnh;
import vn.com.vsc.ptpm.VNPT_DMS.view.fragment.HinhAnhFragment;

public class HinhAnhAdapter extends BaseAdapter {
	private Context context;
	private HinhAnhFragment hinhAnhFragment;
	private List<HinhAnh> blItems;
	private LayoutInflater inflater;
	private String url_head = API_code.URL_API_ROOT;

	public HinhAnhAdapter(Context context, List<HinhAnh> blItems, HinhAnhFragment hinhAnhFragment) {
		this.context = context;
		this.blItems = blItems;
		this.hinhAnhFragment = hinhAnhFragment;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		HinhAnhAdapter.ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list_ha, parent,false);
			holder = new HinhAnhAdapter.ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (HinhAnhAdapter.ViewHolder) convertView.getTag();
		}
//		if (inflater == null)
//			inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		if (convertView == null) {
//
//			convertView = inflater.inflate(R.layout.item_list_ha, null);
//
//		}

//		TextView counter = (TextView) convertView.findViewById(R.id.counter);
//		TextView title = (TextView) convertView.findViewById(R.id.title);
//		TextView content = (TextView) convertView.findViewById(R.id.content);
//		ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
		HinhAnh m = blItems.get(position);
		holder.counter.setText(String.valueOf(position + 1));
		holder.title.setText(m.getName());
		holder.content.setText("Created-Date: " + m.getCreated_date()
				+ ", Created-by: " + m.getCreated_by() + ", file-size: "
				+ m.getFile_size());
		Picasso.with(context).load(url_head + m.getUrl()).resize(80, 80).into(holder.thumbnail);

		holder.btnDeleteImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hinhAnhFragment.deleteImage(Integer.valueOf(blItems.get(position).getId()));
			}
		});
		return convertView;
	}

	@Override
	public int getCount() {
		return blItems.size();
	}

	@Override
	public Object getItem(int location) {
		return blItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView counter, title, content;
		ImageView thumbnail, btnDeleteImage;

		public ViewHolder(View view) {

			counter = (TextView) view.findViewById(R.id.counter);
			title = (TextView) view.findViewById(R.id.title);
			content = (TextView) view.findViewById(R.id.content);
			thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
			btnDeleteImage = (ImageView) view.findViewById(R.id.btnDeleteImage);
		}


	}

	public void notifiDataChange(List<HinhAnh> list) {
		if (blItems != null) {
			blItems.clear();
		}
		blItems= list;
		notifyDataSetChanged();

	}
}
