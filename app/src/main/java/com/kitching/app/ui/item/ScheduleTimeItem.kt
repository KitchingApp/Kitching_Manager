package com.kitching.app.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray600
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.util.customFormat
import com.kitching.app.util.dropShadow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ScheduleTimeItem(
    scheduleTimeName: String,
    startTime: String,
    endTime: String,
    onOptionBtnClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
            .dropShadow(
                shape = RoundedCornerShape(8.dp),
                color = ShadowColor,
                blur = 2.dp,
                offsetX = 0.dp,
                offsetY = 2.dp,
            ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = NeutralGray0
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = scheduleTimeName,
                    style = H3_m.copy(NeutralGray800),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "$startTime - $endTime",
                    style = Body1,
                    textAlign = TextAlign.Center
                )
            }
            IconButton(
                modifier = Modifier
                    .width(24.dp)
                    .align(Alignment.CenterVertically),
                onClick = { onOptionBtnClick() }
            ) {
                AsyncImage(
                    modifier = Modifier.size(24.dp),
                    model = R.drawable.icon_kebab_menu,
                    contentDescription = "option Menu",
                    colorFilter = ColorFilter.tint(NeutralGray600)
                )
            }
        }
    }
}