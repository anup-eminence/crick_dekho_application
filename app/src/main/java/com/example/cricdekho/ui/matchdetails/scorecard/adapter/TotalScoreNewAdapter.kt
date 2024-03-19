package com.example.cricdekho.ui.matchdetails.scorecard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.cricdekho.data.model.getMatchDetails.Batting
import com.example.cricdekho.databinding.ItemTotalScoreBinding
import com.example.cricdekho.theme.CurrentTheme
import com.example.cricdekho.ui.matchdetails.scorecard.diffutil.TotalScoreDiffUtils

class TotalScoreNewAdapter : RecyclerView.Adapter<TotalScoreNewAdapter.TotalScoreVH>(){

    var oldList  = emptyList<Batting>()

    class TotalScoreVH(val binding : ItemTotalScoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalScoreVH {
        return TotalScoreVH(ItemTotalScoreBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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
            CurrentTheme.changeTextColor(tvR,holder.binding.root.context)
            CurrentTheme.changeTextColor(tvB,holder.binding.root.context)
            CurrentTheme.changeTextColor(tv4s,holder.binding.root.context)
            CurrentTheme.changeTextColor(tv6s,holder.binding.root.context)
            CurrentTheme.changeTextColor(tvSR,holder.binding.root.context)
            tvBatters.text = model.name
            tvText.text = model.out_str
            tvR.text = model.runs
            tvB.text = model.balls
            tv4s.text = model.fours
            tv6s.text = model.sixes
            tvSR.text = model.strike_rate.toString()
        }
    }

    fun setData(newList: List<Batting>){
        val diffUtil = TotalScoreDiffUtils(oldList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        result.dispatchUpdatesTo(this)
    }
}