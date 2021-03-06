package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description:消息中心实体类</p>
 * <p>Copyright: Copyright (c) 2016</p>
 * <p>Company: http://www.leediancn.com</p>
 *
 * @author he.liu .
 * @date 2016-08-27.
 */
public class MessageCenterBean implements Serializable {

    private int count;
    private int limit;
    private int offset;

    private List<MessagesBean> messages;

    public static class MessagesBean implements Serializable {
        private String attachment;
        private String body;
        private String command;
        private String subject;
        private String title;

        private String message_id;
        private String message_media_type;
        private String read_time;
        private String recipient_box;
        private String recipient_member_id;
        private String recipient_profile_image;
        private String recipient_screen_name;
        private String sender_box;
        private String sender_member_id;
        private String sender_profile_image;
        private String sender_screen_name;
        private String sent_time;
        private String thread_id;
        //........新加字段..........
        private String in_reply_to;
//        private String creadted_date;
//        private String total_msg_count;
//        private String unread_msg_count;
//        private List<?> entity;


        public String getIn_reply_to() {
            return in_reply_to;
        }

        public void setIn_reply_to(String in_reply_to) {
            this.in_reply_to = in_reply_to;
        }

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage_id() {
            return message_id;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public String getMessage_media_type() {
            return message_media_type;
        }

        public void setMessage_media_type(String message_media_type) {
            this.message_media_type = message_media_type;
        }

        public String getRead_time() {
            return read_time;
        }

        public void setRead_time(String read_time) {
            this.read_time = read_time;
        }

        public String getRecipient_box() {
            return recipient_box;
        }

        public void setRecipient_box(String recipient_box) {
            this.recipient_box = recipient_box;
        }

        public String getRecipient_member_id() {
            return recipient_member_id;
        }

        public void setRecipient_member_id(String recipient_member_id) {
            this.recipient_member_id = recipient_member_id;
        }

        public String getRecipient_profile_image() {
            return recipient_profile_image;
        }

        public void setRecipient_profile_image(String recipient_profile_image) {
            this.recipient_profile_image = recipient_profile_image;
        }

        public String getRecipient_screen_name() {
            return recipient_screen_name;
        }

        public void setRecipient_screen_name(String recipient_screen_name) {
            this.recipient_screen_name = recipient_screen_name;
        }

        public String getSender_box() {
            return sender_box;
        }

        public void setSender_box(String sender_box) {
            this.sender_box = sender_box;
        }

        public String getSender_member_id() {
            return sender_member_id;
        }

        public void setSender_member_id(String sender_member_id) {
            this.sender_member_id = sender_member_id;
        }

        public String getSender_profile_image() {
            return sender_profile_image;
        }

        public void setSender_profile_image(String sender_profile_image) {
            this.sender_profile_image = sender_profile_image;
        }

        public String getSender_screen_name() {
            return sender_screen_name;
        }

        public void setSender_screen_name(String sender_screen_name) {
            this.sender_screen_name = sender_screen_name;
        }

        public String getSent_time() {
            return sent_time;
        }

        public void setSent_time(String sent_time) {
            this.sent_time = sent_time;
        }

        public String getThread_id() {
            return thread_id;
        }

        public void setThread_id(String thread_id) {
            this.thread_id = thread_id;
        }

        @Override
        public String toString() {
            return "MessagesBean{" +
                    "attachment='" + attachment + '\'' +
                    ", body='" + body + '\'' +
                    ", command='" + command + '\'' +
                    ", subject='" + subject + '\'' +
                    ", title='" + title + '\'' +
                    ", message_id='" + message_id + '\'' +
                    ", message_media_type='" + message_media_type + '\'' +
                    ", read_time='" + read_time + '\'' +
                    ", recipient_box='" + recipient_box + '\'' +
                    ", recipient_member_id='" + recipient_member_id + '\'' +
                    ", recipient_profile_image='" + recipient_profile_image + '\'' +
                    ", recipient_screen_name='" + recipient_screen_name + '\'' +
                    ", sender_box='" + sender_box + '\'' +
                    ", sender_member_id='" + sender_member_id + '\'' +
                    ", sender_profile_image='" + sender_profile_image + '\'' +
                    ", sender_screen_name='" + sender_screen_name + '\'' +
                    ", sent_time='" + sent_time + '\'' +
                    ", thread_id='" + thread_id + '\'' +
                    ", in_reply_to='" + in_reply_to + '\'' +
                    '}';
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<MessagesBean> getMessages() {
        return messages;
    }

    public void setMessages(List<MessagesBean> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageCenterBean{" +
                "count=" + count +
                ", limit=" + limit +
                ", offset=" + offset +
                ", messages=" + messages +
                '}';
    }
}
