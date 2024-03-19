package com.example.cricdekho.ui.matchdetails.scorecard.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.Batting
import com.example.cricdekho.data.model.getMatchDetails.Bowling
import com.example.cricdekho.data.model.getMatchDetails.FallOfWicketsArray

class WicketsDiffUtils(
    private val  oldList: List<FallOfWicketsArray>,
    private val newList: List<FallOfWicketsArray>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].score == newList[newItemPosition].score
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].batsman_name != newList[newItemPosition].batsman_name -> false
            oldList[oldItemPosition].batsman_slug != newList[newItemPosition].batsman_slug -> false
            oldList[oldItemPosition].bowler_name != newList[newItemPosition].bowler_name -> false
            oldList[oldItemPosition].bowler_slug != newList[newItemPosition].bowler_slug -> false
            oldList[oldItemPosition].how_out != newList[newItemPosition].how_out -> false
            oldList[oldItemPosition].number != newList[newItemPosition].number -> false
            oldList[oldItemPosition].overs != newList[newItemPosition].overs -> false
            oldList[oldItemPosition].runs != newList[newItemPosition].runs -> false
            oldList[oldItemPosition].score != newList[newItemPosition].score -> false
            else -> true
        }
    }
}