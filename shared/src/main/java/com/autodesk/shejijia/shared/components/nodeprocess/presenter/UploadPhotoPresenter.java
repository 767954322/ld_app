package com.autodesk.shejijia.shared.components.nodeprocess.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.entity.microbean.ConstructionFile;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.nodeprocess.contract.UploadPhotoContact;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/14
 */

public class UploadPhotoPresenter implements UploadPhotoContact.Presenter {

    private UploadPhotoContact.View mView;

    private String mProjectId;
    private String mTaskId;

    public UploadPhotoPresenter(UploadPhotoContact.View view, String projectId, String taskId) {
        mView = view;
        mProjectId = projectId;
        mTaskId = taskId;
    }

    @Override
    public void uploadPhotos(@NonNull List<ImageInfo> imageInfos) {
        mView.showUploading();

        ArrayList<File> files = new ArrayList<>();
        for (ImageInfo imageInfo : imageInfos) {
            files.add(new File(imageInfo.getPath()));
        }

        FileHttpManager.getInstance().uploadFiles(files, new ResponseCallback<ArrayList<ConstructionFile>, ResponseError>() {
            @Override
            public void onSuccess(ArrayList<ConstructionFile> data) {
                bindFilesToTask(data);
            }

            @Override
            public void onError(ResponseError error) {
                mView.hideUploading();
                mView.showError(error.getMessage());
            }
        });
    }

    private void bindFilesToTask(ArrayList<ConstructionFile> data) {
        JSONObject filesJsonObject = new JSONObject();
        JSONArray filesJsonArray = new JSONArray();
        try {
            for (ConstructionFile file : data) {
                JSONObject fileJsonObject = new JSONObject();
                fileJsonObject.put("file_id", file.getFileId());
                fileJsonObject.put("file_type", ConstructionConstants.FileType.IMAGE);
                filesJsonArray.put(fileJsonObject);
            }
            filesJsonObject.put("files", filesJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Bundle params = new Bundle();
        params.putString(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, String.valueOf(mProjectId));
        params.putString(ConstructionConstants.BUNDLE_KEY_TASK_ID, String.valueOf(mTaskId));
        params.putString(ConstructionConstants.BUNDLE_KEY_TASK_FILES, String.valueOf(filesJsonObject.toString()))
        ;
        ProjectRepository.getInstance().uploadTaskFiles(params, "UPLOAD_TASK_FILE", new ResponseCallback<Void, ResponseError>() {
            @Override
            public void onSuccess(Void data) {
                mView.hideUploading();
                mView.onUploadSuccess();
            }

            @Override
            public void onError(ResponseError error) {
                mView.hideUploading();
                mView.showError(error.getMessage());
            }
        });
    }
}
