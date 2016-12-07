package com.autodesk.shejijia.shared.components.message.adapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
            ImageUtils.loadUserAvatar(holder.mImgBtnPersonalHeadPic, avatar);
        }
        if (!TextUtils.isEmpty(mData.get(position).getSender_role())) {
            holder.mTvMeaasgeName.setText(mData.get(position).getSender_role());
        }
//        if (!TextUtils.isEmpty(mData.get(position).getName())) {
//            mPMCVH.mTvMeaasgeCantent.setText(mData.get(position).getName());
//        }
        if (!TextUtils.isEmpty(mData.get(position).getSent_time())) {
            holder.mTvMessageTime.setText(mData.get(position).getSent_time());
        }
    }
     public class ProjectMessageCenterVH extends RecyclerView.ViewHolder {
        private ImageButton mImgBtnPersonalHeadPic;
        private TextView  mTvMeaasgeName;
        private TextView  mTvMeaasgeCantent;
        private TextView  mTvMessageTime;

        ProjectMessageCenterVH(View itemView) {
            super(itemView);
            mImgBtnPersonalHeadPic = (ImageButton) itemView.findViewById(R.id.cdv_task_list);
            mTvMeaasgeName = (TextView) itemView.findViewById(R.id.tv_meaasge_name);
            mTvMeaasgeCantent = (TextView) itemView.findViewById(R.id.tv_meaasge_cantent);
            mTvMessageTime = (TextView) itemView.findViewById(R.id.tv_message_time);
        }
    }

}
