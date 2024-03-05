package com.example.cricdekho.ui.matchdetails.info.adapter

import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.BenchPlayer
import com.example.cricdekho.databinding.ItemInfoTeam2Binding
import easyadapter.dc.com.library.EasyAdapter

class BenchTeam2Adapter : EasyAdapter<BenchPlayer, ItemInfoTeam2Binding>(R.layout.item_info_team2) {
    override fun onBind(binding: ItemInfoTeam2Binding, model: BenchPlayer) {
        binding.apply {
            Glide.with(root.context).load(model.playerImages).placeholder(R.drawable.ic_player).into(ivPlayer)
            tvName.text = model.name
            tvExpert.text = model.role
        }
    }

    override fun onCreatingHolder(binding: ItemInfoTeam2Binding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }
}