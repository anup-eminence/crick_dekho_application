package com.example.cricdekho.ui.schedulepoints.points

import com.bumptech.glide.Glide
import com.example.cricdekho.R
import com.example.cricdekho.data.model.getPointsTable.Group
import com.example.cricdekho.databinding.ItemPointTableBinding
import easyadapter.dc.com.library.EasyAdapter

class PointTableAdapter :
    EasyAdapter<Group, ItemPointTableBinding>(R.layout.item_point_table) {
    override fun onBind(binding: ItemPointTableBinding, model: Group) {
        binding.apply {
            Glide.with(root.context).load(model.team_flag).into(ivImage)
            tvNumber.text = model.position
            tvPlayer.text = model.team_name
            tvText1.text = model.played.toString()
            tvText2.text = model.won.toString()
            tvText3.text = model.lost.toString()
            tvText4.text = model.tied.toString()
            tvText5.text = model.nrr
            tvText6.text = model.points
        }
    }

    override fun onCreatingHolder(binding: ItemPointTableBinding, easyHolder: EasyHolder) {
        super.onCreatingHolder(binding, easyHolder)
        binding.root.setOnClickListener(easyHolder.clickListener)
        binding.tvPlayer.setOnClickListener(easyHolder.clickListener)
    }
}