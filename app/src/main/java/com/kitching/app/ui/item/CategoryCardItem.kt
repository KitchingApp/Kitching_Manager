package com.kitching.app.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray600
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.ShadowColor
import com.kitching.app.util.dropShadow
import com.kitching.app.util.hexToArgb

//@Preview
@Composable
fun CategoryCardItem(
    cardText: String,
    cardColor: String,
    onCardClick: () -> Unit,
    onOptionBtnClick: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(60.dp)
            .dropShadow(
            shape = RoundedCornerShape(8.dp),
            color = ShadowColor,
            blur = 2.dp,
            offsetX = 0.dp,
            offsetY = 2.dp,
            spread = 0.dp
        ).clickable { onCardClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardColors(
            containerColor = Color(hexToArgb(cardColor)),
            contentColor = NeutralGray800,
            disabledContainerColor = Color(hexToArgb(cardColor)),
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = H3_m,
                text = cardText
            )
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { onOptionBtnClick() }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_kebab_menu),
                    tint = NeutralGray600,
                    contentDescription = ""
                )
            }
        }
    }
}