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

@Composable
fun DropdownOptionMenu(
    optionMenuIndex: MutableState<Int?>
) {
    Box() {
        DropdownMenu(
            modifier = Modifier.padding(0.dp).crop(vertical = 8.dp),
            shape = RectangleShape,
            expanded = true,
            onDismissRequest = { optionMenuIndex.value = null },
            containerColor = NeutralGray0
        ) {
            DropdownMenuItem(
                modifier = Modifier.height(40.dp).background(PrimaryGreen300),
                text = { Text(text = "수정", style = H3.copy(color = NeutralGray0)) },
                onClick = {  }
            )
            DropdownMenuItem(
                modifier = Modifier.height(40.dp).border(1.dp, NeutralGray500),
                text = { Text(text = "삭제", style = H3.copy(color = NeutralGray800)) },
                onClick = {  }
            )
        }
    }
}