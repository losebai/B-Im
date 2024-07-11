package com.items.bim.mc.consts

import com.items.bim.R

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

    override val RAKING_ICON: String
        get() = "https://act-upload.mihoyo.com/sr-wiki/2024/01/17/279865110/309646beeab72fcb764c06187443ff82_7283839332794332870.png?x-oss-process=image/quality,q_75/resize,s_96"
    override val LOTTERY_ICON: String
        get() = "https://act-upload.mihoyo.com/sr-wiki/2023/04/21/288909604/8791ff6f87a1c137be0af318abb9d4aa_3700835824542392092.png?x-oss-process=image/quality,q_75/resize,s_96"
    override val SIMULATE_ICON: String
        get() = "https://act-upload.mihoyo.com/sr-wiki/2023/04/21/288909604/efc405ac1839b2f0cf0a0e5568d2a78a_2407769840691232900.png?x-oss-process=image/quality,q_75/resize,s_96"

    override val ROLE_ICON: String
        get() = "https://act-upload.mihoyo.com/sr-wiki/2023/04/21/288909604/eb7c80e9dfc503a24c281be6a89ab013_779574104987902327.png?x-oss-process=image/quality,q_75/resize,s_96"
    override val STREING_ICON: String
        get() = "https://act-upload.mihoyo.com/sr-wiki/2023/04/21/288909604/2e26011af9032d72b665a2f90cc483f0_4601761747747010855.png?x-oss-process=image/quality,q_75/resize,s_96"

    override val bg_id: Int
        get() = R.drawable.tool_td_bg
}