package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;
import android.os.Handler;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.TaskStatusTypeEnum;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.EditPlanContract;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @author wenhulin
 * @since 11/3/16
 */

public class EditTaskNodePresenter implements EditPlanContract.TaskNodePresenter {
    private final static String LOG_TAG_EDIT_PLAN = "edit_plan";

    private EditPlanContract.TaskNodeView mView;

    private String mProjectId;
    private PlanInfo mPlan;
    private Task mActiveTask;
    private ArrayList<Task> mDeletedTasks = new ArrayList<>();

    private TaskFilterType mFilterType = TaskFilterType.ALL_TASKS;

    public EditTaskNodePresenter(String projectId) {
        mProjectId = projectId;
    }

    @Override
    public void fetchPlan() {
        ProjectRepository.getInstance().getEditingPlan(mProjectId, ConstructionConstants.REQUEST_TAG_FETCH_PLAN,
                new ResponseCallback<PlanInfo>() {
                    @Override
                    public void onSuccess(PlanInfo data) {
                        mPlan = data;
                        mDeletedTasks.clear();
                        mDeletedTasks.addAll(mPlan.getDeletedTasks());
                        sortTasks();
                        showTasks(mPlan.getTasks());
                        updateAddIcon();
                    }

                    @Override
                    public void onError(String errorMsg) {
                        mView.showError(errorMsg);
                    }
                });
    }

    @Override
    public void bindView(EditPlanContract.TaskNodeView view) {
        mView = view;
        mActiveTask = null;
    }

    @Override
    public void editTaskNode(Task task) {
        mActiveTask = task;
        mView.showBottomSheet(getMileStoneNodes(), task);
    }

    @Override
    public void onFilterTypeChange(TaskFilterType filterType) {
        mFilterType = filterType;
        mView.updateFilterIcon(getFilterIcon());
        showTasks(mPlan.getTasks());
    }

    @Override
    public void updateTask(List<Date> selectedDates) {
        if (selectedDates.size() < 1) {
            return;
        }

        Collections.sort(selectedDates, new Comparator<Date>() {
            @Override
            public int compare(Date lhs, Date rhs) {
                return DateUtil.compareDate(lhs, rhs);
            }
        });

        Date startDate = selectedDates.get(0);
        Date endDate;
        if (selectedDates.size() == 1) {
            endDate = DateUtil.getDateEndTime(startDate);
        } else {
            endDate = selectedDates.get(selectedDates.size() - 1);
        }

        String startDateString = DateUtil.dateToIso8601(startDate);
        String endDateString = DateUtil.dateToIso8601(endDate);
        updateTaskDate(mActiveTask, startDateString, endDateString);
        sortTasks();
        showTasks(mPlan.getTasks());
    }

    @Override
    public void deleteTasks(List<Task> tasks) {
        List<Task> allTasks = mPlan.getTasks();
        for (Task task : tasks) {
            mDeletedTasks.add(task);
            task.setStatus(TaskStatusTypeEnum.TASK_STATUS_DELETED.getTaskStatus());
        }
        showTasks(allTasks);
        updateAddIcon();
    }

    @Override
    public void addTask(Task toAddTask) {
        toAddTask.setStatus(TaskStatusTypeEnum.TASK_STATUS_OPEN.getTaskStatus());

        for (Task deletedTask : mDeletedTasks) {
            if (deletedTask.getTaskId().equalsIgnoreCase(toAddTask.getTaskId())) {
                mDeletedTasks.remove(deletedTask);
                break;
            }
        }


        List<Task> allTasks = mPlan.getTasks();
        for (Task task : allTasks) {
            if (task.getTaskId().equalsIgnoreCase(toAddTask.getTaskId())) {
                task.setStatus(TaskStatusTypeEnum.TASK_STATUS_OPEN.getTaskStatus().toUpperCase(Locale.getDefault()));
                toAddTask = task;
                break;
            }
        }

        mFilterType = TaskFilterType.ALL_TASKS;
        mView.updateFilterIcon(getFilterIcon());
        updateAddIcon();
        sortTasks();


        List<TaskFilter> includeTaskFilters = new ArrayList<>();
        List<TaskFilter> excludeTaskFilters = new ArrayList<>();

        includeTaskFilters.add(new CategoryFilter(mFilterType));
        excludeTaskFilters.add(new StatusFilter(TaskStatusTypeEnum.TASK_STATUS_DELETED));

        final List<Task> filteredTasks = filterTasks(mPlan.getTasks(), includeTaskFilters, excludeTaskFilters);

        if (mView.scrollToPosition(filteredTasks.indexOf(toAddTask) - 1)) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mView.showTasks(filteredTasks);
                }
            });
        } else {
            mView.showTasks(filteredTasks);
        }
    }

    @Override
    public void startAddTask() {
        mView.showAddTaskDialog(mDeletedTasks);
    }

    @Override
    public void commitPlan() {
        Bundle requestParams = new Bundle();
        requestParams.putString("operation", "edit"); // TODO Get operation
        requestParams.putSerializable("body", mPlan);
        mView.showUpLoading();
        ProjectRepository.getInstance().updatePlan(mProjectId, requestParams, ConstructionConstants.REQUEST_TAG_UPDATE_PLAN, new ResponseCallback<Project>() {
            @Override
            public void onSuccess(Project data) {
                LogUtils.d(LOG_TAG_EDIT_PLAN, "update plan success ");
                mView.hideUpLoading();
                mView.onCommitSuccess();
            }

            @Override
            public void onError(String errorMsg) {
                LogUtils.e(LOG_TAG_EDIT_PLAN, "update plan fail " + errorMsg);
                mView.hideUpLoading();
                mView.onCommitError("Update plan fail!\n " + errorMsg); // TODO update string
            }
        });

    }

    @Override
    public boolean shouldShowAddIcon() {
        return mDeletedTasks.size() > 0;
    }

    private void showTasks(List<Task> tasks) {
        List<TaskFilter> includeTaskFilters = new ArrayList<>();
        List<TaskFilter> excludeTaskFilters = new ArrayList<>();

        includeTaskFilters.add(new CategoryFilter(mFilterType));
        excludeTaskFilters.add(new StatusFilter(TaskStatusTypeEnum.TASK_STATUS_DELETED));

        List<Task> filteredTasks = filterTasks(tasks, includeTaskFilters, excludeTaskFilters);
        mView.showTasks(filteredTasks);
    }

    private List<Task> filterTasks(List<Task> tasks, List<TaskFilter> includeTaskFilters, List<TaskFilter> excludeTaskFilters) {
        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {
            boolean filterResult = true;
            for (TaskFilter filter : includeTaskFilters) {
                filterResult &= filter.filter(task);
            }

            for (TaskFilter filter : excludeTaskFilters) {
                filterResult &= !filter.filter(task);
            }

            if (filterResult) {
                filteredTasks.add(task);
            }
        }

        return filteredTasks;
    }

    interface TaskFilter {
        boolean filter(Task task);
    }

    private static class StatusFilter implements TaskFilter {
        private TaskStatusTypeEnum mFilterType;

        StatusFilter(TaskStatusTypeEnum filterType) {
            mFilterType = filterType;
        }

        @Override
        public boolean filter(Task task) {
            return task.getStatus().equalsIgnoreCase(mFilterType.getTaskStatus());
        }
    }

    private static class CategoryFilter implements TaskFilter {
        private TaskFilterType mFilterType;

        CategoryFilter(TaskFilterType filterType) {
            mFilterType = filterType;
        }

        @Override
        public boolean filter(Task task) {
            switch (mFilterType) {
                case CONSTRUCTION_TASKS:
                    return task.getCategory().equalsIgnoreCase(ConstructionConstants.TaskCategory.CONSTRUCTION);
                case MATERIAL_TASKS:
                    return task.getCategory().equalsIgnoreCase(ConstructionConstants.TaskCategory.MATERIAL_MEASURING)
                            || task.getCategory().equalsIgnoreCase(ConstructionConstants.TaskCategory.MATERIAL_INSTALLATION);
                default:
                    return true;
            }
        }
    }

    private int getFilterIcon() {
        int filterIcon;
        switch (mFilterType) {
            case CONSTRUCTION_TASKS:
            case MATERIAL_TASKS:
                filterIcon = R.drawable.ic_menu_filtered;
                break;
            default:
                filterIcon = R.drawable.ic_menu_filter;
                break;
        }
        return filterIcon;
    }

    private void updateAddIcon() {
        mView.showAddIcon(shouldShowAddIcon());
    }

    private List<Task> getMileStoneNodes() {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : mPlan.getTasks()) {
            if (task.isMilestone()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private void updateTaskDate(Task task, String startDate, String endDate) {
        task.getPlanningTime().setStart(startDate);
        task.getPlanningTime().setCompletion(endDate);
    }

    private void sortTasks() {
        List<Task> tasks = mPlan.getTasks();
        Collections.sort(tasks, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return DateUtil.compareDate(lhs.getPlanningTime().getStart(), rhs.getPlanningTime().getStart());
            }
        });
    }
}
