package com.example.kioskapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class OrderItem(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    var quantity: Int,
    val toppings: List<ToppingItem> = emptyList()
) : Parcelable, Serializable {
    fun getItemTotal(): Int = price * quantity
} 