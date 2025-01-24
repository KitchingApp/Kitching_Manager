package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.navgraph.sliceNavGraph
import com.kitching.app.ui.screen.bottomtab.OtherTabScreen
import com.kitching.app.ui.screen.bottomtab.OrderTabScreen
import com.kitching.app.ui.screen.bottomtab.PrepTabScreen
import com.kitching.app.ui.screen.bottomtab.RecipeTabScreen
import com.kitching.app.ui.screen.bottomtab.ScheduleTabScreen

@Composable
fun CustomNavHost(
    paddingValues: PaddingValues,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRouteDef.ScheduleTab.routeName,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(ScreenRouteDef.ScheduleTab.routeName) {
            ScheduleTabScreen(
                navController
            )
        }
        composable(ScreenRouteDef.PrepTab.routeName) {
            PrepTabScreen(
                navController
            )
        }
        composable(ScreenRouteDef.RecipeTab.routeName) {
            RecipeTabScreen(
                navController
            )
        }
        composable(ScreenRouteDef.OrderTab.routeName) {
            OrderTabScreen(
                navController
            )
        }
        composable(ScreenRouteDef.OtherTab.routeName) {
            OtherTabScreen(
                navController
            )
        }
        sliceNavGraph(navController)
    }
}