package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.StoreLocationAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MallAddressEntity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-27
 * @GitHub: https://github.com/meikoz
 */

public class StoreLocationActivity extends NavigationBarActivity {

    private List<MallAddressEntity> mStoreLocations = new ArrayList<>();

    public static void jumpTo(Context context) {
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
        CommonAdapter adapter = new StoreLocationAdapter(this, mStoreLocations, R.layout.item_store_location);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }
}
