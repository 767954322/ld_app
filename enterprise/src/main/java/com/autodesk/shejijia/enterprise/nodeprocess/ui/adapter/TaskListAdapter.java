package com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.ProjectDetailsActivity;
import com.autodesk.shejijia.enterprise.nodeprocess.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.viewholder.ProjectListVH;

import java.util.List;

/**
 * Created by t_xuz on 8/22/16.
 *
 */
public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TaskListBean.TaskList> taskLists;
    private int resId;
    private Context mContext;

    public TaskListAdapter(List<TaskListBean.TaskList> taskLists, int resId, Context mContext) {
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

        initView(projectVh,position);

        initEvents(projectVh,position);
    }

    @Override
    public int getItemCount() {
        return taskLists.size();
    }

    private void initView(ProjectListVH projectVh,int position){

        if (!TextUtils.isEmpty(taskLists.get(position).getName())) {
            projectVh.mProjectName.setText(taskLists.get(position).getName());
        }
        if (!TextUtils.isEmpty(taskLists.get(position).getPlan().getStatus())) {
            String status = taskLists.get(position).getPlan().getStatus();
            if (status.equalsIgnoreCase("open")) {
                projectVh.mProjectStatus.setText("开工交底");
            }else if (status.equalsIgnoreCase("ready")){
                projectVh.mProjectStatus.setText("排期完毕");
            }else if (status.equalsIgnoreCase("inProgress")){
                projectVh.mProjectStatus.setText("施工阶段");
            }else if (status.equalsIgnoreCase("completion")){
                projectVh.mProjectStatus.setText("完成");
            }else {
                projectVh.mProjectStatus.setText(status);
            }
        }

        if (taskLists != null && taskLists.size() > 0) {
            projectVh.mViewLine.setVisibility(View.VISIBLE);
            //设置任务列表里的数据
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setAutoMeasureEnabled(true);//加上这句可以让它自动去根据数据条数测量recyclerView 的高度.
            projectVh.mTaskListView.setHasFixedSize(true);
            projectVh.mTaskListView.setItemAnimator(new DefaultItemAnimator());
            projectVh.mTaskListView.setLayoutManager(layoutManager);

            if (taskLists.get(position).getPlan().getTasks()!=null && taskLists.get(position).getPlan().getTasks().size()>0){
                projectVh.mViewLine.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setAdapter(new TaskDetailsListAdapter(taskLists.get(position).getPlan().getTasks(), R.layout.listitem_task_list_details_view,mContext));
            }else {//隐藏分割线,与recyclerView
                projectVh.mViewLine.setVisibility(View.GONE);
                projectVh.mTaskListView.setVisibility(View.GONE);
            }
        }
    }

    private void initEvents(ProjectListVH projectVh,final int position) {
        projectVh.mProjectDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long projectId = taskLists.get(position).getProject_id();
                Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
                intent.putExtra("projectId",projectId);
                mContext.startActivity(intent);
            }
        });
    }

}
