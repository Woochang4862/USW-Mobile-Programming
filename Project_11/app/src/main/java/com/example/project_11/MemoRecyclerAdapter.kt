package com.example.project_11

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.project_11.databinding.ItemMemoBinding

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
}

class MemoRecyclerAdapter(private val onItemClickListener: OnItemClickListener) : Adapter<MemoViewHolder>() {

    val items = arrayListOf<Memo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMemoBinding.inflate(layoutInflater, parent, false)
        return MemoViewHolder(binding, onItemClickListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class MemoViewHolder(private val binding: ItemMemoBinding, private val onItemClickListener: OnItemClickListener): ViewHolder(binding.root) {

    fun bind(item : Memo) {
        binding.root.setOnClickListener {
            onItemClickListener.onItemClick(itemView, layoutPosition)
        }
        with(binding) {
            memoId.text = item.id.toString()
            title.text = item.title
            timestamp.text = item.timestamp
        }
    }

}