package com.kitching.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kitching.app.navgraph.Route
import com.kitching.app.service.NAVIGATE_TO_SCREEN_KEY
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) intent.getParcelableExtra(
                    NAVIGATE_TO_SCREEN_KEY,
                    Route.BottomTab::class.java
                ) else intent.getSerializableExtra(NAVIGATE_TO_SCREEN_KEY) as Route.BottomTab
            EntryPointScreen(destination ?: Route.RecipeGraph)
        }
    }
}