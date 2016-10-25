package com.autodesk.shejijia.enterprise.nodeprocess.ui.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.viewholder.ProjectListVH;

import java.util.List;

/**
 * Created by t_xuz on 8/22/16.
 *
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<ProjectInfo> projectLists;
    private int resId;
    private Context mContext;
    private ProjectListItemListener mProjectListItemListener;

    public ProjectListAdapter(List<ProjectInfo> projectLists, int resId, Context mContext, ProjectListItemListener projectListItemListener) {
        this.resId = resId;
        this.mContext = mContext;
        this.projectLists = projectLists;
        this.mProjectListItemListener = projectListItemListener;
    }

    public void setProjectLists(List<ProjectInfo> projectLists) {
        this.projectLists = projectLists;
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
        return projectLists.size();
    }

    private void initView(ProjectListVH projectVh,int position){

        if (!TextUtils.isEmpty(projectLists.get(position).getName())) {
            projectVh.mProjectName.setText(projectLists.get(position).getName());
        }
        if (!TextUtils.isEmpty(projectLists.get(position).getPlan().getStatus())) {
            String status = projectLists.get(position).getPlan().getStatus();
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

        if (projectLists != null && projectLists.size() > 0) {
            projectVh.mViewLine.setVisibility(View.VISIBLE);
            //设置任务列表里的数据
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setAutoMeasureEnabled(true);//加上这句可以让它自动去根据数据条数测量recyclerView 的高度.
            projectVh.mTaskListView.setHasFixedSize(true);
            projectVh.mTaskListView.setItemAnimator(new DefaultItemAnimator());
            projectVh.mTaskListView.setLayoutManager(layoutManager);

            if (projectLists.get(position).getPlan().getTasks()!=null && projectLists.get(position).getPlan().getTasks().size()>0){
                projectVh.mViewLine.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setAdapter(new TaskListAdapter(projectLists.get(position).getPlan().getTasks(), R.layout.listitem_task_list_details_view,mContext,mProjectListItemListener));
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
                mProjectListItemListener.onProjectClick(projectLists,position);
            }
        });
    }


    public interface ProjectListItemListener{
        //项目详情
        void onProjectClick(List<ProjectInfo> projectList, int position);
        //节点详情
        void onTaskClick(List<Task> taskList, int position);
    }
}
