package com.kitching.app.ui.screen.recipe.innercontent

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.kitching.app.common.ActionIconInfo
import com.kitching.app.common.CommonState
import com.kitching.app.common.NavigationIconInfo
import com.kitching.app.common.showToast
import com.kitching.app.ui.screen.recipe.innercontent.camera.CameraScreenForRecipe
import com.kitching.app.ui.screen.recipe.innercontent.camera.PhotoPreviewScreen
import com.kitching.app.ui.theme.KitchingManagerTheme

@Composable
fun RecipeCameraScreen(
    commonState: CommonState,
    context: Context,
    navigateToCreateRecipe: () -> Unit,
    navigateToCreateRecipeWithUri: (String) -> Unit
) {
    var showPhotoPreview by remember { mutableStateOf(false) }
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    commonState.topAppBarState.value = commonState.topAppBarState.value.copy(
        title = "레시피 사진 추가",
        navIconInfo = NavigationIconInfo.BACK,
        onClickNavIcon = {
            navigateToCreateRecipe()
        },
        actionIconInfo = ActionIconInfo.NULL,
        onClickActionIcon = {}
    )

    KitchingManagerTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            if (showPhotoPreview) {
                PhotoPreviewScreen(
                    bitmap = capturedBitmap!!,
                    onConfirm = {
                        // 비트맵을 URI로 변환하여 저장
                        val uri = saveBitmapToUri(context, capturedBitmap!!)
                        if (uri != null) {
                            navigateToCreateRecipeWithUri(uri.toString())
                        } else {
                            showToast("사진 저장에 실패했습니다.")
                            navigateToCreateRecipe()
                        }
                    },
                    onRetake = {
                        showPhotoPreview = false
                        capturedBitmap = null
                    }
                )
            } else {
                CameraScreenForRecipe(
                    context = context,
                    onPhotoTaken = { bitmap ->
                        capturedBitmap = bitmap
                        showPhotoPreview = true
                    }
                )
            }
        }
    }
}

// 비트맵을 URI로 저장하는 유틸리티 함수
private fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val filename = "recipe_${System.currentTimeMillis()}.jpg"

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    return uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        it
    }
}