package com.kitching.app.common

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.kitching.app.R
import kotlinx.coroutines.CoroutineScope

enum class ActionIconInfo(val icon: Int, val description: String) {
    ADD(R.drawable.icon_add, "add button"),
    OPTION(R.drawable.icon_options, "option button"),
    CHECK(R.drawable.icon_check, "confirm button"),
    NULL(R.drawable.icon_check, "no action")
}

enum class NavigationIconInfo(val icon: Int, val description: String) {
    DRAWER(R.drawable.icon_hamburger_menu, "drawer icon"),
    BACK(R.drawable.icon_arrow_back, "back button")
}

data class TopAppBarState(
    val title: String = "Kitching",
    val drawerState: DrawerState,
    val navIconInfo: NavigationIconInfo = NavigationIconInfo.DRAWER,
    val onClickNavIcon: () -> Unit = {},
    val actionIconInfo: ActionIconInfo = ActionIconInfo.ADD,
    val onClickActionIcon: () -> Unit = {}
)

data class CommonState(
    val navController: NavHostController,
    var topAppBarState: MutableState<TopAppBarState>,
    val scope: CoroutineScope,
    )