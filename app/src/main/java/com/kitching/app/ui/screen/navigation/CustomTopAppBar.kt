package com.kitching.app.ui.screen.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.TopAppBarState
import com.kitching.app.ui.theme.PrimaryGreen300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    topAppBarState: TopAppBarState
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = topAppBarState.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrimaryGreen300
        ),
        navigationIcon = {
            IconButton(onClick = topAppBarState.onClickNavIcon) {
                Icon(
                    imageVector = ImageVector.vectorResource(topAppBarState.navIconInfo.icon),
                    contentDescription = topAppBarState.navIconInfo.description,
                    tint = Color.White
                )
            }
        },
        actions = {
            if(topAppBarState.actionIconInfo !== ActionIconInfo.NULL) {
                IconButton(onClick = topAppBarState.onClickActionIcon) {
                    Icon(
                        imageVector = ImageVector.vectorResource(topAppBarState.actionIconInfo.icon),
                        contentDescription = topAppBarState.actionIconInfo.description,
                        tint = Color.White
                    )
                }
            }

        }
    )
}