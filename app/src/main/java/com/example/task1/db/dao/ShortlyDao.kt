package com.example.task1.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task1.db.model.ShortlyModel

@Dao
interface ShortlyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shortlyModel: ShortlyModel)

    @Delete
    suspend fun delete(shortlyModel: ShortlyModel)

    @Update
    suspend fun update(shortlyModel: ShortlyModel)

    @Query(value = "SELECT * from shortly_table")
    fun getAllShortly(): LiveData<List<ShortlyModel>>

    @Query("DELETE FROM shortly_table WHERE id = :id")
    fun deleteById(id: Int)
}