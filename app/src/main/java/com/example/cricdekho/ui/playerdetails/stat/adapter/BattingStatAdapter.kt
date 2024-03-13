package com.example.cricdekho.ui.playerdetails.stat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.databinding.InnItemBinding

class BattingStatAdapter : RecyclerView.Adapter<BattingStatAdapter.BattingStatVH>() {

    class BattingStatVH(val binding : InnItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingStatVH {
        return BattingStatVH(InnItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: BattingStatVH, position: Int) {

    }
}