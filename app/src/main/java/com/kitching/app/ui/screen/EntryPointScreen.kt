package com.kitching.app.ui.screen

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
import com.kitching.app.ui.screen.navigation.CustomNavHost
import com.kitching.app.ui.screen.navigation.CustomNavigationBar
import com.kitching.app.ui.screen.navigation.CustomNavigationDrawer
import com.kitching.app.ui.screen.navigation.CustomTopAppBar

@Preview(showBackground = true)
@Composable
fun EntryPointScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val title = remember { mutableStateOf("Kitching") }

    CustomNavigationDrawer(drawerState) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { CustomTopAppBar(drawerState, scope, title.value) },
            bottomBar = { CustomNavigationBar(navController, currentDestination) }
        ) { paddingValues -> CustomNavHost(paddingValues, navController) }
    }
}