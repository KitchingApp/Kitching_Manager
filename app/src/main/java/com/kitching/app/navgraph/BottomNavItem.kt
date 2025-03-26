package com.kitching.app.navgraph

import androidx.compose.runtime.Composable
import com.kitching.app.R

/**
 * 바텀네비게이션 메뉴 정의
 *
 * @property tabName resource ID 사용
 * @property icon resource ID 사용
 * @property destination
 */
data class BottomNavItem(
    val tabName: Int,
    val icon: Int,
    val destination: ScreenRouteDef
) {
    companion object {
        @Composable
        fun renderBottomNavItems(): List<BottomNavItem> {
            return listOf(
                BottomNavItem(
                    tabName = R.string.schedule,
                    icon = R.drawable.icon_bottom_nav_schedule,
                    destination = ScreenRouteDef.ScheduleGraph
                ),
                BottomNavItem(
                    tabName = R.string.prep,
                    icon = R.drawable.icon_bottom_nav_prep,
                    destination = ScreenRouteDef.PrepGraph
                ),
                BottomNavItem(
                    tabName = R.string.recipe,
                    icon = R.drawable.icon_bottom_nav_recipe,
                    destination = ScreenRouteDef.RecipeGraph
                ),
                BottomNavItem(
                    tabName = R.string.order,
                    icon = R.drawable.icon_bottom_nav_order,
                    destination = ScreenRouteDef.OrderGraph
                ),
                BottomNavItem(
                    tabName = R.string.other,
                    icon = R.drawable.icon_bottom_nav_other,
                    destination = ScreenRouteDef.OtherGraph
                )
            )
        }
    }
}