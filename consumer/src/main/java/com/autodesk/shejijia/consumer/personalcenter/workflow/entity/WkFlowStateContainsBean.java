package com.autodesk.shejijia.consumer.personalcenter.workflow.entity;

import java.util.Map;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 2016-8-3 .
 * @file WkFlowStateBean.java .
 * @brief 全流程状态节点信息bean.
 */
public class WkFlowStateContainsBean {

    /**
     * 22 : {"consumerMessage":"","id":"22","designerMessage":"","description":"打开3D设计","name":"open_3d_design"}
     * 33 : {"consumerMessage":"","id":"33","designerMessage":"","description":"发送量房文件","name":"deliver_measure_file"}
     * 13 : {"consumerMessage":"未支付量房费","id":"13","designerMessage":"等待支付量房费","description":"确认量房","name":"confirm_measure"}
     * 14 : {"consumerMessage":"订单结束  设计师已取消量房","id":"14","designerMessage":"订单结束  已取消量房","description":"设计师拒绝量房","name":"decline_invite_measure"}
     * 11 : {"consumerMessage":"等待设计师确认量房","id":"11","designerMessage":"等待接受量房邀请","description":"邀请量房","name":"invite_measure"}
     * 12 : {"consumerMessage":"","id":"12","designerMessage":"","description":"消费者拒绝量房","name":"decline_measure"}
     * 21 : {"consumerMessage":"等待接收设计合同","id":"21","designerMessage":"未创建设计合同","description":"支付量房","name":"pay_for_measure"}
     * 3 : {"consumerMessage":"","id":"3","designerMessage":"","description":"签订设计合同","name":"confirm_contract"}
     * 2 : {"consumerMessage":"","id":"2","designerMessage":"","description":"支付量房费","name":"payment_of_measure"}
     * 1 : {"consumerMessage":"","id":"1","designerMessage":"","description":"确认量房","name":"measure"}
     * 42 : {"consumerMessage":"","id":"42","designerMessage":"","description":"打开3D设计","name":"open_3d_design"}
     * 6 : {"consumerMessage":"","id":"6","designerMessage":"","description":"设计交付","name":"delivery"}
     * 41 : {"consumerMessage":"未支付设计尾款","id":"41","designerMessage":"等待客户支付设计尾款","description":"支付设计首款","name":"pay_for_first_fee"}
     * 5 : {"consumerMessage":"","id":"5","designerMessage":"","description":"支付设计尾款","name":"payment_of_last_fee"}
     * 31 : {"consumerMessage":"未签订设计合同","id":"31","designerMessage":"等待客户支付首款","description":"发送合同","name":"author_send_contract"}
     * 4 : {"consumerMessage":"","id":"4","designerMessage":"","description":"支付设计首款","name":"payment_of_first_fee"}
     * 64 : {"consumerMessage":"未确认设计交付","id":"64","designerMessage":"客户已延期确认设计交付","description":"交付物延期确认","name":"delay_confirm_design_results"}
     * 51 : {"consumerMessage":"等待接收设计交付","id":"51","designerMessage":"未上传设计交付","description":"支付设计尾款","name":"pay_for_last_fee"}
     * 52 : {"consumerMessage":"","id":"52","designerMessage":"","description":"打开3D设计","name":"open_3d_design"}
     * 62 : {"consumerMessage":"未确认设计交付","id":"62","designerMessage":"等待客户确认设计交付","description":"查看交付物","name":"review_design_results"}
     * 63 : {"consumerMessage":"设计完成","id":"63","designerMessage":"订单完成","description":"交付物确认","name":"confirm_design_results"}
     * 61 : {"consumerMessage":"未确认设计交付","id":"61","designerMessage":"等待客户确认设计交付","description":"上传交付物","name":"deliver_design_results"}
     * 100 : {"consumerMessage":"项目结束","id":"100","designerMessage":"项目结束","description":"项目结束","name":"complete_design"}
     */

    private Map<String,WkFlowStateBean> nodes_message;

    public Map<String, WkFlowStateBean> getNodes_message() {
        return nodes_message;
    }

    public void setNodes_message(Map<String, WkFlowStateBean> nodes_message) {
        this.nodes_message = nodes_message;
    }

    @Override
    public String toString() {
        return "WkFlowStateContainsBean{" +
                "nodes_message=" + nodes_message +
                '}';
    }
}
