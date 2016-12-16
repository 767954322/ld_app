package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.issue.ui.activity.IssueAddListActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-20
 * @file DesignerConstructionFragment.java  .
 * @brief 施工fragment。
 */
public class DesignerConstructionFragment extends BaseFragment {

    private TextView mTvMsg;
    private Button bt_linshi_rukou;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_constuction;
    }

    @Override
    protected void initView() {
        mTvMsg = (TextView) rootView.findViewById(R.id.tv_empty_message);
        bt_linshi_rukou = (Button) rootView.findViewById(R.id.bt_linshi_rukou);

    }

    @Override
    protected void initData() {
        mTvMsg.setText("您还没有施工项目哦！");
        /**
         * 施工给居然演示临时入口，请勿删除，下周恢复
         */
        bt_linshi_rukou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInfo projectInfo = GsonUtil.jsonToBean(LinShiClass.projectInfo.replace("\\", ""), ProjectInfo.class);
                Intent intent1 = new Intent(activity, IssueAddListActivity.class);
                intent1.putExtra("projectinfo", projectInfo);
                startActivity(intent1);
            }
        });
    }
}
