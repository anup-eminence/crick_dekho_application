package com.example.cricdekho.ui.matchdetails.fantasy.adapter

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getSeriesHighestStrikeRate.Data
import com.example.cricdekho.databinding.ItemMostBinding
import easyadapter.dc.com.library.EasyAdapter

class StrikeRateAdapter : EasyAdapter<Data, ItemMostBinding>(R.layout.item_most) {
    override fun onBind(binding: ItemMostBinding, model: Data) {
        binding.apply {
            tvPlayerName.text = model.Player
            tvRuns.text = model.SR
            tvTeam.text = model.Team
            tvMatches.text = "${model.Mat} Matches"
        }
    }

    override fun onCreatingHolder(binding: ItemMostBinding, easyHolder: EasyAdapter.EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}