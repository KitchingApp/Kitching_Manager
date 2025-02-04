package com.kitching.app.ui.screen.other.notice

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.item.NoticeItem
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
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
    commonState: CommonState
) {
    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { /*TODO*/ }
    )

    val noticeList = listOf(
        NoticeDTO(
            date = LocalDate.of(2024, 2, 1),
            noticeId = "N001",
            writerId = "MGR001",
            writerName = "민수",
            title = "주방 위생 점검 안내",
            content = "내일 오전 10시에 주방 위생 점검이 진행됩니다. 모든 직원은 위생복을 착용하고, 조리 도구 정리를 철저히 해주시기 바랍니다."
        ),
        NoticeDTO(
            date = LocalDate.of(2024, 2, 3),
            noticeId = "N002",
            writerId = "MGR002",
            writerName = "채연",
            title = "신메뉴 교육 일정",
            content = "다음 주 월요일 오후 3시에 신메뉴 조리 및 서빙 교육이 있습니다. 모든 직원은 필참 바랍니다."
        ),
        NoticeDTO(
            date = LocalDate.of(2024, 2, 5),
            noticeId = "N003",
            writerId = "MGR003",
            writerName = "데레사",
            title = "근무 태도 관련 안내",
            content = "최근 고객 피드백에서 친절도가 지적되었습니다. 모든 직원은 근무 중 웃는 얼굴과 친절한 응대를 철저히 해주시기 바랍니다."
        )
    )
    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                itemsIndexed(noticeList) { index, notice ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        NoticeItem(notice = notice)
                    }
                }
            }
        }
    }
}