package com.example.kioskapp.adapter.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kioskapp.databinding.ItemOrderBinding
import com.example.kioskapp.model.OrderItem
import java.text.NumberFormat
import java.util.Locale
import com.bumptech.glide.Glide

class OrderViewHolder(
    private val binding: ItemOrderBinding,
    private val onIncreaseClick: (position: Int) -> Unit,
    private val onDecreaseClick: (position: Int) -> Unit
) : ViewHolder(binding.root) {

    init {
        binding.btnIncrease.setOnClickListener {
            onIncreaseClick(adapterPosition)
        }
        
        binding.btnDecrease.setOnClickListener {
            onDecreaseClick(adapterPosition)
        }
    }

    fun bind(orderItem: OrderItem) {
        // 메뉴 이름 설정
        binding.orderName.text = orderItem.name
        
        // 메뉴 이미지 설정
        Glide.with(binding.root.context)
            .load(orderItem.imageUrl)
            .into(binding.orderImage)
        
        // 메뉴 가격 설정 (달러 형식으로 포맷팅)
        val priceFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)
        binding.orderPrice.text = priceFormat.format(orderItem.price)
        
        // 수량 설정
        binding.orderQuantity.text = orderItem.quantity.toString()
    }
} 