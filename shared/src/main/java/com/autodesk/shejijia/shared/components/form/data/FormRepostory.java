package com.autodesk.shejijia.shared.components.form.data;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.form.common.utils.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.data.source.FormDataSource;
import com.autodesk.shejijia.shared.components.form.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.form.listener.LoadDataCallBack;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by t_panya on 16/10/20.
 */

public class FormRepostory implements FormDataSource {

    private static FormRepostory INSTANCE = null;

    private FormDataSource mFormRemoteDataSource;
    private List<ContainedForm> mFormList;


    private FormRepostory(@NonNull  FormDataSource formRemoteDataSource){
        mFormRemoteDataSource = formRemoteDataSource;
    }

    public static FormRepostory getInstance(@NonNull  FormDataSource formRemoteDataSource){
        if (INSTANCE == null) {
            INSTANCE = new FormRepostory(formRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallBack<List> callBack, final String[] fIds) {
        if(mFormList == null || mFormList.size() == 0){
            mFormList = new ArrayList<>();
            mFormRemoteDataSource.getRemoteFormItemDetails(new LoadDataCallBack<List>() {
                @Override
                public void onLoadSuccess(List data){
                    for(int i = 0 ; i < data.size() ; i++){
                        ContainedForm remoteForm = (ContainedForm) data.get(i);
                        String fileName = String.format("%s.json",remoteForm.getFormTemplateId());
                        ContainedForm localForm = GsonUtil.jsonToBean(FormJsonFileUtil.loadJSONFromAsset(AdskApplication.getInstance(),fileName),ContainedForm.class);
                        localForm.applyFormData(remoteForm);
                        mFormList.add(localForm);
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
    public void updateRemoteFormItems(@NonNull LoadDataCallBack callBack, String projectId, String taskId, List<ContainedForm> forms) {

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

}
