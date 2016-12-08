package com.autodesk.shejijia.shared.components.message.activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.appglobal.ConstructionConstants;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.message.adapter.ProjectMessageCenterAdapter;
import com.autodesk.shejijia.shared.components.message.entity.MessageInfo;
import com.autodesk.shejijia.shared.components.message.presenter.ProjectMessageCenterPresenter;
import com.autodesk.shejijia.shared.components.message.presenter.ProjectMessageCenterPresenterImpl;
import com.autodesk.shejijia.shared.framework.activity.BaseActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchongbin on 2016/12/5.
 */
public class ProjectMessageCenterActivity extends BaseActivity implements ProjectMessageCenterPresenter.View,ProjectMessageCenterAdapter.HistoricalRecordstListener {

    private ProjectMessageCenterPresenter.Presenter mProjectMessageCenterPresenter;
    private List<MessageInfo.DataBean> mData;
    private long mProjectId;
    private boolean isUnrea;
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
        isUnrea = getIntent().getBooleanExtra(ConstructionConstants.UNREAD,false);
        mProjectId = getIntent().getLongExtra(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,0);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(UIUtils.getString(R.string.update_priject_details));
        mData = new ArrayList<>();
        mProjectMessageCenterPresenter = new ProjectMessageCenterPresenterImpl(getApplicationContext(),this);
        mProjectMessageCenterAdapter = new ProjectMessageCenterAdapter(mData,isUnrea,R.layout.item_messagecenter);
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
        mProjectMessageCenterPresenter.listMessageCenterInfo(getRequestBundle(),TAG);
    }
    private Bundle getRequestBundle(){
        Bundle requestParams = new Bundle();
        requestParams.putLong(ConstructionConstants.BUNDLE_KEY_PROJECT_ID,mProjectId);//"1642677"
        requestParams.putInt(ConstructionConstants.OFFSET,0);
        requestParams.putBoolean(ConstructionConstants.UNREAD,isUnrea);
        requestParams.putInt(ConstructionConstants.LIMIT,20);
        return requestParams;
    }
    @Override
    protected void initListener() {
        super.initListener();
        ProjectMessageCenterAdapter.setHistoricalRecordstListener(this);
    }
    @Override
    public void updateProjectDetailsView(MessageInfo messageInfo) {
        if(messageInfo.getData() != null) {
            mData.addAll(messageInfo.getData());
        }
        mProjectMessageCenterAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNetError(ResponseError error) {}

    @Override
    public void showError(String errorMsg) {}

    @Override
    public void showLoading() {}

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
