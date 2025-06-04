package com.example.movielistapp

import android.view.View

interface OnItemClickListener {
    fun onItemClick(view: View?, position: Int)
}