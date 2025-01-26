package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.R
import com.kitching.app.navgraph.ScheduleTabItem
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.subTextColor

@Preview
@Composable
fun ScheduleTabScreen() {
    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                    ) {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.icon_left_triangle),
                                contentDescription = "prev date button",
                                tint = subTextColor
                            )
                        }
                        Text(
                            modifier = Modifier.padding(27.dp, 0.dp).align(Alignment.CenterVertically),
                            text = "2025-01-24",
                            color = subTextColor,
                            fontSize = 16.sp
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.icon_right_triangle),
                                contentDescription = "next date button",
                                tint = subTextColor
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    ScheduleTabItem().renderTabItems()
                }
            }
        }
    }
}