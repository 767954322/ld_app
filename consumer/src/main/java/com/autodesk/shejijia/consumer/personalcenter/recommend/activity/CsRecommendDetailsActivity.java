package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.CsRecommendDetailsAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RefreshEvent;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 * des:设计师清单详情
 */

public class CsRecommendDetailsActivity extends NavigationBarActivity {

    private String mAsset_id;
    private ListView mListview;
    private CsRecommendDetailsAdapter mAdapter;
    private List<RecommendSCFDBean> brands = new ArrayList<>();
    private RecommendDetailsBean mEntity;
    private String mScfc;

    public static void jumpTo(Context context, String asset_id, String community_name) {
        Intent intent = new Intent(context, CsRecommendDetailsActivity.class);
        intent.putExtra("asset_id", asset_id);
        intent.putExtra("community_name", community_name);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleBarView();
        mListview = (ListView) findViewById(R.id.listview);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    private void setTitleBarView() {
        String community_name = getIntent().getStringExtra("community_name");
        setTitleForNavbar("清单详情");
        if (!TextUtils.isEmpty(community_name)) {
            setTitleForNavbar(community_name);
        }
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mAsset_id = getIntent().getStringExtra("asset_id");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recommend_details;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecommendDetails();
    }

    private void getRecommendDetails() {
        CustomProgress.show(this, "", false, null);
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String design_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                Log.d("recommend", "CsRecommendDetailsActivity:" + jsonObject.toString());
                mEntity = GsonUtil.jsonToBean(jsonObject.toString(), RecommendDetailsBean.class);
                updateView2Api(mEntity);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                Log.d("recommend", volleyError.toString());
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDetails(design_id, mAsset_id, callback);
    }

    private void updateView2Api(RecommendDetailsBean item) {
        mScfc = item.getScfc();
        List<RecommendSCFDBean> brand_lst = new Gson()
                .fromJson(mScfc, new TypeToken<List<RecommendSCFDBean>>() {
                }.getType());
        if (brand_lst != null && brand_lst.size() > 0) {
            brands.clear();
            brands.addAll(brand_lst);
        }
        mAdapter = new CsRecommendDetailsAdapter(this, brands, R.layout.item_cs_recommend_details, mScfc);
        mListview.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int intExtra = data.getIntExtra(ViewCategoryActivity.LOCATION, 0);
            mListview.setSelection(intExtra);
        }
    }

    /**
     * 返回测试刷新
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new RefreshEvent());
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        super.leftNavButtonClicked(view);
        EventBus.getDefault().post(new RefreshEvent());
    }
}
