package com.autodesk.shejijia.shared.components.issue.ui.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.issue.common.entity.IssueFllowBean;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueFlowPop;
import com.autodesk.shejijia.shared.components.issue.common.view.IssueStylePop;
import com.autodesk.shejijia.shared.components.issue.contract.PopItemClickContract;
import com.autodesk.shejijia.shared.components.issue.ui.activity.AddIssueDescriptionActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.R.array.add_issue_type_list;

/**
 * Created by Menghao.Gu on 2016/12/6.
 */

public class AddIssueListFragment extends BaseFragment implements View.OnClickListener, PopItemClickContract {

    private RelativeLayout mIssueAll;
    private RelativeLayout mIssueStyle;
    private RelativeLayout mIssueDescription;
    private RelativeLayout mIssueFllow;

    private IssueStylePop issueStylePopWin;
    private IssueFlowPop issueFllowPopWin;
    private TextView mIssueStyleContent;

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

        mIssueStyleContent = (TextView) rootView.findViewById(R.id.tx_issuetype);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        super.initListener();

        mIssueStyle.setOnClickListener(this);
        mIssueDescription.setOnClickListener(this);
        mIssueFllow.setOnClickListener(this);

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
            Intent intent_description = new Intent(activity, AddIssueDescriptionActivity.class);
            activity.startActivityForResult(intent_description, 0);

        } else if (i == R.id.rl_issuefllow) {
            //TODO 构建假数据，打通获取角色信息
            List<IssueFllowBean> list_fllow = initRoleData();
            if (issueFllowPopWin == null) {
                issueFllowPopWin = new IssueFlowPop(list_fllow, activity.getBaseContext(), this, this);
            }
            issueFllowPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            issueFllowPopWin.showAtLocation(mIssueAll, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

        } else if (i == R.id.iv_close_style_pop) {
            dismissPopwindow();

        }
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
    public void onPopItemClickListener(View view, int position) {
        mIssueStyleContent.setText(activity.getResources().getStringArray(add_issue_type_list)[position]);
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
}
