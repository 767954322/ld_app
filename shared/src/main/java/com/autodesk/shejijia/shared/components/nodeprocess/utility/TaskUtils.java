package com.autodesk.shejijia.shared.components.nodeprocess.utility;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Confirm;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.PlanInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.SubTask;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Time;
import com.autodesk.shejijia.shared.components.common.uielements.PickDateDialogFragment;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.ActiveMileStoneDecorator;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneDayFormatter;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.widgets.calendar.MileStoneNodeDecorator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/11/29
 */

public class TaskUtils {

    public static int getDisplayStatus(String status) {
        int displayStringId;
        switch (status.toLowerCase()) {
            case ConstructionConstants.TaskStatus.OPEN:
                displayStringId = R.string.task_open;
                break;
            case ConstructionConstants.TaskStatus.RESERVED:
                displayStringId = R.string.task_reserved;
                break;
            case ConstructionConstants.TaskStatus.RESERVING:
                displayStringId = R.string.task_reserving;
                break;
            case ConstructionConstants.TaskStatus.INPROGRESS:
                displayStringId = R.string.task_inProgress;
                break;
            case ConstructionConstants.TaskStatus.DELAYED:
                displayStringId = R.string.task_delayed;
                break;
            case ConstructionConstants.TaskStatus.QUALIFIED:
                displayStringId = R.string.task_qualified;
                break;
            case ConstructionConstants.TaskStatus.UNQUALIFIED:
                displayStringId = R.string.task_unqualified;
                break;
            case ConstructionConstants.TaskStatus.RESOLVED:
                displayStringId = R.string.task_resolved;
                break;
            case ConstructionConstants.TaskStatus.REJECTED:
                displayStringId = R.string.task_rejected;
                break;
            case ConstructionConstants.TaskStatus.REINSPECTION:
                displayStringId = R.string.task_reinspection;
                break;
            case ConstructionConstants.TaskStatus.RECTIFICATION:
                displayStringId = R.string.task_rectification;
                break;
            case ConstructionConstants.TaskStatus.REINSPECTING:
                displayStringId = R.string.task_reinspecting;
                break;
            case ConstructionConstants.TaskStatus.REINSPECTION_AND_RECTIFICATION:
                displayStringId = R.string.task_reinspectionand_rectification;
                break;
            case ConstructionConstants.TaskStatus.REINSPECT_RESERVING:
                displayStringId = R.string.task_reinspect_treserving;
                break;
            case ConstructionConstants.TaskStatus.REINSPECT_RESERVED:
                displayStringId = R.string.task_reinspect_reserved;
                break;
            case ConstructionConstants.TaskStatus.REINSPECT_INPROGRESS:
                displayStringId = R.string.task_reinspect_inprogress;
                break;
            case ConstructionConstants.TaskStatus.REINSPECT_DELAY:
                displayStringId = R.string.task_reinspect_delay;
                break;
            case ConstructionConstants.TaskStatus.DELETED:
                displayStringId = R.string.task_deleted;
                break;
            default:
                LogUtils.e(ConstructionConstants.LOG_TAG_TASK, "Unknow status " + status);
                displayStringId = R.string.task_status_unknow;
                break;
        }

        return displayStringId;
    }

    /**
     * Return status level which will decide background color
     *
     * @param status Task status
     * @return status level
     */
    public static int getStatusLevel(String status) {
        int level;
        switch (status.toLowerCase()) {
            case ConstructionConstants.TaskStatus.RESERVING:
            case ConstructionConstants.TaskStatus.REINSPECT_RESERVING:
            case ConstructionConstants.TaskStatus.INPROGRESS:
            case ConstructionConstants.TaskStatus.REINSPECTING:
            case ConstructionConstants.TaskStatus.REINSPECT_INPROGRESS:
                level = 1;
                break;
            case ConstructionConstants.TaskStatus.DELAYED:
            case ConstructionConstants.TaskStatus.REINSPECT_DELAY:
                level = 2;
                break;
            case ConstructionConstants.TaskStatus.UNQUALIFIED:
            case ConstructionConstants.TaskStatus.REINSPECTION:
            case ConstructionConstants.TaskStatus.REJECTED:
            case ConstructionConstants.TaskStatus.RECTIFICATION:
            case ConstructionConstants.TaskStatus.REINSPECTION_AND_RECTIFICATION:
                level = 3;
                break;
            case ConstructionConstants.TaskStatus.RESOLVED:
                level = 4;
                break;
            case ConstructionConstants.TaskStatus.OPEN:
                level = 5;
                break;
            default:
                level = 0;
                break;
        }

        return level;
    }


    public static int getStatusTextColor(Context context, String status) {
        switch (status.toLowerCase()) {
            case ConstructionConstants.TaskStatus.OPEN:
            case ConstructionConstants.TaskStatus.RESERVED:
                return ContextCompat.getColor(context, R.color.con_font_gray);
            default:
                return ContextCompat.getColor(context, R.color.white);
        }
    }

    /**
     * Return display time for project details and task details screen
     *
     * @param task Task
     * @return Display Time
     */
    public static Time getDisplayTime(Task task) {
        return task.getSortTime();
    }

    /**
     * Get all related assignee roles of the task
     *
     * @param task Task
     * @return return related assignee roles
     */
    private static HashSet<String> getAllAssigneesRole(Task task) {
        HashSet<String> members = new HashSet<>();
        String assignee = task.getAssignee();
        if (!TextUtils.isEmpty(assignee)) {
            members.add(assignee);
        }

        if (ConstructionConstants.TaskCategory.INSPECTOR_INSPECTION.equalsIgnoreCase(task.getCategory())) {
            members.add(ConstructionConstants.MemberType.INSPECTOR);
        }

        List<SubTask> subTasks = task.getSubTasks();
        for (SubTask subTask : subTasks) {
            String subtaskAssignee = subTask.getAssignee();
            if (!TextUtils.isEmpty(subtaskAssignee)) {
                members.add(subtaskAssignee);
            }

            List<Confirm> confirms = subTask.getConfirms();
            for (Confirm confirm: confirms) {
                String confirmRole = confirm.getRole();
                if (!TextUtils.isEmpty(confirmRole)) {
                    members.add(confirmRole);
                }
            }
        }

        return members;
    }

    public static ArrayList<Member> getTaskAssignees(Task task, ProjectInfo projectInfo) {
        ArrayList<Member> assignees = new ArrayList<>();
        HashSet<String> memberRoles = getAllAssigneesRole(task);
        for (String role: memberRoles) {
            Member member = ProjectUtils.getMemberByRole(projectInfo, role);
            if (member != null) {
                assignees.add(member);
            }
        }

        return assignees;
    }


    public static PickDateDialogFragment.Builder getPickDateDialogBuilder(Task task, ProjectInfo projectInfo) {
        PlanInfo plan = projectInfo.getPlan();

        List<Task> milestones = new ArrayList<>();
        for (Task tmpTask : plan.getTasks()) {
            if (tmpTask.isMilestone()) {
                milestones.add(tmpTask);
            }
        }

        PickDateDialogFragment.Builder builder = new PickDateDialogFragment.Builder(task.getName());

        // Set formatter
        MileStoneDayFormatter mileStoneDayFormatter = new MileStoneDayFormatter();
        mileStoneDayFormatter.setData(milestones);
        builder.setDayFormatter(mileStoneDayFormatter);

        // Set decorators
        MileStoneNodeDecorator mileStoneDecorator = new MileStoneNodeDecorator();
        ActiveMileStoneDecorator activeMileStoneDecorator = new ActiveMileStoneDecorator();
        activeMileStoneDecorator.setActiveTask(milestones, task);
        mileStoneDecorator.setData(milestones);
        builder.addDecorators(activeMileStoneDecorator,
                mileStoneDecorator);

        // set date limit
        Date startDate = DateUtil.iso8601ToDate(plan.getStart());
        Date endDate = DateUtil.iso8601ToDate(plan.getCompletion());

        int limitMonthOffset = 6;
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        // TODO optimize date format fail case
        calendar.setTime(startDate == null ? today : startDate);
        calendar.add(Calendar.MONTH, -limitMonthOffset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        Date minDate = calendar.getTime();

        // TODO optimize date format fail case
        calendar.setTime(endDate == null ? today : endDate);
        calendar.add(Calendar.MONTH, limitMonthOffset);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date maxDate = calendar.getTime();

        builder.setDateLimit(minDate, maxDate);

        Time displayTime = TaskUtils.getDisplayTime(task);
        Date taskStartDay = DateUtil.iso8601ToDate(displayTime.getStart());
        Date taskEndDay = DateUtil.iso8601ToDate(displayTime.getCompletion());
        builder.setCurrentDate(taskStartDay == null ? today : taskStartDay);

        // set selected days
        if (DateUtil.isSameDay(taskStartDay, taskEndDay)) {
            builder.setSelectedDate(taskStartDay);
        } else if (taskStartDay != null) {
            builder.setSelectedRange(taskStartDay, taskEndDay);
        }

        //set current date
        Time time = TaskUtils.getDisplayTime(task);
        Date taskStartDate = DateUtil.iso8601ToDate(time.getStart());
        builder.setSelectedDate(taskStartDate);
        builder.setCurrentDate(taskStartDate);

        return builder;
    }

    public static String getAvatarUrl(Context context,ArrayList<Member> members) {
        String uid = UserInfoUtils.getUid(context);
        if (members != null && members.size() > 0) {
            for (Member member : members) {
                if (member.getUid().equalsIgnoreCase(uid)) {
                    return member.getProfile().getAvatar();
                }
            }
        }
        return null;
    }

    public static String getTaskTemplateName(String taskTemplateId){
        String taskTemplateName = null;
        switch (taskTemplateId){
            case ConstructionConstants.TaskTemplateId.KAI_GONG_JIAO_DI:
                taskTemplateName = UIUtils.getString(R.string.kai_gong_jiao_di);
                break;
            case ConstructionConstants.TaskTemplateId.YINBIGONGCHENG_YANSHOU:
                taskTemplateName = UIUtils.getString(R.string.yinbigongcheng_yanshou);
                break;
            case ConstructionConstants.TaskTemplateId.BI_SHUI_SHI_YAN:
                taskTemplateName = UIUtils.getString(R.string.bi_shui_shi_yan);
                break;
            case ConstructionConstants.TaskTemplateId.ZHONGQI_YANSHOU:
                taskTemplateName = UIUtils.getString(R.string.zhongqi_yanshou);
                break;
            case ConstructionConstants.TaskTemplateId.JICHU_WANGONG_YANSHOU:
                taskTemplateName = UIUtils.getString(R.string.jichu_wangong_yanshou);
                break;
            case ConstructionConstants.TaskTemplateId.JUNGONG_YANSHOU:
                taskTemplateName = UIUtils.getString(R.string.jungong_yanshou);
                break;
            default:
                taskTemplateName = taskTemplateId;
                break;
        }
        return taskTemplateName;
    }
}
