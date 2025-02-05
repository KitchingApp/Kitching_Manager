//package com.kitching.app.ui.screen.schedule
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.PagerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//
//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun ScheduleTabContent(
//    pagerState: PagerState,
//    showDeleteDialog: MutableState<Boolean>,
//) {
//    HorizontalPager(state = pagerState) { page ->
//        when (page) {
//            0 -> FixedScheduleScreen(scheduleList, showDeleteDialog)
//            1 -> AppliedScheduleScreen(scheduleList)
//        }
//    }
//}