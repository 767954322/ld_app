package com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/23 0023 14:59 .
 * @file HoverCaseAdapter  .
 * @brief 悬浮按钮案例库 .
 */
public class HoverCaseAdapter extends BaseAdapter<CaseLibraryBean.CasesEntity> {



    public interface OnItemHoverCaseClickListener {
        void OnItemHoverCaseClick(int position);
    }

    public void setOnItemHoverCaseClickListener(OnItemHoverCaseClickListener mOnItemHoverCaseClickListener) {
        this.mOnItemHoverCaseClickListener = mOnItemHoverCaseClickListener;
    }

    public HoverCaseAdapter(Context context, List<CaseLibraryBean.CasesEntity> datas, int screenWidth, int screenHeight) {
        super(context, datas);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_gv_case_library;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.hoverCase = (ImageView) container.findViewById(R.id.img_hover_case);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {

        List<CaseLibraryBean.CasesEntity.ImagesEntity> images = mDatas.get(position).getImages();
        if (images != null && images.size() > 0) {
            for (int i = 0; i < images.size(); i++) {
                CaseLibraryBean.CasesEntity.ImagesEntity imagesEntity = images.get(i);
                if (imagesEntity.isIs_primary()) {
                    mImageOneUrl = imagesEntity.getFile_url();
                }
            }
            if (TextUtils.isEmpty(mImageOneUrl)) {
                mImageOneUrl = images.get(0).getFile_url();
            }
            ImageUtils.displayIconImage(mImageOneUrl + Constant.CaseLibraryDetail.JPG, ((ViewHolder) holder).hoverCase);
        } else {
            ((ViewHolder) holder).hoverCase.setImageResource(R.drawable.common_case_icon);
        }
        ((ViewHolder) holder).hoverCase.setOnClickListener(new MyOnClickListener(position, ((ViewHolder) holder)));
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ((ViewHolder) holder).hoverCase.getLayoutParams();
        layoutParams.width = screenWidth;
        layoutParams.height = 197 * screenWidth / 320;
        ((ViewHolder) holder).hoverCase.setLayoutParams(layoutParams);
    }

    public class ViewHolder extends BaseAdapter.Holder {
        public ImageView hoverCase;
    }

    class MyOnClickListener implements View.OnClickListener {
        private int position;
        private ViewHolder holder;

        private MyOnClickListener(int position, ViewHolder holder) {
            this.position = position;
            this.holder = holder;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_hover_case:
                    if (mOnItemHoverCaseClickListener != null) {
                        mOnItemHoverCaseClickListener.OnItemHoverCaseClick(position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private OnItemHoverCaseClickListener mOnItemHoverCaseClickListener;
    private int screenWidth;
    private int screenHeight;
    private String mImageOneUrl;
    private Context context;
}
