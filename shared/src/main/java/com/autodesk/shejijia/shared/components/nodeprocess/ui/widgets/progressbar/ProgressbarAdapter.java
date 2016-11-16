package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.progressbar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

import java.util.List;

/**
 * Created by t_xuz on 11/15/16.
 */

public class ProgressbarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private int resId;
    private List<ProgressState> stateList;

    public ProgressbarAdapter(Context context, int resId, List<ProgressState> list) {
        this.mContext = context;
        this.resId = resId;
        this.stateList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        return new ProgressbarVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProgressbarVH progressbarVH = (ProgressbarVH) holder;

        initView(progressbarVH, position);

        initEvent(progressbarVH, position);
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    private void initView(ProgressbarVH progressbarVH, int position) {
        progressbarVH.mTaskStatus.setText(stateList.get(position).getName());
        String status = stateList.get(position).getStatus();
        switch (status) {
            case "in_process":
                progressbarVH.mStatusImage.setImageResource(R.drawable.ic_progressbar_inprocess);
                progressbarVH.mStatusImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_progressbar_center_select_shape));
                progressbarVH.mAroundCircle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_progressbar_around_select_shape));
                break;
            case "un_open":
                progressbarVH.mStatusImage.setImageResource(R.drawable.ic_progressbar_unopen);
                progressbarVH.mStatusImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_progressbar_white_shape));
                progressbarVH.mAroundCircle.setBackground(null);
                break;
            case "complete":
                progressbarVH.mStatusImage.setImageResource(R.drawable.ic_progressbar_complete);
                progressbarVH.mStatusImage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.project_progressbar_black_shape));
                progressbarVH.mAroundCircle.setBackground(null);
                break;
        }
    }

    private void initEvent(ProgressbarVH progressbarVH, int position) {

    }

    private static class ProgressbarVH extends RecyclerView.ViewHolder {
        private RelativeLayout mAroundCircle;
        private ImageView mStatusImage;
        private TextView mTaskStatus;

        ProgressbarVH(View itemView) {
            super(itemView);
            mAroundCircle = (RelativeLayout) itemView.findViewById(R.id.rl_around_circle);
            mStatusImage = (ImageView) itemView.findViewById(R.id.img_center_circle);
            mTaskStatus = (TextView) itemView.findViewById(R.id.tv_task_status);
        }
    }
}
