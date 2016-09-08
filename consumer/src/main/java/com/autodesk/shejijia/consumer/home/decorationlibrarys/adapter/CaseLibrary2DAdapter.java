package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.ImagesBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetailActivity;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * @Author: lizhipeng
 * @Data: 16/9/7 下午11:20
 * @Description:
 */
public class CaseLibrary2DAdapter extends BaseCommonRvAdapter<ImagesBean> {
    private Context mContext;
    private CaseDetailBean mCaseDetailBean;

    public CaseLibrary2DAdapter(Context context, int layoutId, List<ImagesBean> datas,CaseDetailBean caseDetailBean) {
        super(context, layoutId, datas);
        mContext = context;
        mCaseDetailBean = caseDetailBean;
    }

    private String mContent;

    public void setmContent(String mContent) {
        this.mContent = mContent;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, ImagesBean imagesBean, final int position) {
        TextView textView = holder.getView(R.id.case_library_item_2_tv);
        ImageView imageView = holder.getView(R.id.case_library_item_2_iv);
        if (position == 0) {
            if (mContent == null || mContent.equals("")) {
                textView.setText(R.string.nodescription);
            } else {
                textView.setText("          " + mContent);
            }
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        String imageUrl = imagesBean.getFile_url() + Constant.CaseLibraryDetail.JPG;
        ImageUtils.loadImageIcon(imageView, imageUrl);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaseLibraryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, mCaseDetailBean);
                bundle.putInt(Constant.CaseLibraryDetail.CASE_DETAIL_POSTION, position);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}
