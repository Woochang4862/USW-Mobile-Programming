package com.example.kioskapp.database.converters

import com.example.kioskapp.database.entities.CategoryEntity
import com.example.kioskapp.database.entities.MenuEntity
import com.example.kioskapp.database.entities.ToppingEntity
import com.example.kioskapp.database.model.CategoryJson
import com.example.kioskapp.database.model.MenuItemJson
import com.example.kioskapp.database.model.ToppingJson
import com.example.kioskapp.model.CategoryItem
import com.example.kioskapp.model.MenuItem
import com.example.kioskapp.model.ToppingItem

// CategoryEntity to CategoryItem
fun CategoryEntity.toCategoryItem(): CategoryItem {
    return CategoryItem(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        isSelected = false
    )
}

// CategoryItem to CategoryEntity
fun CategoryItem.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

// MenuEntity to MenuItem
fun MenuEntity.toMenuItem(): MenuItem {
    return MenuItem(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        categoryId = this.categoryId
    )
}

// MenuItem to MenuEntity
fun MenuItem.toMenuEntity(): MenuEntity {
    return MenuEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        categoryId = this.categoryId
    )
}

// ToppingEntity to ToppingItem
fun ToppingEntity.toToppingItem(): ToppingItem {
    return ToppingItem(
        id = this.id,
        name = this.name,
        price = this.price,
        calories = this.calories,
        imageUrl = this.imageUrl,
        menuId = this.menuId,
        selected = false
    )
}

// ToppingItem to ToppingEntity
fun ToppingItem.toToppingEntity(): ToppingEntity {
    return ToppingEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        calories = this.calories,
        imageUrl = this.imageUrl,
        menuId = this.menuId
    )
}

// JSON to Domain Model converters
fun CategoryJson.toCategoryItem(): CategoryItem {
    return CategoryItem(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        isSelected = false
    )
}

fun CategoryJson.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

fun MenuItemJson.toMenuItem(categoryId: Long): MenuItem {
    return MenuItem(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        categoryId = categoryId
    )
}

fun MenuItemJson.toMenuEntity(categoryId: Long): MenuEntity {
    return MenuEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        imageUrl = this.imageUrl,
        categoryId = categoryId
    )
}

fun ToppingJson.toToppingItem(menuId: Long): ToppingItem {
    return ToppingItem(
        id = this.id,
        name = this.name,
        price = this.price,
        calories = this.calories,
        imageUrl = this.imageUrl,
        menuId = menuId,
        selected = false
    )
}

fun ToppingJson.toToppingEntity(menuId: Long): ToppingEntity {
    return ToppingEntity(
        id = this.id,
        name = this.name,
        price = this.price,
        calories = this.calories,
        imageUrl = this.imageUrl,
        menuId = menuId
    )
} 