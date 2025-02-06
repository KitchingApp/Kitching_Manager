package com.kitching.app.ui.screen.other.notice

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.NoticeItem
import com.kitching.app.ui.model.NoticeViewModel
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.domain.AppResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

data class NoticeDTO(
    val date: LocalDate,
    val noticeId: String,
    val writerId: String,
    val writerName: String,
    val title: String,
    val content: String
)

@Composable
fun NoticeListScreen(
    commonState: CommonState,
    viewModel: NoticeViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"
    val writerName = "채연"

    LaunchedEffect(Unit) {
        viewModel.getNotices(teamId)
    }

    val noticeListState by viewModel.notices.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { commonState.navController.navigate(ScreenRouteDef.InnerContent.NoticeCreateOrUpdate.routeName + "/" + "/$writerName") }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            when (noticeListState) {
                is AppResult.Loading -> {
                    /* 인디케이터 */
                }

                is AppResult.Success -> {
                    val notices = (noticeListState as AppResult.Success).data
                    LazyColumn {
                        itemsIndexed(notices) { _, notice ->
                            NoticeItem(notice = notice) {
                                commonState.navController.navigate(ScreenRouteDef.InnerContent.NoticeDetail.routeName + "/${Json.encodeToString(notice)}")
                            }
                        }
                    }
                }

                is AppResult.Failure -> {}
                AppResult.Initial -> {}
            }
        }
    }
}