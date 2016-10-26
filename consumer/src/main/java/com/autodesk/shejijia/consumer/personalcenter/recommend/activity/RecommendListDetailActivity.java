package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendListEditAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendListDetailBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.GsonUtil.jsonToBean;

/**
 * 清单页面:空白页面及编辑清单页面
 *
 * @author liuhea
 *         created at 16-10-24
 */
public class RecommendListDetailActivity extends NavigationBarActivity implements View.OnClickListener {

    private ListView mRecyclerViewList;
    private AppCompatButton mBtnListSend;
    private Activity mActivity;
    private String mAsset_id;

    public static void actionStartActivity(Context context, String asset_id) {
        Intent intent = new Intent(context, RecommendListDetailActivity.class);
        intent.putExtra("asset_id", asset_id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recommend_list_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerViewList = (ListView) findViewById(R.id.rcy_recommend_detail);
        mBtnListSend = (AppCompatButton) findViewById(R.id.btn_list_send);
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
//        initRecycleView();

        getRecommendDraftDetail();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnListSend.setOnClickListener(this);
    }

    /**
     * 从推荐草稿中获取推荐清单信息
     */
    private void getRecommendDraftDetail() {
        CustomProgress.showDefaultProgress(mActivity);

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String designer_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String jsonString = jsonObject.toString();
                Log.d("RecommendListDetailAc", jsonString);
                RecommendListDetailBean recommendListDetailBean = jsonToBean(jsonString, RecommendListDetailBean.class);
                updateUI(recommendListDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDraftDetail(designer_id, mAsset_id, callback);
    }

    private void updateUI(RecommendListDetailBean recommendListDetailBean) {
        setTitle(recommendListDetailBean);

        String scfd = recommendListDetailBean.getScfd();
        updateItemView(scfd);
    }

    private void updateItemView(String scfd) {
        // 更新适配器
        List<RecommendSCFDBean> recommendSCFDList = new Gson()
                .fromJson(scfd, new TypeToken<List<RecommendSCFDBean>>() {
                }.getType());

        Log.d("RecommendListDetailActi", scfd);
        RecommendListEditAdapter recommendListEditAdapter = new RecommendListEditAdapter(this, recommendSCFDList);
        mRecyclerViewList.setAdapter(recommendListEditAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_send:
                MyToast.show(mActivity, "发送");
                break;
        }
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        //TODO @xuehua.yao
        MyToast.show(mActivity, "添加主材");
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        MyToast.show(mActivity, "当前订单还未发送，是否保存？");
        // 取消　finish．
        /** 确定　P11 主材推荐清单、品类、品牌保存到草稿箱　PUT
         /materials-recommend-app/v1/api/designers/{designer_id}/recommends

         request:
         {
         "asset_id":""
         "scfd":""
         }
         */
        super.leftNavButtonClicked(view);
    }

    private void setTitle(RecommendListDetailBean recommendListDetailBean) {
        setTitleForNavbar(recommendListDetailBean.getCommunity_name());
        setTitleForNavButton(ButtonType.RIGHT, "添加主材");
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
    }

//    private void initRecycleView() {
//        //init recyclerView
//        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerViewList.setHasFixedSize(true);
//        mRecyclerViewList.setItemAnimator(new DefaultItemAnimator());
//    }
}
