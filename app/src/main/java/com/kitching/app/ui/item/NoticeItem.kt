package com.kitching.app.ui.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kitching.app.ui.screen.other.notice.NoticeDTO
import com.kitching.app.ui.theme.Body1_m
import com.kitching.app.ui.theme.Caption1_R
import com.kitching.app.ui.theme.NeutralGray600
import com.kitching.app.ui.theme.NeutralGray800

//@Preview
@Composable
fun NoticeItem(
    notice: NoticeDTO
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = notice.title,
            style = Body1_m.copy(NeutralGray800),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.padding(end = 5.dp),
                text = notice.writerName,
                style = Caption1_R.copy(NeutralGray600),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.weight(1f),
                text = notice.date.toString(),
                style = Caption1_R.copy(NeutralGray600),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = notice.content,
            style = Caption1_R.copy(NeutralGray800),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}