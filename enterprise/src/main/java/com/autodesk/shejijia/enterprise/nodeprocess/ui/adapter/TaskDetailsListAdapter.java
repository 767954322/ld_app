package com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.common.utils.ToastUtils;
import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.NodeDetailsActivity;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.viewholder.TaskListVH;

import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 *
 */
public class TaskDetailsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TaskListBean.TaskList.Plan.Task> taskIdLists ;
    private int resId;
    private Context mContext;

    public TaskDetailsListAdapter(List<TaskListBean.TaskList.Plan.Task> taskIdLists, int resId, Context mContext){
        this.taskIdLists = taskIdLists;
        this.resId = resId;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId,parent,false);
        return new TaskListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TaskListVH taskListVH = (TaskListVH)holder;

        initView(taskListVH,position);

        initEvents(taskListVH,position);
    }

    @Override
    public int getItemCount() {
        return taskIdLists.size();
    }

    private void initView(TaskListVH taskListVH,int position){
        // 当前任务节点名
        if (!TextUtils.isEmpty(taskIdLists.get(position).getName())) {
            taskListVH.mTaskName.setText(taskIdLists.get(position).getName());
        }

        // 当前任务节点的状态
        if (!TextUtils.isEmpty(taskIdLists.get(position).getStatus())) {
            String status = taskIdLists.get(position).getStatus();
            if (status.equalsIgnoreCase("open")) {
                taskListVH.mTaskStatus.setText("未开始");
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext,R.drawable.project_list_tv_blue_shape));
            }else if (status.equalsIgnoreCase("reserving")){
                taskListVH.mTaskStatus.setText("待预约");
            }else if (status.equalsIgnoreCase("inProgress")){
                taskListVH.mTaskStatus.setText("进行中");
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext,R.drawable.project_list_tv_blue_shape));
            }else if (status.equalsIgnoreCase("delayed")){
                taskListVH.mTaskStatus.setText("已延期");
            }else if (status.equalsIgnoreCase("qualified")){
                taskListVH.mTaskStatus.setText("合格");
            }else if (status.equalsIgnoreCase("unqualified")){
                taskListVH.mTaskStatus.setText("不合格");
            }else if (status.equalsIgnoreCase("resolved")){
                taskListVH.mTaskStatus.setText("验收通过");
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext,R.drawable.project_list_tv_lightblue_shape));
            }else if (status.equalsIgnoreCase("rejected")){
                taskListVH.mTaskStatus.setText("验收拒绝");
            }else if (status.equalsIgnoreCase("reinspection")){
                taskListVH.mTaskStatus.setText("强制复验");
                taskListVH.mTaskStatus.setBackground(ContextCompat.getDrawable(mContext,R.drawable.project_list_tv_orange_shape));
            }else if (status.equalsIgnoreCase("rectification")){
                taskListVH.mTaskStatus.setText("监督整改");
            }else if (status.equalsIgnoreCase("reinspecting")){
                taskListVH.mTaskStatus.setText("复验中");
            }else {
                taskListVH.mTaskStatus.setText(status);
            }
        }

        //当前任务节点的类型
        if (!TextUtils.isEmpty(taskIdLists.get(position).getCategory())){
            String category = taskIdLists.get(position).getCategory();
            if (category.equalsIgnoreCase("timeline")){ //开工交底
                Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.default_head);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable,null,null,null);
            }else if (category.equalsIgnoreCase("inspection")){ //验收类
                Drawable drawable= ContextCompat.getDrawable(mContext,R.mipmap.check_accept);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable,null,null,null);
            }else if (category.equalsIgnoreCase("construction")){ //施工类
                Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.construction);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable,null,null,null);
            }else if (category.equalsIgnoreCase("materialMeasuring")){ //主材测量
                Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.material);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable,null,null,null);
            }else if (category.equalsIgnoreCase("materialInstallation")){ //主材安装
                Drawable drawable = ContextCompat.getDrawable(mContext,R.mipmap.material);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                taskListVH.mTaskName.setCompoundDrawables(drawable,null,null,null);
            }
        }
    }

    private void initEvents(TaskListVH taskListVH,final int position){
        taskListVH.mTaskDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort((Activity) mContext,"node-details"+position);
                Intent intent = new Intent(mContext, NodeDetailsActivity.class);
                intent.putExtra("taskId",taskIdLists.get(position).getTask_id());
                mContext.startActivity(intent);
            }
        });
    }

}
