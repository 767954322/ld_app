package com.autodesk.shejijia.enterprise.nodeprocess.data;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.ProjectList;
import com.autodesk.shejijia.shared.components.common.listener.LoadDataCallback;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.ProjectDataSource;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.ProjectRemoteDataSource;

/**
 * Created by t_xuz on 10/17/16.
 *
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectList mProjectList;

    private ProjectRepository(){
    }

    private static final class NodeProcessRepositoryHolder{
        private static final ProjectRepository INSTANCE = new ProjectRepository();
    }

    public static ProjectRepository getInstance(){
        return NodeProcessRepositoryHolder.INSTANCE;
    }

    @Override
    public void getProjectList(Bundle requestParams, String requestTag, @NonNull final LoadDataCallback<ProjectList> callback) {

        if (mProjectList != null){
            callback.onLoadSuccess(mProjectList);
        }else {

            ProjectRemoteDataSource.getInstance().getProjectList(requestParams, requestTag, new LoadDataCallback<ProjectList>() {
                @Override
                public void onLoadSuccess(ProjectList data) {
                    mProjectList = data;
                    callback.onLoadSuccess(data);
                }

                @Override
                public void onLoadFailed(String errorMsg) {
                    callback.onLoadFailed(errorMsg);
                }
            });
        }
    }

    @Override
    public void getProjectDetails(@NonNull LoadDataCallback<ProjectInfo> callback) {
        ProjectRemoteDataSource.getInstance().getProjectDetails(callback);
    }
}
