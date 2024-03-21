package com.example.cricdekho.ui.teaminfo.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.data.model.getTeamNews.NewsItem
import com.example.cricdekho.databinding.ItemExtraNewsBinding

class TeamNewsAdapter(private var newsItem: List<NewsItem?>?) :
    RecyclerView.Adapter<TeamNewsAdapter.ViewHolder>() {

    private var newsAdapterClickListener: NewsAdapterClickListener? = null

    fun setNewsAdapterListener(newsAdapterClickListener: NewsAdapterClickListener) {
        this.newsAdapterClickListener = newsAdapterClickListener
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
                ivPost1.isVisible = true
                tvText1.isVisible = true
                tvTime1.isVisible = true

                Glide.with(root.context).load(item.img).into(ivPost1)
                tvText1.text = item.title
                tvTime1.text = item.time

            }
        }

        holder.binding.root.setOnClickListener {
            newsAdapterClickListener?.onAdapterItemClick(item!!)
        }
    }

    interface NewsAdapterClickListener {
        fun onAdapterItemClick(item: NewsItem)
    }
}