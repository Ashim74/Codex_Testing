package com.droidnova.codex_testing

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.droidnova.codex_testing.data.ClipboardDatabase
import com.droidnova.codex_testing.data.ClipboardItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class ClipboardService : Service() {

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var clipboardManager: ClipboardManager
    private val listener = ClipboardManager.OnPrimaryClipChangedListener {
        val clip = clipboardManager.primaryClip
        val text = clip?.getItemAt(0)?.coerceToText(this).toString()
        if (text.isNotBlank()) {
            Log.d(TAG, "Clipboard changed: $text")
            scope.launch {
                ClipboardDatabase.getInstance(applicationContext)
                    .clipboardDao()
                    .insert(ClipboardItem(text = text))
                Log.d(TAG, "Stored clipboard text")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service created")
        clipboardManager = getSystemService<ClipboardManager>()!!
        clipboardManager.addPrimaryClipChangedListener(listener)
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.clipboard_notification_title))
            .setContentText(getString(R.string.clipboard_notification_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    override fun onDestroy() {
        Log.d(TAG, "Service destroyed")
        clipboardManager.removePrimaryClipChangedListener(listener)
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.clipboard_notification_channel),
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "clipboard_service"
        private const val NOTIFICATION_ID = 1
        private const val TAG = "ClipboardService"
    }
}

