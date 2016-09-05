package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.EliteFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderBeiShuFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderCommonFragment;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-7-19 .
 * @file DesignerOrderActivity.java .
 * @brief 普通设计师, 普通订单 .
 */
public class DesignerOrderFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout mLlBeishuBorder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_order;
    }

    @Override
    protected void initView() {
        mLlBeishuBorder = (LinearLayout) rootView.findViewById(R.id.ll_beishu_border);
        mOrderContainer = (FrameLayout) rootView.findViewById(R.id.fl_designer_order_beishu_container);
        mBeishuOrder = (TextView) rootView.findViewById(R.id.tv_designer_order_beishu);
        mOrder = (TextView) rootView.findViewById(R.id.tv_designer_order);
    }

    @Override
    protected void initData() {
        ///   fixme 以下代码导致竞逻辑缺失，需要和崇斌一块讨论 .
//        setEliteFragment();
        setCommonOrderFragment();
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

            case R.id.tv_designer_order: /// 竟优项目 .

                setColorAndBackgroundForTextView(mOrder);
                setTextColor(new TextView[]{mBeishuOrder});

                if (mCommonOrderFragment == null) {
                    mCommonOrderFragment = new OrderCommonFragment();
                }
                switchFragment(mCommonOrderFragment);
                break;
            case R.id.tv_designer_order_beishu: /// 套餐 .
                setColorAndBackgroundForTextView(mBeishuOrder);
                setTextColor(new TextView[]{mOrder});
                if (mBeishuMealFragment == null) {
                    mBeishuMealFragment = new OrderBeiShuFragment();
                }
                switchFragment(mBeishuMealFragment);
                break;

            default:
                break;
        }
    }

    private void setTextColor(TextView[] textViews) {
        for (TextView textView : textViews) {
            setTvColor(textView);
            textView.setTextColor(UIUtils.getColor(R.color.bg_0084ff));
        }
    }

    /**
     * @param view
     * @brief set the view border and solid .
     */
    private void setTvColor(TextView view) {
        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // solid
        drawable.setStroke(UIUtils.dip2px(getActivity(), 1), UIUtils.getColor(R.color.bg_0084ff));
        drawable.setColor(UIUtils.getColor(R.color.white)); // Border color
        view.setBackground(drawable); // set background

    }

    private void setColorAndBackgroundForTextView(TextView textView) {
        textView.setTextColor(UIUtils.getColor(R.color.white));
        textView.setBackground(UIUtils.getDrawable(R.drawable.bg_common_btn_blue_noradius));
    }

    /**
     * @param to
     * @brief 切换fragment .
     */
    public void switchFragment(Fragment to) {
        if (!to.isAdded()) {    // judge the fragment
            transaction.hide(fromFragment).add(R.id.fl_designer_order_beishu_container, to).commit(); // hide current fragment，add next Fragment to Activity
        } else {
            transaction.hide(fromFragment).show(to).commit(); // hide current fragment，show next fragmetn
        }
        fromFragment = to;
    }

    /**
     * 精选
     */
    public void setEliteFragment() {
        mCommonOrderFragment = new EliteFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_designer_order_beishu_container, mCommonOrderFragment)
                .commit();
        fromFragment = mCommonOrderFragment;
    }

    /**
     * 竞优
     */
    public void setCommonOrderFragment() {
        mCommonOrderFragment = new OrderCommonFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_designer_order_beishu_container, mCommonOrderFragment)
                .commit();
        fromFragment = mCommonOrderFragment;
    }

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private Fragment mBeishuMealFragment, mCommonOrderFragment, fromFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .

}
