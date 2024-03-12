package com.example.cricdekho.ui.matchdetails.scorecard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getMatchDetails.Batting
import com.example.cricdekho.data.model.getMatchDetails.Bowling
import com.example.cricdekho.data.model.getMatchDetails.FallOfWicketsArray
import com.example.cricdekho.databinding.ItemBowlersBinding
import com.example.cricdekho.databinding.ItemTotalScoreBinding
import com.example.cricdekho.databinding.ItemWicketsBinding
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.BowlingDiffUtils
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.TotalScoreDiffUtils
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.WicketsDiffUtils

class WicketsNewAdapter : RecyclerView.Adapter<WicketsNewAdapter.TotalScoreVH>(){

    var oldList  = emptyList<FallOfWicketsArray>()

    class TotalScoreVH(val binding : ItemWicketsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalScoreVH {
        return TotalScoreVH(ItemWicketsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
            tvBattersWicket.text = model.batsman_name
            tvText.text = model.how_out
            tvScore.text = "${model.number} - ${model.score}"
            tvOver.text = model.overs
        }
    }

    fun setData(newList: List<FallOfWicketsArray>){
        val diffUtil = WicketsDiffUtils(oldList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        result.dispatchUpdatesTo(this)
    }
}