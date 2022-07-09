package com.example.task1.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.task1.App
import com.example.task1.db.dao.ShortlyDao
import com.example.task1.db.model.ShortlyModel

@Database(entities = [ShortlyModel::class], version = 1)
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