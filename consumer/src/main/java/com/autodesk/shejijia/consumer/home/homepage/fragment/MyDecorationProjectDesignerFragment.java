package com.autodesk.shejijia.consumer.home.homepage.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.ConsumerApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.homepage.activity.MPConsumerHomeActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.DesignBaseFragment;
import com.autodesk.shejijia.consumer.personalcenter.designer.fragment.BidBidingFragment;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateInfoBean;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date 16-7-13
 * @file MyDecorationProjectDesignerFragment.java  .
 * @brief 我的装修项目--设计师.
 */
public class MyDecorationProjectDesignerFragment extends BaseFragment {

    private BidBidingFragment mBidBidingFragment;
    private DesignBaseFragment designBaseFragment;
    private DesignerConstructionFragment mDesignerConstructionFragment;
    private Fragment fromFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_decoration_project_designer;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        int high_level_audit = ConsumerApplication.high_level_audit;
        int is_loho = ConsumerApplication.is_loho;
        setDesigneBaseFragment(high_level_audit, is_loho);
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (fromFragment != fragment) {
            if (fromFragment != null) {
                transaction.hide(fromFragment);
            }

            if (!fragment.isAdded()) {
                transaction.add(R.id.ll_contain, fragment);
            } else {
                transaction.show(fragment);
            }
            transaction.commit();
            fromFragment = fragment;
        }
    }


    /**
     * 应标
     */
    public void setBidingFragment() {
        if (mBidBidingFragment == null) {
            mBidBidingFragment = new BidBidingFragment();
        }
        switchContent(mBidBidingFragment);
    }

    public void setDesigneBaseFragment(int high_level_audit, int is_loho) {
        if (designBaseFragment == null) {
            designBaseFragment = DesignBaseFragment.newInstance(high_level_audit, is_loho);
        }
        switchContent(designBaseFragment);
    }

    /**
     * 施工
     */
    public void setConstructionFragment() {
        if (mDesignerConstructionFragment == null) {
            mDesignerConstructionFragment = new DesignerConstructionFragment();
        }
        switchContent(mDesignerConstructionFragment);
    }


    @Override
    public void onFragmentShown() {
        Log.d(TAG, "onFragmentShown: onFragmentShown");
        designBaseFragment.onFragmentShown();
        getWkFlowStatePointInformation();
    }

    /**
     * 获取全流程节点提示信息
     */
    public void getWkFlowStatePointInformation() {
        MPServerHttpManager.getInstance().getAll_WkFlowStatePointInformation(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                WkFlowStateInfoBean WkFlowStateInfoBean = GsonUtil.jsonToBean(jsonString, WkFlowStateInfoBean.class);
                List<TipWorkFlowTemplateBean> tip_work_flow_template = WkFlowStateInfoBean.getTip_work_flow_template();
                WkFlowStateMap.sWkFlowBeans = tip_work_flow_template;
            }
        });
    }
}
