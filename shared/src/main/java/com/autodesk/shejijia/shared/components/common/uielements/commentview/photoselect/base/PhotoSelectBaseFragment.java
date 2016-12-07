package com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.autodesk.shejijia.shared.components.common.uielements.commentview.photoselect.album.AlbumActivity;

import java.lang.ref.WeakReference;

public class PhotoSelectBaseFragment extends Fragment {
    protected AlbumActivity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //在Fragment中用 弱引用的方式持有一份Activity 引用，方便在Fragment中使用Context
        if (context instanceof AlbumActivity) {
            WeakReference<AlbumActivity> mActivityRef = new WeakReference<>((AlbumActivity) getActivity());
            mContext = mActivityRef.get();
        } else {
            throw new IllegalArgumentException("unexcepted context ");
        }
    }

}
