package com.example.kioskapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToppingItem(
    val id: Int,
    val name: String,
    val price: Int,
    val calories: Int,
    val imageUrl: String,
    val menuId: Long,
    var selected: Boolean = false,
) : Parcelable