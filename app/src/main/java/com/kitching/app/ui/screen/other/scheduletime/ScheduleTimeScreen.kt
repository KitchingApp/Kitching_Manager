package com.kitching.app.ui.screen.other.scheduletime

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.ScheduleTimeItem
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.customFormat
import com.kitching.domain.AppResult
import com.kitching.domain.entities.ScheduleTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalTime

//@Preview
@Composable
fun ScheduleTimeScreen(
    commonState: CommonState,
    viewModel: ScheduleTimeViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    val initScheduleTime = ScheduleTime(
        scheduleTimeId = "",
        scheduleTimeName = "",
        startTime = "",
        endTime = ""
    )

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedScheduleTime by remember { mutableStateOf(initScheduleTime) }

    val scheduleTimes by viewModel.scheduleTimes.collectAsStateWithLifecycle()
    val scheduleTimeResult by viewModel.scheduleTimeResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "스케줄타임",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            selectedScheduleTime = ScheduleTime(
                scheduleTimeId = "",
                scheduleTimeName = "",
                startTime = LocalTime.now().customFormat(),
                endTime = LocalTime.now().customFormat()
            )
            commonState.navController.navigate(
                ScreenRouteDef.InnerContent.ScheduleTimeCreateOrUpdate.routeName + "/${""}"
            )
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
                                        ScheduleTimeItem(
                                            scheduleTimeName = scheduleTime.scheduleTimeName,
                                            startTime = scheduleTime.startTime,
                                            endTime = scheduleTime.endTime,
                                            onOptionBtnClick = {
                                                selectedScheduleTime = scheduleTime
                                            }
                                        )
                                        if (selectedScheduleTime.scheduleTimeId == scheduleTime.scheduleTimeId) {
                                            DropdownOptionMenu(
                                                onDismissRequest = { selectedScheduleTime = initScheduleTime },
                                                onClickModify = {
                                                    commonState.navController.navigate(
                                                        ScreenRouteDef.InnerContent.ScheduleTimeCreateOrUpdate.routeName +
                                                                "/${if (selectedScheduleTime.scheduleTimeId.isEmpty()) null else Json.encodeToString(selectedScheduleTime)}"
                                                    )
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
            if (showDeleteDialog) {
                BasicConfirmDialog(
                    message = "스케줄타임을 삭제하시겠습니까?",
                    confirmText = "삭제",
                    onClickConfirm = {
                        viewModel.deleteScheduleTime(selectedScheduleTime.scheduleTimeId)
                        viewModel.getScheduleTimes(teamId)
                        showDeleteDialog = false
                        selectedScheduleTime = initScheduleTime
                    },
                    cancelText = "취소",
                    onClickCancel = {
                        showDeleteDialog = false
                        selectedScheduleTime = initScheduleTime
                    }
                )
            }
        }
    }
}