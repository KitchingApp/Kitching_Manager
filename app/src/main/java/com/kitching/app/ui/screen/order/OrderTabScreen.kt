package com.kitching.app.ui.screen.order

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
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.OrderCategoryViewModel
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
import com.kitching.domain.AppResult
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Order tab screen
 *
 * @param commonState 네비게이션 컨트롤러, 앱바 상태, 코루틴 스코프를 갖는 data class
 * @param viewModel
 */
@Composable
fun OrderTabScreen(
    commonState: CommonState,
    viewModel: OrderCategoryViewModel = viewModel(factory = viewModelFactory)
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
    val orderCategoryState by viewModel.orderCategories.collectAsStateWithLifecycle()
    val orderCategoryResultState by viewModel.orderCategoryResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        teamId = PreferencesDataStore().getTeamId()
        viewModel.getOrderCategory(teamId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "Kitching",
        containerColor = NeutralGray0,
        navIconInfo = NavigationIconInfo.DRAWER,
        onClickNavIcon = {
            if (commonState.topAppBarState.value.drawerState.isOpen) {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.close() }
            } else {
                commonState.coroutineScope.launch { commonState.topAppBarState.value.drawerState.open() }
            }
        },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { showCreateDialog = true },
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = (orderCategoryState is AppResult.Loading || orderCategoryResultState is AppResult.Loading),
                successCondition = (orderCategoryState is AppResult.Success),
                failCondition = (orderCategoryState is AppResult.Failure || orderCategoryResultState is AppResult.Failure),
                onRetryBtnClick = {}
            ) {
                val categories = (orderCategoryState as AppResult.Success).data
                if (categories.isEmpty()) {
                    EmptyScreen("발주 카테고리를 추가해주세요")
                } else {
                    CategoryScreen(
                        title = "발주",
                        categoryList = categories.map { category ->
                            CategoryItemForScreen(
                                categoryId = category.categoryId,
                                categoryName = category.categoryName,
                                categoryColor = category.color
                            )
                        },
                        onCardClick = { categoryItemForScreen ->
                            val encodedColor = URLEncoder.encode(
                                categoryItemForScreen.categoryColor,
                                StandardCharsets.UTF_8.toString()
                            )
                            commonState.navController.navigate("${ScreenRouteDef.InnerContent.OrderDetail.routeName}/${categoryItemForScreen.categoryId}/${categoryItemForScreen.categoryName}/${encodedColor}")
                        },
                        onCardOptionBtnClick = { categoryId ->
                            optionMenuId.value =
                                if (optionMenuId.value == categoryId) "" else categoryId
                        },
                        optionMenuId = optionMenuId,
                        onClickModify = { categoryItemForScreen ->
                            optionMenuId.value = categoryItemForScreen.categoryId
                            textState.value = TextFieldValue(categoryItemForScreen.categoryName)
                            colorState.value = Color(hexToArgb(categoryItemForScreen.categoryColor))
                            showModifyDialog = true
                        },
                        onClickDelete = { categoryId ->
                            optionMenuId.value = categoryId
                            showDeleteDialog = true
                        },
                    )
                    if (showCreateDialog) {
                        // state 초기화
                        textState.value = TextFieldValue("")
                        colorState.value = NeutralGray0

                        ColorInputDialog(
                            title = "발주 카테고리 추가",
                            placeHolder = "카테고리명을 입력해주세요",
                            textState = textState,
                            colorState = colorState,
                            confirmText = "생성",
                            onClickConfirm = { },
                            cancelText = "취소",
                            onClickCancel = { showCreateDialog = false }
                        )
                    }
                    if (showModifyDialog) {
                        ColorInputDialog(
                            title = "발주 카테고리 수정",
                            placeHolder = "카테고리명을 입력해주세요",
                            textState = textState,
                            colorState = colorState,
                            confirmText = "수정",
                            onClickConfirm = { },
                            cancelText = "취소",
                            onClickCancel = { showModifyDialog = false }
                        )
                    }
                    if (showDeleteDialog) {
                        BasicConfirmDialog(
                            message = "발주 카테고리를 삭제하시겠습니까?",
                            confirmText = "삭제",
                            onClickConfirm = { },
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
}