package com.autodesk.shejijia.consumer.personalcenter.consumer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author gumenghao .
 * @version 1.0 .
 * @date 16-6-12
 * @file MessageCenter.java  .
 * @brief 消息中心 .
 */
public class MessageCenter implements Serializable {
    private int count;
    private int limit;
    private int offset;
    private List<Message> messages;

    public static class Message implements Serializable {

        private String body;
        private String messageId;
        private String messageMediaType;
        private int recipientMemberId;
        private String recipientProfileImage;
        private String recipientScreenName;
        private String senderBox;
        private int senderMemberId;
        private String senderProfileImage;
        private String senderScreenName;
        private String sentTime;
        private String subject;
        private String threadId;
        private int totalMsgCount;
        private int unreadMsgCount;


        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getMessageMediaType() {
            return messageMediaType;
        }

        public void setMessageMediaType(String messageMediaType) {
            this.messageMediaType = messageMediaType;
        }

        public int getRecipientMemberId() {
            return recipientMemberId;
        }

        public void setRecipientMemberId(int recipientMemberId) {
            this.recipientMemberId = recipientMemberId;
        }

        public String getRecipientProfileImage() {
            return recipientProfileImage;
        }

        public void setRecipientProfileImage(String recipientProfileImage) {
            this.recipientProfileImage = recipientProfileImage;
        }

        public String getRecipientScreenName() {
            return recipientScreenName;
        }

        public void setRecipientScreenName(String recipientScreenName) {
            this.recipientScreenName = recipientScreenName;
        }

        public String getSenderBox() {
            return senderBox;
        }

        public void setSenderBox(String senderBox) {
            this.senderBox = senderBox;
        }

        public int getSenderMemberId() {
            return senderMemberId;
        }

        public void setSenderMemberId(int senderMemberId) {
            this.senderMemberId = senderMemberId;
        }

        public String getSenderProfileImage() {
            return senderProfileImage;
        }

        public void setSenderProfileImage(String senderProfileImage) {
            this.senderProfileImage = senderProfileImage;
        }

        public String getSenderScreenName() {
            return senderScreenName;
        }

        public void setSenderScreenName(String senderScreenName) {
            this.senderScreenName = senderScreenName;
        }

        public String getSentTime() {
            return sentTime;
        }

        public void setSentTime(String sentTime) {
            this.sentTime = sentTime;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getThreadId() {
            return threadId;
        }

        public void setThreadId(String threadId) {
            this.threadId = threadId;
        }

        public int getTotalMsgCount() {
            return totalMsgCount;
        }

        public void setTotalMsgCount(int totalMsgCount) {
            this.totalMsgCount = totalMsgCount;
        }

        public int getUnreadMsgCount() {
            return unreadMsgCount;
        }

        public void setUnreadMsgCount(int unreadMsgCount) {
            this.unreadMsgCount = unreadMsgCount;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "body=" + body +
                    ", messageId='" + messageId + '\'' +
                    ", messageMediaType='" + messageMediaType + '\'' +
                    ", recipientMemberId=" + recipientMemberId +
                    ", recipientProfileImage='" + recipientProfileImage + '\'' +
                    ", recipientScreenName='" + recipientScreenName + '\'' +
                    ", senderBox='" + senderBox + '\'' +
                    ", senderMemberId=" + senderMemberId +
                    ", senderProfileImage='" + senderProfileImage + '\'' +
                    ", senderScreenName='" + senderScreenName + '\'' +
                    ", sentTime='" + sentTime + '\'' +
                    ", subject='" + subject + '\'' +
                    ", threadId='" + threadId + '\'' +
                    ", totalMsgCount=" + totalMsgCount +
                    ", unreadMsgCount=" + unreadMsgCount +
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "MessageCenter{" +
                "count=" + count +
                ", limit=" + limit +
                ", offset=" + offset +
                ", messages=" + messages +
                '}';
    }
}
