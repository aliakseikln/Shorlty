package com.example.task1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "shortly_table")
class ShortlyModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var originalLink: String = "",
    @ColumnInfo
    var shortlyLink: String = ""
) : Serializable
