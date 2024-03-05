package com.example.cricdekho.ui.home.adapter

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getCricketMainTabs.Data
import com.example.cricdekho.databinding.ItemFantasyBinding
import easyadapter.dc.com.library.EasyAdapter

class HomeFeatureAdapter :
    EasyAdapter<Data, ItemFantasyBinding>(R.layout.item_fantasy) {
    override fun onBind(binding: ItemFantasyBinding, model: Data) {
        binding.apply {
            tvText.text = model.name
        }
    }

    override fun onCreatingHolder(binding: ItemFantasyBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.tvText.setOnClickListener(easyHolder.clickListener)
    }
}