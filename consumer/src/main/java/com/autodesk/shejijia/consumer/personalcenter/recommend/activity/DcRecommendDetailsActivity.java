package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.DcRecommendDetailsAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ScfdEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 * des:设计师清单详情
 */

public class DcRecommendDetailsActivity extends NavigationBarActivity {

    private String mAsset_id;
    private ImageView ivRecoWfsico;
    private TextView tvRecommendName;
    private TextView tvAssetId;
    private TextView tvRecoConsumerName;
    private TextView tvRecoConsumerMobile;
    private TextView tvRecoItemAddress;
    private TextView tvRecoItemDetailsAddress;
    private TextView tvCreateDate;
    private ListView mListview;
    private DcRecommendDetailsAdapter mAdapter;
    private List<ScfdEntity> brands = new ArrayList<>();
    private RecommendDetailsEntity mEntity;

    public static void jumpTo(Context context, String asset_id) {
        Intent intent = new Intent(context, DcRecommendDetailsActivity.class);
        intent.putExtra("asset_id", asset_id);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleBarView();
        View rootView = LayoutInflater.from(this).inflate(R.layout.layout_recommend_details_header, null);
        mListview = (ListView) findViewById(R.id.listview);
        mListview.addHeaderView(rootView);
        ivRecoWfsico = (ImageView) rootView.findViewById(R.id.iv_reco_wfsico);
        tvRecommendName = (TextView) rootView.findViewById(R.id.tv_recommend_name);
        tvAssetId = (TextView) rootView.findViewById(R.id.tv_asset_id);
        tvRecoConsumerName = (TextView) rootView.findViewById(R.id.tv_reco_consumer_name);
        tvRecoConsumerMobile = (TextView) rootView.findViewById(R.id.tv_reco_consumer_mobile);
        tvRecoItemAddress = (TextView) rootView.findViewById(R.id.tv_reco_item_address);
        tvRecoItemDetailsAddress = (TextView) rootView.findViewById(R.id.tv_reco_item_details_address);
        tvCreateDate = (TextView) rootView.findViewById(R.id.tv_create_date);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mAdapter = new DcRecommendDetailsAdapter(this, brands, R.layout.item_recommend_details_brand);
        mListview.setAdapter(mAdapter);
    }

    private void setTitleBarView() {
        setTitleForNavbar("清单详情");
        TextView mRightTextView = (TextView) findViewById(R.id.nav_right_textView);
        mRightTextView.setVisibility(View.VISIBLE);
        mRightTextView.setTextColor(UIUtils.getColor(R.color.color_blue_0084ff));
        mRightTextView.setText("编辑");
        mRightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecommendListDetailActivity.actionStartActivity(DcRecommendDetailsActivity.this, mEntity.getMain_project_id() + "");
            }
        });
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
                mEntity = GsonUtil.jsonToBean(jsonObject.toString(), RecommendDetailsEntity.class);
                updateView2Api(mEntity);
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
        // FIXME: 16-10-25  缺少日期字段
//        tvCreateDate.setText(DateUtil.getStringDateByFormat(new Date(item.getDate_submitted()), "yyyy-MM-dd HH:mm"));
        String scfd = item.getScfd();
        List<ScfdEntity> brand_lst = new Gson()
                .fromJson(scfd, new TypeToken<List<ScfdEntity>>() {
                }.getType());
        if (brand_lst != null && brand_lst.size() > 0) {
            brands.addAll(brand_lst);
            mAdapter.notifyDataSetChanged();
        }

    }
}
