package com.autodesk.shejijia.enterprise.personalcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.model.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.ui.activity.ProjectDetailsActivity;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;

import java.util.List;

/**
 * Created by t_xuz on 10/13/16.
 * 我页--项目列表adapter
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TaskListBean.TaskList> projectLists;
    private int resId;
    private Context mContext;

    public ProjectListAdapter(List<TaskListBean.TaskList> projectLists, int resId, Context mContext) {
        this.resId = resId;
        this.mContext = mContext;
        this.projectLists = projectLists;
    }

    public void setProjectLists(List<TaskListBean.TaskList> projectLists) {
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

        ProjectListVH projectListVH = (ProjectListVH) holder;

        initView(projectListVH, position);

        initEvents(projectListVH, position);

    }

    @Override
    public int getItemCount() {
        return projectLists.size();
    }

    private void initView(ProjectListVH projectVh, int position) {

        if (!TextUtils.isEmpty(projectLists.get(position).getName())) {
            projectVh.mProjectName.setText(projectLists.get(position).getName());
        }
        if (!TextUtils.isEmpty(projectLists.get(position).getPlan().getStatus())) {
            String status = projectLists.get(position).getPlan().getStatus();
            LogUtils.e("status",status);
            if (status.equalsIgnoreCase("open")) {
                projectVh.mProjectStatus.setText("开工交底");
            } else if (status.equalsIgnoreCase("ready")) {
                projectVh.mProjectStatus.setText("排期完毕");
            } else if (status.equalsIgnoreCase(Constants.PROJECT_STATUS_INPROGRESS)) {
                projectVh.mProjectStatus.setText("施工阶段");
            } else if (status.equalsIgnoreCase(Constants.PROJECT_STATUS_COMPLETE)) {
                projectVh.mProjectStatus.setText("完成");
            } else {
                projectVh.mProjectStatus.setText(status);
            }
        }
    }

    private void initEvents(ProjectListVH projectVh, final int position) {
        projectVh.mProjectDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long projectId = projectLists.get(position).getProject_id();
                Intent intent = new Intent(mContext, ProjectDetailsActivity.class);
                intent.putExtra("projectId", projectId);
                mContext.startActivity(intent);
            }
        });
    }

    static final class ProjectListVH extends RecyclerView.ViewHolder {
        private Button mStarLabel; //星标按钮
        private TextView mProjectName; //项目名 project/name
        private TextView mProjectStatus; //项目状态 plan/status
        private LinearLayout mProjectDetails;

        public ProjectListVH(View itemView) {
            super(itemView);
            mProjectName = (TextView) itemView.findViewById(R.id.tv_project_name);
            mProjectStatus = (TextView) itemView.findViewById(R.id.tv_project_status);
            mStarLabel = (Button) itemView.findViewById(R.id.btn_star_label);
            mProjectDetails = (LinearLayout) itemView.findViewById(R.id.lv_project_details);
        }
    }
}
