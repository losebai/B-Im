package com.items.bim.dto

import java.time.LocalDateTime


data class GameRoleDto(
    var id: Int = 0,
    var gameName: String? = null,
    var name: String = "",
    var hookId: Long? = null,
    var imageUri: String = "",
    var star: Int? = null,
    var updateTime: LocalDateTime? = null,
    var remake: String? = null,
    /**
     * 攒
     */
    var up: Int? = null,
    /**
     * 踩
     */
    var tr: Int? = null,
    var type: String? = null,
    var raking : Int = 0
)

data class AppGameRole(
    var updateTime : LocalDateTime? = null,
    var appGameRoleRaking : Map<String, List<GameRoleDto>> = mapOf()
)