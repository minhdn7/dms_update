package vn.com.vsc.ptpm.VNPT_DMS.adapter.tmx;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.control.ConvertFont;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.danhsachtuyen.DanhSachVungResponse;

/**
 * Created by MinhDN on 15/12/2017.
 */

public class KhuVucAdapter extends ArrayAdapter<DanhSachVungResponse> {
    private Context context;
    private int resource;
    private ConvertFont conv = new ConvertFont();
    List<DanhSachVungResponse> items, tempItems, suggestions;

    public KhuVucAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachVungResponse> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<DanhSachVungResponse>(items); // this makes the difference.
        this.suggestions = new ArrayList<DanhSachVungResponse>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        DanhSachVungResponse vungResponse = items.get(position);
        if (vungResponse != null) {
            TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
            if (txtItemNhanHieu != null)
                txtItemNhanHieu.setText(vungResponse.getName());
        }
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) { // This view starts when we click the

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        DanhSachVungResponse khachHangResponse = items.get(position);
        if (khachHangResponse != null) {
            TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
            if (txtItemNhanHieu != null)
                txtItemNhanHieu.setText(khachHangResponse.getName());
        }
        return view;

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
            String str = ((DanhSachVungResponse) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DanhSachVungResponse employee : tempItems) {
//                    if(suggestions.size() > 5){
//                        break;
//                    }
                    if (employee.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(employee);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                FilterResults filterResults = new FilterResults();
                filterResults.values = tempItems;
                filterResults.count = tempItems.size();
                return filterResults;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<DanhSachVungResponse> filterList = (ArrayList<DanhSachVungResponse>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DanhSachVungResponse employee : filterList) {
                    add(employee);
                    notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public DanhSachVungResponse getItem(int index) {

        return items.get(index);

    }
}
