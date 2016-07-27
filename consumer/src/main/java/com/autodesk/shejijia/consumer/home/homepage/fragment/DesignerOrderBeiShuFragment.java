package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderBeiShuFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderCommonFragment;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-19
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 设计师：快屋设计师（北舒）数据展示 .
 */
public class DesignerOrderBeiShuFragment extends BaseFragment implements View.OnClickListener{
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_order_beishu;
    }

    @Override
    protected void initView() {

        mOrderContainer = (FrameLayout)rootView.findViewById(R.id.fl_designer_order_beishu_container);
        mBeishuOrder = (TextView) rootView.findViewById(R.id.tv_designer_order_beishu);

        mOrder = (TextView) rootView.findViewById(R.id.tv_designer_order);

    }

    @Override
    protected void initData() {
        //setTitleForNavbar(UIUtils.getString(R.string.decoration_order));
        setDefaultFragment();

        initMemberEntity();

    }

    private void initMemberEntity() {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (memberEntity == null) {
            return;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBeishuOrder.setOnClickListener(this);
        mOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.tv_designer_order_beishu: /// 北舒键 .
                mBeishuOrder.setTextColor(UIUtils.getColor(R.color.white));
                mBeishuOrder.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_blue_noradius));
                setTvColor(mOrder);
                mOrder.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
                if (mBeishuMealFragment == null) {
                    mBeishuMealFragment = new OrderBeiShuFragment();
                }
                switchFragment(mCommonOrderFragment, mBeishuMealFragment);
                break;
            case R.id.tv_designer_order: /// 普通项目键 .

                mOrder.setTextColor(UIUtils.getColor(R.color.white));
                mOrder.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_blue_noradius));
                setTvColor(mBeishuOrder);
                mBeishuOrder.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
                if (mCommonOrderFragment == null) {
                    mCommonOrderFragment = new OrderCommonFragment();
                }
                switchFragment(mBeishuMealFragment, mCommonOrderFragment);
                break;
            default:
                break;
        }
    }

    /**
     * @param mOrder
     * @brief set the view border and solid .
     */
    private void setTvColor(TextView mOrder) {
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // solid
        drawable.setStroke(UIUtils.dip2px(getActivity(), 1), UIUtils.getColor(R.color.bg_0084ff));
        drawable.setColor(UIUtils.getColor(R.color.white)); // Border color
        mOrder.setBackground(drawable); // set background
    }

    /**
     * @param from
     * @param to
     * @brief 切换fragment .
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (!to.isAdded()) {    // judge the fragment
            transaction.hide(from).add(R.id.fl_designer_order_beishu_container, to).commit(); // hide current fragment，add next Fragment to Activity
        } else {
            transaction.hide(from).show(to).commit(); // hide current fragment，show next fragmetn
        }
    }

    /**
     * @brief 默认北舒套餐页面 .
     */
    public void setDefaultFragment() {
        mBeishuMealFragment = new OrderBeiShuFragment();
        fragmentManager = getChildFragmentManager();
        /*  fragmentManager.beginTransaction().add(R.id.fl_designer_order_beishu_container, mBeishuMealFragment)
                .commit();*/
        fragmentManager.beginTransaction().replace(R.id.fl_designer_order_beishu_container, mBeishuMealFragment)
                .commit();
    }

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private Fragment mBeishuMealFragment, mCommonOrderFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .
}
