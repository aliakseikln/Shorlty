package com.example.task1.db.model

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
    var shortlyLink: String = "",
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShortlyModel

        if (id != other.id) return false
        if (originalLink != other.originalLink) return false
        if (shortlyLink != other.shortlyLink) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + originalLink.hashCode()
        result = 31 * result + shortlyLink.hashCode()
        return result
    }
}
