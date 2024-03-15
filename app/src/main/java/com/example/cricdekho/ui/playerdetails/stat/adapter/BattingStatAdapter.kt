package com.example.cricdekho.ui.playerdetails.stat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getPlayerStats.DataItem
import com.example.cricdekho.databinding.InnItemBinding

class BattingStatAdapter : RecyclerView.Adapter<BattingStatAdapter.BattingStatVH>() {

    private var battingList = emptyList<DataItem?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<DataItem?>) {
        println(">>>>>>>>>>>>>>>>>>>>>>jfjfjjfjf $list")
        battingList = list
        notifyDataSetChanged()
    }

    class BattingStatVH(val binding: InnItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingStatVH {
        return BattingStatVH(
            InnItemBinding.inflate(
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
            no.text = item?.nO ?: "0"
            r.text = item?.r ?: "0"
            bf.text = item?.bF ?: "0"
            t100s.text = item?.jsonMember100s ?: "0"
            t50s.text = item?.jsonMember50s ?: "0"
            t4s.text = item?.jsonMember4s ?: "0"
            t6s.text = item?.jsonMember6s ?: "0"
            sr.text = item?.sR ?: "0"
            avg.text = item?.avg ?: "0"
            h.text = item?.h ?: "0"

        }
    }
}