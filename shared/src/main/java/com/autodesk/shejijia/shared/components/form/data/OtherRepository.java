package com.autodesk.shejijia.shared.components.form.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Task;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.shared.components.form.data.source.OtherDataSource;
import com.autodesk.shejijia.shared.components.form.data.source.OtherRemoteDataSource;

/**
 * Created by t_aij on 16/11/1.
 */

public class OtherRepository implements OtherDataSource {
    private Project mProject;
    private Task mTask;
    //清楚内存中的project
    public void clearProject() {
        mProject = null;
    }
    //清除内存中的task
    public void clearTask() {
        mTask = null;
    }

    private OtherRepository(){}
    private static class OtherRepositoryHolder{
        private final static OtherRepository instance = new OtherRepository();
    }
    public static OtherRepository getInstance(){
        return OtherRepositoryHolder.instance;
    }


    @Override
    public void getProjectDetail(@NonNull final LoadDataCallback<Project> callback, Bundle requestParams) {
        if(null == mProject) {
            OtherRemoteDataSource.getInstance().getProjectDetail(new LoadDataCallback<Project>() {
                @Override
                public void onLoadSuccess(Project data) {
                    mProject = data;
                    callback.onLoadSuccess(data);
                }

                @Override
                public void onLoadFailed(String errorMsg) {
                    callback.onLoadFailed(errorMsg);
                }
            }, requestParams);

        } else {
            callback.onLoadSuccess(mProject);
        }
    }

    @Override
    public void getTaskDetail(@NonNull final LoadDataCallback<Task> callback, Bundle requestParams) {
        if (null == mTask) {
            OtherRemoteDataSource.getInstance().getTaskDetail(new LoadDataCallback<com.autodesk.shejijia.shared.components.common.entity.microbean.Task>() {
                @Override
                public void onLoadSuccess(Task data) {
                    mTask = data;
                    callback.onLoadSuccess(data);
                }

                @Override
                public void onLoadFailed(String errorMsg) {
                    callback.onLoadFailed(errorMsg);
                }
            },requestParams);

        } else {
            callback.onLoadSuccess(mTask);
        }
    }
}
