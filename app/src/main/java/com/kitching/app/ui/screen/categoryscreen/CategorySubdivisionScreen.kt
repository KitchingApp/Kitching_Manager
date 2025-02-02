package com.kitching.app.ui.screen.categoryscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.item.SubdivisionCardItem
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800
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
                        Box() {
                            DropdownMenu(
                                expanded = true,
                                onDismissRequest = { optionMenuIndex.value = null },
                                containerColor = NeutralGray0
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "수정", color = NeutralGray800) },
                                    onClick = { }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "삭제", color = NeutralGray800) },
                                    onClick = { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}