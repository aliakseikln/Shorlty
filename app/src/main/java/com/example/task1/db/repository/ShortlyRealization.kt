package com.example.task1.db.repository

import androidx.lifecycle.LiveData
import com.example.task1.db.dao.ShortlyDao
import com.example.task1.db.model.ShortlyModel

class ShortlyRealization(private val shortlyDao: ShortlyDao) : ShortlyRepository {

    override val allShortlyLinks: LiveData<List<ShortlyModel>>
        get() = shortlyDao.getAllShortly()

    override suspend fun insertShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit) {
        shortlyDao.insert(shortlyModel)
        onSuccess()
    }

    override suspend fun deleteShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit) {
        shortlyDao.delete(shortlyModel)
        onSuccess()
    }

    override suspend fun deleteShortlyById(id: Int, onSuccess: () -> Unit) {
        shortlyDao.deleteById(id)
        onSuccess()
    }

    override suspend fun updateShortly(shortlyModel: ShortlyModel, onSuccess: () -> Unit) {
        shortlyDao.update(shortlyModel)
        onSuccess()
    }
}