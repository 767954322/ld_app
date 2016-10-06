package com.autodesk.shejijia.consumer.codecorationBase.coelite.activity;

import android.content.Intent;
import android.os.Bundle;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter.SelectDesignAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.DecorationNeedsListBean;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.uielements.ListViewForScrollView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.List;
/**
 * @author  .
 * @version 1.0 .
 * @date 16-8-16
 * @file SelectDesignerActivity.java  .
 * @brief 精选派单设计师 -选TA量房.
 */

public class SelectDesignerActivity extends NavigationBarActivity implements SelectDesignAdapter.MeasureFormCallBack{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_select_designer;
    }

    @Override
    protected void initView() {
        lvselectionDesign = (ListViewForScrollView)findViewById(R.id.lv_selection_design);
    }
    @Override
    protected void initExtraBundle() {
        decorationNeedsListBean =(DecorationNeedsListBean)getIntent().getSerializableExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN);
        decorationBiddersBeans = decorationNeedsListBean.getBidders();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTitleForNavbar(UIUtils.getString(R.string.send_design));
        falg = false;
        isSelected();
        if(selectDesignAdapter == null){
            selectDesignAdapter = new SelectDesignAdapter(this,decorationBiddersBeans,R.layout.item_select_designer,falg);
        }
        lvselectionDesign.setAdapter(selectDesignAdapter);

    }
    private void isSelected(){
        for(DecorationBiddersBean decorationBiddersBean:decorationBiddersBeans) {
            String wk_cur_sub_node_id = decorationBiddersBean.getWk_cur_sub_node_id();
            int i = Integer.parseInt(wk_cur_sub_node_id != null ? wk_cur_sub_node_id : "-1");
            if (i >= 11 && i != 24) {
                falg =true;
                break;
            }

        }
    }

    //选TA量房

    @Override
    public void measureForm(String designer_id) {
        Intent intent =  new Intent(this,SolicitationDesignerActivity.class);
        intent.putExtra(Constant.ConsumerDecorationFragment.DECORATIONbIDDERBEAN,decorationNeedsListBean);
        intent.putExtra(Constant.SeekDesignerDetailKey.DESIGNER_ID,designer_id);
        this.startActivityForResult(intent,1010);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            finish();
    }
    private ListViewForScrollView lvselectionDesign;
    private List<DecorationBiddersBean> decorationBiddersBeans;
    private SelectDesignAdapter selectDesignAdapter;
    private DecorationNeedsListBean decorationNeedsListBean;
    private boolean falg;
}
