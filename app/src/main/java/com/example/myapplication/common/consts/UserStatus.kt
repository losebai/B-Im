package com.example.myapplication.common.consts

import org.noear.snack.annotation.ONodeAttr

enum class UserStatus(@ONodeAttr val value: Int, val tag: String) {
    INIT(0, "未知"),
    OFF_LINE(1,"离线"), // 离线
    ON_LINE(10, "在线"), // 在线
    HiDING(11, "隐身"); // 隐身

    companion object {
        fun toUserStatus(int: Int): UserStatus{
            for (user: UserStatus in entries){
                if (user.value == int){
                    return user
                }
            }
            return INIT
        }
    }
}