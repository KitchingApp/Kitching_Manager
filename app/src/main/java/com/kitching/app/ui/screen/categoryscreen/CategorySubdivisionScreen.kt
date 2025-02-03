package com.kitching.app.ui.screen.categoryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.item.SubdivisionCardItem
import com.kitching.app.ui.screen.commondialog.BasicConfirmDialog
import com.kitching.app.ui.screen.commondialog.BasicInputDialog
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.defaultPadding

data class SubdivisionItemForScreen(val id: String, val name: String)

@Composable
fun CategorySubDivisionScreen(
    categoryId: String,
    itemList: List<SubdivisionItemForScreen>,
    onCardOptionBtnClick: (Int) -> Unit,
    optionMenuIndex: MutableState<Int?>
) {
    var showModifyDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var deleteItemId by remember { mutableStateOf("") }
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding, defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            itemsIndexed(itemList) { index, item ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    SubdivisionCardItem(
                        cardText = item.name,
                        onOptionBtnClick = { onCardOptionBtnClick(index) },
                    )
                    if (optionMenuIndex.value == index) {
                        DropdownOptionMenu(
                            optionMenuIndex,
                            onClickModify = {
                                textState.value = TextFieldValue(item.name)
                                showModifyDialog = true
                            },
                            onClickDelete = {
                                deleteItemId = item.id
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
        if (showModifyDialog) {
            BasicInputDialog(
                title = "세부 프렙 목록 수정",
                confirmText = "생성",
                onClickConfirm = { },
                cancelText = "취소",
                onClickCancel = { showModifyDialog = false },
                textState = textState,
                placeHolder = "세부 프렙을 입력해주세요"
            )
        }
        if (showDeleteDialog) {
            BasicConfirmDialog(
                message = "세부 프렙 목록을\n삭제하시겠습니까?",
                confirmText = "삭제",
                onClickConfirm = { },
                cancelText = "취소",
                onClickCancel = { showDeleteDialog = false }
            )
        }
    }
}