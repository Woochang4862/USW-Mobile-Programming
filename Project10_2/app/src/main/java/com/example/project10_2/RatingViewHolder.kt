package com.example.project10_2

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.project10_2.databinding.ItemRatingBinding

class RatingViewHolder(private val binding: ItemRatingBinding) : ViewHolder(binding.root) {

    fun bind(name:String, rating:Float){
        binding.apply {
            imageName.text = name
            imageRating.rating = rating
        }
    }
}