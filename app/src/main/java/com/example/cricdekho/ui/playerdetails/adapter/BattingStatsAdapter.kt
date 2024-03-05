package com.example.cricdekho.ui.playerdetails.adapter

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerInfo.DataItem
import com.example.cricdekho.databinding.ItemBattingStatsBinding
import easyadapter.dc.com.library.EasyAdapter

class BattingStatsAdapter :
    EasyAdapter<DataItem, ItemBattingStatsBinding>(R.layout.item_batting_stats) {
    override fun onBind(binding: ItemBattingStatsBinding, model: DataItem) {
        binding.apply {
            tvGameType.text = model.gameType
            tvMat.text = model.mat
            tvInn.text = model.inn
            tvR.text = model.r
            tvBF.text = model.bF
            tvNO.text = model.nO
            tvAvg.text = model.avg
            tvSR.text = model.sR
            tv100s.text = model.jsonMember100s
            tv50s.text = model.jsonMember50s
            tvH.text = model.h
            tv4s.text = model.jsonMember4s
            tv6s.text = model.jsonMember6s
            tvCt.text = model.ct
            tvSt.text = model.st
        }
    }

    override fun onCreatingHolder(binding: ItemBattingStatsBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}