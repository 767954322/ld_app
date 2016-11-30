package com.autodesk.shejijia.shared.components.message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.RefreshLoadMoreListener;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.SwipeRecyclerView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.message.entity.Message;
import com.autodesk.shejijia.shared.components.message.entity.MessageItem;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */
public class ProjectMessageCenterActivity extends BaseActivity implements ProjectMessageCenterContract.View,
        ProjectMessageCenterAdapter.HistoricalRecordstListener, RefreshLoadMoreListener {
    private ProjectMessageCenterContract.Presenter mProjectMessageCenterPresenter;
    private List<MessageItem> mMessageItems;
    private long mProjectId;
    private boolean mIsUnread;
    private String mThreadId;
    private SwipeRecyclerView mRvProjectMessagCenterView;
    private ProjectMessageCenterAdapter mProjectMessageCenterAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_message_center;
    }

    @Override
    protected void initView() {
        mRvProjectMessagCenterView = (SwipeRecyclerView) findViewById(R.id.rv_project_message_center_view);
        initRecyclerView();
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mIsUnread = intent.getBooleanExtra(ConstructionConstants.BUNDLE_KEY_UNREAD, false);
        mProjectId = intent.getLongExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID, 0);
        mThreadId = intent.getStringExtra(ConstructionConstants.BUNDLE_KEY_THREAD_ID);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(UIUtils.getString(R.string.update_project_details));
        mProjectMessageCenterPresenter = new ProjectMessageCenterPresenter(getApplicationContext(), getClass().getSimpleName(), this);
    }

    private void initRecyclerView() {
        mRvProjectMessagCenterView.getRecyclerView().setLayoutManager(new LinearLayoutManager(this));
        mRvProjectMessagCenterView.getRecyclerView().setHasFixedSize(true);
        mRvProjectMessagCenterView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mRvProjectMessagCenterView.getSwipeRefreshLayout()
                .setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        mMessageItems = new ArrayList<>();
        mProjectMessageCenterAdapter = new ProjectMessageCenterAdapter(this, mMessageItems, mIsUnread, R.layout.item_messagecenter);
        mRvProjectMessagCenterView.setAdapter(mProjectMessageCenterAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRvProjectMessagCenterView.setRefreshLoadMoreListener(this);
        mRvProjectMessagCenterView.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        mProjectMessageCenterPresenter.refreshProjectMessages(mProjectId, mIsUnread);
    }

    @Override
    public void onLoadMore() {
        mProjectMessageCenterPresenter.loadMoreProjectMessages(mProjectId, mIsUnread);
    }

    @Override
    public void refreshProjectMessagesView(Message messageInfo) {
        mRvProjectMessagCenterView.complete();
        mProjectMessageCenterPresenter.changeUnreadMsgState(mThreadId);
        if (messageInfo.getMessageItemList() != null && messageInfo.getMessageItemList().size() > 0) {
            mRvProjectMessagCenterView.scrollToPosition(0);
            mProjectMessageCenterAdapter.notifyDataForRecyclerView(messageInfo.getMessageItemList(), mIsUnread, messageInfo.getOffset());
        }
    }

    @Override
    public void loadMoreProjectMessagesView(Message messageInfo) {
        mRvProjectMessagCenterView.complete();
        mProjectMessageCenterAdapter.notifyDataForRecyclerView(messageInfo.getMessageItemList(), mIsUnread, messageInfo.getOffset());
        if (messageInfo.getMessageItemList() != null && messageInfo.getMessageItemList().size() <= 0) {
            mRvProjectMessagCenterView.onNoMore(null);
        }

    }

    @Override
    public void changeUnreadMsgStateView() {
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void showNetError(final ResponseError error) {


    }

    @Override
    public void showError(String errorMsg) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void onHistoricalRecordstClick() {
        mIsUnread = false;
        mRvProjectMessagCenterView.setRefreshing(true);
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
