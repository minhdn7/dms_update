package vn.com.vsc.ptpm.VNPT_DMS.adapter;

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
import vn.com.vsc.ptpm.VNPT_DMS.model.response.tmx.NhanHieuResponse;

/**
 * Created by MinhDN on 6/10/2017.
 */

public class DanhSachNhanHieuAdapter extends ArrayAdapter<NhanHieuResponse> {
    private Context context;
    private int resource;
//    private List<NhanHieuResponse> objects;
    private ConvertFont conv = new ConvertFont();
    List<NhanHieuResponse> items, tempItems, suggestions;
    public DanhSachNhanHieuAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NhanHieuResponse> items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<NhanHieuResponse>(items); // this makes the difference.
        this.suggestions = new ArrayList<NhanHieuResponse>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        NhanHieuResponse nhanHieuResponse = items.get(position);
        if (nhanHieuResponse != null) {
            TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
            if (txtItemNhanHieu != null)
                txtItemNhanHieu.setText(nhanHieuResponse.getName());
        }
        return view;
    }


    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) { // This view starts when we click the

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);

        NhanHieuResponse nhanHieuResponse = items.get(position);
        if (nhanHieuResponse != null) {
            TextView txtItemNhanHieu = (TextView) view.findViewById(R.id.txtItemNhanHieu);
            if (txtItemNhanHieu != null)
                txtItemNhanHieu.setText(nhanHieuResponse.getName());
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
            String str = ((NhanHieuResponse) resultValue).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (NhanHieuResponse employee : tempItems) {
                    if(suggestions.size() > 5){
                        break;
                    }
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
            List<NhanHieuResponse> filterList = (ArrayList<NhanHieuResponse>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (NhanHieuResponse employee : filterList) {
                    add(employee);
                    notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public NhanHieuResponse getItem(int index) {

        return items.get(index);

    }
}
