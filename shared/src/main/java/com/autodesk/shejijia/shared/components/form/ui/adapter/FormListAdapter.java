package com.autodesk.shejijia.shared.components.form.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

import java.util.List;

/**
 * Created by t_aij on 16/11/22.
 */

public class FormListAdapter extends RecyclerView.Adapter<FormListAdapter.FormListVH> {
    private Context mContext;
    private List<String> mTitleList;
    private OnItemClickListener mOnItemClickListener;

    public FormListAdapter(Context context, List<String> titleList) {
        mContext = context;
        mTitleList = titleList;
    }

    @Override
    public FormListVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_plain_table_cell, parent, false);
        return new FormListVH(view);
    }

    @Override
    public void onBindViewHolder(final FormListVH holder, final int position) {
        String title = mTitleList.get(position);
        holder.mTitle.setText(title);

        if (null != mOnItemClickListener) {
            holder.mTableCellLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }

            });

            mOnItemClickListener.onResultClick(holder.mResultTv);  //将结果标签抛出处理
        }
    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    static class FormListVH extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mResultTv;
        private RelativeLayout mTableCellLayout;

        public FormListVH(View itemView) {
            super(itemView);
            mTableCellLayout = (RelativeLayout) itemView.findViewById(R.id.rl_table_cell);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mResultTv = (TextView) itemView.findViewById(R.id.tv_result);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onResultClick(TextView view);
    }

}
