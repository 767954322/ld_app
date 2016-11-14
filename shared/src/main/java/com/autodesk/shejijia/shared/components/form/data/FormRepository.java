package com.autodesk.shejijia.shared.components.form.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.common.constant.TaskStatusTypeEnum;
import com.autodesk.shejijia.shared.components.form.common.uitity.FormFactory;
import com.autodesk.shejijia.shared.components.form.common.uitity.JsonAssetHelper;
import com.autodesk.shejijia.shared.components.form.data.source.FormDataSource;
import com.autodesk.shejijia.shared.components.form.common.entity.SHForm;
import com.autodesk.shejijia.shared.components.form.data.source.FormRemoteDataSource;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by t_panya on 16/10/20.
 */

public class FormRepository implements FormDataSource {

    private Project mProject;  //包含task id的项目详情
    private ProjectInfo mProjectInfo;  //包含task data的项目详情
    private static HashMap<String,String> templateID2Path;
    private List<SHForm> mFormList;
    private String projectID;
    private String taskID;
    private String taskAssignee;
    private String memberID;
    private String userName;
    private String userPhone;
    private String projectName;
    private String projectAddr;
    private List<Form> taskForms;

    private FormRepository(){
        templateID2Path = (HashMap<String, String>) JsonAssetHelper.routerJSON2Path();
    }

    private static class FormRepositoryHolder{
        private static final FormRepository INSTANCE = new FormRepository();
    }
    public static FormRepository getInstance(){
        return FormRepositoryHolder.INSTANCE;
    }

    @Override
    public void getRemoteFormItemDetails(@NonNull final ResponseCallback<List> callBack, final List<String> formIds) {
            if(mFormList == null || mFormList.size() == 0){
            mFormList = new ArrayList<>();
            FormRemoteDataSource.getInstance().getRemoteFormItemDetails(new ResponseCallback<List>() {
                @Override
                public void onSuccess(List data){
                    for(int i = 0 ; i < data.size() ; i++){
                        HashMap remoteMap = (HashMap) data.get(i);
                        String fileName = String.format("%s.json",remoteMap.get("form_template_id"));
                        String filePath = templateID2Path.get(fileName);
                        JSONObject object = FormJsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(),filePath);
                        HashMap templateMap = (HashMap) FormJsonFileUtil.jsonObj2Map(object);
//                        HashMap templateMap = (HashMap) FormJsonFileUtil.jsonObj2Map(FormJsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(),fileName));
                        SHForm form = FormFactory.createCategoryForm(templateMap);
//                        SHForm form = new SHForm(templateMap);
                        form.applyFormData(remoteMap);
                        mFormList.add(form);
                    }
                    callBack.onSuccess(mFormList);
                }
                @Override
                public void onError(String errorMsg) {
                    callBack.onError(errorMsg);
                }
            },formIds);
        } else {
            callBack.onSuccess(mFormList);
        }
    }


    public void updateRemoteForms(List<SHForm> forms, Bundle bundle, @NonNull final ResponseCallback callBack) {
        if(forms == null || forms.size() == 0){
            return;
        }

        List<Map> tempFormList = new ArrayList<>();
        for(SHForm form : forms){
            tempFormList.add(form.getUpdateFormData());
        }
        FormRemoteDataSource.getInstance().updateFormDataWithData(tempFormList,bundle, new ResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                callBack.onSuccess(data);
            }

            @Override
            public void onError(String errorMsg) {
                callBack.onError(errorMsg);
            }
        });
    }

    public void initInspectionTaskWithTask(Task task) {
        projectID = task.getProjectId();
        taskID = task.getTaskId();
        taskAssignee = task.getAssignee();
        taskForms = task.getForms();
    }

    public void initInspectionTaskWithProjectID(String projectID){
        getProjectDetailsWithPId(Long.parseLong(projectID));
    }

    public void getProjectDetailsWithPId(final Long projectId) {
        taskForms = new ArrayList<>();
        Bundle requestParamsBundle = new Bundle();
        requestParamsBundle.putLong("pid", projectId);
        requestParamsBundle.putBoolean("task_data",true);
        ProjectRemoteDataSource.getInstance().getProjectInfo(requestParamsBundle, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new ResponseCallback<ProjectInfo>() {
            @Override
            public void onSuccess(ProjectInfo projectInfo) {

                for(Task task : projectInfo.getPlan().getTasks()){
                    if("inspector".equals(task.getAssignee())
                            && (TaskStatusTypeEnum.TASK_STATUS_INPROGRESS.getTaskStatus()
                                        .equalsIgnoreCase(task.getStatus())
                                ||TaskStatusTypeEnum.TASK_STATUS_DELAY.getTaskStatus()
                                        .equalsIgnoreCase(task.getStatus())
                                ||TaskStatusTypeEnum.TASK_STATUS_REINSPECTION.getTaskStatus()
                                        .equalsIgnoreCase(task.getStatus())
                                ||TaskStatusTypeEnum.TASK_STATUS_RECTIFICATION.getTaskStatus()
                                        .equalsIgnoreCase(task.getStatus())
                                ||TaskStatusTypeEnum.TASK_STATUS_REINSPECTION_AND_RECTIFICATION.getTaskStatus()
                                        .equalsIgnoreCase(task.getStatus()))){

                        projectID = String.valueOf(projectInfo.getProjectId());
                        taskID = task.getTaskId();
                        taskAssignee = task.getAssignee();
                        if(task.getForms() != null && task.getForms().size() != 0){
                            for(Form formInfo : task.getForms()){
                                if("inspection".equals(formInfo.getCategory())
                                        || "precheck".equals(formInfo.getCategory())){
                                    taskForms.add(formInfo);
                                }
                            }
                        }

                        if(projectInfo.getMembers() != null && projectInfo.getMembers().size()!= 0){
                            for(Member member : projectInfo.getMembers()){
                                if("member".equals(member.getRole())){
                                    memberID = member.getUid();
                                    userName = member.getProfile().getName();
                                    userPhone = member.getProfile().getMobile();
                                    break;
                                }
                            }
                        }
                        projectName = projectInfo.getName();
                        projectAddr = String.format("%s%s%s%s",projectInfo.getBuilding().getProvince(),projectInfo.getBuilding().getCityName()
                                                                ,projectInfo.getBuilding().getDistrictName(),projectInfo.getBuilding().getCommunityName());
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    /**
     * 获取到项目的详情(不包含具体的task)
     * @param requestParams
     * @param requestTag
     * @param callback
     */
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project> callback) {
        if(null == mProject) {
            ProjectRemoteDataSource.getInstance().getProject(requestParams, requestTag, new ResponseCallback<Project>() {
                @Override
                public void onSuccess(Project data) {
                    callback.onSuccess(data);
                }

                @Override
                public void onError(String errorMsg) {
                    callback.onError(errorMsg);
                }
            });

        } else {
            callback.onSuccess(mProject);
        }
    }

    /**
     * 获取到包含task详情的项目
     * @param requestParams
     * @param requestTag
     * @param callback
     */
    public void getProjectTaskData(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<ProjectInfo> callback) {
        if(null == mProjectInfo) {
            ProjectRemoteDataSource.getInstance().getProjectInfo(requestParams, requestTag, new ResponseCallback<ProjectInfo>() {
                @Override
                public void onSuccess(ProjectInfo data) {
                    callback.onSuccess(data);
                }

                @Override
                public void onError(String errorMsg) {
                    callback.onError(errorMsg);
                }
            });

        } else {
            callback.onSuccess(mProjectInfo);
        }
    }




}
