package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.RecommendListDetailActivity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.activity.RevokeCauseActivity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.OnItemButtonClickListener;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-10-21
 * @GitHub: https://github.com/meikoz
 */

public class RecommendAdapter extends CommonAdapter<RecommendDetailsBean> implements OnItemButtonClickListener {

    private boolean isDesiner = false;
    private OnRevokeCallback mCallback;

    public RecommendAdapter(Context context, List<RecommendDetailsBean> datas, int layoutId, boolean isDesiner) {
        super(context, datas, layoutId);
        this.isDesiner = isDesiner;
    }

    @Override
    public void convert(CommonViewHolder holder, final RecommendDetailsBean item) {
        if (!TextUtils.isEmpty(item.getCommunity_name())) {
            holder.setText(R.id.tv_recommend_name, UIUtils.substring(item.getCommunity_name(), 4));
        }
        holder.setText(R.id.tv_asset_id, "清单编号：" + item.getProject_number() + "");
        holder.setText(R.id.tv_reco_consumer_name, item.getConsumer_name());
        holder.setText(R.id.tv_reco_consumer_mobile, item.getConsumer_mobile());
        holder.setText(R.id.tv_reco_item_address, item.getProvince_name() + item.getCity_name() + UIUtils.getNoStringIfEmpty(item.getDistrict_name()));
        holder.setText(R.id.tv_reco_item_details_address, item.getCommunity_address());
        holder.setText(R.id.tv_create_date, item.getDate_submitted());
        convertDesignerDataUI(holder, item);
        holder.setOnClickListener(R.id.tv_edit_btn, new ItemOnClickListener(item));
        holder.setOnClickListener(R.id.tv_cancel_btn, new ItemOnClickListener(item));
        //判断显示不显示编辑按钮
        holder.setVisible(R.id.tv_edit_btn, isDesiner);
    }

    private void convertDesignerDataUI(CommonViewHolder holder, RecommendDetailsBean item) {
        String status = item.getSent_status();
        String revoke_state = item.getStatus();
        if (!TextUtils.isEmpty(revoke_state)) {
            //设计师　消费者退回的项目不应该被置灰　可以
            notifyUISetChanged(holder, revoke_state.equals("canceled") || revoke_state.equals("refused"));
            if (!TextUtils.isEmpty(status)) {
                boolean unsent = status.equals("unsent");
                holder.setVisible(R.id.iv_reco_wfsico, isDesiner ? unsent : false);
            }
            //如果是canceled那么就是显示撤销原因：othe如果不是退回原因下边回隐藏
            holder.setText(R.id.tv_revoke_cause, revoke_state.equals("canceled") ? "撤销原因：" : "退回原因：");
        }
        holder.setText(R.id.tv_revoke_cause_details, item.getRemark());
        if (!TextUtils.isEmpty(status)) {
            //消费者显示退回　－　设计是显示删除和撤销
            holder.setText(R.id.tv_cancel_btn, isDesiner ? (status.equals("unsent") ? "删除" : "撤销") : "退回");
        }
    }

    //更新撤销之后显示样式
    private void notifyUISetChanged(CommonViewHolder holder, boolean hasRevoke) {
        updateRevokeUI(hasRevoke,
                holder.getView(R.id.tv_recommend_name),
                holder.getView(R.id.tv_reco_consumer_name),
                holder.getView(R.id.tv_reco_consumer_mobile),
                holder.getView(R.id.tv_reco_item_address),
                holder.getView(R.id.tv_reco_item_details_address),
                holder.getView(R.id.tv_create_date),
                holder.getView(R.id.tv_item_name),
                holder.getView(R.id.tv_item_mobile),
                holder.getView(R.id.tv_item_address),
                holder.getView(R.id.tv_item_location),
                holder.getView(R.id.tv_item_create_data)
        );
        holder.setVisible(R.id.ll_revoke_view, !hasRevoke);
        holder.setVisible(R.id.rlt_revoke_cause, hasRevoke);

    }

    //撤销成功之后全部置灰
    private void updateRevokeUI(boolean hasRevoke, View... view) {
        for (View mTextView : view) {
            ((TextView) mTextView).setTextColor(UIUtils.getColor(
                    hasRevoke ? R.color.font_gray : R.color.mybid_text_color_normal
            ));
        }
    }


    @Override
    public void onItemDeteleOnClick(String text, final RecommendDetailsBean item) {
        Log.d("onClick", "删除");
        new AlertView(null, "您确定要删除吗?", "取消", null, new String[]{UIUtils.getString(R.string.sure)}, mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != -1)
                    deleteItemRecommend(item);
            }
        }).show();
    }

    private void deleteItemRecommend(RecommendDetailsBean item) {
        final String member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
        MPServerHttpManager.getInstance().deleteItemRecommend(member_id, item.getAsset_id(), new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MPNetworkUtils.logError("deleteItemRecommend", error);
                if (mCallback != null)
                    mCallback.onRevokeFailer();
            }

            @Override
            public void onResponse(JSONObject object) {
                if (mCallback != null)
                    mCallback.onRevokeSuccessFul();
            }
        });
    }

    @Override
    public void onItemReturnOnClick(String text, final RecommendDetailsBean item) {
        //设计师撤销和消费者退回
        Log.d("onClick", "退回");
        final JSONObject object1 = new JSONObject();
        try {
            object1.put("remark", "原因");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new AlertView(null, text.equals("撤销") ? "您确定要撤销吗?" : "您确定要退回吗?", "取消", null, new String[]{UIUtils.getString(R.string.sure)}, mContext, AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object object, int position) {
                if (position != -1)
                    revokeRecommend(item.getAsset_id(), object1);
            }
        }).show();
    }

    class ItemOnClickListener implements View.OnClickListener {
        private RecommendDetailsBean mItem;

        public ItemOnClickListener(RecommendDetailsBean item) {
            this.mItem = item;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_cancel_btn:
                    //消费者退回 - 设计师撤销
                    String text = (String) ((TextView) v).getText();
                    if (text.equals("撤销") || text.equals("退回")) {
                        RevokeCauseActivity.jumpTo(mContext, mItem.getAsset_id(), text);
//                        onItemReturnOnClick(text, mItem);
                    } else if (text.equals("删除")) {
                        onItemDeteleOnClick(text, mItem);
                    }
                    break;
                case R.id.tv_edit_btn:
                    //消费者编辑
                    RecommendListDetailActivity.actionStartActivity(mContext, mItem.getAsset_id() + "");
                    break;
            }
        }
    }

    public interface OnRevokeCallback {
        void onRevokeSuccessFul();

        void onRevokeFailer();
    }

    public void setOnRevokeCallback(OnRevokeCallback callback) {
        this.mCallback = callback;
    }

    //撤销和退回逻辑
    private void revokeRecommend(int id, JSONObject object1) {
        MPServerHttpManager.getInstance().revokeRecommend(id, object1, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mCallback != null)
                    mCallback.onRevokeFailer();
            }

            @Override
            public void onResponse(JSONObject object) {
                if (mCallback != null)
                    mCallback.onRevokeSuccessFul();
            }
        });
    }
}
