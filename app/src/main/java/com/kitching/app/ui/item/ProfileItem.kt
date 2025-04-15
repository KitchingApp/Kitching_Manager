package com.kitching.app.ui.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.common.CoilImageRequest
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.H2
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.util.dropShadow

@Composable
fun ProfileSection(imageSource: String, name: String, role: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier
            .size(80.dp)
            .background(Color.White, shape = CircleShape)
            .dropShadow(shape = CircleShape, offsetY = 2.dp, spread = 2.dp)
        ) {
            AsyncImage(
                model = CoilImageRequest.getImageRequest(imageSource),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = role, style = Caption1_m, color = NeutralGray800)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = name, style = H2)
        }
    }
}