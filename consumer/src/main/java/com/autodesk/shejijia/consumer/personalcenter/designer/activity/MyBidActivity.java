package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.MyBidBean;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidBidingFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidFailureFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidSuccessFragment;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author luchongbin .
 * @version 1.0 .
 * @date 16-6-7
 * @file MyBidActivity.java  .
 * @brief .
 */
public class MyBidActivity extends NavigationBarActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_bid_view;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_bid_be_being_bid = (TextView) findViewById(R.id.tv_bid_be_being_bid);
        tv_my_bid_bingo_bid = (TextView) findViewById(R.id.tv_my_bid_bingo_bid);
        tv_my_bid_outflow_bid = (TextView) findViewById(R.id.tv_my_bid_outflow_bid);
        mBeingDidLine = (TextView) findViewById(R.id.tv_bid_be_being_bid_line);
        mMyBidDidLine = (TextView) findViewById(R.id.tv_my_bid_bingo_bid_line);
        mOutFlowLine = (TextView) findViewById(R.id.tv_my_bid_outflow_bid_line);

        resetTextTitleColor();
        setDefaultFragment();
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.response_manage));
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_bid_be_being_bid.setOnClickListener(this);
        tv_my_bid_bingo_bid.setOnClickListener(this);
        tv_my_bid_outflow_bid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetTextTitleColor();
        switch (v.getId()) {
            case R.id.tv_bid_be_being_bid:
                setColorAndBackgroundForTextView(tv_bid_be_being_bid);
                setTextColor(new TextView[]{tv_my_bid_bingo_bid, tv_my_bid_outflow_bid});
                mBeingDidLine.setVisibility(View.VISIBLE);
                setTextLineVisibility(new TextView[]{mMyBidDidLine, mOutFlowLine});
                if (beBeingFragment == null) {
                    beBeingFragment = new BidBidingFragment();
                }
                switchFragment(beBeingFragment);

                break;

            case R.id.tv_my_bid_bingo_bid:
                setColorAndBackgroundForTextView(tv_my_bid_bingo_bid);
                setTextColor(new TextView[]{tv_bid_be_being_bid, tv_my_bid_outflow_bid});
                mMyBidDidLine.setVisibility(View.VISIBLE);
                setTextLineVisibility(new TextView[]{mBeingDidLine, mOutFlowLine});
                if (bingoFragment == null) {
                    bingoFragment = new BidSuccessFragment();
                }
                switchFragment(bingoFragment);
                break;

            case R.id.tv_my_bid_outflow_bid:
                setColorAndBackgroundForTextView(tv_my_bid_outflow_bid);
                setTextColor(new TextView[]{tv_bid_be_being_bid, tv_my_bid_bingo_bid});
                mOutFlowLine.setVisibility(View.VISIBLE);
                setTextLineVisibility(new TextView[]{mBeingDidLine, mMyBidDidLine});
                if (outflowFragment == null) {
                    outflowFragment = new BidFailureFragment();
                }
                switchFragment(outflowFragment);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setColorAndBackgroundForTextView(TextView textView) {
        textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
//        textView.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_blue_noradius));
    }

    private void setTextColor(TextView[] textViews) {
        for (TextView textView : textViews) {
//            setTvColor(textView);
            textView.setTextColor(UIUtils.getColor(R.color.bg_33));
        }
    }

    private void setTextLineVisibility(TextView[] textViewLines){
        for (TextView textView : textViewLines){
            textView.setVisibility(View.GONE);
        }
    }

    private void resetTextTitleColor(){
        tv_bid_be_being_bid.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        tv_my_bid_bingo_bid.setTextColor(UIUtils.getColor(R.color.bg_33));
        tv_my_bid_outflow_bid.setTextColor(UIUtils.getColor(R.color.bg_33));
    }

    /**
     * set the view border and solid
     *
     * @param mOrder
     */
    private void setTvColor(TextView mOrder) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // solid
        drawable.setStroke(UIUtils.dip2px(MyBidActivity.this, 1), UIUtils.getColor(R.color.bg_0084ff));
        drawable.setColor(UIUtils.getColor(R.color.white)); // Border color
        mOrder.setBackground(drawable); // set background
    }

    /**
     * 全部
     */
    private void setDefaultFragment() {
        beBeingFragment = new BidBidingFragment();
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.fl_my_bid_container, beBeingFragment)
                .commit();
        fromFragment = beBeingFragment;
    }

    /**
     * 切换fragment
     *
     * @param
     * @param to
     */
    private void switchFragment(Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(fromFragment).add(R.id.fl_my_bid_container, to).commit();
        } else {
            transaction.hide(fromFragment).show(to).commit();
        }
        fromFragment = to;
    }

    private TextView tv_bid_be_being_bid;
    private TextView tv_my_bid_bingo_bid;
    private TextView tv_my_bid_outflow_bid;
    private TextView mBeingDidLine,mMyBidDidLine,mOutFlowLine;
    private Fragment beBeingFragment, bingoFragment, outflowFragment;
    private Fragment fromFragment;
    private FragmentManager fragmentManager;
}
