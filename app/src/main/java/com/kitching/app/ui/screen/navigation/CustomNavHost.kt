package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.app.common.CommonState
import com.kitching.app.navgraph.OrderTab
import com.kitching.app.navgraph.OtherTab
import com.kitching.app.navgraph.PrepTab
import com.kitching.app.navgraph.RecipeTab
import com.kitching.app.navgraph.ScheduleTab
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.navgraph.sliceNavGraph
import com.kitching.app.ui.screen.login.CreateTeamScreen
import com.kitching.app.ui.screen.order.OrderTabScreen
import com.kitching.app.ui.screen.other.OtherTabScreen
import com.kitching.app.ui.screen.prep.PrepTabScreen
import com.kitching.app.ui.screen.recipe.RecipeTabScreen
import com.kitching.app.ui.screen.schedule.ScheduleTabScreen


@Composable
fun CustomNavHost(
    paddingValues: PaddingValues,
    commonState: CommonState
) {
    NavHost(
        navController = commonState.navController,
        startDestination = ScheduleTab,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(ScreenRouteDef.CreateTeamScreen.routeName) {
            CreateTeamScreen(
                coroutineScope = commonState.coroutineScope,
                onNavigateBack = {
                    commonState.navController.popBackStack()
                },
                onTeamCreated = {

                    commonState.navController.popBackStack(ScreenRouteDef.ScheduleTab.routeName, false)
                }
            )
        }
        composable<ScheduleTab> { ScheduleTabScreen(commonState = commonState) }
        composable<PrepTab> { PrepTabScreen(commonState = commonState) }
        composable<RecipeTab> { RecipeTabScreen(commonState = commonState) }
        composable<OrderTab> { OrderTabScreen(commonState = commonState) }
        composable<OtherTab> { OtherTabScreen(commonState = commonState) }
        sliceNavGraph(commonState = commonState)
    }
}