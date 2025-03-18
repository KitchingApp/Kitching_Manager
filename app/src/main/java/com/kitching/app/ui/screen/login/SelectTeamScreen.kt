package com.kitching.app.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kitching.app.R
import com.kitching.app.ui.item.TeamListItem
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.domain.AppResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SelectTeamScreen(
    viewModel: LoginViewModel,
    coroutineScope: CoroutineScope,
    onNavigateToCreateTeam: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val teamListState by viewModel.teamList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTeamList(viewModel.dataStore.getUserId())
    }

    KitchingManagerTheme {
        ResultConditionScreen(
            loadingCondition = teamListState is AppResult.Loading,
            successCondition = teamListState is AppResult.Success,
            failCondition = teamListState is AppResult.Failure,
            onRetryBtnClick = {
                coroutineScope.launch {
                    viewModel.getTeamList(viewModel.dataStore.getUserId())
                }
            }
        ) {
            val teamList = (teamListState as AppResult.Success).data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = defaultPadding),
                    contentAlignment = Alignment.TopStart
                ) {
                    Image(
                        painter = painterResource(R.drawable.kitching_name_logo),
                        contentDescription = "Kitching name img",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 28.dp, top = 30.dp)
                            .size(width = 149.dp, height = 43.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    teamList.forEach { team ->
                        item(key = team.teamId) {
                            TeamListItem(
                                teamName = team.teamName,
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.dataStore.saveTeamId(team.teamId)
                                        viewModel.dataStore.saveTeamName(team.teamName)
                                        onNavigateToMain()
                                    }
                                }
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        onNavigateToCreateTeam()
                    },
                    modifier = Modifier
                        .width(296.dp)
                        .height(76.dp)
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryGreen300,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "팀 생성",
                        style = H3_m
                    )
                }
            }
        }
    }
}