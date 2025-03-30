package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.R
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScheduleTabItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.ScheduleViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.schedule.dialog.DatePickerModal
import com.kitching.app.ui.screen.schedule.dialog.ScheduleCreateDialog
import com.kitching.app.ui.screen.schedule.dialog.ScheduleRejectDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.Schedule
import com.kitching.domain.entities.ScheduleTime
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Schedule tab screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param viewModel
 */
@Composable
fun ScheduleMainScreen(
    commonState: CommonState,
    viewModel: ScheduleViewModel = viewModel(factory = viewModelFactory)
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    var targetSchedule by remember { mutableStateOf(Schedule()) }
    val rejectReasonState = remember { mutableStateOf(TextFieldValue("")) }

    val tabItems = ScheduleTabItem().renderTabItems()
    val tabPageState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabItems.size }
    )

    var teamId by remember { mutableStateOf("") }
    val schedulesState by viewModel.schedules.collectAsStateWithLifecycle()
    val scheduleResultState by viewModel.scheduleResult.collectAsStateWithLifecycle()
    val rejectPushMessageResultState by viewModel.rejectPushMessageResult.collectAsStateWithLifecycle()
    val allMembersState by viewModel.members.collectAsStateWithLifecycle()
    val scheduleTimesState by viewModel.scheduleTimes.collectAsStateWithLifecycle()

    LaunchedEffect(selectedDateTime) {
        teamId = PreferencesDataStore(KitchingApplication.getInstance()).getTeamId()
        viewModel.getSchedules(teamId, selectedDateTime.toLocalDate().toString())
        viewModel.getMembers(teamId)
        viewModel.getScheduleTimes(teamId)
    }

    LaunchedEffect(rejectPushMessageResultState) {
        if (rejectPushMessageResultState is AppResult.Success) viewModel.deleteSchedule(
            targetSchedule.scheduleId
        )
    }

    LaunchedEffect(scheduleResultState) {
        if (scheduleResultState is AppResult.Success) viewModel.getSchedules(
            teamId,
            selectedDateTime.toLocalDate().toString()
        )
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = PrimaryGreen300,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            showCreateDialog = true
        }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ResultConditionScreen(
                    loadingCondition =
                    (schedulesState is AppResult.Loading || scheduleResultState is AppResult.Loading || allMembersState is AppResult.Loading || scheduleTimesState is AppResult.Loading || rejectPushMessageResultState is AppResult.Loading),
                    successCondition =
                    (schedulesState is AppResult.Success && allMembersState is AppResult.Success && scheduleTimesState is AppResult.Success),
                    failCondition =
                    (schedulesState is AppResult.Failure || scheduleResultState is AppResult.Failure || allMembersState is AppResult.Failure || scheduleTimesState is AppResult.Failure || rejectPushMessageResultState is AppResult.Failure),
                    onRetryBtnClick = {
                    }
                ) {
                    DateSelector(
                        selectedDateTime = selectedDateTime,
                        onDateChange = { newDate ->
                            selectedDateTime = newDate
                        },
                        onClickDateBtn = {
                            showDatePickerDialog = true
                        }
                    )
                    ScheduleTabs(
                        tabItems = tabItems,
                        tabPageState = tabPageState,
                        onClickTabs = { index ->
                            commonState.coroutineScope.launch {
                                tabPageState.animateScrollToPage(index)
                            }
                        }
                    )
                    // schedule tab content
                    HorizontalPager(state = tabPageState) { page ->
                        when (page) {
                            0 -> FixedScheduleScreen(
                                scheduleList = (schedulesState as AppResult.Success).data.filter { it.fix },
                                onDeleteClick = { scheduleId ->
                                    targetSchedule.scheduleId = scheduleId
                                    showDeleteDialog = true
                                }
                            )

                            1 -> AppliedScheduleScreen(
                                scheduleList = (schedulesState as AppResult.Success).data.filter { !it.fix },
                                onApplyClick = { scheduleId ->
                                    viewModel.applySchedule(scheduleId)
                                },
                                onRejectClick = { schedule ->
                                    targetSchedule = schedule
                                    showRejectDialog = true
                                }
                            )
                        }
                    }
                    if (showDatePickerDialog) {
                        DatePickerModal(
                            selectedDateTime = selectedDateTime,
                            onDismissRequest = { showDatePickerDialog = false },
                            onClickConfirm = { selectedDateMillis ->
                                if (selectedDateMillis !== null) {
                                    selectedDateTime =
                                        LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(selectedDateMillis),
                                            ZoneId.systemDefault()
                                        )
                                }
                                showDatePickerDialog = false
                            },
                            onClickCancel = { showDatePickerDialog = false }
                        )
                    }
                    if (showCreateDialog) {
                        ScheduleCreateDialog(
                            onDismissRequest = {
                                showCreateDialog = false
                            },
                            selectedDateTime = selectedDateTime,
                            members = (allMembersState as AppResult.Success<List<Member>>).data,
                            onClickConfirm = { selectedMember, selectedScheduleTime ->
                                viewModel.createSchedule(
                                    teamId = teamId,
                                    dateString = selectedDateTime.toLocalDate().toString(),
                                    userId = selectedMember.userId,
                                    scheduleTimeId = selectedScheduleTime.scheduleTimeId,
                                    fix = true
                                )
                            },
                            scheduleTimes = (scheduleTimesState as AppResult.Success<List<ScheduleTime>>).data,
                        )
                    }
                    if (showDeleteDialog) {
                        BasicConfirmDialog(
                            message = stringResource(R.string.schedule_delete_dialog_message),
                            confirmText = stringResource(R.string.button_delete),
                            onClickConfirm = {
                                viewModel.deleteSchedule(targetSchedule.scheduleId)
                            },
                            cancelText = stringResource(R.string.button_cancel),
                            onClickCancel = { showDeleteDialog = false }
                        )
                    }
                    if (showRejectDialog) {
                        ScheduleRejectDialog(
                            rejectReasonState = rejectReasonState,
                            onClickReject = {
                                viewModel.sendRejectPushMessage(
                                    teamId = teamId,
                                    schedule = targetSchedule,
                                    rejectReason = rejectReasonState.value.text
                                )
                            },
                            onClickCancel = { showRejectDialog = false }
                        )
                    }
                }
            }

        }
    }
}