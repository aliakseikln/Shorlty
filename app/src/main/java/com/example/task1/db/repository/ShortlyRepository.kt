package com.example.task1.db.repository

import androidx.lifecycle.LiveData
import com.example.task1.db.model.ShortlyModel

interface ShortlyRepository {

    val allShortlyLinks: LiveData<List<ShortlyModel>>

    suspend fun insertShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit)

    suspend fun deleteShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit)

    suspend fun deleteShortlyById(id: Int, onSuccess: () -> Unit)

    suspend fun updateShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit)
}