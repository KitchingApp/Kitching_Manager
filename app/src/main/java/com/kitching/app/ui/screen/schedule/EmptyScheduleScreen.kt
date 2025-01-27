package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.R

@Preview
@Composable
fun EmptyScheduleScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(0.dp, 0.dp, 0.dp, 33.dp),
            imageVector = ImageVector.vectorResource(R.drawable.img_group),
            contentDescription = "empty schedule icon",
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "근무인원이 없습니다",
            color = Color.LightGray,
            fontSize = 20.dp.value.sp,
            textAlign = TextAlign.Center
        )
    }
}