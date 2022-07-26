package com.example.task1.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.task1.data.pojo.Shortly

@Dao
interface ShortlyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shortly: Shortly)

    @Delete
    suspend fun delete(shortly: Shortly)

    @Update
    suspend fun update(shortly: Shortly)

    @Query(value = "SELECT * from shortly_table")
    fun getAllShortly(): LiveData<List<Shortly>>

    @Query("DELETE FROM shortly_table WHERE id = :id")
    fun deleteById(id: Int)
}