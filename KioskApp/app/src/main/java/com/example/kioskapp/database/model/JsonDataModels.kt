package com.example.kioskapp.database.model

import com.google.gson.annotations.SerializedName

data class InitialDataJson(
    @SerializedName("categories")
    val categories: List<CategoryJson>
)

data class CategoryJson(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("menuItems")
    val menuItems: List<MenuItemJson>
)

data class MenuItemJson(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("toppings")
    val toppings: List<ToppingJson>
)

data class ToppingJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("calories")
    val calories: Int,
    @SerializedName("imageUrl")
    val imageUrl: String
) 