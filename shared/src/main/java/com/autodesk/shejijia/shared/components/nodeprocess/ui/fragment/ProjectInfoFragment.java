package com.autodesk.shejijia.shared.components.nodeprocess.ui.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.tools.zxing.encoding.EncodingHandler;
import com.autodesk.shejijia.shared.components.common.utility.ScreenUtil;

/**
 * Created by t_xuz on 11/10/16.
 * 项目信息页的弹出框
 */

public class ProjectInfoFragment extends AppCompatDialogFragment {

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
        setStyle(STYLE_NO_FRAME,0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_info_dialog, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        updateViews();
        initEvent();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getActivity(),R.style.Construction_DialogStyle_Translucent);
    }

    private void initView(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.imgBtn_close);
        mUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mUserAddress = (TextView) view.findViewById(R.id.tv_user_address);
        mRoomArea = (TextView) view.findViewById(R.id.tv_room_area);
        mRoomType = (TextView) view.findViewById(R.id.tv_room_type);
        mQRCodeImg = (ImageView) view.findViewById(R.id.img_qr_code);
    }

    private void initEvent() {
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
            try {
                Bitmap bitmap = EncodingHandler.createQRCode(getArguments().getLong("projectId") + "", ScreenUtil.dip2px(200));
                mQRCodeImg.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
