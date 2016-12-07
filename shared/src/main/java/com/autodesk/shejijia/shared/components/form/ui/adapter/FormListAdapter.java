package com.autodesk.shejijia.shared.components.form.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.form.common.entity.ItemCell;

import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormListVH> {
    private List<ItemCell> mItemCells;
    private OnItemClickListener mOnItemClickListener;

    public FormListAdapter(List<ItemCell> titleList, OnItemClickListener onItemClickListener) {
        mItemCells = titleList;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public FormListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_plain_table_cell, parent, false);
        return new FormListVH(view);
    }

    @Override
    public void onBindViewHolder(final FormListVH holder, final int position) {
        ItemCell itemCell = mItemCells.get(position);
        holder.mTitleTv.setText(itemCell.getTitle());  //标题

        initResult(holder, itemCell);                  //验收结果

        initReinspectionNum(holder, itemCell);         //提醒复检数目

        initInformationShow(holder, itemCell);         //是否含有错误信息

        initListener(holder, position);                 //设置监听

    }

    @Override
    public int getItemCount() {
        return mItemCells.size();
    }

    private void initResult(FormListVH holder, ItemCell itemCell) {
        if (!TextUtils.isEmpty(itemCell.getResult())) {
            holder.mResultTv.setText(Html.fromHtml(itemCell.getResult()));
        } else {
            holder.mResultTv.setText("");
        }
    }

    private void initReinspectionNum(FormListVH holder, ItemCell itemCell) {
        if (itemCell.getReinspectionNum() == 0) {
            holder.mNotificationTv.setVisibility(View.GONE);
        } else {
            holder.mNotificationTv.setVisibility(View.VISIBLE);
            holder.mNotificationTv.setText(String.valueOf(itemCell.getReinspectionNum()));
        }
    }

    private void initInformationShow(FormListVH holder, ItemCell itemCell) {
        if (itemCell.isShow()) {
            holder.mInformationIv.setVisibility(View.VISIBLE);
        } else {
            holder.mInformationIv.setVisibility(View.GONE);
        }
    }

    private void initListener(FormListVH holder, final int position) {
        if (mOnItemClickListener != null) {
            holder.mTableCellLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }

            });

        }
    }

    static class FormListVH extends RecyclerView.ViewHolder {
        private TextView mTitleTv;  //标题
        private TextView mResultTv; //随时跟新结果  初始化的时候就应该做出判断,是否有复验
        private RelativeLayout mTableCellLayout;  //条目点击
        private TextView mNotificationTv;   //通知标签   初始化的时候就应该做出判断,是复验的通知,和数量
        private ImageView mInformationIv;    //错误详情视图   初始化的时候就应该做出判断,是否又错误信息

        public FormListVH(View itemView) {
            super(itemView);
            mTableCellLayout = (RelativeLayout) itemView.findViewById(R.id.rl_table_cell);
            mTitleTv = (TextView) itemView.findViewById(R.id.tv_title);
            mResultTv = (TextView) itemView.findViewById(R.id.tv_result);
            mNotificationTv = (TextView) itemView.findViewById(R.id.tv_notifications);
            mInformationIv = (ImageView) itemView.findViewById(R.id.iv_information);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
