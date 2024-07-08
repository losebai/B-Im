package com.items.bim.common.ui.web

import android.net.Uri
import androidx.navigation.NavHostController
import com.items.bim.common.util.WanHelper


class WanNavActions(
    private val navController: NavHostController
) {
    val navigateToBookmarkHistory: () -> Unit = {
        navigate(WanDestinations.BOOKMARK_HISTORY_ROUTE)
    }
    val navigateToDemo: () -> Unit = {
        navigate(WanDestinations.DEMO_ROUTE)
    }
    val navigateToLogin: () -> Unit = {
        navigate(WanDestinations.LOGIN_ROUTE)
    }
    val navigateToMyCoin: () -> Unit = {
        navigate(WanDestinations.MY_COIN_ROUTE)
    }
    val navigateToMyCollect: () -> Unit = {
        navigate(WanDestinations.MY_COLLECT_ROUTE)
    }
    val navigateToMyShare: () -> Unit = {
        navigate(WanDestinations.MY_SHARE_ROUTE)
    }
    val navigateToRank: () -> Unit = {
        navigate(WanDestinations.RANK_ROUTE)
    }
    val navigateToRegister: () -> Unit = {
        navigate(WanDestinations.REGISTER_ROUTE)
    }
    val navigateToSearch: (key: String) -> Unit = {
        navigate(WanDestinations.SEARCH_ROUTE + "/$it")
    }
    val navigateToSetting: () -> Unit = {
        navigate(WanDestinations.SETTING_ROUTE)
    }
    val navigateToShareArticle: () -> Unit = {
        navigate(WanDestinations.SHARE_ARTICLE_ROUTE)
    }
    val navigateToSystem: (cid: String) -> Unit = {
        navigate(WanDestinations.SYSTEM_ROUTE + "/$it")
    }
    val navigateToUser: (userId: String) -> Unit = {
        navigate(WanDestinations.USER_ROUTE + "/$it")
    }
    val navigateToWeb: (url: String) -> Unit = {
        navigate(WanDestinations.WEB_ROUTE + "/${Uri.encode(it)}")
    }
    val navigateUp: () -> Unit = {
        if (!navController.navigateUp()) {
            navigate(WanDestinations.MAIN_ROUTE)
        }
    }

    val popBackStack: (route: String) -> Unit = {
        navController.popBackStack(it, false)
    }

    fun navigate(route: String) {
        navController.graph.findNode(route) ?: return
        WanHelper.getUser { user ->
            navController.navigate(
                if (interceptRoute(route) && user.name.isBlank()) {
                    WanDestinations.LOGIN_ROUTE
                } else {
                    route
                }
            )
        }
    }
}

object WanDestinations {
    const val BOOKMARK_HISTORY_ROUTE = "bookmark_history_route"
    const val DEMO_ROUTE = "demo_route"
    const val LOGIN_ROUTE = "login_route"
    const val MAIN_ROUTE = "main_route"
    const val MY_COIN_ROUTE = "my_coin_route"
    const val MY_COLLECT_ROUTE = "my_collect_route"
    const val MY_SHARE_ROUTE = "my_share_route"
    const val RANK_ROUTE = "rank_route"
    const val REGISTER_ROUTE = "register_route"
    const val SEARCH_ROUTE = "search_route"
    const val SETTING_ROUTE = "setting_route"
    const val SHARE_ARTICLE_ROUTE = "share_article_route"
    const val SYSTEM_ROUTE = "system_route"
    const val USER_ROUTE = "user_route"
    const val WEB_ROUTE = "web_route"
}

private fun interceptRoute(route: String): Boolean {
    return route.startsWith(WanDestinations.MY_COIN_ROUTE)
            || route.startsWith(WanDestinations.MY_COLLECT_ROUTE)
            || route.startsWith(WanDestinations.MY_SHARE_ROUTE)
}