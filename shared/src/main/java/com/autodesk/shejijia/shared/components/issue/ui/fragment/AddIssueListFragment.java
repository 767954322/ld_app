package com.autodesk.shejijia.shared.components.issue.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.TimePickerView;
import com.autodesk.shejijia.shared.components.common.utility.ToastUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueDescription;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueFllowBean;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueFlowPop;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueStylePop;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;
import com.autodesk.shejijia.shared.components.issue.ui.activity.AddIssueDescriptionActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.autodesk.shejijia.shared.R.array.add_issue_type_list;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class AddIssueListFragment extends BaseFragment implements View.OnClickListener, PopItemClickContract, CompoundButton.OnCheckedChangeListener {

    private RelativeLayout mIssueAll;
    private RelativeLayout mIssueStyle;
    private RelativeLayout mIssueDescription;
    private RelativeLayout mIssueFllow;
    private RelativeLayout mIssueReply;

    private IssueStylePop issueStylePopWin;
    private IssueFlowPop issueFllowPopWin;
    private TextView mIssueStyleContent;
    private TextView mIssueFllowContent;
    private TextView mIssueReplyContent;
    private TextView mIssueDescriptionContent;
    private List<IssueFllowBean> list_fllow;
    private Switch mIssueSwitchNotify;
    private TimePickerView pvTime;
    private SimpleDateFormat dateFormat;
    private IssueDescription mDescriptionBean;


    public static AddIssueListFragment getInstance() {
        AddIssueListFragment fragment = new AddIssueListFragment();
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
        mIssueDescription = (RelativeLayout) rootView.findViewById(R.id.rl_issuedescrip);
        mIssueFllow = (RelativeLayout) rootView.findViewById(R.id.rl_issuefllow);
        mIssueReply = (RelativeLayout) rootView.findViewById(R.id.rl_issuereply);

        mIssueStyleContent = (TextView) rootView.findViewById(R.id.tx_issuetype);
        mIssueFllowContent = (TextView) rootView.findViewById(R.id.tx_issuefllow);
        mIssueReplyContent = (TextView) rootView.findViewById(R.id.tx_issuereply);
        mIssueDescriptionContent = (TextView) rootView.findViewById(R.id.tx_issuedescrip);
        mIssueSwitchNotify = (Switch) rootView.findViewById(R.id.sw_notity_customer);
    }

    @Override
    protected void initData() {

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        initTimePick();

    }

    @Override
    protected void initListener() {
        super.initListener();
        mIssueStyle.setOnClickListener(this);
        mIssueDescription.setOnClickListener(this);
        mIssueFllow.setOnClickListener(this);
        mIssueSwitchNotify.setOnCheckedChangeListener(this);
        mIssueReply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_issuetype) {
            if (issueStylePopWin == null) {
                issueStylePopWin = new IssueStylePop(activity.getBaseContext(), this, this);
            }
            issueStylePopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            issueStylePopWin.showAtLocation(mIssueAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } else if (i == R.id.rl_issuedescrip) {
            Intent intent_description = new Intent(getActivity(), AddIssueDescriptionActivity.class);
            this.startActivityForResult(intent_description, ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_REQUEST_CODE);

        } else if (i == R.id.rl_issuefllow) {
            //TODO 构建假数据，打通获取角色信息
            list_fllow = initRoleData();
            if (issueFllowPopWin == null) {
                issueFllowPopWin = new IssueFlowPop(list_fllow, activity.getBaseContext(), this);
            }
            issueFllowPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            issueFllowPopWin.showAtLocation(mIssueAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else if (i == R.id.rl_issuereply) {
            pvTime.show();
        }
    }

    //通知客户开关按钮监听
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ToastUtils.showLong(activity, "开");
        } else {
            ToastUtils.showLong(activity, "关");
        }
    }

    @Override
    public void onPopItemClickListener(View view, int position) {
        if (view.getId() == R.id.rl_issue_fllow_person) {
            IssueFllowBean issueFllowBean = list_fllow.get(position);
            mIssueFllowContent.setText(issueFllowBean.getmIssueFllowRole() + issueFllowBean.getmIssueFllowName() + "跟踪");
        } else {
            mIssueStyleContent.setText(activity.getResources().getStringArray(add_issue_type_list)[position]);
        }
        dismissPopwindow();
    }

    private void dismissPopwindow() {
        if (null != issueStylePopWin) {
            issueStylePopWin.dismiss();
        }
        if (null != issueFllowPopWin) {
            issueFllowPopWin.dismiss();
        }
    }

    //初始化时间控件
    private void initTimePick() {
        pvTime = new TimePickerView(activity, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setRange(1965, 2100);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(false);
        pvTime.setTitle(UIUtils.getString(R.string.pop_issuereply_title));
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                String mDate = dateFormat.format(date);
                mDate = mDate.length() > 8 ? mDate.substring(0, mDate.length() - 8) : mDate;
                mIssueReplyContent.setText(mDate);
            }
        });
    }

    private List<IssueFllowBean> initRoleData() {
        List<IssueFllowBean> list_fllow = new ArrayList<>();
        String issueImgPath = "http://img3.imgtn.bdimg.com/it/u=214931719,1608091472&fm=21&gp=0.jpg";
        String issueRole = "设计师";
        String issueName = "网络名称";
        for (int i = 0; i < 4; i++) {
            IssueFllowBean mIssueFllowBean = new IssueFllowBean(issueImgPath, issueRole + i, issueName + i);
            list_fllow.add(mIssueFllowBean);
        }
        return list_fllow;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == activity.RESULT_OK && null != data) {
            if (requestCode == ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_REQUEST_CODE && data != null) {
                IssueDescription mDescriptionBean = (IssueDescription) data.getSerializableExtra(ConstructionConstants.IssueTracking.ADD_ISSUE_DESCRIPTION_RESULT_KEY);
                Message message = new Message();
                message.obj = mDescriptionBean;
                handler.sendMessage(message);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDescriptionBean = (IssueDescription) msg.obj;
            onShowIssueDescription();
        }

        private void onShowIssueDescription() {
            if (mDescriptionBean != null && !TextUtils.isEmpty(mDescriptionBean.getmDescription())) {
                mIssueDescriptionContent.setText(mDescriptionBean.getmDescription());
            } else {
                mIssueDescriptionContent.setText(UIUtils.getString(R.string.add_issuelist_descrip));
            }
        }
    };

}
