package com.example.cricdekho.ui.matchdetails.info.adapter

import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.databinding.ItemInfoTeam1Binding
import easyadapter.dc.com.library.EasyAdapter

class BenchTeam1Adapter : EasyAdapter<BenchPlayer, ItemInfoTeam1Binding>(R.layout.item_info_team1) {
    override fun onBind(binding: ItemInfoTeam1Binding, model: BenchPlayer) {
        binding.apply {
            Glide.with(root.context).load(model.playerImages).placeholder(R.drawable.ic_player).into(ivPlayer)
            tvName.text = model.name
            tvExpert.text = model.role
        }
    }

    override fun onCreatingHolder(binding: ItemInfoTeam1Binding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }
}