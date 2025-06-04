package com.example.kioskapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "toppings",
    foreignKeys = [
        ForeignKey(
            entity = MenuEntity::class,
            parentColumns = ["id"],
            childColumns = ["menuId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["menuId"])]
)
data class ToppingEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Int,
    val calories: Int,
    val imageUrl: String,
    val menuId: Long
) 