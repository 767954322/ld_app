package com.autodesk.shejijia.consumer.personalcenter.workflow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPBidderBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;

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
        if (StringUtils.isNumeric(wk_template_id)) {
            mWk_template_id_int = Integer.parseInt(wk_template_id);
        }
        wk_cur_sub_node_id = biddersEntity.getWk_cur_sub_node_id();
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
            viewHolder.tv_meal_content = (TextView) convertView.findViewById(R.id.designer_meal_detail_desc);
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
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) { // 消费者
            switch (position) {
                case 0:
                    viewHolder.piv_meal_phone.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_xuanzeshejishi));
                    if (stateCode == 11) {
                        textColor = StepEnableColor;
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.send_invitation_designer_quantity_room));
                    } else if (stateCode == 12) {
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.refused_designer_quantity_room_invitation));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.your_confirmed_quantity));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_agreed_room_invitation));
                            default:
                                break;
                        }
                    } else if (stateCode == 13) {
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.your_confirmed_quantity));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_agreed_room_invitation));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_refused_room_invitation));
                            default:
                                break;
                        }
                    } else if (stateCode == 14) {
                        textColor = StepEnableColor;
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_refused_room_invitation));
                    } else if (stateCode > 14) {
                        textColor = StepEnableColor;
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designers_have_complete_quantity));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 1:
                    viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                    viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.pay_amount_of_room_charge));
                    if (stateCode == 12) {
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.consumers_refused_standard));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.consumers_refused_you_standard));
                                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.please_pay_designer_volume_rate));
                            default:
                                break;
                        }
                    } else if (stateCode == 13) {
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.please_pay_designer_volume_rate));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_refused_room_invitation));
                                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                            default:
                                break;
                        }
                    } else if (stateCode == 14) {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_has_declined_room));
                    } else if (stateCode > 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.you_designers_pay_excess_rate));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.please_pay_designer_volume_rate));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 2:
                    if (stateCode == 21) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.receive_design_contract));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_design_contract));
                    } else if (stateCode == 31) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.receive_design_contract));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designers_send_contract));
                    } else if (stateCode == 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.receiving_room_deliverable));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.receive_designer_from_deliverables));
                    } else if (stateCode > 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jiesoushejiketong));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.your_contract_agreed_designer));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jieshoushejihetong_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jiesoushejiketong));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_design_contract));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 3:
                    viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.zhifusheijiweikuan));
                    if (stateCode == 31) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_online_payment_first));
                    } else if (stateCode > 31 && stateCode != 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designers_pay_design_first));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.shejishoukuan_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_online_payment_first));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;
                case 4:
                    viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.zhifushejiket));
                    if (stateCode == 41 || stateCode == 42) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_online_payment_balance_payment));
                    } else if (stateCode > 42) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.payment_designers_make_design));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.weikuan_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.choose_designer_online_payment_balance_payment));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 5:
                    viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jieshoujiafuwu));
                    if (stateCode == 51 || stateCode == 52) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.receive_designer_send_design_deliverables));
                    } else if (stateCode > 52) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.you_receiving_designer_sent_design_deliverables));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jiaofuwu_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.receiving_designer_sent_design_deliverables));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);

                    break;
            }
        } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(member_type)) { // 设计师

            /**
             * 设计师全流程状态
             */
            switch (position) {
                case 0:
                    if (stateCode == 11) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.querenliangfang));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.check_room_invitation_and_confirm_room));
                    } else if (stateCode > 11 && stateCode != 12 && stateCode != 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.your_confirmed_quantity));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.your_confirmed_quantity));
                    } else if (stateCode == 12) {
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.consumers_refused_standard));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.consumers_refused_you_standard));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.your_confirmed_quantity));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_agreed_room_invitation));
                            default:
                                break;
                        }
                    } else if (stateCode == 14) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_xuanzeshejishi));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.refused_to_amount_of_room));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.you_refused_customer_amount_room));
                    } else if (stateCode >= 21) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.you_have_receiving_volume_rate));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.you_received_amount_room_charge_consumers));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 1:
                    if (stateCode == 12) {
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.consumers_refused_standard));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.consumers_refused_you_standard));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_agreed_room_invitation));
                            default:
                                break;
                        }
                    } else if (stateCode == 13) {
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                        switch (mWk_template_id_int) {
                            case 1:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_owner_pay_room));
                                break;
                            case 2:
                                textColor = StepEnableColor;
                                viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.designer_has_declined_room));
                                break;
                        }
                    } else if (stateCode == 14) {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                    } else if (stateCode >= 21) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_liangfang));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.you_have_receiving_volume_rate));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.you_received_amount_room_charge_consumers));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.liangfangfei_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.jeishouliangfangfei));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;

                case 2:
                    if (stateCode == 21 || stateCode == 22) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.chuangjiansheijihetong));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.create_design_contract_sent_customer));
                    } else if (stateCode >= 31 && stateCode != 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.design_contract_sent));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.design_contract_sent_consumer));
                    } else if (stateCode == 33) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.uploaded_deliverable));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.upload_your_room_deliverables));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jieshoushejihetong_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.uploaded_deliverable));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.create_design_contract_sent_customer));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;
                case 3:
                    if (stateCode == 31) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jiesoushejiweikuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_receiving_customer_send_design_down_payment));
                    } else if (stateCode >= 41) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejishoukuan));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.have_received_first_design));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.have_received_customer_down_payment));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.shejishoukuan_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.have_received_first_design));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_receiving_customer_send_design_down_payment));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;
                case 4:
                    if (stateCode == 41) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.jieshouweikuan));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_receiving_customer_send_design_down_payment_end));
                    } else if (stateCode > 41 && stateCode != 42) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_zhifushejiweikuan));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.have_received_end_design));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.have_received_customer_down_payment_end));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.weikuan_ico));
                        viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.have_received_end_design));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_receiving_customer_send_design_down_payment_end));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;
                case 5:
                    viewHolder.tv_meal_title.setText(UIUtils.getString(R.string.shangchuanjiaofuwu));
                    if (stateCode == 51) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_uploading_final_version_design_deliverables));
                    } else if (stateCode > 51) {
                        textColor = StepEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejijiaofuwu));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.wait_uploading_final_version_design_deliverables));
                    } else {
                        textColor = StepDisEnableColor;
                        viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.jiaofuwu_ico));
                        viewHolder.tv_meal_content.setText(UIUtils.getString(R.string.waiting_uploading_final_version_design_deliverables));
                    }
                    viewHolder.tv_meal_title.setTextColor(textColor);
                    viewHolder.tv_meal_content.setTextColor(textColor);
                    break;
            }
        }
        return convertView;
    }

    public class ViewHolder {
        public PolygonImageView piv_meal_phone;
        public TextView tv_meal_title;
        public TextView tv_meal_content;
    }

    private Context context;
    private String wk_cur_sub_node_id;
    private String member_type;
    private int mWk_template_id_int;
    private final int StepEnableColor = Color.rgb(30, 30, 30); // 亮
    private final int StepDisEnableColor = Color.rgb(188, 188, 188); // 暗

}
