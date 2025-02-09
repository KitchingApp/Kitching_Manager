package com.kitching.app.ui.screen.other.memberlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.H2_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800

@Preview
@Composable
fun CardPreview() {
    Box(
        modifier = Modifier
            .width(320.dp)
            .height(340.dp)
            .background(color = NeutralGray300, shape = RoundedCornerShape(20.dp)),
    ) {
        Image(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            painter = painterResource(R.drawable.pancake),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "김채연",
                style = H2_m.copy(color = NeutralGray800)
            )
        }
    }
}