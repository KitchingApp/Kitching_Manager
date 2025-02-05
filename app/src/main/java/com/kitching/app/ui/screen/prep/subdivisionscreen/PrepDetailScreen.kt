package com.kitching.app.ui.screen.prep.subdivisionscreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.PrepViewModel
import com.kitching.app.ui.screen.categoryscreen.CategorySubDivisionScreen
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.categoryscreen.SubdivisionItemForScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.hexToArgb
import com.kitching.domain.AppResult

/**
 * Prep detail screen
 *
 * @param commonState 현 화면의 앱바등을 정의하는 data class
 * @param categoryId
 * @param categoryName
 * @param categoryColor Hex color code (ex. #B3F31C)
 * @param viewModel
 */
@Composable
fun PrepDetailScreen(
    commonState: CommonState,
    categoryId: String?,
    categoryName: String?,
    categoryColor: String?,
    viewModel: PrepViewModel = viewModel(factory = viewModelFactory)
) {
    if (categoryId !== null && categoryName !== null && categoryColor !== null) {

        var showCreateDialog by remember { mutableStateOf(false) }
        var showModifyDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }

        val textState = remember { mutableStateOf(TextFieldValue("")) }
        val colorState = remember { mutableStateOf(NeutralGray0) }
        val optionMenuId = remember { mutableStateOf<String>("") }

        val prepState by viewModel.prepList.collectAsState()
        val prepResultState by viewModel.prepResult.collectAsState()

        commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
            title = categoryName,
            containerColor = Color(hexToArgb(categoryColor)),
            navIconInfo = NavigationIconInfo.BACK,
            onClickNavIcon = { commonState.navController.popBackStack() },
            actionIconInfo = ActionIconInfo.ADD,
            onClickActionIcon = { showCreateDialog = true }
        )

        LaunchedEffect(Unit) {
            viewModel.getPrepList(categoryId)
        }

        KitchingManagerTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                when (prepState) {
                    is AppResult.Loading -> {
                        /* 인디케이터 */
                    }

                    is AppResult.Success -> {
                        val preps = (prepState as AppResult.Success).data
                        if (preps.isEmpty()) {
                            EmptyScreen("세부 프렙을 추가해주세요")
                        } else {
                            CategorySubDivisionScreen(
                                itemList = preps.map { item ->
                                    SubdivisionItemForScreen(
                                        id = item.prepId,
                                        name = item.prepName
                                    )
                                },
                                onCardOptionBtnClick = { itemId ->
                                    optionMenuId.value =
                                        if (optionMenuId.value == itemId) "" else itemId
                                },
                                optionMenuId = optionMenuId,
                                onClickModify = { itemId, itemName ->
                                    optionMenuId.value = itemId
                                    textState.value = TextFieldValue(itemName)
                                    showModifyDialog = true

                                },
                                onClickDelete = { itemId ->
                                    optionMenuId.value = itemId
                                    showDeleteDialog = true
                                }
                            )
                        }
                        if (showCreateDialog) {
                            BasicInputDialog(
                                title = "세부 프렙 목록 추가",
                                confirmText = "생성",
                                onClickConfirm = {
                                    viewModel.createPrep(
                                        categoryId = categoryId,
                                        name = textState.value.text
                                    )
                                    optionMenuId.value = ""
                                    showCreateDialog = false
                                    viewModel.getPrepList(categoryId)
                                },
                                cancelText = "취소",
                                onClickCancel = {
                                    viewModel.updatePrep(optionMenuId.value, textState.value.text)
                                    viewModel.getPrepList(categoryId)
                                    optionMenuId.value = ""
                                    showCreateDialog = false
                                },
                                textState = textState,
                                placeHolder = "세부 프렙을 입력해주세요"
                            )
                        }
                        if (showModifyDialog) {
                            BasicInputDialog(
                                title = "세부 프렙 목록 수정",
                                confirmText = "수정",
                                onClickConfirm = {
                                    viewModel.updatePrep(optionMenuId.value, textState.value.text)
                                    viewModel.getPrepList(categoryId)
                                    optionMenuId.value = ""
                                    showModifyDialog = false
                                },
                                cancelText = "취소",
                                onClickCancel = { showModifyDialog = false },
                                textState = textState,
                                placeHolder = "세부 프렙을 입력해주세요"
                            )
                        }
                        if (showDeleteDialog) {
                            BasicConfirmDialog(
                                message = "세부 프렙 목록을\n삭제하시겠습니까?",
                                confirmText = "삭제",
                                onClickConfirm = {
                                    viewModel.deletePrep(optionMenuId.value)
                                    viewModel.getPrepList(categoryId)
                                    optionMenuId.value = ""
                                    showDeleteDialog = false
                                },
                                cancelText = "취소",
                                onClickCancel = { showDeleteDialog = false }
                            )
                        }
                    }

                    is AppResult.Failure -> {
                        /* 실패 화면 넣고싶음 */
                    }
                }

            }
        }
    }
}