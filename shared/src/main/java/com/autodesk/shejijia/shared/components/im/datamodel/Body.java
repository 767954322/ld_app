package com.autodesk.shejijia.shared.components.im.datamodel;

import java.io.Serializable;

/**
 * Created by allengu on 16-7-26.
 */
public class Body implements Serializable {
    //    "consumer_id":"20738400",
//            "designer_id":"20730531",
//            "for_consumer":"支付设计首款成功，请耐心等待设计师[ 刘新乐 ]为您提供设计方案。",
//            "for_designer":"客户[ 2006848 ]已支付设计首款0.02元，请尽快为客户制作设计方案。",
//            "need_id":"1588424",
//            "sender":"admin",
//            "sub_node_id":"41"
    private String consumer_id;
    private String designer_id;
    private String for_consumer;
    private String for_designer;
    private String need_id;
    private String sender;
    private String sub_node_id;


    public Body(String sub_node_id, String consumer_id, String designer_id, String for_consumer, String for_designer, String need_id, String sender) {
        this.sub_node_id = sub_node_id;
        this.consumer_id = consumer_id;
        this.designer_id = designer_id;
        this.for_consumer = for_consumer;
        this.for_designer = for_designer;
        this.need_id = need_id;
        this.sender = sender;
    }

    public Body() {

    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(String consumer_id) {
        this.consumer_id = consumer_id;
    }

    public String getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(String designer_id) {
        this.designer_id = designer_id;
    }

    public String getFor_consumer() {
        return for_consumer;
    }

    public void setFor_consumer(String for_consumer) {
        this.for_consumer = for_consumer;
    }

    public String getFor_designer() {
        return for_designer;
    }

    @Override
    public String toString() {
        return "Body{" +
                "consumer_id='" + consumer_id + '\'' +
                ", designer_id='" + designer_id + '\'' +
                ", for_consumer='" + for_consumer + '\'' +
                ", for_designer='" + for_designer + '\'' +
                ", need_id='" + need_id + '\'' +
                ", sender='" + sender + '\'' +
                ", sub_node_id='" + sub_node_id + '\'' +
                '}';
    }

    public void setFor_designer(String for_designer) {
        this.for_designer = for_designer;
    }

    public String getNeed_id() {
        return need_id;
    }

    public void setNeed_id(String need_id) {
        this.need_id = need_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSub_node_id() {
        return sub_node_id;
    }

    public void setSub_node_id(String sub_node_id) {
        this.sub_node_id = sub_node_id;
    }
}
