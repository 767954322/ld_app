package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.os.Bundle;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-16 .
 * @file DecorationBidderActivity.java .
 * @brief 应标人数页面 .
 */
public class DecorationBidderActivity extends NavigationBarActivity {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_decoration_bidder;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.title_designer_count));
        setTextColorForRightNavButton(R.color.actionsheet_blue);
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.title_bidder_introduce));

    }

    @Override
    protected void initListener() {
        super.initListener();

    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);

        new AlertView(UIUtils.getString(R.string.title_bidder_introduce),
                UIUtils.getString(R.string.alert_bidder_introduce),
                null, null, new String[]{"确定"}, this, AlertView.Style.Alert, null).show();
    }


}
