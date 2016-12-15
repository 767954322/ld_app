package com.autodesk.shejijia.shared.components.message;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.message.entity.messageItemListEntity;
import com.autodesk.shejijia.shared.components.message.entity.DisplayMessageEntity;

import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */

public class ProjectMessageCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<messageItemListEntity> messageItemBeans;
    private boolean mIsUnread;
    private boolean mISOutstripLIMIT;
    private HistoricalRecordstListener mHistoricalRecordstListener;
    private int resId;

    public ProjectMessageCenterAdapter(HistoricalRecordstListener historicalRecordstListener, List<messageItemListEntity> messageItemBeans, boolean isUnread, int resId) {
        super();
        this.messageItemBeans = messageItemBeans;
        this.resId = resId;
        this.mIsUnread = isUnread;
        this.mHistoricalRecordstListener = historicalRecordstListener;
        this.mISOutstripLIMIT = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new ProjectMessageCenterVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectMessageCenterVH projectMessageCenterVHHolder = (ProjectMessageCenterVH) holder;
        initData(projectMessageCenterVHHolder, position);
        initListener(projectMessageCenterVHHolder);
    }

    @Override
    public int getItemCount() {
        return getContentItemCount();
    }

    public void notifyDataForRecyclerView(List<messageItemListEntity> messageItemBean, boolean isUnread, int offset) {
        mISOutstripLIMIT = messageItemBean.size() < ProjectMessageCenterPresenter.LIMIT ? true : false;
        if (offset == 0) {
            messageItemBeans.clear();
        }
        if (messageItemBean.size() > 0) {
            this.messageItemBeans.addAll(messageItemBean);
        }
        this.mIsUnread = isUnread;
        notifyDataSetChanged();
    }

    public int getContentItemCount() {
        return messageItemBeans == null ? 0 : messageItemBeans.size();
    }

    private void initListener(ProjectMessageCenterVH projectMessageCenterVHHolder) {
        projectMessageCenterVHHolder.mTvMessageHistoricalRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoricalRecordstListener.onHistoricalRecordstClick();
            }
        });
    }

    private void initData(ProjectMessageCenterVH holder, int position) {
        String avatar = messageItemBeans.get(position).getSenderAvatar();
        if (TextUtils.isEmpty(avatar)) {
            holder.mImgBtnPersonalHeadPic.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        } else {
            ImageUtils.loadImageRound(holder.mImgBtnPersonalHeadPic, avatar);
        }
        if (mIsUnread && mISOutstripLIMIT && (position == messageItemBeans.size() - 1)) {
            holder.mTvMessageHistoricalRecords.setVisibility(View.VISIBLE);
        } else {
            holder.mTvMessageHistoricalRecords.setVisibility(View.GONE);
        }

        DisplayMessageEntity displayMessage = messageItemBeans.get(position).getDisplayMessage();
        if (displayMessage != null && !TextUtils.isEmpty(displayMessage.getSummary())) {
            String title = displayMessage.getSummary();
            for (int i = 0; title.contains("*"); i++) {
                title = (i % 2 == 0) ? title.replaceFirst("\\*", "<b><tt>") : title.replaceFirst("\\*", "</b></tt>");
            }
            holder.mTvMeaasgeName.setText(Html.fromHtml(title));
        }
        List<String> detailItems = displayMessage.getDetailItems();
        if (detailItems != null) {
            String detailItem = "";
            for (String str : detailItems) {
                detailItem += str + "\n";
            }
            detailItem = detailItem.substring(0, detailItem.length() - 1);
            holder.mTvMeaasgeCantent.setText(detailItem);
        }
        if (!TextUtils.isEmpty(messageItemBeans.get(position).getSentTime())) {
            String sentTime = DateUtil.getTimeMYD(messageItemBeans.get(position).getSentTime());
            holder.mTvMessageTime.setText(sentTime);
        }
    }

    public static class ProjectMessageCenterVH extends RecyclerView.ViewHolder {
        private ImageView mImgBtnPersonalHeadPic;
        private TextView mTvMeaasgeName;
        private TextView mTvMeaasgeCantent;
        private TextView mTvMessageTime;
        private TextView mTvMessageHistoricalRecords;

        ProjectMessageCenterVH(View itemView) {
            super(itemView);
            mImgBtnPersonalHeadPic = (ImageView) itemView.findViewById(R.id.imgBtn_personal_headPic);
            mTvMeaasgeName = (TextView) itemView.findViewById(R.id.tv_meaasge_name);
            mTvMeaasgeCantent = (TextView) itemView.findViewById(R.id.tv_meaasge_cantent);
            mTvMessageTime = (TextView) itemView.findViewById(R.id.tv_message_time);
            mTvMessageHistoricalRecords = (TextView) itemView.findViewById(R.id.tv_message_historical_records);
        }
    }

    public interface HistoricalRecordstListener {
        void onHistoricalRecordstClick();
    }

}
