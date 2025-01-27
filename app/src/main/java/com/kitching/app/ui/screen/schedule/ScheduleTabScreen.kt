package com.kitching.app.ui.screen.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kitching.app.navgraph.ScheduleTabItem
import com.kitching.app.ui.screen.dialog.DatePickerModal
import com.kitching.app.ui.theme.KitchingManagerTheme
import java.time.LocalDateTime

//@RequiresApi(Build.VERSION_CODES.O)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview
@Composable
fun ScheduleTabScreen() {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    val tabItems = ScheduleTabItem().renderTabItems()
    val tabPageState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabItems.size }
    )
    val scope = rememberCoroutineScope()

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                DateSelector(
                    selectedDateTime = selectedDateTime,
                    onDateChange = { newDate ->
                        selectedDateTime = newDate
                    },
                    onClickDateBtn = {
                        showDatePicker = true
                    }
                )
                ScheduleTabs(
                    tabItems = tabItems,
                    tabPageState = tabPageState,
                    scope = scope
                )
                ScheduleTabContent(tabPageState)
                if (showDatePicker) {
                    DatePickerModal(
                        selectedDateTime = selectedDateTime,
                        onDismissRequest = { showDatePicker = false },
                        onClickConfirm = { selectedDateMillis ->
                            if(selectedDateMillis !== null) {
                                selectedDateTime =
                                    LocalDateTime.ofInstant(
                                        java.time.Instant.ofEpochMilli(selectedDateMillis),
                                        java.time.ZoneId.systemDefault()
                                    )
                            }
                            showDatePicker = false
                        },
                        onClickCancel = { showDatePicker = false }
                    )
                }
            }
        }
    }
}