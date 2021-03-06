package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.DcRecommendDetailsAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RefreshEvent;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
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

import de.greenrobot.event.EventBus;

/**
 * @User: 蜡笔小新
 * @date: 16-10-24
 * @GitHub: https://github.com/meikoz
 * des:设计师清单详情
 */

public class DcRecommendDetailsActivity extends NavigationBarActivity {

    private String mAsset_id;
    private ImageView mIvRecoWfsico;
    private TextView tvRecommendName;
    private TextView tvAssetId;
    private TextView tvRecoConsumerName;
    private TextView tvRecoConsumerMobile;
    private TextView tvRecoItemAddress;
    private TextView tvRecoItemDetailsAddress;
    private TextView tvCreateDate;
    private ListView mListView;
    private DcRecommendDetailsAdapter mAdapter;
    private List<RecommendSCFDBean> brands = new ArrayList<>();
    private RecommendDetailsBean mEntity;
    private boolean mCanceled;
    private TextView tvVillageName;

    public static void jumpTo(Context context, String asset_id, boolean canceled) {
        Intent intent = new Intent(context, DcRecommendDetailsActivity.class);
        intent.putExtra(JsonConstants.ASSET_ID, asset_id);
        intent.putExtra(JsonConstants.CANCELED, canceled);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleBarView();
        View rootView = LayoutInflater.from(this).inflate(R.layout.layout_recommend_details_header, null);
        mListView = (ListView) findViewById(R.id.listview);
        mListView.addHeaderView(rootView);
        mIvRecoWfsico = (ImageView) rootView.findViewById(R.id.iv_reco_wfsico);
        tvRecommendName = (TextView) rootView.findViewById(R.id.tv_recommend_name);
        tvVillageName = (TextView) rootView.findViewById(R.id.tv_reco_village_name);
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
    }

    private void setTitleBarView() {
        setTitleForNavbar(UIUtils.getString(R.string.recommend_detail));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.color_blue_0084ff));
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        RecommendListDetailActivity.actionStartActivity(DcRecommendDetailsActivity.this, mEntity.getAsset_id() + "");
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mAsset_id = getIntent().getStringExtra(JsonConstants.ASSET_ID);
        mCanceled = getIntent().getBooleanExtra(JsonConstants.CANCELED, false);
        setTitleForNavButton(ButtonType.RIGHT, mCanceled ? "" : UIUtils.getString(R.string.recommend_edit));
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
        CustomProgress.showDefaultProgress(this);
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String design_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                mEntity = GsonUtil.jsonToBean(jsonObject.toString(), RecommendDetailsBean.class);
                updateView2Api(mEntity);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDetails(design_id, mAsset_id, callback);
    }

    private void updateView2Api(RecommendDetailsBean item) {
        if (!TextUtils.isEmpty(item.getCommunity_name())) {
            tvRecommendName.setText(UIUtils.substring(item.getCommunity_name(), 4));
            tvVillageName.setText(item.getCommunity_name());
        }
        tvAssetId.setText(UIUtils.getString(R.string.recommend_asset_code) + item.getProject_number() + "");
        tvRecoConsumerName.setText(item.getConsumer_name());
        tvRecoConsumerMobile.setText(item.getConsumer_mobile());
        tvRecoItemAddress.setText(item.getProvince_name() + item.getCity_name() + UIUtils.getNoStringIfEmpty(item.getDistrict_name()));
        tvRecoItemDetailsAddress.setText(item.getCommunity_address());
        tvCreateDate.setText(item.getDate_submitted());
        String scfd = item.getScfd();
        List<RecommendSCFDBean> brand_lst = new Gson()
                .fromJson(scfd, new TypeToken<List<RecommendSCFDBean>>() {
                }.getType());
        if (brand_lst != null && brand_lst.size() > 0) {
            brands.clear();
            brands.addAll(brand_lst);
        }
        mAdapter = new DcRecommendDetailsAdapter(this, brands, R.layout.item_recommend_details_brand, scfd);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int intExtra = data.getIntExtra(JsonConstants.LOCATION, 0);
            mListView.setSelection(intExtra + 1);
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


    public void onEventMainThread(RefreshEvent event) {
        getRecommendDetails();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
