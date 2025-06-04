package com.example.kioskapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kioskapp.database.entities.MenuEntity

@Dao
interface MenuDao {
    
    @Query("SELECT * FROM menu_items WHERE categoryId = :categoryId")
    suspend fun getMenuItemsByCategory(categoryId: Long): List<MenuEntity>
    
    @Query("SELECT * FROM menu_items")
    suspend fun getAllMenuItems(): List<MenuEntity>
    
    @Insert
    suspend fun insertAll(menuItems: List<MenuEntity>)
    
    @Query("DELETE FROM menu_items")
    suspend fun deleteAll()
} 