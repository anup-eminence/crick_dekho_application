package com.example.cricdekho.ui.playerdetails.stat.adapter.recent_match_score

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getPlayerStats.DataItem
import com.example.cricdekho.data.model.getPlayerStats.TablesItem
import com.example.cricdekho.data.model.getPlayerStats.VSTeamStatsItem
import com.example.cricdekho.databinding.InnItemBinding
import com.example.cricdekho.databinding.VsLayoutBinding

class RecentMatchVsStatAdapter : RecyclerView.Adapter<RecentMatchVsStatAdapter.BattingStatVH>() {

    private var vsTeamList = emptyList<TablesItem?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<TablesItem?>){
        println(">>>>>>>>>>>>>>>>>>>>>>datacaught $list")
        vsTeamList = list
        notifyDataSetChanged()
    }

    class BattingStatVH(val binding : VsLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingStatVH {
        return BattingStatVH(VsLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return vsTeamList.size
    }

    override fun onBindViewHolder(holder: BattingStatVH, position: Int) {
        val item = vsTeamList[position]
        holder.binding.apply {
            countryName.text = item?.match

        }

    }
}