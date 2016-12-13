package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.PreCheckUnqualified;
import com.autodesk.shejijia.shared.components.form.ui.activity.UnqualifiedActivity;

import java.io.File;
import java.util.List;

/**
 * Created by t_panya on 16/12/12.
 */

public class PreCheckUnqualifiedPresenter implements PreCheckUnqualified.Presenter {
    private static final String TAG = "PreCheckUnqualifiedPresenter";
    private PreCheckUnqualified.View mView;
    private Context mContext;

    public PreCheckUnqualifiedPresenter(PreCheckUnqualified.View view) {
        mView = view;
        if (view instanceof UnqualifiedActivity) {
            mContext = (Context) view;
        }
    }

    @Override
    public void nextStep(List<ImageInfo> mPictures, String mediaType) {
        for (ImageInfo data : mPictures) {
            if (data.getPhotoSource() == ImageInfo.PhotoSource.CLOUD) {
                continue;
            }
            String path = data.getPath();
            LogUtils.d(TAG, path);
            File file = new File(path);
            FileHttpManager.getInstance().upLoadFileByType(file, "image/jpg", new FileHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    LogUtils.d(TAG, response);
                }

                @Override
                public void onFailure() {
                    LogUtils.d(TAG, "onFailure");
                }
            });
        }

    }

    @Override
    public void setCheckIndex(SHPrecheckForm mPreCheckForm, boolean checked, int id) {
        if (id == R.id.imgbtn_equipment) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("equipment_whether_qualification".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("equipment_whether_qualification".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_custom_disagree) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("acceptance_whether_check".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("acceptance_whether_check".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_monitor_absence) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("monitor_whether_presence".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("monitor_whether_presence".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        } else if (id == R.id.imgbtn_not_standard) {
            if (checked) {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("interface_wether_standard".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(1);
                        break;
                    }
                }
            } else {
                for (CheckItem item : mPreCheckForm.getCheckItems()) {
                    if ("interface_wether_standard".equals(item.getItemId())) {
                        item.getFormFeedBack().setCurrentCheckIndex(0);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onButtonClick(int id) {

    }

}
