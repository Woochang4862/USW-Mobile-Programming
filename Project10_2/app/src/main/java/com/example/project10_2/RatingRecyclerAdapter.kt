package com.example.project10_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project10_2.databinding.ItemRatingBinding

class RatingRecyclerAdapter : RecyclerView.Adapter<RatingViewHolder>() {

    var results: ArrayList<Pair<String, Float>>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingViewHolder {
        val binding = ItemRatingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RatingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results?.size ?: 0
    }

    override fun onBindViewHolder(holder: RatingViewHolder, position: Int) {
    holder.bind(results!![position].first, results!![position].second)
    }
}