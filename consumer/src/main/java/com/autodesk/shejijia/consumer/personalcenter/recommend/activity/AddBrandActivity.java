package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.AddBrandAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddBrandActivity extends NavigationBarActivity implements PullToRefreshLayout.OnRefreshListener,
        OkJsonRequest.OKResponseCallback,AdapterView.OnItemClickListener{
    private PullListView addBrandListview;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<RecommendBrandsBean> brandsBeanList;
    private RecommendSCFDBean transmissionBean;
    private AddBrandAdapter addBrandAdapter;
    private Boolean isRefresh = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_brand;
    }

    @Override
    protected void initView() {
        super.initView();
        addBrandListview = (PullListView)findViewById(R.id.add_brand_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout)findViewById(R.id.refresh_addbrand_view));
    }
    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        transmissionBean = (RecommendSCFDBean)intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        brandsBeanList = new ArrayList<>();
        setNavigationBar();
        addBrandAdapter = new AddBrandAdapter(this,brandsBeanList,R.layout.add_check_textview);
        addBrandListview.setAdapter(addBrandAdapter);
//        getBrands(0,10);
        mPullToRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
        addBrandListview.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long[] ss = addBrandListview.getCheckedItemIds();
        ToastUtil.showCustomToast(this,ss.length+"");
    }
    private void setNavigationBar() {
        setTitleForNavbar(transmissionBean.getSub_category_3d_name());
//        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));
//        setTextColorForRightNavButton(UIUtils.getColor(R.color.actionsheet_gray));
    }


    @Override
    public void onResponse(JSONObject jsonObject) {
        String jsonString = jsonObject.toString();
        RecommendSCFDBean recommendSCFDBean =  GsonUtil.jsonToBean(jsonString,RecommendSCFDBean.class);
        brandsBeanList.clear();
//        if(isRefresh){
//            brandsBeanList.clear();
//        }
        filterBrand(recommendSCFDBean.getBrands());
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        MPNetworkUtils.logError(TAG, volleyError);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = true;
        getBrands(0,30);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = false;
        getBrands(addBrandListview.getCount(),30);
    }
    private  void getBrands(int offset, int limit){
        MPServerHttpManager.getInstance().getCategoryBrandsInformation("","","","","","",offset,limit,this);
    }
    private void filterBrand(List<RecommendBrandsBean> brandsBeans){
        for(RecommendBrandsBean brandsBean:brandsBeans){
            for(RecommendBrandsBean tb:transmissionBean.getBrands()){
                if(brandsBean.getCode().equals(tb.getCode())){
                    continue;
                }
                brandsBeanList.add(brandsBean);
            }
        }
        addBrandAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }
}
