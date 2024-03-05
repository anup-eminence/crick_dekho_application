package com.example.cricdekho.ui.match.result

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getResultMatches.Tab
import com.example.cricdekho.databinding.ItemFantasyBinding
import easyadapter.dc.com.library.EasyAdapter

class TabsAdapter :
    EasyAdapter<Tab, ItemFantasyBinding>(R.layout.item_fantasy) {
    override fun onBind(binding: ItemFantasyBinding, model: Tab) {
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