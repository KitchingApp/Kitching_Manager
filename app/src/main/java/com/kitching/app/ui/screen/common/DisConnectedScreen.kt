package com.kitching.app.ui.screen.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.H4
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun DisConnectedScreen(
    onRetryBtnClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(56.dp),
                model = R.drawable.icon_disconnect,
                contentDescription = null
            )
            Text(
                text = "네트워크에 접속할 수 없습니다.",
                style = H4,
                textAlign = TextAlign.Center,
                color = NeutralGray800
            )
            Text(
                text = "네트워크 연결상태를 확인하거나 아래 버튼 클릭 후 \n" +
                        "다시 접속을 시도해주시기 바랍니다.",
                style = Caption1_m,
                textAlign = TextAlign.Center,
                color = NeutralGray500
            )
            TextButton(
                modifier = Modifier.width(100.dp).height(40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.textButtonColors().copy(
                    containerColor = PrimaryGreen300,
                    contentColor = NeutralGray0
                ),
                onClick = onRetryBtnClick
            ) {
                Text(
                    text = "재시도",
                    style = Body1
                )
            }
        }
    }
}