package com.example.myapplication.common.consts

enum class UserStatus(private val value: Int) {
    INIT(0),
    OFF_LINE(-1), // 离线
    ON_LINE(1), // 在线
    HiDING(3); // 隐身
}