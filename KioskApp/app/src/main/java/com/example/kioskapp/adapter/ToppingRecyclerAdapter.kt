package com.example.kioskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kioskapp.R
import com.example.kioskapp.model.ToppingItem

class ToppingRecyclerAdapter(private val onToppingSelected: (ToppingItem) -> Unit) : 
    RecyclerView.Adapter<ToppingRecyclerAdapter.ToppingViewHolder>() {

    private val toppingItems = mutableListOf<ToppingItem>()

    fun setItems(items: List<ToppingItem>) {
        toppingItems.clear()
        toppingItems.addAll(items)
        notifyDataSetChanged()
    }

    fun getSelectedToppings(): List<ToppingItem> {
        return toppingItems.filter { it.selected }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_topping, parent, false)
        return ToppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        holder.bind(toppingItems[position])
    }

    override fun getItemCount() = toppingItems.size

    inner class ToppingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val toppingImage: ImageView = itemView.findViewById(R.id.topping_image)
        private val toppingName: TextView = itemView.findViewById(R.id.topping_name)
        private val toppingCalories: TextView = itemView.findViewById(R.id.topping_calories)
        private val btnAddTopping: TextView = itemView.findViewById(R.id.btn_add_topping)

        fun bind(item: ToppingItem) {
            toppingName.text = item.name
            toppingCalories.text = "${item.calories} Cal"
            
            Glide.with(itemView.context)
                .load(item.imageUrl)
                .into(toppingImage)

            // 토핑 선택 상태에 따라 버튼 표시 변경
            if (item.selected) {
                btnAddTopping.text = "✓"
            } else {
                btnAddTopping.text = "+"
            }

            btnAddTopping.setOnClickListener {
                item.selected = !item.selected
                if (item.selected) {
                    btnAddTopping.text = "✓"
                } else {
                    btnAddTopping.text = "+"
                }
                onToppingSelected(item)
            }
        }
    }

    fun getToppings(): List<ToppingItem> {
        return toppingItems.filter { it.selected }
    }
} 