package com.example.cricdekho.ui.match.live

import android.view.View
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getLiveMatches.CricketMatch
import com.example.cricdekho.databinding.ItemScheduleBinding
import easyadapter.dc.com.library.EasyAdapter

class LiveAdapter : EasyAdapter<CricketMatch, ItemScheduleBinding>(R.layout.item_schedule) {
    override fun onBind(binding: ItemScheduleBinding, model: CricketMatch) {

        binding.apply {
            Glide.with(root.context).load(model.t1_flag).placeholder(R.drawable.ic_team_default).into(ivFlag1)
            Glide.with(root.context).load(model.t2_flag).placeholder(R.drawable.ic_team_default).into(ivFlag2)
            tvTitle1.text = model.t1_key
            tvTitle2.text = model.t2_key
            tvRuns1.text = model.t1_score
            tvRuns2.text = model.t2_score
            tvMatch.text = model.event
            tvDecision.text = model.result
            tvLive.visibility = View.VISIBLE
        }
    }

    override fun onCreatingHolder(binding: ItemScheduleBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }
}