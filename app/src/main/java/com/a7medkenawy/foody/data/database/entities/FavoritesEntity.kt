package com.a7medkenawy.foody.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.a7medkenawy.foody.models.Result

@Entity(tableName = "favorites_table")
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val result: Result,
) {
}