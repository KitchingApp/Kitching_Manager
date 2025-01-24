package com.kitching.app.ui.screen.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.kitching.app.R
import com.kitching.app.ui.theme.mainColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    drawerState: DrawerState,
    scope: CoroutineScope,
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainColor
        ),
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_menu),
                    contentDescription = "navigation icon",
                    tint = Color.White
                )
            }
        },
        actions = {
//            IconButton(onClick = {}) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(R.drawable.icon_add),
//                    tint = Color.White,
//                    contentDescription = "add button",
//                )
//            }
        }
    )
}