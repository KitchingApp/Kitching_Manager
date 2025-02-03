package com.kitching.app.ui.screen.other

import android.util.Log
import com.kitching.app.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo.DRAWER
import com.kitching.app.common.menuItems
import com.kitching.app.ui.item.OtherButtonItem
import com.kitching.app.ui.item.ProfileSection
import com.kitching.app.ui.theme.KitchingManagerTheme
import kotlinx.coroutines.launch

@Composable
fun OtherTabScreen(commonState: CommonState) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL
    )
    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                ProfileSection(
                    imageRes = R.drawable.minsu_profile,
                    name = "박민수",
                    role = "관리자"
                )

                Spacer(modifier = Modifier.height(32.dp))

                menuItems.forEach { label ->
                    OtherButtonItem(label = label, onClick = {
                        Log.d("OtherTabScreen", "$label 클릭됨")
                    })
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}