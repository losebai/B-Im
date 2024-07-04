package com.example.myapplication.mc.consts



class JueQuZeroAPI : BaseAPI(){

    override val GET_WEB_HOME = "https://bbs-api-static.miyoushe.com/apihub/wapi/webHome?gids=8&page=1&page_size=20";


    override val HOME = "https://zzz.mihoyo.com/main"

    override val WIKI = "https://baike.mihoyo.com/zzz/wiki/"

    override val HOME_ICON: String = "https://bbs-static.miyoushe.com/static/2023/11/03/a656c33c5b828840428aad75a06e7292_3318931884571074949.png"
    override val WIKI_ICON: String
        get() = super.WIKI_ICON
    override val MAP: String
        get() = super.MAP
    override val MAP_ICON: String
        get() = super.MAP_ICON
}