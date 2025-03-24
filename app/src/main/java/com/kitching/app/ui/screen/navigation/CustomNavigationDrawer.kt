package com.kitching.app.ui.screen.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kitching.app.ui.item.TeamCardItem
import com.kitching.app.ui.theme.H1
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.ui.theme.drawerWidth
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Team

@Composable
fun CustomNavigationDrawer(
    drawerState: DrawerState,
    teamListState: AppResult<List<Team>>,
    onTeamItemClick: (Team) -> Unit,
onTeamCreateClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = NeutralGray0
            ) {
                Column(
                    modifier = Modifier
                        .width(drawerWidth)
                        .padding(defaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            text = "팀 리스트",
                            style = H1.copy(color = NeutralGray800)
                        )
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 3.dp,
                            color = PrimaryGreen300
                        )
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
//                        val teamListdata = if(teamListState is AppResult.Success) teamListState.data else emptyList<Team>()
//                        teamListdata.forEach { team ->
//                            item(key = team.teamId) {
//                                TeamCardItem(
//                                    teamName = team.teamName,
//                                    onCardClick = { onTeamItemClick(team) }
//                                )
//                            }
//                        }
                    }
                    TextButton(
                        modifier = Modifier
                            .width(260.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonColors(
                            containerColor = PrimaryGreen300,
                            contentColor = NeutralGray0,
                            disabledContainerColor = PrimaryGreen300,
                            disabledContentColor = NeutralGray0
                        ),
                        onClick = { onTeamCreateClick() },
                    ) {
                        Text(
                            text = "팀 생성",
                            color = NeutralGray0,
                            style = H1.copy(
                                color = NeutralGray800,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        content()
    }
}