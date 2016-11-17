package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.CalendarDay;
import com.autodesk.shejijia.shared.components.common.uielements.calanderview.MaterialCalendarView;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.EditTaskNodeAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenhulin on 11/17/16.
 */

public class AddTaskDialogFragment extends BottomSheetDialogFragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = LayoutInflater.from(getActivity()).inflate(R.layout.layout_add_task_dialog, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        setupHeader(view);
        setupListView();
        return view;
    }

    private void setupHeader(View parentView) {
        TextView taskNameView = (TextView) parentView.findViewById(R.id.tv_task_name);
        taskNameView.setText("选择新增节点");

        TextView actionBtn = (TextView) parentView.findViewById(R.id.tv_action);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO callback
                dismiss();
            }
        });
    }

    private void setupListView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<TaskViewHolder>() {

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view  = LayoutInflater.from(getContext()).inflate(R.layout.item_add_task, parent, false);
                return new TaskViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TaskViewHolder holder, int position) {
                holder.mTvNodeName.setText("节点" + position);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
    }

    private static class TaskViewHolder extends RecyclerView.ViewHolder {
        final TextView mTvNodeName;

        TaskViewHolder(View itemView) {
            super(itemView);
            mTvNodeName = (TextView) itemView.findViewById(R.id.tv_task_name);
        }
    }

}
