package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.app.Activity;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.uielements.PinnedHeaderExpandableListView;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author malidong .
 * @version 1.0 .
 * @date 16-6-7 上午11:16
 * @file PinnedHeaderExpandableAdapter.java  .
 * @brief 附用历史量房订单适配器.
 */
public class PinnedHeaderExpandableAdapter extends BaseExpandableListAdapter implements PinnedHeaderExpandableListView.HeaderAdapter {

    private ArrayList<DecorationNeedsListBean> mList;
    private Activity activity;
    private PinnedHeaderExpandableListView listView;
    private LayoutInflater inflater;
    private String living_room, room, toilet;
    private Map<String, String> livingRoomJson;
    private Map<String, String> roomJson;
    private Map<String, String> toiletJson;
    private String living_room_convert, room_convert, toilet_convert;


    public PinnedHeaderExpandableAdapter(ArrayList<DecorationNeedsListBean> mList, Activity activity, PinnedHeaderExpandableListView listView) {
        this.mList = mList;
        this.activity = activity;
        this.listView = listView;
        inflater = LayoutInflater.from(this.activity);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createChildrenView();
        }
        /// 室厅卫 .
        room = mList.get(groupPosition).getRoom();
        living_room = mList.get(groupPosition).getLiving_room();
        toilet = mList.get(groupPosition).getToilet();

        roomJson = AppJsonFileReader.getRoomHall(activity);
        toiletJson = AppJsonFileReader.getToilet(activity);
        livingRoomJson = AppJsonFileReader.getLivingRoom(activity);

        room_convert = ConvertUtils.getConvert2CN(roomJson, room);
        living_room_convert = ConvertUtils.getConvert2CN(livingRoomJson, living_room);
        toilet_convert = ConvertUtils.getConvert2CN(toiletJson, toilet);

        TextView tv_name = (TextView) view.findViewById(R.id.tv_child_name);
        TextView tv_phone = (TextView) view.findViewById(R.id.tv_child_phone);
        TextView tv_house_type = (TextView) view.findViewById(R.id.tv_child_house_type);
        TextView tv_house_area = (TextView) view.findViewById(R.id.tv_child_house_area);
        TextView tv_village_name = (TextView) view.findViewById(R.id.tv_child_village_name);
        TextView tv_project_budget = (TextView) view.findViewById(R.id.tv_child_project_budget);
        TextView tv_planning_budget = (TextView) view.findViewById(R.id.tv_child_planning_budget);
        TextView tv_measure_house_address = (TextView) view.findViewById(R.id.tv_child_measure_house_address);

        tv_name.setText(mList.get(groupPosition).getContacts_name());
        tv_phone.setText(mList.get(groupPosition).getContacts_mobile());
        tv_house_type.setText(room_convert + " " + living_room_convert + " " + toilet_convert);
        tv_house_area.setText(mList.get(groupPosition).getHouse_area() + "m²");
        tv_project_budget.setText(mList.get(groupPosition).getDecoration_budget());
        tv_planning_budget.setText(mList.get(groupPosition).getDesign_budget());
        tv_measure_house_address.setText(mList.get(groupPosition).getProvince_name() + " " + mList.get(groupPosition).getCity_name() + " " + mList.get(groupPosition).getDistrict_name());
        tv_village_name.setText(mList.get(groupPosition).getCommunity_name());
        return view;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView != null) {
            view = convertView;
        } else {
            view = createGroupView();
        }

        ImageView iv = (ImageView) view.findViewById(R.id.groupIcon);

        if (isExpanded) {
            iv.setImageResource(R.drawable.have_measure_form_select_yes);
        } else {
            iv.setImageResource(R.drawable.have_measure_form_select_no);
        }

        TextView text = (TextView) view.findViewById(R.id.groupto);
        text.setText(mList.get(groupPosition).getCommunity_name());
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View createChildrenView() {
        return inflater.inflate(R.layout.item_measuemeal_child, null);
    }

    private View createGroupView() {
        return inflater.inflate(R.layout.view_expand_group, null);
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        ((TextView) header.findViewById(R.id.groupto)).setText(mList.get(groupPosition).getCommunity_name());
    }

    private SparseIntArray groupStatusMap = new SparseIntArray();

    @Override
    public void setGroupClickStatus(int groupPosition, int status) {
        groupStatusMap.put(groupPosition, status);
    }

    @Override
    public int getGroupClickStatus(int groupPosition) {
        if (groupStatusMap.keyAt(groupPosition) >= 0) {
            return groupStatusMap.get(groupPosition);
        } else {
            return 0;
        }
    }
}
