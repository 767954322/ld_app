package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/27 0027 10:16 .
 * @file SeekDesignerAdapter  .
 * @brief 为SeekDesignerAdapter适配数据.
 */
public class SeekDesignerAdapter extends BaseAdapter<SeekDesignerBean.DesignerListEntity> {

    /// item单击监听接口.
    public interface OnItemChatClickListener {
        void OnItemChatClick(int position);
    }

    public void setOnItemChatClickListener(OnItemChatClickListener mOnItemChatClickListener) {
        this.mOnItemChatClickListener = mOnItemChatClickListener;
    }

    public SeekDesignerAdapter(Context context, List<SeekDesignerBean.DesignerListEntity> datas) {
        super(context, datas);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_seek_designer;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.mTvName = (TextView) container.findViewById(R.id.tv_seek_designer_name);
        viewHolder.mTvProduction = (TextView) container.findViewById(R.id.tv_seek_designer_production);
        viewHolder.mTvAdept = (TextView) container.findViewById(R.id.tv_seek_designer_adept);
        viewHolder.mTvCost = (TextView) container.findViewById(R.id.tv_seek_designer_cost);
        viewHolder.mPolygonImageView = (PolygonImageView) container.findViewById(R.id.piv_seek_designer_photo);
        viewHolder.mImageView = (ImageView) container.findViewById(R.id.img_seek_designer_authentication);
        viewHolder.mImgSeekDesignerChat = (ImageView) container.findViewById(R.id.img_seek_designer_chat);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        if (null != mDatas) {

            String nick_name = mDatas.get(position).getNick_name();
            if (null != nick_name) {
                ((ViewHolder) holder).mTvName.setText(nick_name);
            }

            String avatar = mDatas.get(position).getAvatar();
            if (!TextUtils.isEmpty(avatar)) {
                ImageUtils.displayAvatarImage(avatar, ((ViewHolder) holder).mPolygonImageView);
            }

            String case_count = mDatas.get(position).getDesigner().getCase_count() + "";
            if (null != case_count) {
                ((ViewHolder) holder).mTvProduction.setText(context.getResources().getString(R.string.work) + case_count);
            } else {
                ((ViewHolder) holder).mTvProduction.setText(context.getResources().getString(R.string.work) + "0");
            }

            String style_long_names = mDatas.get(position).getDesigner().getStyle_long_names();
            if (null == style_long_names || style_long_names.trim().equals("")) {

                ((ViewHolder) holder).mTvAdept.setText(context.getResources().getString(R.string.be_good_at) + UIUtils.getResources().getString(R.string.has_yet_to_fill_out));

            } else {
                ((ViewHolder) holder).mTvAdept.setText(context.getResources().getString(R.string.be_good_at) + style_long_names);
            }

            String design_price_min = mDatas.get(position).getDesigner().getDesign_price_min();
            String design_price_max = mDatas.get(position).getDesigner().getDesign_price_max();
            if (null != design_price_min && null != design_price_max) {
                ((ViewHolder) holder).mTvCost.setText(design_price_min + "-" + design_price_max + "元/m²");
            } else {
                ((ViewHolder) holder).mTvCost.setText(R.string.has_yet_to_fill_out);
            }

            SeekDesignerBean.DesignerListEntity.DesignerEntity designer = mDatas.get(position).getDesigner();
            if (null != designer && null != designer.getIs_real_name() + "" && designer.getIs_real_name() == 2) {
                ((ViewHolder) holder).mImageView.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder) holder).mImageView.setVisibility(View.GONE);
            }

        } else {
            ((ViewHolder) holder).mTvName.setText(R.string.has_yet_to_fill_out);
            ((ViewHolder) holder).mPolygonImageView.setImageResource(R.drawable.icon_default_avator);
            ((ViewHolder) holder).mTvProduction.setText(R.string.work + "0");
            ((ViewHolder) holder).mTvAdept.setText(R.string.has_yet_to_fill_out);
            ((ViewHolder) holder).mTvCost.setText(R.string.has_yet_to_fill_out);
            ((ViewHolder) holder).mImageView.setVisibility(View.GONE);
        }
        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            ((ViewHolder) holder).mImgSeekDesignerChat.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).mImgSeekDesignerChat.setVisibility(View.VISIBLE);
        }
        ((ViewHolder) holder).mImgSeekDesignerChat.setOnClickListener(new MyOnClickListener(position, (ViewHolder) holder));
    }

    /// 监听事件.
    class MyOnClickListener implements View.OnClickListener {
        private int position;
        private ViewHolder mViewHolder;

        private MyOnClickListener(int position, ViewHolder mViewHolder) {
            this.position = position;
            this.mViewHolder = mViewHolder;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_seek_designer_chat:
                    if (mOnItemChatClickListener != null) {
                        mOnItemChatClickListener.OnItemChatClick(position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public class ViewHolder extends BaseAdapter.Holder {
        public PolygonImageView mPolygonImageView;
        public TextView mTvName;
        public TextView mTvProduction;
        public TextView mTvAdept;
        public TextView mTvCost;
        public ImageView mImageView;
        public ImageView mImgSeekDesignerChat;
    }

    private Context context;
    private OnItemChatClickListener mOnItemChatClickListener;
}
