package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.Wk3DPlanShowActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean.DesignFileEntity;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-8 .
 * @file Wk3DPlanAdapter.java .
 * @brief 3d设计方案适配器 .
 */
public class Wk3DPlanAdapter extends CommonAdapter<DesignFileEntity> {

    public interface OnItemCheckListener {
        void onItemCheck(ToggleButton view, int position,
                         boolean isChecked, ImageButton chooseBt);
    }

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.mOnItemCheckListener = onItemCheckListener;
    }

    /**
     * 普通level构造
     *
     * @param context
     * @param designFileEntities_3DPlan
     */
    public Wk3DPlanAdapter(Context context, ArrayList<DesignFileEntity> designFileEntities_3DPlan, ArrayList<String> selectedDataList) {
        super(context, designFileEntities_3DPlan, R.layout.item_gridview_zero_3dplan);
        this.context = context;
        this.designFileEntities_3DPlan = designFileEntities_3DPlan;
        this.selectedDataList = selectedDataList;
    }

    @Override
    public void convert(CommonViewHolder holder, final DesignFileEntity designFileEntity) {
        /// no_pic .
        final ImageView mImageVShow = holder.getView(R.id.iv_show_3dplan);
        mImageVShow.setImageDrawable(UIUtils.getDrawable(R.drawable.common_case_icon));
        /// no_select .
        holder.setImageResource(R.id.ibn_choosedbt, R.drawable.icon_common_radio_off);
        /**
         * 3D方案的缩略图
         */
        String link = designFileEntity.getLink();
        String str = link.substring(link.lastIndexOf('.') + 1);
        if (Constant.DocumentTypeKey.TYPE_PNG.equals(str) || Constant.DocumentTypeKey.TYPE_JPG.equals(str)) {
            ImageUtils.displayIconImage(link, mImageVShow);
        }

        setReflectIcon(mImageVShow, str);

        /**
         * 3D方案的名字
         */
        String name = designFileEntity.getName();
        if (TextUtils.isEmpty(name)) {
            holder.setText(R.id.tv_3dplan_name, UIUtils.getString(R.string.three_plan));
        } else {
            holder.setText(R.id.tv_3dplan_name, name);
        }

        final ImageButton mChooseBt = holder.getView(R.id.ibn_choosedbt);
        ToggleButton toggleButton = holder.getView(R.id.toggle_button);

        int position = holder.getPosition();
        if (designFileEntities_3DPlan == null || designFileEntities_3DPlan.size() < 1) {
            return;
        }
        String design_file_id = designFileEntities_3DPlan.get(position).getId();
        toggleButton.setTag(holder.getPosition());
        mChooseBt.setTag(holder.getPosition());
        mImageVShow.setTag(holder.getPosition());
        toggleButton.setOnClickListener(new ToggleClickListener(mChooseBt));

        if (selectedDataList != null && selectedDataList.size() > 0 && selectedDataList.contains(design_file_id)) {
            toggleButton.setChecked(true);
            mChooseBt.setImageResource(R.drawable.icon_common_radio_on);
        } else {
            toggleButton.setChecked(false);
            mChooseBt.setImageResource(R.drawable.icon_common_radio_off);
        }

        mImageVShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Wk3DPlanShowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN, designFileEntity);
                bundle.putString(Constant.DeliveryShowBundleKey._JAVA_BEAN, Constant.DeliveryShowBundleKey.DESIGN_DELIVERY_OTHERS);
                bundle.putBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG, true);
                intent.putExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT, bundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void setReflectIcon(ImageView imageView, String str) {
        if (Constant.DocumentTypeKey.TYPE_DOCX.equals(str) || Constant.DocumentTypeKey.TYPE_DOC.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_world));
        } else if (Constant.DocumentTypeKey.TYPE_XLSX.equals(str) || Constant.DocumentTypeKey.TYPE_XLS.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_excel));
        } else if (Constant.DocumentTypeKey.TYPE_PDF.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_pdf));
        }
    }

    private class ToggleClickListener implements View.OnClickListener {
        ImageButton chooseBt;

        public ToggleClickListener(ImageButton chooseBt) {
            this.chooseBt = chooseBt;
        }

        @Override
        public void onClick(View view) {
            if (view instanceof ToggleButton) {
                ToggleButton toggleButton = (ToggleButton) view;
                int position = (Integer) toggleButton.getTag();
                if (designFileEntities_3DPlan != null && mOnItemCheckListener != null
                        && position < designFileEntities_3DPlan.size()) {
                    mOnItemCheckListener.onItemCheck(toggleButton, position, toggleButton.isChecked(), chooseBt);
                }
            }
        }
    }

    private final String TAG = getClass().getSimpleName();
    private OnItemCheckListener mOnItemCheckListener;
    private ArrayList<String> selectedDataList = new ArrayList<>();
    private ArrayList<DesignFileEntity> designFileEntities_3DPlan;
    private Context context;
}
