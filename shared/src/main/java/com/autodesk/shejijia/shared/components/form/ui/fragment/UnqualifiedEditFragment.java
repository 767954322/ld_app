package com.autodesk.shejijia.shared.components.form.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.CustomDialog;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.CommentConfig;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentFragment;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.comment.CommentPresenter;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.form.common.constant.SHFormConstant;
import com.autodesk.shejijia.shared.components.form.common.entity.categoryForm.SHPrecheckForm;
import com.autodesk.shejijia.shared.components.form.common.entity.microBean.CheckItem;
import com.autodesk.shejijia.shared.components.form.contract.UnqualifiedContract;
import com.autodesk.shejijia.shared.components.form.presenter.UnqualifiedPresenter;
import com.autodesk.shejijia.shared.components.form.ui.activity.UnqualifiedActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_panya on 16/12/12.
 */

public class UnqualifiedEditFragment extends BaseConstructionFragment implements View.OnClickListener,
        UnqualifiedContract.View {

    private FrameLayout mFragmentContainer;
    private ImageButton mEquipmentNotQualifiedBtn;
    private ImageButton mCustomDisagreeBtn;
    private ImageButton mMonitorAbsenceBtn;
    private ImageButton mUnStandardBtn;
    private Button mNextStepBtn;
    private TextView mEquipment;
    private TextView mCustomerDisAgree;
    private TextView mMonitorAbsence;
    private TextView mInterface;
    private CommentFragment mCommentFragment;
    private CustomDialog mDialog;
    private CommentPresenter mCommentPresenter;
    private UnqualifiedSubmitFragment mFragment;

    private UnqualifiedPresenter mPresenter;

    private List<CheckItem> mItems;
    private SHPrecheckForm mPreCheckForm;
    private ArrayList<ImageInfo> mPicture;
    private String mAudioPath;
    private String mCommentContent;

    public static UnqualifiedEditFragment getInstance(Bundle params) {
        UnqualifiedEditFragment fragment = new UnqualifiedEditFragment();
        fragment.setArguments(params);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_unqualified;
    }

    @Override
    protected void initView() {
        mFragmentContainer = (FrameLayout) rootView.findViewById(R.id.fl_comment_container);
        mEquipmentNotQualifiedBtn = (ImageButton) rootView.findViewById(R.id.imgbtn_equipment);
        mCustomDisagreeBtn = (ImageButton) rootView.findViewById(R.id.imgbtn_custom_disagree);
        mMonitorAbsenceBtn = (ImageButton) rootView.findViewById(R.id.imgbtn_monitor_absence);
        mUnStandardBtn = (ImageButton) rootView.findViewById(R.id.imgbtn_not_standard);
        mNextStepBtn = (Button) rootView.findViewById(R.id.btn_next_step);
        mEquipment = (TextView) rootView.findViewById(R.id.tv_equipment);
        mCustomerDisAgree = (TextView) rootView.findViewById(R.id.tv_custom_disagree);
        mMonitorAbsence = (TextView) rootView.findViewById(R.id.tv_monitor_absence);
        mInterface = (TextView) rootView.findViewById(R.id.tv_interface_not_standard);
        mEquipmentNotQualifiedBtn.setOnClickListener(this);
        mCustomDisagreeBtn.setOnClickListener(this);
        mMonitorAbsenceBtn.setOnClickListener(this);
        mUnStandardBtn.setOnClickListener(this);
        mNextStepBtn.setOnClickListener(this);
        mCommentFragment = CommentFragment.getInstance(new CommentConfig());
        mCommentPresenter = new CommentPresenter(mCommentFragment, null);
        setFragment();
        mPresenter = new UnqualifiedPresenter(this);
    }

    private void setFragment() {
        FragmentManager manager = mContext.getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.fl_comment_container, mCommentFragment, CommentFragment.TAG);
        ft.commit();
    }


    @Override
    protected void initData() {
        mPreCheckForm = (SHPrecheckForm) getArguments().getSerializable(UnqualifiedActivity.UNQUALIFIED_FORM);
        for(CheckItem item : mPreCheckForm.getCheckItems()){
            if("equipment_whether_qualification".equals(item.getItemId())){
                mEquipment.setText(item.getStandard());
                continue;
            }
            if("acceptance_whether_check".equals(item.getItemId())){
                mCustomerDisAgree.setText(item.getStandard());
                continue;
            }
            if("monitor_whether_presence".equals(item.getItemId())){
                mMonitorAbsence.setText(item.getStandard());
                continue;
            }
            if("interface_wether_standard".equals(item.getItemId())){
                mInterface.setText(item.getStandard());
                continue;
            }
        }
        initToolbar(mPreCheckForm.getTitle() + "表");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgbtn_equipment) {
            mEquipmentNotQualifiedBtn.setSelected(!mEquipmentNotQualifiedBtn.isSelected());
            mPresenter.setCheckIndex(mPreCheckForm, mEquipmentNotQualifiedBtn.isSelected(), R.id.imgbtn_equipment);
        } else if (v.getId() == R.id.imgbtn_custom_disagree) {
            mCustomDisagreeBtn.setSelected(!mCustomDisagreeBtn.isSelected());
            mPresenter.setCheckIndex(mPreCheckForm, mEquipmentNotQualifiedBtn.isSelected(), R.id.imgbtn_custom_disagree);
        } else if (v.getId() == R.id.imgbtn_monitor_absence) {
            mMonitorAbsenceBtn.setSelected(!mMonitorAbsenceBtn.isSelected());
            mPresenter.setCheckIndex(mPreCheckForm, mEquipmentNotQualifiedBtn.isSelected(), R.id.imgbtn_monitor_absence);
        } else if (v.getId() == R.id.imgbtn_not_standard) {
            mUnStandardBtn.setSelected(!mUnStandardBtn.isSelected());
            mPresenter.setCheckIndex(mPreCheckForm, mEquipmentNotQualifiedBtn.isSelected(), R.id.imgbtn_not_standard);
        } else if (v.getId() == R.id.btn_next_step) {
            if (!(mEquipmentNotQualifiedBtn.isSelected()
                    || mCustomDisagreeBtn.isSelected()
                    || mMonitorAbsenceBtn.isSelected()
                    || mUnStandardBtn.isSelected())) {
                ToastUtils.showShort(mContext, "必选项至少选一个");
                return;
            }
            mPicture = (ArrayList<ImageInfo>) mCommentPresenter.getImageData();
            mCommentContent = mCommentPresenter.getCommentContent();
            mAudioPath = mCommentPresenter.getAudioPath();
            Bundle params = new Bundle();
            params.putSerializable(UnqualifiedActivity.UNQUALIFIED_FORM, mPreCheckForm);
            params.putString(UnqualifiedActivity.UNQUALIFIED_COMMENT, mCommentContent);
            params.putString(UnqualifiedActivity.UNQUALIFIED_AUDIO, mAudioPath);
            params.putParcelableArrayList(UnqualifiedActivity.UNQUALIFIED_IMAGES, mPicture);
            mFragment = UnqualifiedSubmitFragment.newInstance(params);
            mContext.getSupportFragmentManager().beginTransaction()
                    .hide(mContext.getSupportFragmentManager().findFragmentByTag(SHFormConstant.FragmentTag.FORM_UNQUALIFIED_EDIT))
                    .add(R.id.fl_activity_fragment_container, mFragment, SHFormConstant.FragmentTag.FORM_UNQUALIFIED_COMMIT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void initToolbar(String title) {
        ActionBar actionBar = mContext.getSupportActionBar();
        actionBar.setTitle(title);
    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitError() {

    }
}
