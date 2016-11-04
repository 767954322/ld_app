package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendExpandableAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.CustomHeaderExpandableListView;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.GsonUtil.jsonToBean;

/**
 * 清单页面:空白页面及编辑清单页面
 *
 * @author liuhea
 *         created at 16-10-24
 */
public class RecommendListDetailActivity extends NavigationBarActivity implements View.OnClickListener {

    private CustomHeaderExpandableListView mRecyclerViewList;
    private AppCompatButton mBtnListSend;
    private Activity mActivity;
    private String mAsset_id;
    private LinearLayout mLlEmptyContentView;
    private RecommendExpandableAdapter mRecommendExpandableAdapter;


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
        mRecyclerViewList = (CustomHeaderExpandableListView) findViewById(R.id.rcy_recommend_detail);
        mBtnListSend = (AppCompatButton) findViewById(R.id.btn_list_send);
        mLlEmptyContentView = (LinearLayout) findViewById(R.id.empty_view);
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
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String jsonString = jsonObject.toString();
                Log.d("RecommendListDetailAc", jsonString);
                RecommendDetailsBean recommendListDetailBean = jsonToBean(jsonString, RecommendDetailsBean.class);
                updateUI(recommendListDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDraftDetail(mAsset_id, callback);
    }

    private void updateUI(RecommendDetailsBean recommendListDetailBean) {
        setTitle(recommendListDetailBean);

        String scfd = recommendListDetailBean.getScfd();
        updateItemView(scfd);
    }

    private void updateItemView(String scfd) {
        // 更新适配器
        List<RecommendSCFDBean> recommendSCFDList = new Gson()
                .fromJson(scfd, new TypeToken<List<RecommendSCFDBean>>() {
                }.getType());

        if (null == recommendSCFDList) {
            mLlEmptyContentView.setVisibility(View.VISIBLE);
        } else {
            mLlEmptyContentView.setVisibility(View.GONE);
            mRecyclerViewList.setHeaderView(getLayoutInflater().inflate(R.layout.item_group_indicator,
                    mRecyclerViewList, false));
            mRecommendExpandableAdapter = new RecommendExpandableAdapter(this, getTestData(), mRecyclerViewList);
            mRecyclerViewList.setAdapter(mRecommendExpandableAdapter);
            for (int i = 0; i < getTestData().size(); i++) {
                mRecyclerViewList.expandGroup(i);
            }

//        adapter = new PinnedHeaderExpandableAdapter(childrenData, groupData, getApplicationContext(), explistview);
//         RecommendListEditParentAdapter recommendListEditAdapter = new RecommendListEditParentAdapter(this, recommendSCFDList);
        }

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
        Intent intent = new Intent(RecommendListDetailActivity.this, AddMaterialActivity.class);
        startActivity(intent);

    }

    @Override
    protected void leftNavButtonClicked(View view) {
        new AlertView(UIUtils.getString(R.string.tip), "当前订单还未发送，是否保存？",
                null, null, new String[]{UIUtils.getString(R.string.sure)}, RecommendListDetailActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != AlertView.CANCELPOSITION) {
                    /** 确定　P11 主材推荐清单、品类、品牌保存到草稿箱　PUT
                     /materials-recommend-app/v1/api/designers/{designer_id}/recommends

                     request:
                     {
                     "asset_id":""
                     "scfd":""
                     }
                     */
                    Toast.makeText(mActivity, "保存", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        }).show();
        super.leftNavButtonClicked(view);
    }

    private void setTitle(RecommendDetailsBean recommendListDetailBean) {
        setTitleForNavbar(recommendListDetailBean.getCommunity_name());
        setTitleForNavButton(ButtonType.RIGHT, "添加主材");
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
    }

    ArrayList getTestData() {
        ArrayList list = new ArrayList<RecommendSCFDBean>();
        for (int i = 0; i < 3; i++) {
            RecommendSCFDBean recommendSCFDBean = new RecommendSCFDBean();
            recommendSCFDBean.setSub_category_3d_name("分组" + i);
            List<RecommendBrandsBean> brands = new ArrayList<RecommendBrandsBean>();
            for (int j = 0; j < 5; j++) {
                RecommendBrandsBean recommendBrandsBean = new RecommendBrandsBean();
                recommendBrandsBean.setBrand_name("好友" + i + "-" + j);
                brands.add(recommendBrandsBean);
            }
            recommendSCFDBean.setBrands(brands);
            list.add(recommendSCFDBean);
        }
        return list;

    }
}
