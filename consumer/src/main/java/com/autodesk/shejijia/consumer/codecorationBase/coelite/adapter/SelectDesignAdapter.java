package com.autodesk.shejijia.consumer.codecorationBase.coelite.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPWkFlowManager;
import com.autodesk.shejijia.consumer.personalcenter.resdecoration.entity.DecorationBiddersBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlMessagesContants;
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
    private Context context;
    private String needs_id;
    public SelectDesignAdapter(Context context, List<DecorationBiddersBean> biddersEntities,
                               int layoutId,boolean falg,String needs_id ) {
        super(context, biddersEntities, layoutId);
        this.measureFormCallBack = (MeasureFormCallBack)context;
        this.falg = falg;
        this.context = context;
        this.needs_id = needs_id;
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
        int  sub_node_id =Integer.parseInt(wk_cur_sub_node_id!=null?wk_cur_sub_node_id:"-1");

        if(sub_node_id != MPWkFlowManager.START_NODE){
            String wkSubNodeName = MPWkFlowManager.getWkSubNodeName(context, MPWkFlowManager.PAYMENT_OF_FIRST_FEE, wk_cur_sub_node_id);
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
        String member_id = memberEntity.getAcs_member_id();
        String memType = memberEntity.getMember_type();
        String designer_thread_id = biddersBean.getDesign_thread_id();
        String userName = biddersBean.getUser_name();
        String designer_id = biddersBean.getDesigner_id();

        if (TextUtils.isEmpty(designer_thread_id)) {
            designer_thread_id = "";
        }

        Intent intent = new Intent(context, ChatRoomActivity.class);
        intent.putExtra(ChatRoomActivity.THREAD_ID, designer_thread_id);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_ID, designer_id);
        intent.putExtra(ChatRoomActivity.RECIEVER_USER_NAME, userName);
        intent.putExtra(ChatRoomActivity.ASSET_ID, needs_id);
        intent.putExtra(ChatRoomActivity.ACS_MEMBER_ID, member_id);
        intent.putExtra(ChatRoomActivity.MEMBER_TYPE, memType);
        intent.putExtra(ChatRoomActivity.MEDIA_TYPE, UrlMessagesContants.mediaIdProject);
        context.startActivity(intent);
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