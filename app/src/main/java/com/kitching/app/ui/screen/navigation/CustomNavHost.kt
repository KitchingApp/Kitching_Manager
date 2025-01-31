package com.kitching.app.ui.screen.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.app.common.CommonState
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.navgraph.sliceNavGraph
import com.kitching.app.ui.screen.order.OrderTabScreen
import com.kitching.app.ui.screen.other.OtherTabScreen
import com.kitching.app.ui.screen.prep.PrepTabScreen
import com.kitching.app.ui.screen.recipe.RecipeTabScreen
import com.kitching.app.ui.screen.schedule.ScheduleTabScreen

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun CustomNavHost(
    paddingValues: PaddingValues,
    commonState: CommonState
) {
    NavHost(
        navController = commonState.navController,
        startDestination = ScreenRouteDef.ScheduleTab.routeName,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(ScreenRouteDef.ScheduleTab.routeName) {
            ScheduleTabScreen(commonState = commonState)
        }
        composable(ScreenRouteDef.PrepTab.routeName) {
            PrepTabScreen(commonState = commonState)
        }
        composable(ScreenRouteDef.RecipeTab.routeName) {
            RecipeTabScreen(commonState = commonState)
        }
        composable(ScreenRouteDef.OrderTab.routeName) {
            OrderTabScreen(commonState = commonState)
        }
        composable(ScreenRouteDef.OtherTab.routeName) {
            OtherTabScreen(commonState = commonState)
        }
        sliceNavGraph(commonState = commonState)
    }
}