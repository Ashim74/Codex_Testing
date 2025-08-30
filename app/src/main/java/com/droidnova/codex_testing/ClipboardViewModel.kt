package com.droidnova.codex_testing

import android.app.Application
import android.content.ClipboardManager
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel

class ClipboardViewModel(application: Application) : AndroidViewModel(application) {

    private val clipboardManager: ClipboardManager? = application.getSystemService()

    val clipboardHistory = mutableStateListOf<String>()

    private val listener = ClipboardManager.OnPrimaryClipChangedListener {
        val clip = clipboardManager?.primaryClip
        val text = clip?.getItemAt(0)?.coerceToText(application).toString()
        if (text.isNotBlank()) {
            clipboardHistory.add(0, text)
        }
    }

    init {
        clipboardManager?.addPrimaryClipChangedListener(listener)
    }

    override fun onCleared() {
        clipboardManager?.removePrimaryClipChangedListener(listener)
        super.onCleared()
    }
}
