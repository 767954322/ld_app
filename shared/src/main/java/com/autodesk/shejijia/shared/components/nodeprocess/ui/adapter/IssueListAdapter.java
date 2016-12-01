package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;

import java.util.List;

/**
 * Created by Menghao.Gu on 2016/12/1.
 */

public class IssueListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] mIssueListData;
    private Context mContext;
    private int resId;
    private IssueListItemListener mIssueListItemListener;

    public IssueListAdapter(String[] mIssueListData, Context mContext, int resId, IssueListItemListener mIssueListItemListener) {
        this.mIssueListData = mIssueListData;
        this.mContext = mContext;
        this.resId = resId;
        this.mIssueListItemListener = mIssueListItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new IssueListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        IssueListVH issueListVh = (IssueListVH) holder;

        initView(issueListVh, position);

        initEvents(issueListVh, position);

    }

    private void initView(IssueListVH issueListVh, int position) {

        issueListVh.mIssueItemTitle.setText(mIssueListData[position]);

    }

    private void initEvents(IssueListVH issueListVh, final int position) {

        issueListVh.mIssueItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIssueListItemListener.onIssueListClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mIssueListData.length;
    }

    private static class IssueListVH extends RecyclerView.ViewHolder {
        private ImageButton mIssueItemIcon; //Item 图标
        private TextView mIssueItemTitle; //Item 标题
        private TextView mIssueItemNumber; //Item 数量
        private RelativeLayout mIssueItemLayout; //Item布局

        IssueListVH(View itemView) {
            super(itemView);
            mIssueItemIcon = (ImageButton) itemView.findViewById(R.id.ibn_issuelist_item_icon);
            mIssueItemTitle = (TextView) itemView.findViewById(R.id.tv_issuelist_item_title);
            mIssueItemNumber = (TextView) itemView.findViewById(R.id.tv_issuelist_item_number);
            mIssueItemLayout = (RelativeLayout) itemView.findViewById(R.id.rl_issuelist_item);
        }
    }


    public interface IssueListItemListener {

        void onIssueListClick(int item_position);

    }

}
