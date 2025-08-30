package com.droidnova.codex_testing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clipboard")
data class ClipboardItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

