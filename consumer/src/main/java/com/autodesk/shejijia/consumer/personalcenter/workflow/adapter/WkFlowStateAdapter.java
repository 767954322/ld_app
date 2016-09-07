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
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkFlowTemplateBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.TipWorkflowInfoBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.List;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-7
 * @file WkFlowStateAdapter.java  .
 * @brief 全流程状态机适配器  .
 */
public class WkFlowStateAdapter extends BaseAdapter {

    public WkFlowStateAdapter(Context context, String member_type, MPBidderBean biddersEntity, TipWorkFlowTemplateBean tipWorkFlowTemplateBean, int tempdate_id) {
        this.context = context;
        this.member_type = member_type;

        this.tempdate_id = tempdate_id;
        this.tipWorkFlowTemplateBean = tipWorkFlowTemplateBean;
        wk_cur_sub_node_id = biddersEntity.getWk_cur_sub_node_id();
    }

    @Override
    public int getCount() {
        if (tempdate_id == 4) {
            return 5;
        }
        return 6;
    }

    @Override
    public Object getItem(int position) {

        return tipWorkFlowTemplateBean.getTip_workflow_infos().get(position + 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int stateCode = Integer.parseInt(wk_cur_sub_node_id);
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
        if (tipWorkFlowTemplateBean != null) {
            final List<TipWorkflowInfoBean> tipWorkflowInfoBeans = tipWorkFlowTemplateBean.getTip_workflow_infos();
            TipWorkflowInfoBean tipWorkflowInfoBean = tipWorkflowInfoBeans.get(position + 1);
            if (tipWorkflowInfoBeans.size() < 1) {
                return convertView;
            }
            if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {//消费者
                viewHolder.tv_meal_title.setText(tipWorkflowInfoBean.getTip_for_consumer());
            } else {
                viewHolder.tv_meal_title.setText(tipWorkflowInfoBean.getTip_for_designer());
            }

            if (tempdate_id == 4) {
                initEliteViewHolder(viewHolder, position, stateCode, tipWorkflowInfoBean);
            } else {
                initGeneralViewHolder(viewHolder, position, stateCode, tipWorkflowInfoBean);
            }
        }


        return convertView;
    }

    public class ViewHolder {
        public PolygonImageView piv_meal_phone;
        public TextView tv_meal_title;
    }

    private void initViewHolder(ViewHolder viewHolder, int drawable, int textColor) {
        viewHolder.piv_meal_phone.setImageDrawable(context.getResources().getDrawable(drawable));
        viewHolder.tv_meal_title.setTextColor(textColor);
    }

    /**
     * 实例化精选流程节点
     *
     * @param viewHolder
     * @param position
     * @param stateCode
     * @param tipWorkflowInfoBean
     */
    private void initEliteViewHolder(ViewHolder viewHolder, int position, int stateCode, TipWorkflowInfoBean tipWorkflowInfoBean) {
        switch (position) {
            case 0://确认量房
                measureFormNode(viewHolder, stateCode);
                break;
            case 1://接受量房、设计交付物(或者签订设计合同)
                eliteEstablishContract(viewHolder, stateCode);
                break;
            case 2://支付设计首款(或者 接收设计首款)
                firstPay(viewHolder, stateCode);
                break;
            case 3://支付设计尾款(或者 接收接受设计尾款)
                lastPay(viewHolder, stateCode);
                break;
            case 4://接收设计交付物(或者 设计交付物)
                deliver(viewHolder, stateCode);
                break;
            default:
                break;
        }

    }

    /**
     * 实例化普通流程节点
     *
     * @param viewHolder
     * @param position
     * @param stateCode
     * @param tipWorkflowInfoBean
     */
    private void initGeneralViewHolder(ViewHolder viewHolder, int position, int stateCode, TipWorkflowInfoBean tipWorkflowInfoBean) {
        switch (position) {
            case 0://确认量房
                measureFormNode(viewHolder, stateCode);
                break;
            case 1://支付量房费用（或者接收量房费）
                costMeasureFeeNode(viewHolder, stateCode);
                break;
            case 2://接受量房、设计交付物(或者签订设计合同)
                if (stateCode == 33) {
                    viewHolder.tv_meal_title.setText("量房交付物");//量房交付物
                }
                establishContract(viewHolder, stateCode);
                break;
            case 3: //支付设计首款(或者 接收设计首款)
                firstPay(viewHolder, stateCode);
                break;
            case 4://支付设计尾款(或者 接收接受设计尾款)
                lastPay(viewHolder, stateCode);
                break;
            case 5://接收设计交付物(或者 设计交付物)
                deliver(viewHolder, stateCode);
                break;
            default:
                break;
        }

    }

    /**
     * 接收设计交付物(或者 设计交付物)节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void deliver(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode >= 51) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejijiaofuwu;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jiaofuwu_ico;
            }
        } else {
            if (stateCode == 51 || stateCode > 51) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejijiaofuwu;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jiaofuwu_ico;
            }

        }
        if (stateCode >= 51 && stateCode != 63) {
            setItemAnimationForView(textColor, viewHolder);
        }
        initViewHolder(viewHolder, drawable, textColor);
    }

    /**
     * 支付设计尾款(或者 接收接受设计尾款)节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void lastPay(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode >= 41) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_zhifushejiweikuan;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.weikuan_ico;
            }


        } else {
            if (stateCode == 41 || (stateCode > 41 && stateCode != 42)) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_zhifushejiweikuan;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.weikuan_ico;
            }
        }
        if (stateCode == 41) {
            setItemAnimationForView(textColor, viewHolder);
        }
        initViewHolder(viewHolder, drawable, textColor);
    }

    /**
     * 支付设计首款(或者 接收设计首款)节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void firstPay(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode >= 32 && stateCode != 33) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_zhifushejishoukuan;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.shejishoukuan_ico;
            }
            if (stateCode == 32) {
                setItemAnimationForView(textColor, viewHolder);
            }
        } else {
            if (stateCode == 32 || stateCode >= 41) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_zhifushejishoukuan;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.shejishoukuan_ico;
            }
            if (stateCode == 32) {
                setItemAnimationForView(textColor, viewHolder);
            }
        }
        initViewHolder(viewHolder, drawable, textColor);

    }

    /**
     * 接受量房、设计交付物(或者签订设计合同)节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void eliteEstablishContract(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode == 33 || stateCode == 24) {
                viewHolder.tv_meal_title.setText(R.string.receiving_room_deliverable);//量房交付物
            }
            if (stateCode >= 11) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejihetong;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jieshoushejihetong_ico;
            }
        } else {
            if (stateCode == 33 || stateCode == 24) {
                viewHolder.tv_meal_title.setText(R.string.uploaded_deliverable);//量房交付物
            }
            if (stateCode >= 11) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejihetong;
                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jieshoushejihetong_ico;
            }
        }

        if (stateCode == 11 ||stateCode == 31||stateCode == 24 ||stateCode == 33) {
            setItemAnimationForView(textColor, viewHolder);
        }
        initViewHolder(viewHolder, drawable, textColor);

    }

    /**
     * 接受量房、设计交付物(或者签订设计合同)节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void establishContract(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode >= 21) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejihetong;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jieshoushejihetong_ico;
            }
        } else {
            if ((stateCode == 21 || stateCode == 22) || (stateCode >= 31 && stateCode != 33) || stateCode == 33) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_jieshoushejihetong;
                viewHolder.piv_meal_phone.setImageDrawable(UIUtils.getDrawable(R.drawable.icon_jieshoushejihetong));
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.jieshoushejihetong_ico;
            }
        }
        if (stateCode == 31||stateCode == 33) {
            setItemAnimationForView(textColor, viewHolder);
        }
        initViewHolder(viewHolder, drawable, textColor);

    }

    /**
     * 支付量房费用（或者接收量房费）节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void costMeasureFeeNode(ViewHolder viewHolder, int stateCode) {
        int textColor;
        int drawable = R.drawable.icon_liangfang;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {
            if (stateCode == 13) {
                textColor = StepEnableColor;
            } else if (stateCode > 14) {
                textColor = StepEnableColor;
                drawable = R.drawable.icon_liangfang;
            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.liangfangfei_ico;
            }
        } else {
            if (stateCode == 13 || stateCode >= 21) {
                drawable = R.drawable.icon_liangfang;
                textColor = StepEnableColor;

            } else {
                textColor = StepDisEnableColor;
                drawable = R.drawable.liangfangfei_ico;
            }
        }
        initViewHolder(viewHolder, drawable, textColor);
    }

    /**
     * 确认量房（或者量房邀约）节点
     *
     * @param viewHolder
     * @param stateCode
     */
    private void measureFormNode(ViewHolder viewHolder, int stateCode) {
        int drawable = R.drawable.quedingliangfang_ico;
        int textColor = StepDisEnableColor;
        if (Constant.UerInfoKey.CONSUMER_TYPE.equals(member_type)) {// 消费者
            if (stateCode >= 11) {
                drawable = R.drawable.icon_xuanzeshejishi;
                textColor = StepEnableColor;
            }

        } else {//设计师
            if (stateCode >= 11) {
                drawable = R.drawable.icon_xuanzeshejishi;
                textColor = StepEnableColor;
            }
            if (stateCode == 11) {
                setItemAnimationForView(textColor, viewHolder);
            }
        }

        viewHolder.tv_meal_title.setTextColor(textColor);
        initViewHolder(viewHolder, drawable, textColor);
    }

    /**
     * 设置呼吸状态
     *
     * @param textColor
     * @param viewHolder
     */

    private void setItemAnimationForView(int textColor, ViewHolder viewHolder) {
        if (textColor == StepEnableColor) {
            setItemAnimation(viewHolder.piv_meal_phone, false);
        }
    }

    //呼吸状态，动画效果呢；

    public void setItemAnimation(View view, boolean cancelAnimation) {

        imagView_SaleAnimation = new ScaleAnimation(1.0f, 1.25f, 1.0f, 1.25f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
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
    private int tempdate_id;
    private TipWorkFlowTemplateBean tipWorkFlowTemplateBean;
    private String member_type;
    private final int StepEnableColor = Color.rgb(30, 30, 30); // 亮
    private final int StepDisEnableColor = Color.rgb(188, 188, 188); // 暗
    private Animation imagView_SaleAnimation;

}
