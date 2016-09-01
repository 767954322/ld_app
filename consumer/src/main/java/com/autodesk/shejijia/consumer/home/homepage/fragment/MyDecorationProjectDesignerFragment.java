package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file DesignerOrderBeiShuActivity.java  .
 * @brief 我的装修项目--设计师.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment{

    public static MyDecorationProjectDesignerFragment getInstance() {
        MyDecorationProjectDesignerFragment uhf = new MyDecorationProjectDesignerFragment();
        return uhf;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {
        llFragmentContain = (LinearLayout) rootView.findViewById(R.id.ll_contain);


    }

    @Override
    protected void initData() {

        //获取设计师信息
        MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        getDesignerInfoData(mMemberEntity.getAcs_member_id(), mMemberEntity.getHs_uid());

    }

    /**
     * 设置应标的fragment
     * */
    public void setBidingFragment(){

            if (mBidBidingFragment == null) {

                mBidBidingFragment = new BidingFragment();
            }
        switchFragment(mBidBidingFragment);
    }
    /**
     * 设置设计的fragment
     * */
    public void setDesignBeiShuFragment(){

        if (mBeishuMealFragment == null  ){

            mBeishuMealFragment = new DesignerOrderBeiShuFragment();

        }

        switchFragment(mBeishuMealFragment);
    }

    /**
     * 设置设计的fragment
     * */
    public void setDesignFragment(){

        if (mCommonOrderFragment == null){

            mCommonOrderFragment = new DesignerOrderFragment();

        }

        switchFragment(mCommonOrderFragment);
    }

    /**
     * 设置施工的fragment
     * */
    public void setConstructionFragment(){

        if (mDesignerConstructionFragment == null){

            mDesignerConstructionFragment = new DesignerConstructionFragment();

        }

        switchFragment(mDesignerConstructionFragment);
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
            transaction.hide(fromFragment).add(R.id.ll_contain, to).commit();
        } else {
            transaction.hide(fromFragment).show(to).commit();
        }
        fromFragment = to;
    }

    /**
     * @brief 默认北舒套餐页面 .
     */
    public void setDefaultFragment() {
        mCommonFragment = new DesignerOrderBeiShuFragment();
//        if (designerInfoDetails.getDesigner().getIs_loho() == IS_BEI_SHU){
//
//            mCommonFragment = new DesignerOrderBeiShuFragment();
//        }else {
//
//            mCommonFragment = new DesignerOrderFragment();
//        }
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ll_contain, mCommonFragment)
                .commit();
        fromFragment = mCommonFragment;
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
                designerInfoDetails = GsonUtil.jsonToBean(jsonString, DesignerInfoDetails.class);
                setDefaultFragment();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
//                        AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError,getActivity());
            }
        });
    }

    private LinearLayout llFragmentContain;

    private TextView mBeishuOrder, mOrder;
    private Context context = getActivity();
    private FrameLayout mOrderContainer;
    private int mIsLoho;
    private static final int IS_BEI_SHU = 0;
    private boolean isRefreshJust = false;
    private DesignerInfoDetails designerInfoDetails;
    private Fragment mBeishuMealFragment, mCommonOrderFragment,mCommonFragment;
    private FragmentManager fragmentManager;
    private Fragment fromFragment;
    private FragmentTransaction transaction;
    private GradientDrawable drawable;/// set Textview bordercolor .
    private Fragment mDesignerConstructionFragment;
    private BidingFragment mBidBidingFragment;

}
