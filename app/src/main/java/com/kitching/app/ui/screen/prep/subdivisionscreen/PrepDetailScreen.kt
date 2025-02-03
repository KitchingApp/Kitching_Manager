package com.kitching.app.ui.screen.prep.subdivisionscreen

import android.util.Log
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
import com.kitching.app.ui.screen.categoryscreen.EmptyScreen
import com.kitching.app.ui.screen.categoryscreen.SubdivisionItemForScreen
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.theme.KitchingManagerTheme
import com.kitching.app.util.hexToArgb

data class PrepDTO(val categoryId: String, val prepId: String, val prepName: String)

@Composable
fun PrepDetailScreen(
    commonState: CommonState,
    categoryId: String?,
    categoryName: String?,
    categoryColor: String?,
) {
    if(categoryId !== null && categoryName !== null && categoryColor !== null) {
        val mockDataList = listOf(
            PrepDTO(
                categoryId = "nXyjU7oMmHljfPgzfYF5",
                prepId = "3WXkGNFkgIzPMNdgoNe3",
                prepName = "루꼴라페스토"
            ),
            PrepDTO(
                categoryId = "hvkhbRkK1gjoF5F1DP9F",
                prepId = "5SvdEkZ4laFefnZKLGmm",
                prepName = "라구소스"
            ),
            PrepDTO(
                categoryId = "hvkhbRkK1gjoF5F1DP9F",
                prepId = "Au6CDdirkbbztn3uvI8l",
                prepName = "양지손질"
            ),
            PrepDTO(
                categoryId = "nXyjU7oMmHljfPgzfYF5",
                prepId = "EPQwttZ91FGztTRz8uI3",
                prepName = "스콘크림"
            ),
            PrepDTO(
                categoryId = "nXyjU7oMmHljfPgzfYF5",
                prepId = "KSs4w3fiS8YO2kR5HTOy",
                prepName = "블루베리콤포트"
            ),
            PrepDTO(
                categoryId = "hvkhbRkK1gjoF5F1DP9F",
                prepId = "Mtev1AKjwmZITVeyxi5D",
                prepName = "샥슈카"
            ),
//            PrepDTO(
//                categoryId = "YG9hVIsiIKvNQxNOutIG",
//                prepId = "cy2atGrRm9XkL0qeRWI3",
//                prepName = "양송이소테"
//            ),
//            PrepDTO(
//                categoryId = "YG9hVIsiIKvNQxNOutIG",
//                prepId = "e057sAzVOazZdmWC25cQ",
//                prepName = "수란"
//            ),
//            PrepDTO(
//                categoryId = "YG9hVIsiIKvNQxNOutIG",
//                prepId = "yTGHbejrJwqAH3ILMeKc",
//                prepName = "홀랜다이즈소스"
//            )
        ).filter { it.categoryId == categoryId }

        var showCreateDialog by remember { mutableStateOf(false) }

        val textState = remember { mutableStateOf(TextFieldValue("")) }

        val optionMenuIndex = remember { mutableStateOf<Int?>(null) }

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
                                id = item.prepId,
                                name = item.prepName
                            )
                        },
                        onCardOptionBtnClick = { index ->
                            optionMenuIndex.value = if (optionMenuIndex.value == index) null else index
                        },
                        optionMenuIndex = optionMenuIndex,
                        categoryId = categoryId
                    )
                }
                if(showCreateDialog) {
                    BasicInputDialog(
                        title = "세부 프렙 목록 추가",
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