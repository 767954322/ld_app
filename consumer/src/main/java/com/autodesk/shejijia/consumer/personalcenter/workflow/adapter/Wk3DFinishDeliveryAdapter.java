package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPFileBean;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanDelivery;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-18 .
 * @file Wk3DFinishDeliveryAdapter.java .
 * @brief 交付完成含有分享页面的适配器 .
 */
public class Wk3DFinishDeliveryAdapter extends CommonAdapter<MPFileBean> {

    public Wk3DFinishDeliveryAdapter(Context context, ArrayList<MPFileBean> deliveryFilesEntities) {
        super(context, deliveryFilesEntities, R.layout.item_gridview_3dplan);
        this.context = context;
        this.deliveryFilesEntities = deliveryFilesEntities;
    }

    @Override
    public void convert(final CommonViewHolder holder, final MPFileBean deliveryFilesEntity) {
        final ImageView mShowImageView = holder.getView(R.id.iv_show_3dplan);
        /**
         * 方案的缩略图
         */
        final String url = deliveryFilesEntity.getUrl();
        String str = url.substring(url.lastIndexOf('.') + 1);
        if (Constant.DocumentTypeKey.TYPE_PNG.equals(str) || Constant.DocumentTypeKey.TYPE_JPG.equals(str)) {
            ImageUtils.displayIconImage(url, mShowImageView);
        } else {
            setReflectIcon(mShowImageView, str);
        }

        /**
         * 方案的名字
         */
        String name = deliveryFilesEntity.getName();
        if (TextUtils.isEmpty(name)) {
            holder.setText(R.id.tv_3dplan_name, (UIUtils.getString(R.string.three_plan)));
        } else {
            holder.setText(R.id.tv_3dplan_name, name);
        }
    }

    private void setReflectIcon(ImageView imageView, String str) {
        if (Constant.DocumentTypeKey.TYPE_DOCX.equals(str) || Constant.DocumentTypeKey.TYPE_DOC.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_world));
        } else if (Constant.DocumentTypeKey.TYPE_XLSX.equals(str) || Constant.DocumentTypeKey.TYPE_XLS.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_excel));
        } else if (Constant.DocumentTypeKey.TYPE_PDF.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_pdf));
        } else {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.common_case_icon));
        }
    }

    private ArrayList<MPFileBean> deliveryFilesEntities;
    private Context context;
}
