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
public class MyBidActivity extends NavigationBarActivity implements View.OnClickListener, BidBidingFragment.FragmentCallBack {


    public int is_loho;

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

        switch (v.getId()) {
            case R.id.tv_bid_be_being_bid:
                setColorAndBackgroundForTextView(tv_bid_be_being_bid);
                setTextColor(new TextView[]{tv_my_bid_bingo_bid, tv_my_bid_outflow_bid});
                if (beBeingFragment == null) {
                    beBeingFragment = new BidBidingFragment();
                    setArguments(beBeingFragment);
                }
                switchFragment(beBeingFragment);

                break;

            case R.id.tv_my_bid_bingo_bid:
                setColorAndBackgroundForTextView(tv_my_bid_bingo_bid);
                setTextColor(new TextView[]{tv_bid_be_being_bid, tv_my_bid_outflow_bid});
                if (bingoFragment == null) {
                    bingoFragment = new BidSuccessFragment();
                    setArguments(bingoFragment);
                }
                switchFragment(bingoFragment);
                break;

            case R.id.tv_my_bid_outflow_bid:
                setColorAndBackgroundForTextView(tv_my_bid_outflow_bid);
                setTextColor(new TextView[]{tv_bid_be_being_bid, tv_my_bid_bingo_bid});
                if (outflowFragment == null) {
                    outflowFragment = new BidFailureFragment();
                    setArguments(outflowFragment);
                }
                switchFragment(outflowFragment);
                break;
            default:
                break;
        }

    }

    @Override
    public void getMyBidBean(MyBidBean myBidBean) {
        if (mList != null) {
            mList.clear();
            mList.addAll(myBidBean.getBidding_needs_list());
            if (bingoFragment != null) {
                updateFragment(bingoFragment);
            }
            if (outflowFragment != null) {
                updateFragment(outflowFragment);
            }
        }
    }

    private void setColorAndBackgroundForTextView(TextView textView) {
        textView.setTextColor(UIUtils.getColor(R.color.white));
        textView.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_blue_noradius));
    }

    private void setTextColor(TextView[] textViews) {
        for (TextView textView : textViews) {
            setTvColor(textView);
            textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        }
    }

    /**
     * @param fragment
     * @brief 往Fragment传输数据
     */
    private void setArguments(Fragment fragment) {
        Bundle data = new Bundle();
        data.putSerializable("FragmentData", mList);
        fragment.setArguments(data);
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


    private void updateFragment(Fragment fragment) {
        if (fragment instanceof BidSuccessFragment) {
            BidSuccessFragment sub = (BidSuccessFragment) fragment;
            sub.onFragmentShown(mList);
        } else if (fragment instanceof BidFailureFragment) {
            BidFailureFragment sub = (BidFailureFragment) fragment;
            sub.onFragmentShown(mList);

        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //获取设计师信息
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == mMemberEntity) {
            return;
        }
        String member_id = mMemberEntity.getAcs_member_id();
        String hs_uid = mMemberEntity.getHs_uid();
        getDesignerInfoData(member_id, hs_uid);
        setDefaultFragment();
    }


    /**
     * 设计师个人信息
     *
     * @param designer_id
     * @param hs_uid
     */
    public void getDesignerInfoData(String designer_id, String hs_uid) {
        MPServerHttpManager.getInstance().getDesignerInfoData(designer_id, hs_uid, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                DesignerInfoDetails designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                is_loho = designerInfoDetails.getDesigner().getIs_loho();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, MyBidActivity.this);
            }
        });
    }

    private TextView tv_bid_be_being_bid;
    private TextView tv_my_bid_bingo_bid;
    private TextView tv_my_bid_outflow_bid;
    private Fragment beBeingFragment, bingoFragment, outflowFragment;
    private Fragment fromFragment;
    private ArrayList<MyBidBean.BiddingNeedsListEntity> mList = new ArrayList<>();
    private FragmentManager fragmentManager;
}
