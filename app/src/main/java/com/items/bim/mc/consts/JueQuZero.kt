package com.items.bim.mc.consts

import com.items.bim.R


class JueQuZeroAPI : BaseAPI(){

    override val GET_WEB_HOME = "https://bbs-api-static.miyoushe.com/apihub/wapi/webHome?gids=8&page=1&page_size=20";


    override val HOME = "https://zzz.mihoyo.com/main"

    override val WIKI = "https://baike.mihoyo.com/zzz/wiki/"

    override val HOME_ICON: String = "https://bbs-static.miyoushe.com/static/2023/11/03/a656c33c5b828840428aad75a06e7292_3318931884571074949.png"
    override val WIKI_ICON: String
        get() = "https://bbs-static.miyoushe.com/static/2024/07/04/36eb2f754af87c2b8d2490c95d2a5a0f_8638554836896061640.png?x-oss-process=image/resize,s_150/quality,q_80/auto-orient,0/interlace,1/format,png"
    override val MAP: String
        get() = super.MAP
    override val MAP_ICON: String
        get() = super.MAP_ICON

    override val RAKING_ICON: String
        get() = "https://act-upload.mihoyo.com/nap-obc-indep/2024/06/01/76099754/3593482e8866f0529e8a247772e02cf4_5418014644502214835.png?x-oss-process=image/quality,q_75/resize,s_96"
    override val LOTTERY_ICON: String
        get() = "https://act-upload.mihoyo.com/nap-obc-indep/2024/05/21/284550490/ac51f7f15784b399abaa3afc0cc533e6_984961226078874111.png"
    override val SIMULATE_ICON: String
        get() = "https://act-upload.mihoyo.com/nap-obc-indep/2024/05/21/284550490/73df4c85103d663040bf462712bbbaa0_57857857507272263.png?x-oss-process=image/resize,m_fill,w_186,h_186,limit_0/format,webp"

    override val ROLE_ICON: String
        get() = "https://act-upload.mihoyo.com/nap-obc-indep/2024/06/01/76099754/1e8d488828fed5c9caba7eb393d18a52_4588078170030823167.png?x-oss-process=image/quality,q_75/resize,s_96"
    override val STREING_ICON: String
        get() = "https://act-upload.mihoyo.com/nap-obc-indep/2024/06/01/76099754/b61294cb18cd1fd562ffe2a0c6f0642e_536792962072159478.png?x-oss-process=image/quality,q_75/resize,s_96"

    override val bg_id: Int
        get() = R.drawable.tool_zero_bg
}