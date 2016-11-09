package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.AddBrandAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddBrandActivity extends NavigationBarActivity implements PullToRefreshLayout.OnRefreshListener,
        OkJsonRequest.OKResponseCallback,AdapterView.OnItemClickListener,View.OnClickListener{
    private PullListView addBrandListview;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<RecommendBrandsBean> brandsBeanList;
    private RecommendSCFDBean mRecommendSCFDBean;
    private AddBrandAdapter addBrandAdapter;
    private Boolean isRefresh = true;
    private TextView tvNumber;
    private AppCompatButton btFinsh;
    private List<Integer> itemIds = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_brand;
    }

    @Override
    protected void initView() {
        super.initView();
        tvNumber = (TextView)findViewById(R.id.tv_two);
        btFinsh = (AppCompatButton)findViewById(R.id.bt_finsh);
        addBrandListview = (PullListView)findViewById(R.id.add_brand_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout)findViewById(R.id.refresh_addbrand_view));
    }
    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mRecommendSCFDBean = (RecommendSCFDBean)intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        brandsBeanList = new ArrayList<>();
        setNavigationBar();
        addBrandAdapter = new AddBrandAdapter(this,brandsBeanList,itemIds,R.layout.add_check_textview);
        addBrandListview.setAdapter(addBrandAdapter);
        getBrands(0,100);
//        mPullToRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
        addBrandListview.setOnItemClickListener(this);
        btFinsh.setOnClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        long[] items = addBrandListview.getCheckedItemIds();
        int number = Constant.FragmentEnum.FIVE - items.length;
        tvNumber.setText(number+"");
        btFinsh.setEnabled(items.length > 0?true:false);
        btFinsh.setBackground(this.getResources().getDrawable(items.length > 0?R.color.bg_0084ff:R.color.gray));
        itemIds.clear();
        for(long pt:items){
            itemIds.add((int)pt);
        }
        addBrandAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_finsh:
                callBack();
                break;
            default:
                break;
        }

    }
    private void callBack(){
        List<RecommendBrandsBean> list = new ArrayList<>();
        for (long i:itemIds){
            RecommendBrandsBean rbb =  brandsBeanList.get((int)i);
            list.add(rbb);
        }
        mRecommendSCFDBean.setBrands(list);
        Intent intent = new Intent();
        intent.putExtra(Constant.JsonLocationKey.SUB_CATEGORY_3D_ID,mRecommendSCFDBean.getSub_category_3d_id());
        intent.putExtra(JsonConstants.RECOMMENDBRANDBEAN,(Serializable)list);//List<>list = (List<…<…>>)getIndent.getIntent().getSerializableExtra(key);//接
        setResult(RESULT_OK,intent);
        finish();
    }


    private void setNavigationBar() {
        setTitleForNavbar(mRecommendSCFDBean.getSub_category_3d_name());
    }


    @Override
    public void onResponse(JSONObject jsonObject) {
        String jsonString = jsonObject.toString();
        RecommendSCFDBean recommendSCFDBean =  GsonUtil.jsonToBean(jsonString,RecommendSCFDBean.class);
        brandsBeanList.clear();
        List<RecommendBrandsBean> Brands = recommendSCFDBean.getBrands();
        if(Brands == null){
            return;
        }
        filterBrand(Brands);
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        MPNetworkUtils.logError(TAG, volleyError);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//        isRefresh = true;
//        getBrands(0,30);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
//        isRefresh = false;
//        getBrands(addBrandListview.getCount(),30);
    }
    private  void getBrands(int offset, int limit){
        MPServerHttpManager.getInstance().getCategoryBrandsInformation(mRecommendSCFDBean.getCategory_3d_id(),
                mRecommendSCFDBean.getCategory_3d_name(), mRecommendSCFDBean.getSub_category_3d_id(),
                mRecommendSCFDBean.getSub_category_3d_name(), "", "", offset, limit, this);
    }
    private void filterBrand(List<RecommendBrandsBean> brandsBeans){
        for(RecommendBrandsBean brandsBean:brandsBeans){
            for(RecommendBrandsBean tb:mRecommendSCFDBean.getBrands()){
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
