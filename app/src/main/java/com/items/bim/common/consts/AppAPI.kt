package com.items.bim.common.consts

import android.os.Build.VERSION
import com.items.bim.BuildConfig

object AppAPI {


    const val GET_USER: String = "/dispatch-app/appUser/"

    const val GET_USER_BY_NUMBER: String = "/dispatch-app/appUser/guest/gerUserByNumber"

    const val POST_USER_SAVE: String = "/dispatch-app/appUser/guest/save"

    const val POST_USER_UPDATE: String = "/dispatch-app/appUser/update"

    const val POST_USER_LIST: String = "/dispatch-app/appUser/list"

    const val CONFIG_URI: String  = "/dispatch-app/config/app-config-${BuildConfig.VERSION_NAME}.json"

    object LoginAPI{
        const val GET_SEND_CODE = "/dispatch-app/email/sendCode"

        const val POST_USER_LOGIN =  "/dispatch-app/userLogin/login"

        const val POST_USER_LOGOUT =  "/dispatch-app/userLogin/logout"

        const val POST_USER_REGISTER =  "/dispatch-app/userLogin/register"

        const val GET_CUR_USER = "/dispatch-app/userLogin/currUser"
    }

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

    object AppGameRoleRaking{
        const val GET_APP_GAME_ROLE = "/dispatch-app/appGameRoleRaking/appGameRole"
    }
}