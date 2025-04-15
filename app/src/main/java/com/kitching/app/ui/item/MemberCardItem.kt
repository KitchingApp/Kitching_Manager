package com.kitching.app.ui.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.R
import com.kitching.app.common.CoilImageRequest
import com.kitching.app.navgraph.MemberItem
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800

@Composable
fun MemberCardItem(
    member: MemberItem,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(320.dp)
            .height(84.dp)
            .padding(20.dp, 10.dp)
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            contentColor = NeutralGray800,
            disabledContentColor = NeutralGray800
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(20.dp)),
                model = CoilImageRequest.getImageRequest(member.userImage),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.width(108.dp),
                    text = member.userName,
                    style = Body1_m,
                    textAlign = TextAlign.Center
                )
                if(member.staffLevelName == "") {
                    Text(
                        modifier = Modifier.width(108.dp),
                        text = stringResource(R.string.no_staffLevel_message),
                        style = Body1_m.copy(color = NeutralGray300),
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        modifier = Modifier.width(108.dp),
                        text = member.staffLevelName,
                        style = Body1_m,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}