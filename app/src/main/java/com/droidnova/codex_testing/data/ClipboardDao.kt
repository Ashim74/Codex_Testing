package com.droidnova.codex_testing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ClipboardDao {
    @Query("SELECT * FROM clipboard ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ClipboardItem>>

    @Insert
    suspend fun insert(item: ClipboardItem)
}

