package com.example.cricdekho.ui.matchdetails.scorecard.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.Batting

class TotalScoreDiffUtils(
    private val  oldList: List<Batting>,
    private val newList: List<Batting>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].balls == newList[newItemPosition].balls
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].balls != newList[newItemPosition].balls -> false
            oldList[oldItemPosition].is_captain != newList[newItemPosition].is_captain -> false
            oldList[oldItemPosition].runs != newList[newItemPosition].runs -> false
            oldList[oldItemPosition].fours != newList[newItemPosition].fours -> false
            oldList[oldItemPosition].out_str != newList[newItemPosition].out_str -> false
            oldList[oldItemPosition].slug != newList[newItemPosition].slug -> false
            oldList[oldItemPosition].strike_rate != newList[newItemPosition].strike_rate -> false
            oldList[oldItemPosition].sixes != newList[newItemPosition].sixes -> false
            else -> true
        }
    }
}