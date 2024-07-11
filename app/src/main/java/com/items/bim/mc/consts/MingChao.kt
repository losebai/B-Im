package com.items.bim.mc.consts

import com.items.bim.R
import org.noear.snack.annotation.ONodeAttr

class MingChaoAPI : BaseAPI() {

    companion object {
        const val WUQI = 1106
        const val ROLE = 1105
        const val POST_CATALOGUE: String =
            "https://api.kurobbs.com/wiki/core/catalogue/item/getPage"
        const val POST_HOME_PAGE = "https://api.kurobbs.com/wiki/core/homepage/getPage";
        const val POST_BANNER_LIST = "https://api.kurobbs.com/forum/getBannerListForH5"
        const val POST_RACORD = "https://gmserver-api.aki-game2.com/gacha/record/query"

    }

    override val HOME: String = "https://mc.kurogames.com/main"
    override val WIKI: String = "https://wiki.kurobbs.com/mc/home"

    override val HOME_ICON: String =
        "https://web-static.kurobbs.com/resource/prod/assets/mc-forum-logo-Dj6aNSKC.png"
    override val WIKI_ICON: String =
        "https://prod-alicdn-community.kurobbs.com/postBanner/1718695194603669304.png"


    override val MAP: String = "https://www.kurobbs.com/mc/map/"
    override val MAP_ICON: String =
        "https://prod-alicdn-community.kurobbs.com/postBanner/1719576043888541107.png"


    override val GET_WEB_HOME: String
        get() = super.GET_WEB_HOME
    override val RAKING_ICON: String
        get() = "https://prod-alicdn-community.kurobbs.com/forum/f92b449640374599ae7326e2b46f40b620240509.png"
    override val LOTTERY_ICON: String
        get() =  "https://prod-alicdn-community.kurobbs.com/forum/45963f70164d4c6bb4c5d52fd5f2187620240519.png"
    override val SIMULATE_ICON: String
        get() = "https://prod-alicdn-community.kurobbs.com/forum/ab4d00e54528486996c4ed72fc8ab5b220240520.png"

    override val ROLE_ICON: String
        get() = "https://prod-alicdn-community.kurobbs.com/forum/5e5bb6eaa1de43e6bcb66eb8d780e92c20240509.png"
    override val STREING_ICON: String
        get() = "https://prod-alicdn-community.kurobbs.com/forum/45963f70164d4c6bb4c5d52fd5f2187620240519.png"

    override val bg_id: Int
        get() = R.drawable.tool_mc_bg
}


enum class LotteryPollEnum(@ONodeAttr val value: Int, val poolName: String) {
    UP_ROLE(1, "角色UP"),
    UP_WEAPON(2, "武器Up"),
    C_ROLE(3, "角色常驻"),
    C_WEAPON(4, "武器常驻"),
    INIT_ROLE(5, "新手"),
    ;

    companion object {
        fun toUserStatus(int: Int): LotteryPollEnum {
            for (user: LotteryPollEnum in LotteryPollEnum.entries) {
                if (user.value == int) {
                    return user
                }
            }
            return LotteryPollEnum.INIT_ROLE
        }
    }
}
