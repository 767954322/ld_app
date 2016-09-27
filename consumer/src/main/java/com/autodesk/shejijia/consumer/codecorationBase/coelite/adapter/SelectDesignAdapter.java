package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.activity.SeekDesignerDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpBean;
import com.autodesk.shejijia.shared.components.common.tools.chatroom.JumpToChatRoom;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;

import java.util.List;

/**
 * Created by luchongbin on 16-8-18.
 */
public class SelectDesignAdapter extends CommonAdapter<DecorationBiddersBean> {
    private  MeasureFormCallBack measureFormCallBack;
    private boolean falg;
    private Context context;
    public SelectDesignAdapter(Context context, List<DecorationBiddersBean> biddersEntities,
                               int layoutId,boolean falg) {
        super(context, biddersEntities, layoutId);
        this.measureFormCallBack = (MeasureFormCallBack)context;
        this.falg = falg;
        this.context = context;
    }
    @Override
    public void convert(CommonViewHolder holder, final DecorationBiddersBean biddersBean) {

        String nick_name = biddersBean.getUser_name();
        nick_name = TextUtils.isEmpty(nick_name) ? UIUtils.getString(R.string.anonymity) : nick_name;
        String style_names = biddersBean.getStyle_names();
        style_names = (style_names != null)?style_names.replace(","," "):"";
        style_names = UIUtils.getNotDataIfEmpty(style_names);
        String avatar = biddersBean.getAvatar();
        avatar = TextUtils.isEmpty(avatar) ? "" : avatar;
        PolygonImageView polygonImageView = holder.getView(R.id.piv_design_photo);
        ImageUtils.displayAvatarImage(avatar, polygonImageView);
        holder.setText(R.id.tv_designer_name, nick_name);
        holder.setText(R.id.tv_profession, UIUtils.getString(R.string.profession)+style_names);
        Button button = holder.getView(R.id.bt_select_ta_measure_form);

        TextView textView = holder.getView(R.id.tv_measure_invite);

        String wk_cur_sub_node_id = biddersBean.getWk_cur_sub_node_id();
        int  sub_node_id =Integer.parseInt(wk_cur_sub_node_id!=null?wk_cur_sub_node_id:"-1");

        if(sub_node_id != MPWkFlowManager.START_NODE){
            String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(context, MPWkFlowManager.PAYMENT_OF_FIRST_FEE, wk_cur_sub_node_id,biddersBean.getDelivery());
            holder.setText(R.id.tv_measure_invite,wkSubNodeName);
        }else{
            holder.setText(R.id.tv_measure_invite,UIUtils.getString(R.string.optional_measure));
        }
        verification(sub_node_id,button,textView);
        holder.setOnClickListener(R.id.img_consume_home_chat, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 在线聊天 .
                 */
                openChatRoom(biddersBean);
            }
        });

        holder.getView(R.id.piv_design_photo).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                /**
                 * 进入设计师主页 .
                 */
                Intent intent = new Intent(context, SeekDesignerDetailActivity.class);
                intent.putExtra(Constant.ConsumerDecorationFragment.designer_id, biddersBean.getDesigner_id());
                intent.putExtra(Constant.ConsumerDecorationFragment.hs_uid, biddersBean.getUid());
                context.startActivity(intent);

            }
        });

        holder.setOnClickListener(R.id.bt_select_ta_measure_form, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measureFormCallBack.measureForm( biddersBean.getDesigner_id());
            }
        });
    }
    /**
     * 打开聊天室
     */
    private void openChatRoom(DecorationBiddersBean biddersBean) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if(memberEntity == null){
             return;
        }
        String userName = biddersBean.getUser_name();
        String designer_id = biddersBean.getDesigner_id();
        JumpBean jumpBean = new JumpBean();
        jumpBean.setReciever_hs_uid(biddersBean.getUid());
        jumpBean.setReciever_user_id(designer_id);
        jumpBean.setReciever_user_name(userName);
        jumpBean.setThread_id(biddersBean.getDesign_thread_id());
        jumpBean.setAcs_member_id(memberEntity.getAcs_member_id());
        jumpBean.setMember_type(memberEntity.getMember_type());
        JumpToChatRoom.getChatRoom(context,jumpBean);

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