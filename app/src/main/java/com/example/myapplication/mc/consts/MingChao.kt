package com.example.myapplication.mc.consts

import org.noear.snack.annotation.ONodeAttr

object MingChaoAPI {

    const val WUQI = 1106

    const val ROLE = 1105

    const val MAIN_URL = "https://mc.kurogames.com/main"

    const val WIKI_URL =  "https://wiki.kurobbs.com/mc/home"

    const val POST_CATALOGUE: String = "https://api.kurobbs.com/wiki/core/catalogue/item/getPage"

    const val POST_HOME_PAGE = "https://api.kurobbs.com/wiki/core/homepage/getPage";

    const val POST_BANNER_LIST = "https://api.kurobbs.com/forum/getBannerListForH5"

    const val POST_RACORD = "https://gmserver-api.aki-game2.com/gacha/record/query"
}


enum class LotteryPollEnum(@ONodeAttr val value: Int, val poolName: String) {
    UP_ROLE(1 ,"角色UP"),
    UP_WEAPON(2, "武器Up"),
    C_ROLE(3, "角色常驻"),
    C_WEAPON(4, "武器常驻"),
    INIT_ROLE(5, "新手"),
    ;

    companion object {
        fun toUserStatus(int: Int): LotteryPollEnum {
            for (user: LotteryPollEnum in LotteryPollEnum.entries){
                if (user.value == int){
                    return user
                }
            }
            return LotteryPollEnum.INIT_ROLE
        }
    }
}
