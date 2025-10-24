package com.example.levelup.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelup.model.UserDao
import com.example.levelup.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(
            context: Context
        ): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "items_table"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}