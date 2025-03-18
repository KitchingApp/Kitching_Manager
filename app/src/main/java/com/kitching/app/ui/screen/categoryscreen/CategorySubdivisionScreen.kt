package com.kitching.app.ui.screen.categoryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.item.SubdivisionCardItem
import com.kitching.app.ui.screen.commondialog.DropdownOptionMenu
import com.kitching.app.ui.theme.defaultPadding

data class SubdivisionItemForScreen(val id: String, val name: String)

@Composable
fun CategorySubDivisionScreen(
    itemList: List<SubdivisionItemForScreen>,
    onCardOptionBtnClick: (itemId: String) -> Unit,
    optionMenuId: MutableState<String>,
    onClickModify: (itemId: String, itemName: String) -> Unit,
    onClickDelete: (itemId: String) -> Unit
) {
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
            itemList.forEach { item ->
                item(key = item.id) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        SubdivisionCardItem(
                            cardText = item.name,
                            onOptionBtnClick = { onCardOptionBtnClick(item.id) },
                        )
                        if (optionMenuId.value == item.id) {
                            DropdownOptionMenu(
                                onDismissRequest = { optionMenuId.value = "" },
                                onClickModify = { onClickModify(item.id, item.name) },
                                onClickDelete = { onClickDelete(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}