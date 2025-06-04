package com.example.kioskapp.model

data class CategoryItem(
    val id: Long,
    val name: String,
    val imageUrl: String,
    var isSelected: Boolean = false
)