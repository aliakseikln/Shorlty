package com.example.task1.data.repository

import androidx.lifecycle.LiveData
import com.example.task1.data.dao.ShortlyDao
import com.example.task1.data.pojo.Shortly

class ShortlyRealization(private val shortlyDao: ShortlyDao) : ShortlyRepository {

    override val allShortlyLinks: LiveData<List<Shortly>>
        get() = shortlyDao.getAllShortly()

    override suspend fun insertShortly(shortly: Shortly) {
        shortlyDao.insert(shortly)
    }

    override suspend fun deleteShortly(shortly: Shortly) {
        shortlyDao.delete(shortly)
    }

    override suspend fun deleteShortlyById(id: Int) {
        shortlyDao.deleteById(id)
    }

    override suspend fun updateShortly(shortly: Shortly) {
        shortlyDao.update(shortly)
    }
}