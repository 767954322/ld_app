package com.autodesk.shejijia.consumer.codecorationBase.coelite.fragment;



import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.activity.IssueEliteDemanActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter.SelectionAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.entity.DesignWorksBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.List;

/**
 * 精选
 */
public class CoEliteFragment extends BaseFragment implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private ViewPager vpSelection;
    private ViewGroup vgSelection;
    private ImageButton imReservationButton;
    private boolean falg = false;


    /**
     * 装点点的ImageView数组
     */
    private ImageView[] tips;

    private ImageView[] mImageViews;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_selection;
    }

    @Override
    protected void initView() {
        vgSelection = (ViewGroup)rootView.findViewById(R.id.vgSelection);
        vpSelection = (ViewPager) rootView.findViewById(R.id.vpSelection);
        imReservationButton = (ImageButton) rootView.findViewById(R.id.imReservationButton);
    }

    @Override
    protected void initData() {
        if(falg){
            return;
        }
        //载入图片资源
        getDesignWorks();
    }

    /**
     * 更新UI
     * @param myBidBean
     */
    private void updataView(DesignWorksBean myBidBean){
        int size = 1;
        if(myBidBean != null){
            List<DesignWorksBean.InnerPicListBean> innerPicListBeans=  myBidBean.getInnerPicList();
            size = innerPicListBeans.size();
            addBackgroundForImageView(innerPicListBeans);
        }else{
            ImageView imageView = new ImageView(activity);
            mImageViews[0] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(R.drawable.pic1_ico2x);

        }
        addImageViewtips(size);
        vpSelection.setAdapter(new SelectionAdapter(mImageViews));
        vpSelection.setOnPageChangeListener(this);
    }
    /**
     * 给每一个ImageView添加背景图
     */
    private void addBackgroundForImageView( List<DesignWorksBean.InnerPicListBean> innerPicListBeans){
        int i=0;
        mImageViews = new ImageView[innerPicListBeans.size()];
        for(DesignWorksBean.InnerPicListBean innerPicListBean:innerPicListBeans){
            ImageView imageView = new ImageView(activity);
            mImageViews[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtils.loadImageIcon(imageView,innerPicListBean.getAndroid());
            i++;
        }
    }
    /**
     * 将点点加入到ViewGroup中
     */
    private void addImageViewtips(int size){

        tips = new ImageView[size];
        for(int i=0; i<tips.length; i++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams LayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LayoutParams.setMargins(10,0,10,0);
            imageView.setLayoutParams(LayoutParams);
            tips[i] = imageView;
            if(i == 0){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
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
        switch (v.getId()){
            case R.id.imReservationButton:
                showIssueDemandActivity();

                break;
            default:
                break;
        }
    }
    private void showIssueDemandActivity(){

        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null == mMemberEntity){
            AdskApplication.getInstance().doLogin(getActivity());
            return;
        }
        String nick_name = (mMemberEntity != null && mMemberEntity.getNick_name() != null
                && mMemberEntity.getNick_name().length() > 0)?mMemberEntity.getNick_name():UIUtils.getString(R.string.anonymity);
        Intent intent = new Intent(getActivity(), IssueEliteDemanActivity.class);
        intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, nick_name);
        startActivity(intent);
    }

    //载入设计师作品
    private void getDesignWorks(){
        CustomProgress.showDefaultProgress(activity);
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                falg = true;
                CustomProgress.cancelDialog();
                String str = GsonUtil.jsonToString(jsonObject);
                DesignWorksBean myBidBean = GsonUtil.jsonToBean(str, DesignWorksBean.class);
                updataView(myBidBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                updataView(null);
            }
        };
        MPServerHttpManager.getInstance().getDesignWorks(okResponseCallback);

    }


    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }
    @Override
    public void onPageSelected(int arg0) {
        setImageBackground(arg0 % mImageViews.length);
    }

    private void setImageBackground(int selectItems){
        for(int i=0; i<tips.length; i++){
            if(i == selectItems){
                tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
            }else{
                tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
        }
    }

}