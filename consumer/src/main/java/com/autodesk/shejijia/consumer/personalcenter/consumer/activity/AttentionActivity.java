package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;


import android.os.Bundle;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.AttentionAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AttentionEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
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
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/8/1 0029 17:32 .
 * @file AttentionActivity  .
 * @brief 关注列表 .
 */
public class AttentionActivity extends NavigationBarActivity implements AttentionAdapter.OnItemCancelAttentionClickListener, PullToRefreshLayout.OnRefreshListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        super.initView();
        lv_attention = (PullListView) findViewById(R.id.lv_attention);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 第一次进入自动刷新
        if (isFirstIn) {
            //changeState
            mPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_attention));

        attentionAdapter = new AttentionAdapter(this, attentionList);
        lv_attention.setAdapter(attentionAdapter);

        memberEntity = AdskApplication.getInstance().getMemberEntity();
        acs_member_id = memberEntity.getAcs_member_id();


    }

    @Override
    protected void initListener() {
        super.initListener();
        attentionAdapter.setOnItemCancelAttentionClick(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 取消关注按钮点击事件
     *
     * @param position 位置
     */
    @Override
    public void OnItemCancelAttentionClick(int position) {

        String member_id = attentionList.get(position).getMember_id();
        String hs_uid = attentionList.get(position).getHs_uid();

        CustomProgress.show(this, "", false, null);

        String followed_member_id = member_id;
        String followed_member_uid = hs_uid;

        unFollowedDesigner(acs_member_id, followed_member_id, followed_member_uid);

    }

    /**
     * 关注列表
     *
     * @param member_id 用户id
     * @param limit
     * @param offset
     */
    public void attentionListData(String member_id, int limit, int offset, final int state) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                String userInfo = GsonUtil.jsonToString(jsonObject);
                attentionEntity = GsonUtil.jsonToBean(userInfo, AttentionEntity.class);
                updateViewFromData(state);
                mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                MPNetworkUtils.logError(TAG, volleyError);
            }

        };
        MPServerHttpManager.getInstance().attentionListData(member_id, limit, offset, okResponseCallback);
    }

    /**
     * 获取数据，更新页面
     *
     * @param state 　刷新用到的状态
     */
    private void updateViewFromData(int state) {
        switch (state) {
            case 0:
                OFFSET = 0;
                attentionList.clear();
                break;
            case 1:
                OFFSET = 10;
                attentionList.clear();
                break;
            case 2:
                OFFSET += 10;
                break;
            default:
                break;
        }

        attentionList.addAll(attentionEntity.getDesigner_list());
        attentionAdapter.notifyDataSetChanged();
    }

    /**
     * 取消关注
     *
     * @param member_id           用户id
     * @param followed_member_id  已关注用户的id
     * @param followed_member_uid 已关注用户的uid
     */
    private void unFollowedDesigner(String member_id, String followed_member_id, String followed_member_uid) {

        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                attentionList.clear();
                attentionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().unFollowedDesigner(member_id, followed_member_id, followed_member_uid, okResponseCallback);
    }


    /// 控件.
    private PullListView lv_attention;
    private PullToRefreshLayout mPullToRefreshLayout;

    /// 变量.
    private String acs_member_id;
    private MemberEntity memberEntity;

    /// 集合，类.
    private AttentionEntity attentionEntity;
    private AttentionAdapter attentionAdapter;
    public ArrayList<AttentionEntity.DesignerListBean> attentionList = new ArrayList<>();
    private boolean isFirstIn = true;
    private int OFFSET = 0;
    private int LIMIT = 10;

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        attentionListData(acs_member_id, 0, LIMIT, 1);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        attentionListData(acs_member_id, OFFSET, LIMIT, 2);
    }
}
