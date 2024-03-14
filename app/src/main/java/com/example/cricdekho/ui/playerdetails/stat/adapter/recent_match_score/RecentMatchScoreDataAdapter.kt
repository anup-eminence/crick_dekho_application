package com.example.cricdekho.ui.playerdetails.stat.adapter.recent_match_score

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getPlayerStats.DataItem
import com.example.cricdekho.data.model.getPlayerStats.TablesItem
import com.example.cricdekho.databinding.RecentMatchDataItemBinding
import com.example.cricdekho.databinding.TournamentBattingDataBinding

class RecentMatchScoreDataAdapter : RecyclerView.Adapter<RecentMatchScoreDataAdapter.TournamentDataVH>() {

    private var battingList = emptyList<TablesItem?>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<TablesItem?>) {
        battingList = list
        notifyDataSetChanged()
    }

    class TournamentDataVH(val binding : RecentMatchDataItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentDataVH {
        return TournamentDataVH(RecentMatchDataItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return battingList.size
    }

    override fun onBindViewHolder(holder: TournamentDataVH, position: Int) {
        val item = battingList[position]
        holder.binding.apply {
            runs.text = item?.rUNS?:"0"
            o.text = item?.o?:"0"
            w.text = item?.w?:"0"
            r.text = item?.r?:"0"
            bf.text = item?.bF?:"0"
            er.text = item?.eR?:"0"
            t4s.text = item?.jsonMember4s?:"0"
            t6s.text = item?.jsonMember6s?:"0"
            sr.text = item?.sR?:"0"
        }
    }
}