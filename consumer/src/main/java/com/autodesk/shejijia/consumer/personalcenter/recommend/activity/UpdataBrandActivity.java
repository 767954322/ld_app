package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.UpdataBrandAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendListDetailBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ShowBrandsBean;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
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
            OkJsonRequest.OKResponseCallback{
    private ListView updataBrandListview;
    private PullToRefreshLayout mPullToRefreshLayout;
    private List<ShowBrandsBean.BrandsBean> brandsBeanList;
    private UpdataBrandAdapter updataBrandAdapter;
    private Boolean isRefresh = true;
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

    }
    private  void getBrands(int offset, int limit){
        MPServerHttpManager.getInstance().getCategoryBrandsInformation("","","","","","",offset,limit,this);
    }
    @Override
    public void onResponse(JSONObject jsonObject) {
        String jsonString = jsonObject.toString();
        ShowBrandsBean showBrandsBean =  GsonUtil.jsonToBean(jsonString,ShowBrandsBean.class);
        brandsBeanList.clear();//接口有问题，待修改
//        if(isRefresh){
//            brandsBeanList.clear();
//        }
        brandsBeanList.addAll(showBrandsBean.getBrands());
        updataBrandAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }
    private void setNavigationBar() {
        setTitleForNavbar("123456");
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
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
