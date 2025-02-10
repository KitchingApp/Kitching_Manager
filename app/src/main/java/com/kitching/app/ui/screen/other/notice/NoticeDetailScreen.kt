package com.kitching.app.ui.screen.other.notice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.NoticeViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray100
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Notice detail screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param notice
 * @param viewModel
 */
@Composable
fun NoticeDetailScreen(
    commonState: CommonState,
    notice: Notice,
    viewModel: NoticeViewModel = viewModel(factory = viewModelFactory)
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    var teamId by remember { mutableStateOf("") }
    val noticeResultState by viewModel.noticeResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = noticeResultState is AppResult.Loading,
                successCondition = noticeResultState is AppResult.Success,
                failCondition = noticeResultState is AppResult.Failure,
                failContent = {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = 20.dp),
                                text = notice.title,
                                style = H2.copy(color = NeutralGray800)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(7.dp)
                            ) {
                                Text(
                                    style = Body1_m.copy(color = NeutralGray800),
                                    text = notice.writerName
                                )
                                Text(
                                    style = H5.copy(color = NeutralGray800),
                                    text = "|"
                                )
                                Text(
                                    style = Body1_m.copy(color = NeutralGray800),
                                    text = notice.date
                                )
                            }
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = notice.content,
                                style = Caption1_R.copy(color = NeutralGray800)
                            )
                            Spacer(Modifier.weight(1f))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(19.dp)
                            ) {
                                TextButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    onClick = {
                                        commonState.navController.navigate(
                                            ScreenRouteDef.InnerContent.NoticeCreateOrUpdate.routeName
                                                    + "/${Json.encodeToString(notice)}"
                                        )
                                    },
                                    colors = ButtonColors(
                                        containerColor = PrimaryGreen300,
                                        contentColor = NeutralGray0,
                                        disabledContainerColor = PrimaryGreen300,
                                        disabledContentColor = NeutralGray0
                                    )
                                ) {
                                    Text(
                                        text = "수정",
                                        style = H5.copy(color = NeutralGray0)
                                    )
                                }
                                TextButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    onClick = {
                                        showDeleteDialog = true
                                    },
                                    colors = ButtonColors(
                                        containerColor = NeutralGray100,
                                        contentColor = NeutralGray0,
                                        disabledContainerColor = PrimaryGreen300,
                                        disabledContentColor = NeutralGray0
                                    )
                                ) {
                                    Text(
                                        text = "삭제",
                                        style = H5_m.copy(color = NeutralGray800)
                                    )
                                }
                            }
                        }
                        if (showDeleteDialog) {
                            BasicConfirmDialog(
                                message = "공지사항을 삭제하시겠습니까?",
                                confirmText = "삭제",
                                onClickConfirm = {
                                    viewModel.deleteNotice(notice.noticeId)
                                    if (noticeResultState is AppResult.Success) {
                                        viewModel.getNotices(teamId)
                                        commonState.navController.navigate(ScreenRouteDef.InnerContent.NoticeList)
                                    }
                                    showDeleteDialog = false
                                },
                                cancelText = "취소",
                                onClickCancel = { showDeleteDialog = false }
                            )
                        }
                    }
                }
            }
        }
    }
}