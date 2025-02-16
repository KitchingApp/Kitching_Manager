package com.kitching.app.ui.screen.commondialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray50
import com.kitching.app.ui.theme.NeutralGray800

/**
 * Recipe create option menu 직접 입력해 레시피를 생성하거나 엑셀 파일로 레시피를 생성할 수 있는 드롭다운
 *
 * @param onDismissRequest 옵션메뉴 닫을 시 액션
 * @param onClickUseDevice 직접 입력 레시피 생성 선택
 * @param onClickUseExcelFile 엑셀 파일로 불러오기 선택
 */
@Composable
fun RecipeCreateOptionMenu(
    onDismissRequest: () -> Unit,
    onClickUseDevice: () -> Unit,
    onClickUseExcelFile: () -> Unit
) {
    Box() {
        DropdownMenu(
            expanded = true,
            onDismissRequest = onDismissRequest,
            containerColor = NeutralGray50
        ) {
            DropdownMenuItem(
                modifier = Modifier.width(200.dp).height(48.dp).padding(20.dp, 10.dp),
                contentPadding = PaddingValues(0.dp),
                text = { Text(text = "직접 입력하기", style = Body1_m.copy(color = NeutralGray800)) },
                onClick = { onClickUseDevice() }
            )
            HorizontalDivider(
                color = NeutralGray200
            )
            DropdownMenuItem(
                modifier = Modifier.width(200.dp).height(48.dp).padding(20.dp, 10.dp),
                contentPadding = PaddingValues(0.dp),
                text = { Text(text = "엑셀로 불러오기", style = Body1_m.copy(color = NeutralGray800)) },
                onClick = { onClickUseExcelFile() }
            )
        }
    }
}