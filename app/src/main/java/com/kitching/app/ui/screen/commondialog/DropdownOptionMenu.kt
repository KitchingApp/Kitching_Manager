package com.kitching.app.ui.screen.commondialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.H3
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray500
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.util.crop

/**
 * Dropdown option menu: 수정, 삭제가 있는 드롭다운
 *
 * @param optionMenuId 선택된 옵션버튼의 아이템 ID(선택하지 않을 시 "")
 * @param onClickModify 수정 버튼 클릭 시 액션
 * @param onClickDelete 삭제 버튼 클릭 시 액션
 */
@Composable
fun DropdownOptionMenu(
    optionMenuId: MutableState<String>,
    onClickModify: () -> Unit,
    onClickDelete: () -> Unit
) {
    Box() {
        DropdownMenu(
            modifier = Modifier.padding(0.dp).crop(vertical = 8.dp),
            shape = RectangleShape,
            expanded = true,
            onDismissRequest = { optionMenuId.value = "" },
            containerColor = NeutralGray0
        ) {
            DropdownMenuItem(
                modifier = Modifier.height(40.dp).background(PrimaryGreen300),
                text = { Text(text = "수정", style = H3.copy(color = NeutralGray0)) },
                onClick = { onClickModify() }
            )
            DropdownMenuItem(
                modifier = Modifier.height(40.dp).border(1.dp, NeutralGray500),
                text = { Text(text = "삭제", style = H3.copy(color = NeutralGray800)) },
                onClick = { onClickDelete() }
            )
        }
    }
}