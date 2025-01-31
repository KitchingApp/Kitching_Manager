package com.kitching.app.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kitching.app.common.CommonState
import com.kitching.app.common.TopAppBarState
import com.kitching.app.ui.screen.navigation.CustomNavHost
import com.kitching.app.ui.screen.navigation.CustomNavigationBar
import com.kitching.app.ui.screen.navigation.CustomNavigationDrawer
import com.kitching.app.ui.screen.navigation.CustomTopAppBar

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun EntryPointScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title by remember { mutableStateOf("Kitching") } // 레스토랑 이름
    val topAppBarState = remember { mutableStateOf(TopAppBarState(drawerState = drawerState, title = title)) }
    val commonState by remember { mutableStateOf(
        CommonState(navController = navController, topAppBarState = topAppBarState, scope = scope)
    ) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    CustomNavigationDrawer(drawerState) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CustomTopAppBar(
                topAppBarState = topAppBarState.value
            ) },
            bottomBar = { CustomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            ) }
        ) { paddingValues -> CustomNavHost(
            paddingValues = paddingValues,
            commonState = commonState
            ) }
    }
}