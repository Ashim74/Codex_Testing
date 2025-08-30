package com.droidnova.codex_testing

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droidnova.codex_testing.navigation.AppNavHost
import com.droidnova.codex_testing.ui.theme.Codex_TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextCompat.startForegroundService(
            this,
            Intent(this, ClipboardService::class.java)
        )
        enableEdgeToEdge()
        setContent {
            Codex_TestingTheme {
                val vm: ClipboardViewModel = viewModel()
                AppNavHost(viewModel = vm)
            }
        }
    }
}
