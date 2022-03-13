package com.a7medkenawy.foody.di

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Room
import com.a7medkenawy.foody.data.database.RecipesDao
import com.a7medkenawy.foody.data.database.RecipesDatabase
import com.a7medkenawy.foody.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //room database instance
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            RecipesDatabase::class.java,
            Constants.RECIPES_DATABASE
        ).build()

    //instance from dao
    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase)=database.recipeDao()

}