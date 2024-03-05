package com.example.cricdekho.ui.playerdetails.adapter

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerInfo.DataItem
import com.example.cricdekho.databinding.ItemBowlingStatsBinding
import easyadapter.dc.com.library.EasyAdapter

class BowlingStatsAdapter :
    EasyAdapter<DataItem, ItemBowlingStatsBinding>(R.layout.item_bowling_stats) {
    override fun onBind(binding: ItemBowlingStatsBinding, model: DataItem) {
        binding.apply {
            tvGameType.text = model.gameType
            tvMat.text = model.mat
            tvInn.text = model.inn
            tvO.text = model.o
            tvR.text = model.r
            tvW.text = model.w
            tvAvg.text = model.avg
            tvER.text = model.eR
            tvBest.text = model.best
            tv5w.text = model.jsonMember5w
            tv10w.text = model.jsonMember10w
        }
    }

    override fun onCreatingHolder(binding: ItemBowlingStatsBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}