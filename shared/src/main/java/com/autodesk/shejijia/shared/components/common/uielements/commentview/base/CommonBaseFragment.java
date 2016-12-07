package com.autodesk.shejijia.shared.components.common.uielements.commentview.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by t_panya on 16/12/2.
 */

public class CommonBaseFragment extends Fragment {
    protected CommonBaseActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在Fragment中用 弱引用的方式持有一份Activity 引用，方便在Fragment中使用Context
        if (context instanceof CommonBaseActivity) {
            WeakReference<CommonBaseActivity> mActivityRef = new WeakReference<>((CommonBaseActivity) getActivity());
            mContext = mActivityRef.get();
        } else {
            throw new IllegalArgumentException("unexcepted context ");
        }
    }
}
