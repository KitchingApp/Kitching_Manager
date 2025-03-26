package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.app.common.CommonState
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.navgraph.orderSliceNavGraph
import com.kitching.app.navgraph.otherSliceNavGraph
import com.kitching.app.navgraph.prepSliceNavGraph
import com.kitching.app.navgraph.recipeSliceNavGraph
import com.kitching.app.navgraph.scheduleSliceNavGraph
import com.kitching.app.ui.screen.login.CreateTeamScreen


@Composable
fun CustomNavHost(
    paddingValues: PaddingValues,
    commonState: CommonState,
    navController: NavController
) {
    NavHost(
        navController = commonState.navController,
        startDestination = ScreenRouteDef.ScheduleGraph.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = ScreenRouteDef.CreateTeam.route) {
            CreateTeamScreen(
                coroutineScope = commonState.coroutineScope,
                onNavigateBack = {
                    commonState.navController.popBackStack()
                },
                onTeamCreated = {
                    commonState.navController.popBackStack(
                        ScreenRouteDef.ScheduleGraph.route,
                        false
                    )
                }
            )
        }
        scheduleSliceNavGraph(commonState = commonState, navController = navController)
        prepSliceNavGraph(
            commonState = commonState,
            navController = navController
        )
        recipeSliceNavGraph(
            commonState = commonState,
            navController = navController
        )
        orderSliceNavGraph(
            commonState = commonState,
            navController = navController
        )
        otherSliceNavGraph(commonState = commonState, navController = navController)
    }
}