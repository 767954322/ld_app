package com.autodesk.shejijia.enterprise.nodeprocess.data;

import android.support.annotation.NonNull;

import com.autodesk.shejijia.enterprise.common.entity.ProjectBean;
import com.autodesk.shejijia.enterprise.common.entity.ProjectListBean;
import com.autodesk.shejijia.enterprise.common.listener.LoadDataCallback;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.ProjectDataSource;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.ProjectRemoteDataSource;

/**
 * Created by t_xuz on 10/17/16.
 *
 */
public final class ProjectRepository implements ProjectDataSource {

    private ProjectListBean mProjectListBean;

    private ProjectRepository(){
    }

    private static final class NodeProcessRepositoryHolder{
        private static final ProjectRepository INSTANCE = new ProjectRepository();
    }

    public static ProjectRepository getInstance(){
        return NodeProcessRepositoryHolder.INSTANCE;
    }

    @Override
    public void getProjectList(String requestUrl, String eventTag, String requestTag, @NonNull final LoadDataCallback<ProjectListBean> callback) {

        if (mProjectListBean != null){
            callback.onLoadSuccess(mProjectListBean);
        }else {

            ProjectRemoteDataSource.getInstance().getProjectList(requestUrl, eventTag, requestTag, new LoadDataCallback<ProjectListBean>() {
                @Override
                public void onLoadSuccess(ProjectListBean data) {
                    mProjectListBean = data;
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
    public void getProjectDetails(@NonNull LoadDataCallback<ProjectBean> callback) {
        ProjectRemoteDataSource.getInstance().getProjectDetails(callback);
    }
}
