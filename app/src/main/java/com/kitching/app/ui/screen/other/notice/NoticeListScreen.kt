package com.kitching.app.ui.screen.other.notice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kitching.app.navgraph.NoticeItemForScreen
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.NoticeItem
import com.kitching.app.ui.model.NoticeViewModel
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult

/**
 * Notice list screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param viewModel
 */
@Composable
fun NoticeListScreen(
    commonState: CommonState,
    goToCreateNotice: () -> Unit,
    goToNoticeDetail: (notice: NoticeItemForScreen) -> Unit,
    viewModel: NoticeViewModel = viewModel(factory = viewModelFactory)
) {

    var teamId by remember { mutableStateOf("") }
    val noticeListState by viewModel.notices.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getNotices(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { goToCreateNotice() }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = noticeListState is AppResult.Loading,
                successCondition = noticeListState is AppResult.Success,
                failCondition = noticeListState is AppResult.Failure,
                onRetryBtnClick = {}
            ) {
                val notices = (noticeListState as AppResult.Success).data
                if (notices.isEmpty()) {
                    EmptyScreen(
                        message = "공지사항을 입력해주세요."
                    )
                } else {
                    LazyColumn {
                        notices.forEach { notice ->
                            item(key = notice.noticeId) {
                                NoticeItem(notice = notice) {
                                    goToNoticeDetail(
                                        NoticeItemForScreen(
                                            noticeId = notice.noticeId,
                                            writerName = notice.writerName,
                                            date = notice.date,
                                            title = notice.title,
                                            content = notice.content
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}