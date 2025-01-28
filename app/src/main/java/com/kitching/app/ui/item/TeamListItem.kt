package com.kitching.app.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.subColor3
import com.kitching.app.R
import com.kitching.app.ui.theme.mainColor
import com.kitching.app.ui.theme.whiteColor

@Preview
@Composable
fun TeamListItem(
    teamName: String = "팀 이름"
) {
    Box(
        modifier = Modifier
            .size(width = 344.dp, height = 60.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(width = 2.dp, color = subColor3, shape = RoundedCornerShape(8.dp))
            .background(color = whiteColor, shape = RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp,18.dp, 10.dp, 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.fluent_spatula_spoon_24_filled),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = mainColor
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = teamName,
            )
        }
    }
}