package com.example.task1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.task1.data.dao.ShortlyDao
import com.example.task1.data.pojo.Shortly

@Database(entities = [Shortly::class], version = 1)
abstract class ShortlyDataBase : RoomDatabase() {

    abstract fun getShortlyDao(): ShortlyDao

    companion object {
        private var database: ShortlyDataBase? = null

        @Synchronized
        fun getInstance(context: Context): ShortlyDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, ShortlyDataBase::class.java, "db").build()
                database as ShortlyDataBase
            } else {
                database as ShortlyDataBase
            }
        }
    }
}