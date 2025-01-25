package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.kitching.app.navgraph.BottomNavItem
import com.kitching.app.ui.theme.bottomNavItemColor
import com.kitching.app.ui.theme.bottomNavLabelColor
import com.kitching.app.ui.theme.mainColor
import com.kitching.app.ui.theme.pretendard

@Composable
fun CustomNavigationBar(
    navController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier.drawBehind {
            drawLine(
                color = bottomNavLabelColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 0.5f,
            )
        },
        containerColor = Color.White,
        contentColor = bottomNavItemColor,
    ) {
        BottomNavItem().renderBottomNavItems()
            .forEachIndexed { _, bottomNavItem ->
                NavigationBarItem(
                    selected = bottomNavItem.routeName == currentDestination?.route,
                    label = {
                        Text(
                            text = bottomNavItem.tabName,
                            fontFamily = pretendard
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = bottomNavItem.tabName,
                        )
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.routeName) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = mainColor,
                        selectedTextColor = mainColor,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = bottomNavItemColor,
                        unselectedTextColor = bottomNavLabelColor,
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                    )
                )
            }
    }
}