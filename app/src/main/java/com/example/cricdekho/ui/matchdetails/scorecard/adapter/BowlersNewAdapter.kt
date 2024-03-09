package com.example.cricdekho.ui.matchdetails.scorecard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getMatchDetails.Batting
import com.example.cricdekho.data.model.getMatchDetails.Bowling
import com.example.cricdekho.databinding.ItemBowlersBinding
import com.example.cricdekho.databinding.ItemTotalScoreBinding
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.BowlingDiffUtils
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.TotalScoreDiffUtils

class BowlersNewAdapter : RecyclerView.Adapter<BowlersNewAdapter.TotalScoreVH>(){

    var oldList  = emptyList<Bowling>()

    class TotalScoreVH(val binding : ItemBowlersBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalScoreVH {
        return TotalScoreVH(ItemBowlersBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return oldList.size
    }

    override fun onBindViewHolder(holder: TotalScoreVH, position: Int) {
       bindItem(holder,position)
    }

    private fun bindItem(holder: TotalScoreVH, position: Int) {
        val model = oldList[position]
        holder.binding.apply {
            tvBowlers.text = model.name
            tvO.text = model.overs
            tvM.text = model.maiden_overs
            tvRBowler.text = model.runs
            tvW.text = model.wickets
            tvEco.text = model.economy.toString()
        }
    }

    fun setData(newList: List<Bowling>){
        val diffUtil = BowlingDiffUtils(oldList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        result.dispatchUpdatesTo(this)
    }
}