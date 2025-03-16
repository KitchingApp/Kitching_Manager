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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kitching.app.common.CommonState
import com.kitching.app.common.TopAppBarState
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.navigation.CustomNavHost
import com.kitching.app.ui.screen.navigation.CustomNavigationBar
import com.kitching.app.ui.screen.navigation.CustomNavigationDrawer
import com.kitching.app.ui.screen.navigation.CustomTopAppBar
import com.kitching.domain.AppResult
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun EntryPointScreen(
    viewModel: LoginViewModel = viewModel(factory = viewModelFactory),
) {
    var userId = ""
    val teamListState by viewModel.teamList.collectAsStateWithLifecycle()
    val selectedTeamId by remember { mutableStateOf("") }
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Kitching") } // 레스토랑 이름
    val topAppBarState =
        remember { mutableStateOf(TopAppBarState(drawerState = drawerState, title = title)) }
    val commonState by remember {
        mutableStateOf(
            CommonState(
                navController = navController,
                topAppBarState = topAppBarState,
                coroutineScope = coroutineScope
            )
        )
    }

    LaunchedEffect(Unit) {
        userId = viewModel.dataStore.getUserId()
        viewModel.getTeamList(userId)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    ResultConditionScreen(
        loadingCondition = teamListState is AppResult.Loading,
        successCondition = teamListState is AppResult.Success,
        failCondition = teamListState is AppResult.Success,
        onRetryBtnClick = {
            coroutineScope.launch {
                viewModel.getTeamList(viewModel.dataStore.getUserId())
            }
        }
    ) {
        CustomNavigationDrawer(
            drawerState = drawerState,
            teamListState = teamListState
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
                        navController = navController,
                        currentDestination = currentDestination
                    )
                }
            ) { paddingValues ->
                CustomNavHost(
                    paddingValues = paddingValues,
                    commonState = commonState
                )
            }
        }
    }
}