package com.autodesk.shejijia.enterprise.projectlists.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;

/**
 * Created by t_xuz on 8/22/16.
 *
 */
public class ProjectListVH extends RecyclerView.ViewHolder{

    public RecyclerView ry_project_list; //任务列表
    public TextView tv_project_name; //项目名 project/name
    public TextView tv_project_status; //项目状态 plan/status

    public ProjectListVH(View itemView) {
        super(itemView);

        ry_project_list = (RecyclerView)itemView.findViewById(R.id.ry_project_list);
        tv_project_name = (TextView)itemView.findViewById(R.id.tv_project_name);
        tv_project_status = (TextView)itemView.findViewById(R.id.tv_project_status);
    }

}
