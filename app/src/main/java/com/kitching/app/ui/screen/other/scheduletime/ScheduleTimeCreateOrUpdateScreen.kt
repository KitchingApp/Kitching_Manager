package com.kitching.app.ui.screen.other.scheduletime

import android.icu.util.Calendar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberTimePickerState
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
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.TimeInputItem
import com.kitching.app.ui.model.ScheduleTimeViewModel
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray400
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.entities.ScheduleTime
import java.time.LocalTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTimeCreateOrUpdateScreen(
    commonState: CommonState,
    scheduleTime: ScheduleTime?,
    goToScheduleTimeList: () -> Unit,
    viewModel: ScheduleTimeViewModel = viewModel(factory = viewModelFactory)
) {
    val textState =
        remember { mutableStateOf(TextFieldValue(scheduleTime?.scheduleTimeName ?: "")) }

    val currentTime = Calendar.getInstance()
    val startTimeState = rememberTimePickerState(
        initialHour = if (scheduleTime !== null) LocalTime.parse(scheduleTime.startTime).hour else (currentTime.get(
            Calendar.HOUR_OF_DAY
        )),
        initialMinute = if (scheduleTime !== null) LocalTime.parse(scheduleTime.startTime).minute else (currentTime.get(
            Calendar.MINUTE
        )),
        is24Hour = false
    )
    val endTimeState = rememberTimePickerState(
        initialHour = if (scheduleTime !== null) LocalTime.parse(scheduleTime.endTime).hour else (currentTime.get(
            Calendar.HOUR_OF_DAY
        )),
        initialMinute = if (scheduleTime !== null) LocalTime.parse(scheduleTime.endTime).minute else (currentTime.get(
            Calendar.MINUTE
        )),
        is24Hour = false
    )

    var teamId by remember { mutableStateOf("") }
    val scheduleTimeResult by viewModel.scheduleTimeResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = if (scheduleTime == null) "스케줄타임 생성" else "스케줄타임 수정",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .border(
                                border = BorderStroke(1.dp, NeutralGray300),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(20.dp, 0.dp)
                    ) {
                        // 텍스트가 비어 있을 때만 placeholder를 표시
                        if (textState.value.text.isEmpty()) {
                            Text(
                                text = "타임명을 입력해주세요",
                                style = Body1.copy(
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
                            value = textState.value,
                            onValueChange = {
                                textState.value = it
                            },
                            textStyle = Body1.copy(
                                color = NeutralGray800,
                                textAlign = TextAlign.Start
                            ),
                            singleLine = true,
                            cursorBrush = SolidColor(NeutralGray800),
                        )
                    }
                }
                TimeInputItem(
                    title = "시작시간",
                    timePickerState = startTimeState
                )
                TimeInputItem(
                    title = "종료시간",
                    timePickerState = endTimeState
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = defaultPadding),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        modifier = Modifier
                            .width(150.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonColors(
                            containerColor = PrimaryGreen300,
                            contentColor = NeutralGray0,
                            disabledContainerColor = PrimaryGreen300,
                            disabledContentColor = NeutralGray0
                        ),
                        onClick = {
                            if (scheduleTime == null) {
                                viewModel.createScheduleTime(
                                    teamId = teamId,
                                    name = textState.value.text,
                                    startTime = String.format(Locale.KOREA,"%02d:%02d", startTimeState.hour, startTimeState.minute),
                                    endTime = String.format(Locale.KOREA, "%02d:%02d", endTimeState.hour, endTimeState.minute)
                                )
                            } else {
                                viewModel.updateScheduleTime(
                                    scheduleTimeId = scheduleTime.scheduleTimeId,
                                    name = textState.value.text,
                                    startTime = String.format(Locale.KOREA, "%02d:%02d", startTimeState.hour, startTimeState.minute),
                                    endTime = String.format(Locale.KOREA, "%02d:%02d", endTimeState.hour, endTimeState.minute)
                                )
                            }
                            goToScheduleTimeList()
                        }
                    ) {
                        Text(
                            text = if (scheduleTime == null) "생성" else "수정",
                            style = Body1
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .width(150.dp)
                            .height(40.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonColors(
                            containerColor = NeutralGray0,
                            contentColor = NeutralGray400,
                            disabledContainerColor = NeutralGray0,
                            disabledContentColor = NeutralGray400
                        ),
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, NeutralGray300),
                        onClick = { goToScheduleTimeList() }
                    ) {
                        Text(
                            text = "취소",
                            style = Body1
                        )
                    }
                }
            }
        }
    }
}