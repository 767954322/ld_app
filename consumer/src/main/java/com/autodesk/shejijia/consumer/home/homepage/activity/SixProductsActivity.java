package com.autodesk.shejijia.consumer.home.homepage.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter.SixProductsAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.matertab.MaterialTabs;
import com.autodesk.shejijia.shared.components.common.uielements.slippingviewpager.NoSlippingViewPager;
import com.autodesk.shejijia.shared.components.common.utility.DensityUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.constants.BroadCastInfo;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.socks.library.KLog;

/**
 * Created by luchongbin on 16-8-16.
 */
public class SixProductsActivity extends NavigationBarActivity {


    private SixProductsAdapter sixProductsAdapter;
    private NoSlippingViewPager noSlippingViewPager;
    private MaterialTabs pagerSlidingTabStrip;
    private SignInNotificationReceiver signInNotificationReceiver;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_six_products;
    }
    @Override
    protected void initView() {
        pagerSlidingTabStrip = (MaterialTabs) findViewById(R.id.consumer_six_products_tabs);
        noSlippingViewPager = (NoSlippingViewPager)findViewById(R.id.consumer_six_products_viewPager);
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitleForNavbar(UIUtils.getString(R.string.tab_six_products));
        noSlippingViewPager.setPagingEnabled(false);
        registerBroadCast();
        String[] tabItems =this.getResources().getStringArray(R.array.sixProducts);

        sixProductsAdapter = new SixProductsAdapter(getSupportFragmentManager(), tabItems);
        noSlippingViewPager.setAdapter(sixProductsAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        noSlippingViewPager.setPageMargin(pageMargin);


        pagerSlidingTabStrip.setBackgroundColor(Color.WHITE);//Tab的背景色
        pagerSlidingTabStrip.setIndicatorColor(UIUtils.getColor(R.color.tx_ef));//下滑指示器的颜色
        pagerSlidingTabStrip.setIndicatorHeight(DensityUtil.dip2px(this, 2));//下滑指示器的高度
        pagerSlidingTabStrip.setTextColorSelected(UIUtils.getColor(R.color.tx_ef));
        pagerSlidingTabStrip.setTextColorUnselected(UIUtils.getColor(R.color.my_project_title_text_color));//设置未选中的tab字体颜色
//        pagerSlidingTabStrip.setTabPaddingLeftRight(29);//设置tab距离左右的padding值
        pagerSlidingTabStrip.setTabTypefaceSelectedStyle(Typeface.NORMAL);//选中时候字体
        pagerSlidingTabStrip.setTabTypefaceUnselectedStyle(Typeface.NORMAL);//未选中时候字体
        pagerSlidingTabStrip.setTextSize(DensityUtil.dip2px(this, 15));
        pagerSlidingTabStrip.setSameWeightTabs(true);
//        pagerSlidingTabStrip.setPaddingMiddle(true);//设置tab控件居中
        pagerSlidingTabStrip.setOnClickItemListener(new MaterialTabs.OnClickItemListener() {
            @Override
            public void onClickItemListener(int position) {
                setImageForNavButton(ButtonType.RIGHT, R.drawable.work_room_explain);
                if (position == 1){

                    setVisibilityForNavButton(ButtonType.RIGHT, true);
                }else {
                    setVisibilityForNavButton(ButtonType.RIGHT, false);
                }
            }
        });

        pagerSlidingTabStrip.setViewPager(noSlippingViewPager);
        noSlippingViewPager.setCurrentItem(0);

    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);

        new AlertView(UIUtils.getString(R.string.rule_instructions), UIUtils.getString(R.string.rule_instructions_content), null, null, new String[]{"关闭"}, SixProductsActivity.this,
                AlertView.Style.Alert, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == IssueDemandActivity.RESULT_CODE){
            setResult(IssueDemandActivity.RESULT_CODE, data);
            finish();
        }
    }

    private void registerBroadCast() {
        signInNotificationReceiver = new SignInNotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastInfo.LOGIN_ACTIVITY_FINISHED);
        registerReceiver(signInNotificationReceiver, filter);
    }

    /**
     * 全局的广播接收者,用于处理登录后数据的操作
     */
    private class SignInNotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if  (action.equalsIgnoreCase(BroadCastInfo.LOGIN_ACTIVITY_FINISHED)) {
                String strToken = intent.getStringExtra(BroadCastInfo.LOGIN_TOKEN);
                MemberEntity entity = GsonUtil.jsonToBean(strToken, MemberEntity.class);
                //设计师的话返回首页
                if(entity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(entity.getMember_type())){
                    SixProductsActivity.this.finish();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(signInNotificationReceiver);
    }
}
