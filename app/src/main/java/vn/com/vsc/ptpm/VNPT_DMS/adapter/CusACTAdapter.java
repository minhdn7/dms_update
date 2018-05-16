package vn.com.vsc.ptpm.VNPT_DMS.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.SanPham;

public class CusACTAdapter extends ArrayAdapter<SanPham> implements Filterable {

    final String TAG = "AutocompleteCustomArrayAdapter.java";

    private Context mContext;
    private int layoutResourceId;
    private List<SanPham> items, tempItems, suggestions;
    private ConvertFont conv = new ConvertFont();
    private DataTransfer data;

    public CusACTAdapter(Context mContext, int layoutResourceId,
                         List<SanPham> items) {
        super(mContext, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.items = items;
    }

    public CusACTAdapter(Context mContext, int layoutResourceId, List<SanPham> items, DataTransfer data) {
        super(mContext, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.items = items;
        this.data = data;
    }

    @SuppressWarnings("static-access")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layoutResourceId, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.numb = (TextView) convertView.findViewById(R.id.numb);
            viewHolder.txt_item1 = (TextView) convertView.findViewById(R.id.textViewItem);
            viewHolder.item_SSP = (RelativeLayout) convertView.findViewById(R.id.item_SSP);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        final SanPham sp = items.get(position);
        if (sp != null) {
            viewHolder.txt_item1.setText(sp.getProduct_cat_id() + " - " + conv.getUTF8StringFromNCR(sp.getName()));
            viewHolder.numb.setText(String.valueOf(position + 1));
        }

        return convertView;
    }

    public interface DataTransfer {
        public void setValues(String pos);
    }

    static class ViewHolderItem {
        public TextView numb, txt_item1;
        public RelativeLayout item_SSP;
        // public LinearLayout item_list_dsdh;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SanPham getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            SanPham sp = (SanPham) resultValue;
            String str = sp.getProduct_cat_id() + " - " + conv.getUTF8StringFromNCR(sp.getName());
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (SanPham people : tempItems) {
                    if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())
                            || people.getProduct_cat_id().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(people);
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
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<SanPham> filterList = (ArrayList<SanPham>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (SanPham people : filterList) {
                    add(people);
                    notifyDataSetChanged();
                }
            }
        }
    };

//     @Override
//     public Filter getFilter() {
//     return nameFilter;
//     }

    // /**
    // * Custom Filter implementation for custom suggestions we provide.
    // */
//     Filter nameFilter = new Filter() {
//     @Override
//     public CharSequence convertResultToString(Object resultValue) {
//     String str = ((SanPham) resultValue).getName();
//     return str;
//     }
    //
    // @Override
    // protected FilterResults performFiltering(CharSequence constraint) {
    // if (constraint != null) {
    // suggestions.clear();
    // for (SanPham people : tempItems) {
    // if (people.getName().toLowerCase()
    // .contains(constraint.toString().toLowerCase())
    // || people
    // .getProduct_cat_id()
    // .toLowerCase()
    // .contains(
    // constraint.toString().toLowerCase())) {
    // suggestions.add(people);
    // }
    // }
    // FilterResults filterResults = new FilterResults();
    // filterResults.values = suggestions;
    // filterResults.count = suggestions.size();
    // return filterResults;
    // } else {
    // return new FilterResults();
    // }
    // }
    //
    // @Override
    // protected void publishResults(CharSequence constraint,
    // FilterResults results) {
    // List<SanPham> filterList = (ArrayList<SanPham>) results.values;
    // if (results != null && results.count > 0) {
    // clear();
    // for (SanPham people : filterList) {
    // add(people);
    // notifyDataSetChanged();
    // }
    // }
    // }
    // };
}
