package com.example.cricdekho.ui.matchdetails.scorecard

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.InningTab
import com.example.cricdekho.databinding.ItemFantasyBinding
import easyadapter.dc.com.library.EasyAdapter

class ScoreCardAdapter :
    EasyAdapter<InningTab, ItemFantasyBinding>(R.layout.item_fantasy) {
    override fun onBind(binding: ItemFantasyBinding, model: InningTab) {
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