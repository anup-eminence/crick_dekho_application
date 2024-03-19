package com.example.cricdekho.ui.matchdetails.info.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getMatchDetails.Player
import com.example.cricdekho.databinding.ItemInfoTeam2Binding
import easyadapter.dc.com.library.EasyAdapter

class InfoTeam2Adapter : EasyAdapter<Player, ItemInfoTeam2Binding>(R.layout.item_info_team2) {
    override fun onBind(binding: ItemInfoTeam2Binding, model: Player) {
        binding.apply {
            Glide.with(root.context).load(model.playerImages).placeholder(R.drawable.ic_player).into(ivPlayer)
            tvName.text = model.name
            tvExpert.text = model.role
            if (model.position == "captain") {
                tvCaption.visibility = View.VISIBLE
            } else {
                tvCaption.visibility = View.GONE
            }
        }
    }

    override fun onCreatingHolder(binding: ItemInfoTeam2Binding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.clItem.setOnClickListener(easyHolder.clickListener)
    }
}