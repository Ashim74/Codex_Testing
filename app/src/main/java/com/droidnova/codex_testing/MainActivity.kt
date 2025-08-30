package com.droidnova.codex_testing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droidnova.codex_testing.navigation.AppNavHost
import com.droidnova.codex_testing.ui.theme.Codex_TestingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
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
