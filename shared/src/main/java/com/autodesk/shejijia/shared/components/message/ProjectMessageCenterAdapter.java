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
import com.autodesk.shejijia.shared.components.message.entity.MessageItemBean;
import com.autodesk.shejijia.shared.components.message.entity.DisplayMessageBean;

import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */

public class ProjectMessageCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<MessageItemBean> messageItemBeans;
    private boolean mIsUnread;
    private  HistoricalRecordstListener mHistoricalRecordstListener;
    private int mBottomCount=1;//底部View个数
    private  final int ITEMTYPECONTENT = 0;
    private  final int ITEMTYPEBOTTOM = 1;
    private int resId;
    public ProjectMessageCenterAdapter(HistoricalRecordstListener historicalRecordstListener,List<MessageItemBean> messageItemBeans, boolean isUnread, int resId) {
        super();
        this.messageItemBeans = messageItemBeans;
        this.resId = resId;
        this.mIsUnread = isUnread;
        this.mHistoricalRecordstListener = historicalRecordstListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEMTYPECONTENT){
            View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
            return new ProjectMessageCenterVH(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foot_text, parent, false);
            return new BottomViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProjectMessageCenterAdapter.ProjectMessageCenterVH) {
            initData((ProjectMessageCenterVH)holder,position);
        } else{
            BottomViewHolder mBottomViewHolder = (BottomViewHolder)holder;
            String mHistoricalRecords = UIUtils.getString(R.string.historical_records);
            mBottomViewHolder.mTvMessageHistoricalRecords.setText(Html.fromHtml("<u>"+mHistoricalRecords+"</u>"));
            initListener(mBottomViewHolder);
            mBottomViewHolder.mTvMessageHistoricalRecords.setVisibility(mIsUnread?View.VISIBLE:View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return getContentItemCount()+mBottomCount;
    }
    @Override
    public int getItemViewType(int position){
        int dataItemCount = getContentItemCount();
        if (mBottomCount != 0 && position >= dataItemCount){
            return ITEMTYPEBOTTOM;
        }else{
            return ITEMTYPECONTENT;
        }
    }
    public void notifyDataForRecyclerView(List<MessageItemBean> messageItemBean,boolean isUnread,int offset) {
        if(offset == 0){
            messageItemBeans.clear();
        }
        this.messageItemBeans.addAll(messageItemBean);
        this.mIsUnread = isUnread;
        notifyDataSetChanged();
    }
    public int getContentItemCount(){
        return messageItemBeans == null?0:messageItemBeans.size();
    }
    private void initListener(BottomViewHolder mBottomViewHolder){
        mBottomViewHolder.mTvMessageHistoricalRecords.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mHistoricalRecordstListener.onHistoricalRecordstClick();
            }
        });
    }
    private void initData(ProjectMessageCenterVH holder,int position){
        String avatar = messageItemBeans.get(position).getSenderAvatar();
        if (TextUtils.isEmpty(avatar)) {
            holder.mImgBtnPersonalHeadPic.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        } else {
            ImageUtils.loadImageRound(holder.mImgBtnPersonalHeadPic, avatar);
        }

        DisplayMessageBean displayMessage = messageItemBeans.get(position).getDisplayMessage();
        if (displayMessage != null && !TextUtils.isEmpty(displayMessage.getSummary())) {
            String title = displayMessage.getSummary();
            for (int i = 0; title.contains("*"); i++) {
                title = (i % 2 == 0) ? title.replaceFirst("\\*", "<b><tt>") : title.replaceFirst("\\*", "</b></tt>");
            }
            holder.mTvMeaasgeName.setText(Html.fromHtml(title));
        }
        List<String> detailItems = displayMessage.getDetailItems();
        if (detailItems != null ) {
            String detailItem="";
            for(String str:detailItems){
                detailItem += str+"\n";
            }
            detailItem = detailItem.substring(0,detailItem.length()-1);
            holder.mTvMeaasgeCantent.setText(detailItem);
        }
        if (!TextUtils.isEmpty(messageItemBeans.get(position).getSentTime())) {
            String sentTime = DateUtil.getTimeMYD(messageItemBeans.get(position).getSentTime());
            holder.mTvMessageTime.setText(sentTime);
        }
    }//底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        private TextView  mTvMessageHistoricalRecords;

        public BottomViewHolder(View itemView) {
            super(itemView);
            mTvMessageHistoricalRecords = (TextView) itemView.findViewById(R.id.tv_message_historical_records);
        }
    }

     public static class ProjectMessageCenterVH extends RecyclerView.ViewHolder {
        private ImageView mImgBtnPersonalHeadPic;
        private TextView  mTvMeaasgeName;
        private TextView  mTvMeaasgeCantent;
        private TextView  mTvMessageTime;

        ProjectMessageCenterVH(View itemView) {
            super(itemView);
            mImgBtnPersonalHeadPic = (ImageView) itemView.findViewById(R.id.imgBtn_personal_headPic);
            mTvMeaasgeName = (TextView) itemView.findViewById(R.id.tv_meaasge_name);
            mTvMeaasgeCantent = (TextView) itemView.findViewById(R.id.tv_meaasge_cantent);
            mTvMessageTime = (TextView) itemView.findViewById(R.id.tv_message_time);
        }
    }
    public interface HistoricalRecordstListener {
        void onHistoricalRecordstClick();
    }

}
