package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.Wk3DPlanShowActivity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanDelivery;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-18 .
 * @file Wk3DFinishDeliveryAdapter.java .
 * @brief 交付完成含有分享页面的适配器 .
 */
public class Wk3DFinishDeliveryAdapter extends CommonAdapter<Wk3DPlanDelivery.DeliveryFilesEntity> {

    public Wk3DFinishDeliveryAdapter(Context context, ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> deliveryFilesEntities) {
        super(context, deliveryFilesEntities, R.layout.item_gridview_3dplan);
        this.context = context;
        this.deliveryFilesEntities = deliveryFilesEntities;
    }

    @Override
    public void convert(final CommonViewHolder holder, final Wk3DPlanDelivery.DeliveryFilesEntity deliveryFilesEntity) {
        final ImageView mShowImageView = holder.getView(R.id.iv_show_3dplan);
        /**
         * 方案的缩略图
         */
        final String url = deliveryFilesEntity.getUrl();
        Log.i("aaa",""+url);
        String str = url.substring(url.lastIndexOf('.') + 1);
        /**
         * 方案的名字
         */
        String name = deliveryFilesEntity.getName();
        if (TextUtils.isEmpty(name)) {
            holder.setText(R.id.tv_3dplan_name, (UIUtils.getString(R.string.three_plan)));
        } else {
            holder.setText(R.id.tv_3dplan_name, name);
        }

        if (Constant.DocumentTypeKey.TYPE_PNG.equals(str) || Constant.DocumentTypeKey.TYPE_JPG.equals(str)) {
            ImageUtils.displayIconImage(url, mShowImageView);
            mShowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Wk3DPlanShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN, deliveryFilesEntity);
                    bundle.putBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG, false);
                    intent.putExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT, bundle);
                    context.startActivity(intent);
                }
            });
        } else {
            setReflectIcon(mShowImageView, str,url,name);
        }


    }

    private void setReflectIcon(ImageView imageView, String str, final String url,final String name) {
        if (Constant.DocumentTypeKey.TYPE_DOCX.equals(str) || Constant.DocumentTypeKey.TYPE_DOC.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_world));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);

                    mContext.startActivity(intent);
                }
            });
        } else if (Constant.DocumentTypeKey.TYPE_XLSX.equals(str) || Constant.DocumentTypeKey.TYPE_XLS.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_excel));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);

                    mContext.startActivity(intent);
                }
            });
        } else if (Constant.DocumentTypeKey.TYPE_PDF.equals(str)) {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_pdf));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);

                    mContext.startActivity(intent);
                }
            });
        } else {
            imageView.setImageDrawable(UIUtils.getDrawable(R.drawable.common_case_icon));

        }
    }

    private Wk3DPlanDelivery.DeliveryFilesEntity deliveryFilesEntity;
    private ArrayList<Wk3DPlanDelivery.DeliveryFilesEntity> deliveryFilesEntities;
    private Context context;
}
