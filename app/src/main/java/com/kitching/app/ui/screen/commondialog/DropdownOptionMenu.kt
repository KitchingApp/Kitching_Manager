package com.kitching.app.ui.screen.commondialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.NeutralGray200
import com.kitching.app.ui.theme.NeutralGray50
import com.kitching.app.ui.theme.NeutralGray600
import com.kitching.app.ui.theme.NeutralGray800

/**
 * Dropdown option menu: 수정, 삭제가 있는 드롭다운
 *
 * @param onDismissRequest 옵션메뉴 닫을 시 액션
 * @param onClickModify 수정 버튼 클릭 시 액션
 * @param onClickDelete 삭제 버튼 클릭 시 액션
 */
@Composable
fun DropdownOptionMenu(
    onDismissRequest: () -> Unit,
    onClickModify: () -> Unit,
    onClickDelete: () -> Unit
) {
    Box {
        DropdownMenu(
            expanded = true,
            onDismissRequest = onDismissRequest,
            containerColor = NeutralGray50
        ) {
            DropdownMenuItem(
                modifier = Modifier.width(200.dp).height(48.dp).padding(20.dp, 10.dp),
                contentPadding = PaddingValues(0.dp),
                text = { Text(text = stringResource(R.string.button_modify), style = Body1_m.copy(color = NeutralGray800)) },
                trailingIcon = {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(NeutralGray600),
                        model = R.raw.icon_edit,
                        contentDescription = null
                    )
                },
                onClick = { onClickModify() }
            )
            HorizontalDivider(
                color = NeutralGray200
            )
            DropdownMenuItem(
                modifier = Modifier.width(200.dp).height(48.dp).padding(20.dp, 10.dp),
                contentPadding = PaddingValues(0.dp),
                text = { Text(text = stringResource(R.string.button_delete), style = Body1_m.copy(color = NeutralGray800)) },
                trailingIcon = {
                    AsyncImage(
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(NeutralGray600),
                        model = R.raw.icon_trash,
                        contentDescription = null
                    )
                },
                onClick = { onClickDelete() }
            )
        }
    }
}