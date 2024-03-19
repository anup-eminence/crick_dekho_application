package com.example.cricdekho.ui.fantasy.adapter

import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getUpcomingMatches.ResponseUpcomingMatch
import com.example.cricdekho.databinding.ItemFantasyMatchesBinding
import com.example.cricdekho.theme.CurrentTheme
import easyadapter.dc.com.library.EasyAdapter

class FantasyMatchesAdapter :
    EasyAdapter<ResponseUpcomingMatch, ItemFantasyMatchesBinding>(R.layout.item_fantasy_matches) {
    override fun onBind(binding: ItemFantasyMatchesBinding, model: ResponseUpcomingMatch) {
        binding.apply {
            Glide.with(root.context).load(model.t1_flag).into(ivImage1)
            Glide.with(root.context).load(model.t2_flag).into(ivImage2)
            CurrentTheme.changeTextColor(tvMatch,root.context)
            CurrentTheme.changeTextColor(tvMatch1,root.context)
            CurrentTheme.changeTextColor(tvMatch2,root.context)
            CurrentTheme.changeTextColor(tvTime,root.context)
            tvMatch.text = model.event
            tvMatch1.text = model.t1_key
            tvMatch2.text = model.t2_key
            tvTime.text = model.formatted_date
        }
    }

    override fun onCreatingHolder(binding: ItemFantasyMatchesBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }
}