package com.example.cricdekho.ui.playerdetails.overview.adapter

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPlayerInfo.DataItem
import com.example.cricdekho.databinding.ItemMostRecentMatchesBinding
import easyadapter.dc.com.library.EasyAdapter

class MostRecentMatchesAdapter :
    EasyAdapter<DataItem, ItemMostRecentMatchesBinding>(R.layout.item_most_recent_matches) {
    override fun onBind(binding: ItemMostRecentMatchesBinding, model: DataItem) {
        binding.apply {
            tvMatch.text = model.match
            tvBF.text = model.bF
            tv4s.text = model.jsonMember4s
            tv6s.text = model.jsonMember6s
            tvSR.text = model.sR
            tvO.text = model.o
            tvR.text = model.r
            tvW.text = model.w
            tvER.text = model.eR
        }
    }

    override fun onCreatingHolder(binding: ItemMostRecentMatchesBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}