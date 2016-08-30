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
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

/**
 * 精选
 */
public class CoEliteFragment extends BaseFragment implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private ViewPager vpSelection;
    private ViewGroup vgSelection;
    private ImageButton imReservationButton;

    /**
     * 图片资源id
     */
    private int[] imgIdArray ;
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
        //载入图片资源ID
//        getDesignWorks();

        imgIdArray = new int[]{R.drawable.pic1, R.drawable.pic2,R.drawable.pic3,R.drawable.pic4};
        addImageViewtips();
        addBackgroundForImageView();

        vpSelection.setAdapter(new SelectionAdapter(mImageViews));
        vpSelection.setOnPageChangeListener(this);
        vpSelection.setCurrentItem((mImageViews.length) * 100);

    }
    /**
     * 给每一个ImageView添加背景图
     */
    private void addBackgroundForImageView(){
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){

            ImageView imageView = new ImageView(getActivity());
            mImageViews[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            ImageUtils.loadImage(imageView,"http://www.ciccphoto.com/01/02/201603/W020160301562832103832.jpg");
            imageView.setBackgroundResource(imgIdArray[i]);
        }
    }
    /**
     * 将点点加入到ViewGroup中
     */
    private void addImageViewtips(){

        tips = new ImageView[imgIdArray.length];
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
        String nick_name = (mMemberEntity != null && mMemberEntity.getNick_name() != null
                && mMemberEntity.getNick_name().length() > 0)?mMemberEntity.getNick_name():UIUtils.getString(R.string.anonymity);
        Intent intent = new Intent(getActivity(), IssueEliteDemanActivity.class);
        intent.putExtra(Constant.ConsumerPersonCenterFragmentKey.NICK_NAME, nick_name);
        getActivity().startActivityForResult(intent,1002);
    }

    //载入设计师作品
    private void getDesignWorks(){
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                String str = GsonUtil.jsonToString(jsonObject);
                DesignWorksBean myBidBean = GsonUtil.jsonToBean(str, DesignWorksBean.class);
                myBidBean.getInnerPicList();
               // MyToast.show(getActivity(),"dsadasd");
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MyToast.show(getActivity(),"001");
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
