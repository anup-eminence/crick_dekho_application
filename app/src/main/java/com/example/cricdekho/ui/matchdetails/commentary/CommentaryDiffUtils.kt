package com.example.cricdekho.ui.matchdetails.commentary

import androidx.recyclerview.widget.DiffUtil
import com.example.cricdekho.data.model.getMatchDetails.Commentary

class CommentaryDiffUtils(private val oldList: List<Commentary>,
    private val newList : List<Commentary>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
       return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].timestamp == newList[newItemPosition].timestamp
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  when {
            oldList[oldItemPosition].timestamp != newList[newItemPosition].timestamp -> false
            oldList[oldItemPosition].comment_text != newList[newItemPosition].comment_text -> false
            oldList[oldItemPosition].batsmen != newList[newItemPosition].batsmen -> false
            oldList[oldItemPosition].bowler != newList[newItemPosition].bowler -> false
            oldList[oldItemPosition].bowlers != newList[newItemPosition].bowlers -> false
            oldList[oldItemPosition].comment_edited != newList[newItemPosition].comment_edited -> false
            oldList[oldItemPosition].comment_type != newList[newItemPosition].comment_type -> false
            oldList[oldItemPosition].inning_number != newList[newItemPosition].inning_number -> false
            oldList[oldItemPosition].opta_ball_type != newList[newItemPosition].opta_ball_type -> false
            oldList[oldItemPosition].over != newList[newItemPosition].over -> false
            oldList[oldItemPosition].over_summary != newList[newItemPosition].over_summary -> false
            oldList[oldItemPosition].runs != newList[newItemPosition].runs -> false
            oldList[oldItemPosition].score != newList[newItemPosition].score -> false
            oldList[oldItemPosition].title != newList[newItemPosition].title -> false
            oldList[oldItemPosition].timestamp != newList[newItemPosition].timestamp -> false
            oldList[oldItemPosition].custom_image != newList[newItemPosition].custom_image -> false
            oldList[oldItemPosition].custom_title != newList[newItemPosition].custom_title -> false
            oldList[oldItemPosition].custom_url != newList[newItemPosition].custom_url -> false
            else -> true
        }
    }
}