package com.kitching.app.ui.screen.login

import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.ui.model.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kitching.app.ui.screen.splash.SplashScreen
import com.kitching.domain.AppResult

@Composable
fun LoginMainScreen(
    viewModel: LoginViewModel,
    coroutineScope: CoroutineScope
    ) {
    val loginState by viewModel.loginState.collectAsState()
    var destination by remember { mutableStateOf<String?>("") }

    LaunchedEffect(loginState) {
        if (loginState is AppResult.Success) {
            coroutineScope.launch {
                val userId = viewModel.dataStore.getUserId().toString()
                viewModel.dataStore.saveUserId(userId)
                destination = "select_team_screen"
            }
        } else {
            destination = "splash_screen"
        }
    }

    when(destination) {
        "select_team_screen" -> SelectTeamScreen(viewModel, coroutineScope)
        else -> SplashScreen()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 283.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.kitching_splash),
            contentDescription = "Logo",
            modifier = Modifier
                .size(width = 150.dp, height = 80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(id = R.string.login_info_text1),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFF565656)
            )
        )

        Text(
            text = stringResource(id = R.string.login_info_text2),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color(0xFF565656)
            )
        )

        Spacer(modifier = Modifier.height(200.dp))

        Surface(
            onClick = {
                coroutineScope.launch {
                    viewModel.performKakaoLogin()
                }
            },
            shape = RoundedCornerShape(6.dp),
            color = Color.Transparent, // 배경색 제거
            modifier = Modifier
                .size(width = 320.dp, height = 48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kakao_login_img),
                contentDescription = "Login with Kakao",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}