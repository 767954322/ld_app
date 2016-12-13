package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.network.FileHttpManager;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.PreCheckUnqualified;
import com.autodesk.shejijia.shared.components.form.presenter.PreCheckUnqualifiedPresenter;
import com.autodesk.shejijia.shared.components.form.ui.activity.UnqualifiedActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by t_panya on 16/12/13.
 */

public class UnqualifiedCommitFragment extends BaseConstructionFragment implements View.OnClickListener,
        PreCheckUnqualified.View {
    private static final String TAG = "UnqualifiedCommitFragment";
    private TextView mAcceptanceCondition;
    private TextView mCustomAbsenceTel;
    private TextView mCustomDetail;
    private TextView mConfirmId;
    private TextView mConfirmDetail;
    private TextView mCheckPaper;
    private TextView mCheckPaperDetail;
    private Button mCommitBtn;
    private Button mCommitCancel;
    private CommentFragment mCommentFragment;
    private SHPrecheckForm mPreCheckForm;
    private ArrayList<ImageInfo> mPicture;
    private String mAudioPath;
    private String mCommentContent;
    private CommentPresenter mCommentPresenter;
    private PreCheckUnqualifiedPresenter mPresenter;

    public static UnqualifiedCommitFragment newInstance(Bundle params) {
        UnqualifiedCommitFragment fragment = new UnqualifiedCommitFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_commit) {
            LogUtils.d(TAG, mPicture.get(0).getPath());
            File file = new File(mPicture.get(0).getPath());
            FileHttpManager.getInstance().upLoadFileByType(file, "IMAGE", new FileHttpManager.ResponseHandler() {
                @Override
                public void onSuccess(String response) {
                    LogUtils.d(TAG, response);
                    ToastUtils.showShort(mContext, "上传成功");
                }

                @Override
                public void onFailure() {

                }
            });
        } else if (v.getId() == R.id.btn_cancel) {

        }
    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_unqualified_commit;
    }

    @Override
    protected void initView() {
        mAcceptanceCondition = (TextView) rootView.findViewById(R.id.tv_acceptance_condition);
        mCustomAbsenceTel = (TextView) rootView.findViewById(R.id.tv_custom_absence_tel);
        mCustomDetail = (TextView) rootView.findViewById(R.id.tv_custom_detail);
        mConfirmId = (TextView) rootView.findViewById(R.id.tv_confirm_id);
        mConfirmDetail = (TextView) rootView.findViewById(R.id.tv_confirm_detail);
        mCheckPaper = (TextView) rootView.findViewById(R.id.tv_check_paper);
        mCheckPaperDetail = (TextView) rootView.findViewById(R.id.tv_check_paper_detail);
        mCommitBtn = (Button) rootView.findViewById(R.id.btn_commit);
        mCommitBtn.setOnClickListener(this);
        mCommitCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        mCommitCancel.setOnClickListener(this);
        mPresenter = new PreCheckUnqualifiedPresenter(this);
    }

    @Override
    protected void initData() {
        mPreCheckForm = (SHPrecheckForm) getArguments().getSerializable(UnqualifiedActivity.UNQUALIFIED_FORM);
        mPicture = getArguments().getParcelableArrayList(UnqualifiedActivity.UNQUALIFIED_IMAGES);
        mCommentContent = getArguments().getString(UnqualifiedActivity.UNQUALIFIED_COMMENT);
        mAudioPath = getArguments().getString(UnqualifiedActivity.UNQUALIFIED_AUDIO);
        initToolbar(mPreCheckForm.getTitle());
        CommentConfig config = new CommentConfig();
        config.seteLayoutType(CommentConfig.LayoutType.SHOW).setAudioPath(mAudioPath).setCommentContent(mCommentContent);
        mCommentFragment = CommentFragment.getInstance(config);
        mCommentPresenter = new CommentPresenter(mCommentFragment, mPicture, true);
        setFragment();

        for (CheckItem item : mPreCheckForm.getCheckItems()) {
            if ("telephone_consultation".equals(item.getItemId())) {
                mCustomAbsenceTel.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mCustomDetail.setText("否");
                        break;
                    case 0:
                        mCustomDetail.setText("是");
                        break;
                    default:
                        mCustomDetail.setText("否");
                        break;
                }
                continue;
            } else if ("identify_status".equals(item.getItemId())) {
                mConfirmId.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mConfirmDetail.setText("否");
                        break;
                    case 0:
                        mConfirmDetail.setText("是");
                        break;
                    default:
                        mConfirmDetail.setText("否");
                        break;
                }
                continue;
            } else if ("check_hydropower_drawings".equals(item.getItemId())) {
                mCheckPaper.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mCheckPaperDetail.setText("否");
                        break;
                    case 0:
                        mCheckPaperDetail.setText("是");
                        break;
                    default:
                        mCheckPaperDetail.setText("否");
                        break;
                }
                continue;
            }
        }
    }

    private void setFragment() {
        FragmentManager manager = mContext.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_commit_comment_container, mCommentFragment, CommentFragment.TAG);
        ft.commit();
    }

    private void initToolbar(String title) {
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setTitle(title);
    }

}
