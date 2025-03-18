package com.kitching.app.ui.screen.other.memberlist

import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.MemberCardItem
import com.kitching.app.ui.model.MemberViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Member list screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param viewModel
 */
@Composable
fun MemberListScreen(
    commonState: CommonState,
    viewModel: MemberViewModel = viewModel(factory = viewModelFactory)
) {
    var teamId by remember { mutableStateOf("") }
    val membersState by viewModel.members.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getMembers(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "멤버관리",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding)
            ) {
                ResultConditionScreen(
                    loadingCondition = membersState is AppResult.Loading,
                    successCondition = membersState is AppResult.Success,
                    failCondition = membersState is AppResult.Failure,
                    onRetryBtnClick = {}
                ) {
                    val membersData = (membersState as AppResult.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(defaultPadding)
                    ) {
                        membersData.forEach { member ->
                            item(key = member.userId) {
                                MemberCardItem(
                                    member = member,
                                    onCardClick = {
                                        val json = Json.encodeToString(member)
                                        val encodedJson = Base64.encodeToString(
                                            json.toByteArray(),
                                            Base64.URL_SAFE or Base64.NO_WRAP
                                        )
                                        commonState.navController.navigate(
                                            ScreenRouteDef.InnerContent.MemberDetail.routeName + "/$encodedJson"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}