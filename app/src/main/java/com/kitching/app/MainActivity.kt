package com.kitching.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
            KitchingManagerTheme {
                EntryPointScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setContent {
            val destination =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getSerializableExtra(
                    NAVIGATE_TO_SCREEN_KEY,
                    NavigateToScreenIntent::class.java
                ) else intent.getSerializableExtra(NAVIGATE_TO_SCREEN_KEY) as NavigateToScreenIntent
            EntryPointScreen(destination ?: NavigateToScreenIntent.SCHEDULE_GRAPH)
        }
    }
}