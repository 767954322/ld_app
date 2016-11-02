package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.UpdataBrandAdapter;
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
public class UpdataBrandActivity extends NavigationBarActivity implements PullToRefreshLayout.OnRefreshListener,
            OkJsonRequest.OKResponseCallback,AdapterView.OnItemClickListener{
    private ListView updataBrandListview;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<RecommendBrandsBean> brandsBeanList;
    private UpdataBrandAdapter updataBrandAdapter;
    private Boolean isRefresh = true;
    private Boolean isFinish = false;
    private RecommendSCFDBean recommendSCFDBean;
    private RecommendBrandsBean recommendBrandsBean;
    private RecommendBrandsBean selectRecommendBrandsBean;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_updata_brand;
    }
    @Override
    protected void initView() {
        super.initView();
        updataBrandListview = (ListView)findViewById(R.id.updata_brand_listview);
        mPullToRefreshLayout = ((PullToRefreshLayout)findViewById(R.id.refresh_view));
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Intent intent = getIntent();
        recommendBrandsBean = (RecommendBrandsBean)intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDBEAN);
        brandsBeanList = new ArrayList<>();
        updataBrandAdapter = new UpdataBrandAdapter(this,brandsBeanList,R.layout.select_check_textview);
        updataBrandListview.setAdapter(updataBrandAdapter);
        setNavigationBar();
        mPullToRefreshLayout.autoRefresh();
    }


    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
        updataBrandListview.setOnItemClickListener(this);
    }
    private  void getBrands(int offset, int limit){
        MPServerHttpManager.getInstance().getCategoryBrandsInformation("","","","","","",offset,limit,this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
        selectRecommendBrandsBean = brandsBeanList.get(position);
        isFinish = true;
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        if(isFinish){
            Intent intent = new Intent();
            intent.putExtra(JsonConstants.RECOMMENDBRANDBEAN,selectRecommendBrandsBean);
            setResult(1000111,intent);
            finish();
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        String jsonString = jsonObject.toString();
        recommendSCFDBean =  GsonUtil.jsonToBean(jsonString,RecommendSCFDBean.class);
        brandsBeanList.clear();//接口有问题，待修改
//        if(isRefresh){
//            brandsBeanList.clear();
//        }
        brandsBeanList.addAll(recommendSCFDBean.getBrands());
        updataBrandAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }
    private void setNavigationBar() {
        setTitleForNavbar(recommendBrandsBean.getSub_category_3d_name());
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.actionsheet_gray));
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        MPNetworkUtils.logError(TAG, volleyError);
    }
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = true;
        getBrands(0,10);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        isRefresh = false;
        getBrands(updataBrandListview.getCount(),10);
    }
}
