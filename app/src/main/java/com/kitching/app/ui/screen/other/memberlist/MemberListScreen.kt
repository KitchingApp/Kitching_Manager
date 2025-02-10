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
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.domain.AppResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

//@Preview
@Composable
fun MemberListScreen(
    commonState: CommonState,
    viewModel: MemberViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    val members by viewModel.members.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "멤버관리",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )

    LaunchedEffect(Unit) {
        viewModel.getMembers(teamId)
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding)
            ) {
                when (members) {
                    is AppResult.Success -> {
                        val membersData = (members as AppResult.Success).data
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(defaultPadding)
                        ) {
                            items(membersData) { member ->
                                MemberCardItem(
                                    member = member,
                                    onCardClick = {
                                        val json = Json.encodeToString(member)
                                        val encodedJson = Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
                                        commonState.navController.navigate(
                                            ScreenRouteDef.InnerContent.MemberDetail.routeName + "/$encodedJson"
                                        )
                                    }
                                )
                            }
                        }
                    }

                    is AppResult.Failure -> {}
                    is AppResult.Initial -> {}
                    is AppResult.Loading -> {}
                }
            }
        }
    }
}