package com.example.kioskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kioskapp.adapter.holder.OrderViewHolder
import com.example.kioskapp.databinding.ItemOrderBinding
import com.example.kioskapp.model.OrderItem
import kotlin.math.max

class OrderRecyclerAdapter(
    private val onItemQuantityUpdate: (OrderItem, Int) -> Unit = { _, _ -> }
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
        val existingItem = items.find { (it.id == item.id) and (it.toppings == item.toppings) }
        
        if (existingItem != null) {
            // 이미 있으면 수량만 증가
            existingItem.quantity+=item.quantity
            notifyDataSetChanged()
        } else {
            // 새 아이템 추가
            items.add(item)
            notifyItemInserted(max(0,items.size - 1))
        }
        
    }
    
    fun getItems(): List<OrderItem> {
        return items.toList()
    }
    
    fun setItems(newItems: List<OrderItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
    
    fun getTotalPrice(): Int {
        return items.sumOf { it.getItemTotal() }
    }
    
    private fun increaseQuantity(position: Int) {
        if (position >= 0 && position < items.size) {
            val item = items[position]
            // UI에서만 즉시 업데이트
            item.quantity++
            notifyItemChanged(position)
            // 실제 데이터 변경은 콜백으로 알림
            onItemQuantityUpdate(item, item.quantity)
        }
    }
    
    private fun decreaseQuantity(position: Int) {
        if (position >= 0 && position < items.size) {
            val item = items[position]
            
            if (item.quantity > 1) {
                // 1보다 크면 수량만 감소
                item.quantity--
                notifyItemChanged(position)
                // 실제 데이터 변경은 콜백으로 알림
                onItemQuantityUpdate(item, item.quantity)
            } else {
                // 수량이 1이면 아이템 삭제
                items.removeAt(position)
                notifyItemRemoved(position)
                // 실제 데이터 변경은 콜백으로 알림 (수량 0으로 삭제)
                onItemQuantityUpdate(item, 0)
            }
        }
    }
    
    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
} 