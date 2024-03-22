package com.example.cricdekho.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricdekho.data.model.getHomeNews.DataItem
import com.example.cricdekho.databinding.ItemExtraNewsBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.util.showToast
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
            CurrentTheme.changeTextColor(this.tvText1,root.context)
            CurrentTheme.changeTextColor(this.tvTime1,root.context)
            CurrentTheme.changeTextColor(this.tvTime2,root.context)
            CurrentTheme.changeTextColor(this.tvText2,root.context)
            CurrentTheme.changeCardViewBackgroundColor(cardView,root.context)


            if (item != null) {
                if (item.type == "primary") {
                    ivPost1.isVisible = true
                    tvText1.isVisible = true
                    tvTime1.isVisible = true

                    Glide.with(root.context).load(item.img).into(ivPost1)
                    tvText1.text = item.title
                    tvTime1.text = item.time
                } else {
                    ivPost2.isVisible = true
                    tvText2.isVisible = true
                    tvTime2.isVisible = true

                    Glide.with(root.context).load(item.img).into(ivPost2)
                    tvText2.text = item.title
                    tvTime2.text = item.time
                }
            }
        }

        holder.binding.root.setOnClickListener {
            if (item?.isLiveNews != true) {
                extraNewsAdapterClickListener?.onAdapterItemClick(item!!)
            } else {
                holder.binding.root.context?.showToast("Not Clickable")
            }
        }
    }

    interface ExtraNewsAdapterClickListener {
        fun onAdapterItemClick(item: DataItem)
    }
}