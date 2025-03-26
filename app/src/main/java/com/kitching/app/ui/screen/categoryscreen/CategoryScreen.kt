package com.kitching.app.ui.screen.categoryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.navgraph.CategoryItemForScreen
import com.kitching.app.ui.item.CategoryCardItem
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.defaultPadding

/**
 * Category screen
 *
 * @param title 카테고리 이름
 * @param categoryList [CategoryItemForScreen]으로 변환된 카테고리 목록
 * @param onCardClick 카드 클릭 시 액션
 * @param onCardOptionBtnClick 옵션버튼 클릭 시 액션
 * @param optionMenuId 선택된 옵션버튼의 아이템 ID(선택하지 않을 시 "")
 * @param onClickModify 옵션메뉴의 수정 클릭 시
 * @param onClickDelete 옵션메뉴의 삭제 클릭 시
 */
@Composable
fun CategoryScreen(
    title: String,
    categoryList: List<CategoryItemForScreen>,
    onCardClick: (categoryItemForScreen: CategoryItemForScreen) -> Unit,
    onCardOptionBtnClick: (categoryId: String) -> Unit,
    optionMenuId: MutableState<String>,
    onClickModify: (categoryItemForScreen: CategoryItemForScreen) -> Unit,
    onClickDelete: (categoryId: String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight(),
                text = stringResource(R.string.category_detail_screen_title, title),
                style = H2.copy(color = NeutralGray800)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(categoryList) { _, category ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    CategoryCardItem(
                        cardText = category.categoryName,
                        cardColor = category.categoryColor,
                        onCardClick = { onCardClick(category) },
                        onOptionBtnClick = { onCardOptionBtnClick(category.categoryId) },
                    )
                    if (optionMenuId.value == category.categoryId) {
                        DropdownOptionMenu(
                            onDismissRequest = { optionMenuId.value = "" },
                            onClickModify = {
                                onClickModify(category)
                            },
                            onClickDelete = { onClickDelete(category.categoryId) }
                        )
                    }
                }
            }
        }
    }
}