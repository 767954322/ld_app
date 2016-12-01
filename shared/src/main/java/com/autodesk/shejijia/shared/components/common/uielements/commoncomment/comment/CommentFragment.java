package com.autodesk.shejijia.shared.components.common.uielements.commoncomment.comment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.autodesk.shejijia.shared.components.common.uielements.commoncomment.CommentContract;

/**
 * Created by t_panya on 16/11/29.
 */

public class CommentFragment extends Fragment implements CommentContract.View{

    public static final String TAG = "CommentFragment";
    Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity != null){
            mActivity = activity;
        }
    }

    /**
     * 设置layout类型
     */
    @Override
    public void setLayoutType() {

    }

    /**
     * 设置presenter
     * @param presenter
     */
    @Override
    public void setPresenter(CommentContract.Presenter presenter) {

    }

    @Override
    public void showToast(CharSequence message) {

    }
}
