package com.example.cricdekho.ui.matchdetails.scorecard

import com.example.cricdekho.R
import com.example.cricdekho.databinding.ItemTotalScoreBinding
import com.example.cricdekho.data.model.getMatchDetails.Batting
import easyadapter.dc.com.library.EasyAdapter

class TotalScoreAdapter :
    EasyAdapter<Batting, ItemTotalScoreBinding>(R.layout.item_total_score) {
    override fun onBind(binding: ItemTotalScoreBinding, model: Batting) {
        binding.apply {
            tvBatters.text = model.name
            tvText.text = model.out_str
            tvR.text = model.runs
            tvB.text = model.balls
            tv4s.text = model.fours
            tv6s.text = model.sixes
            tvSR.text = model.strike_rate.toString()
        }
    }

    override fun onCreatingHolder(binding: ItemTotalScoreBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
    }
}