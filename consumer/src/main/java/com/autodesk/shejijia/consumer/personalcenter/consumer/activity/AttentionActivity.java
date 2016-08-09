package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;


import android.os.Bundle;
import android.text.TextUtils;

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
        if (null == memberEntity) {
            return;
        }
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
        attentionList.remove(position);
        followingOrUnFollowedDesigner(false, member_id, hs_uid);
    }

    /**
     * 关注列表
     *
     * @param member_id 用户id
     * @param limit
     * @param offset
     */
    public void attentionListData(String member_id, int offset, int limit, final int state) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {

                String userInfo = GsonUtil.jsonToString(jsonObject);
                AttentionEntity attentionEntity = GsonUtil.jsonToBean(userInfo, AttentionEntity.class);

                updateViewFromData(state, attentionEntity);
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
     * @param attentionEntity
     * @param state           　刷新用到的状态
     */
    private void updateViewFromData(int state, AttentionEntity attentionEntity) {
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
        /// 处理上拉加载更多时候，重复加载问题 .
        int count = attentionEntity.getCount();
        if (count == attentionList.size()) {
            return;
        }
        List<AttentionEntity.DesignerListBean> designer_list = attentionEntity.getDesigner_list();
        if (null != designer_list && designer_list.size() > 0) {
            String nick_name;
            for (AttentionEntity.DesignerListBean designerListBean : designer_list) {
                nick_name = designerListBean.getNick_name();
                designerListBean.setNick_name(subString(nick_name));
            }

            attentionList.addAll(designer_list);
        }
        attentionAdapter.notifyDataSetChanged();
    }

    /**
     * 截取，执行字符串中的内容
     *
     * @param nick_name
     * @return
     */
    private String subString(String nick_name) {
        String nick_name_1 = UIUtils.getString(R.string.nodata);
        if (TextUtils.isEmpty(nick_name)) {
            return nick_name_1;
        }
        int index = nick_name.indexOf("_");
        if (index > 0) {
            nick_name_1 = nick_name.substring(0, index);
        }
        return nick_name_1;
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        attentionListData(acs_member_id, 0, LIMIT, 1);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        attentionListData(acs_member_id, OFFSET, LIMIT, 2);
    }

    /**
     * 关注或者取消关注设计师
     *
     * @param followsType true 为关注，false 取消关注
     */
    private void followingOrUnFollowedDesigner(final boolean followsType, String followed_member_id, String followed_member_uid) {
        CustomProgress.show(this, "", false, null);

        MPServerHttpManager.getInstance().followingOrUnFollowedDesigner(acs_member_id, followed_member_id, followed_member_uid, followsType, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                attentionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }


    private PullListView lv_attention;
    private PullToRefreshLayout mPullToRefreshLayout;
    private String acs_member_id;
    private MemberEntity memberEntity;

    //    private AttentionEntity attentionEntity;
    private AttentionAdapter attentionAdapter;
    public ArrayList<AttentionEntity.DesignerListBean> attentionList = new ArrayList<>();
    private boolean isFirstIn = true;
    private int OFFSET = 0;
    private int LIMIT = 10;


}
