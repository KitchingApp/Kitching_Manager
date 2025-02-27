package com.kitching.app.ui.screen.other.notice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
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
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.H5_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray100
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Notice
import java.time.LocalDate

/**
 * Notice create or modify screen 공지사항을 생성하거나 수정하는 화면
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param notice notice가 null이면 생성, null이 아니면 수정
 * @param viewModel
 */
@Composable
fun NoticeCreateOrModifyScreen(
    commonState: CommonState,
    notice: Notice?,
    viewModel: NoticeViewModel = viewModel(factory = viewModelFactory)
) {
    var titleTextState by remember { mutableStateOf(TextFieldValue(notice?.title ?: "")) }
    var contentTextState by remember { mutableStateOf(TextFieldValue(notice?.content ?: "")) }

    var teamId by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    val userNameResultState by viewModel.userName.collectAsStateWithLifecycle()
    val noticeResultState by viewModel.noticeResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        userId = PreferencesDataStore().getUserId()
        if(notice == null) viewModel.getUserName(userId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = NeutralGray0,
        title = "공지사항",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL,
        onClickActionIcon = {}
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = noticeResultState is AppResult.Loading && userNameResultState is AppResult.Loading,
                successCondition = if(notice == null) userNameResultState is AppResult.Success else true,
                failCondition =
                noticeResultState is AppResult.Failure && (if(notice == null) userNameResultState is AppResult.Failure else true),
                onRetryBtnClick = {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth() // 가로 꽉 채우기
                                .border(
                                    border = BorderStroke(1.dp, NeutralGray300)
                                )
                                .padding(10.dp, 10.dp)
                        ) {
                            if (titleTextState.text.isEmpty()) {
                                Text(
                                    text = "제목을 입력해주세요.",
                                    style = H2.copy(
                                        color = NeutralGray300,
                                        textAlign = TextAlign.Start
                                    ),
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                value = titleTextState,
                                onValueChange = {
                                    titleTextState = it
                                },
                                textStyle = H2.copy(
                                    color = NeutralGray800,
                                    textAlign = TextAlign.Start
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(NeutralGray800),
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(7.dp)
                        ) {
                            Text(
                                style = Body1_m.copy(color = NeutralGray800),
                                text = notice?.writerName ?: (userNameResultState as AppResult.Success).data
                            )
                            Text(
                                style = H5.copy(color = NeutralGray800),
                                text = "|"
                            )
                            Text(
                                style = Body1_m.copy(color = NeutralGray800),
                                text = notice?.date ?: LocalDate.now().toString()
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .border(
                                    border = BorderStroke(1.dp, NeutralGray300)
                                )
                                .padding(10.dp, 10.dp)
                        ) {
                            if (contentTextState.text.isEmpty()) {
                                Text(
                                    text = "내용을 입력해주세요.",
                                    style = Caption1_R.copy(
                                        color = NeutralGray300,
                                        textAlign = TextAlign.Start
                                    ),
                                    modifier = Modifier.align(Alignment.TopStart)
                                )
                            }
                            BasicTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopStart),
                                value = contentTextState,
                                onValueChange = {
                                    contentTextState = it
                                },
                                textStyle = Caption1_R.copy(
                                    color = NeutralGray800,
                                    textAlign = TextAlign.Start
                                ),
                                singleLine = false, // 멀티라인 허용
                                cursorBrush = SolidColor(NeutralGray800),
                            )
                        }
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
                                    if (notice !== null) {
                                        viewModel.updateNotice(
                                            noticeId = notice.noticeId,
                                            title = titleTextState.text,
                                            content = contentTextState.text
                                        )
                                    } else {
                                        viewModel.createNotice(
                                            userId = userId,
                                            title = titleTextState.text,
                                            content = contentTextState.text,
                                            teamId = teamId
                                        )
                                    }
                                    if(noticeResultState is AppResult.Success) {
                                        viewModel.getNotices(teamId)
                                        commonState.navController.navigate(ScreenRouteDef.InnerContent.NoticeList.routeName)
                                    }
                                },
                                colors = ButtonColors(
                                    containerColor = PrimaryGreen300,
                                    contentColor = NeutralGray0,
                                    disabledContainerColor = PrimaryGreen300,
                                    disabledContentColor = NeutralGray0
                                )
                            ) {
                                Text(
                                    text = if (notice !== null) "수정" else "등록",
                                    style = H5.copy(color = NeutralGray0)
                                )
                            }
                            TextButton(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp),
                                shape = RoundedCornerShape(20.dp),
                                onClick = {
                                    commonState.navController.popBackStack()
                                },
                                colors = ButtonColors(
                                    containerColor = NeutralGray100,
                                    contentColor = NeutralGray0,
                                    disabledContainerColor = PrimaryGreen300,
                                    disabledContentColor = NeutralGray0
                                )
                            ) {
                                Text(
                                    text = "취소",
                                    style = H5_m.copy(color = NeutralGray800)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}