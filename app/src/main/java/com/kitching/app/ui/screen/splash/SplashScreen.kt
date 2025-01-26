package com.kitching.app.ui.screen.splash

import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SplashScreen() {
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
            contentDescription = null
        )
    }
}