package com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter.DecorationBidderAdapter;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-16 .
 * @file DecorationBidderActivity.java .
 * @brief 应标人数页面 .
 */
public class DecorationBidderActivity extends NavigationBarActivity {
    public static final String BIDDER_KEY = "DecorationBidderActivity";

    private ArrayList<DecorationBiddersBean> mBidders;
    private DecorationBidderAdapter mDecorationBidderAdapter;

    private String mNeeds_id;
    private ListView mListView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_decoration_bidder;
    }

    @Override
    protected void initView() {
        super.initView();
        mListView = (ListView) findViewById(R.id.lv_designer_list);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mBidders = (ArrayList<DecorationBiddersBean>) intent.getExtras().get(BIDDER_KEY);
        mNeeds_id = intent.getExtras().getString(Constant.ConsumerDecorationFragment.NEED_ID);
        if (null == mBidders) {
            return;
        }
        if (null == mDecorationBidderAdapter) {
            mDecorationBidderAdapter = new DecorationBidderAdapter(this, mBidders, mNeeds_id);
        }
        mListView.setDivider(null);
        mListView.setAdapter(mDecorationBidderAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.title_designer_count));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.title_bidder_introduce));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));

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
