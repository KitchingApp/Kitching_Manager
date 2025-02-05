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
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.screen.categoryscreen.CategoryItemForScreen
import com.kitching.app.ui.screen.categoryscreen.CategoryScreen
import com.kitching.app.ui.screen.categoryscreen.EmptyScreen
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.ColorInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.util.hexToArgb
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class OrderCategoryDTO(val categoryId: String, val categoryName: String, val color: String)

@Composable
fun OrderTabScreen(commonState: CommonState) {
    val mockDataList = listOf<OrderCategoryDTO>(
        OrderCategoryDTO(
            categoryId = "Bjk5Geux2zFETtzmnL6T",
            categoryName = "육가공",
            color = "#FFE1E1"
        ),
        OrderCategoryDTO(
            categoryId = "3hEXf6yWbveFBAGKhWXO",
            categoryName = "농수산물",
            color = "#D6F6FF"
        ),
        OrderCategoryDTO(
            categoryId = "HUmhEwUsHUYfp0uhSDrO",
            categoryName = "공산품",
            color = "#EEEEEE"
        )
    )

    // 다이얼로그 상태
    var showCreateDialog by remember { mutableStateOf(false) }
    var showModifyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // input 상태
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val colorState = remember { mutableStateOf(NeutralGray0) }
    val optionMenuId = remember { mutableStateOf("") }

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
            if(mockDataList.isEmpty()) {
                // 리스트가 비어있을때
                EmptyScreen("발주 카테고리를 추가해주세요")
            } else {
                CategoryScreen(
                    title = "발주",
                    categoryList = mockDataList.map { category ->
                        CategoryItemForScreen(
                            categoryId = category.categoryId,
                            categoryName = category.categoryName,
                            categoryColor = category.color
                        )
                    },
                    onCardClick = { categoryId, categoryName, categoryColor ->
                        val encodedColor =
                            URLEncoder.encode(categoryColor, StandardCharsets.UTF_8.toString())
                        commonState.navController.navigate("${ScreenRouteDef.InnerContent.OrderDetail.routeName}/${categoryId}/${categoryName}/${encodedColor}")
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
                    },
                )
                if(showCreateDialog) {
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
                if(showModifyDialog) {
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
                        message = "발주 카테고리를 \n삭제하시겠습니까?",
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