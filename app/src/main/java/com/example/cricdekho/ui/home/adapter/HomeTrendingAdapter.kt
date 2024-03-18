package com.example.cricdekho.ui.home.adapter

import com.example.cricdekho.R
import com.example.cricdekho.databinding.ItemTrendingBinding
import com.example.cricdekho.data.model.HomeTrendingList
import com.example.cricdekho.theme.CurrentTheme
import easyadapter.dc.com.library.EasyAdapter

class HomeTrendingAdapter :
    EasyAdapter<HomeTrendingList, ItemTrendingBinding>(R.layout.item_trending) {
    override fun onBind(binding: ItemTrendingBinding, model: HomeTrendingList) {
        binding.apply {
            CurrentTheme.changeTextColor(this.tvName,root.context)
            CurrentTheme.changeTextColor(this.tvUserName,root.context)
            CurrentTheme.changeTextColor(this.tvDesc,root.context)

            ivProfile.setImageResource(model.profileImage)
            tvName.text = model.name
            tvUserName.text = model.userName
            tvDesc.text = model.description
            ivPost.setImageResource(model.postImage)
        }
    }

    override fun onCreatingHolder(binding: ItemTrendingBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.ivTwitter.setOnClickListener(easyHolder.clickListener)
    }
}