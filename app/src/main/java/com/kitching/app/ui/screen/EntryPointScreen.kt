package com.kitching.app.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kitching.app.common.CommonState
import com.kitching.app.common.TopAppBarState
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.TeamViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.navigation.CustomNavHost
import com.kitching.app.ui.screen.navigation.CustomNavigationBar
import com.kitching.app.ui.screen.navigation.CustomNavigationDrawer
import com.kitching.app.ui.screen.navigation.CustomTopAppBar
import com.kitching.domain.AppResult
import kotlinx.coroutines.launch

@Composable
fun EntryPointScreen(
    destination: String,
    teamViewModel: TeamViewModel = viewModel(factory = viewModelFactory),
) {
    var userId by remember { mutableStateOf("") }
    var selectedTeamId by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("Approach") } // 레스토랑 이름

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val topAppBarState =
        remember { mutableStateOf(TopAppBarState(drawerState = drawerState, title = title)) }
    val commonState by remember {
        mutableStateOf(
            CommonState(
                topAppBarState = topAppBarState,
                coroutineScope = coroutineScope
            )
        )
    }
    val teamListState by teamViewModel.teamList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userId = teamViewModel.dataStore.getUserId()
        selectedTeamId = teamViewModel.dataStore.getTeamId()
        title = teamViewModel.dataStore.getTeamName()

        teamViewModel.getTeam(selectedTeamId)
        teamViewModel.getTeamList(userId)
    }

    ResultConditionScreen(
        loadingCondition = teamListState is AppResult.Loading,
        successCondition = teamListState is AppResult.Success,
        failCondition = teamListState is AppResult.Success,
        onRetryBtnClick = {
            coroutineScope.launch {
                teamViewModel.getTeamList(teamViewModel.dataStore.getUserId())
            }
        }
    ) {
        CustomNavigationDrawer(
            drawerState = drawerState,
            teamListState = teamListState,
            onTeamCreateClick = {
                navController.navigate(ScreenRouteDef.CreateTeam)
            },
            onTeamItemClick = { team ->
                selectedTeamId = team.teamId
                title = team.teamName
                navController.popBackStack(ScreenRouteDef.ScheduleGraph, false)
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CustomTopAppBar(
                        topAppBarState = topAppBarState.value
                    )
                },
                bottomBar = {
                    CustomNavigationBar(
                        navController = navController
                    )
                }
            ) { paddingValues ->
                CustomNavHost(
                    paddingValues = paddingValues,
                    commonState = commonState,
                    navController = navController,
                    destination = destination
                )
            }
        }
    }
}