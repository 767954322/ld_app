package com.autodesk.shejijia.consumer.personalcenter.resdecoration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.adapter.DecorationBidderAdapter;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowMeasureFormActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-16 .
 * @file DecorationBidderActivity.java .
 * @brief 应标人数页面 .
 */
public class DecorationBidderActivity extends NavigationBarActivity implements DecorationBidderAdapter.OnItemViewClickCallback /*implements AdapterView.OnItemClickListener*/ {

    public static final String BIDDER_KEY = "DecorationBidderActivity";

    private ListView mListView;

    private String mNeeds_id;
    private ArrayList<DecorationBiddersBean> mBidders;
    private DecorationBidderAdapter mDecorationBidderAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_decoration_bidder;
    }

    @Override
    protected void initView() {
        super.initView();
        mListView = (ListView) findViewById(R.id.lv_designer_list);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mBidders = (ArrayList<DecorationBiddersBean>) intent.getExtras().get(BIDDER_KEY);
        mNeeds_id = intent.getExtras().getString(Constant.ConsumerDecorationFragment.NEED_ID);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.title_designer_count));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.title_bidder_introduce));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
        if (null == mBidders) {
            return;
        }
        if (null == mDecorationBidderAdapter) {
            mDecorationBidderAdapter = new DecorationBidderAdapter(this, mBidders, mNeeds_id);
        }
        mListView.setDivider(null);
        mListView.setAdapter(mDecorationBidderAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mDecorationBidderAdapter.setOnItemViewClickCallback(this);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);

        new AlertView(UIUtils.getString(R.string.title_bidder_introduce),
                UIUtils.getString(R.string.alert_bidder_introduce),
                null, null, new String[]{"确定"}, this, AlertView.Style.Alert, null).show();
    }

    @Override
    public void setOnItemViewClickCallback(String designer_id) {
        Intent intent;
        if (!TextUtils.isEmpty(mNeeds_id) && !TextUtils.isEmpty(designer_id)) {
            /***
             * 如果消费者选TA量房，根据量房表单的操作，进行逻辑操作
             */
            intent = new Intent(DecorationBidderActivity.this, FlowMeasureFormActivity.class);
            intent.putExtra(Constant.BundleKey.BUNDLE_ASSET_NEED_ID, mNeeds_id);
            intent.putExtra(Constant.BundleKey.BUNDLE_DESIGNER_ID, designer_id);
            intent.putExtra(Constant.WorkFlowStateKey.JUMP_FROM_STATE, Constant.WorkFlowStateKey.STEP_DECORATION);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data) {
            String designer_id = data.getExtras().getString(Constant.BundleKey.BUNDLE_DESIGNER_ID);
            String wk_cur_sub_node_id = data.getExtras().getString(Constant.BundleKey.BUNDLE_SUB_NODE_ID);
            int size = mBidders.size();
            for (int i = size - 1; i >= 0; i--) {
                String designer_id1 = mBidders.get(i).getDesigner_id();
                if (designer_id.equals(designer_id1)) {
                    mBidders.get(i).setWk_cur_sub_node_id(wk_cur_sub_node_id);
                }
            }
            mDecorationBidderAdapter.notifyDataSetChanged();
        }
    }
}
