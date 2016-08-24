package com.autodesk.shejijia.enterprise.projectlists.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.base.common.utils.LogUtils;
import com.autodesk.shejijia.enterprise.projectlists.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.projectlists.viewholder.TaskListVH;

import java.util.List;

/**
 * Created by t_xuz on 8/23/16.
 *
 */
public class TaskListReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TaskListBean.TaskList.Plan.Task> taskIdLists ;
    private int resId;

    public TaskListReAdapter(List<TaskListBean.TaskList.Plan.Task> taskIdLists, int resId){
        this.taskIdLists = taskIdLists;
        this.resId = resId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId,parent,false);
        return new TaskListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TaskListVH taskListVH = (TaskListVH)holder;
        LogUtils.e("taskId--name",taskIdLists.get(position).getName());
        if (!TextUtils.isEmpty(taskIdLists.get(position).getName())) {
            taskListVH.tv_task_name.setText(taskIdLists.get(position).getName());
        }

        if (!TextUtils.isEmpty(taskIdLists.get(position).getStatus())) {
            String status = taskIdLists.get(position).getStatus();
            if (status.equalsIgnoreCase("open")) {
                taskListVH.tv_task_status.setText("未开始");
            }else if (status.equalsIgnoreCase("reserving")){
                taskListVH.tv_task_status.setText("待预约");
            }else if (status.equalsIgnoreCase("inProgress")){
                taskListVH.tv_task_status.setText("进行中");
            }else if (status.equalsIgnoreCase("delayed")){
                taskListVH.tv_task_status.setText("已延期");
            }else if (status.equalsIgnoreCase("qualified")){
                taskListVH.tv_task_status.setText("合格");
            }else if (status.equalsIgnoreCase("unqualified")){
                taskListVH.tv_task_status.setText("不合格");
            }else if (status.equalsIgnoreCase("resolved")){
                taskListVH.tv_task_status.setText("验收拒绝");
            }else if (status.equalsIgnoreCase("reinspection")){
                taskListVH.tv_task_status.setText("强制复验");
            }else if (status.equalsIgnoreCase("rectification")){
                taskListVH.tv_task_status.setText("监督整改");
            }else if (status.equalsIgnoreCase("reinspecting")){
                taskListVH.tv_task_status.setText("复验中");
            }else {
                taskListVH.tv_task_status.setText(status);
            }
        }
    }

    @Override
    public int getItemCount() {
        return taskIdLists.size();
    }

}
