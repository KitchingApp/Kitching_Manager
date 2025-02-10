package com.kitching.app.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.H5
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300
import com.kitching.app.ui.theme.PrimaryGreen50
import com.kitching.app.ui.theme.SecondaryLightGreen100
import com.kitching.app.ui.theme.defaultPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputItem(
    title: String,
    timePickerState: TimePickerState
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(44.dp)
                .background(SecondaryLightGreen100),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = H5.copy(color = NeutralGray800),
                textAlign = TextAlign.Center
            )
        }
        TimeInput(
            modifier = Modifier.padding(top = defaultPadding),
            state = timePickerState,
            colors = TimePickerColors(
                // TimePicker에서 사용하는 컬러
                clockDialColor = NeutralGray800,
                clockDialSelectedContentColor = NeutralGray800,
                clockDialUnselectedContentColor = NeutralGray800,
                selectorColor = NeutralGray800,
                containerColor = NeutralGray800,
                // TimePicker에서 사용하는 컬러

                periodSelectorBorderColor = NeutralGray800,
                periodSelectorSelectedContainerColor = PrimaryGreen50,
                periodSelectorUnselectedContainerColor = Color.Transparent,
                periodSelectorSelectedContentColor = PrimaryGreen300,
                periodSelectorUnselectedContentColor = NeutralGray800,
                timeSelectorSelectedContainerColor = PrimaryGreen50,
                timeSelectorUnselectedContainerColor = Color.Transparent,
                timeSelectorSelectedContentColor = PrimaryGreen300,
                timeSelectorUnselectedContentColor = NeutralGray800
            )
        )
    }
}