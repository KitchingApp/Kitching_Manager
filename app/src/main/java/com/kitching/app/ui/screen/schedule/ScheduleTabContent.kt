package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable

@Composable
fun ScheduleTabContent(pagerState: PagerState) {
    HorizontalPager(state = pagerState) { page ->
        when(page) {
            0 -> FixedScheduleScreen()
            1 -> AppliedScheduleScreen()
        }
    }
}