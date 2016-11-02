package com.autodesk.shejijia.shared.components.form.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.datamodel.ProjectRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.form.data.source.FormDataSource;
import com.autodesk.shejijia.shared.components.form.data.source.FormRemoteDataSource;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by t_panya on 16/10/20.
 */

public class FormRepository implements FormDataSource {
    private Project mProject;
    private Task mTask;

    private List<ContainedForm> mFormList;


    private FormRepository(){
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
                        HashMap templateMap = (HashMap) FormJsonFileUtil.jsonObj2Map(FormJsonFileUtil.loadJSONDataFromAsset(AdskApplication.getInstance(),fileName));
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

    @Override
    public void updateRemoteFormItems(@NonNull LoadDataCallback callBack, String projectId, String taskId, List<ContainedForm> forms) {


    }

//    @Override
//    public void getRemoteFormItemDetails(@NonNull LoadDataCallBack<Map> callBack, String[] fIds) {
//        if(mFormList == null || mFormList.size() == 0){
//            mFormList = new ArrayList<>();
//            mFormRemoteDataSource.getRemoteFormItemDetails(new LoadDataCallBack<Map>() {
//                @Override
//                public void onLoadSuccess(Map data) {
//
//                }
//
//                @Override
//                public void onLoadFailed(String errorMsg) {
//
//                }
//            },fIds);
//        }
//    }

    /**
     * 获取到项目的详情(不包含具体的task)
     * @param requestParams
     * @param requestTag
     * @param callback
     */
    public void getProjectTaskId(Bundle requestParams, String requestTag, @NonNull final LoadDataCallback<Project> callback) {
        if(null == mProject) {
            ProjectRemoteDataSource.getInstance().getProjectTaskId(requestParams, requestTag, new LoadDataCallback<Project>() {
                @Override
                public void onLoadSuccess(Project data) {
                    callback.onLoadSuccess(data);
                }

                @Override
                public void onLoadFailed(String errorMsg) {
                    callback.onLoadFailed(errorMsg);
                }
            });

        } else {
            callback.onLoadSuccess(mProject);
        }
    }




}
