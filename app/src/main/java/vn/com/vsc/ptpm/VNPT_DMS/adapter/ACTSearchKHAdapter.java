package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.DSKHModel;

/**
 * Created by ThaoPit on 10/31/2016.
 */

public class ACTSearchKHAdapter extends ArrayAdapter<DSKHModel> implements Filterable {
    private static String TAG = ACTSearchKHAdapter.class.getSimpleName();
    private Context mContext;
    private int layoutResourceId;
    private List<DSKHModel> list_dskh;

    public ACTSearchKHAdapter(Context context, int resource, List<DSKHModel> list_dskh) {
        super(context, resource, list_dskh);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.list_dskh = list_dskh;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.numb = (TextView) convertView.findViewById(R.id.numb);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewItem);
            viewHolder.item_SSP = (RelativeLayout) convertView.findViewById(R.id.item_SSP);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DSKHModel kh = list_dskh.get(position);
        if (kh != null) {
            viewHolder.name.setText(kh.getId() + "_" + kh.getName());
            viewHolder.numb.setText(String.valueOf(position + 1));
        }
        return convertView;
    }


    static class ViewHolder {
        public TextView numb, name;
        public RelativeLayout item_SSP;
    }

}
