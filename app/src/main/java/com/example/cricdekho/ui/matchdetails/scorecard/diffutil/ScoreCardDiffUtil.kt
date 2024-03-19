package com.example.cricdekho.ui.matchdetails.scorecard.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.InningTab

class ScoreCardDiffUtil(
    private val oldList : List<InningTab>,
    private val newList: List<InningTab>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].inningsNo != newList[newItemPosition].inningsNo
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].inningsNo != newList[newItemPosition].inningsNo -> false
            oldList[newItemPosition].name != newList[newItemPosition].name -> false
            else -> true
        }
    }
}