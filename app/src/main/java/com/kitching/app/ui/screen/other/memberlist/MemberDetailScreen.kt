package com.kitching.app.ui.screen.other.memberlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CoilImageRequest
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.MemberViewModel
import com.kitching.app.ui.theme.Body1
import com.kitching.app.ui.theme.H2_m
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.domain.AppResult
import com.kitching.domain.entities.Member
import com.kitching.domain.entities.StaffLevel

@Composable
fun MemberDetailScreen(
    commonState: CommonState,
    member: Member,
    viewModel: MemberViewModel = viewModel(factory = viewModelFactory)
) {
    val teamId = "3uM01g5GSz8lC49JA6vq"

    var isManager by remember { mutableStateOf(member.manager) }
    val selectedStaffLevel = remember { mutableStateOf(StaffLevel(staffLevelId = member.staffLevelId, staffLevelName = member.staffLevelName)) }
    val isExpended = remember { mutableStateOf(false) }

    val staffLevels by viewModel.staffLevels.collectAsStateWithLifecycle()
    val memberResult by viewModel.memberResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "멤버수정",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.NULL
    )

    LaunchedEffect(Unit) {
        viewModel.getStaffLevels(teamId)
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            if(staffLevels is AppResult.Success) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(defaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(defaultPadding)
                ) {
                    Box(
                        modifier = Modifier
                            .width(320.dp)
                            .height(340.dp)
                            .background(color = NeutralGray300, shape = RoundedCornerShape(20.dp))
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(20.dp)),
                            model = CoilImageRequest.getImageRequest(member.userImage),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.padding(bottom = defaultPadding),
                                text = member.userName,
                                style = H2_m.copy(color = NeutralGray0)
                            )
                        }
                    }
                    StaffLevelDropdownComponent(
                        staffLevels = (staffLevels as AppResult.Success).data,
                        selectedStaffLevel = selectedStaffLevel,
                        isExpanded = isExpended
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = defaultPadding),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "관리자로 지정하기",
                            style = Body1.copy(color = NeutralGray800)
                        )
                        Switch(
                            checked = isManager,
                            onCheckedChange = { isManager = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = PrimaryGreen300,
                                checkedTrackColor = NeutralGray0,
                                checkedBorderColor = NeutralGray300,
                                uncheckedThumbColor = NeutralGray300,
                                uncheckedTrackColor = NeutralGray0,
                                uncheckedBorderColor = NeutralGray300,
                            )
                        )
                    }
                    TextButton(
                        modifier = Modifier.width(162.dp).height(40.dp),
                        onClick = {
                            viewModel.updateMember(member.userTeamId, selectedStaffLevel.value.staffLevelId, isManager)
                            if(memberResult is AppResult.Success) {
                                commonState.navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryGreen300,
                            contentColor = NeutralGray0
                        )
                    ) {
                        Text(
                            text ="수정완료",
                            style = H5
                        )
                    }
                }
            }
        }
    }
}