package com.kitching.app.ui.screen.navigation

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kitching.app.MainActivity
import com.kitching.app.common.navIfNew
import com.kitching.app.navgraph.LoginRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.screen.login.CreateTeamScreen
import com.kitching.app.ui.screen.login.LoginMainScreen
import com.kitching.app.ui.screen.login.SelectTeamScreen
import com.kitching.app.ui.screen.splash.SplashScreen

@Preview(showBackground = true)
@Composable
fun LoginNavHost() {
    val navController = rememberNavController()
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = LoginRouteDef.Splash
    ) {
        composable<LoginRouteDef.Splash> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navIfNew(LoginRouteDef.Login) {
                        popUpTo(LoginRouteDef.Splash) { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    val context = navController.context
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                },
                viewModel = viewModel
            )
        }

        composable<LoginRouteDef.Login> {
            LoginMainScreen(
                viewModel = viewModel,
                coroutineScope = coroutineScope,
                onNavigateToSelectTeam = {
                    navController.navIfNew(LoginRouteDef.SelectTeam) {
                        popUpTo(LoginRouteDef.Login) { inclusive = true }
                    }
                }
            )
        }

        composable<LoginRouteDef.SelectTeam> {
            SelectTeamScreen(
                viewModel = viewModel,
                coroutineScope = coroutineScope,
                onNavigateToCreateTeam = {
                    navController.navIfNew(LoginRouteDef.CreateTeam)
                },
                onNavigateToMain = {
                    val context = navController.context
                    context.startActivity(Intent(context, MainActivity::class.java))
                    (context as? Activity)?.finish()
                }
            )
        }

        composable<LoginRouteDef.CreateTeam> {
            CreateTeamScreen(
                viewModel = viewModel,
                coroutineScope = coroutineScope,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onTeamCreated = {
                    navController.popBackStack(LoginRouteDef.SelectTeam, false)
                }
            )
        }
    }
}