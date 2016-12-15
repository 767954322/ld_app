package com.autodesk.shejijia.shared.components.issue.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.entity.microbean.Member;
import com.autodesk.shejijia.shared.components.common.uielements.commentview.model.entity.ImageInfo;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerViewIssue;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.issue.common.tools.IssueRoleUtils;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueFlowPop;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueStylePop;
import com.autodesk.shejijia.shared.components.issue.contract.IssueAddContract;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;
import com.autodesk.shejijia.shared.components.issue.presenter.IssueAddPresenter;
import com.autodesk.shejijia.shared.components.issue.ui.activity.IssueAddDescriptionActivity;
import com.autodesk.shejijia.shared.components.issue.ui.adapter.IssueAddListImageAdapter;
import com.autodesk.shejijia.shared.components.nodeprocess.data.ProjectRepository;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static com.autodesk.shejijia.shared.R.array.add_issue_type_list;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class IssueAddListFragment extends BaseFragment implements View.OnClickListener, PopItemClickContract, CompoundButton.OnCheckedChangeListener, IssueAddContract.View {


    private int tomorrowYear;
    private int tomorrowMonth;
    private int tomorrowDay;
    private String mTomorrow;

    public static IssueAddListFragment getInstance() {
        IssueAddListFragment fragment = new IssueAddListFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_issue_list;
    }

    @Override
    protected void initView() {
        mIssueAll = (RelativeLayout) rootView.findViewById(R.id.rl_issue_all);
        mIssueStyle = (RelativeLayout) rootView.findViewById(R.id.rl_issuetype);
        mIssueDescription = (RelativeLayout) rootView.findViewById(R.id.rl_layout_descrition);
        mIssueFllow = (RelativeLayout) rootView.findViewById(R.id.rl_issuefllow);
        mIssueReply = (RelativeLayout) rootView.findViewById(R.id.rl_issuereply);
        mIssueAudio = (LinearLayout) rootView.findViewById(R.id.ll_audio_play_container);

        mIssueStyleContent = (TextView) rootView.findViewById(R.id.tx_issuetype);
        mIssueFllowContent = (TextView) rootView.findViewById(R.id.tx_issuefllow);
        mIssueReplyContent = (TextView) rootView.findViewById(R.id.tx_issuereply);
        mIssueDescriptionContent = (TextView) rootView.findViewById(R.id.tx_issuedescrip);
        mIssueImagesList = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mIssueSwitchNotify = (Switch) rootView.findViewById(R.id.sw_notity_customer);
    }

    @Override
    protected void initData() {
        mIssueAddPresenter = new IssueAddPresenter(this, activity);
        mProjectInfo = ProjectRepository.getInstance().getActiveProject();
        if (mProjectInfo != null) {
            mMapMember = IssueRoleUtils.getInstance().getMembersFromProject(mProjectInfo);
            mListMember = IssueRoleUtils.getInstance().getFollowListByProject(mProjectInfo);
        }
        initTimePick();
        initImageList();
    }

    private void initImageList() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mIssueImagesList.setLayoutManager(linearLayoutManager);
        mIssueImagesList.setHasFixedSize(true);
        mIssueImagesList.setItemAnimator(new DefaultItemAnimator());
        //init recyclerView adapter
        mIssueImageAdapter = new IssueAddListImageAdapter(null, getContext(), R.layout.item_addissue_image);
        mIssueImagesList.setAdapter(mIssueImageAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIssueStyle.setOnClickListener(this);
        mIssueFllow.setOnClickListener(this);
        mIssueSwitchNotify.setOnCheckedChangeListener(this);
        mIssueReply.setOnClickListener(this);
        mIssueDescription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_issuetype) {

            if (mIssueStylePopWin == null) {
                mIssueStylePopWin = new IssueStylePop(activity.getBaseContext(), this, this);
            }
            mIssueStylePopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mIssueStylePopWin.showAtLocation(mIssueAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (i == R.id.rl_layout_descrition) {

            Intent intent_description = new Intent(getActivity(), IssueAddDescriptionActivity.class);
            this.startActivityForResult(intent_description, ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_REQUEST_CODE);
        } else if (i == R.id.rl_issuefllow) {

            if (mIssueFllowPopWin == null) {
                mIssueFllowPopWin = new IssueFlowPop(mListMember, activity.getBaseContext(), this);
            }
            mIssueFllowPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            mIssueFllowPopWin.showAtLocation(mIssueAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (i == R.id.rl_issuereply) {

            mPvTime.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mNotifyCustormer = 1;
        } else {
            mNotifyCustormer = 0;
        }
    }

    @Override
    public void onPopItemClickListener(View view, int position) {
        if (view.getId() == R.id.rl_issue_fllow_person) {
            mFollowMember = mListMember.get(position);
            String chinaRole = IssueRoleUtils.getInstance().getChiRoleByEngRole(mFollowMember.getRole());
            mIssueFllowContent.setText(chinaRole + mFollowMember.getProfile().getName() + UIUtils.getString(R.string.Fragment_addissue_fllow_end));
        } else {
            mIssueType = mArrayType[position];
            String strType = activity.getResources().getStringArray(add_issue_type_list)[position];
            mIssueStyleContent.setText(strType);
            //设置跟进人员
            mFollowMember = IssueRoleUtils.getInstance().getMemberByIssueType(mMapMember, strType);
            String followRole = IssueRoleUtils.getInstance().getChiRoleByIssueType(strType);
            mIssueFllowContent.setText(followRole + mFollowMember.getProfile().getName().trim() + UIUtils.getString(R.string.Fragment_addissue_fllow_end));
        }
        dismissPopwindow();
    }

    private void dismissPopwindow() {
        if (null != mIssueStylePopWin) {
            mIssueStylePopWin.dismiss();
        }
        if (null != mIssueFllowPopWin) {
            mIssueFllowPopWin.dismiss();
        }
    }

    /**
     * 添加问题回调
     *
     * @param status
     */
    @Override
    public void onShowStatus(boolean status) {

        if (status) {
            ToastUtils.showLong(activity, "上传成功");
        } else {
            ToastUtils.showLong(activity, "上传失败");
        }

    }

    /**
     * 初始化时间控件
     */
    private void initTimePick() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);
        tomorrowYear = calendar.get(Calendar.YEAR);
        tomorrowMonth = calendar.get(Calendar.MONTH);
        tomorrowDay = calendar.get(Calendar.DAY_OF_MONTH);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mPvTime = new TimePickerViewIssue(activity, TimePickerViewIssue.Type.YEAR_MONTH_DAY);
        mPvTime.setRange(1965, 2100);
        mPvTime.setTime(tomorrowYear, tomorrowMonth, tomorrowDay);
        mPvTime.setCyclic(false);
        mPvTime.setCancelable(false);
        mPvTime.setTitle(UIUtils.getString(R.string.pop_issuereply_title));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        mTomorrow = String.valueOf(cal.getTime().getTime());
        mTime = mTomorrow;
        mPvTime.setOnTimeSelectListener(new TimePickerViewIssue.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                mDate = mDateFormat.format(date);
                mDate = mDate.length() > 8 ? mDate.substring(0, mDate.length() - 8) : mDate;
                String[] time = mDate.split("-");
                if (time.length == 3) {
                    if (time[0].trim().equals(String.valueOf(tomorrowYear)) && time[1].trim().equals(String.valueOf(tomorrowMonth + 1)) && time[2].trim().equals(String.valueOf(tomorrowDay))) {
                        mTime = mTomorrow;
                        mIssueReplyContent.setText(UIUtils.getString(R.string.add_issuelist_reply));
                    } else {
                        mTime = String.valueOf(date.getTime());
                        mIssueReplyContent.setText(mDate);
                    }
                }
            }
        });
    }

    /**
     * 发送添加问题
     */
    public void sendIssueTracking() {
        if (mIssueType != -1 || mFollowMember != null || !TextUtils.isEmpty(mTime)) {
            if (!TextUtils.isEmpty(mDescriptionContent) || !TextUtils.isEmpty(mDescriptionVoice)) {
                mIssueAddPresenter.putIssueTracking(mNotifyCustormer, mProjectInfo, mIssueType, mFollowMember, mTime,
                        mDescriptionContent, mDescriptionVoice, mDescriptionImage);
            }
        } else {
            //TODO 会删除掉（逻辑会修改，暂时给个提示）
            ToastUtils.showLong(activity, "请将信息填写完整");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK && null != data) {
            if (requestCode == ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_REQUEST_CODE && data != null) {
                mDescriptionContent = data.getStringExtra(ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_CONTENT);
                mDescriptionVoice = data.getStringExtra(ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_VOICE);
                mDescriptionImage = data.getParcelableArrayListExtra(ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_IMAGES);
                handler.sendEmptyMessage(0);

            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onShowIssueDescription();
        }

        private void onShowIssueDescription() {
            if (!TextUtils.isEmpty(mDescriptionContent)) {
                mIssueDescriptionContent.setText(mDescriptionContent);
            } else {
                mIssueDescriptionContent.setText(UIUtils.getString(R.string.add_issuelist_descrip));
            }
            if (!TextUtils.isEmpty(mDescriptionVoice)) {
                mIssueAudio.setVisibility(View.VISIBLE);
            } else {
                mIssueAudio.setVisibility(View.GONE);
            }
            if (mDescriptionImage != null && mDescriptionImage.size() > 0) {
                mIssueImageAdapter.reflushList(mDescriptionImage);
            } else {
                mIssueImageAdapter.reflushList(null);
            }
        }
    };


    private RelativeLayout mIssueAll;
    private RelativeLayout mIssueStyle;
    private RelativeLayout mIssueDescription;
    private RelativeLayout mIssueFllow;
    private RelativeLayout mIssueReply;
    private LinearLayout mIssueAudio;

    private IssueStylePop mIssueStylePopWin;
    private IssueFlowPop mIssueFllowPopWin;
    private TextView mIssueStyleContent;
    private TextView mIssueFllowContent;
    private TextView mIssueReplyContent;
    private TextView mIssueDescriptionContent;
    private RecyclerView mIssueImagesList;

    private IssueAddListImageAdapter mIssueImageAdapter;
    private ArrayList<Member> mListMember;
    private Map<String, Member> mMapMember;
    private Switch mIssueSwitchNotify;
    private TimePickerViewIssue mPvTime;
    private SimpleDateFormat mDateFormat;
    private IssueAddContract.Presenter mIssueAddPresenter;

    private ProjectInfo mProjectInfo;
    private Member mFollowMember;
    private String mDate;
    private String mTime;
    private String mDescriptionContent;
    private String mDescriptionVoice;
    private ArrayList<ImageInfo> mDescriptionImage;


    private int mIssueType = -1;
    private int mNotifyCustormer = 0;
    private int[] mArrayType = new int[]{11, 10, 12, 13, 15};

}
