package com.example.cricdekho.ui.playerdetails.stat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.databinding.InnItemBinding
import com.example.cricdekho.databinding.VsLayoutBinding

class VsStatAdapter : RecyclerView.Adapter<VsStatAdapter.BattingStatVH>() {

    class BattingStatVH(val binding : VsLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingStatVH {
        return BattingStatVH(VsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: BattingStatVH, position: Int) {

    }
}