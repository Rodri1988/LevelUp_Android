package com.example.levelup.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.levelup.model.CartItem
import com.example.levelup.model.UserEntity
import com.example.levelup.model.ProductEntity

@Database(
    entities = [UserEntity::class, CartItem::class, ProductEntity::class],
    version = 3,
    exportSchema = false
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ApplicationDatabase? = null

        fun getDatabase(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    "levelup_database"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true) // ✅ Versión actualizada
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}