package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-6-12
 * @file MessageCenterBody.java  .
 * @brief 消息中心 .
 */
public class MessageCenterBody implements Serializable {

    private String consumer_id;
    private String designer_id;
    private String for_consumer;
    private String for_designer;
    private String need_id;
    private String sender;
    private String sub_node_id;

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

    @Override
    public String toString() {
        return "MessageBody{" +
                "consumer_id='" + consumer_id + '\'' +
                ", designer_id='" + designer_id + '\'' +
                ", for_consumer='" + for_consumer + '\'' +
                ", for_designer='" + for_designer + '\'' +
                ", need_id='" + need_id + '\'' +
                ", sender='" + sender + '\'' +
                ", sub_node_id='" + sub_node_id + '\'' +
                '}';
    }

}
