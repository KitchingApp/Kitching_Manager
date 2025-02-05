package com.kitching.app.ui.screen.order

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.ui.screen.categoryscreen.CategorySubDivisionScreen
import com.kitching.app.ui.screen.common.EmptyScreen
import com.kitching.app.ui.screen.categoryscreen.SubdivisionItemForScreen
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.hexToArgb

data class OrderDTO(val categoryId: String, val orderId: String, val orderName: String)

@Composable
fun OrderDetailScreen(
    commonState: CommonState,
    categoryId: String?,
    categoryName: String?,
    categoryColor: String?,
) {
    if(categoryId !== null && categoryName !== null && categoryColor !== null) {
        val mockDataList = listOf<OrderDTO>(
        ).filter { it.categoryId == categoryId }

        var showCreateDialog by remember { mutableStateOf(false) }
        var showModifyDialog by remember { mutableStateOf(false) }
        var showDeleteDialog by remember { mutableStateOf(false) }

        val textState = remember { mutableStateOf(TextFieldValue("")) }
        val colorState = remember { mutableStateOf(NeutralGray0) }
        val optionMenuId = remember { mutableStateOf<String>("") }

        commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
            title = categoryName,
            containerColor = Color(hexToArgb(categoryColor)),
            navIconInfo = NavigationIconInfo.BACK,
            onClickNavIcon = { commonState.navController.popBackStack() },
            actionIconInfo = ActionIconInfo.ADD,
            onClickActionIcon = { showCreateDialog = true }
        )

        KitchingManagerTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                if(mockDataList.isEmpty()) {
                    EmptyScreen("발주 카테고리를 추가해주세요")
                } else {
                    CategorySubDivisionScreen(
                        itemList = mockDataList.map { item ->
                            SubdivisionItemForScreen(
                                id = item.orderId,
                                name = item.orderName
                            )
                        },
                        onCardOptionBtnClick = { itemId ->
                            optionMenuId.value = if (optionMenuId.value == itemId) "" else itemId
                        },
                        optionMenuId = optionMenuId,
                        onClickModify = {itemId, itemName ->  },
                        onClickDelete = {}
                    )
                }
                if(showCreateDialog) {
                    BasicInputDialog(
                        title = "세부 발주 목록 추가",
                        confirmText = "생성",
                        onClickConfirm = { },
                        cancelText = "취소",
                        onClickCancel = { showCreateDialog = false },
                        textState = textState,
                        placeHolder = "세부 프렙을 입력해주세요"
                    )
                }
            }
        }
    } else {
        return // 여기 뭐해줘야되지?
    }

}