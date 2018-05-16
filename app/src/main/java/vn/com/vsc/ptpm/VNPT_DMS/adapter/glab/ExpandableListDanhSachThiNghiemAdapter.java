package vn.com.vsc.ptpm.VNPT_DMS.adapter.glab;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.com.vsc.ptpm.VNPT_DMS.R;
import vn.com.vsc.ptpm.VNPT_DMS.model.model.GroupListModel;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachNhomXetNghiemResponse;
import vn.com.vsc.ptpm.VNPT_DMS.model.response.glab.DanhSachXetNghiemResponse;

/**
 * Created by MinhDN on 23/11/2017.
 */

public class ExpandableListDanhSachThiNghiemAdapter extends BaseExpandableListAdapter {
//    ArrayList<Boolean> positionArray;
    HashMap<DanhSachNhomXetNghiemResponse, ArrayList<Boolean>> positionArray = new HashMap<>();
    private List<CheckBox> listCheckBox = new ArrayList<>();
    private Context _context;
    private List<DanhSachNhomXetNghiemResponse> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<DanhSachNhomXetNghiemResponse, List<DanhSachXetNghiemResponse>> _listDataChild;
    public List<DanhSachXetNghiemResponse> danhSachXetNghiemSelect = new ArrayList<>();

    public ExpandableListDanhSachThiNghiemAdapter(Context context, List<DanhSachNhomXetNghiemResponse> listDataHeader,
                                 HashMap<DanhSachNhomXetNghiemResponse, List<DanhSachXetNghiemResponse>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        for(int i = 0; i < listDataHeader.size(); i++){
            ArrayList<Boolean> arrChild = new ArrayList<>();
            try{
                for(int j = 0; j < listChildData.get(listDataHeader.get(i)).size(); j++){
                    arrChild.add(false);
                }
            }catch (Exception ex){

            }
            positionArray.put(listDataHeader.get(i), arrChild);

        }


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon).getName();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        long id = Long.parseLong(this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).getProductCatId());
        return id;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
//        final String childText = (String) getChild(groupPosition, childPosition);
        final String childText = _listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).getName();
        final DanhSachXetNghiemResponse item = _listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_expandable_lisview, parent, false);
//            holder = new Holder();
//            holder.txtListChild = (TextView) convertView.findViewById(R.id.txtListItem);
//            holder.ckSelectItem = (CheckBox) convertView.findViewById(R.id.ckSelectItem);
        }else {
//            holder = (Holder) convertView.getTag();
//            holder.ckSelectItem.setOnCheckedChangeListener(null);
        }
        final Holder holder = new Holder();
        holder.txtListChild = (TextView) convertView.findViewById(R.id.txtListItem);
        holder.ckSelectItem = (CheckBox) convertView.findViewById(R.id.ckSelectItem);
        holder.ckSelectItem.setFocusable(false);
        holder.ckSelectItem.setChecked(positionArray.get(this._listDataHeader.get(groupPosition)).get(childPosition));

//        if (listData.get(position).isselected) {
//            holder.ckSelectItem.setChecked(true);
//        } else {
//            holder.ckSelectItem.setChecked(false);
//        }
//        TextView txtListChild = (TextView) convertView.findViewById(R.id.txtListItem);
//        final CheckBox ckSelectItem = (CheckBox) convertView.findViewById(R.id.ckSelectItem);

//        holder.ckSelectItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    positionArray.get(_listDataHeader.get(groupPosition)).set(childPosition, true);
//                    if(danhSachXetNghiemSelect.size() > 0){
//                        if(!danhSachXetNghiemSelect.contains(item)){
//                            danhSachXetNghiemSelect.add(item);
//                        }
//                    }else {
//                        danhSachXetNghiemSelect.add(item);
//                    }
////                    positionArray.get(this._listDataHeader.get(groupPosition)).set(true);
//                }else {
//                    positionArray.get(_listDataHeader.get(groupPosition)).set(childPosition, false);
//                    if(danhSachXetNghiemSelect.size() > 0){
//                        if(danhSachXetNghiemSelect.contains(item)){
//                            danhSachXetNghiemSelect.remove(item);
//                        }
//                    }
//                }
//            }
//        });
        holder.ckSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.ckSelectItem.isChecked()){
                    positionArray.get(_listDataHeader.get(groupPosition)).set(childPosition, true);
                    if(danhSachXetNghiemSelect.size() > 0){
                        if(!danhSachXetNghiemSelect.contains(item)){
                            danhSachXetNghiemSelect.add(item);
                        }
                    }else {
                        danhSachXetNghiemSelect.add(item);
                    }
                }else {
                    positionArray.get(_listDataHeader.get(groupPosition)).set(childPosition, false);
                    if(danhSachXetNghiemSelect.size() > 0){
                        if(danhSachXetNghiemSelect.contains(item)){
                            danhSachXetNghiemSelect.remove(item);
                        }
                    }
                }
            }
        });

        holder.txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) _listDataHeader.get(groupPosition).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.hearder_expandable_lisview, parent, false);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txtListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }

    static class Holder {
        TextView txtListChild;
        CheckBox ckSelectItem;

    }
}
