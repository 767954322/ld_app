package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getActivity(),
                R.style.BottomSheetDialogTheme_Calendar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO disable hideable if necessary
//        View bottomSheet = getDialog().findViewById(android.support.design.R.id.design_bottom_sheet);
//        BottomSheetBehavior.from(bottomSheet).setHideable(false);
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
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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
