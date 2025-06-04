package com.example.kioskapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kioskapp.database.entities.ToppingEntity

@Dao
interface ToppingDao {
    
    @Query("SELECT * FROM toppings WHERE menuId = :menuId")
    suspend fun getToppingsByMenu(menuId: Long): List<ToppingEntity>
    
    @Query("SELECT * FROM toppings")
    suspend fun getAllToppings(): List<ToppingEntity>
    
    @Insert
    suspend fun insertAll(toppings: List<ToppingEntity>)
    
    @Query("DELETE FROM toppings")
    suspend fun deleteAll()
} 