package com.example.cricdekho.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getLatestNews.DataItem
import com.example.cricdekho.databinding.ItemHomeNewsBinding

class HomeNewsAdapter(private var newsItem: List<DataItem?>?) :
    RecyclerView.Adapter<HomeNewsAdapter.ViewHolder>() {

    private var newsAdapterClickListener: NewsAdapterClickListener? = null

    fun setNewsAdapterListener(newsAdapterClickListener: NewsAdapterClickListener) {
        this.newsAdapterClickListener = newsAdapterClickListener
    }

    class ViewHolder(val binding: ItemHomeNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                tvNumber.text = (position + 1).toString()
                tvNews.text = item.p
            }
        }

        holder.binding.root.setOnClickListener {
            newsAdapterClickListener?.onNewsAdapterItemClick(item!!)
        }
    }

    interface NewsAdapterClickListener {
        fun onNewsAdapterItemClick(item: DataItem)
    }
}