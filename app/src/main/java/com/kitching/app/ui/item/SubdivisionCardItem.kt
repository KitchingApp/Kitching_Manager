package com.kitching.app.ui.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kitching.app.R
import com.kitching.app.ui.theme.H3_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray600
import com.kitching.app.ui.theme.NeutralGray800

/**
 * Subdivision card item
 *
 * @param cardText 카테고리 속 세부 아이템 이름
 * @param onOptionBtnClick 옵션버튼 클릭 시 액션
 */
@Composable
fun SubdivisionCardItem(
    cardText: String,
    onOptionBtnClick: () -> Unit,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(8.dp),
        border =  BorderStroke(1.dp, NeutralGray300),
        colors = CardColors(
            containerColor = NeutralGray0,
            contentColor = NeutralGray800,
            disabledContainerColor = NeutralGray0,
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
                    contentDescription = null
                )
            }
        }
    }
}