package com.example.cricdekho.ui.matchdetails.scorecard

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Bowling
import com.example.cricdekho.databinding.ItemBowlersBinding
import easyadapter.dc.com.library.EasyAdapter

class BowlersAdapter : EasyAdapter<Bowling, ItemBowlersBinding>(R.layout.item_bowlers) {
    override fun onBind(binding: ItemBowlersBinding, model: Bowling) {
        binding.apply {
            tvBowlers.text = model.name
            tvO.text = model.overs
            tvM.text = model.maiden_overs
            tvRBowler.text = model.runs
            tvW.text = model.wickets
            tvEco.text = model.economy.toString()
        }
    }

    override fun onCreatingHolder(binding: ItemBowlersBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}