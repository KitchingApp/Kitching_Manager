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
import com.kitching.app.navgraph.CategoryItemForScreen
import com.kitching.app.ui.factory.viewModelFactory
import com.kitching.app.ui.model.OrderViewModel
import com.kitching.app.ui.screen.categoryscreen.CategorySubDivisionScreen
import com.kitching.app.ui.screen.categoryscreen.SubdivisionItemForScreen
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.common.ResultConditionScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.util.hexToArgb
import com.kitching.domain.AppResult

@Composable
fun OrderDetailScreen(
    commonState: CommonState,
    categoryItemForScreen: CategoryItemForScreen,
    viewModel: OrderViewModel = viewModel(factory = viewModelFactory)
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val optionMenuId = remember { mutableStateOf<String>("") }

    val orderState by viewModel.orders.collectAsStateWithLifecycle()
    val orderResultState by viewModel.orderResult.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getOrderList(categoryItemForScreen.categoryId)
    }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = categoryItemForScreen.categoryName,
        containerColor = Color(hexToArgb(categoryItemForScreen.categoryColor)),
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = { commonState.navController.popBackStack() },
        actionIconInfo = ActionIconInfo.ADD,
        onClickActionIcon = { showCreateDialog = true }
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ResultConditionScreen(
                loadingCondition = (orderState is AppResult.Loading || orderResultState is AppResult.Loading),
                successCondition = orderState is AppResult.Success,
                failCondition = (orderState is AppResult.Failure || orderResultState is AppResult.Failure),
                onRetryBtnClick = {}
            ) {
                val orders = (orderState as AppResult.Success).data
                if (orders.isEmpty()) {
                    EmptyScreen("발주 물품을 추가해주세요")
                } else {
                    CategorySubDivisionScreen(
                        itemList = orders.map { item ->
                            SubdivisionItemForScreen(
                                id = item.orderId,
                                name = item.orderName
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
                        title = "발주 물품 추가",
                        confirmText = "생성",
                        onClickConfirm = {
                            viewModel.createOrder(
                                categoryId = categoryItemForScreen.categoryId,
                                orderName = textState.value.text
                            )
                            optionMenuId.value = ""
                            showCreateDialog = false
                            viewModel.getOrderList(categoryItemForScreen.categoryId)

                        },
                        cancelText = "취소",
                        onClickCancel = {
                            optionMenuId.value = ""
                            showCreateDialog = false
                        },
                        textState = textState,
                        placeHolder = "발주 물품을 입력해주세요"
                    )
                }
                if (showModifyDialog) {
                    BasicInputDialog(
                        title = "발주 물품 수정",
                        confirmText = "수정",
                        onClickConfirm = {
                            viewModel.updateOrder(optionMenuId.value, textState.value.text)
                            viewModel.getOrderList(categoryItemForScreen.categoryId)
                            optionMenuId.value = ""
                            showModifyDialog = false
                        },
                        cancelText = "취소",
                        onClickCancel = { showModifyDialog = false },
                        textState = textState,
                        placeHolder = "발주 물품을 입력해주세요"
                    )
                }
                if (showDeleteDialog) {
                    BasicConfirmDialog(
                        message = "발주 물품을 삭제하시겠습니까?",
                        confirmText = "삭제",
                        onClickConfirm = {
                            viewModel.deleteOrder(optionMenuId.value)
                            viewModel.getOrderList(categoryItemForScreen.categoryId)
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