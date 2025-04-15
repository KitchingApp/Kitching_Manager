package com.kitching.app.ui.screen

import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.kitching.app.R
import com.kitching.app.common.CommonState
import com.kitching.app.common.TopAppBarState
import com.kitching.app.common.navIfNew
import com.kitching.app.navgraph.Route
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.TeamViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.navigation.CustomNavHost
import com.kitching.app.ui.screen.navigation.CustomNavigationBar
import com.kitching.app.ui.screen.navigation.CustomNavigationDrawer
import com.kitching.app.ui.screen.navigation.CustomTopAppBar
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.domain.AppResult
import kotlinx.coroutines.launch

@Composable
fun EntryPointScreen(
    destination: Route.BottomTab = Route.ScheduleGraph,
    teamViewModel: TeamViewModel = viewModel(factory = viewModelFactory),
) {
    val backPressedMessage = stringResource(id = R.string.back_pressed_message)
    var userId by remember { mutableStateOf("") }
    var selectedTeamId by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("Approach") } // 레스토랑 이름

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val topAppBarState =
        remember { mutableStateOf(TopAppBarState(drawerState = drawerState, title = title)) }
    val lastBackPressedTime = remember { mutableLongStateOf(0L) }
    val commonState by remember {
        mutableStateOf(
            CommonState(
                topAppBarState = topAppBarState,
                coroutineScope = coroutineScope,
                snackbarHostState = snackbarHostState
            )
        )
    }
    val teamListState by teamViewModel.teamList.collectAsStateWithLifecycle()

    val currentRoute by navController.currentBackStackEntryFlow
        .collectAsStateWithLifecycle(navController.currentDestination?.route)

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    LaunchedEffect(Unit) {
        userId = teamViewModel.dataStore.getUserId()
        selectedTeamId = teamViewModel.dataStore.getTeamId()
        title = teamViewModel.dataStore.getTeamName()

        teamViewModel.getTeam(selectedTeamId)
        teamViewModel.getTeamList(userId)
    }

    LaunchedEffect(currentRoute) {
        backPressedDispatcher?.addCallback {
            val currentTime = SystemClock.elapsedRealtime()
            if(drawerState.isOpen) {
                coroutineScope.launch {
                    drawerState.close()
                }
            } else {
                if (currentTime - lastBackPressedTime.longValue < 2000) {
                    (navController.context as? ComponentActivity)?.finish()
                } else {
                    if (navController.previousBackStackEntry == null) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(backPressedMessage)
                        }
                    } else {
                        navController.popBackStack()
                    }
                }
                lastBackPressedTime.longValue = currentTime
            }
        }
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
                navController.navIfNew(Route.CreateTeam)
            },
            onTeamItemClick = { team ->
                selectedTeamId = team.teamId
                title = team.teamName
                navController.popBackStack(Route.ScheduleGraph, false)
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
                },
                snackbarHost = { SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        Snackbar(
                            shape = RoundedCornerShape(8.dp),
                            snackbarData = it,
                            containerColor = NeutralGray0,
                            contentColor = NeutralGray800,
                            actionColor = PrimaryGreen300,
                            dismissActionContentColor = NeutralGray400
                        )
                    }
                ) }
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