package com.kitching.app.ui.screen.prep.subdivisionscreen


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.CategoryItem
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.PrepViewModel
import com.kitching.app.ui.screen.categoryscreen.CategorySubDivisionScreen
import com.kitching.app.ui.screen.categoryscreen.SubdivisionItemForScreen
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.util.hexToArgb
import com.kitching.domain.AppResult

/**
 * Prep detail screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param categoryItemForScreen 카테고리 아이템
 * @param viewModel
 */
@Composable
fun PrepDetailScreen(
    commonState: CommonState,
    categoryItemForScreen: CategoryItem,
    viewModel: PrepViewModel = viewModel(factory = viewModelFactory)
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val optionMenuId = remember { mutableStateOf<String>("") }

    val prepState by viewModel.prepList.collectAsStateWithLifecycle()
    val prepResultState by viewModel.prepResult.collectAsStateWithLifecycle()

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = categoryItemForScreen.categoryName,
        containerColor = Color(hexToArgb(categoryItemForScreen.categoryColor)),
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { showCreateDialog = true }
    )

    LaunchedEffect(Unit) {
        viewModel.getPrepList(categoryItemForScreen.categoryId)
    }

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = (prepState is AppResult.Loading || prepResultState is AppResult.Loading),
                successCondition = (prepState is AppResult.Success),
                failCondition = (prepState is AppResult.Failure),
                onRetryBtnClick = {}
            ) {
                val preps = (prepState as AppResult.Success).data
                if (preps.isEmpty()) {
                    EmptyScreen("프렙을 추가해주세요")
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
                        title = "프렙 추가",
                        confirmText = "생성",
                        onClickConfirm = {
                            viewModel.createPrep(
                                categoryId = categoryItemForScreen.categoryId,
                                name = textState.value.text
                            )
                            optionMenuId.value = ""
                            showCreateDialog = false
                            viewModel.getPrepList(categoryItemForScreen.categoryId)
                        },
                        cancelText = "취소",
                        onClickCancel = {
                            optionMenuId.value = ""
                            showCreateDialog = false
                        },
                        textState = textState,
                        placeHolder = "프렙명을 입력해주세요"
                    )
                }
                if (showModifyDialog) {
                    BasicInputDialog(
                        title = "프렙 수정",
                        confirmText = "수정",
                        onClickConfirm = {
                            viewModel.updatePrep(optionMenuId.value, textState.value.text)
                            viewModel.getPrepList(categoryItemForScreen.categoryId)
                            optionMenuId.value = ""
                            showModifyDialog = false
                        },
                        cancelText = "취소",
                        onClickCancel = { showModifyDialog = false },
                        textState = textState,
                        placeHolder = "프렙명을 입력해주세요"
                    )
                }
                if (showDeleteDialog) {
                    BasicConfirmDialog(
                        message = "프렙을 삭제하시겠습니까?",
                        confirmText = "삭제",
                        onClickConfirm = {
                            viewModel.deletePrep(optionMenuId.value)
                            viewModel.getPrepList(categoryItemForScreen.categoryId)
                            optionMenuId.value = ""
                            showDeleteDialog = false
                        },
                        cancelText = "취소",
                        onClickCancel = { showDeleteDialog = false }
                    )
                }
            }
        }
    }
}