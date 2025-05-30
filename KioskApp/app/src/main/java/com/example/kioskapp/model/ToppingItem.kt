package com.example.kioskapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ToppingItem(
    val id: Int,
    val name: String,
    val calories: Int,
    val imageUrl: String,
    var selected: Boolean = false
) : Parcelable 