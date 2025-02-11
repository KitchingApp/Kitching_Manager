package com.kitching.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kitching.app.ui.screen.navigation.LoginNavHost
import com.kitching.app.ui.theme.KitchingManagerTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KitchingManagerTheme {
                LoginNavHost()
            }
        }
    }
}