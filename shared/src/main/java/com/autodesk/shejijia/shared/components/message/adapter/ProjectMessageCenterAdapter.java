package com.autodesk.shejijia.shared.components.message.adapter;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;

import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */

public class ProjectMessageCenterAdapter extends RecyclerView.Adapter<ProjectMessageCenterAdapter.ProjectMessageCenterVH>{
    private List<MessageInfo.DataBean> mData;
    private int resId;
    public ProjectMessageCenterAdapter(List<MessageInfo.DataBean> mData,int resId) {
        super();
        this.mData = mData;
        this.resId = resId;
    }

    @Override
    public ProjectMessageCenterVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new ProjectMessageCenterVH(view);
    }

    @Override
    public void onBindViewHolder(ProjectMessageCenterVH holder, int position) {
        initData(holder,position);
    }

    @Override
    public int getItemCount() {
        return mData != null?mData.size():0;
    }
    private void initData(ProjectMessageCenterVH holder,int position){
        String avatar = mData.get(position).getSender_avatar();
        if (TextUtils.isEmpty(avatar)) {
            holder.mImgBtnPersonalHeadPic.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_default_avator));
        } else {
            ImageUtils.loadImageRound(holder.mImgBtnPersonalHeadPic, avatar);
        }

        MessageInfo.DataBean.DisplayMessageBean display_message = mData.get(position).getDisplay_message();
        if (display_message != null && !TextUtils.isEmpty(display_message.getSummary())) {
            String title = display_message.getSummary();
            for (int i = 0; title.contains("*"); i++) {
                title = (i % 2 == 0) ? title.replaceFirst("\\*", "<b><tt>") : title.replaceFirst("\\*", "</b></tt>");
            }
            holder.mTvMeaasgeName.setText(Html.fromHtml(title));
        }
        List<String> detail_items = display_message.getDetail_items();
        if (detail_items != null ) {
            String detail_item="";
            for(String str:detail_items){
                detail_item += "\n"+str;
            }
            detail_item = detail_item.substring(2);
            holder.mTvMeaasgeCantent.setText(detail_item);
        }
        if (!TextUtils.isEmpty(mData.get(position).getSent_time())) {
            holder.mTvMessageTime.setText(mData.get(position).getSent_time());
        }
    }
     public class ProjectMessageCenterVH extends RecyclerView.ViewHolder {
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

}
