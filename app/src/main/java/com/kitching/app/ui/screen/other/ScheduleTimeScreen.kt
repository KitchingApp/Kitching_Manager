package com.kitching.app.ui.screen.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.SubdivisionCardItem
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.domain.AppResult

//@Preview
@Composable
fun ScheduleTimeScreen(
    commonState: CommonState,
    viewModel: ScheduleTimeViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    var showCreateDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val textInputState = remember { mutableStateOf(TextFieldValue("")) }

    val optionMenuId = remember { mutableStateOf("") }

    val scheduleTimes by viewModel.scheduleTimes.collectAsStateWithLifecycle()
    val scheduleTimeResult by viewModel.scheduleTimeResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "스케줄타임",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            textInputState.value = TextFieldValue("")
            optionMenuId.value = ""
            showCreateDialog = true
        }
    )

    LaunchedEffect(Unit) {
        viewModel.getScheduleTimes(teamId)
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                when (scheduleTimes) {
                    is AppResult.Initial -> {}
                    is AppResult.Loading -> {}
                    is AppResult.Success -> {
                        val scheduleTimeData = (scheduleTimes as AppResult.Success).data
                        if (scheduleTimeData.isEmpty()) {
                            EmptyScreen("스케줄타임을 추가해주세요.")
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                items(scheduleTimeData) { scheduleTime ->
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        SubdivisionCardItem(
                                            cardText = scheduleTime.scheduleTimeName,
                                            onOptionBtnClick = {
                                                textInputState.value =
                                                    TextFieldValue(scheduleTime.scheduleTimeName)
                                                optionMenuId.value = scheduleTime.scheduleTimeId
                                            }
                                        )
                                        if (optionMenuId.value == scheduleTime.scheduleTimeId) {
                                            DropdownOptionMenu(
                                                optionMenuId = optionMenuId,
                                                onClickModify = {
                                                    showUpdateDialog = true
                                                },
                                                onClickDelete = {
                                                    showDeleteDialog = true
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is AppResult.Failure -> {}
                }
            }
            if (showCreateDialog) {
                BasicInputDialog(
                    title = "직급 생성",
                    textState = textInputState,
                    placeHolder = "직급명을 입력해주세요",
                    confirmText = "생성",
                    onClickConfirm = {
//                        viewModel.createScheduleTime(teamId, textInputState.value.text)
                        viewModel.getScheduleTimes(teamId)
                        showCreateDialog = false
                        optionMenuId.value = ""
                    },
                    cancelText = "취소",
                    onClickCancel = {
                        optionMenuId.value = ""
                        showCreateDialog = false
                        optionMenuId.value = ""
                    }
                )
            }
            if (showUpdateDialog) {
                BasicInputDialog(
                    title = "직급 수정",
                    textState = textInputState,
                    placeHolder = "직급명을 입력해주세요",
                    confirmText = "수정",
                    onClickConfirm = {
//                        viewModel.updateScheduleTime(optionMenuId.value, textInputState.value.text)
                        viewModel.getScheduleTimes(teamId)
                        showUpdateDialog = false
                        optionMenuId.value = ""
                    },
                    cancelText = "취소",
                    onClickCancel = {
                        showUpdateDialog = false
                        optionMenuId.value = ""
                    }
                )
            }
            if (showDeleteDialog) {
                BasicConfirmDialog(
                    message = "직급을 삭제하시겠습니까?",
                    confirmText = "삭제",
                    onClickConfirm = {
                        viewModel.deleteScheduleTime(optionMenuId.value)
                        viewModel.getScheduleTimes(teamId)
                        showDeleteDialog = false
                        optionMenuId.value = ""
                    },
                    cancelText = "취소",
                    onClickCancel = {
                        showDeleteDialog = false
                        optionMenuId.value = ""
                    }
                )
            }
        }
    }
}