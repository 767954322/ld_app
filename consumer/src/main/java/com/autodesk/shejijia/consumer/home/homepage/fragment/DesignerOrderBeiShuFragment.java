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
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.EliteFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.OrderCommonFragment;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_order_beishu;
    }

    @Override
    protected void initView() {

        mOrderContainer = (FrameLayout)rootView.findViewById(R.id.fl_designer_order_beishu_container);
        mOrder = (TextView) rootView.findViewById(R.id.tv_designer_order);
        tvEliteProject = (TextView) rootView.findViewById(R.id.tv_elite_project);

    }

    @Override
    protected void initData() {
        setDefaultFragment();
    }
    @Override
    protected void initListener() {
        super.initListener();
        mOrder.setOnClickListener(this);
        tvEliteProject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        transaction = fragmentManager.beginTransaction();
//
//        if (mBeishuMealFragment == null) {
//            mBeishuMealFragment = new OrderBeiShuFragment();
//        }
//
//        if (mCommonOrderFragment == null) {
//            mCommonOrderFragment = new OrderCommonFragment();
//        }

        switch (v.getId()) {
            case R.id.tv_elite_project:///精选项目
                setColorAndBackgroundForTextView(tvEliteProject);
                setTextColor(new TextView[]{mOrder});
                if(eliteFragment == null){
                    eliteFragment = new EliteFragment();
                }
                switchFragment(eliteFragment);
                break;
            case R.id.tv_designer_order: /// 竟优项目 .

                setColorAndBackgroundForTextView(mOrder);
                setTextColor(new TextView[]{tvEliteProject});

                if (mCommonOrderFragment == null) {
                    mCommonOrderFragment = new OrderCommonFragment();
                }
                switchFragment(mCommonOrderFragment);
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
    private void setTvColor(TextView  view) {
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
     * @brief 默认北舒套餐页面 .
     */
    public void setDefaultFragment() {
        eliteFragment = new EliteFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_designer_order_beishu_container, eliteFragment)
                .commitAllowingStateLoss();
        fromFragment = eliteFragment;
    }

    private TextView mOrder,tvEliteProject;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private Fragment  mCommonOrderFragment,eliteFragment,fromFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .
}
