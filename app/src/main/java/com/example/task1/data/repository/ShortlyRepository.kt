package com.example.task1.data.repository

import androidx.lifecycle.LiveData
import com.example.task1.data.pojo.Shortly

interface ShortlyRepository {

    val allShortlyLinks: LiveData<List<Shortly>>

    suspend fun insertShortly(shortly: Shortly)

    suspend fun deleteShortly(shortly: Shortly)

    suspend fun deleteShortlyById(id: Int)

    suspend fun updateShortly(shortly: Shortly)
}