package com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenhulin on 11/17/16.
 */

public class AddTaskDialogFragment extends BottomSheetDialogFragment {

    private RecyclerView mRecyclerView;

    private ArrayList<Task> mTasks = new ArrayList<>();

    public static AddTaskDialogFragment newInstance(ArrayList<Task> tasks) {
        AddTaskDialogFragment fragment = new AddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("tasks", tasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTasks.clear();
        mTasks.addAll((ArrayList<Task>) getArguments().getSerializable("tasks"));
    }

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
    }

    private void setupHeader(View parentView) {
        TextView actionBtn = (TextView) parentView.findViewById(R.id.tv_action);
        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getTargetFragment() != null) {
                    getTargetFragment().onActivityResult(getTargetRequestCode(),
                            Activity.RESULT_CANCELED, getActivity().getIntent());
                }

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
                        Task task = (Task) v.getTag();
                        Intent intent = new Intent();
                        intent.putExtra("task", task);
                        getTargetFragment().onActivityResult(getTargetRequestCode(),
                                Activity.RESULT_OK, intent);
                        dismiss();
                    }
                });
                return new TaskViewHolder(view);
            }

            @Override
            public void onBindViewHolder(TaskViewHolder holder, int position) {
                Task task = getItem(position);
                holder.mTvNodeName.setText(task.getName());
                holder.itemView.setTag(task);
            }

            @Override
            public int getItemCount() {
                return mTasks.size();
            }

            private Task getItem(int position) {
                return mTasks.get(position);
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
