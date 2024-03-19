package com.example.cricdekho.ui.matchdetails.scorecard.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.Batting
import com.example.cricdekho.data.model.getMatchDetails.Bowling

class BowlingDiffUtils(
    private val  oldList: List<Bowling>,
    private val newList: List<Bowling>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].maiden_overs == newList[newItemPosition].maiden_overs
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].economy != newList[newItemPosition].economy -> false
            oldList[oldItemPosition].extras != newList[newItemPosition].extras -> false
            oldList[oldItemPosition].maiden_overs != newList[newItemPosition].maiden_overs -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].overs != newList[newItemPosition].overs -> false
            oldList[oldItemPosition].runs != newList[newItemPosition].runs -> false
            oldList[oldItemPosition].slug != newList[newItemPosition].slug -> false
            oldList[oldItemPosition].wickets != newList[newItemPosition].wickets -> false
            else -> true
        }
    }
}