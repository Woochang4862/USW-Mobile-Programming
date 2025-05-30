package com.example.kioskapp.adapter.holder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.kioskapp.adapter.listener.OnItemClickListener
import com.example.kioskapp.databinding.ItemMenuBinding
import com.example.kioskapp.model.MenuItem
import java.text.NumberFormat
import java.util.Locale

class MenuViewHolder(private val binding: ItemMenuBinding, private val onItemClickListener: OnItemClickListener) : ViewHolder(binding.root) {

    fun bind(menuItem: MenuItem) {
        binding.menuContainer.setOnClickListener {
            onItemClickListener.onItemClick(it, layoutPosition)
        }

        // 메뉴 이름 설정
        binding.menuName.text = menuItem.name

        // 메뉴 이미지 설정
        Glide.with(binding.root.context)
            .load(menuItem.imageUrl)
            .into(binding.menuImage)
        
        // 메뉴 가격 설정 (한국 원화 형식으로 포맷팅)
        val priceFormat = NumberFormat.getCurrencyInstance(Locale.KOREA)
        binding.menuPrice.text = priceFormat.format(menuItem.price)
    }
} 