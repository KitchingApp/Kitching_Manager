package com.kitching.app.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun OtherButtonItem(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        border = BorderStroke(2.dp, PrimaryGreen300),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = PrimaryGreen300)
    ) {
        Text(text = label, style = H3_m, color = NeutralGray800)
    }
}