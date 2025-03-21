package com.kitching.app.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.kitching.app.R

data class BottomNavItem(
    val tabName: String = "",
    val icon: ImageVector = Icons.Default.Home,
    val destination: BottomNavItems = ScheduleTab
) {
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
                destination = PrepTab
            ),
            BottomNavItem(
                tabName = stringResource(R.string.recipe),
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                destination = RecipeTab
            ),
            BottomNavItem(
                tabName = stringResource(R.string.order),
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                destination = OrderTab
            ),
            BottomNavItem(
                tabName = stringResource(R.string.other),
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                destination = OtherTab
            )
        )
    }
}