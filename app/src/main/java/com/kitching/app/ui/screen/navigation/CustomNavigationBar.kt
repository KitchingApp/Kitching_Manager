package com.kitching.app.ui.screen.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.kitching.app.navgraph.BottomNavItem.Companion.renderBottomNavItems
import com.kitching.app.navgraph.BottomNavigationItem
import com.kitching.app.navgraph.ScheduleTab
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun CustomNavigationBar(
    navController: NavController,
) {
    var selectedTab by remember { mutableStateOf<BottomNavigationItem>(ScheduleTab) }

    NavigationBar(
        modifier = Modifier.drawBehind {
            drawLine(
                color = NeutralGray400,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
            )
        },
        containerColor = Color.White,
        contentColor = NeutralGray200,
    ) {
        renderBottomNavItems()
            .forEach { bottomNavItem ->
                NavigationBarItem(
                    selected = selectedTab == bottomNavItem.destination,
                    label = {
                        Text(
                            text = bottomNavItem.tabName,
                        )
                    },
                    icon = {
                        Icon(
                            bottomNavItem.icon,
                            contentDescription = bottomNavItem.tabName,
                        )
                    },
                    onClick = {
                        selectedTab = bottomNavItem.destination
                        navController.navigate(bottomNavItem.destination)
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = PrimaryGreen300,
                        selectedTextColor = PrimaryGreen300,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = NeutralGray200,
                        unselectedTextColor = NeutralGray400,
                        disabledIconColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                    )
                )
            }
    }
}