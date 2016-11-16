package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.StoreLocationAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MallAddressEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class StoreLocationActivity extends NavigationBarActivity {

    private List<MallAddressEntity.MallAddressesBean> mStoreLocations = new ArrayList<>();
    private CommonAdapter mAdapter;
    private ListView mMallListView;
    private String mBrand_id;

    public static void jumpTo(Context context, String brand_id) {
        Intent intent = new Intent(context, StoreLocationActivity.class);
        intent.putExtra("brand_id", brand_id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_location;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar("店面地址");
        mMallListView = (ListView) findViewById(R.id.lv_mall_location);
        mAdapter = new StoreLocationAdapter(this, mStoreLocations, R.layout.item_store_location);
        mMallListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getMallsLocation();
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mBrand_id = getIntent().getStringExtra("brand_id");
    }

    private void getMallsLocation() {
        CustomProgress.show(this, "", false, null);
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                Log.d("recommend", "CsRecommendDetailsActivity:" + jsonObject.toString());
                MallAddressEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), MallAddressEntity.class);
                List<MallAddressEntity.MallAddressesBean> addresses = entity.getJuran_storefront_info();
                if (addresses != null && addresses.size() > 0)
                    mStoreLocations.addAll(addresses);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getMallsLocation(mBrand_id, callback);
    }
}
