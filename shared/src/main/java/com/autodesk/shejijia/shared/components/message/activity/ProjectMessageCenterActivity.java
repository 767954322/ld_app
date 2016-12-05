package com.autodesk.shejijia.shared.components.message.activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.message.adapter.ProjectMessageCenterAdapter;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
/**
 * Created by luchongbin on 2016/12/5.
 */
public class ProjectMessageCenterActivity extends BaseActivity {
    private RecyclerView mRvProjectMessagCenterView;
    private ProjectMessageCenterAdapter mProjectMessageCenterAdapter;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_message_center;
    }
    @Override
    protected void initView() {
        mRvProjectMessagCenterView = (RecyclerView)findViewById(R.id.rv_project_message_center_view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        mProjectMessageCenterAdapter = new ProjectMessageCenterAdapter();
        mRvProjectMessagCenterView.setAdapter(mProjectMessageCenterAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
