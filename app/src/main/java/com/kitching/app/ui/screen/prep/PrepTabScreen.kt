package com.kitching.app.ui.screen.prep

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.navgraph.ScreenRouteDef
import com.kitching.app.ui.screen.categoryscreen.CategoryItemForScreen
import com.kitching.app.ui.screen.categoryscreen.CategoryScreen
import com.kitching.app.ui.screen.categoryscreen.EmptyScreen
import com.kitching.app.ui.screen.commondialog.ColorInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.ui.theme.NeutralGray0
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class PrepCategoryDTO(val categoryId: String, val categoryName: String, val color: String)

@Composable
fun PrepTabScreen(
    commonState: CommonState
) {
    val mockDataList = listOf<PrepCategoryDTO>(
        PrepCategoryDTO(
            categoryId = "YG9hVIsiIKvNQxNOutIG",
            categoryName = "핫",
            color = "#FFE1E1"
        ),
        PrepCategoryDTO(
            categoryId = "nXyjU7oMmHljfPgzfYF5",
            categoryName = "콜드",
            color = "#D6F6FF"
        ),
        PrepCategoryDTO(
            categoryId = "hvkhbRkK1gjoF5F1DP9F",
            categoryName = "프랩키친",
            color = "#EEEEEE"
        )
    )

    var showCreateDialog by remember { mutableStateOf(false) }

    val optionMenuIndex = remember { mutableStateOf<Int?>(null) }

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
                EmptyScreen("프렙 카테고리를 추가해주세요")
            } else {
                CategoryScreen(
                    title = "프렙",
                    categoryList = mockDataList.map { category ->
                        CategoryItemForScreen(
                            categoryId = category.categoryId,
                            categoryName = category.categoryName,
                            categoryColor = category.color
                        )
                    },
                    onCardClick = { categoryId, categoryName, categoryColor ->
                        val encodedColor = URLEncoder.encode(categoryColor, StandardCharsets.UTF_8.toString())
                        commonState.navController.navigate("${ScreenRouteDef.InnerContent.PrepDetail.routeName}/${categoryId}/${categoryName}/${encodedColor}")
                    },
                    onCardOptionBtnClick = { index ->
                        optionMenuIndex.value = if (optionMenuIndex.value == index) null else index
                    },
                    optionMenuIndex = optionMenuIndex
                )
                if(showCreateDialog) {
                    ColorInputDialog(
                        title = "프렙 카테고리 추가",
                        placeHolder = "카테고리 이름을 입력해주세요",
                        confirmText = "생성",
                        onClickConfirm = { },
                        cancelText = "취소",
                        onClickCancel = { showCreateDialog = false }
                    )
                }
            }
        }
    }
}