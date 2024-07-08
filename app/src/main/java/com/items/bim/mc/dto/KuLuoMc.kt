package com.items.bim.mc.dto



data class BannerDto(
        var contentType:Int = 0,
        var gameId: Int = 0,
        var name:String = "",
        var toAppAndroid : String = "",
        var toAppIOS: String = "",
        var url: String = "",
        var linkUri: String = ""
)