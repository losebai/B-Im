package com.example.myapplication.mc.consts

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
}

