package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AttentionEntity;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.consumer.base.adapter.BaseAdapter;

import java.util.List;


/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/8/1 0029 17:32 .
 * @file AttentionAdapter  .
 * @brief 关注列表适配器 .
 */
public class AttentionAdapter extends BaseAdapter {

    public AttentionAdapter(Context context, List<AttentionEntity.DesignerListBean> datas) {
        super(context, datas);

    }

    /// item单击监听接口.
    public interface OnItemClickListener {
        void OnItemCancelAttentionClick(int position);

        void OnItemAvatarClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public int getLayoutId() {
        return R.layout.item_lv_attention;
    }

    @Override
    public Holder initHolder(View container) {
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.piv_attention_piv_head_icon = (PolygonImageView) container.findViewById(R.id.piv_attention_piv_head_icon);
        viewHolder.img_attention_attestation_icon = (ImageView) container.findViewById(R.id.img_attention_attestation_icon);
        viewHolder.tv_attention_name = (TextView) container.findViewById(R.id.tv_attention_name);
        viewHolder.iv_cancel_attention = (ImageView) container.findViewById(R.id.iv_cancel_attention);
        return viewHolder;
    }

    @Override
    public void initItem(View view, Holder holder, int position) {
        AttentionEntity.DesignerListBean designerListBean = (AttentionEntity.DesignerListBean) mDatas.get(position);
        String avatar = designerListBean.getAvatar();

        if (!TextUtils.isEmpty(avatar)) {
            ImageUtils.displayAvatarImage(avatar, ((ViewHolder) holder).piv_attention_piv_head_icon);
        }

        String nick_name = designerListBean.getNick_name();
        if (null != nick_name) {
            ((ViewHolder) holder).tv_attention_name.setText(nick_name);
        } else {
            ((ViewHolder) holder).tv_attention_name.setText(R.string.no_data);
        }

        int is_real_name = designerListBean.getIs_real_name();

        if (is_real_name == 2) {
            ((ViewHolder) holder).img_attention_attestation_icon.setVisibility(View.VISIBLE);
        } else {
            ((ViewHolder) holder).img_attention_attestation_icon.setVisibility(View.GONE);
        }

        ((ViewHolder) holder).iv_cancel_attention.setOnClickListener(new MyOnClickListener(position, (ViewHolder) holder));
        ((ViewHolder) holder).piv_attention_piv_head_icon.setOnClickListener(new MyOnClickListener(position, (ViewHolder) holder));
    }

    class MyOnClickListener implements View.OnClickListener {

        private int position;
        private ViewHolder mViewHolder;

        private MyOnClickListener(int position, ViewHolder mViewHolder) {
            this.position = position;
            this.mViewHolder = mViewHolder;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_cancel_attention:
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemCancelAttentionClick(position);
                    }
                    break;
                case R.id.piv_attention_piv_head_icon:
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.OnItemAvatarClickListener(position);
                    }
            }
        }
    }

    public class ViewHolder extends BaseAdapter.Holder {

        private PolygonImageView piv_attention_piv_head_icon;
        private ImageView img_attention_attestation_icon;
        private TextView tv_attention_name;
        private ImageView iv_cancel_attention;
    }

    private OnItemClickListener mOnItemClickListener;
}
