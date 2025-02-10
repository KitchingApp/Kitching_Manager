package com.kitching.app.ui.screen.other

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.item.SubdivisionCardItem
import com.kitching.app.ui.model.StaffLevelViewModel
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.defaultPadding
import com.kitching.app.util.PreferencesDataStore
import com.kitching.domain.AppResult

@Composable
fun StaffLevelScreen(
    commonState: CommonState,
    viewModel: StaffLevelViewModel = viewModel(factory = viewModelFactory)
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showUpdateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val textInputState = remember { mutableStateOf(TextFieldValue("")) }
    val optionMenuId = remember { mutableStateOf("") }

    var teamId by remember { mutableStateOf("") }
    val staffLevelsState by viewModel.staffLevelList.collectAsStateWithLifecycle()
    val staffLevelResultState by viewModel.staffLevelResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getStaffLevelList(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "직급관리",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            textInputState.value = TextFieldValue("")
            optionMenuId.value = ""
            showCreateDialog = true
        }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(defaultPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ResultConditionScreen(
                    loadingCondition = staffLevelsState is AppResult.Loading || staffLevelResultState is AppResult.Loading,
                    successCondition = staffLevelsState is AppResult.Success,
                    failCondition = staffLevelsState is AppResult.Failure || staffLevelResultState is AppResult.Failure,
                    failContent = {}
                ) {
                    val staffLevelData = (staffLevelsState as AppResult.Success).data
                    if (staffLevelData.isEmpty()) {
                        EmptyScreen("직급을 추가해주세요.")
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(staffLevelData) { staffLevel ->
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    SubdivisionCardItem(
                                        cardText = staffLevel.staffLevelName,
                                        onOptionBtnClick = {
                                            textInputState.value =
                                                TextFieldValue(staffLevel.staffLevelName)
                                            optionMenuId.value = staffLevel.staffLevelId
                                        }
                                    )
                                    if (optionMenuId.value == staffLevel.staffLevelId) {
                                        DropdownOptionMenu(
                                            onDismissRequest = { optionMenuId.value = "" },
                                            onClickModify = {
                                                showUpdateDialog = true
                                            },
                                            onClickDelete = {
                                                showDeleteDialog = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    if (showCreateDialog) {
                        BasicInputDialog(
                            title = "직급 생성",
                            textState = textInputState,
                            placeHolder = "직급명을 입력해주세요",
                            confirmText = "생성",
                            onClickConfirm = {
                                viewModel.createStaffLevel(teamId, textInputState.value.text)
                                viewModel.getStaffLevelList(teamId)
                                showCreateDialog = false
                                optionMenuId.value = ""
                            },
                            cancelText = "취소",
                            onClickCancel = {
                                optionMenuId.value = ""
                                showCreateDialog = false
                                optionMenuId.value = ""
                            }
                        )
                    }
                    if (showUpdateDialog) {
                        BasicInputDialog(
                            title = "직급 수정",
                            textState = textInputState,
                            placeHolder = "직급명을 입력해주세요",
                            confirmText = "수정",
                            onClickConfirm = {
                                viewModel.updateStaffLevel(optionMenuId.value, textInputState.value.text)
                                viewModel.getStaffLevelList(teamId)
                                showUpdateDialog = false
                                optionMenuId.value = ""
                            },
                            cancelText = "취소",
                            onClickCancel = {
                                showUpdateDialog = false
                                optionMenuId.value = ""
                            }
                        )
                    }
                    if (showDeleteDialog) {
                        BasicConfirmDialog(
                            message = "직급을 삭제하시겠습니까?",
                            confirmText = "삭제",
                            onClickConfirm = {
                                viewModel.deleteStaffLevel(optionMenuId.value)
                                viewModel.getStaffLevelList(teamId)
                                showDeleteDialog = false
                                optionMenuId.value = ""
                            },
                            cancelText = "취소",
                            onClickCancel = {
                                showDeleteDialog = false
                                optionMenuId.value = ""
                            }
                        )
                    }
                }
            }
        }
    }
}