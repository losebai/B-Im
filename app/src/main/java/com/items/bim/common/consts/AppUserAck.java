package com.items.bim.common.consts;


public enum AppUserAck {
    INIT(0),

    SEND(1),

    SERVER_ACK(2),

    STOP_ACK(12),

    ACK_OK(3), // 接收确认

    SERVER_READ_ACK(4), // 服务器已读

    READ_OK(5), // 已读

    ERROR_ACK(11);


    private final Integer value;

    AppUserAck(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
