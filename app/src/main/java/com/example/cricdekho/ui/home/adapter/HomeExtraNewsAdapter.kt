package com.example.cricdekho.ui.home.adapter

import com.example.cricdekho.R
import com.example.cricdekho.databinding.ItemExtraNewsBinding
import com.example.cricdekho.data.model.HomeExtraNewsList
import easyadapter.dc.com.library.EasyAdapter

class HomeExtraNewsAdapter :
    EasyAdapter<HomeExtraNewsList, ItemExtraNewsBinding>(R.layout.item_extra_news) {
    override fun onBind(binding: ItemExtraNewsBinding, model: HomeExtraNewsList) {
        binding.apply {
            ivPost1.setImageResource(model.image1)
            tvText1.text = model.text1
            tvTime1.text = model.time1
            ivPost2.setImageResource(model.image2)
            tvText2.text = model.text2
            tvTime2.text = model.time2
            ivPost3.setImageResource(model.image3)
            tvText3.text = model.text3
            tvTime3.text = model.time3
        }
    }

    override fun onCreatingHolder(binding: ItemExtraNewsBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}