package com.kitching.app.common

import android.widget.Toast
import androidx.compose.runtime.Composable
import com.kitching.app.ui.screen.common.ProgressIndicatorScreen
import com.kitching.domain.AppResult

fun showToast(message: String, delayTime: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(KitchingApplication.getInstance(), message, delayTime).show()
}

@Composable
fun <T> AppResultHandler(
    state: AppResult<T>,
    onFailure: @Composable (Throwable) -> Unit = {},
    onSuccess: @Composable (T) -> Unit
) {
    when (state) {
        is AppResult.Initial, AppResult.Loading -> {
            ProgressIndicatorScreen()
        }

        is AppResult.Failure -> { onFailure(state.exception) }

        is AppResult.Success -> { onSuccess(state.data) }
    }
}