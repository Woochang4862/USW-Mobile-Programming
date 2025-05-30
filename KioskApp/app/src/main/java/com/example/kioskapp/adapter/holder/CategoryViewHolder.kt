package com.example.kioskapp.adapter.holder

import CategoryItem
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kioskapp.adapter.listener.OnItemClickListener
import com.example.kioskapp.databinding.ItemCategoryBinding

class CategoryViewHolder(private val binding: ItemCategoryBinding, private val onItemClickListener: OnItemClickListener) : ViewHolder(binding.root) {

    fun bind(categoryItem: CategoryItem, isSelected: Boolean) {
        binding.categoryName.text = categoryItem.name
        binding.categoryName.setTextColor(Color.parseColor(if (isSelected) "#FFFFFF" else "#000000"))
        Glide.with(binding.root.context)
            .load(categoryItem.imageUrl)
            .centerCrop()
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_gallery)
            .timeout(10000)
            .skipMemoryCache(false)
            .diskCacheStrategy(com.bumptech.glide.load.engine.DiskCacheStrategy.ALL)
            .into(binding.categoryImage)
        binding.categoryContainer.setBackgroundColor(Color.parseColor(if (isSelected) "#FD2424" else "#FFFFFF"))

        binding.categoryContainer.setOnClickListener {
            onItemClickListener.onItemClick(it, layoutPosition)
        }
    }

}