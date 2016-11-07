package com.autodesk.shejijia.shared.components.form.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.microbean.Form;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.common.uitity.JsonAssetHelper;
import com.autodesk.shejijia.shared.components.form.data.source.FormDataSource;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
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

    private static HashMap<String,String> json2Path;
    private List<ContainedForm> mFormList;
    private String projectID;
    private String taskID;
    private String taskAssignee;
    private List<Form> taskForms;

    private FormRepository(){
        json2Path = (HashMap<String, String>) JsonAssetHelper.routerJSON2Path();
    }

    private static class FormRepositoryHolder{
        private static final FormRepository INSTANCE = new FormRepository();
    }
    public static FormRepository getInstance(){
        return FormRepositoryHolder.INSTANCE;
    }

    @Override
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallback<List> callBack, final String[] fIds) {
        if(mFormList == null || mFormList.size() == 0){
            mFormList = new ArrayList<>();
            FormRemoteDataSource.getInstance().getRemoteFormItemDetails(new LoadDataCallback<List>() {
                @Override
                public void onLoadSuccess(List data){
                    for(int i = 0 ; i < data.size() ; i++){
                        HashMap remoteMap = (HashMap) data.get(i);
                        String fileName = String.format("%s.json",remoteMap.get("form_template_id"));
                        String filePath = json2Path.get(fileName);
                        JSONObject object = FormJsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(),filePath);
                        HashMap templateMap = (HashMap) FormJsonFileUtil.jsonObj2Map(object);
//                        HashMap templateMap = (HashMap) FormJsonFileUtil.jsonObj2Map(FormJsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(),fileName));
                        ContainedForm form = new ContainedForm(templateMap);
                        form.applyFormData(remoteMap);
                        mFormList.add(form);
                    }
                    callBack.onLoadSuccess(mFormList);
                }
                @Override
                public void onLoadFailed(String errorMsg) {
                    callBack.onLoadFailed(errorMsg);
                }
            },fIds);
        }
    }


    public void updateRemoteForms(List<ContainedForm> forms, Bundle bundle,  @NonNull final LoadDataCallback callBack) {
        if(forms == null || forms.size() == 0){
            return;
        }

        List<Map> tempFormList = new ArrayList<>();
        for(ContainedForm form : forms){
            tempFormList.add(form.getUpdateFormData());
        }
        FormRemoteDataSource.getInstance().updateFormDataWithData(tempFormList,bundle, new LoadDataCallback() {
            @Override
            public void onLoadSuccess(Object data) {
                callBack.onLoadSuccess(data);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                callBack.onLoadFailed(errorMsg);
            }
        });
    }

    public void initInspectionTaskWithTask(Task task) {
        taskForms = task.getForms();
    }

    public void initInspectionTaskWithProjectID(String projectID){
//        getProjectDetailsWithPId(Long.parseLong(projectID));
    }

//    public void getProjectDetailsWithPId(Long projectID) {
//        Bundle requestParamsBundle = new Bundle();
//        requestParamsBundle.putLong("pid", projectID);
//        requestParamsBundle.putBoolean("task_data",true);
//        ProjectRemoteDataSource.getInstance().getProjectTaskData(requestParamsBundle, ConstructionConstants.REQUEST_TAG_GET_PROJECT_DETAILS, new LoadDataCallback<ProjectInfo>() {
//            @Override
//            public void onLoadSuccess(ProjectInfo data) {
//                for(Task task : data.getPlan().getTasks()){
//                    if("inspector".equals(task.getAssignee()) &&
//                            (task.getStatus().equals())){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onLoadFailed(String errorMsg) {
//
//            }
//        });
//    }

    /**
     * 获取到项目的详情(不包含具体的task)
     * @param requestParams
     * @param requestTag
     * @param callback
     */
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull final ResponseCallback<Project> callback) {
        if(null == mProject) {
            ProjectRemoteDataSource.getInstance().getProjectTaskId(requestParams, requestTag, new ResponseCallback<Project>() {
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




}
