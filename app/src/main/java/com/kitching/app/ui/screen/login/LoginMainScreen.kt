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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun LoginMainScreen() {
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
            onClick = { /* TODO: 버튼 클릭 동작 */ },
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