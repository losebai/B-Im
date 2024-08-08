package com.items.bim.dto;

import java.time.LocalDateTime;

public class AppUserMessage {

    String messagesId;
    Long sendUserId;
    Long recvUserId;
    String messageData;

    LocalDateTime sendDateTime;
    LocalDateTime recvDateTime;
    Integer ack;

    @Override
    public String toString() {
        return "AppUserMessage{" +
                "messagesId='" + messagesId + '\'' +
                ", sendUserId=" + sendUserId +
                ", recvUserId=" + recvUserId +
                ", messageData='" + messageData + '\'' +
                ", sendDateTime=" + sendDateTime +
                ", recvDateTime=" + recvDateTime +
                ", ack=" + ack +
                '}';
    }

    public String getMessagesId() {
        return messagesId;
    }

    public void setMessagesId(String messagesId) {
        this.messagesId = messagesId;
    }

    public Long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Long getRecvUserId() {
        return recvUserId;
    }

    public void setRecvUserId(Long recvUserId) {
        this.recvUserId = recvUserId;
    }

    public String getMessageData() {
        return messageData;
    }

    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public LocalDateTime getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(LocalDateTime sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public LocalDateTime getRecvDateTime() {
        return recvDateTime;
    }

    public void setRecvDateTime(LocalDateTime recvDateTime) {
        this.recvDateTime = recvDateTime;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }
}
