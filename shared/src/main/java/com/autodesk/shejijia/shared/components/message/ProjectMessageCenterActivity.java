package com.autodesk.shejijia.shared.components.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.components.message.entity.MessageItemBean;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */
public class ProjectMessageCenterActivity extends BaseActivity implements ProjectMessageCenterContract.View,ProjectMessageCenterAdapter.HistoricalRecordstListener {

    private ProjectMessageCenterContract.Presenter mProjectMessageCenterPresenter;
    private List<MessageItemBean> messageItemBeans;
    private long mProjectId;
    private boolean mIsUnread;
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
        mIsUnread = getIntent().getBooleanExtra(ConstructionConstants.UNREAD,false);
        mProjectId = getIntent().getLongExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,0);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(UIUtils.getString(R.string.update_priject_details));
        messageItemBeans = new ArrayList<>();
        mProjectMessageCenterPresenter = new ProjectMessageCenterPresenter(getApplicationContext(),this);
        mProjectMessageCenterAdapter = new ProjectMessageCenterAdapter(messageItemBeans,mIsUnread,R.layout.item_messagecenter);
        initRecyclerView();
        getListMessageCenterInfo();
    }

    private void initRecyclerView(){
        mRvProjectMessagCenterView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvProjectMessagCenterView.setLayoutManager(layoutManager);
        mRvProjectMessagCenterView.setAdapter(mProjectMessageCenterAdapter);
    }
    private void getListMessageCenterInfo(){
        mProjectMessageCenterPresenter.getMessageCenterInfo(getRequestBundle(),TAG);
    }
    private Bundle getRequestBundle(){
        Bundle requestParams = new Bundle();
        requestParams.putLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,mProjectId);//"1642677"
        requestParams.putInt(ConstructionConstants.OFFSET,0);
        requestParams.putBoolean(ConstructionConstants.UNREAD,mIsUnread);
        requestParams.putInt(ConstructionConstants.LIMIT,20);
        return requestParams;
    }
    @Override
    protected void initListener() {
        super.initListener();
        ProjectMessageCenterAdapter.setHistoricalRecordstListener(this);
    }
    @Override
    public void updateProjectMessageView(MessageInfo messageInfo) {
        if(messageInfo.getMessageItemBean() != null) {
            mProjectMessageCenterAdapter.notifyDataForRecyclerView(messageInfo.getMessageItemBean(),mIsUnread);
            mIsUnread = false;
            setResult(RESULT_OK,new Intent());
        }
    }

    @Override
    public void showNetError(ResponseError error) {}

    @Override
    public void showError(String errorMsg) {}

    @Override
    public void showLoading() {}

    @Override
    public void updateUnreadCountView(List list){}

    @Override
    public void hideLoading() {}
    @Override
    public void onHistoricalRecordstClick() {
        getListMessageCenterInfo();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
