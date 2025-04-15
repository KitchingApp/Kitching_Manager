package com.kitching.app.ui.screen.other

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.InviteCodeViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.SecondaryLightGreen500
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.app.util.dropShadow
import com.kitching.domain.AppResult

@Composable
fun InviteCodeScreen(
    commonState: CommonState,
    navigateToOther: () -> Unit,
    viewModel: InviteCodeViewModel = viewModel(factory = viewModelFactory)
) {
    var teamId by remember { mutableStateOf("") }
    val teamState by viewModel.team.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getTeam(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "초대코드",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { navigateToOther() },
        actionIconInfo = ActionIconInfo.NULL
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = teamState is AppResult.Loading,
                successCondition = teamState is AppResult.Success,
                failCondition = teamState is AppResult.Failure,
                onRetryBtnClick = {}
            ) {
                val team = (teamState as AppResult.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = defaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.size(86.dp),
                        model = R.drawable.icon_invite,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(SecondaryLightGreen500)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 70.dp),
                        text = "초대코드를 통해 직원을 관리할 수 있어요!",
                        style = H3_m.copy(NeutralGray800)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                            .background(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Transparent
                            )
                            .border(
                                width = 1.dp,
                                color = PrimaryGreen300,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(10.dp),
                            text = team.inviteCode,
                            style = Caption1_m.copy(color = NeutralGray800)
                        )
                        IconButton(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = {
                                val clipboard = KitchingApplication.getInstance().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val inviteCodeClip = ClipData.newPlainText("초대코드", team.inviteCode)
                                clipboard.setPrimaryClip(inviteCodeClip)
                            }
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .width(14.dp)
                                    .height(25.dp),
                                model = R.drawable.icon_copy,
                                contentDescription = "초대코드 복사"
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .width(200.dp)
                            .height(49.dp)
                            .dropShadow(
                                shape = RoundedCornerShape(8.dp),
                                offsetX = 0.dp,
                                offsetY = 2.dp,
                                blur = 8.dp,
                                spread = 0.dp,
                                color = ShadowColor
                            )
                            .background(
                                color = NeutralGray0,
                                shape = RoundedCornerShape(8.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier.size(29.dp),
                            painter = painterResource(R.drawable.icon_kakao_share),
                            contentDescription = "kakao share icon"
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "카카오톡으로 공유",
                            style = H5_m.copy(NeutralGray800)
                        )
                    }
                }
            }
        }
    }
}