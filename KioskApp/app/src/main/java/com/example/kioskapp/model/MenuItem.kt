package com.example.kioskapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val categoryId: Long,
) : Parcelable 