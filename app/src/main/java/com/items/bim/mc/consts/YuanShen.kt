package com.items.bim.mc.consts

import com.items.bim.R

class YuanShenAPI : BaseAPI() {

     override var GET_WEB_HOME = "https://bbs-api-static.miyoushe.com/apihub/wapi/webHome?gids=2&page=1&page_size=20"


     override var HOME = "https://ys.mihoyo.com/"
     override val HOME_ICON: String = "https://www.miyoushe.com/_nuxt/img/game-ys.dfc535b.jpg"


     override val WIKI: String = "https://bbs.mihoyo.com/ys/obc/"
     override val WIKI_ICON: String = "https://bbs-static.miyoushe.com/static/2023/03/31/19911bcf951e8d3016e7874c52a31474_4243794310247796689.png?x-oss-process=image/resize,s_150/quality,q_80/auto-orient,0/interlace,1/format,png"


     override val MAP: String
         = "https://act.mihoyo.com/ys/app/interactive-map/index.html?bbs_presentation_style=no_header&lang=zh-cn&utm_source=bbs&utm_medium=mys&utm_campaign=pcicon&_markerFps=24#/map/2"
     override val MAP_ICON: String
          = "https://bbs-static.miyoushe.com/static/2023/03/31/18cec053ef31537d0802502a28b32857_2187383493070612518.png?x-oss-process=image/resize,s_150/quality,q_80/auto-orient,0/interlace,1/format,png"


     override val RAKING_ICON: String
          get() = "https://uploadstatic.mihoyo.com/ys-obc/2021/06/07/75276545/5b66dea03fe089075c3d95573ab07047_2781055864473387780.png?x-oss-process=image/quality,q_75/resize,s_96"
     override val LOTTERY_ICON: String
          get() = "https://uploadstatic.mihoyo.com/ys-obc/2021/06/07/75276545/96545e91b257a990d53e11732df53cbf_5018949295715050020.png"
     override val SIMULATE_ICON: String
          get() = "https://act-upload.mihoyo.com/ys-obc/2024/07/02/195563531/1bebdc0a2c472525c184d51e7ebd6e43_2332103853725934143.png"

     override val ROLE_ICON: String
          get() = "https://uploadstatic.mihoyo.com/ys-obc/2021/06/07/75276545/da087c70d5ce5e95ab353896cde77186_732830328053361739.png?x-oss-process=image/quality,q_75/resize,s_96"
     override val STREING_ICON: String
          get() = "https://prod-alicdn-community.kurobbs.com/forum/45963f70164d4c6bb4c5d52fd5f2187620240519.png"

     override val bg_id: Int
          get() = R.drawable.tool_ys_bg
}

