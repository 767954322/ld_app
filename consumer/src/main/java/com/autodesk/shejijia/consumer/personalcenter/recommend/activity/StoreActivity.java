package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewControls;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-21 .
 * @file StoreActivity.java .
 * @brief 展示店铺页面 .
 */

public class StoreActivity extends NavigationBarActivity implements View.OnClickListener {


    private LinearLayout showStoreViewGroup;
    private String[] arr = new String[]{"火影", "海贼", "秦时明月", "侠岚", "灵主", "不良人", "画江湖", "行尸走肉", "海底总动员", "诺克"};
    private Button store_sure_btn;
    private DynamicAddViewControls dynamicView;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.store_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.store_show_title));
        showStoreViewGroup = (LinearLayout) findViewById(R.id.ll_store);
        store_sure_btn = (Button) findViewById(R.id.store_sure_btn);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

//        dynamicCreateStoreLine();
        dynamicView = new DynamicAddViewControls(StoreActivity.this);
        dynamicView.dynamicData(arr);
        showStoreViewGroup.addView(dynamicView);
        dynamicView.setListener(new DynamicAddViewControls.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(int itemIndex) {
                ToastUtil.showCustomToast(StoreActivity.this,""+arr[itemIndex]);
            }
        });
    }

}
