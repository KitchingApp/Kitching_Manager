package com.kitching.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.kitching.app.service.NAVIGATE_TO_SCREEN_KEY
import com.kitching.app.service.NavigateToScreenIntent
import com.kitching.app.ui.screen.EntryPointScreen
import com.kitching.app.ui.theme.KitchingManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var destination by remember { mutableStateOf(intent.getStringExtra(NAVIGATE_TO_SCREEN_KEY) ?: NavigateToScreenIntent.SCHEDULE_GRAPH.name) }

            val currentIntent = rememberUpdatedState(intent)

            DisposableEffect(currentIntent.value) {
                destination = currentIntent.value.getStringExtra(NAVIGATE_TO_SCREEN_KEY) ?: NavigateToScreenIntent.SCHEDULE_GRAPH.name
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