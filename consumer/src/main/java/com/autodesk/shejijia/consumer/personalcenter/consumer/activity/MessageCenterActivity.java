package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.MessageCenterAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.MessageCenter;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-6-12
 * @file MessageCenterActivity.java  .
 * @brief 消息中心 .
 */
public class MessageCenterActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {


    private MemberEntity mMemberEntity;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_messagecenter;
    }

    @Override
    protected void initView() {
        mIbnBack = (ImageButton) findViewById(R.id.nav_left_imageButton);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mListView = (PullListView) findViewById(R.id.message_center_list_view);
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mIvEmpty = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));

        mListView.addFooterView(mFooterView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mContext = this;
        setTitleForNavbar(UIUtils.getString(R.string.title_messagecenter));

        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            ifIsDesiner = true;
        } else {
            ifIsDesiner = false;
        }

        if (messageCenterAdapter == null) {
            messageCenterAdapter = new MessageCenterAdapter(mContext, mCasesEntities, ifIsDesiner);
        }

        mListView.setAdapter(messageCenterAdapter);
        mPullToRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIbnBack.setOnClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_left_imageButton:
                finish();
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        offset = 0;
        showListView();
        getMessageData(REFRESH_STATUS);

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        getMessageData(LOADMORE_STATUS);

    }

    //是否显示ListView.
    private void showListView() {
        mPullToRefreshLayout.setVisibility(View.VISIBLE);
        mRlEmpty.setVisibility(View.GONE);
    }

    //获取消息数据
    public void getMessageData(final String state) {

        MPServerHttpManager.getInstance().getMessageCentermsgs(offset, limit, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                if (state.equals(REFRESH_STATUS)) {
                    mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                } else {
                    mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                }

                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, mContext,
                        AlertView.Style.Alert, null).show();
                hideFooterView(mCasesEntities);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                MessageCenter messageCenter = GsonUtil.jsonToBean(jsonString, MessageCenter.class);
                updateViewFromData(messageCenter.getMessages(), state);
            }
        });

    }

    private void updateViewFromData(List<MessageCenter.Message> messages, String state) {
        switch (state) {
            case REFRESH_STATUS:
                offset = 10;
                mCasesEntities.clear();
                break;
            case LOADMORE_STATUS:
                offset += 10;
                break;
        }

        mCasesEntities.addAll(messages);
        hideFooterView(mCasesEntities);
        messageCenterAdapter.notifyDataSetChanged();
        if (state.equals(REFRESH_STATUS)) {
            mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        } else {
            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    /**
     * 是否隐藏底部布局
     *
     * @param list 数据集合
     */
    private void hideFooterView(List<MessageCenter.Message> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
        mIvEmpty.setImageBitmap(bmp);
        mTvEmptyMessage.setText(R.string.no_designer_case);
        WindowManager wm = (WindowManager) MessageCenterActivity.this.getSystemService(MessageCenterActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams layoutParams = mRlEmpty.getLayoutParams();
        mRlEmpty.getLayoutParams();
        layoutParams.height = height - 10;
        mRlEmpty.setLayoutParams(layoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.no_designer_case));
    }

    private Context mContext;
    private View mFooterView;
    private RelativeLayout mRlEmpty;
    private boolean ifIsDesiner = true;

    private final String REFRESH_STATUS = "refresh";
    private final String LOADMORE_STATUS = "loadmore";
    private int offset = 0;
    private int limit = 10;
    private int screenWidth;
    private int screenHeight;
    private TextView mTvEmptyMessage;
    private ImageView mIvEmpty;
    private ImageButton mIbnBack;

    private PullListView mListView;
    private MessageCenterAdapter messageCenterAdapter;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<MessageCenter.Message> mCasesEntities = new ArrayList<MessageCenter.Message>();

}
