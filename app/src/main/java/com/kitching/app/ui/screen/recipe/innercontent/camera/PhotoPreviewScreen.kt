package com.kitching.app.ui.screen.recipe.innercontent.camera

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.ui.theme.Caption1_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray300
import com.kitching.app.ui.theme.NeutralGray800
import com.kitching.app.ui.theme.PrimaryGreen300

@Composable
fun PhotoPreviewScreen(
    bitmap: Bitmap,
    onConfirm: () -> Unit,
    onRetake: () -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(NeutralGray0),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = bitmap,
            contentDescription = "Captured Image"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // 다시찍기 버튼
            Button(
                onClick = onRetake,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeutralGray300
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(90.dp, 40.dp)
            ) {
                Text(
                    text = "다시찍기",
                    style = Caption1_m.copy(NeutralGray800)
                )
            }

            // 저장 버튼
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen300,
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .size(90.dp, 40.dp)
            ) {
                Text(
                    text = "저장",
                    style = Caption1_m.copy(NeutralGray0)
                )
            }
        }
    }
}