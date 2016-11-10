package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewGroupCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by t_xuz on 11/10/16.
 * 项目信息页的弹出框
 */

public class ProjectInfoFragment extends DialogFragment {

    private TextView mUserName;
    private TextView mUserAddress;
    private TextView mRoomArea;
    private TextView mRoomType;
    private ImageView mQRCodeImg;

    public static ProjectInfoFragment newInstance(Bundle projectInfoBundle) {
        ProjectInfoFragment projectInfoFragment = new ProjectInfoFragment();
        projectInfoFragment.setArguments(projectInfoBundle);
        return projectInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_info_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        updateViews();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    private void initView(View view) {
        mUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mUserAddress = (TextView) view.findViewById(R.id.tv_user_address);
        mRoomArea = (TextView) view.findViewById(R.id.tv_room_area);
        mRoomType = (TextView) view.findViewById(R.id.tv_room_type);
        mQRCodeImg = (ImageView) view.findViewById(R.id.img_qr_code);
    }

    private void updateViews() {
        if (getArguments() != null) {
            mUserName.setText(getArguments().getString("user_name", " "));
            mUserAddress.setText(getArguments().getString("user_address", " "));
            mRoomArea.setText(getArguments().getString("room_area", " "));
            mRoomType.setText(getArguments().getString("room_type", " "));
        }
    }


}
