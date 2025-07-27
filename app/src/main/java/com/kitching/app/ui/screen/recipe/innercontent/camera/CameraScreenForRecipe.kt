package com.kitching.app.ui.screen.recipe.innercontent.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil3.compose.AsyncImage
import com.kitching.app.R

@Composable
fun CameraScreenForRecipe(
    context: Context,
    onPhotoTaken: (Bitmap) -> Unit
) {
    var hasStoragePermission by remember {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasStoragePermission = isGranted
            if (!isGranted) {
                Toast.makeText(context, "저장소 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
            imageCaptureMode = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
            cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CameraPreview(controller, modifier = Modifier.fillMaxSize())

        IconButton(
            onClick = {
                if (hasStoragePermission) {
                    takePhotoForRecipe(context, controller, onPhotoTaken)
                } else {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(60.dp)
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp),
                model = R.drawable.icon_camera,
                contentDescription = "사진 촬영"
            )
        }
    }
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                /**
                 * 현재 화면의 Life Cycle 과 CameraX Controller 을 동기화 시킴
                 */
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}

// 레시피용 사진 촬영 함수
private fun takePhotoForRecipe(
    context: Context,
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                val rotationDegrees = image.imageInfo.rotationDegrees

                // 원본 비트맵 생성
                val originalBitmap = image.toBitmap()

                // 회전 보정된 비트맵 생성
                val correctedBitmap = when (rotationDegrees) {
                    90 -> rotateBitmap(originalBitmap, 90f)
                    180 -> rotateBitmap(originalBitmap, 180f)
                    270 -> rotateBitmap(originalBitmap, -90f)
                    else -> originalBitmap  // 0도는 그대로
                }

                image.close()
                onPhotoTaken(correctedBitmap)
            }
            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Toast.makeText(context, "사진 촬영에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

// 비트맵 회전 함수
private fun rotateBitmap(bitmap: Bitmap, degrees: Float): Bitmap {
    return if (degrees != 0f) {
        val matrix = Matrix().apply {
            postRotate(degrees)
        }
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
        // 원본 비트맵 해제 (메모리 절약)
        if (!bitmap.isRecycled) {
            bitmap.recycle()
        }
        rotatedBitmap
    } else {
        bitmap
    }
}