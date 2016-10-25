package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecoDetailsAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ScfdEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.DateUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 * des:设计师清单详情
 */

public class DesignRecomDetailsActivity extends NavigationBarActivity {

    private String mAsset_id;
    private ImageView ivRecoWfsico;
    private TextView tvRecommendName;
    private TextView tvAssetId;
    private TextView tvRecoConsumerName;
    private TextView tvRecoConsumerMobile;
    private TextView tvRecoItemAddress;
    private TextView tvRecoItemDetailsAddress;
    private TextView tvCreateDate;
    private RecyclerView mDetailsRecyclerView;

    public static void jumpTo(Context context, String asset_id) {
        Intent intent = new Intent(context, DesignRecomDetailsActivity.class);
        intent.putExtra("asset_id", asset_id);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleBarView();
        ivRecoWfsico = (ImageView) findViewById(R.id.iv_reco_wfsico);
        tvRecommendName = (TextView) findViewById(R.id.tv_recommend_name);
        tvAssetId = (TextView) findViewById(R.id.tv_asset_id);
        tvRecoConsumerName = (TextView) findViewById(R.id.tv_reco_consumer_name);
        tvRecoConsumerMobile = (TextView) findViewById(R.id.tv_reco_consumer_mobile);
        tvRecoItemAddress = (TextView) findViewById(R.id.tv_reco_item_address);
        tvRecoItemDetailsAddress = (TextView) findViewById(R.id.tv_reco_item_details_address);
        tvCreateDate = (TextView) findViewById(R.id.tv_create_date);
        mDetailsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_details);
        mDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailsRecyclerView.setAdapter(new RecoDetailsAdapter());
    }

    private void setTitleBarView() {
        setTitleForNavbar("清单详情");
        TextView mRightTextView = (TextView) findViewById(R.id.nav_right_textView);
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setTextColor(UIUtils.getColor(R.color.color_blue_0084ff));
        mRightTextView.setText("编辑");
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
        getRecommendDetails();
    }

    private void getRecommendDetails() {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String design_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("RecommendFragment", jsonObject.toString());
                RecommendDetailsEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), RecommendDetailsEntity.class);
                updateView2Api(entity);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RecommendFragment", volleyError.toString());
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDetails(design_id, mAsset_id, callback);
    }

    private void updateView2Api(RecommendDetailsEntity item) {
        tvRecommendName.setText(item.getCommunity_name());
        tvAssetId.setText("清单编号：" + item.getMain_project_id());
        tvRecoConsumerName.setText(item.getConsumer_name());
        tvRecoConsumerMobile.setText(item.getConsumer_mobile());
        tvRecoItemAddress.setText(item.getProvince_name() + item.getCity_name() + item.getDistrict_name());
        tvRecoItemDetailsAddress.setText(item.getCommunity_address());
//        tvCreateDate.setText(DateUtil.getStringDateByFormat(new Date(item.getDate_submitted()), "yyyy-MM-dd HH:mm"));
        String scfd = item.getScfd();
        List<ScfdEntity> brand_lst = new Gson()
                .fromJson(scfd, new TypeToken<List<ScfdEntity>>() {
                }.getType());

    }
}
