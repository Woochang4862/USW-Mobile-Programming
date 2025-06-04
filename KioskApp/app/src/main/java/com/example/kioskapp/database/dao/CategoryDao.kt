package com.example.kioskapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kioskapp.database.entities.CategoryEntity

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categories ORDER BY id")
    suspend fun getAllCategories(): List<CategoryEntity>
    
    @Insert
    suspend fun insertAll(categories: List<CategoryEntity>)
    
    @Query("DELETE FROM categories")
    suspend fun deleteAll()
} 