package com.kitching.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.kitching.app.service.NAVIGATE_TO_RECIPE_MAIN_KEY
import com.kitching.app.ui.screen.EntryPointScreen
import com.kitching.app.ui.theme.KitchingManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var destination by remember { mutableStateOf(intent.getStringExtra(NAVIGATE_TO_RECIPE_MAIN_KEY) ?: "") }

            val currentIntent = rememberUpdatedState(intent)

            DisposableEffect(currentIntent.value) {
                destination = currentIntent.value.getStringExtra(NAVIGATE_TO_RECIPE_MAIN_KEY) ?: ""
                onDispose {  }
            }

            LaunchedEffect(Unit) {
            }

            KitchingManagerTheme {
                EntryPointScreen(destination)
            }
        }
    }
}