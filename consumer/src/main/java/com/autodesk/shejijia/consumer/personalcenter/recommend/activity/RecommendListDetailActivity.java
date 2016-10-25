package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendListDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

/**
 * 清单页面:空白页面及编辑清单页面
 *
 * @author liuhea
 *         created at 16-10-24
 */
public class RecommendListDetailActivity extends NavigationBarActivity {

    private RelativeLayout mActivityRecommendListDetail;
    private RecyclerView mRecyclerViewList;
    private AppCompatButton mBtnListSend;
    private Activity mActivity;
    private String mAsset_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recommend_list_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mActivityRecommendListDetail = (RelativeLayout) findViewById(R.id.activity_recommend_list_detail);
        mRecyclerViewList = (RecyclerView) findViewById(R.id.recycler_view_list);
        mBtnListSend = (AppCompatButton) findViewById(R.id.btn_list_send);

    }

    public static void actionStartActivity(Context context, String asset_id) {
        Intent intent = new Intent(context, RecommendListDetailActivity.class);
        intent.putExtra("asset_id", asset_id);
        context.startActivity(intent);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mAsset_id = intent.getStringExtra("asset_id");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mActivity = this;
        CustomProgress.showDefaultProgress(mActivity);
        getRecommendDraftDetail();
    }


    /**
     * 从推荐草稿中获取推荐清单信息
     */
    void getRecommendDraftDetail() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String designer_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String jsonString = GsonUtil.jsonToString(jsonObject);
                Log.d("RecommendListDetailAc", jsonString);
                RecommendListDetailBean recommendListDetailBean = GsonUtil.jsonToBean(jsonString, RecommendListDetailBean.class);

                setTitleForNavbar(recommendListDetailBean.getCommunity_name());
                setTitleForNavButton(ButtonType.RIGHT, "添加主材");
                setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDraftDetail(designer_id, mAsset_id, callback);
    }

}
