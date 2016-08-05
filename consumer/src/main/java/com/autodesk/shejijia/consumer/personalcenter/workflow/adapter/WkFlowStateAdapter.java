package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.WkFlowStateBean;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file WkFlowStateAdapter.java  .
 * @brief 全流程状态机适配器  .
 */
public class WkFlowStateAdapter extends BaseAdapter {

    public WkFlowStateAdapter(Context context, String member_type, MPBidderBean biddersEntity, String wk_template_id) {
        this.context = context;
        this.member_type = member_type;
        wk_cur_sub_node_id = biddersEntity.getWk_cur_sub_node_id();

        if (WkFlowStateMap.map != null) {

            mapWkFlowState = WkFlowStateMap.map;

        }

    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int stateCode = Integer.parseInt(wk_cur_sub_node_id);
        int textColor = 0;

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_designer_meal_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.piv_meal_phone = (PolygonImageView) convertView.findViewById(R.id.piv_designer_meal_detail);
            viewHolder.tv_meal_title = (TextView) convertView.findViewById(R.id.tv_designer_meal_detail_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 消费者全流程状态
         *
         * bid_designorder:       1	 应标
         * 12 decline_measure	消费者拒绝设计师
         *13	confirm_measure 设计师同意量房
         *14	decline_invite_measure 设计师拒绝量房

         * choose_designorder: 	2  自选
         *12	confirm_measure	        设计师同意量房
         *13	decline_invite_measure 设计师拒绝量房
         */
        mapWkFlowState.get("11").getDesignerMessage();
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) { // 消费者


            switch (position) {
                //确认量房
                case 0:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("1").getDescription());//确认量房
                    viewHolder.piv_meal_phone.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_xuanzeshejishi));
                    if (stateCode >= 11) {
                        textColor = StepEnableColor;
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //支付量房费用
                case 1:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("2").getDescription());
                    viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                    if (stateCode == 12) {//消费者拒绝应标
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                    } else if (stateCode == 13) {//未支付量房费
                        textColor = StepEnableColor;
                    } else if (stateCode == 14) {//设计师拒绝量房
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                    } else if (stateCode > 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //接受设计交付物
                case 2:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("3").getDescription());
                    if (stateCode >= 21) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jieshoushejihetong_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //支付设计首款
                case 3:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("4").getDescription());
//                    if (stateCode == 31) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
//                    } else if (stateCode > 31 && stateCode != 33) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
//                    } else {
//                        textColor = StepDisEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.shejishoukuan_ico));
//                    }

                    if (stateCode >= 31){

                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                    }else {

                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.shejishoukuan_ico));
                    }

                    viewHolder.tv_meal_title.setTextColor(textColor);



                    break;
                //支付设计尾款
                case 4:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("5").getDescription());
                    // viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.zhifushejiket));
//                    if (stateCode == 41 || stateCode == 42) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
//                    } else if (stateCode > 42) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
//                    } else {
//                        textColor = StepDisEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.weikuan_ico));
//                    }

                    if (stateCode >= 41){

                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                    }else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.weikuan_ico));
                    }

                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //接收设计交付物
                case 5:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("6").getDescription());//设计交付
//                    if (stateCode == 51 || stateCode == 52) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
//                    } else if (stateCode > 52) {
//                        textColor = StepEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
//                    } else {
//                        textColor = StepDisEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jiaofuwu_ico));
//                    }

                    if (stateCode >= 51){

                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                    }else {

                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jiaofuwu_ico));

                    }

                    viewHolder.tv_meal_title.setTextColor(textColor);

                    break;
            }
        } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(member_type)) { // 设计师

            /**
             * 设计师全流程状态
             */
            switch (position) {
                //确认量房
                case 0:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("1").getDescription());//确认量房
                    if (stateCode == 11) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                    } else if (stateCode > 11 && stateCode != 12 && stateCode != 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                    } else if (stateCode == 12) {

                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                    } else if (stateCode == 13) {
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                        textColor = StepEnableColor;
                    } else if (stateCode == 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;

                //接收量房费
                case 1:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("2").getDescription());//支付量房费
                    if (stateCode == 12) {
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
//                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
//
//                        textColor = StepDisEnableColor;
//                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
//                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.consumers_refused_standard));
//                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.consumers_refused_you_standard));

                    } else if (stateCode == 13) {
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        textColor = StepEnableColor;
                        // viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_owner_pay_room));//等待业主支付量房费

                    } else if (stateCode == 14) {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                    } else if (stateCode >= 21) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //上传量房交付物
                case 2:

                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("3").getDescription());//签订设计合同
                    if (stateCode == 21 || stateCode == 22) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                    } else if (stateCode >= 31 && stateCode != 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                    } else if (stateCode == 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jieshoushejihetong_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //接收设计首款
                case 3:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("4").getDescription());//支付设计首款
                    if (stateCode == 31) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                    } else if (stateCode >= 41) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.shejishoukuan_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //接受设计尾款
                case 4:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("5").getDescription());//支付设计尾款
                    if (stateCode == 41) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                    } else if (stateCode > 41 && stateCode != 42) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.weikuan_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
                //上传设计交付物
                case 5:
                    viewHolder.tv_meal_title.setText(mapWkFlowState.get("6").getDescription());//上传交付
                    if (stateCode == 51) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                    } else if (stateCode > 51) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jiaofuwu_ico));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    break;
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public PolygonImageView piv_meal_phone;
        public TextView tv_meal_title;
    }

    //呼吸状态，动画效果呢；

    public void setItemAnimation(View view, boolean cancelAnimation, boolean animation) {

        imagView_SaleAnimation = new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f, 0.7f, 0.7f);
        imagView_SaleAnimation.setDuration(1000);
        imagView_SaleAnimation.setRepeatCount(10000);
        imagView_SaleAnimation.setRepeatMode(Animation.REVERSE);
        view.setAnimation(imagView_SaleAnimation);

        if (cancelAnimation) {

            setCancelItemAnimation(view);
        }
    }

    //呼吸状态，取消动画效果；
    public void setCancelItemAnimation(View view) {

        if (imagView_SaleAnimation != null) {
            view.clearAnimation();
        }
    }

    private Context context;
    private String wk_cur_sub_node_id;
    private String member_type;
    private final int StepEnableColor = Color.rgb(30, 30, 30); // 亮
    private final int StepDisEnableColor = Color.rgb(188, 188, 188); // 暗
    private Animation imagView_SaleAnimation;
    private Map<String, WkFlowStateBean> mapWkFlowState;

}
