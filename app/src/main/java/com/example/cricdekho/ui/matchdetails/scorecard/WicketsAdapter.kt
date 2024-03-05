package com.example.cricdekho.ui.matchdetails.scorecard

import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.FallOfWicketsArray
import com.example.cricdekho.databinding.ItemWicketsBinding
import easyadapter.dc.com.library.EasyAdapter

class WicketsAdapter : EasyAdapter<FallOfWicketsArray, ItemWicketsBinding>(R.layout.item_wickets) {
    override fun onBind(binding: ItemWicketsBinding, model: FallOfWicketsArray) {
        binding.apply {
            tvBattersWicket.text = model.batsman_name
            tvText.text = model.how_out
            tvScore.text = "${model.number} - ${model.score}"
            tvOver.text = model.overs
        }
    }

    override fun onCreatingHolder(binding: ItemWicketsBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}