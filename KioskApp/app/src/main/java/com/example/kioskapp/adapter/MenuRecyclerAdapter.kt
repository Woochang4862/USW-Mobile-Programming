package com.example.kioskapp.adapter

import CategoryItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kioskapp.adapter.holder.MenuViewHolder
import com.example.kioskapp.adapter.listener.OnItemClickListener
import com.example.kioskapp.databinding.ItemMenuBinding
import com.example.kioskapp.model.MenuItem

class MenuRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MenuViewHolder>() {

    private val items: ArrayList<MenuItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMenuBinding.inflate(layoutInflater, parent, false)
        return MenuViewHolder(binding, object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                onItemClickListener.onItemClick(view, position)
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun add(item: MenuItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }
    
    fun addAll(newItems: List<MenuItem>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startPosition, newItems.size)
    }
    
    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun setCategory(category: CategoryItem) {
        val prevItemCount = items.size
        items.clear()
        notifyItemRangeRemoved(0,prevItemCount)
        for (i in 1..30) {
            items.add((MenuItem(i.toLong(),"메뉴 $i", 4500, category.imageUrl)))
        }
        notifyItemRangeInserted(0,items.size)
    }

    fun getItem(position: Int): MenuItem {
        return items[position]
    }
} 