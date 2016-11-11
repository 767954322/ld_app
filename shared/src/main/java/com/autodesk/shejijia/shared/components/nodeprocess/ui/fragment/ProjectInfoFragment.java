package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;

/**
 * Created by t_xuz on 11/10/16.
 * 项目信息页的弹出框
 */

public class ProjectInfoFragment extends DialogFragment {

    private ImageButton mCloseBtn;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);
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
        initEvent();
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
        mCloseBtn = (ImageButton)view.findViewById(R.id.imgBtn_close);
        mUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mUserAddress = (TextView) view.findViewById(R.id.tv_user_address);
        mRoomArea = (TextView) view.findViewById(R.id.tv_room_area);
        mRoomType = (TextView) view.findViewById(R.id.tv_room_type);
        mQRCodeImg = (ImageView) view.findViewById(R.id.img_qr_code);
    }

    private void initEvent(){
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void updateViews() {
        if (getArguments() != null) {
            mUserName.setText(getArguments().getString("userName", " "));
            mUserAddress.setText(getArguments().getString("userAddress", " "));
            mRoomArea.setText(getArguments().getString("roomArea", " "));
            mRoomType.setText(getArguments().getString("roomType", " "));
        }
    }


}
