package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.AsyncImage
import com.kitching.app.navgraph.BottomNavItem.Companion.renderBottomNavItems
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun CustomNavigationBar(
    navController: NavController,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

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
                    selected = currentDestination?.split("_")
                        ?.get(0) == bottomNavItem.destination.route.split("_")[0],
                    label = {
                        Text(
                            text = stringResource(bottomNavItem.tabName),
                        )
                    },
                    icon = {
                        AsyncImage(
                            modifier = Modifier.size(24.dp),
                            model = bottomNavItem.icon,
                            contentDescription = null,
                            colorFilter = if (currentDestination?.split("_")
                                    ?.get(0) == bottomNavItem.destination.route.split("_")[0]
                            ) ColorFilter.tint(
                                PrimaryGreen300
                            ) else ColorFilter.tint(NeutralGray200)
                        )
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.destination.route)
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