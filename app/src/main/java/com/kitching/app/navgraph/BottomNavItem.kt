package com.kitching.app.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.kitching.app.R

data class BottomNavItem(
    val tabName: String,
    val icon: ImageVector,
    val destination: BottomNavigationItem
) {
    companion object {
        @Composable
        fun renderBottomNavItems(): List<BottomNavItem> {
            return listOf(
                BottomNavItem(
                    tabName = stringResource(R.string.schedule),
                    icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_schedule),
                    destination = ScheduleTab
                ),
                BottomNavItem(
                    tabName = stringResource(R.string.prep),
                    icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_prep),
                    destination = PrepGraph
                ),
                BottomNavItem(
                    tabName = stringResource(R.string.recipe),
                    icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                    destination = RecipeGraph
                ),
                BottomNavItem(
                    tabName = stringResource(R.string.order),
                    icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                    destination = OrderGraph
                ),
                BottomNavItem(
                    tabName = stringResource(R.string.other),
                    icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                    destination = OtherGraph
                )
            )
        }
    }
}