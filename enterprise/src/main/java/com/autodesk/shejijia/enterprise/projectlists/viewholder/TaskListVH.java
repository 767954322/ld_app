package com.autodesk.shejijia.enterprise.projectlists.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;

/**
 * Created by t_xuz on 8/23/16.
 *
 */
public class TaskListVH extends RecyclerView.ViewHolder{

    public TextView tv_task_name;
    public TextView tv_task_status;

    public TaskListVH(View itemView) {
        super(itemView);
        tv_task_name = (TextView) itemView.findViewById(R.id.tv_task_name);
        tv_task_status = (TextView)itemView.findViewById(R.id.tv_task_status);
    }
}
