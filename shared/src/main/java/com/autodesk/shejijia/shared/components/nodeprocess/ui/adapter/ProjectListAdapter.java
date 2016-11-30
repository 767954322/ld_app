package com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Like;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.Date;
import java.util.List;

/**
 * Created by t_xuz on 8/22/16.
 * 项目列表－adapter
 */
public class ProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ProjectInfo> mProjectLists;
    private int mResId;
    private Context mContext;
    private ProjectListItemListener mProjectListItemListener;

    public ProjectListAdapter(List<ProjectInfo> projectLists, int resId, Context context, ProjectListItemListener projectListItemListener) {
        this.mResId = resId;
        this.mContext = context;
        this.mProjectLists = projectLists;
        this.mProjectListItemListener = projectListItemListener;
    }

    public void setProjectLists(List<ProjectInfo> projectLists) {
        this.mProjectLists.clear();
        this.mProjectLists.addAll(projectLists);
        notifyDataSetChanged();
    }

    public void appendProjectLists(List<ProjectInfo> projectLists) {
        this.mProjectLists.addAll(projectLists);
        notifyDataSetChanged();
    }

    public void updateItemData(ProjectInfo projectInfo) {
        for (int index = 0; index < mProjectLists.size(); index++) {
            ProjectInfo tmpProjectInfo = mProjectLists.get(index);
            if (projectInfo.getProjectId() == tmpProjectInfo.getProjectId()) {
                mProjectLists.remove(index);
                mProjectLists.add(index, projectInfo);
                notifyItemChanged(index);
                break;
            }
        }
    }

    /*
    * 根据星标接口，获得星标更新成功后的结果，从而更新内存中的该项目对应的数据源
    * */
    public void updateProjectState(String filterLike, Like newLike, int likePosition) {
        LogUtils.e(newLike.getLike() + "  " + likePosition);
        List<Like> likeList = mProjectLists.get(likePosition).getLikes();
        for (Like oldLike : likeList) {
            if (oldLike.getUid().equals(newLike.getUid())) {
                likeList.remove(oldLike);
            }
        }
        likeList.add(newLike);
        if (filterLike.equalsIgnoreCase("true")) { //星标列表里，移除取消星标成功该位置的数据
            mProjectLists.remove(likePosition);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
        return new ProjectListVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectListVH projectVh = (ProjectListVH) holder;

        initView(projectVh, position);

        initEvents(projectVh, position);
    }

    @Override
    public int getItemCount() {
        return mProjectLists.size();
    }

    private void initView(ProjectListVH projectVh, int position) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            projectVh.mStarLabel.setZ(ScreenUtil.dip2px(6));
            projectVh.mCardView.setClipToOutline(false);
        }

        ProjectInfo projectInfo = mProjectLists.get(position);
        if (!TextUtils.isEmpty(projectInfo.getName())) {
            projectVh.mProjectName.setText(projectInfo.getName());
        }
        if (projectInfo.getPlan().getMilestone() != null) {
            if (!TextUtils.isEmpty(projectInfo.getPlan().getMilestone().getMilestoneName())) {
                String milestoneName = projectInfo.getPlan().getMilestone().getMilestoneName();
                if (projectInfo.isXDebug()) {
                    projectVh.mProjectStatus.setTextColor(ContextCompat.getColor(mContext, R.color.con_font_red));
                    String xDebugDateString = "UT: ";
                    if (projectInfo.getXDebugCurrentTime() == null) {
                        xDebugDateString += "null";
                    } else {
                        Date xDebugDate = DateUtil.iso8601ToDate(projectInfo.getXDebugCurrentTime());
                        xDebugDateString += DateUtil.getStringDateByFormat(xDebugDate, mContext.getString(R.string.date_format_month_day));
                    }
                    projectVh.mProjectStatus.setText(milestoneName + "(" + xDebugDateString + ")");
                } else {
                    projectVh.mProjectStatus.setTextColor(ContextCompat.getColor(mContext, R.color.font_gray));
                    projectVh.mProjectStatus.setText(milestoneName);
                }

            }
        }

        if (mProjectLists != null && mProjectLists.size() > 0) {
            //设置是否是星标项目
            if (isLikeProject(mProjectLists.get(position))) {
                projectVh.mStarLabel.setBackgroundResource(R.drawable.ic_project_like);
            } else {
                projectVh.mStarLabel.setBackgroundResource(R.drawable.ic_project_normal);
            }

            projectVh.mViewLine.setVisibility(View.VISIBLE);
            //设置任务列表里的数据
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            layoutManager.setAutoMeasureEnabled(true);//加上这句可以让它自动去根据数据条数测量recyclerView 的高度.
            projectVh.mTaskListView.setHasFixedSize(true);
            projectVh.mTaskListView.setItemAnimator(new DefaultItemAnimator());
            projectVh.mTaskListView.setLayoutManager(layoutManager);

            if (mProjectLists.get(position).getPlan().getTasks() != null && mProjectLists.get(position).getPlan().getTasks().size() > 0) {
                projectVh.mViewLine.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setVisibility(View.VISIBLE);
                projectVh.mTaskListView.setAdapter(new TaskListAdapter(mProjectLists.get(position), R.layout.listitem_task_list_details_view, mContext, mProjectListItemListener));
            } else {//隐藏分割线,与recyclerView
                projectVh.mViewLine.setVisibility(View.GONE);
                projectVh.mTaskListView.setVisibility(View.GONE);
            }
        }
    }

    private void initEvents(ProjectListVH projectVh, final int position) {
        projectVh.mProjectDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProjectListItemListener.onProjectClick(mProjectLists, position);
            }
        });

        projectVh.mStarLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectListItemListener.onStarLabelClick(mProjectLists, !isLikeProject(mProjectLists.get(position)), position);
            }
        });
    }

    private boolean isLikeProject(ProjectInfo project) {
        List<Like> likeList = project.getLikes();
        if (likeList != null) {
            for (Like like : likeList) {
                if (like.getUid().equals(UserInfoUtils.getUid(AdskApplication.getInstance())) && like.getLike()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static class ProjectListVH extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private LinearLayout mProjectItem;
        private LinearLayout mProjectDetails;

        private RecyclerView mTaskListView; //任务列表
        private TextView mProjectName; //项目名 project/name
        private TextView mProjectStatus; //项目状态 plan/status

        private View mViewLine; //分割线
        private Button mStarLabel; //星标按钮

        ProjectListVH(View itemView) {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.cdv_task_list);
            mProjectItem = (LinearLayout) itemView.findViewById(R.id.lv_project_item);
            mProjectDetails = (LinearLayout) itemView.findViewById(R.id.lv_project_details);
            mTaskListView = (RecyclerView) itemView.findViewById(R.id.rcy_task_list);
            mProjectName = (TextView) itemView.findViewById(R.id.tv_project_name);
            mProjectStatus = (TextView) itemView.findViewById(R.id.tv_project_status);
            mViewLine = itemView.findViewById(R.id.view_taskItem_line);
            mStarLabel = (Button) itemView.findViewById(R.id.btn_star_label);
        }
    }


    public interface ProjectListItemListener {
        //项目详情
        void onProjectClick(List<ProjectInfo> projectList, int position);

        //节点详情
        void onTaskClick(ProjectInfo projectInfo, Task task);

        //星标按钮监听
        void onStarLabelClick(List<ProjectInfo> projectList, boolean like, int position);
    }
}
