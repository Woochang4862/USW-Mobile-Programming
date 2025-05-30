package com.example.kioskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kioskapp.adapter.holder.OrderViewHolder
import com.example.kioskapp.databinding.ItemOrderBinding
import com.example.kioskapp.model.OrderItem

class OrderRecyclerAdapter(
    private val onQuantityChanged: (items: List<OrderItem>) -> Unit
) : RecyclerView.Adapter<OrderViewHolder>() {

    private val items: MutableList<OrderItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(layoutInflater, parent, false)
        return OrderViewHolder(
            binding,
            onIncreaseClick = { position -> increaseQuantity(position) },
            onDecreaseClick = { position -> decreaseQuantity(position) }
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addItem(item: OrderItem) {
        // 이미 동일한 메뉴가 있는지 확인
        val existingItem = items.find { it.id == item.id }
        
        if (existingItem != null) {
            // 이미 있으면 수량만 증가
            existingItem.quantity++
            notifyDataSetChanged()
        } else {
            // 새 아이템 추가
            items.add(item)
            notifyItemInserted(items.size - 1)
        }
        
        updateTotalPrice()
    }
    
    fun getItems(): List<OrderItem> {
        return items.toList()
    }
    
    fun getTotalPrice(): Int {
        return items.sumOf { it.getItemTotal() }
    }
    
    private fun increaseQuantity(position: Int) {
        if (position >= 0 && position < items.size) {
            items[position].quantity++
            notifyItemChanged(position)
            updateTotalPrice()
        }
    }
    
    private fun decreaseQuantity(position: Int) {
        if (position >= 0 && position < items.size) {
            val item = items[position]
            
            if (item.quantity > 1) {
                // 1보다 크면 수량만 감소
                item.quantity--
                notifyItemChanged(position)
            } else {
                // 수량이 1이면 아이템 삭제
                items.removeAt(position)
                notifyItemRemoved(position)
            }
            
            updateTotalPrice()
        }
    }
    
    private fun updateTotalPrice() {
        onQuantityChanged(items)
    }
    
    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        updateTotalPrice()
    }
} 