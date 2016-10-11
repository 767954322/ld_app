package com.autodesk.shejijia.enterprise.nodeprocess.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.enterprise.R;

/**
 * Created by t_xuz on 8/23/16.
 *
 */
public class TaskListVH extends RecyclerView.ViewHolder{

    public TextView mTaskName;
    public TextView mTaskStatus;
    public LinearLayout mTaskDetails;

    public TaskListVH(View itemView) {
        super(itemView);

        mTaskName = (TextView) itemView.findViewById(R.id.tv_task_name);
        mTaskStatus = (TextView)itemView.findViewById(R.id.tv_task_status);
        mTaskDetails = (LinearLayout)itemView.findViewById(R.id.ll_task_details);
    }
}
