package com.autodesk.shejijia.enterprise.nodeprocess.projectlists.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;

/**
 * Created by t_xuz on 8/22/16.
 *
 */
public class ProjectListVH extends RecyclerView.ViewHolder{

    public LinearLayout mProjectItem;
    public LinearLayout mProjectDetails;

    public RecyclerView mTaskListView; //任务列表
    public TextView mProjectName; //项目名 project/name
    public TextView mProjectStatus; //项目状态 plan/status

    public View mViewLine; //分割线
    public Button mStarLabel; //星标按钮

    public ProjectListVH(View itemView) {
        super(itemView);

        mProjectItem = (LinearLayout)itemView.findViewById(R.id.lv_project_item);
        mProjectDetails = (LinearLayout)itemView.findViewById(R.id.lv_project_details);
        mTaskListView = (RecyclerView)itemView.findViewById(R.id.rcy_task_list);
        mProjectName = (TextView)itemView.findViewById(R.id.tv_project_name);
        mProjectStatus = (TextView)itemView.findViewById(R.id.tv_project_status);
        mViewLine = itemView.findViewById(R.id.view_taskItem_line);
        mStarLabel = (Button)itemView.findViewById(R.id.btn_star_label);
    }

}
