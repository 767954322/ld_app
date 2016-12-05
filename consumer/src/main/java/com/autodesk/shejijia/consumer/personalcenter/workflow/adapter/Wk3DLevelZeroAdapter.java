package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.consumer.uielements.DeliverySelector;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-18 .
 * @file Wk3DLevelZeroAdapter.java .
 * @brief 　交付3D方案适配器 .
 */
public class Wk3DLevelZeroAdapter extends CommonAdapter<Wk3DPlanListBean> {

    public interface OnItemCheckListener {
        void onItemCheck(ToggleButton view, int position,
                         boolean isChecked, ImageButton chooseBt);
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.mOnItemCheckListener = onItemCheckListener;
    }

    public Wk3DLevelZeroAdapter(Context context, ArrayList<Wk3DPlanListBean> wk3DPlanListBeanArrayList, String selectedId) {
        super(context, wk3DPlanListBeanArrayList, R.layout.item_gridview_zero_3dplan);
        this.wk3DPlanListBeanArrayList = wk3DPlanListBeanArrayList;
        this.mContext = context;
        this.selectedId = selectedId;
    }

    @Override
    public void convert(final CommonViewHolder holder, final Wk3DPlanListBean wk3DPlanListBean) {
        /// no_pic .
        holder.setImageResource(R.id.iv_show_3dplan, R.drawable.common_case_icon);
        /// no_select .
        holder.setImageResource(R.id.ibn_choosedbt, R.drawable.icon_common_radio_off);

        /**
         * 3D方案的缩略图
         */
        ImageUtils.displayIconImage(wk3DPlanListBean.getLink(), (ImageView) holder.getView(R.id.iv_show_3dplan));

        /**
         * 3D方案的名字
         */
        String name = wk3DPlanListBean.getDesign_name();
        if (TextUtils.isEmpty(name)) {
            holder.setText(R.id.tv_3dplan_name, UIUtils.getString(R.string.three_plan_no_name));
        } else {
            holder.setText(R.id.tv_3dplan_name, name);
        }

        final ImageView mShowImageView = holder.getView(R.id.iv_show_3dplan);
        final ImageButton mChooseBt = holder.getView(R.id.ibn_choosedbt);
        ToggleButton toggleButton = holder.getView(R.id.toggle_button);

        final String design_asset_id = wk3DPlanListBeanArrayList.get(holder.getPosition()).getDesign_asset_id();

        toggleButton.setTag(holder.getPosition());
        mChooseBt.setTag(holder.getPosition());
        mShowImageView.setTag(holder.getPosition());
        toggleButton.setOnClickListener(new ToggleClickListener(mChooseBt));


        if (DeliverySelector.isSelected && !TextUtils.isEmpty(selectedId) && selectedId.contains(design_asset_id)) {
            toggleButton.setChecked(true);
            mChooseBt.setImageResource(R.drawable.icon_common_radio_on);
        } else {
            toggleButton.setChecked(false);
            mChooseBt.setImageResource(R.drawable.icon_common_radio_off);
        }

        mShowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*用户在Web端查看*/
                new AlertView(UIUtils.getString(R.string.common_tip), UIUtils.getString(R.string.delivery_tip_3d), null, null, new String[]{UIUtils.getString(R.string.sure)}, mContext, AlertView.Style.Alert, null).show();
            }
        });
    }

    private class ToggleClickListener implements View.OnClickListener {
        ImageButton chooseBt;

        public ToggleClickListener(ImageButton choosebt) {
            this.chooseBt = choosebt;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                int position = (Integer) toggleButton.getTag();
                if (wk3DPlanListBeanArrayList != null && mOnItemCheckListener != null
                        && position < wk3DPlanListBeanArrayList.size()) {
                    mOnItemCheckListener.onItemCheck(toggleButton, position, toggleButton.isChecked(), chooseBt);
                }
            }
        }
    }


    private Context mContext;
    private String selectedId = "";
    private OnItemCheckListener mOnItemCheckListener;
    private ArrayList<Wk3DPlanListBean> wk3DPlanListBeanArrayList;

}

