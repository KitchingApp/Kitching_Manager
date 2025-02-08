package com.kitching.app.ui.screen.login

import com.kitching.app.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.common.TeamSize
import com.kitching.app.common.teamSizeList
import com.kitching.app.ui.model.LoginViewModel
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.PrimaryGreen300
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTeamScreen(
    viewModel: LoginViewModel,
    coroutineScope: CoroutineScope
) {
    val navigationIconInfo = NavigationIconInfo.BACK

    var teamName by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    var selectedTeamSize by remember { mutableStateOf<TeamSize?>(null) }

    val onTeamNameChangeChange = { inputText: String ->
        teamName = inputText
    }

    var createTeamState by remember { mutableStateOf(false) }

    if (createTeamState) {
        SelectTeamScreen(viewModel, coroutineScope)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (-30).dp),
                            text = "팀 만들기",
                            style = H3_m,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(navigationIconInfo.icon),
                            contentDescription = navigationIconInfo.description,
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.create_team),
                contentDescription = "puzzle img",
                modifier = Modifier
                    .padding(top = 30.dp)
                    .size(width = 132.dp, height = 130.dp)
            )

            Spacer(modifier = Modifier.height(38.dp))

            OutlinedTextField(
                value = teamName,
                onValueChange = { onTeamNameChangeChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen300,
                    unfocusedBorderColor = PrimaryGreen300
                ),
                placeholder = { Text("팀 이름을 적어주세요!") },
                modifier = Modifier.padding(10.dp),
                textStyle = Body1_m
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedTeamSize?.label ?: "팀 인원 선택",
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else {
                                    Icons.Default.KeyboardArrowDown
                                },
                                contentDescription = "드롭다운 아이콘",
                                tint = Color.Black
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen300,
                        unfocusedBorderColor = PrimaryGreen300
                    ),
                    textStyle = Body1_m,
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    teamSizeList.forEach { item ->
                        DropdownMenuItem(
                            onClick = {
                                selectedTeamSize = item
                                expanded = false
                            },
                            text = {
                                Text(text = item.label)
                            },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(68.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        val userId = viewModel.dataStore.getUserId().toString()
                        viewModel.createTeam(userId, teamName, selectedTeamSize?.value ?: 0)
                        createTeamState = true
                    }
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
                    text = "만들기",
                    style = H3_m
                )
            }
        }
    }
}