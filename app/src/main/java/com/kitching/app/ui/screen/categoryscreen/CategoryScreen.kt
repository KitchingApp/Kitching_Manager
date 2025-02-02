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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.item.CategoryCardItem
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.defaultPadding

data class CategoryItemForScreen(val categoryId: String, val categoryName: String, val categoryColor: String)

@Composable
fun CategoryScreen(
    title: String,
    categoryList: List<CategoryItemForScreen>,
    onCardClick: (categoryId: String, categoryName: String, color: String) -> Unit,
    onCardOptionBtnClick: (Int) -> Unit,
    optionMenuIndex: MutableState<Int?>
    ) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(defaultPadding, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.fillMaxSize().wrapContentHeight(),
                text = "$title 카테고리",
                style = H2.copy(color = NeutralGray800)
            )
        }
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(categoryList) { index, category ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    CategoryCardItem(
                        cardText = category.categoryName,
                        cardColor = category.categoryColor,
                        onCardClick = { onCardClick(category.categoryId, category.categoryName, category.categoryColor) },
                        onOptionBtnClick = { onCardOptionBtnClick(index) },
                    )
                    if(optionMenuIndex.value == index) {
                        DropdownOptionMenu(
                            optionMenuIndex = optionMenuIndex,
                            onClickModify = { },
                            onClickDelete = { showDeleteDialog = true }
                        )
                    }
                    if(showDeleteDialog) {
                        BasicConfirmDialog(
                            message = "프렙 카테고리를 \n삭제하시겠습니까?",
                            confirmText = "삭제",
                            onClickConfirm = { },
                            cancelText = "취소",
                            onClickCancel = { showDeleteDialog = false }
                        )
                    }
                }
            }
        }
    }
}