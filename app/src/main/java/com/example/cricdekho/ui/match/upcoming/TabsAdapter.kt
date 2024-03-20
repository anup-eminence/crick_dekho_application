package com.example.cricdekho.ui.match.upcoming

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getUpcomingMatches.Tab
import com.example.cricdekho.databinding.ItemFantasyBinding

class TabsAdapter : RecyclerView.Adapter<TabsAdapter.ItemFantasyVH>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    var list = emptyList<Tab>()
    private var onItemClick: OnItemClick? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: List<Tab>) {
        list = listData
        notifyDataSetChanged()
    }

    class ItemFantasyVH(val binding: ItemFantasyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFantasyVH {
        return ItemFantasyVH(
            ItemFantasyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemFantasyVH, position: Int) {
        val item = list[position]
        holder.binding.tvText.text = item.name

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition;
            selectedPosition = holder.adapterPosition
            onItemClick?.onTabItemClick(item)
            notifyItemChanged(lastSelectedPosition);
            notifyItemChanged(selectedPosition);
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.binding.tvText.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.white
                )
            )
            holder.binding.tvText.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.bg_rounded_shape_solid
                )
            )
        } else {
            holder.binding.tvText.setTextColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.black
                )
            )
            holder.binding.tvText.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    holder.binding.root.context,
                    R.drawable.bg_rounded_shape
                )
            )
        }

    }

    fun setOnTabItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    interface OnItemClick {
        fun onTabItemClick(tab: Tab)
    }
}