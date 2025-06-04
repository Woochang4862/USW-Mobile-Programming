package com.example.kioskapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kioskapp.database.dao.CategoryDao
import com.example.kioskapp.database.dao.MenuDao
import com.example.kioskapp.database.dao.ToppingDao
import com.example.kioskapp.database.entities.CategoryEntity
import com.example.kioskapp.database.entities.MenuEntity
import com.example.kioskapp.database.entities.ToppingEntity

@Database(
    entities = [CategoryEntity::class, MenuEntity::class, ToppingEntity::class],
    version = 1,
    exportSchema = false
)
abstract class KioskDatabase : RoomDatabase() {
    
    abstract fun categoryDao(): CategoryDao
    abstract fun menuDao(): MenuDao
    abstract fun toppingDao(): ToppingDao
    
    companion object {
        @Volatile
        private var INSTANCE: KioskDatabase? = null
        
        fun getDatabase(context: Context): KioskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KioskDatabase::class.java,
                    "kiosk_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 