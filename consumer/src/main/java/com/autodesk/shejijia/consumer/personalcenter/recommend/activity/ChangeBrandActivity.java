package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.ChanageBrandAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luchongbin on 16-11-1.
 */
public class ChangeBrandActivity extends NavigationBarActivity implements PullToRefreshLayout.OnRefreshListener,
        OkJsonRequest.OKResponseCallback, AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView updataBrandListview;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<RecommendBrandsBean> brandsBeanList;
    private ChanageBrandAdapter updataBrandAdapter;
    private Boolean isRefresh = true;
    private RecommendSCFDBean mRecommendSCFDBean;
    private RecommendBrandsBean selectRecommendBrandsBean;
    private AppCompatButton changeFinsh;
    private String brandCode;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_updata_brand;
    }

    @Override
    protected void initView() {
        super.initView();
        updataBrandListview = (ListView) findViewById(R.id.updata_brand_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        changeFinsh = (AppCompatButton) findViewById(R.id.change_finsh);

    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mRecommendSCFDBean = (RecommendSCFDBean) intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
        brandCode = (String) intent.getSerializableExtra("brandCode");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        brandsBeanList = new ArrayList<>();
        updataBrandAdapter = new ChanageBrandAdapter(this, brandsBeanList, R.layout.select_check_textview);
        updataBrandListview.setAdapter(updataBrandAdapter);
        setNavigationBar();
        mPullToRefreshLayout.autoRefresh();
    }


    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
        updataBrandListview.setOnItemClickListener(this);
        changeFinsh.setOnClickListener(this);
    }

    private void getBrands(int offset, int limit) {
        MPServerHttpManager.getInstance().getCategoryBrandsInformation("", "", "", "", "", "", offset, limit, this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
        selectRecommendBrandsBean = brandsBeanList.get(position);
        changeFinsh.setEnabled(true);
        changeFinsh.setBackground(this.getResources().getDrawable(R.color.bg_0084ff));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_finsh:
                List<RecommendBrandsBean> brandsBeen = mRecommendSCFDBean.getBrands();
                Intent intent = new Intent();
                for(RecommendBrandsBean recommendBrandsBean: brandsBeen){
                    if(recommendBrandsBean.getCode().equals(brandCode)){
                        int post = brandsBeen.indexOf(recommendBrandsBean);
                        mRecommendSCFDBean.getBrands().remove(recommendBrandsBean);
                        recommendBrandsBean.setMalls(selectRecommendBrandsBean.getMalls());
                        recommendBrandsBean.setName(selectRecommendBrandsBean.getBrand_name());
                        recommendBrandsBean.setBrand_name(selectRecommendBrandsBean.getBrand_name());
                        brandsBeen.add(post,recommendBrandsBean);
                        intent.putExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN, mRecommendSCFDBean);
                        break;
                    }
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        String jsonString = jsonObject.toString();
        RecommendSCFDBean recommendSCFDBean = GsonUtil.jsonToBean(jsonString, RecommendSCFDBean.class);
        brandsBeanList.clear();//接口有问题，待修改
//        if(isRefresh){
//            brandsBeanList.clear();
//        }
        filterBrand(recommendSCFDBean.getBrands());

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        MPNetworkUtils.logError(TAG, volleyError);
    }

    private void filterBrand(List<RecommendBrandsBean> brandsBeans) {
        for (RecommendBrandsBean brandsBean : brandsBeans) {
            for (RecommendBrandsBean tb : mRecommendSCFDBean.getBrands()) {
                if (brandsBean.getCode().equals(tb.getCode())) {
                    continue;
                }
                brandsBeanList.add(brandsBean);
            }
        }
        updataBrandAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    private void setNavigationBar() {
        setTitleForNavbar(mRecommendSCFDBean.getSub_category_3d_name());
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = true;
        getBrands(0, 30);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = false;
        getBrands(updataBrandListview.getCount(), 30);
    }
}
