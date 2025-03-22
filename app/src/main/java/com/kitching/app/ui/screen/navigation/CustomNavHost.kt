package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kitching.app.common.CommonState
import com.kitching.app.navgraph.CreateTeamScreen
import com.kitching.app.navgraph.ScheduleTab
import com.kitching.app.navgraph.orderSliceNavGraph
import com.kitching.app.navgraph.otherSliceNavGraph
import com.kitching.app.navgraph.prepSliceNavGraph
import com.kitching.app.navgraph.recipeSliceNavGraph
import com.kitching.app.ui.screen.login.CreateTeamScreen
import com.kitching.app.ui.screen.schedule.ScheduleTabScreen


@Composable
fun CustomNavHost(
    paddingValues: PaddingValues,
    commonState: CommonState,
    navController: NavController
) {
    NavHost(
        navController = commonState.navController,
        startDestination = ScheduleTab,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable<CreateTeamScreen> {
            CreateTeamScreen(
                coroutineScope = commonState.coroutineScope,
                onNavigateBack = {
                    commonState.navController.popBackStack()
                },
                onTeamCreated = {
                    commonState.navController.popBackStack(
                        ScheduleTab,
                        false
                    )
                }
            )
        }
        composable<ScheduleTab> { ScheduleTabScreen(commonState = commonState) }
        prepSliceNavGraph(commonState = commonState, navController = navController)
        recipeSliceNavGraph(commonState = commonState, navController = navController)
        orderSliceNavGraph(commonState = commonState, navController = navController)
        otherSliceNavGraph(commonState = commonState, navController = navController)
    }
}