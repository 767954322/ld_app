package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.AttentionAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AttentionEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
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
 * @brief 关注列表 .
 */
public class AttentionListActivity extends NavigationBarActivity implements AttentionAdapter.OnItemClickListener, PullToRefreshLayout.OnRefreshListener{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        super.initView();
        mLvAttention = (PullListView) findViewById(R.id.lv_attention);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mRlEmptyView = (RelativeLayout) findViewById(R.id.rl_empty_layout);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        // 第一次进入自动刷新
//        if (isFirstIn) {
//            //changeState
//            mPullToRefreshLayout.autoRefresh();
//            isFirstIn = false;
//        }
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mPullToRefreshLayout.autoRefresh();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_attention));

        attentionAdapter = new AttentionAdapter(this, attentionList);
        mLvAttention.setAdapter(attentionAdapter);

        memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == memberEntity) {
            return;
        }
        acs_member_id = memberEntity.getAcs_member_id();
    }

    @Override
    protected void initListener() {
        super.initListener();
        attentionAdapter.setOnItemClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
        mLvAttention.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(AttentionListActivity.this, SeekDesignerDetailActivity.class);
                String hs_uid = attentionList.get(position).getHs_uid();
                String member_id = attentionList.get(position).getMember_id();
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, member_id);
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
                startActivity(intent);
            }
        });
    }

    /**
     * 取消关注按钮点击事件
     *
     * @param position 位置
     */
    @Override
    public void OnItemCancelAttentionClick(int position) {
        if (position + 1 > attentionList.size()) {
            return;
        }
        mMemberId = attentionList.get(position).getMember_id();
        mHsUid = attentionList.get(position).getHs_uid();
        mNickName = attentionList.get(position).getNick_name();

        initFollowedAlertView(position);
        unFollowedAlertView.show();
    }

    /**
     * 点击头像进入设计师主页
     *
     * @param position
     */
    @Override
    public void OnItemAvatarClickListener(int position) {
        Intent intent = new Intent(this, SeekDesignerDetailActivity.class);
        String hs_uid = attentionList.get(position).getHs_uid();
        String member_id = attentionList.get(position).getMember_id();
        intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, member_id);
        intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, hs_uid);
        startActivity(intent);
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
                if (attentionEntity.getCount() == 0) {
                    mRlEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mRlEmptyView.setVisibility(View.GONE);
                }
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
                splitString(nick_name);
                designerListBean.setNick_name(mNickName);
                designerListBean.setHs_uid(mHsUid);
            }
            attentionList.addAll(designer_list);
        }
        attentionAdapter.notifyDataSetChanged();
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
     * 取消关注设计师
     *
     * @param followsType true 为关注，false 取消关注
     * @param position
     */
    private void followingOrUnFollowedDesigner(String followed_member_id, String followed_member_uid, final boolean followsType, final int position) {
        CustomProgress.show(this, "", false, null);

        MPServerHttpManager.getInstance().followingOrUnFollowedDesigner(acs_member_id, followed_member_id, followed_member_uid, followsType, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                /// 手动移除集合中已经取消的设计师 .
                attentionList.remove(position);

                if (attentionList.size() == 0) {
                    mRlEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mRlEmptyView.setVisibility(View.GONE);
                }
                attentionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        });
    }

    /**
     * 取消关注提示框
     *
     * @param cancelPosition
     */
    private void initFollowedAlertView(final int cancelPosition) {
        unFollowedAlertView = new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.attention_tip_message_first) + mNickName + UIUtils.getString(R.string.attention_tip_message_last),
                UIUtils.getString(R.string.following_cancel), null,
                new String[]{UIUtils.getString(R.string.following_sure)},
                AttentionListActivity.this,
                AlertView.Style.Alert, new com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    followingOrUnFollowedDesigner(mMemberId, mHsUid, false, cancelPosition);
                }
            }
        }).setCancelable(true);
    }

    /**
     * 截取，以“-”分割的字符串中的内容
     *
     * @param nick_name 需要截取的字符串
     */
    private void splitString(String nick_name) {
        if (TextUtils.isEmpty(nick_name)) {
            return;
        }
        String[] nickNameArray = nick_name.split("_");
        if (null != nickNameArray && nickNameArray.length > 1) {
            mHsUid = nickNameArray[1];
            mNickName = nickNameArray[0];
        } else {
            mNickName = UIUtils.getString(R.string.nodata);
            mHsUid = "";
        }

    }

    private PullListView mLvAttention;
    private PullToRefreshLayout mPullToRefreshLayout;
    private String acs_member_id;
    private MemberEntity memberEntity;
    private AlertView unFollowedAlertView;
    private RelativeLayout mRlEmptyView;

    private AttentionAdapter attentionAdapter;
    public ArrayList<AttentionEntity.DesignerListBean> attentionList = new ArrayList<>();
    private String mMemberId;
    private String mHsUid;
    private String mNickName;
    private boolean isFirstIn = true;
    private int OFFSET = 0;
    private int LIMIT = 10;

}
