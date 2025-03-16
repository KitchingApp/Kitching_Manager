package com.kitching.app.ui.screen.login

import android.annotation.SuppressLint
import android.app.Activity
import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil3.compose.AsyncImage
import com.kitching.app.ui.screen.common.ColumnSpacer
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.ui.theme.loginButtonHeight
import com.kitching.app.ui.theme.loginButtonWidth
import com.kitching.app.ui.theme.splashLogoSizeHeight
import com.kitching.app.ui.theme.splashLogoSizeWidth
import com.kitching.domain.AppResult

@SuppressLint("ContextCastToActivity")
@Composable
fun LoginMainScreen(
    viewModel: LoginViewModel,
    coroutineScope: CoroutineScope,
    onNavigateToSelectTeam: () -> Unit
) {
    val loginState by viewModel.loginState.collectAsState()
    val context = LocalContext.current as Activity

    LaunchedEffect(loginState) {
        if (loginState is AppResult.Success) {
            coroutineScope.launch {
                val userId = viewModel.dataStore.getUserId().toString()
                viewModel.dataStore.saveUserId(userId)
                onNavigateToSelectTeam()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = splashLogoSizeWidth, height = splashLogoSizeHeight),
            model = R.drawable.kitching_splash,
            contentDescription = null,
        )

        ColumnSpacer(defaultPadding)

        Text(
            text = stringResource(id = R.string.login_info_text1) + "\n" + stringResource(id = R.string.login_info_text2),
            textAlign = TextAlign.Center,
            style = H3_m
        )

        ColumnSpacer(defaultPadding)


        AsyncImage(
            modifier = Modifier
                .width(loginButtonWidth)
                .height(loginButtonHeight)
                .clickable {
                    coroutineScope.launch {
                        viewModel.performKakaoLogin(context)
                    }
                },
            model = R.drawable.kakao_login_img,
            contentDescription = "Login with Kakao",
        )
    }
}