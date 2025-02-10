package com.kitching.app.ui.screen.prep

import android.util.Log
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
import com.kitching.app.common.KitchingApplication
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.PrepCategoryViewModel
import com.kitching.app.ui.screen.categoryscreen.CategoryItemForScreen
import com.kitching.app.ui.screen.categoryscreen.CategoryScreen
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.ColorInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.PreferencesDataStore
import com.kitching.app.util.hexToArgb
import com.kitching.app.util.toHex
import com.kitching.domain.AppResult
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Prep tab screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param viewModel
 */
@Composable
fun PrepTabScreen(
    commonState: CommonState,
    viewModel: PrepCategoryViewModel = viewModel(factory = viewModelFactory)
) {

    // 다이얼로그 상태
    var showCreateDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // input 상태
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val colorState = remember { mutableStateOf(NeutralGray0) }
    val optionMenuId = remember { mutableStateOf("") }

    var teamId by remember { mutableStateOf("") }
    // 프렙 카테고리 리스트 상태
    val prepCategoryState by viewModel.prepCategories.collectAsStateWithLifecycle()
    // 프렙 카테고리 생성, 수정, 삭제 상태
    val prepResultState by viewModel.prepCategoryResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore(KitchingApplication.getInstance()).getTeamId() ?: ""
        viewModel.getPrepCategory(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.scope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = {
            showCreateDialog = true
        },
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = (prepCategoryState is AppResult.Loading || prepResultState is AppResult.Loading),
                successCondition = (prepCategoryState is AppResult.Success),
                failCondition = (prepCategoryState is AppResult.Failure),
                failContent = {}
            ) {
                val categories = (prepCategoryState as AppResult.Success).data
                if (categories.isEmpty()) {
                    EmptyScreen("프렙 카테고리를 추가해주세요")
                } else {
                    CategoryScreen(
                        title = "프렙",
                        categoryList = categories.map { category ->
                            CategoryItemForScreen(
                                categoryId = category.categoryId,
                                categoryName = category.categoryName,
                                categoryColor = category.color
                            )
                        },
                        onCardClick = { categoryId, categoryName, categoryColor ->
                            val encodedColor = URLEncoder.encode(
                                categoryColor,
                                StandardCharsets.UTF_8.toString()
                            )
                            commonState.navController.navigate("${ScreenRouteDef.InnerContent.PrepDetail.routeName}/${categoryId}/${categoryName}/${encodedColor}")
                        },
                        onCardOptionBtnClick = { categoryId ->
                            optionMenuId.value =
                                if (optionMenuId.value == categoryId) "" else categoryId
                        },
                        optionMenuId = optionMenuId,
                        onClickModify = { categoryId, categoryName, categoryColor ->
                            optionMenuId.value = categoryId
                            textState.value = TextFieldValue(categoryName)
                            colorState.value = Color(hexToArgb(categoryColor))
                            showModifyDialog = true
                        },
                        onClickDelete = { categoryId ->
                            optionMenuId.value = categoryId
                            showDeleteDialog = true
                        }
                    )
                }
                if (showCreateDialog) {
                    // state 초기화
                    textState.value = TextFieldValue("")
                    colorState.value = NeutralGray0

                    ColorInputDialog(
                        title = "프렙 카테고리 추가",
                        placeHolder = "카테고리명을 입력해주세요",
                        textState = textState,
                        colorState = colorState,
                        confirmText = "생성",
                        onClickConfirm = {
                            viewModel.createPrepCategory(
                                teamId = teamId,
                                categoryName = textState.value.text,
                                color = colorState.value.toHex()
                            )
                            showCreateDialog = false
                            viewModel.getPrepCategory(teamId)
                        },
                        cancelText = "취소",
                        onClickCancel = { showCreateDialog = false }
                    )
                }
                if (showModifyDialog) {
                    ColorInputDialog(
                        title = "프렙 카테고리 수정",
                        placeHolder = "카테고리명을 입력해주세요",
                        textState = textState,
                        colorState = colorState,
                        confirmText = "수정",
                        onClickConfirm = {
                            viewModel.updatePrepCategory(
                                categoryId = optionMenuId.value,
                                categoryName = textState.value.text,
                                color = colorState.value.toHex()
                            )
                            optionMenuId.value = ""
                            showModifyDialog = false
                            viewModel.getPrepCategory(teamId)
                        },
                        cancelText = "취소",
                        onClickCancel = {
                            showModifyDialog = false
                        }
                    )
                }
                if (showDeleteDialog) {
                    BasicConfirmDialog(
                        message = "프렙 카테고리를 삭제하시겠습니까?",
                        confirmText = "삭제",
                        onClickConfirm = {
                            viewModel.deletePrepCategory(optionMenuId.value)
                            optionMenuId.value = ""
                            showDeleteDialog = false
                            viewModel.getPrepCategory(teamId)
                        },
                        cancelText = "취소",
                        onClickCancel = {
                            showDeleteDialog = false
                        }
                    )
                }
            }
        }
    }
}