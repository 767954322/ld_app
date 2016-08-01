package com.autodesk.shejijia.consumer.personalcenter.consumer.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AttentionEntity;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.adapter.BaseAdapter;

import java.util.List;

/**
 * Created by xueqiudong on 16-8-1.
 */
public class AttentionAdapter extends BaseAdapter {

    public AttentionAdapter(Context context, List<AttentionEntity.DesignerListBean> datas) {
        super(context, datas);

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

        if (is_real_name==2) {
            ((ViewHolder) holder).img_attention_attestation_icon.setVisibility(View.VISIBLE);
        }else {
            ((ViewHolder) holder).img_attention_attestation_icon.setVisibility(View.GONE);
        }


    }

    public class ViewHolder extends BaseAdapter.Holder {

        private PolygonImageView piv_attention_piv_head_icon;
        private ImageView img_attention_attestation_icon;
        private TextView tv_attention_name;
        private ImageView iv_cancel_attention;

    }

}
