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
import com.kitching.app.navgraph.ScheduleTimeItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.ScheduleTimeItem
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.ScheduleTime

@Composable
fun ScheduleTimeScreen(
    commonState: CommonState,
    naviagateToCreateOfModifyScheduleTime: (scheduleTime: ScheduleTimeItem) -> Unit,
    viewModel: ScheduleTimeViewModel = viewModel(factory = viewModelFactory)
) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedScheduleTime by remember { mutableStateOf(ScheduleTime()) }

    var teamId by remember { mutableStateOf("") }
    val scheduleTimes by viewModel.scheduleTimes.collectAsStateWithLifecycle()
    val scheduleTimeResult by viewModel.scheduleTimeResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getScheduleTimes(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "스케줄타임",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            naviagateToCreateOfModifyScheduleTime(ScheduleTimeItem.init())
        }
    )

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
                ResultConditionScreen(
                    loadingCondition = scheduleTimes is AppResult.Loading || scheduleTimeResult is AppResult.Loading,
                    successCondition = scheduleTimes is AppResult.Success,
                    failCondition = scheduleTimes is AppResult.Failure || scheduleTimeResult is AppResult.Failure,
                    onRetryBtnClick = {}
                ) {
                    val scheduleTimeData = (scheduleTimes as AppResult.Success).data
                    if (scheduleTimeData.isEmpty()) {
                        EmptyScreen("스케줄타임을 추가해주세요.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(items = scheduleTimeData, key = {it.scheduleTimeId}) { scheduleTime ->
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
                                            onDismissRequest = { selectedScheduleTime = ScheduleTime() },
                                            onClickModify = {
                                                naviagateToCreateOfModifyScheduleTime(ScheduleTimeItem.domainToItem(selectedScheduleTime))
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
                    if (showDeleteDialog) {
                        BasicConfirmDialog(
                            message = "스케줄타임을 삭제하시겠습니까?",
                            confirmText = "삭제",
                            onClickConfirm = {
                                viewModel.deleteScheduleTime(selectedScheduleTime.scheduleTimeId)
                                viewModel.getScheduleTimes(teamId)
                                showDeleteDialog = false
                                selectedScheduleTime = ScheduleTime()
                            },
                            cancelText = "취소",
                            onClickCancel = {
                                showDeleteDialog = false
                                selectedScheduleTime = ScheduleTime()
                            }
                        )
                    }
                }
            }
        }
    }
}