package com.autodesk.shejijia.consumer.codecorationBase.average.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.activity.IssueDemandActivity;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * @author luchongbin .
 * @version 1.0 .
 * @date 16-8-16
 * @file AverageFragment.java  .
 * @brief 六大产品-竞优 .
 */

public class AverageFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mIvAverageTip;
    private ImageView mIvSendDemand;
    private ImageView mIvChart;
    private ImageView mIvBackGround;

    public AverageFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_average;
    }

    @Override
    protected void initView() {
        mIvAverageTip = (ImageView) rootView.findViewById(R.id.iv_average_tip);
        mIvSendDemand = (ImageView) rootView.findViewById(R.id.iv_send_demand);

        mIvBackGround = (ImageView) rootView.findViewById(R.id.rl_container_img);
        mIvChart = (ImageView) rootView.findViewById(R.id.average_img);
    }

    @Override
    protected void initData() {

        //bg picture load
        if (WkFlowStateMap.sixProductsPicturesBean != null) {

            String pictureUrl = WkFlowStateMap.sixProductsPicturesBean.getAndroid().getBidding().get(0).getBack();
            String backPicture[] = pictureUrl.split(",");

            ImageUtils.displaySixImage(backPicture[0],mIvBackGround);
            ImageUtils.loadImageIcon(mIvChart, backPicture[1]);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mIvAverageTip.setOnClickListener(this);
        mIvSendDemand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_send_demand: /// .
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                if (null == memberEntity) {
                    AdskApplication.getInstance().doLogin(activity);
                } else {
                    Intent intent = new Intent(activity, IssueDemandActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.iv_average_tip:
                showAlertView();
                break;
        }
    }


    private void showAlertView() {
        AlertView alertViewExt = new AlertView(UIUtils.getString(R.string.title_average_rule),
                null, null, null, new String[]{UIUtils.getString(R.string.close_alert)}, activity, AlertView.Style.Alert, null);
        ViewGroup extView = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.alert_average_new, null);
        alertViewExt.addExtView(extView);
        alertViewExt.show();
    }
}
