package com.droidnova.codex_testing

import android.app.Application
import android.content.ClipboardManager
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.droidnova.codex_testing.data.ClipboardDatabase
import com.droidnova.codex_testing.data.ClipboardItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ClipboardViewModel(application: Application) : AndroidViewModel(application) {

    private val clipboardManager: ClipboardManager? = application.getSystemService()
    private val clipboardDao = ClipboardDatabase.getInstance(application).clipboardDao()

    val clipboardHistory: Flow<List<ClipboardItem>> = clipboardDao.getAll()

    private val listener = ClipboardManager.OnPrimaryClipChangedListener {
        val clip = clipboardManager?.primaryClip
        val text = clip?.getItemAt(0)?.coerceToText(application).toString()
        if (text.isNotBlank()) {
            viewModelScope.launch {
                clipboardDao.insert(ClipboardItem(text = text))
            }
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
