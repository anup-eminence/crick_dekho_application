package com.example.cricdekho.ui.playerdetails.stat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getPlayerStats.DataItem
import com.example.cricdekho.databinding.BowlingInnItemBinding
import com.example.cricdekho.databinding.InnItemBinding

class BowlingStatAdapter : RecyclerView.Adapter<BowlingStatAdapter.BattingStatVH>() {

    private var battingList = emptyList<DataItem?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DataItem?>) {
        println(">>>>>>>>>>>>>>>>>>>>>>datacaught $list")
        battingList = list
        notifyDataSetChanged()
    }

    class BattingStatVH(val binding: BowlingInnItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingStatVH {
        return BattingStatVH(
            BowlingInnItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return battingList.size
    }

    override fun onBindViewHolder(holder: BattingStatVH, position: Int) {
        val item = battingList[position]
        holder.binding.apply {
            inn.text = item?.inn ?: "0"
            b.text = item?.b ?: "0"
            r.text = item?.r ?: "0"
            w.text = item?.w ?: "0"
            er.text = item?.eR ?: "0"
            dots.text = item?.dots ?: "0"
            mdns.text = item?.mdns ?: "0"
            t4w.text = item?.jsonMember4w ?: "0"
            t5w.text = item?.jsonMember5w ?: "0"
            t10w.text = item?.jsonMember10w ?: "0"
            avg.text = item?.avg ?: "0"

        }
    }
}