package com.a7medkenawy.foody.data.database

import androidx.room.Database

@Database(
    entities = [RecipesEntity::class],
    version = 2,
    exportSchema = false
)
abstract class RecipesDatabase {
    abstract fun recipeDao(): RecipesDao
}