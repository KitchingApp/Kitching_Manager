package com.kitching.app.ui.item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.domain.entities.Schedule

//@Preview
@Composable
fun AppliedScheduleItemUI(
    schedule: Schedule,
    onApplyClick: (scheduleId: String) -> Unit,
    onRejectClick: (schedule: Schedule) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawBehind {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(48.dp, 0.dp, 0.dp, 0.dp)
                .weight(1f),
            textAlign = TextAlign.Left,
            text = schedule.userName
        )
        Text(
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            text = schedule.scheduleTimeName
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 48.dp, 0.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .width(28.dp)
                    .height(28.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen300,
                    contentColor = NeutralGray0,
                    disabledContainerColor = NeutralGray300,
                    disabledContentColor = NeutralGray0
                ),
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onApplyClick(schedule.scheduleId)
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                    contentDescription = "apply icon",
                    tint = NeutralGray0,
                )
            }
            Box(modifier = Modifier.padding(16.dp, 0.dp, 0.dp, 0.dp)) {
                Button(
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .border(
                            width = 1.dp,
                            color = NeutralGray300,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(0.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NeutralGray0,
                        contentColor = NeutralGray300,
                        disabledContainerColor = NeutralGray300,
                        disabledContentColor = NeutralGray0
                    ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        onRejectClick(schedule)
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_clear),
                        contentDescription = "cancel icon",
                        tint = NeutralGray300,
                    )
                }
            }
        }
    }
}