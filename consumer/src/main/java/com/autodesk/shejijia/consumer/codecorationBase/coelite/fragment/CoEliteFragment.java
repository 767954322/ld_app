package com.autodesk.shejijia.consumer.codecorationBase.coelite.fragment;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.activity.IssueEliteDemanActivity;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter.SelectionAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.SixProductsPicturesBean;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author luchongbin .
 * @version 1.0 .
 * @date 16-8-16
 * @file CoEliteFragment.java  .
 * @brief 六大产品-精选 .
 */

public class CoEliteFragment extends BaseFragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;
    private String [] pictures;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_selection;
    }

    @Override
    protected void initView() {
        vgSelection = (ViewGroup) rootView.findViewById(R.id.vgSelection);
        vpSelection = (ViewPager) rootView.findViewById(R.id.vpSelection);
        llBackground = (LinearLayout) rootView.findViewById(R.id.llBackground);
        imReservationButton = (ImageButton) rootView.findViewById(R.id.imReservationButton);
    }

    @Override
    protected void initData() {
        SixProductsPicturesBean sixProductsPicturesBean = WkFlowStateMap.sixProductsPicturesBean;
        String backgroundURL = "";
        if(sixProductsPicturesBean != null ){
            SixProductsPicturesBean.AndroidBean.SelectionBean selectionBean = sixProductsPicturesBean.getAndroid().getSelection().get(0);
            String png = selectionBean.getPng();
            backgroundURL = sixProductsPicturesBean.getAndroid().getBackground().get(0).getPic_1();
            pictures = png.split(",");
        }
        updataView(pictures,backgroundURL);
    }

    /**
     * 更新UI
     *
     * @param pictures
     */
    private void updataView(String [] pictures,String backgroundURL) {
        int size = 1;
        if (pictures != null && pictures.length > 0) {
            size = pictures.length;
            llBackground.setBackgroundResource(R.drawable.bg_ico3x);
        }

        addImageViewtips(size);
        vpSelection.setAdapter(new SelectionAdapter(getActivity(), pictures));
        vpSelection.setOnPageChangeListener(this);
    }

    /**
     * 将点点加入到ViewGroup中
     */
    private void addImageViewtips(int size) {

        tips = new ImageView[size];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LayoutParams.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(LayoutParams);
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }

            vgSelection.addView(imageView);
        }

    }

    @Override
    protected void initListener() {
        imReservationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imReservationButton:
                showIssueDemandActivity();
                break;
            default:
                break;
        }
    }

    /**
     * 点击立即预约按钮跳转到发布精选需求界面
     */

    private void showIssueDemandActivity() {

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == mMemberEntity) {
            LoginUtils.doLogin(getActivity());
            return;
        }

        String nick_name = mMemberEntity.getNick_name() ;
        String phone_num = mMemberEntity.getMobile_number() ;
        Intent intent = new Intent(getActivity(), IssueEliteDemanActivity.class);
        intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, nick_name);
        intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.PHONE_NUMBER, phone_num);
        startActivity(intent);

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % tips.length);
    }

    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }
    private ViewPager vpSelection;
    private ViewGroup vgSelection;
    private ImageButton imReservationButton;
    private LinearLayout llBackground;

}