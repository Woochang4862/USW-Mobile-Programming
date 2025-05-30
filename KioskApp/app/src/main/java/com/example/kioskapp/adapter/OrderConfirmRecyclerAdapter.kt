package com.example.kioskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kioskapp.R
import com.example.kioskapp.model.OrderItem
import com.example.kioskapp.model.ToppingItem

interface OnOrderConfirmListener {
    fun onOrderConfirm(quantity: Int, toppings: List<ToppingItem>)
}

class OrderConfirmRecyclerAdapter(
    private var orderItems: ArrayList<OrderItem>,
    private val onItemsChanged: (ArrayList<OrderItem>) -> Unit
) : RecyclerView.Adapter<OrderConfirmRecyclerAdapter.OrderConfirmViewHolder>() {

    fun updateItems(newItems: List<OrderItem>) {
        orderItems.clear()
        orderItems.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderConfirmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_confirm, parent, false)
        return OrderConfirmViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderConfirmViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount() = orderItems.size

    inner class OrderConfirmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val menuImage: ImageView = itemView.findViewById(R.id.menu_image)
        private val menuName: TextView = itemView.findViewById(R.id.menu_name)
        private val menuPrice: TextView = itemView.findViewById(R.id.menu_price)
        private val toppingsInfo: TextView = itemView.findViewById(R.id.toppings_info)
        private val quantity: TextView = itemView.findViewById(R.id.quantity)
        private val btnDecrease: TextView = itemView.findViewById(R.id.btn_decrease)
        private val btnIncrease: TextView = itemView.findViewById(R.id.btn_increase)

        fun bind(orderItem: OrderItem) {
            menuName.text = orderItem.name
            menuPrice.text = "${orderItem.price}원"
            quantity.text = orderItem.quantity.toString()

            // 메뉴 이미지 로드
            Glide.with(itemView.context)
                .load(orderItem.imageUrl)
                .into(menuImage)

            // 토핑 정보 표시
            if (orderItem.toppings.isNotEmpty()) {
                val toppingsText = orderItem.toppings.joinToString(", ") { it.name }
                toppingsInfo.text = "토핑: $toppingsText"
                toppingsInfo.visibility = View.VISIBLE
            } else {
                toppingsInfo.visibility = View.GONE
            }

            // 수량 감소 버튼
            btnDecrease.setOnClickListener {
                if (orderItem.quantity > 1) {
                    orderItem.quantity--
                    quantity.text = orderItem.quantity.toString()
                    onItemsChanged(orderItems)
                } else {
                    // 수량이 1일 때는 아이템 삭제
                    orderItems.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                    onItemsChanged(orderItems)
                }
            }

            // 수량 증가 버튼
            btnIncrease.setOnClickListener {
                orderItem.quantity++
                quantity.text = orderItem.quantity.toString()
                onItemsChanged(orderItems)
            }
        }
    }
} 