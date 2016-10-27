package com.autodesk.shejijia.shared.components.form.data;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.FormJsonFileUtil;
import com.autodesk.shejijia.shared.components.form.data.source.FormDataSource;
import com.autodesk.shejijia.shared.components.form.common.entity.ContainedForm;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/10/20.
 */

public class FormRepository implements FormDataSource {

    private static FormRepository INSTANCE = null;

    private FormDataSource mFormRemoteDataSource;
    private List<ContainedForm> mFormList;


    private FormRepository(){
    }

    private static class FormRepostoryHolder{
        private static final FormRepository INSTANCE = new FormRepository();
    }
    public static FormRepository getInstance(){
        return FormRepostoryHolder.INSTANCE;
    }

    @Override
    public void getRemoteFormItemDetails(@NonNull final LoadDataCallback<List> callBack, final String[] fIds) {
        if(mFormList == null || mFormList.size() == 0){
            mFormList = new ArrayList<>();
            mFormRemoteDataSource.getRemoteFormItemDetails(new LoadDataCallback<List>() {
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

}
