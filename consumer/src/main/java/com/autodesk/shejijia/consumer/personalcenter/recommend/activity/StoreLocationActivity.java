package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.StoreLocationAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MallAddressEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsEntity;
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

    public static void jumpTo(Context context, String brand_id) {
        Intent intent = new Intent(context, StoreLocationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_location;
    }

    @Override
    protected void initView() {
        super.initView();
        mMallListView = (ListView) findViewById(R.id.lv_mall_location);
        mAdapter = new StoreLocationAdapter(this, mStoreLocations, R.layout.item_store_location);
        mMallListView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getMallsLocation();
    }

    private void getMallsLocation() {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("recommend", "CsRecommendDetailsActivity:" + jsonObject.toString());
                MallAddressEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), MallAddressEntity.class);
                List<MallAddressEntity.MallAddressesBean> addresses = entity.getMallAddresses();
                mStoreLocations.addAll(addresses);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getMallsLocation("0", callback);
    }
}
