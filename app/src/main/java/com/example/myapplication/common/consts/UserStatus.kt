package com.example.myapplication.common.consts

enum class UserStatus(private val value: Int, public val tag: String) {
    INIT(0, ""),
    OFF_LINE(1,"离线"), // 离线
    ON_LINE(10, "在线"), // 在线
    HiDING(11, "隐身"); // 隐身
}