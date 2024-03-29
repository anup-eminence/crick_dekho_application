package com.example.cricdekho.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getCricketMainTabs.Data
import com.example.cricdekho.databinding.ItemFantasyBinding
import com.example.cricdekho.theme.CurrentTheme
import easyadapter.dc.com.library.EasyAdapter


class HomeTabAdapter : RecyclerView.Adapter<HomeTabAdapter.ItemFantasyVH>(){
    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    var list = emptyList<Data>()
    private var onFeatureItemClick : OnFeatureItemClick?=null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listData: List<Data>){
        list = listData
        notifyDataSetChanged()
    }

    class ItemFantasyVH(val binding: ItemFantasyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemFantasyVH {
        return ItemFantasyVH(ItemFantasyBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
                onFeatureItemClick?.onTabItemClick(item)
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.binding.tvText.setTextColor(ContextCompat.getColor(holder.binding.root.context,R.color.white))
            holder.binding.tvText.setBackgroundDrawable(ContextCompat.getDrawable(holder.binding.root.context,R.drawable.bg_rounded_shape_solid))
        } else {
            holder.binding.tvText.setTextColor(ContextCompat.getColor(holder.binding.root.context,R.color.black))
            holder.binding.tvText.setBackgroundDrawable(ContextCompat.getDrawable(holder.binding.root.context,R.drawable.bg_rounded_shape))
        }

    }

    fun setOnTabItemClick(onFeatureItemClick : OnFeatureItemClick){
        this.onFeatureItemClick = onFeatureItemClick
    }

    interface OnFeatureItemClick {
        fun onTabItemClick(model: Data)
    }
}


class HomeFeatureAdapter :
    EasyAdapter<Data, ItemFantasyBinding>(R.layout.item_fantasy) {
    var selectedPosition = -1
    var lastSelectedPosition = -1
    override fun onBind(binding: ItemFantasyBinding, model: Data) {
        binding.apply {
            tvText.text = model.name
            if (CurrentTheme.textColor != null){
                println(">>>>>>>>>>>>>>lkfjlkdjfkjdlk ")
                tvText.setTextColor(CurrentTheme.textColor!!)
            }
        }

    }

    override fun onCreatingHolder(binding: ItemFantasyBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)


        binding.tvText.setOnClickListener(easyHolder.clickListener)
    }
}