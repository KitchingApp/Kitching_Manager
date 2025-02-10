package com.kitching.app.ui.screen.splash

import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.model.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: LoginViewModel
) {
    LaunchedEffect(Unit) {
        delay(1000)
        val userId = viewModel.dataStore.getUserId()
        if (userId.isNullOrEmpty()) {
            onNavigateToLogin()
        } else {
            onNavigateToMain()
        }
    }

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
}