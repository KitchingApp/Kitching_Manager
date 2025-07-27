package com.kitching.app.ui.screen.recipe.innercontent.camera

import androidx.compose.foundation.clickable
import com.kitching.app.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kitching.app.ui.theme.Body2_m
import com.kitching.app.ui.theme.NeutralGray0
import com.kitching.app.ui.theme.NeutralGray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickerBottomSheet(
    onDismiss: () -> Unit,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit
) {
    ModalBottomSheet(
        containerColor = NeutralGray0,
        dragHandle =null,
        shape = RoundedCornerShape(28.dp),
        onDismissRequest = onDismiss
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .clickable {
                        onCameraSelected()
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.icon_camera,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 10.dp)
                )

                Text(
                    text = "카메라 촬영",
                    style = Body2_m.copy(NeutralGray800)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .clickable {
                        onGallerySelected()
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = R.drawable.icon_image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 10.dp)
                )

                Text(
                    text = "갤러리 선택",
                    style = Body2_m.copy(NeutralGray800)
                )
            }

        }
    }
}