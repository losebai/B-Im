package com.example.myapplication.mc.consts

class TieDaoAPI : BaseAPI(){

    override val GET_WEB_HOME: String
        get() = "https://bbs-api-static.miyoushe.com/apihub/wapi/webHome?gids=6&page=1&page_size=20"

    override val HOME: String
        get() = "https://sr.mihoyo.com/"
    override val HOME_ICON: String
        get() = "https://www.miyoushe.com/_nuxt/img/game-sr.4f80911.jpg"


    override val WIKI: String
        get() = "https://bbs.mihoyo.com/sr/wiki/"
    override val WIKI_ICON: String
        get() = "https://bbs-static.miyoushe.com/static/2023/04/23/ef35d73eea870358d29adcb9d4d60c00_2550289335377324174.png?x-oss-process=image/resize,s_150/quality,q_80/auto-orient,0/interlace,1/format,png"

    override val MAP: String
        get() = "https://webstatic.mihoyo.com/sr/app/interactive-map/index.html?bbs_presentation_style=no_header#/map/240"

    override val MAP_ICON: String
        get() = "https://bbs-static.miyoushe.com/static/2023/04/23/46b00ea00950105bc9fb0af4b7ef5803_2457579838106825238.png?x-oss-process=image/resize,s_150/quality,q_80/auto-orient,0/interlace,1/format,png"
}