package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.im.activity.ChatRoomActivity;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * Created by luchongbin on 16-8-18.
 */
public class SelectDesignAdapter extends CommonAdapter<DecorationBiddersBean> {
    private  MeasureFormCallBack measureFormCallBack;
    private boolean falg;
    private String payment_status;
    private Context context;
    public SelectDesignAdapter(Context context, List<DecorationBiddersBean> biddersEntities, int layoutId,String payment_status,boolean falg) {
        super(context, biddersEntities, layoutId);
        this.measureFormCallBack = (MeasureFormCallBack)context;
        this.payment_status = payment_status;
        this.falg = falg;
        this.context = context;
    }
    @Override
    public void convert(CommonViewHolder holder, final DecorationBiddersBean biddersBean) {

        String nick_name = biddersBean.getUser_name();
        nick_name = TextUtils.isEmpty(nick_name) ? "匿名" : nick_name;
        String style_names = biddersBean.getStyle_names();
        style_names = (style_names != null)?style_names.replace(","," "):"";
        String avatar = biddersBean.getAvatar();
        avatar = TextUtils.isEmpty(avatar) ? "" : avatar;
        PolygonImageView polygonImageView = holder.getView(R.id.piv_design_photo);
        ImageUtils.displayAvatarImage(avatar, polygonImageView);
        holder.setText(R.id.tv_designer_name, nick_name);
        holder.setText(R.id.tv_profession, UIUtils.getString(R.string.profession)+style_names);

        Button button = holder.getView(R.id.bt_select_ta_measure_form);

        TextView textView = holder.getView(R.id.tv_measure_invite);

        String wk_cur_sub_node_id = biddersBean.getWk_cur_sub_node_id();
        int  key=Integer.parseInt(wk_cur_sub_node_id!=null?wk_cur_sub_node_id:"-1");

        verification(key,button,textView);


        holder.setOnClickListener(R.id.img_consume_home_chat, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 在线聊天 .
                 */
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();
                Intent intent = new Intent(context, ChatRoomActivity.class);
                String acs_member_id = mMemberEntity.getAcs_member_id();
                String member_type = mMemberEntity.getMember_type();
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, biddersBean.getUser_name());
                intent.putExtra(ChatRoomActivity.THREAD_ID, biddersBean.getDesign_thread_id());
                intent.putExtra(ChatRoomActivity.ASSET_ID, "");
                intent.putExtra(ChatRoomActivity.MEMBER_TYPE, member_type);
                intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, biddersBean.getDesigner_id());
                intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, acs_member_id);
                context.startActivity(intent);

            }
        });

//

        holder.setOnClickListener(R.id.bt_select_ta_measure_form, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measureFormCallBack.measureForm( biddersBean.getDesigner_id());
            }
        });
    }
    private void verification(int key,Button button,TextView textView){
        if(falg){
            isHideButton(false, button, textView);
        }else {
            if(key == 24){
                isHideButton(false, button, textView);
            }else{
                isHideButton(true, button, textView);
            }
        }
    }
    private void isHideButton(boolean flg,Button button,TextView textView ){
        if(flg){
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);

    }
    public interface MeasureFormCallBack {
        void measureForm(String designer_id);
    }
}