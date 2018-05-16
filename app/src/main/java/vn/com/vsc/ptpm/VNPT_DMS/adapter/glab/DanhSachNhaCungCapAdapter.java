package vn.com.vsc.ptpm.VNPT_DMS.adapter.glab;

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
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachNhaCungCapResponse;

/**
 * Created by MinhDN on 24/11/2017.
 */

public class DanhSachNhaCungCapAdapter extends ArrayAdapter<DanhSachNhaCungCapResponse> {
    private Context context;
    private int resource;
    private ConvertFont conv = new ConvertFont();
    List<DanhSachNhaCungCapResponse> items, tempItems, suggestions;

    public DanhSachNhaCungCapAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhSachNhaCungCapResponse> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<DanhSachNhaCungCapResponse>(items); // this makes the difference.
        this.suggestions = new ArrayList<DanhSachNhaCungCapResponse>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        DanhSachNhaCungCapResponse khachHangResponse = items.get(position);
        if (khachHangResponse != null) {
            TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
            if (txtItemNhanHieu != null)
                txtItemNhanHieu.setText(khachHangResponse.getName());
        }
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) { // This view starts when we click the

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        DanhSachNhaCungCapResponse khachHangResponse = items.get(position);
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
            String str = ((DanhSachNhaCungCapResponse) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (DanhSachNhaCungCapResponse employee : tempItems) {
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
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<DanhSachNhaCungCapResponse> filterList = (ArrayList<DanhSachNhaCungCapResponse>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (DanhSachNhaCungCapResponse employee : filterList) {
                    add(employee);
                    notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public DanhSachNhaCungCapResponse getItem(int index) {

        return items.get(index);

    }
}
