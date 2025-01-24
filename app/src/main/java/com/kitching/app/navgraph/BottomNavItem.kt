package com.kitching.app.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kitching.app.R

data class BottomNavItem(
    val tabName: String = "",
    val icon: ImageVector = Icons.Default.Home,
    val routeName: String = ""
) {
    @Composable
    fun renderBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabName = "스케줄",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_schedule),
                routeName = "Schedule"
            ),
            BottomNavItem(
                tabName = "프렙",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_prep),
                routeName = "Prep"
            ),
            BottomNavItem(
                tabName = "레시피",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_recipe),
                routeName = "Recipe"
            ),
            BottomNavItem(
                tabName = "발주목록",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_order),
                routeName = "Order"
            ),
            BottomNavItem(
                tabName = "Other",
                icon = ImageVector.vectorResource(R.drawable.icon_bottom_nav_other),
                routeName = "Other"
            )
        )
    }
}