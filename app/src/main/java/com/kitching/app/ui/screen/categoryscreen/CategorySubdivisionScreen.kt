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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.item.SubdivisionCardItem
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
                    SubdivisionCardItem (
                        cardText = item.name,
                        onOptionBtnClick = { onCardOptionBtnClick(index) },
                    )
                    if (optionMenuIndex.value == index) {
                        DropdownOptionMenu(optionMenuIndex)
                    }
                }
            }
        }
    }
}