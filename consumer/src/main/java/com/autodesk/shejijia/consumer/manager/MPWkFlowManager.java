package com.autodesk.shejijia.consumer.manager;

import android.content.Context;

import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPSubNodeStateBean;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.StringUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-6-7 .
 * @file MPWkFlowManager.java .
 * @brief 全流程状态显示管理类  .
 */
public class MPWkFlowManager {

    private MPWkFlowManager() {
    }

    /**
     * 获取当前节点的名字
     *
     * @param context            上下文对象
     * @param wk_cur_sub_node_id 当前节点
     * @return 当前节点的名字
     */
    public static String getWkSubNodeName(Context context, String wk_template_id, String wk_cur_sub_node_id) {
        MPSubNodeStateBean MPSubNodeStateBean;
        String jsonFromAsset = null;
        String memType = null;
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (null != memberEntity) {
            memType = memberEntity.getMember_type();
        }

        /// [1]判断是应标、量房还是其他 .
        int templateIdPosition = getTemplateIdPosition(wk_template_id);
        switch (templateIdPosition) {
            case 0:                     /// 应标 .
                if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memType)) {
                    jsonFromAsset = AppJsonFileReader.loadJSONFromAsset(context, Constant.WkFlowStatePath.WF_BID_SUB_NODE_ID_NAME_CONSUMER);
                } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memType)) {
                    jsonFromAsset = AppJsonFileReader.loadJSONFromAsset(context, Constant.WkFlowStatePath.WF_BID_SUB_NODE_ID_NAME_DESIGNER);
                }
                break;
            case 1:                 /// 量房 .
                if (Constant.UerInfoKey.CONSUMER_TYPE.equals(memType)) {
                    jsonFromAsset = AppJsonFileReader.loadJSONFromAsset(context, Constant.WkFlowStatePath.WF_CHOOSE_SUB_NODE_ID_NAME_CONSUMER);
                } else if (Constant.UerInfoKey.DESIGNER_TYPE.equals(memType)) {
                    jsonFromAsset = AppJsonFileReader.loadJSONFromAsset(context, Constant.WkFlowStatePath.WF_CHOOSE_SUB_NODE_ID_NAME_DESIGNER);
                }
                break;
            case 2:                /// 北舒 .
            default:
                break;
        }
        MPSubNodeStateBean = GsonUtil.jsonToBean(jsonFromAsset, MPSubNodeStateBean.class);
        ArrayList<MPSubNodeStateBean.RootEntity> mpSubNodeStateBeanRoot = MPSubNodeStateBean.getRoot();
        String wk_cur_sub_name = "";

        /// [2]获取对应订单节点的名字 .
        if (StringUtils.isNumeric(wk_cur_sub_node_id)) {
            int wk_cur_sub_node_id_temp = Integer.parseInt(wk_cur_sub_node_id);
            if (wk_cur_sub_node_id_temp >= 11 && wk_cur_sub_node_id_temp <= 62) {
                int position = -1;
                switch (templateIdPosition) {
                    case 0:     /// 应标 .
                        position = getBidSubNodePosition(wk_cur_sub_node_id);
                        break;
                    case 1:     /// 量房 .
                        position = getChooseSubNodePosition(wk_cur_sub_node_id);
                        break;
                    default:
                        break;
                }
                wk_cur_sub_name = mpSubNodeStateBeanRoot.get(position).getWk_cur_sub_name();
            } else {
                wk_cur_sub_name = UIUtils.getString(R.string.my_bid_be_being_bid);
            }
        }
        return wk_cur_sub_name;
    }

    /**
     * 获取应标订单相应节点对应的数组索引
     *
     * @param wk_cur_sub_node_id 全流程节点
     *                           11	邀请量房
     *                           12	消费者拒绝设计师
     *                           13	设计师同意量房
     *                           14	 设计师拒绝量房
     *                           21	支付量房费
     *                           22	打开3d工具
     *                           31	设计师发送合同
     *                           33	上传量房交付物
     *                           41	支付设计首款
     *                           42	打开3d工具
     *                           51	支付尾款
     *                           52	打开3d工具
     *                           61	上传支付交付物
     *                           62	 编辑交付物
     * @return 节点对应的索引
     */
    private static int getBidSubNodePosition(String wk_cur_sub_node_id) {
        int subNodePosition = 0;
        if (!StringUtils.isNumeric(wk_cur_sub_node_id)) {
            return subNodePosition;
        }
        switch (Integer.parseInt(wk_cur_sub_node_id)) {
            case 11:
                subNodePosition = 0;
                break;
            case 12:
                subNodePosition = 1;
                break;
            case 13:
                subNodePosition = 2;
                break;
            case 14:
                subNodePosition = 3;
                break;
            case 21:
                subNodePosition = 4;
                break;
            case 22:
                subNodePosition = 5;
                break;
            case 31:
                subNodePosition = 6;
                break;
            case 33:
                subNodePosition = 7;
                break;
            case 41:
                subNodePosition = 8;
                break;
            case 42:
                subNodePosition = 9;
                break;
            case 51:
                subNodePosition = 10;
                break;
            case 52:
                subNodePosition = 11;
                break;
            case 61:
                subNodePosition = 12;
                break;
            case 62:
                subNodePosition = 13;
                break;
        }
        return subNodePosition;
    }

    /**
     * 获取自选量房相应节点对应的数组索引
     *
     * @param wk_cur_sub_node_id 全流程节点
     *                           11	 邀请量房
     *
     *                           12	 设计师同意量房   13
     *                           13	 设计师拒绝量房   14
     *
     *                           21	 支付量房费
     *                           22	 打开3d工具
     *                           31	 设计师发送合同
     *                           33	 上传量房交付物
     *                           41	 支付设计首款
     *                           42	 打开3d工具
     *                           51	 支付尾款
     *                           52	 打开3d工具
     *                           61	 上传支付交付物
     *                           62	 编辑交付物
     * @return 节点对应的索引
     */
    private static int getChooseSubNodePosition(String wk_cur_sub_node_id) {
        int subNodePosition = 0;
        if (!StringUtils.isNumeric(wk_cur_sub_node_id)) {
            return subNodePosition;
        }
        switch (Integer.parseInt(wk_cur_sub_node_id)) {
            case 11:
                subNodePosition = 0;
                break;
            case 13:
                subNodePosition = 1;
                break;
            case 14:
                subNodePosition = 2;
                break;
            case 21:
                subNodePosition = 3;
                break;
            case 22:
                subNodePosition = 4;
                break;
            case 31:
                subNodePosition = 5;
                break;
            case 33:
                subNodePosition = 6;
                break;
            case 41:
                subNodePosition = 7;
                break;
            case 42:
                subNodePosition = 8;
                break;
            case 51:
                subNodePosition = 9;
                break;
            case 52:
                subNodePosition = 10;
                break;
            case 61:
                subNodePosition = 11;
                break;
            case 62:
                subNodePosition = 12;
                break;
        }
        return subNodePosition;
    }

    /**
     * 获取全流程状态模板的索引
     *
     * @param wk_template_id 模板类型
     *                       1	应标
     *                       2	自选
     *                       3	北舒
     * @return 模板对应的索引
     */
    private static int getTemplateIdPosition(String wk_template_id) {
        int templateIdPosition = 0;
        if (!StringUtils.isNumeric(wk_template_id)) {
            return templateIdPosition;
        }
        switch (Integer.parseInt(wk_template_id)) {
            case 1:
                templateIdPosition = 0;
                break;
            case 2:
                templateIdPosition = 1;
                break;
            case 3:
                templateIdPosition = 2;
                break;
        }
        return templateIdPosition;
    }

    /**
     * 获取全流程节点的索引
     *
     * @param wk_cur_node_id 全流程二级节点
     *                       -1	start_node
     *                       1    (designer:量房,user:量房)
     *                       2	(designer:接受量房费,user:支付量房费)
     *                       3	(designer:等待确认合同,user:确认合同)
     *                       4	(designer:等待设计首款,user:支付设计首款)
     *                       5	(designer:等待设计尾款,user:支付设计尾款)
     *                       6	(designer:上传交付物,user:等待交付物)
     * @return 二级节点对应索引
     */
    private static int getCurNodeIdPosition(String wk_cur_node_id) {
        int curNodeIdPosition = 0;
        switch (Integer.parseInt(wk_cur_node_id)) {
            case -1:
                curNodeIdPosition = 0;
                break;
            case 1:
                curNodeIdPosition = 1;
                break;
            case 2:
                curNodeIdPosition = 2;
                break;
            case 3:
                curNodeIdPosition = 3;
                break;
            case 4:
                curNodeIdPosition = 4;
                break;
            case 5:
                curNodeIdPosition = 5;
                break;
            case 6:
                curNodeIdPosition = 6;
                break;
        }
        return curNodeIdPosition;
    }
}
