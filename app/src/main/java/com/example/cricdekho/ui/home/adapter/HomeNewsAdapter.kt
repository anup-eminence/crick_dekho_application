package com.example.cricdekho.ui.home.adapter

import com.example.cricdekho.R
import com.example.cricdekho.databinding.ItemHomeNewsBinding
import com.example.cricdekho.data.model.HomeNewsList
import easyadapter.dc.com.library.EasyAdapter

class HomeNewsAdapter : EasyAdapter<HomeNewsList, ItemHomeNewsBinding>(R.layout.item_home_news) {
    override fun onBind(binding: ItemHomeNewsBinding, model: HomeNewsList) {
        binding.apply {
            tvNumber.text = model.number
            tvNews.text = model.news
        }
    }

    override fun onCreatingHolder(binding: ItemHomeNewsBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}