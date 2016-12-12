package com.autodesk.shejijia.shared.components.nodeprocess.utility;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.JsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/1
 */

public class TaskActionHelper {
    public enum TaskActionEnum {
        UNKOWN,
        ADD_REVERSE_TIME,
        UPDATE_REVERSE_TIME,
        FILL_FORM,
        VIEW_FORM,
        UPDATE_FORM,
        MARK_COMPLETE,
        UPLOAD_PHOTO,
        ADD_REINSPECTION_TIME,
        UPDATE_REINSPECTION_TIME,
    }

    private JSONObject mTaskActionsJsonObject;

    private TaskActionHelper() {
        mTaskActionsJsonObject = JsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(), "template/task_actions.json");
    }

    private static class TaskActionHolder {
        private static final TaskActionHelper INSTANCE = new TaskActionHelper();
    }

    public static TaskActionHelper getInstance() {
        return TaskActionHelper.TaskActionHolder.INSTANCE;
    }

    public List<TaskActionEnum> getActions(Task task) {
        String memType = UserInfoUtils.getMemberType(AdskApplication.getInstance());
        String taskCategory = task.getCategory();
        String taskStatus = task.getStatus();
        List<String> taskActionList = new ArrayList<>();
        try {
            JSONObject memberActions = mTaskActionsJsonObject.getJSONObject(memType);
            JSONObject taskCategoryActions = memberActions.getJSONObject(taskCategory);
            JSONArray taskActions = taskCategoryActions.getJSONArray(taskStatus.toLowerCase());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>(){}.getType();
            taskActionList = gson.fromJson(taskActions.toString(), listType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<TaskActionEnum> actionEnums = new ArrayList<>();
        for (String actionType: taskActionList) {
            TaskActionEnum taskActionEnum = convertTypeToEnum(actionType);
            if (taskActionEnum != TaskActionEnum.UNKOWN) {
                actionEnums.add(convertTypeToEnum(actionType));
            }
        }

        // Special case: forman + constucion + resolved + no files, show upload photo
        if (ConstructionConstants.MemberType.FORMAN.equalsIgnoreCase(memType)
                && ConstructionConstants.TaskCategory.CONSTRUCTION.equalsIgnoreCase(taskCategory)
                && ConstructionConstants.TaskStatus.RESOLVED.equalsIgnoreCase(taskStatus)
                && (task.getFiles() == null || task.getFiles().isEmpty())) {
            actionEnums.add(TaskActionEnum.UPLOAD_PHOTO);
        }

        return actionEnums;
    }


    private TaskActionEnum convertTypeToEnum(String type) {
        switch (type) {
            case "add_reserve_time":
                return TaskActionEnum.ADD_REVERSE_TIME;
            case "change_reserve_time":
                return TaskActionEnum.UPDATE_REVERSE_TIME;
            case "view_form":
                return TaskActionEnum.VIEW_FORM;
            case "fill_form":
                return TaskActionEnum.FILL_FORM;
            case "update_form":
                return TaskActionEnum.UPDATE_FORM;
            case "mark_complete":
                return TaskActionEnum.MARK_COMPLETE;
            case "upload_photo":
                return TaskActionEnum.UPLOAD_PHOTO;
            case "add_reinspection_time":
                return TaskActionEnum.ADD_REINSPECTION_TIME;
            case "change_reinspection_time":
                return TaskActionEnum.UPDATE_REINSPECTION_TIME;
            default:
                LogUtils.e("Unkown action type " + type);
                return TaskActionEnum.UNKOWN;
        }
    }

}
