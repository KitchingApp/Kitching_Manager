package com.kitching.app.ui.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.navgraph.ScheduleTabItem
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.SecondaryLightGreen100
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ScheduleTabs(
    tabItems: List<ScheduleTabItem>,
    tabPageState: PagerState,
    scope: CoroutineScope
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = tabPageState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabPageState.currentPage]),
                    color = PrimaryGreen300
                )
            }
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = tabPageState.currentPage == index,
                    onClick = {
                        scope.launch {
                            tabPageState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier
                        .background(
                            color = if (tabPageState.currentPage == index) SecondaryLightGreen100
                            else Color.White,
                            shape = RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp
                            )
                        )
                        .height(60.dp),
                    text = {
                        Text(
                            text = tabItem.tabName,
                            fontWeight = if (tabPageState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                            color = Color.Black,
                            fontSize = 16.dp.value.sp,
                        )
                    }
                )
            }
        }
    }
}