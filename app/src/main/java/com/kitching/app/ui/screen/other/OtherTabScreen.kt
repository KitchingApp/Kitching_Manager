package com.kitching.app.ui.screen.other

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.LoginActivity
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo.DRAWER
import com.kitching.app.navgraph.OtherMenuItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.OtherButtonItem
import com.kitching.app.ui.item.ProfileSection
import com.kitching.app.ui.model.UserViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import kotlinx.coroutines.launch

@Composable
fun OtherTabScreen(
    commonState: CommonState,
    userViewModel: UserViewModel = viewModel(factory = viewModelFactory)
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        navIconInfo = DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.NULL
    )

    var userId by remember { mutableStateOf("") }
    val userState = userViewModel.user.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userId = PreferencesDataStore(commonState.navController.context).getUserId()
        userViewModel.getUser(userId)
    }

    ResultConditionScreen(
        loadingCondition = userState.value is AppResult.Loading,
        successCondition = userState.value is AppResult.Success,
        failCondition = userState.value is AppResult.Failure,
        onRetryBtnClick = {}
    ) {
        val userData = (userState.value as AppResult.Success).data
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
                        imageSource = userData.userImage,
                        name = userData.userName,
                        role = "관리자"
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        OtherMenuItem().renderOtherMenuItems().forEach { otherMenu ->
                            OtherButtonItem(label = otherMenu.tabName) {
                                commonState.navController.navigate(otherMenu.routeName)
                            }
                        }

                        OtherButtonItem(label = "로그아웃") {
                            logout(commonState)
                        }
                    }
                }
            }
        }
    }
}

private fun logout(commonState: CommonState) {
    commonState.coroutineScope.launch {
        val context = commonState.navController.context
        PreferencesDataStore(context).clearUserId()
        PreferencesDataStore(context).clearTeamId()

        context.startActivity(Intent(context, LoginActivity::class.java))
        (context as? Activity)?.finish()
    }
}