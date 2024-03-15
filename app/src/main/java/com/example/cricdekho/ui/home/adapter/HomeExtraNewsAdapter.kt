package com.example.cricdekho.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getLatestNews.DataItem
import com.example.cricdekho.databinding.ItemExtraNewsBinding
import com.example.cricdekho.databinding.ItemHomeNewsBinding
import easyadapter.dc.com.library.EasyAdapter

class HomeExtraNewsAdapter(private var newsItem: List<DataItem?>?) :
    RecyclerView.Adapter<HomeExtraNewsAdapter.ViewHolder>() {

    private var extraNewsAdapterClickListener: ExtraNewsAdapterClickListener? = null

    fun setExtraNewsAdapterListener(extraNewsAdapterClickListener: ExtraNewsAdapterClickListener) {
        this.extraNewsAdapterClickListener = extraNewsAdapterClickListener
    }

    class ViewHolder(val binding: ItemExtraNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemExtraNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsItem?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder, position)
    }

    private fun bind(holder: ViewHolder, position: Int) {
        val item = newsItem?.get(position)
        holder.binding.apply {
            if (item != null) {
                Glide.with(root.context).load(item.img).into(ivPost1)
                tvText1.text = item.p
                tvTime1.text = item.time
            }
        }

        holder.binding.root.setOnClickListener {
            extraNewsAdapterClickListener?.onAdapterItemClick(item!!)
        }
    }

    interface ExtraNewsAdapterClickListener {
        fun onAdapterItemClick(item: DataItem)
    }
}