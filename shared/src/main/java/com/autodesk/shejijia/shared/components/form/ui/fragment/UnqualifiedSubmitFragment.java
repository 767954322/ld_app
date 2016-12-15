package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.ConProgressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomDialog;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.UnqualifiedContract;
import com.autodesk.shejijia.shared.components.form.presenter.UnqualifiedPresenter;
import com.autodesk.shejijia.shared.components.form.ui.activity.UnqualifiedActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;

/**
 * Created by t_panya on 16/12/13.
 */

public class UnqualifiedSubmitFragment extends BaseConstructionFragment implements View.OnClickListener,
        UnqualifiedContract.View {
    private static final String TAG = "UnqualifiedCommitFragment";
    public static boolean isSubmitFinish = false;
    private TextView mCustomAbsenceTelTV;
    private TextView mCustomDetailTV;
    private TextView mConfirmIdTV;
    private TextView mConfirmDetailTV;
    private TextView mCheckPaperTV;
    private TextView mCheckPaperDetailTV;
    private Button mSubmitBtn;
    private Button mSubmitCancelBtn;

    private CommentFragment mCommentFragment;
    private SHPrecheckForm mPreCheckForm;
    private ArrayList<ImageInfo> mPicture;
    private String mAudioPath;
    private String mCommentContent;
    private CommentPresenter mCommentPresenter;
    private UnqualifiedPresenter mPresenter;

    private ConProgressDialog mProgressDialog;

    private onFinishListener mListener;

    public interface onFinishListener {
        void onSubmitFinish();
    }

    public static UnqualifiedSubmitFragment newInstance(Bundle params) {
        UnqualifiedSubmitFragment fragment = new UnqualifiedSubmitFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UnqualifiedActivity) {
            LogUtils.d(TAG, "context instanceof UnqualifiedActivity");
            mListener = (onFinishListener) context;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_commit) {
            showDialog("上传以后不可更改,是否确定上传");
        } else if (v.getId() == R.id.btn_cancel) {
            getActivity().finish();
        }
    }

    @Override
    public void showDialog(String message) {
        new CustomDialog(mContext, new CustomDialog.OnDialogClickListenerCallBack() {
            @Override
            public void onClickOkListener() {
                showLoading();
                mPresenter.upLoadFiles(mPicture, mAudioPath, mCommentContent, mPreCheckForm);
            }

            @Override
            public void onClickCancelListener() {

            }
        }).showCustomViewDialog("提示", message, false, true, true, false);
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ConProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.submit_loading));
        }

        mProgressDialog.show();
    }

    @Override
    public void submitSuccess() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        isSubmitFinish = true;
        mListener.onSubmitFinish();
    }

    @Override
    public void submitError() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_unqualified_commit;
    }

    @Override
    protected void initView() {
        mCustomAbsenceTelTV = (TextView) rootView.findViewById(R.id.tv_custom_absence_tel);
        mCustomDetailTV = (TextView) rootView.findViewById(R.id.tv_custom_detail);
        mConfirmIdTV = (TextView) rootView.findViewById(R.id.tv_confirm_id);
        mConfirmDetailTV = (TextView) rootView.findViewById(R.id.tv_confirm_detail);
        mCheckPaperTV = (TextView) rootView.findViewById(R.id.tv_check_paper);
        mCheckPaperDetailTV = (TextView) rootView.findViewById(R.id.tv_check_paper_detail);
        mSubmitBtn = (Button) rootView.findViewById(R.id.btn_commit);
        mSubmitBtn.setOnClickListener(this);
        mSubmitCancelBtn = (Button) rootView.findViewById(R.id.btn_cancel);
        mSubmitCancelBtn.setOnClickListener(this);
        mPresenter = new UnqualifiedPresenter(this);
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
                mCustomAbsenceTelTV.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mCustomDetailTV.setText("否");
                        break;
                    case 0:
                        mCustomDetailTV.setText("是");
                        break;
                    default:
                        mCustomDetailTV.setText("否");
                        break;
                }
                continue;
            } else if ("identify_status".equals(item.getItemId())) {
                mConfirmIdTV.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mConfirmDetailTV.setText("否");
                        break;
                    case 0:
                        mConfirmDetailTV.setText("是");
                        break;
                    default:
                        mConfirmDetailTV.setText("否");
                        break;
                }
                continue;
            } else if ("check_hydropower_drawings".equals(item.getItemId())) {
                mCheckPaperTV.setText(item.getTitle());
                switch (item.getFormFeedBack().getCurrentCheckIndex()) {
                    case 1:
                        mCheckPaperDetailTV.setText("否");
                        break;
                    case 0:
                        mCheckPaperDetailTV.setText("是");
                        break;
                    default:
                        mCheckPaperDetailTV.setText("否");
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
