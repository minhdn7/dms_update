package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.NameKHDisplay;

/**
 * Created by ThaoPit on 12/22/2016.
 */

public class NameKHAdapter extends ArrayAdapter<NameKHDisplay> {
    Context context;
    int textViewResourceId;
    List<NameKHDisplay> items = new ArrayList<>(), tempItems, suggestions;

    public NameKHAdapter(Context context, int textViewResourceId, List<NameKHDisplay> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<NameKHDisplay>(items);
        suggestions = new ArrayList<NameKHDisplay>();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.row_name_kh, parent, false);
        }
        NameKHDisplay nameKHDisplay = items.get(position);
        if (nameKHDisplay != null) {
            TextView lblName = (TextView) v.findViewById(R.id.lbl_name);
            lblName.setText(nameKHDisplay.getCode() + "_" + nameKHDisplay.getName());
        }
        return v;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return super.convertResultToString(((NameKHDisplay) resultValue).getCode() + "_" + ((NameKHDisplay) resultValue).getName());
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (NameKHDisplay nameKHDisplay : tempItems) {
                    if (nameKHDisplay.getName().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        suggestions.add(nameKHDisplay);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            List<NameKHDisplay> filterList = (ArrayList<NameKHDisplay>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (NameKHDisplay nameKHDisplay : filterList) {
                    add(nameKHDisplay);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
