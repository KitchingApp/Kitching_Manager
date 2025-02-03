package com.kitching.app.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.item.TeamListItem
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.PrimaryGreen300

@Preview(showBackground = true)
@Composable
fun SelectTeamScreen() {
    val dummyTeams = listOf(
        "팀 1",
        "팀 2",
        "팀 3",
        "팀 4",
        "팀 5"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.kitching_name_logo),
            contentDescription = "Kitching name img",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 28.dp, top = 16.dp)
                .size(width = 149.dp, height = 43.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false)
                .heightIn(max = 600.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(dummyTeams) { teamName ->
                    TeamListItem(teamName = teamName)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        Button(
            onClick = {  },
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