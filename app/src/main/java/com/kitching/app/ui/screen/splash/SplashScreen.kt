package com.kitching.app.ui.screen.splash

import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.screen.EntryPointScreen
import com.kitching.app.ui.screen.login.LoginMainScreen
import com.kitching.app.ui.screen.login.SelectTeamScreen
import com.kitching.app.ui.theme.KitchingManagerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val coroutineScope = rememberCoroutineScope()
    var destination by remember { mutableStateOf<String?>("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(modifier = Modifier
            .padding(top = 360.dp)
            .sizeIn(150.dp, 80.dp)
            .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.kitching_splash),
            contentDescription = "splashImg"
        )
    }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            delay(2000)

            val userId = viewModel.dataStore.getUserId()
            val teamId = viewModel.dataStore.getTeamId()

            destination = when {
                userId.isNullOrEmpty() && teamId.isNullOrEmpty() -> "login_screen"
                teamId.isNullOrEmpty() -> "select_team_screen"
                else -> "entry_screen"
            }
        }
    }

    when(destination) {
        "login_screen" -> KitchingManagerTheme { LoginMainScreen(viewModel, coroutineScope) }
        "select_team_screen" -> KitchingManagerTheme { SelectTeamScreen(viewModel, coroutineScope) }
        else -> KitchingManagerTheme { EntryPointScreen() }

    }
}