package com.autodesk.shejijia.shared.components.form.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.form.common.entity.OptionCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by t_aij on 16/12/1.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListVH> {
    private Context mContext;
    private List<OptionCell> mOptionCellList = new ArrayList<>();

    public ItemListAdapter(Context context,List<OptionCell> optionCellList) {
        mContext = context;
        mOptionCellList.addAll(optionCellList);
    }

    @Override
    public ItemListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_option_table_cell, parent, false);
        return new ItemListVH(view);
    }

    @Override
    public void onBindViewHolder(ItemListVH holder, int position) {
        OptionCell optionCell = mOptionCellList.get(position);
// TODO: 16/12/2 对显示的条件判断
        holder.mTitleTv.setText(optionCell.getTitle());

        holder.mStandardTv.setText(optionCell.getStandard());
        holder.mActionTypeTv.setText("该项目属于" + optionCell.getActionType() + "项");

        holder.mInformationTv.setVisibility(View.GONE);

        HashMap<String, String[]> typeDictMap = optionCell.getTypeDict();
        if(typeDictMap.containsKey("acceptance_options")) {
            String[] acceptanceOptionses = typeDictMap.get("acceptance_options");
            holder.mLeftLayout.setVisibility(View.VISIBLE);
            holder.mLeftImgv.setBackgroundResource(R.drawable.option_none_bg_selector);
            holder.mLeftTv.setText(acceptanceOptionses[2]);
            holder.mCenterImgv.setBackgroundResource(R.drawable.option_unqualified_bg_selector);
            holder.mCenterTv.setText(acceptanceOptionses[1]);
            holder.mRightImgv.setBackgroundResource(R.drawable.option_qualified_bg_selector);
            holder.mRightTv.setText(acceptanceOptionses[0]);


        } else if(typeDictMap.containsKey("level_options")) {
            String[] levelOptionses = typeDictMap.get("level_options");
            holder.mLeftLayout.setVisibility(View.GONE);

            holder.mCenterTv.setText(levelOptionses[1]);
            holder.mRightTv.setText(levelOptionses[0]);


        } else if(typeDictMap.containsKey("notification_options")) {
            String[] notificationOptionses = typeDictMap.get("notification_options");

            holder.mLeftLayout.setVisibility(View.GONE);

            holder.mCenterTv.setText(notificationOptionses[1]);
            holder.mRightTv.setText(notificationOptionses[0]);

        }

    }

    @Override
    public int getItemCount() {
        return mOptionCellList.size();
    }


    static class ItemListVH extends RecyclerView.ViewHolder {

        private TextView mTitleTv;
        private TextView mStandardTv;
        private TextView mActionTypeTv;
        private LinearLayout mLeftLayout;   //左边选项的布局
        private ImageButton mLeftImgv;
        private TextView mLeftTv;
        private ImageButton mCenterImgv;
        private TextView mCenterTv;
        private ImageButton mRightImgv;
        private TextView mRightTv;
        private View mLineView;            //分隔线
        private TextView mInformationTv;  //显示错误信息的表示

        public ItemListVH(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_title);
            mStandardTv = (TextView) itemView.findViewById(R.id.tv_standard);
            mActionTypeTv = (TextView) itemView.findViewById(R.id.tv_action_type);

            mLeftLayout = (LinearLayout) itemView.findViewById(R.id.ll_left);
            mLineView = itemView.findViewById(R.id.view_line);
            mInformationTv = (TextView) itemView.findViewById(R.id.tv_information);

            mLeftImgv = (ImageButton) itemView.findViewById(R.id.iv_left);
            mLeftTv = (TextView) itemView.findViewById(R.id.tv_left);

            mCenterImgv = (ImageButton) itemView.findViewById(R.id.iv_center);
            mCenterTv = (TextView) itemView.findViewById(R.id.tv_center);

            mRightImgv = (ImageButton) itemView.findViewById(R.id.iv_right);
            mRightTv = (TextView) itemView.findViewById(R.id.tv_right);
        }
    }
}
