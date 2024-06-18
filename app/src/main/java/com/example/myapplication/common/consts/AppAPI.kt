package com.example.myapplication.common.consts

object AppAPI {


    const val GET_USER: String = "/dispatch-app/appUser/"

    const val GET_USER_BY_NUMBER: String = "/dispatch-app/appUser/gerUserByNumber"

    const val POST_USER_SAVE: String = "/dispatch-app/appUser/save"

    const val POST_USER_LIST: String = "/dispatch-app/appUser/list"



    object CommunityAPI{
        const  val GET_DYNAMIC: String = "/dispatch-app/appDynamic/"

        const  val GET_DYNAMIC_PAGE: String = "/dispatch-app/appDynamic/page"

        const  val GET_DYNAMIC_SAVE: String = "/dispatch-app/appDynamic/save"
    }

    object AppLotteryPoolAPI{
        const val CURRENT_POOLS = "/dispatch-app/AppLotteryPool/currentPools"
    }

    object MingChao {

        const val WUQI = 1106

        const val ROLE = 1105

        const val MAIN_URL = "https://mc.kurogames.com/main"

        const val WIKI_URL =  "https://wiki.kurobbs.com/mc/home"

        const val POST_CATALOGUE: String = "https://api.kurobbs.com/wiki/core/catalogue/item/getPage"
    }
}