package com.example.kioskapp.adapter

import com.example.kioskapp.model.CategoryItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kioskapp.adapter.holder.CategoryViewHolder
import com.example.kioskapp.adapter.listener.OnItemClickListener
import com.example.kioskapp.databinding.ItemCategoryBinding

class CategoryRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<CategoryViewHolder>() {

    private val items: ArrayList<CategoryItem> = arrayListOf()

    private var selectedItemIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding, object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val prevIndex = selectedItemIndex
                selectedItemIndex = position
                onItemClickListener.onItemClick(view, position)
                notifyItemChanged(prevIndex)
                notifyItemChanged(position)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position], position==selectedItemIndex)
    }

    fun add(item:CategoryItem) {
        items.add(item)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): CategoryItem {
        return items[position]
    }
}