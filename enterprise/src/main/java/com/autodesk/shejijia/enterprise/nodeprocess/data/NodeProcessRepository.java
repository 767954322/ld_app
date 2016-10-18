package com.autodesk.shejijia.enterprise.nodeprocess.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.enterprise.nodeprocess.data.entity.TaskListBean;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.NodeProcessDataSource;
import com.autodesk.shejijia.enterprise.nodeprocess.data.source.NodeProcessRemoteDataSource;

/**
 * Created by t_xuz on 10/17/16.
 *
 */
public final class NodeProcessRepository implements NodeProcessDataSource{

    private TaskListBean mTaskListBean;

    private NodeProcessRepository (){
    }

    private static final class NodeProcessRepositoryHolder{
        private static final NodeProcessRepository INSTANCE = new NodeProcessRepository();
    }

    public static NodeProcessRepository getInstance(){
        return NodeProcessRepositoryHolder.INSTANCE;
    }

    @Override
    public void getProjectList(String requestUrl, String eventTag, String requestTag, @NonNull final LoadProjectListCallback callback) {

        //NodeProcessRemoteDataSource.getInstance().getProjectList(requestUrl,eventTag,requestTag,callback);
        if (mTaskListBean != null){
            callback.onProjectListLoadSuccess(mTaskListBean);
        }else {

            NodeProcessRemoteDataSource.getInstance().getProjectList(requestUrl, eventTag, requestTag, new LoadProjectListCallback() {
                @Override
                public void onProjectListLoadSuccess(TaskListBean taskList) {
                    mTaskListBean = taskList;
                    callback.onProjectListLoadSuccess(taskList);
                }

                @Override
                public void onProjectListLoadFailed(String errorMsg) {
                    callback.onProjectListLoadFailed(errorMsg);
                }
            });
        }
    }

    @Override
    public void getProjectDetails(@NonNull GetProjectDetailsCallback callback) {
        NodeProcessRemoteDataSource.getInstance().getProjectDetails(callback);
    }
}
