package com.autodesk.shejijia.enterprise.projectlists.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.nodedetails.entity.NodeBean;
import com.autodesk.shejijia.enterprise.projectlists.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.projectlists.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.projectlists.viewholder.ProjectListVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_xuz on 8/22/16.
 *
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TaskListBean.TaskList> taskLists;
    private int resId;
    private Context mContext;

    public ProjectListAdapter(List<TaskListBean.TaskList> taskLists, int resId, Context mContext) {
        this.resId = resId;
        this.mContext = mContext;
        this.taskLists = taskLists;
    }

    public void setTaskLists(List<TaskListBean.TaskList> taskLists) {
        this.taskLists = taskLists;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new ProjectListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectListVH projectVh = (ProjectListVH) holder;


        if (!TextUtils.isEmpty(taskLists.get(position).getName())) {
            projectVh.tv_project_name.setText(taskLists.get(position).getName());
        }
        if (!TextUtils.isEmpty(taskLists.get(position).getPlan().getStatus())) {
            String status = taskLists.get(position).getPlan().getStatus();
            if (status.equalsIgnoreCase("open")) {
                projectVh.tv_project_status.setText("开工交底");
            }else if (status.equalsIgnoreCase("ready")){
                projectVh.tv_project_status.setText("排期完毕");
            }else if (status.equalsIgnoreCase("inProgress")){
                projectVh.tv_project_status.setText("施工阶段");
            }else if (status.equalsIgnoreCase("completion")){
                projectVh.tv_project_status.setText("完成");
            }else {
                projectVh.tv_project_status.setText(status);
            }
        }

        if (taskLists != null && taskLists.size() > 0) {
            //设置任务列表里的数据
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setAutoMeasureEnabled(true);//加上这句可以让它自动去根据数据条数测量recyclerView 的高度.
            projectVh.ry_project_list.setHasFixedSize(true);
            projectVh.ry_project_list.setItemAnimator(new DefaultItemAnimator());
            projectVh.ry_project_list.setLayoutManager(layoutManager);

            projectVh.ry_project_list.setAdapter(new TaskListReAdapter(taskLists.get(position).getPlan().getTasks(), R.layout.project_list_item_details_view));
        }

    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }

}
