package com.autodesk.shejijia.shared.components.nodeprocess.utility;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.JsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by t_xuz on 12/14/16.
 * 用于任务列表显示头像的辅助类
 */

public class TaskHeadPicHelper {

    public static final String SHOW_HEAD = "show_head";
    public static final String SHOW_DEFAULT = "show_default";

    private JSONObject taskHeadPicJsonObject;

    private TaskHeadPicHelper() {
        taskHeadPicJsonObject = JsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(), "template/task_pic_actions.json");
    }

    private static class TaskHeadPicHelperHolder {
        private static final TaskHeadPicHelper INSTANCE = new TaskHeadPicHelper();
    }

    public static TaskHeadPicHelper getInstance() {
        return TaskHeadPicHelperHolder.INSTANCE;
    }

    public String getActions(Task task) {
        String memberType = UserInfoUtils.getMemberType(AdskApplication.getInstance());
        String taskCategory = task.getCategory();
        String taskStatus = task.getStatus();
        try {
            JSONObject memberActions = taskHeadPicJsonObject.getJSONObject(memberType);
            JSONObject taskCategoryActions = memberActions.getJSONObject(taskCategory);
            LogUtils.e("taskCategoryActions", taskCategoryActions.toString());
            if (taskCategoryActions.has(taskStatus.toLowerCase())) {
                JSONArray taskActions = taskCategoryActions.getJSONArray(taskStatus.toLowerCase());
                Gson gson = new Gson();
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                List<String> taskActionList = gson.fromJson(taskActions.toString(), listType);
                if (taskActionList.get(0).equalsIgnoreCase(SHOW_HEAD)) {
                    return SHOW_HEAD;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SHOW_DEFAULT;
    }

}
