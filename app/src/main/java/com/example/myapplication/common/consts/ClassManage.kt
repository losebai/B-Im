package com.example.myapplication.common.consts

import com.example.myapplication.entity.AppDynamic
import com.example.myapplication.remote.entity.AppUserEntity


val AppUserEntityClass: Class<AppUserEntity> = AppUserEntity().javaClass

val AppDynamicClass : Class<AppDynamic> =  AppDynamic().javaClass