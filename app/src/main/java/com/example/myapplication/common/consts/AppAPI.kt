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

    object DictAPI{
        const  val GET_DICT_BY_KEYS: String = "/dispatch-app/dict/getDictByKeys"


    }

    object AppLotteryPoolAPI{
        const val CURRENT_POOLS = "/dispatch-app/AppLotteryPool/currentPools"

        const val RANDOM_AWARD = "/dispatch-app/AppLotteryAward/randomAppAward"

        const val LOTTERY_AWARD_COUNT = "/dispatch-app/AppLotteryAward/lotteryAwayCount"

        const val LOTTERY_AWARD_COUNT_PROD = "/dispatch-app/AppLotteryAward/lotteryAwayCountPro"

        const val AWARD_ASYNC_RECORD = "/dispatch-app/AppLotteryAward/asyncMcRecord"
    }

    object RakingAPI{
        const val GET_RAKING_LIST = "/dispatch-app/raking/rakingList"

        const val GET_USER_GAME = "/dispatch-app/raking/userGame"
    }

    object FileAPI{
        const val POST_FILE_UPLOAD = "/dispatch-app/file/uploadImage"
    }
}