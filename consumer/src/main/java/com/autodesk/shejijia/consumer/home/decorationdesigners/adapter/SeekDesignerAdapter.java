package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerInfoBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.RealNameBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/2/27 0027 10:16 .
 * @file SeekDesignerAdapter  .
 * @brief 为SeekDesignerAdapter适配数据.
 */
public class SeekDesignerAdapter extends CommonAdapter<SeekDesignerBean.DesignerListEntity> {

    private Context mContext;
    private OnItemChatClickListener mOnItemChatClickListener;
    private MemberEntity mMemberEntity;
    private static final String IS_REAL_NAME = "2";

    public SeekDesignerAdapter(Context context, List<SeekDesignerBean.DesignerListEntity> datas) {
        super(context, datas, R.layout.item_lv_seek_designer);
        mContext = context;
        mMemberEntity = AdskApplication.getInstance().getMemberEntity();
        if (mMemberEntity == null) {
            return;
        }
    }

    @Override
    public void convert(final CommonViewHolder holder, SeekDesignerBean.DesignerListEntity designerListEntity) {

        if (mMemberEntity != null && Constant.UerInfoKey.DESIGNER_TYPE.equals(mMemberEntity.getMember_type())) {
            holder.setVisible(R.id.img_seek_designer_chat, false);
        } else {
            holder.setVisible(R.id.img_seek_designer_chat, true);
        }

        String nick_name = designerListEntity.getNick_name();
        String avatar = designerListEntity.getAvatar();
        String following_count = designerListEntity.getFollows();
        DesignerInfoBean designerInfoBean = designerListEntity.getDesigner();
        RealNameBean real_name = designerListEntity.getReal_name();

        nick_name = TextUtils.isEmpty(nick_name) ? "暂无数据" : nick_name;
        avatar = TextUtils.isEmpty(avatar) ? "" : avatar;
        following_count = TextUtils.isEmpty(following_count) ? "0" : following_count;
        holder.setText(R.id.tv_seek_designer_name, nick_name);
        holder.setText(R.id.tv_attention_num, UIUtils.getString(R.string.attention_num) + " : " + following_count);

        PolygonImageView polygonImageView = holder.getView(R.id.piv_seek_designer_photo);
        ImageUtils.displayAvatarImage(avatar, polygonImageView);

        String case_count = null;
        String style_long_names = null;
        String design_price_min = null;
        String design_price_max = null;
        int is_real_name = 0;
        String audit_status = null;

        if (null != real_name) {
            audit_status = real_name.getAudit_status();
        }

        if (null != designerInfoBean) {
            case_count = designerInfoBean.getCase_count() + "";
            style_long_names = designerInfoBean.getStyle_long_names();
            design_price_min = designerInfoBean.getDesign_price_min();
            design_price_max = designerInfoBean.getDesign_price_max();
            is_real_name = designerInfoBean.getIs_real_name();
        }


        String case_count_prefix = UIUtils.getString(R.string.work);
        String be_good_at_prefix = UIUtils.getString(R.string.be_good_at);

        case_count = TextUtils.isEmpty(case_count) ? "0" : case_count;
        style_long_names = TextUtils.isEmpty(style_long_names) ?
                UIUtils.getString(R.string.has_yet_to_fill_out) : style_long_names;

        holder.setText(R.id.tv_seek_designer_production, case_count_prefix + case_count);
        holder.setText(R.id.tv_seek_designer_adept, be_good_at_prefix + style_long_names);

        boolean costBoolean = TextUtils.isEmpty(design_price_max) || TextUtils.isEmpty(design_price_min);
        if (costBoolean) {
            holder.setText(R.id.tv_seek_designer_cost, UIUtils.getString(R.string.has_yet_to_fill_out));
        } else {
            if (StringUtils.isNumeric(design_price_min)) {
                design_price_min = Integer.valueOf(design_price_min)/100 + "";
            }

            if (StringUtils.isNumeric(design_price_max)) {
                design_price_max = Integer.valueOf(design_price_max)/100 + "";
            }

            holder.setText(R.id.tv_seek_designer_cost, design_price_min + "-" + design_price_max + "元/m²");
        }
        audit_status = TextUtils.isEmpty(audit_status) ? "" : audit_status;

        ///  TODO is_real_name 老字段  .

        if (IS_REAL_NAME.equals(audit_status) || is_real_name == 2) {
            holder.setVisible(R.id.img_seek_designer_authentication, true);
        } else {
            holder.setVisible(R.id.img_seek_designer_authentication, false);
        }

        holder.setOnClickListener(R.id.img_seek_designer_chat, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemChatClickListener != null) {
                    mOnItemChatClickListener.OnItemChatClick(holder.getPosition());
                }
            }
        });
    }

    /// item单击监听接口.
    public interface OnItemChatClickListener {
        void OnItemChatClick(int position);
    }

    public void setOnItemChatClickListener(OnItemChatClickListener mOnItemChatClickListener) {
        this.mOnItemChatClickListener = mOnItemChatClickListener;
    }
}
