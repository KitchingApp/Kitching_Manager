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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScheduleTabItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.ScheduleViewModel
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.schedule.dialog.DatePickerModal
import com.kitching.app.ui.screen.schedule.dialog.ScheduleCreateDialog
import com.kitching.app.ui.screen.schedule.dialog.ScheduleRejectDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.ScheduleTime
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun ScheduleTabScreen(
    commonState: CommonState,
    viewModel: ScheduleViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showRejectDialog by remember { mutableStateOf(false) }

    /** 드롭다운 메뉴가 열려있는지 저장 */
    val isExpanded = remember { mutableStateOf(false) }

    val selectedDateTime = remember { mutableStateOf(LocalDateTime.now()) }

    var targetScheduleId by remember { mutableStateOf("") }
    val rejectReasonState = remember { mutableStateOf(TextFieldValue("")) }
    
    /** 드롭다운에서 선택된 멤버 */
    val selectedMember = remember {
        mutableStateOf(
            Member(
                userTeamId = "",
                userId = "",
                userName = "",
                userImage = "",
                staffLevelId = "",
                staffLevelName = "",
                manager = false
            )
        )
    }
    /** 선택된 스케줄타임 */
    val selectedScheduleTime = remember {
        mutableStateOf(
            ScheduleTime(
                scheduleTimeId = "",
                scheduleTimeName = "",
                startTime = LocalTime.now(),
                endTime = LocalTime.now()
            )
        )
    }

    val tabItems = ScheduleTabItem().renderTabItems()
    val tabPageState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabItems.size }
    )

    val schedulesState by viewModel.schedules.collectAsStateWithLifecycle()
    val scheduleResultState by viewModel.scheduleResult.collectAsStateWithLifecycle()
    val allMembers by viewModel.members.collectAsStateWithLifecycle()
    val scheduleTimes by viewModel.scheduleTimes.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
        viewModel.getMembers(teamId)
        viewModel.getScheduleTimes(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        containerColor = PrimaryGreen300,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            showCreateDialog = true
        },
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
                if (schedulesState is AppResult.Success) {
                    DateSelector(
                        selectedDateTime = selectedDateTime.value,
                        onDateChange = { newDate ->
                            selectedDateTime.value = newDate
                        },
                        onClickDateBtn = {
                            showDatePickerDialog = true
                        }
                    )
                    ScheduleTabs(
                        tabItems = tabItems,
                        tabPageState = tabPageState,
                        onClickTabs = { index ->
                            commonState.scope.launch {
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
                                    targetScheduleId = scheduleId
                                    showDeleteDialog = true
                                    viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                                }
                            )
                            1 -> AppliedScheduleScreen(
                                scheduleList = (schedulesState as AppResult.Success).data.filter { !it.fix },
                                onApplyClick = { scheduleId ->
                                    viewModel.applySchedule(scheduleId)
                                    viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                                },
                                onRejectClick = { scheduleId ->
                                    targetScheduleId = scheduleId
                                    showRejectDialog = true
                                    viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                                }
                            )
                        }
                    }
                }

                if (showDatePickerDialog) {
                    DatePickerModal(
                        selectedDateTime = selectedDateTime.value,
                        onDismissRequest = { showDatePickerDialog = false },
                        onClickConfirm = { selectedDateMillis ->
                            if (selectedDateMillis !== null) {
                                selectedDateTime.value =
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
                if(scheduleTimes is AppResult.Success && allMembers is AppResult.Success) {
                    if (showCreateDialog) {
                        ScheduleCreateDialog(
                            isExpanded = isExpanded,
                            onDismissRequest = {
                                if (isExpanded.value) {
                                    isExpanded.value = false
                                } else {
                                    showCreateDialog = false
                                }
                            },
                            selectedDateTime = selectedDateTime.value,
                            members = (allMembers as AppResult.Success<List<Member>>).data,
                            onClickConfirm = {
                                viewModel.createSchedule(
                                    teamId = teamId,
                                    dateString = selectedDateTime.value.toLocalDate().toString(),
                                    userId = selectedMember.value.userId,
                                    scheduleTimeId = selectedScheduleTime.value.scheduleTimeId,
                                    fix = true
                                )
                                viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                            },
                            selectedMember = selectedMember,
                            scheduleTimes = (scheduleTimes as AppResult.Success<List<ScheduleTime>>).data,
                            selectedScheduleTimes = selectedScheduleTime
                        )
                    }
                }
                if (showDeleteDialog) {
                    BasicConfirmDialog(
                        message = "스케줄을 삭제하시겠습니까?",
                        confirmText = "삭제",
                        onClickConfirm = {
                            viewModel.deleteSchedule(targetScheduleId)
                            viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                        },
                        cancelText = "취소",
                        onClickCancel = { showDeleteDialog = false }
                    )
                }
                if(showRejectDialog) {
                    ScheduleRejectDialog(
                        rejectReasonState = rejectReasonState,
                        onClickReject = {
                            viewModel.deleteSchedule(targetScheduleId)
                            viewModel.getSchedules(teamId, selectedDateTime.value.toLocalDate().toString())
                        },
                        onClickCancel = { showRejectDialog = false }
                    )
                }
            }

        }
    }
}